package Execution;

import XML.XMLUtil;
import gen.base.XpathLexer;
import gen.base.XpathParser;
import org.antlr.v4.runtime.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.util.LinkedList;
import Xpath.*;
public class XpathEXE {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Starting Query.");
//            if (args.length != 2) {
//                System.out.println("Usage: java Main <input_XQuery_path> <output_file_path>");
//                return;
//            }
////             input XQuery
//            String inputFilePath = args[0];
//            String outputFilePath = args[1];
            String inputFilePath = "input/Xquery.txt";
            String outputFilePath = "output/XqueryOptimize.xml";
            XpathParser parser = parseXPathQuery(inputFilePath);

            CustomizedVisitor visitor = new CustomizedVisitor();
            LinkedList<Node> results = visitor.visit(parser.ap());

            System.out.println(results.size());

            // Build new output documents
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document outputDoc = builder.newDocument();

            XMLUtil xmlUtil = new XMLUtil();
            Node output = xmlUtil.createNode(outputDoc, results);
            xmlUtil.saveXMLFile(outputDoc, output, outputFilePath);


        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error executing the XQuery: " + e.getMessage());
        }
    }

    /**
     * Parses an XPath query from a file and creates a parser.
     */
    private static XpathParser parseXPathQuery(String filePath) throws IOException {
        ANTLRFileStream input = new ANTLRFileStream(filePath);
        XpathLexer lexer = new XpathLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        XpathParser parser = new XpathParser(tokens);
        return parser;
    }


}
