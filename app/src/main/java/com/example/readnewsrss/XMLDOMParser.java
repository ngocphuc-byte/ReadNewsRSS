package com.example.readnewsrss;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLDOMParser {
    public Document getDocument(String xml) throws IOException, SAXException, ParserConfigurationException {
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = factory.newDocumentBuilder();
        InputSource IS = new InputSource();
        IS.setCharacterStream(new StringReader(xml));
        IS.setEncoding("UTF-8");
        document = db.parse(IS);
        return document;
    }
    public String getValue(Element item, String name){
        NodeList nodeList = item.getElementsByTagName(name);
        return this.getTextNodeValue(nodeList.item(0));
    }

    private String getTextNodeValue(Node item) {
        Node child;
        if(item!=null){
            if(item.hasChildNodes()){
                for(child = item.getFirstChild(); child!=null;child=child.getNextSibling()){
                    if(child.getNodeType()==Node.TEXT_NODE){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }



}
