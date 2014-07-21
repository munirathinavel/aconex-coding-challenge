package com.pktr.gedcom;

import javax.xml.parsers.ParserConfigurationException;

import com.pktr.gedcom.process.NodeParser;
import com.pktr.gedcom.process.XMLCreator;

/**
 * Hello world!
 *
 */
public class GedcomCLI 
{
    public static final String GEDCOM_INPUT_FILE = "GEDCOM Parser Challenge sample data.txt";
    public static final String GEDCOM_OUTPUT_FILE = "GEDCOM Parser Challenge sample data.xml";
    

    public static void main( String[] args )
    {
    	try {
            XMLCreator xmlCreator = new XMLCreator(GEDCOM_INPUT_FILE, GEDCOM_OUTPUT_FILE, new NodeParser());
            System.out.println(String.format("Input TXT File %s parsed and the output XML is created %s",
                GEDCOM_INPUT_FILE, xmlCreator.generateXMLDocument()));
        } catch (ParserConfigurationException e) {
            System.out.println("Exception occured when generating XML");
            e.printStackTrace();
        }
    }
}
