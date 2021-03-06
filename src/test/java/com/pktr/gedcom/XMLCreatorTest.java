package com.pktr.gedcom;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.pktr.gedcom.model.ChildNode;
import com.pktr.gedcom.model.ParentNode;
import com.pktr.gedcom.process.NodeParser;
import com.pktr.gedcom.process.XMLCreator;

@RunWith(MockitoJUnitRunner.class)
public class XMLCreatorTest extends GedcomTestBase {

   

    @Mock
    NodeParser parser;
    
    XMLCreator xmlCreator;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        xmlCreator = new XMLCreator(GedcomCLI.GEDCOM_INPUT_FILE, GedcomCLI.GEDCOM_OUTPUT_FILE, parser);
    }
    
    @Test
    public void testGenerateDocumentWithOneChild() throws ParserConfigurationException {
        Map<ParentNode, List<ChildNode>> oneChildMap = createMapWithSingleChild();
        when(parser.toNodeElements(anyString())).thenReturn(oneChildMap);
        String location = xmlCreator.generateXMLDocument();
        assertThat(location, containsString(GedcomCLI.GEDCOM_OUTPUT_FILE));
        assertFileContents(location, oneChildMap);
    }
    
    @Test
    public void testGenerateDocumentWithSecondaryChild() throws ParserConfigurationException {
        Map<ParentNode, List<ChildNode>> oneChildMap = createMapWithSingleChild();
        when(parser.toNodeElements(anyString())).thenReturn(oneChildMap);
        String location = xmlCreator.generateXMLDocument();
        assertThat(location, containsString(GedcomCLI.GEDCOM_OUTPUT_FILE));
        assertFileContents(location, oneChildMap);
    }


    private Map<ParentNode, List<ChildNode>> createMapWithSingleChild() {
        ParentNode parentNode = createParentNode("INDI", "id", "@I1@");
        ChildNode childNode = createChildNode(false);
        Map<ParentNode, List<ChildNode>> dummyMap = new HashMap<ParentNode, List<ChildNode>>();
        dummyMap.put(parentNode, Arrays.asList(childNode));
        return dummyMap;
    }
    

    
}
