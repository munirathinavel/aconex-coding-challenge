package com.pktr.gedcom;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class GedcomCLITest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GedcomCLITest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(GedcomCLITest.class);
    }

    /**
     * Rigorous Test :-)
     */
    public void testApp() {
        try {
            GedcomCLI.main(new String[] {});
        } catch (ParserConfigurationException e) {
            fail("Test failed as parsing resulted in exception");
        }
    }
}
