package com.pktr.gedcom.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.pktr.gedcom.model.ChildNode;
import com.pktr.gedcom.model.GedcomConstants;
import com.pktr.gedcom.model.ParentNode;

/**
 * An utility class containing static methods for file, XML and String based re-usable operations
 * 
 * @author pkumar
 */
public class CommonUtil {

    /**
     * Converts the file contents into {@link java.lang.String}
     * 
     * @param fileToRead - File to be read
     * @return
     */
    public static String readAsString(File fileToRead) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileToRead));

            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return builder.toString();
    }

    /**
     * Reads the data from input {@link org.w3c.dom.Document} and writes to the output {@link java.io.File}
     * 
     * @param document - The {@link org.w3c.dom.Document} containing data
     * @param outputFile - The {@link java.io.File} to which the data will be written
     */
    public static void writeDocumentToFile(Document document, File outputFile) {
        if (document == null || outputFile == null) {
            throw new IllegalArgumentException("Incorrect usage, Document or File to be written is invalid");
        }
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(outputFile);
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an empty {@link org.w3c.dom.Document} using {@link javax.xml.parsers.DocumentBuilderFactory}
     * 
     * @return The {@link org.w3c.dom.Document}
     * @throws ParserConfigurationException
     */
    public static Document createXMLDocument() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = docFactory.newDocumentBuilder();
        Document xmlDocument = builder.newDocument();
        return xmlDocument;
    }

    public static boolean isEmpty(String data) {
        return data == null || data.length() == 0;
    }

    public static boolean isNotEmpty(Set<Entry<ParentNode, List<ChildNode>>> set) {
        return set != null && set.size() > 0;
    }

    public static boolean isValidTag(String name) {
        return !isEmpty(name) && name.matches(GedcomConstants.TAG_PATTERN);
    }

}
