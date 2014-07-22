package com.pktr.gedcom.process;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.pktr.gedcom.model.ChildNode;
import com.pktr.gedcom.model.GedcomConstants;
import com.pktr.gedcom.model.ParentNode;
import com.pktr.gedcom.util.FileXMLUtil;
import static com.pktr.gedcom.util.FileXMLUtil.*;

/**
 * This class is the heart of gedcom-parser, takes the input TXT file and convert to XML file 
 * @author pkumar
 *
 */
public class XMLCreator {


    private File inputFile;

    private File outputFile;
    
    private NodeParser parser;
    

    public XMLCreator(String inputFileName, String outputFileName, NodeParser parser) {
        this.inputFile = new File(inputFileName);
        this.outputFile = new File(outputFileName);
        this.parser = parser;
    }

    /** Creates DOM document and populates with data from input and writes to the output XML file
     * @return Absolute path of the output XML generated
     * @throws ParserConfigurationException
     */
    public String generateXMLDocument() throws ParserConfigurationException {
        Document xmlDocument = createXMLDocument();
        populateDocumentWithContent(xmlDocument, inputFile);
        FileXMLUtil.writeDocumentToFile(xmlDocument, outputFile);
        return outputFile.getAbsolutePath();
    }

    private Document createXMLDocument() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = docFactory.newDocumentBuilder();
        Document xmlDocument = builder.newDocument();
        return xmlDocument;
    }

    private void populateDocumentWithContent(Document xmlDocument, File fileToRead) {
        String inputData = FileXMLUtil.readAsString(fileToRead);
        Map<ParentNode, List<ChildNode>> xmlMap = parser.toNodeElements(inputData);
        populateDocument(xmlDocument, xmlMap);
        
    }

    private void populateDocument(Document xmlDocument, Map<ParentNode, List<ChildNode>> xmlMap) {
        if(xmlMap != null && isNotEmpty(xmlMap.entrySet()) && xmlDocument != null) {
            Element rootElement = xmlDocument.createElement(GedcomConstants.ROOT);
            xmlDocument.appendChild(rootElement);
            
            for (Map.Entry<ParentNode, List<ChildNode>> entry : xmlMap.entrySet())
            {
                ParentNode parent = entry.getKey();
                Element parentElement = xmlDocument.createElement(parent.getTagName());
                parentElement.setAttribute(parent.getAttributeName(), parent.getAttributeValue());
                for (ChildNode childNode  : entry.getValue()) {
                    System.out.println(childNode);
                    //addChildren(parentElement, childNode, xmlDocument);
                }
                rootElement.appendChild(parentElement);
            }
        }
        
    }

    private void addChildren(Element parentElement, ChildNode child, Document xmlDocument) {
        Element childElement = xmlDocument.createElement(child.getTagName());
        childElement.setTextContent(child.getTagValue());
        final String attributeName = isEmpty(child.getAttributeName()) ? GedcomConstants.EMPTY_STRING : child.getAttributeName();
        final String attributeValue = isEmpty(child.getAttributeValue()) ? GedcomConstants.EMPTY_STRING : child.getAttributeValue();
        //childElement.setAttribute(attributeName, attributeValue);
        if(child.hasChildren()) {
            for (ChildNode secondLevelChild : child.getChildren()) {
                addChildren(childElement, secondLevelChild, xmlDocument);
            }
        }
        parentElement.appendChild(childElement);
    }

    private boolean isNotEmpty(Set<Entry<ParentNode, List<ChildNode>>> set) {
        return set != null && set.size() > 0;
    }

    

}
