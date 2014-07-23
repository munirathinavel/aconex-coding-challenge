package com.pktr.gedcom;

import javax.xml.parsers.ParserConfigurationException;

import com.pktr.gedcom.process.NodeParser;
import com.pktr.gedcom.process.XMLCreator;

/**
 * Hello world!
 */
public class GedcomCLI {

    public static final String GEDCOM_INPUT_FILE = "GEDCOM Parser Challenge sample data.txt";

    public static final String GEDCOM_OUTPUT_FILE = "GEDCOM Parser Challenge sample data.xml";

    public static void main(String[] args) throws ParserConfigurationException {
        XMLCreator xmlCreator = null;
        NodeParser nodeParser = new NodeParser();
        if (args.length > 2 || args.length < 2) {
            xmlCreator = new XMLCreator(GEDCOM_INPUT_FILE, GEDCOM_OUTPUT_FILE, nodeParser);
            System.out.println(String.format("Input TXT File %s parsed and the output XML is created %s",
                GEDCOM_INPUT_FILE, xmlCreator.generateXMLDocument()));
        } else {
            xmlCreator = new XMLCreator(args[0], args[1], nodeParser);
            System.out.println(String.format("Input TXT File %s parsed and the output XML is created %s",
                args[0], xmlCreator.generateXMLDocument()));
        }
    }
}
