package Xquery;

import Xpath.CustomizedEvaluator;
import Xpath.CustomizedVisitor;
import gen.base.XpathLexer;
import gen.base.XpathParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.w3c.dom.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class CustomizedXqueryEvaluator extends CustomizedEvaluator {
    public static LinkedList<Node> isSingleton(Node node) {
        LinkedList<Node> result = new LinkedList<>();
        if (node != null) {
            result.add(node);
        }
        return result;
    }

    private static ParseTree parseString(String inputString, Function<XpathParser, ParseTree> parseFunction) {
        try {
            ANTLRInputStream input = new ANTLRInputStream(inputString);
            XpathLexer lexer = new XpathLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            XpathParser parser = new XpathParser(tokens);
            parser.removeErrorListeners();
            return parseFunction.apply(parser);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Consider rethrowing as a runtime exception or a specific checked exception.
            return null;
        }
    }

    public static LinkedList<Node> evaluateAP(String ap) {
        LinkedList<Node> result = new LinkedList<>();
        ParseTree tree = parseString(ap, XpathParser::ap); // Utilize method reference for brevity
        if (tree != null) { // Check if parsing was successful
            CustomizedVisitor visitor = new CustomizedVisitor();
            result = visitor.visit(tree);
        }
        return result;
    }

    public static LinkedList<Node> evaluateRP(LinkedList<Node> current, String rp) {
        LinkedList<Node> result = new LinkedList<>();
        ParseTree tree = parseString(rp, XpathParser::rp); // Utilize method reference for brevity
        if (tree != null) { // Check if parsing was successful
            CustomizedVisitor visitor = new CustomizedVisitor();
            visitor.setNodes(current); // Set the current context nodes
            result = visitor.visit(tree);
        }
        return result;
    }


    public static boolean isEqual(LinkedList<Node> left, LinkedList<Node> right) {
        for (Node l : left) {
            for (Node r : right) {
                if (l.isEqualNode(r)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSame(LinkedList<Node> left, LinkedList<Node> right) {
        for (Node l : left) {
            for (Node r : right) {
                if (l.isSameNode(r)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String nodesToString(NodeList nodes, boolean includeWhitespace) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case Node.ELEMENT_NODE:
                    stringBuilder.append("<").append(node.getNodeName()).append(">");
                    stringBuilder.append(nodesToString(node.getChildNodes(), includeWhitespace));
                    stringBuilder.append("</").append(node.getNodeName()).append(">");
                    break;
                case Node.ATTRIBUTE_NODE:
                    stringBuilder.append(" ").append(node.getNodeName()).append("=\"")
                            .append(node.getNodeValue()).append("\"");
                    break;
                case Node.TEXT_NODE:
                    if (includeWhitespace || !node.getTextContent().trim().isEmpty()) {
                        stringBuilder.append(node.getTextContent().trim());
                    }
                    break;
                case Node.CDATA_SECTION_NODE:
                    stringBuilder.append("<![CDATA[").append(node.getNodeValue()).append("]]>");
                    break;
                case Node.COMMENT_NODE:
                    stringBuilder.append("<!--").append(node.getTextContent().trim()).append("-->");
                    break;
                case Node.PROCESSING_INSTRUCTION_NODE:
                    ProcessingInstruction pi = (ProcessingInstruction) node;
                    stringBuilder.append("<?").append(pi.getTarget()).append(" ")
                            .append(pi.getData()).append("?>");
                    break;
                // Add cases for other node types if needed
                default:
                    // Ignore or handle other types of nodes
                    break;
            }
        }
        return stringBuilder.toString();
    }
}
