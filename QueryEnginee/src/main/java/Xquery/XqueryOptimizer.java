package Xquery;

import gen.base.*;

import java.util.*;
import java.util.stream.Collectors;

public class XqueryOptimizer extends XqueryBaseVisitor<String> {
    // stages while visiting Xquery
    private enum State {
        BEGIN, FOR, WHERE, RETURN, OPTIMIZE, REWRITE
    }

    private class QueryInfo {
        HashMap<String, String> map = new HashMap<>(); // map variables and expressions
        HashMap<String, String> dependencies = new HashMap<>(); // track dependency between variables ("$x", "$y")
        LinkedHashMap<String, LinkedList<String>> groupedVariables = new LinkedHashMap<>(); // (root, variables on the root)
        LinkedList<String> ccEqual = new LinkedList<>(); // "..." = "..."
        HashMap<String, HashSet<String>> vvEqual = new HashMap<>(); // $a = $b
        HashMap<String, LinkedList<String>> vcEqual = new HashMap<>(); // $a = "..." or "..." = $a
        LinkedList<String> joinedVariables = new LinkedList<>(); // stored joined variables
        boolean optimizable = true;
        State state = State.BEGIN;
    }

    private QueryInfo queryInfo;
    public XqueryOptimizer() {
        queryInfo = new QueryInfo();
    }

    @Override
    public String visitStrXQ(XqueryParser.StrXQContext ctx) {
        if (queryInfo.state == State.FOR) queryInfo.optimizable = false;
        return ctx.str().getText();
    }

    @Override
    public String visitUnionXQ(XqueryParser.UnionXQContext ctx) {
        if ((queryInfo.state == State.FOR) || (queryInfo.state == State.WHERE)) queryInfo.optimizable = false;
        return visit(ctx.xq(0)) + "," + visit(ctx.xq(1));
    }

    private String writeFlwr(XqueryParser.FlwrXQContext ctx){
        String result = "";
        // for
        result += visit(ctx.forClause());
        // let
        if (ctx.letClause() != null) {
            result += visit(ctx.letClause());
        }
        // where
        if (ctx.whereClause() != null) {
            result += visit(ctx.whereClause());
        }
        // return
        result += visit(ctx.returnClause());
        return result;
    }

    /**
     * Constructs the next subquery for FLWR expressions based on current grouped variables.
     */
    private String constructNextSubquery() {
        // Extracting the root and its associated variables for the current subquery
        Map.Entry<String, LinkedList<String>> entry = queryInfo.groupedVariables.entrySet().iterator().next();
        String rootVariable = entry.getKey();
        LinkedList<String> dependentVariables = queryInfo.groupedVariables.remove(rootVariable);

        // for clause
        String result = " for ";
        List<String> forClauseItems = new ArrayList<>();
        dependentVariables.forEach(var -> {
            forClauseItems.add(var + " in " + queryInfo.map.get(var));
        });
        result += String.join(", ", forClauseItems);

        // where clause "$x = 1"
        List<String> conditions = new ArrayList<>(queryInfo.ccEqual);
        dependentVariables.forEach(var -> {
            conditions.addAll(queryInfo.vcEqual.getOrDefault(var, new LinkedList<>()).stream()
                    .map(cond -> var + " = " + cond)
                    .toList());
        });
        // Including variable-variable equalities
        dependentVariables.forEach(var1 -> {
            dependentVariables.forEach(var2 -> {
                if (queryInfo.vvEqual.getOrDefault(var1, new HashSet<>()).contains(var2)) {
                    conditions.add(var1 + " = " + var2);
                }
            });
        });
        if (!conditions.isEmpty()) {
            result += "\n where " + String.join(" and ", conditions);
        }

        // Build the 'return' clause, wrapping each variable with XML tags
        List<String> returnItems = dependentVariables.stream()
                .map(var -> "<" + var.substring(1) + ">{" + var + "}</" + var.substring(1) + ">")
                .collect(Collectors.toList());
        result += "\n return <tuple>{" + String.join(", ", returnItems) + "}</tuple>";
        queryInfo.joinedVariables.addAll(dependentVariables);

        return "\n" + result;
    }

    /**
     * optimizes an FLWR (For-Let-Where-Return)
     */
    @Override
    public String visitFlwrXQ(XqueryParser.FlwrXQContext ctx) {
        // If not in the initial state or not optimizable, write directly without optimization.
        if ((queryInfo.state != State.BEGIN) || (!queryInfo.optimizable)) {
            queryInfo.optimizable = false;
            return writeFlwr(ctx);
        }
        // Check that the query can be optimized
        queryInfo.state = State.FOR;
        visit(ctx.forClause());
        if (!queryInfo.optimizable) return writeFlwr(ctx);
        if (ctx.letClause() != null) {
            queryInfo.optimizable = false;
            return writeFlwr(ctx);
        }
        queryInfo.state = State.WHERE;
        if (ctx.whereClause() != null) {
            visit(ctx.whereClause());
        }
        if (!queryInfo.optimizable) {
            return writeFlwr(ctx);
        }
        queryInfo.state = State.RETURN;
        visit(ctx.returnClause());
        if (!queryInfo.optimizable) {
            return writeFlwr(ctx);
        }
        // Optimize the query
        queryInfo.state = State.OPTIMIZE;
        visit(ctx.forClause());
        if (ctx.whereClause() != null) {
            visit(ctx.whereClause());
        }

        // If less than two groups are formed, no need to optimize
        if (queryInfo.groupedVariables.size() <= 1) {
            queryInfo = new QueryInfo();
            return writeFlwr(ctx);
        }
        String result = "for $tuple in ";
        String tmp = constructNextSubquery();
        // Iteratively build the JOIN clauses by pairing variables based on equality
        while (!queryInfo.groupedVariables.isEmpty()) {
            LinkedList<String> nextGroupedVariables = queryInfo.groupedVariables.entrySet().iterator().next().getValue();
            LinkedList<String> matchLeft = new LinkedList<>();
            LinkedList<String> matchRight = new LinkedList<>();

            // Evaluate join conditions based on variable equalities
            for (String current : queryInfo.joinedVariables) {
                HashSet<String> varEquals = queryInfo.vvEqual.getOrDefault(current, new HashSet<>());
                for (String next : nextGroupedVariables) {
                    if (varEquals.contains(next)) {
                        matchLeft.add(current.substring(1));
                        matchRight.add(next.substring(1));

                    }
                }
            }
            String right = constructNextSubquery();
            tmp = "join(" + tmp + "," + right + "," + "\n [" + String.join(",", matchLeft) + "]," +
                    "[" + String.join(",", matchRight) + "])";
        }
        // Rewrite return clause
        queryInfo.state = State.REWRITE;
        tmp += visit(ctx.returnClause());
        result += tmp;
        queryInfo = new QueryInfo();
        return result;
    }

    @Override
    public String visitTagXQ(XqueryParser.TagXQContext ctx) {
        if ((queryInfo.state == State.FOR) || (queryInfo.state == State.WHERE)) queryInfo.optimizable = false;
        return "<" + ctx.tagName(0).getText() + ">{" + visit(ctx.xq()) + "}</" + ctx.tagName(1).getText() + ">";
    }

    @Override
    public String visitAllXQ(XqueryParser.AllXQContext ctx) {
        if (queryInfo.state == State.WHERE) queryInfo.optimizable = false;
        return visit(ctx.xq()) + "//" + visit(ctx.rp());
    }

    @Override
    public String visitApXQ(XqueryParser.ApXQContext ctx) {
        if (queryInfo.state == State.WHERE) queryInfo.optimizable = false;
        return visit(ctx.ap());
    }

    // letxq cannot be optimized
    @Override
    public String visitLetXQ(XqueryParser.LetXQContext ctx) {
        queryInfo.optimizable = false;
        return visit(ctx.letClause()) + " " + visit(ctx.xq());
    }

    @Override
    public String visitHaveXQ(XqueryParser.HaveXQContext ctx) {
        return "(" + visit(ctx.xq()) + ")";
    }

    @Override
    public String visitVarXQ(XqueryParser.VarXQContext ctx) {
        String var = ctx.var().getText();;
        if (queryInfo.state == State.REWRITE) {
            String varName = var.substring(1);
            var = "$tuple/" + varName + "/*";
        }
        return var;
    }

    // joinxq cannot be optimized
    @Override
    public String visitJoinXQ(XqueryParser.JoinXQContext ctx) {
        queryInfo.optimizable = false;
        return visit(ctx.joinClause());
    }

    @Override
    public String visitChildXQ(XqueryParser.ChildXQContext ctx) {
        if (queryInfo.state == State.WHERE) {
            queryInfo.optimizable = false;
        }
        return visit(ctx.xq()) + "/" + visit(ctx.rp());
    }


    /**
     * Visits the 'For' clause part of an XQuery FLWR expression.
     * In the non-optimization phase, constructs a standard 'for' clause string.
     * In the optimization phase, collects variable bindings and their dependencies
     */
    public String visitForClause(XqueryParser.ForClauseContext ctx) {
        String result = " for ";
        int n = ctx.var().size();
        // Iterating over all variable-bindings in the 'for' clause
        for (int i = 0; i < n; i++) {
            String variable = ctx.var(i).getText();
            String expression = visit(ctx.xq(i));
            result += variable + " in " + expression;

            if (i < n - 1) {
                result += ", ";
            }

            // optimization phase
            if (queryInfo.state == State.OPTIMIZE) {
                queryInfo.map.put(variable, expression);

                // Check dependency
                if (expression.startsWith("$")) {
                    String parentVar = expression.split("/")[0];  // Extracting the parent variable
                    queryInfo.dependencies.put(variable, parentVar);

                    // root variable in the dependency chain
                    String rootVar = findRootVariable(parentVar, queryInfo.dependencies);

                    // Group dependent variables under their root variable
                    queryInfo.groupedVariables.computeIfAbsent(rootVar, k -> new LinkedList<>()).add(variable);
                } else {
                    queryInfo.dependencies.put(variable, null); // No parent
                    queryInfo.groupedVariables.put(variable, new LinkedList<>(Arrays.asList(variable)));
                }
            }
        }
        return result.toString();
    }

    /**
     * Finds the root variable in a chain of dependencies.
     */
    private String findRootVariable(String variable, Map<String, String> dependencies) {
        while (dependencies.containsKey(variable) && dependencies.get(variable) != null) {
            variable = dependencies.get(variable);
        }
        return variable;
    }

    @Override
    public String visitLetClause(XqueryParser.LetClauseContext ctx) {
        String result = " let ";
        int n = ctx.var().size();
        for (int i = 0; i < n; ++i) {
            result += ctx.var(i).getText();
            result += ":=";
            result += visit(ctx.xq(i));

            if (i != (n - 1)) {
                result += ", ";
            }
        }
        return result.toString();
    }

    @Override
    public String visitWhereClause(XqueryParser.WhereClauseContext ctx) {
        return " where" + visit(ctx.cond());
    }

    @Override
    public String visitReturnClause(XqueryParser.ReturnClauseContext ctx) {
        return "\nreturn " + visit(ctx.xq());
    }

    @Override
    public String visitJoinClause(XqueryParser.JoinClauseContext ctx) {
        return " join(" + visit(ctx.xq(0)) + "," + visit(ctx.xq(1)) + ","
                + visit(ctx.tags(0)) + "," + visit(ctx.tags(1)) + ")";
    }

    @Override
    public String visitTags(XqueryParser.TagsContext ctx) {
        String result = "[";

        for (int i = 0; i < ctx.ID().size(); i++) {
            result += ctx.ID(i).getText();
            if (i != (ctx.ID().size() - 1)) {
                result += ", ";
            }
        }
        result += "]";
        return result;
    }

    @Override
    public String visitOrCond(XqueryParser.OrCondContext ctx) {
        queryInfo.optimizable = false;
        return visit(ctx.cond(0)) + " or " + visit(ctx.cond(1));
    }

    @Override
    public String visitEmpCond(XqueryParser.EmpCondContext ctx) {
        queryInfo.optimizable = false;
        return " empty(" + visit(ctx.xq()) + ")";
    }

    @Override
    public String visitHaveCond(XqueryParser.HaveCondContext ctx) {
        return "(" + visit(ctx.cond()) + ")";
    }

    // | Cond ’and’ Cond
    @Override
    public String visitAndCond(XqueryParser.AndCondContext ctx) {
        return visit(ctx.cond(0)) + " and " + visit(ctx.cond(1));
    }

    @Override
    public String visitIsCond(XqueryParser.IsCondContext ctx) {
        queryInfo.optimizable = false;
        return visit(ctx.xq(0)) + "==" + visit(ctx.xq(1));
    }

    @Override
    public String visitSatCond(XqueryParser.SatCondContext ctx) {
        queryInfo.optimizable = false;
        String result = " some ";
        int n = ctx.var().size();
        for (int i = 0; i < n; i++) {
            result += ctx.var(i).getText();
            result += " in ";
            result += visit(ctx.xq(i));

            if (i < (n - 1)) {
                result += ", ";
            }
        }
        result += " satisfies ";
        result += visit(ctx.cond());
        return result;
    }
    // (Var|Constant) ’eq’ (Var|Constant)
    @Override
    public String visitEqCond(XqueryParser.EqCondContext ctx) {
        // Check if in the optimization phase
        if (queryInfo.state == State.OPTIMIZE) {
            String left = visit(ctx.xq(0));
            String right = visit(ctx.xq(1));
            boolean varLeft = left.startsWith("$");
            boolean varRight = right.startsWith("$");
            if (varLeft && varRight) {
                queryInfo.vvEqual.computeIfAbsent(left, k -> new HashSet<>()).add(right);
                queryInfo.vvEqual.computeIfAbsent(right, k -> new HashSet<>()).add(left);
            }
            // reduce scope
            else if (varLeft) {
                queryInfo.vcEqual.computeIfAbsent(left, k -> new LinkedList<>()).add(right);
            }
            else if (varRight) {
                queryInfo.vcEqual.computeIfAbsent(right, k -> new LinkedList<>()).add(left);

            }
            // find directly
            else {
                queryInfo.ccEqual.add(left + " = " + right);
            }
        }
        return visit(ctx.xq(0)) + "=" + visit(ctx.xq(1));
    }

    @Override
    public String visitNotCond(XqueryParser.NotCondContext ctx) {
        queryInfo.optimizable = false;
        return " not " + visit(ctx.cond());
    }

    @Override
    public String visitVar(XqueryParser.VarContext ctx) {
        return super.visitVar(ctx);
    }

    @Override
    public String visitChildrenAp(XqueryParser.ChildrenApContext ctx) {
        return visit(ctx.doc()) + "/" + visit(ctx.rp());
    }

    @Override
    public String visitAllAp(XqueryParser.AllApContext ctx) {
        return visit(ctx.doc()) + "//" + visit(ctx.rp());
    }

    @Override
    public String visitHaveRp(XqueryParser.HaveRpContext ctx) {
        return "(" + visit(ctx.rp()) + ")";
    }

    @Override
    public String visitChildRp(XqueryParser.ChildRpContext ctx) {
        return "*";
    }

    @Override
    public String visitTextRp(XqueryParser.TextRpContext ctx) {
        return "text()";
    }

    @Override
    public String visitParentRp(XqueryParser.ParentRpContext ctx) {
        return "..";
    }

    @Override
    public String visitUnionRp(XqueryParser.UnionRpContext ctx) {
        return visit(ctx.rp(0)) + "," + visit(ctx.rp(1));
    }

    @Override
    public String visitCurrentRp(XqueryParser.CurrentRpContext ctx) {
        return ".";
    }

    @Override
    public String visitFilterRP(XqueryParser.FilterRPContext ctx) {
        return visit(ctx.rp()) + "[" + visit(ctx.f()) + "]";
    }

    @Override
    public String visitAllRP(XqueryParser.AllRPContext ctx) {
        return visit(ctx.rp(0)) + "//" + visit(ctx.rp(1));
    }

    @Override
    public String visitChildrenRP(XqueryParser.ChildrenRPContext ctx) {
        return visit(ctx.rp(0)) + "/" + visit(ctx.rp(1));
    }

    @Override
    public String visitTagRp(XqueryParser.TagRpContext ctx) {
        return ctx.tagName().getText();
    }

    @Override
    public String visitAttributeRp(XqueryParser.AttributeRpContext ctx) {
        return "@" + ctx.attName().getText();
    }

    @Override
    public String visitEqFilter(XqueryParser.EqFilterContext ctx) {
        return visit(ctx.rp(0)) + "=" + visit(ctx.rp(1));
    }

    @Override
    public String visitHaveFilter(XqueryParser.HaveFilterContext ctx) {
        return "(" + visit(ctx.f()) + ")";
    }

    @Override
    public String visitNotFilter(XqueryParser.NotFilterContext ctx) {
        return " not " + visit(ctx.f());
    }

    @Override
    public String visitStrFilter(XqueryParser.StrFilterContext ctx) {
        return visit(ctx.rp()) + "=" + ctx.str().getText();
    }

    @Override
    public String visitAndFilter(XqueryParser.AndFilterContext ctx) {
        return visit(ctx.f(0)) + " and " + visit(ctx.f(1));
    }

    @Override
    public String visitIsFilter(XqueryParser.IsFilterContext ctx) {
        return visit(ctx.rp(0)) + "==" + visit(ctx.rp(1));
    }

    @Override
    public String visitRpFilter(XqueryParser.RpFilterContext ctx) {
        return visit(ctx.rp());
    }

    @Override
    public String visitOrFilter(XqueryParser.OrFilterContext ctx) {
        return visit(ctx.f(0)) + " or " + visit(ctx.f(1));
    }

    @Override
    public String visitDoc(XqueryParser.DocContext ctx) {
        return "doc(" + ctx.fileName().getText() + ")";
    }

    @Override
    public String visitTagName(XqueryParser.TagNameContext ctx) {
        return super.visitTagName(ctx);
    }

    @Override
    public String visitAttName(XqueryParser.AttNameContext ctx) {
        return super.visitAttName(ctx);
    }

    @Override
    public String visitStr(XqueryParser.StrContext ctx) {
        return super.visitStr(ctx);
    }

    @Override
    public String visitFileName(XqueryParser.FileNameContext ctx) {
        return super.visitFileName(ctx);
    }

}
