package Xpath;

import Xpath.CustomizedEvaluator;
import Xquery.CustomizedXqueryEvaluator;
import gen.base.XpathBaseVisitor;
import gen.base.XpathParser;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.LinkedList;

public class CustomizedVisitor extends XpathBaseVisitor<LinkedList<Node>> {


    // Current list of nodes
    private LinkedList<Node> nodes;

    public CustomizedVisitor(){
        this.nodes = new LinkedList<>();
    }

    public LinkedList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(LinkedList<Node> curNodes) {
        this.nodes = curNodes;
    }

    /**
     * [doc ( fileName ) / rp]  ->  [rp] (root( fileName ))
     */
    @Override
    public LinkedList<Node> visitChildrenAp(XpathParser.ChildrenApContext ctx) {
        visit(ctx.doc());
        return visit(ctx.rp());
    }

    /**
     * [doc ( fileName ) // rp]  ->  [. // rp] (root( fileName ))
     */
    @Override
    public LinkedList<Node> visitAllAp(XpathParser.AllApContext ctx) {
        visit(ctx.doc());
        LinkedList<Node> descendants = CustomizedEvaluator.getDescendants(nodes);
        nodes.addAll(descendants);
        return visit(ctx.rp());
    }

    /**
     * [ () ]  ->  children(n)
     */
    @Override
    public LinkedList<Node> visitHaveRp(XpathParser.HaveRpContext ctx) {
        return visit(ctx.rp());
    }

    /**
     * [ * ]  ->  children(n)
     */
    @Override
    public LinkedList<Node> visitChildRp(XpathParser.ChildRpContext ctx) {
        nodes =  CustomizedEvaluator.getChildren(nodes);
        return nodes;
    }

    /**
     * [ text() ]  ->  txt()
     */
    @Override
    public LinkedList<Node> visitTextRp(XpathParser.TextRpContext ctx) {
        nodes = CustomizedEvaluator.getText(nodes);
        return nodes;
    }

    /**
     * [ .. ]  ->  parent(n)
     */
    @Override
    public LinkedList<Node> visitParentRp(XpathParser.ParentRpContext ctx) {
        nodes = CustomizedEvaluator.getParent(nodes);
        return nodes;
    }


    /**
     * [[rp1, rp2]]R(n) = [[rp1]]R(n), [[rp2]]R(n)
     */
    @Override
    public LinkedList<Node> visitUnionRp(XpathParser.UnionRpContext ctx) {
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
    public LinkedList<Node> visitCurrentRp(XpathParser.CurrentRpContext ctx) {
        return nodes;
    }

    /**
     * [rp[ f ]]]R(n) = < x | x <- [[rp]]R(n), [[ f ]] F (x)
     */
    @Override
    public LinkedList<Node> visitFilterRP(XpathParser.FilterRPContext ctx) {
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
    public LinkedList<Node> visitAllRP(XpathParser.AllRPContext ctx) {
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
    public LinkedList<Node> visitChildrenRP(XpathParser.ChildrenRPContext ctx) {
        visit(ctx.rp(0));
        nodes = CustomizedEvaluator.unique(visit(ctx.rp(1)));
        return nodes;
    }

    /**
     * [ tagName ]  ->  LinkedList<Node> = <c | c <- children(n) and tag(c) = tagName >
     */
    @Override
    public LinkedList<Node> visitTagRp(XpathParser.TagRpContext ctx) {
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
    public LinkedList<Node> visitAttributeRp(XpathParser.AttributeRpContext ctx) {
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
    public LinkedList<Node> visitEqFilter(XpathParser.EqFilterContext ctx) {
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
    public LinkedList<Node> visitHaveFilter(XpathParser.HaveFilterContext ctx) {
        return visit(ctx.f());
    }


    /**
     *  [[not f ]]F (n) = ¬[[f ]]F (n)
     */
    @Override
    public LinkedList<Node> visitNotFilter(XpathParser.NotFilterContext ctx) {
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
    public LinkedList<Node> visitStrFilter(XpathParser.StrFilterContext ctx) {
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
    public LinkedList<Node> visitAndFilter(XpathParser.AndFilterContext ctx) {
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
    public LinkedList<Node> visitIsFilter(XpathParser.IsFilterContext ctx) {
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
    public LinkedList<Node> visitRpFilter(XpathParser.RpFilterContext ctx) {
//        LinkedList<Node> nodes = this.nodes;
//        LinkedList<Node> filter = visit(ctx.rp());
//        this.nodes = nodes;
//        return filter;
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
    public LinkedList<Node> visitOrFilter(XpathParser.OrFilterContext ctx) {
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
    public LinkedList<Node> visitDoc(XpathParser.DocContext ctx) {
        String fileName = ctx.fileName().getText();
        nodes = CustomizedXqueryEvaluator.evaluate(fileName);
        return nodes;
    }

//    @Override
//    public LinkedList<Node> visitStr(XpathParser.StrContext ctx) {
//        return visitChildren(ctx);
//    }
//    @Override
//    public LinkedList<Node> visitTagName(XpathParser.TagNameContext ctx) {
//        return visitChildren(ctx);
//    }
//
//    @Override
//    public LinkedList<Node> visitAttName(XpathParser.AttNameContext ctx) {
//        return visitChildren(ctx);
//    }
//
//    @Override
//    public LinkedList<Node> visitFileName(XpathParser.FileNameContext ctx) {
//        return visitChildren(ctx);
//    }
}
