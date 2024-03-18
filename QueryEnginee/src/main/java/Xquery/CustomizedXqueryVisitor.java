package Xquery;

import XML.XMLUtil;
import Xpath.*;
import gen.base.*;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;
import java.util.stream.Collectors;

public class CustomizedXqueryVisitor extends XqueryBaseVisitor<LinkedList<Node>> {

    private LinkedList<Node> nodes;
    private XMLUtil xmlUtil = new XMLUtil();
    private HashMap<String, LinkedList<Node>> map = new HashMap<>();
    private Stack<HashMap<String, LinkedList<Node>>> stack = new Stack<>();
    private Document outputDoc = null;
    private Document doc = null;

    public Document getOutputDoc(){
        return outputDoc;
    }

    @Override
    public LinkedList<Node> visitJoinXQ(XqueryParser.JoinXQContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public LinkedList<Node> visitJoinClause(XqueryParser.JoinClauseContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        LinkedList<Node> origin = new LinkedList<>(nodes);
        this.nodes = origin;
        LinkedList<Node> left = visit(ctx.xq(0));
        this.nodes = origin;
        LinkedList<Node> right = visit(ctx.xq(1));

        // Extract tags for joining
        List<TerminalNode> joinTagsLeft = ctx.tags(0).ID();
        List<TerminalNode> joinTagsRight = ctx.tags(1).ID();
        HashMap<String, LinkedList<Node>> rightHashed = new HashMap<>();

        validateTags(joinTagsLeft, joinTagsRight);

       // the right side
        for (Node r: right){
            String rightKey = generateHashKey(r, joinTagsRight);
            rightHashed.computeIfAbsent(rightKey, k -> new LinkedList<>()).add(r);
        }

        // loop the left side and match with the right one
        for (Node l: left) {
            String leftKey = generateHashKey(l, joinTagsLeft);
            LinkedList<Node> matchingNodes = rightHashed.get(leftKey);

            if (matchingNodes != null) {
                for (Node match : matchingNodes) {
                    result.add(mergeNodes(l, match, doc));
                }
            }
        }
        this.nodes = result;
        return result;
    }

    // Ensures that the lists of tags used to join nodes from the left and
    // right subqueries contain the same number of tags.
    public void validateTags(List<TerminalNode> leftTags, List<TerminalNode> rightTags) {
        if (leftTags.size() != rightTags.size()) {
            throw new IllegalArgumentException("The number of tags on the left and right sides do not match.");
        }
    }

        // Generates a unique key for a node based on the values of specified tags.
        // This is used to identify which nodes from the left and right subqueries should be joined.
    public static String generateHashKey(Node node, List<TerminalNode> tags) {
        StringBuilder keyBuilder = new StringBuilder();
        // store all label names for quick check if the node's name is in the label
        Set<String> tagNames = tags.stream().map(TerminalNode::getText).collect(Collectors.toSet());

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            // check if the current child is in tags
            if (tagNames.contains(child.getNodeName())) {
                String content = child.getTextContent();
                if (content != null) {
                    keyBuilder.append(content.trim());
                }
                keyBuilder.append('|'); // separate different tag names
            }
        }
        return keyBuilder.toString();
    }


    // individual matches between the left and right subqueries are consolidated into single nodes
    public Node mergeNodes(Node leftNode, Node rightNode, Document doc) {
        Element mergedElement = doc.createElement("joined");

        // combine left
        NodeList leftChildren = leftNode.getChildNodes();
        for (int i = 0; i < leftChildren.getLength(); i++) {
            Node importedChild = doc.importNode(leftChildren.item(i), true);
            mergedElement.appendChild(importedChild);
        }

        // combine right
        NodeList rightChildren = rightNode.getChildNodes();
        for (int i = 0; i < rightChildren.getLength(); i++) {
            Node importedChild = doc.importNode(rightChildren.item(i), true);
            mergedElement.appendChild(importedChild);
        }

        return mergedElement;
    }

    public CustomizedXqueryVisitor() throws Exception{
        this.nodes = new LinkedList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        doc = builder.newDocument();
        outputDoc = builder.newDocument();
    }


    /**
     * [Var]]X (C) = < C(Var) >
     */
    @Override
    public LinkedList<Node> visitVarXQ(XqueryParser.VarXQContext ctx) {
        return map.get(ctx.getText());
    }

    /**
     * [StringConstant]]X (C) = < makeText(StringConstant) >
     */
    @Override
    public LinkedList<Node> visitStrXQ(XqueryParser.StrXQContext ctx) {
        String fullText = ctx.str().getText();
        String text = fullText.substring(1, fullText.length() - 1);
        LinkedList<Node> result = new LinkedList<>();
        result.add(generateText(text));
        nodes = result;
        return result;
    }


    /**
     * [[ap]]X (C) = [[ap]]A
     */
    @Override
    public LinkedList<Node> visitApXQ(XqueryParser.ApXQContext ctx) {
        return visit(ctx.ap());
    }

    /**
     * [(XQ1)]]X (C) = [[XQ1]]X (C)
     */
    @Override
    public LinkedList<Node> visitHaveXQ(XqueryParser.HaveXQContext ctx) {
        return visit(ctx.xq());
    }


    /**
     * [XQ1, XQ2]]X (C) = [[XQ1]]X (C), [[XQ2]]X (C)
     */
    @Override
    public LinkedList<Node> visitUnionXQ(XqueryParser.UnionXQContext ctx) {
        LinkedList<Node> original = nodes;
        LinkedList<Node> result = new LinkedList<>(visit(ctx.xq(0)));
        this.nodes = original;
        result.addAll(visit(ctx.xq(1)));
        this.nodes = result;
        return result;
    }


    /**
     * [XQ1/rp]]X (C) = unique(< m | n ← [[XQ1]]X (C), m ← [[rp]]R(n) >)
     */
    @Override
    public LinkedList<Node> visitChildXQ(XqueryParser.ChildXQContext ctx) {
        // Directly evaluate the 'xq' part and use the result for 'rp' evaluation
        LinkedList<Node> children = new LinkedList<>(visit(ctx.xq()));
        LinkedList<Node> result = CustomizedXqueryEvaluator.evaluateRP(children, ctx.rp().getText());
        nodes = result;
        return result;
    }

    /**
     * [XQ1//rp]]X (C) = unique(< m | n ← [[XQ1]]X (C), m ← [[.//rp]]R(n) >)(
     */
    @Override
    public LinkedList<Node> visitAllXQ(XqueryParser.AllXQContext ctx) {
        // Directly evaluate the 'xq' part to get the current nodes
        LinkedList<Node> descendants = new LinkedList<>(visit(ctx.xq()));
        descendants.addAll(CustomizedEvaluator.getDescendants(descendants));
        LinkedList<Node> result = CustomizedXqueryEvaluator.evaluateRP(descendants, ctx.rp().getText());
        nodes = result;
        return result;
    }

    /**
     * [<tagName>{XQ1}</tagName>]]X (C) = < makeElem(tagName, [[XQ1]]X (C)) >
     */
    @Override
    public LinkedList<Node> visitTagXQ(XqueryParser.TagXQContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        String tagName = ctx.tagName(0).getText();
        Node element = generateElement(tagName, visit(ctx.xq()));

        result.add(element);
        this.nodes = result;
        return result;
    }

    /**
     *[XQ1 eq XQ2]]C (C) = [[XQ1 = XQ2]]C (C) = ∃x ∈ [[XQ1]]X (C) ∃y ∈ [[XQ2]]X (C) x eq y
     */
    @Override
    public LinkedList<Node> visitEqCond(XqueryParser.EqCondContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        LinkedList<Node> nodes = this.nodes;
        LinkedList<Node> left = visit(ctx.xq(0));
        LinkedList<Node> right = visit(ctx.xq(1));
        this.nodes = nodes;

        if (CustomizedXqueryEvaluator.isEqual(left, right)){
            result.add(doc.createTextNode("true"));
            return result;
        }
        return result;
    }

    /**
     *[[XQ1 is XQ2]]C (C) = [[XQ1 == XQ2]]C (C) = ∃x ∈ [[XQ1]]X (C) ∃y ∈ [[XQ2]]X (C) x is y
     */
    @Override
    public LinkedList<Node> visitIsCond(XqueryParser.IsCondContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        LinkedList<Node> nodes = this.nodes;
        LinkedList<Node> left = visit(ctx.xq(0));
        LinkedList<Node> right = visit(ctx.xq(1));
        this.nodes = nodes;
        if (CustomizedXqueryEvaluator.isSame(left,right)){
            result.add(doc.createTextNode("true"));
            return result;
        }
        return result;
    }

    /**
     * [[empty(XQ1)]]C (C) = [[XQ1]]X (C) =<>
     */
    @Override
    public LinkedList<Node> visitEmpCond(XqueryParser.EmpCondContext ctx) {
        LinkedList<Node> origin = nodes;
        LinkedList<Node> xq = visit(ctx.xq());
        LinkedList<Node> result = new LinkedList<>();
        nodes = origin;
        if (xq.isEmpty()) {
            result.add(doc.createTextNode("true"));
        }
        return result;
    }

    /**
     * [[some Var_1 in XQ_1, . . . , Var_n in XQ_n
     * satisfies Cond
     * ]]
     * Cn
     * (C) = ∃v1 ∈ [[XQ1]]X (C0)
     * . . .
     * ∃vn ∈ [[XQ_n]]X (Cn−1)
     * [[Cond]]C (Cn)
     * where C0 := C, Ci := {V ari 7 → vi}Ci−1, i ∈ [1, . . . , n]
     * */
    @Override
    public LinkedList<Node> visitSatCond(XqueryParser.SatCondContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        if (satisfyRecur(ctx, 0)){
            result.add(doc.createTextNode("true"));
        }
        return result;
    }

    private boolean satisfyRecur(XqueryParser.SatCondContext ctx, int num){
        int numVariables = ctx.var().size();
        if (num == numVariables){
            // Directly check the condition when all variables have been assigned
            return visit(ctx.cond()).size() == 1;
        }
        else{
            String key = ctx.var(num).getText();
            LinkedList<Node> values = visit(ctx.xq(num));
            for (Node node : values) {
                // Save the current context by pushing a copy onto the stack
                stack.push(new HashMap<>(map)); // Push a copy of the current context onto the stack

                // Set the new context for the variable
                LinkedList<Node> newContext = new LinkedList<>();
                newContext.add(node);
                map.put(key, newContext);

                // Recursively attempt to satisfy the condition with the updated context
                if (satisfyRecur(ctx, num + 1)) {
                    // If a satisfying assignment is found, no need to restore here, just return true
                    return true;
                }

                // Restore the original context before trying the next value
                map = stack.pop(); // Restore the context from the stack
            }
        }
        return false;
    }


    /**
     * FLWR
     [for Var_1 in XQ_1, . . . ,
        Var_n in XQ_n
     let Var_n+1 := XQ_n+1, . . . ,
     Var_n+k := XQ_n+k
     where Cond
     return XQ_n+k+1]
     =
     < [[XQ_n+k+1]]X (C_n+k) |
     v1 <- [[XQ1]]X (C0),
     . . . ,
     vn <- [[XQn]]X (Cn−1),
     [[Cond]]C (C_n+k ) >

     where C_0 := C, C_i := {Var_i -> v_i}C_i−1, i ∈ {1, . . . , n}
     and Cj := {Varj -> [[XQ_j ]]X (C_j−1)}C_j−1, j ∈ [n + 1, . . . , n + k]
     */
    @Override
    public LinkedList<Node> visitFlwrXQ(XqueryParser.FlwrXQContext ctx) {
        LinkedList<Node> results = new LinkedList<>();
        // Save the current context
        stack.push(new HashMap<>(map));
        // Start processing the FLWR expression recursively
        FlwrRecur(0, results, ctx);

        map = stack.pop();
        return results;
    }

    private void FlwrRecur(int num, LinkedList<Node> results, XqueryParser.FlwrXQContext ctx){
        int numVariables;
        numVariables = ctx.forClause().var().size();
        if (num == numVariables){
            if (ctx.letClause() != null){
                visit(ctx.letClause());
            }
            if (ctx.whereClause() != null && visit(ctx.whereClause()).isEmpty()) {
                return; // Skip if 'Where' condition fails
            }
            results.addAll(visit(ctx.returnClause()));
        }
        else{
            String key = ctx.forClause().var(num).getText();
            LinkedList<Node> valueList = visit(ctx.forClause().xq(num));

            for (Node node: valueList){
                // Save the current context before updating the variable
                stack.push(new HashMap<>(map));
                map.put(key, new LinkedList<>(Collections.singletonList(node)));
                FlwrRecur(num + 1, results, ctx);
                map = stack.pop();
            }
        }
    }

    /**
     * XQuery (let)
     * <pre>
     * [let Var_1 := xq_1, ..., Var_n = xq_n xq_n+1](C)
     *   → [xq_n+1](C_n)
     *   where C_0 := C, C_i := { Var_i → [xq_i](C_i-1) } ∪ C_i-1, i ∈ [1, ..., n]
     * </pre>
     *
     * @param ctx Current parse tree context
     * @return List of nodes returned by the xquery query applied
     * to the context generated by chaining the variable definitions
     */
    @Override
    public LinkedList<Node> visitLetXQ(XqueryParser.LetXQContext ctx) {
        HashMap<String, LinkedList<Node>> origin = new HashMap<>(map);
        stack.push(origin);
        LinkedList<Node> result = visitChildren(ctx);
        map = stack.pop();
        return result;
    }

    /**
     * XQuery - for clause
     */
    @Override
    public LinkedList<Node> visitForClause(XqueryParser.ForClauseContext ctx) {
        return null;
    }

    /**
     * XQuery - let clause
     */
    @Override
    public LinkedList<Node> visitLetClause(XqueryParser.LetClauseContext ctx) {
        // Return null to raise an exception if caller tries to use the result of visiting the let clause
        for (int i = 0; i < ctx.var().size(); i++) {
            String varName = ctx.var(i).getText();
            LinkedList<Node> queryValue = visit(ctx.xq(i));
            map.put(varName, queryValue);
        }
        return null;
    }

    /**
     * XQuery - where
     */
    @Override
    public LinkedList<Node> visitWhereClause(XqueryParser.WhereClauseContext ctx) {
        return visit(ctx.cond());
    }

    /**
     * XQuery - return
     */
    @Override
    public LinkedList<Node> visitReturnClause(XqueryParser.ReturnClauseContext ctx) {
        return visit(ctx.xq());
    }

    // XQuery - Condition
    /**
     *[[not Cond1]]C (C) = ¬[[Cond1]]C (C)
     */
    @Override
    public LinkedList<Node> visitNotCond(XqueryParser.NotCondContext ctx) {
        LinkedList<Node> not = new LinkedList<>(visit(ctx.cond()));
        LinkedList<Node> result = new LinkedList<>();
        if (not.isEmpty()){
            result.add(doc.createTextNode("true"));
        }
        nodes = result;
        return result;
    }

    /**
     [[(Cond1)]]C (C) = [[Cond1]]C (C)
     */
    @Override
    public LinkedList<Node> visitHaveCond(XqueryParser.HaveCondContext ctx) {
        return visit(ctx.cond());
    }

    /**
     * [[Cond1 or Cond2]]C (C) = [[Cond1]]C (C) ∨ [[Cond2]]C (C)
     */
    @Override
    public LinkedList<Node> visitOrCond(XqueryParser.OrCondContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        if ((!visit(ctx.cond(0)).isEmpty()) || (!visit(ctx.cond(1)).isEmpty())) {
            result.add(doc.createTextNode("true"));
        }
        return result;
    }

    /**
     * [Cond1 and Cond2]]C (C) = [[Cond1]]C (C) ∧ [[Cond2]]C (C)
     */
    @Override
    public LinkedList<Node> visitAndCond(XqueryParser.AndCondContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        if ((!visit(ctx.cond(0)).isEmpty()) && (!visit(ctx.cond(1)).isEmpty())) {
            result.add(doc.createTextNode("true"));
        }
        return result;
    }


    private Node generateElement(String tag, LinkedList<Node> list){
        return xmlUtil.createNode(outputDoc, tag, list);
    }

    private Node generateText(String s){
        return doc.createTextNode(s);
    }

    @Override
    public LinkedList<Node> visitVar(XqueryParser.VarContext ctx) {
        return visitChildren(ctx);
    }


    // Xpath
    /**
     * [doc ( fileName ) / rp]  ->  [rp] (root( fileName ))
     */
    @Override
    public LinkedList<Node> visitChildrenAp(XqueryParser.ChildrenApContext ctx) {
        visit(ctx.doc());
        return visit(ctx.rp());
    }

    /**
     * [doc ( fileName ) // rp]  ->  [. // rp] (root( fileName ))
     */
    @Override
    public LinkedList<Node> visitAllAp(XqueryParser.AllApContext ctx) {
        visit(ctx.doc());
        LinkedList<Node> descendants = CustomizedEvaluator.getDescendants(nodes);
        nodes.addAll(descendants);
        return visit(ctx.rp());
    }

    /**
     * [ () ]  ->  children(n)
     */
    @Override
    public LinkedList<Node> visitHaveRp(XqueryParser.HaveRpContext ctx) {
        return visit(ctx.rp());
    }

    /**
     * [ * ]  ->  children(n)
     */
    @Override
    public LinkedList<Node> visitChildRp(XqueryParser.ChildRpContext ctx) {
        nodes =  CustomizedEvaluator.getChildren(nodes);
        return nodes;
    }

    /**
     * [ text() ]  ->  txt()
     */
    @Override
    public LinkedList<Node> visitTextRp(XqueryParser.TextRpContext ctx) {
        nodes = CustomizedEvaluator.getText(nodes);
        return nodes;
    }

    /**
     * [ .. ]  ->  parent(n)
     */
    @Override
    public LinkedList<Node> visitParentRp(XqueryParser.ParentRpContext ctx) {
        nodes = CustomizedEvaluator.getParent(nodes);
        return nodes;
    }

    /**
     * [[rp1, rp2]]R(n) = [[rp1]]R(n), [[rp2]]R(n)
     */
    @Override
    public LinkedList<Node> visitUnionRp(XqueryParser.UnionRpContext ctx) {
        LinkedList<Node> origin = new LinkedList<>(nodes);
        LinkedList<Node> left = visit(ctx.rp(0));

        // restore the origin
        nodes = new LinkedList<>(origin);
        LinkedList<Node> right = visit(ctx.rp(1));

        LinkedList<Node> result = new LinkedList<>(left);
        result.addAll(right);

        nodes = result;
        return nodes;
    }

    /**
     * [ . ]  ->  LinkedList = n
     */
    @Override
    public LinkedList<Node> visitCurrentRp(XqueryParser.CurrentRpContext ctx) {
        return nodes;
    }

    /**
     * [rp[ f ]]]R(n) = < x | x <- [[rp]]R(n), [[ f ]] F (x)
     */
    @Override
    public LinkedList<Node> visitFilterRP(XqueryParser.FilterRPContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        LinkedList<Node> children = visit(ctx.rp());
        for (Node child : children) {
            LinkedList<Node> singleton = new LinkedList<>();
            if (child != null) {
                singleton.add(child);
            }
            nodes = singleton;
            if (!visit(ctx.f()).isEmpty()) {
                result.add(child);
            }
        }
        nodes = result;
        return nodes;
    }

    /**
     * [ rp1 // rp2 ]  ->  unique([rp1/rp2]]R(n), [[rp1/ * //rp2]]R(n)))
     */
    @Override
    public LinkedList<Node> visitAllRP(XqueryParser.AllRPContext ctx) {
        visit(ctx.rp(0));
        LinkedList<Node> descendants = CustomizedEvaluator.getDescendants(nodes);
        nodes.addAll(descendants);
        nodes = CustomizedEvaluator.unique(visit(ctx.rp(1)));
        return nodes;
    }

    /**
     * [ rp1 / rp2 ]  ->  unique(< y | x <- [[rp1]](n), y <- [[rp2]](x) >)
     */
    @Override
    public LinkedList<Node> visitChildrenRP(XqueryParser.ChildrenRPContext ctx) {
        visit(ctx.rp(0));
        nodes = CustomizedEvaluator.unique(visit(ctx.rp(1)));
        return nodes;
    }

    /**
     * [ tagName ]  ->  LinkedList<Node> = <c | c <- children(n) and tag(c) = tagName >
     */
    @Override
    public LinkedList<Node> visitTagRp(XqueryParser.TagRpContext ctx) {
        LinkedList<Node> results = new LinkedList<>();
        String tagName = ctx.getText();
        LinkedList<Node> children = CustomizedEvaluator.getChildren(nodes);
        for (Node child: children){
            if(child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals(tagName))
                results.add(child);
        }
        nodes = results;
        return nodes;
    }

    /**
     * [@attName]]R(n) = attrib(n, attName)
     */
    @Override
    public LinkedList<Node> visitAttributeRp(XqueryParser.AttributeRpContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        String attName = ctx.getText();
        for (Node node: nodes){
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Attr attr = element.getAttributeNode(attName);
                if (attr != null) {
                    result.add(attr);
                }
            }
        }
        return result;
    }

    /**
     *  [rp1 = rp2]]F (n) = [[rp1 eq rp2]]F (n) = ∃x ∈ [[rp1]]R(n) ∃y ∈ [[rp2]]R(n) x eq y
     *  Note: filter should not change the original node
     */
    @Override
    public LinkedList<Node> visitEqFilter(XqueryParser.EqFilterContext ctx) {
        LinkedList<Node> origin = new LinkedList<>(nodes);
        LinkedList<Node> left = visit(ctx.rp(0));
        nodes = new LinkedList<>(origin);
        LinkedList<Node> right = visit(ctx.rp(1));
        nodes = new LinkedList<>(origin);

        // exist equal
        for (Node l : left){
            for (Node r: right){
                if (l.isEqualNode(r)) {
                    return nodes;
                }
            }
        }
        return new LinkedList<>();
    }

    /**
     *  [[(f )]]F (n) = [[f ]]F (n)
     */
    @Override
    public LinkedList<Node> visitHaveFilter(XqueryParser.HaveFilterContext ctx) {
        return visit(ctx.f());
    }

    /**
     *  [[not f ]]F (n) = ¬[[f ]]F (n)
     */
    @Override
    public LinkedList<Node> visitNotFilter(XqueryParser.NotFilterContext ctx) {
        LinkedList<Node> filtered = visit(ctx.f());
        LinkedList<Node> not = new LinkedList<>();

        for (Node node : nodes) {
            if (!filtered.contains(node)) {
                not.add(node);
            }
        }
        return not;
    }

    /**
     *  [rp = StringConstant]]F (n) = ∃x ∈ [[rp]]R(n) x eq StringConstant
     */
    @Override
    public LinkedList<Node> visitStrFilter(XqueryParser.StrFilterContext ctx) {
        LinkedList<Node> result = new LinkedList<>();
        String str = ctx.str().getText().replaceAll("^\"|\"$", "");

        for (Node node : nodes) {
            if (CustomizedEvaluator.matchString(node, str)) {
                result.add(node);
            }
        }
        return result;
    }

    /**
     *  [f1 and f2]]F (n) = [[f1]]F (n) ∧ [[f2]]F (n)
     */
    @Override
    public LinkedList<Node> visitAndFilter(XqueryParser.AndFilterContext ctx) {
        LinkedList<Node> origin = new LinkedList<>(nodes);
        LinkedList<Node> result = new LinkedList<>();
        LinkedList<Node> left = visit(ctx.f(0));
        nodes = origin;
        LinkedList<Node> right = visit(ctx.f(1));
        nodes = origin;
        for (Node node : left) {
            if (right.contains(node)) {
                result.add(node);
            }
        }
        return result;
    }

    /**
     *  [[rp1 == rp2]]F (n) = [[rp1 is rp2]]F (n) = ∃x ∈ [[rp1]]R(n) ∃y ∈ [[rp2]]R(n) x is y
     */
    @Override
    public LinkedList<Node> visitIsFilter(XqueryParser.IsFilterContext ctx) {
        LinkedList<Node> origin = new LinkedList<>(nodes);
        LinkedList<Node> left = visit(ctx.rp(0));
        nodes = new LinkedList<>(origin);
        LinkedList<Node> right = visit(ctx.rp(1));
        nodes = new LinkedList<>(origin);

        // exist same
        for (Node l : left){
            for (Node r: right){
                if (l.isSameNode(r)) {
                    return nodes;
                }
            }
        }
        return new LinkedList<>();
    }

    /**
     *  [[rp]]F (n) = [[rp]]R(n) / =<>
     *  Note: filter functions do not change the nodes
     */
    @Override
    public LinkedList<Node> visitRpFilter(XqueryParser.RpFilterContext ctx) {
        LinkedList<Node> origin = nodes;
        LinkedList<Node> result = new LinkedList<>();
        for (Node node : origin){
            LinkedList<Node> now = new LinkedList<>();
            now.add(node);
            nodes = now;
            if (!visit(ctx.rp()).isEmpty())
                result.add(node);
        }
        nodes = result;
        return result;
    }

    /**
     *  [f1 or f2]]F (n) = [[f1]]F (n) ∨ [[f2]](n)
     */
    @Override
    public LinkedList<Node> visitOrFilter(XqueryParser.OrFilterContext ctx) {
        LinkedList<Node> left = visit(ctx.f(0));
        LinkedList<Node> origin = new LinkedList<>(nodes);
        nodes = origin;
        LinkedList<Node> right = visit(ctx.f(1));


        LinkedList<Node> result = new LinkedList<>(left);
        result.addAll(right);
        LinkedList<Node> unique = CustomizedEvaluator.unique(result);
        nodes = origin;
        return unique;
    }

    /**
     * [doc ( fileName ) ]  ->  root( fileName )
     */
    @Override
    public LinkedList<Node> visitDoc(XqueryParser.DocContext ctx) {
        String fileName = ctx.fileName().getText();
        nodes = CustomizedXqueryEvaluator.evaluate(fileName);
        return nodes;
    }

    // @Override
    // public LinkedList<Node> visitTagName(XqueryParser.TagNameContext ctx) {
    //     return super.visitTagName(ctx);
    // }

    // @Override
    // public LinkedList<Node> visitAttName(XqueryParser.AttNameContext ctx) {
    //     return super.visitAttName(ctx);
    // }

    // @Override
    // public LinkedList<Node> visitFileName(XqueryParser.FileNameContext ctx) {
    //     return super.visitFileName(ctx);
    // }

    // @Override
    // public LinkedList<Node> visitStr(XqueryParser.StrContext ctx) {
    //     return super.visitStr(ctx);
    // }
}
