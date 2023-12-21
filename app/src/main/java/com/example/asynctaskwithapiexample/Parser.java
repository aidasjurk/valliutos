package com.example.asynctaskwithapiexample;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<String> parseXML(String xmlData) {
        List<String> currencyList = new ArrayList<>();
        try {
            // Create a new instance of DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Set it to be aware of namespaces
            factory.setNamespaceAware(true);

            // Disable DTD validation
            factory.setValidating(false);

            // Ignore the external DTD completely
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            // Create a new DocumentBuilder instance
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML data
            Document document = builder.parse(new InputSource(new StringReader(xmlData)));

            // Normalize the document
            document.getDocumentElement().normalize();

            // Get all "Cube" elements
            NodeList nList = document.getElementsByTagNameNS("*", "Cube");
            for (int i = 0; i < nList.getLength(); i++) {
                Element element = (Element) nList.item(i);

                // Check if the "Cube" element has "currency" and "rate" attributes
                if (element.hasAttribute("currency") && element.hasAttribute("rate")) {
                    String currency = element.getAttribute("currency");
                    String rate = element.getAttribute("rate");
                    currencyList.add(currency + " - " + rate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
        return currencyList;
    }
}
