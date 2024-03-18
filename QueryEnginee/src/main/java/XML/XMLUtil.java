package XML;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.LinkedList;

// used to read and write XML
public class XMLUtil {
    private LinkedList<Node> nodes;
    public LinkedList<Node> getNodes() {
        return nodes;
    }

    // parse XML
    public void parseXMLFile(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true); // ignore white space
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));
            document.getDocumentElement().normalize(); // normalizes the XML structure

            if (this.nodes == null) {
                this.nodes = new LinkedList<>();
            } else {
                this.nodes.clear(); // Clear existing nodes if reusing the parser
            }
            this.nodes.add(document);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // constructs a new Node based on the node list
    public Node createNode(Document input, LinkedList<Node> list){
        Node root = input.createElement("result");
        for (Node node : list) {
            if (node != null) {
                Node importedNode = input.importNode(node, true);
                root.appendChild(importedNode);
            }
        }
        return root;
    }

    // construct a new Node based on the tage name
    public Node createNode(Document doc, String tag, LinkedList<Node> list){
        Element elem = doc.createElement(tag);
        for (Node node : list) {
            if (node != null){
                Node importedNode = doc.importNode(node, true);
                if (importedNode.getNodeType() == Node.ATTRIBUTE_NODE) {
                    elem.setAttributeNode((Attr) importedNode);
                } else {
                    elem.appendChild(importedNode);
                }
            }

        }
        return elem;
    }

    // save the xml document to file
    public void saveXMLFile(Document document, Node node, String filePath) {
        // check if the list is empty to avoid IndexOutOfBoundsException.
        if (node != null) {
            document.appendChild(document.importNode(node, true));
            try {
                Transformer transformer = createTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new File(filePath));
                transformer.transform(source, result);
                System.out.println("XML file saved to: " + filePath);
            } catch (TransformerException e) {
                System.out.println("Error saving XML file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("No node to save to XML file.");
        }
    }

    public void saveXMLFile(Document document, LinkedList<Node> nodes, String filePath) {
        document.appendChild(nodes.get(0));
        try {
            Transformer transformer = createTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
            System.out.println("XML file saved to: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Transformer createTransformer() throws TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        return transformer;
    }
}


//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import java.io.File;
//
//
//public class XML.XMLUtil {
//    private Node root;
//    public void parseXMLFile(String filePath) {
//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(new File(filePath));
//            document.getDocumentElement().normalize(); // normalizes the XML structure
//            this.root = document.getDocumentElement();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Node getRoot() {
//        return root;
//    }
//
//    // test the xmlparser
//    public static void printNode(Node node, String indent) {
//        System.out.println(indent + "Node Name: " + node.getNodeName());
//        System.out.println(indent + "Node Value: " + node.getNodeValue());
//
//        NodeList children = node.getChildNodes();
//        for (int i = 0; i < children.getLength(); i++) {
//            printNode(children.item(i), indent + "  ");
//        }
//    }
//    public static void main(String[] args) {
//                    XML.XMLUtil parser = new XML.XMLUtil();
//                    parser.parseXMLFile("src/main/resources/j_caesar.xml");
//                    Node root = parser.getRoot();
//                    if (root != null) {
//                        parser.printNode(root, "");
//                    }
//                }
//            }