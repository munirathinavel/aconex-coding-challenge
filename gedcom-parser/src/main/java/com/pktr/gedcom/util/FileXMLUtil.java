package com.pktr.gedcom.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * A utility class containing utility static methods for reading and writing to XML files 
 * @author pkumar
 *
 */
public class FileXMLUtil {

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

}
