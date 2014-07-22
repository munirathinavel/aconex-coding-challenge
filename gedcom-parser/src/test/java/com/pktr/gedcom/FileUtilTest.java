package com.pktr.gedcom;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.pktr.gedcom.model.GedcomConstants;
import com.pktr.gedcom.util.FileXMLUtil;

public class FileUtilTest {

    private static final String TEST_CONTENT = "Test content";
    private static final String FIRST_CHILD = "firstChild";
    private Document document;
    private static final ThreadLocal<SimpleDateFormat> formatter = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue()
        {
            return new SimpleDateFormat("yyyyMMdd HHmm");
        }
    };

    @Before
    public void setUp() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        document = docBuilder.newDocument();
        Element rootElement = document.createElement(GedcomConstants.ROOT);
        document.appendChild(rootElement);

        // staff elements
        Element firstChild = document.createElement(FIRST_CHILD);
        rootElement.appendChild(firstChild);
        firstChild.setTextContent(TEST_CONTENT);
    }

    @Test
    public void testReadContent() {
        File f = new File(GedcomCLI.GEDCOM_INPUT_FILE);
        String data = FileXMLUtil.readAsString(f);
        assertNotNull("Failed to read content from file", data);
    }

    @Test
    public void testWriteToFile() throws ParserConfigurationException, SAXException, IOException {
        String formattedTime = formatter.get().format(new Date(System.currentTimeMillis()));
        StringBuilder builder = new StringBuilder();
        builder.append(formattedTime);
        builder.append("-");
        builder.append(GedcomCLI.GEDCOM_OUTPUT_FILE);
        File outputFile = new File(builder.toString());
        FileXMLUtil.writeDocumentToFile(document, outputFile);
        assertOutputFile(outputFile);
    }

    private void assertOutputFile(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document doc = dBuilder.parse(file);
        if (doc.hasChildNodes()) {
            NodeList nodeList = doc.getChildNodes();

            for (int count = 0; count < nodeList.getLength(); count++) {
                Node tempNode = nodeList.item(count);
                // make sure it's element node.
                if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                    // get node name and value
                    assertThat(tempNode.getNodeName(), is(equalTo(GedcomConstants.ROOT)));
                    assertThat(tempNode.getTextContent(), is(equalTo(TEST_CONTENT)));
                    assertThat(tempNode.getFirstChild().getNodeName(), is(FIRST_CHILD));
                }

            }
        }
    }
}
