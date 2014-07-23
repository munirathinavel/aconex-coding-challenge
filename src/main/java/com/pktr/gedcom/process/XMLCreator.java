package com.pktr.gedcom.process;

import static com.pktr.gedcom.util.CommonUtil.createXMLDocument;
import static com.pktr.gedcom.util.CommonUtil.isNotEmpty;
import static com.pktr.gedcom.util.CommonUtil.isValidTag;
import static com.pktr.gedcom.util.CommonUtil.readAsString;
import static com.pktr.gedcom.util.CommonUtil.writeDocumentToFile;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.pktr.gedcom.model.ChildNode;
import com.pktr.gedcom.model.GedcomConstants;
import com.pktr.gedcom.model.ParentNode;

/**
 * This class is the heart of gedcom-parser, takes the input TXT file and convert to XML file
 * 
 * @author pkumar
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

    /**
     * Creates DOM document and populates with data from input and writes to the output XML file
     * 
     * @return Absolute path of the output XML generated
     * @throws ParserConfigurationException
     */
    public String generateXMLDocument() throws ParserConfigurationException {
        Document xmlDocument = createXMLDocument();
        populateDocumentWithContent(xmlDocument, inputFile);
        writeDocumentToFile(xmlDocument, outputFile);
        return outputFile.getAbsolutePath();
    }

    private void populateDocumentWithContent(Document xmlDocument, File fileToRead) {
        String inputData = readAsString(fileToRead);
        Map<ParentNode, List<ChildNode>> xmlMap = parser.toNodeElements(inputData);
        populateDocument(xmlDocument, xmlMap);

    }

    private void populateDocument(Document xmlDocument, Map<ParentNode, List<ChildNode>> xmlMap) {
        if (xmlMap != null && isNotEmpty(xmlMap.entrySet()) && xmlDocument != null) {
            Element rootElement = xmlDocument.createElement(GedcomConstants.ROOT);
            xmlDocument.appendChild(rootElement);

            for (Map.Entry<ParentNode, List<ChildNode>> entry : xmlMap.entrySet()) {
                ParentNode parent = entry.getKey();
                if (isValidTag(parent.getTagName())) {
                    Element parentElement = xmlDocument.createElement(parent.getTagName());
                    parentElement.setAttribute(parent.getAttributeName(), parent.getAttributeValue());
                    for (ChildNode childNode : entry.getValue()) {
                        addChildren(parentElement, childNode, xmlDocument);
                    }
                    rootElement.appendChild(parentElement);
                }
            }
        }

    }

    private void addChildren(Element parentElement, ChildNode child, Document xmlDocument) {
        if (isValidTag(child.getTagName())) {
            Element childElement = xmlDocument.createElement(child.getTagName());
            childElement.setTextContent(child.getTagValue());
            if (isValidTag(child.getAttributeName())) {
                childElement.setAttribute(child.getAttributeName(), child.getAttributeValue());
            }
            if (child.hasChildren()) {
                for (ChildNode secondLevelChild : child.getChildren()) {
                    addChildren(childElement, secondLevelChild, xmlDocument);
                }
            }
            parentElement.appendChild(childElement);
        }
    }

}
