package Xpath;

import XML.XMLUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashSet;
import java.util.LinkedList;

// Functions to be used by Xpath.CustomizedVisitor for a Xpath expression
public class CustomizedEvaluator {

    private static XMLUtil xmlUtil;

    static {
        xmlUtil = new XMLUtil();
    }

    /**
     * read xml and load the content to nodes using XMLParser
     */
    public static LinkedList<Node> evaluate(String filePath) {
        LinkedList<Node> result = new LinkedList<>();
        filePath = filePath.substring(1,filePath.length()-1);
        try {
            xmlUtil.parseXMLFile("src/main/resources/"+filePath);
            LinkedList<Node> root = xmlUtil.getNodes();
            if (root != null) {
                result.addAll(root);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
        return result;
    }


    /**
     * return the children of LinkedList<Node> (just the next level)
     */
    public static LinkedList<Node> getChildren(LinkedList<Node> nodes){
        LinkedList<Node> result = new LinkedList<>();
        for (Node node: nodes){
            for (int i = 0; i < node.getChildNodes().getLength(); i++){
                Node cur = node.getChildNodes().item(i);
                result.add(cur);
            }
        }
        return result;
    }


    /**
     * Recursively collects all descendants of nodes in the provided list using a depth-first search approach.
     */
    public static LinkedList<Node> getDescendants(LinkedList<Node> nodes) {
        LinkedList<Node> result = new LinkedList<>();
        for (Node node : nodes) {
            dfs(node, result);
        }
        return result;
    }

    /**
     * perform depth-first search traversal on a node and add all its descendants to the result list.
     * Note: This method does not add the starting node itself to the list, only its descendants.
     */
    public static void dfs(Node node, LinkedList<Node> nodes){
        if (node == null) return;
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++){
            Node child = children.item(i);
            nodes.add(child);
            dfs(child, nodes);
        }
    }

    /**
     * return the parent of the node
     * Note: attribute node's parent is different from others
     */
    public static LinkedList<Node> getParent(LinkedList<Node> nodes){
        LinkedList<Node> result = new LinkedList<>();
        for (Node node: nodes){
            Node parent;
            if (node.getNodeType() == Node.ATTRIBUTE_NODE){
                parent = node.getOwnerDocument();
            }
            else{
                parent = node.getParentNode();
            }

            if (!result.contains(parent)){
                result.add(parent);
            }

        }
        return result;
    }

    /**
     * return the text node
     */
    public static LinkedList<Node> getText(LinkedList<Node> nodes){
        LinkedList<Node> result = new LinkedList<>();
        LinkedList<Node> children = getChildren(nodes);
        for (Node child: children){
            if ((child.getNodeType() == Node.TEXT_NODE) && (child.getTextContent() != null) && (!child.getTextContent().trim().isEmpty())){
                result.add(child);
            }
        }
        return result;
    }

    public static LinkedList<Node> unique(LinkedList<Node> nodes){
        HashSet<Node> tempSet = new HashSet<>();
        LinkedList<Node> result = new LinkedList<>();

        for (Node node : nodes) {
            if (tempSet.add(node)) {
                result.add(node);
            }
        }

        return result;
    }

    /**
     * recursively checks a node and its descendants to find any text node with content equal to stringConstant.
     */
    public static boolean matchString(Node node, String str){
        // If the node is a text node, check if its text matches the string constant
        if (node.getNodeType() == Node.TEXT_NODE && str.equals(node.getTextContent().trim())) {
            return true;
        }

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (matchString(children.item(i), str)) {
                return true;
            }
        }
        return false;
    }
}
