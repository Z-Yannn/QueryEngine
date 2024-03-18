package Execution;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import XML.XMLUtil;
import Xpath.CustomizedVisitor;
import Xquery.*;
import Xquery.CustomizedXqueryVisitor;
import gen.base.*;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.io.FileInputStream;
import java.util.List;

/**
 * XQueryOptimizerEngine - Utility functions to deal with XQueryOptimizer
 */
public class RewriterTest {
    public static void main(String[] args) {
        String optimizedQuery = "";
        try {
            String inputFilePath = "input/Xquery.txt";
            XqueryParser parser = parseXquery(inputFilePath);
            XqueryOptimizer optimizer = new XqueryOptimizer();
            // rewrite Xquery
            optimizedQuery = optimizer.visit(parser.xq());
            System.out.println("Optimized Query:\n" + optimizedQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (FileWriter writer = new FileWriter("output/RewrittenQuery.txt")) {
            writer.write(optimizedQuery);
            System.out.println("Storing rewritten queries in RewrittenQuery.txt");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static XqueryParser parseXquery(String filePath) throws IOException {
        ANTLRFileStream input = new ANTLRFileStream(filePath);
        XqueryLexer lexer = new XqueryLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        XqueryParser parser = new XqueryParser(tokens);
        return parser;
    }
}