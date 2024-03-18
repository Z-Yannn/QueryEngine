package Execution;

import XML.XMLUtil;
import Xpath.CustomizedVisitor;
import Xquery.CustomizedXqueryEvaluator;
import Xquery.CustomizedXqueryVisitor;
import gen.base.*;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.util.LinkedList;

public class XqueryEXE {
    public static void main(String[] args) {
        try {
            System.out.println("Starting Query.");
//            if (args.length != 2) {
//                System.out.println("Usage: java Main <input_XQuery_path> <output_file_path>");
//                return;
//            }
////             input XQuery
            String inputFilePath = args[0];
            String outputFilePath = args[1];
//            String inputFilePath = "input/Xquery.txt";
//            String outputFilePath = "output/Xquery.xml";
            XqueryParser parser = parseXquery(inputFilePath);

            CustomizedXqueryVisitor visitor = new CustomizedXqueryVisitor();
            LinkedList<Node> results = visitor.visit(parser.xq());

            XMLUtil xmlUtil = new XMLUtil();
            LinkedList<Node> output = new LinkedList<>();
            // for Xquery
//            if (results.size() == 1) {
//                System.out.println(results.get(0).getChildNodes().getLength());
//                output = results;
//            }
//            // for Xpath
//            else{
//                System.out.println(results.size());
//                output.add(xmlUtil.createNode(visitor.getOutputDoc(), results));
//            }
            output.add(xmlUtil.createNode(visitor.getOutputDoc(), results));
            xmlUtil.saveXMLFile(visitor.getOutputDoc(), output, outputFilePath);

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Parses an Xquery from a file and creates a parser.
     */
    private static XqueryParser parseXquery(String filePath) throws IOException {
        ANTLRFileStream input = new ANTLRFileStream(filePath);
        XqueryLexer lexer = new XqueryLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        XqueryParser parser = new XqueryParser(tokens);
        return parser;
    }
}
