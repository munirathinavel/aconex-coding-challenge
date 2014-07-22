package com.pktr.gedcom;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.pktr.gedcom.model.ChildNode;
import com.pktr.gedcom.model.GedcomConstants;
import com.pktr.gedcom.model.ParentNode;
import com.pktr.gedcom.process.NodeParser;

public class NodeParserTest extends GedcomTestBase {

    private NodeParser nodeParser;

    private ParentNode parentNode;

    private ChildNode primaryChildwithTags;
    
    private ChildNode primaryChildWithAttributes;

    private ChildNode secondaryChild;

    @Before
    public void setUp() {
        nodeParser = new NodeParser();

        parentNode = createParentNode("INDI", GedcomConstants.PARENT_ATTRIBUTE_ID, "@I0001@");

        primaryChildwithTags = createChildNode("NAME", "James", null, null);

        primaryChildWithAttributes = createChildNode(null, null, "value", "James");
        
        secondaryChild = createChildNode("SUR", "Junior", null, null);
        
        primaryChildWithAttributes.setChildren(Arrays.asList(secondaryChild));
    }

    @Test
    public void testNodeParserWithNoChild() {
        Map<ParentNode, List<ChildNode>> returnMap = nodeParser.toNodeElements("0 @I0001@ INDI");
        assertNotNull(returnMap);
        for (Map.Entry<ParentNode, List<ChildNode>> entry : returnMap.entrySet()) {
            Assert.assertEquals(parentNode, entry.getKey());
            Assert.assertEquals(entry.getValue(), (Collections.<ChildNode> emptyList()));
        }
    }

    @Test
    public void testNodeParserWithOneChild() {
        Map<ParentNode, List<ChildNode>> returnMap = nodeParser.toNodeElements("0 @I0001@ INDI 1 NAME James");
        assertNotNull(returnMap);
        for (Map.Entry<ParentNode, List<ChildNode>> entry : returnMap.entrySet()) {
            Assert.assertEquals(parentNode, entry.getKey());
            List<ChildNode> childNodes = entry.getValue();
            assertThat(childNodes.size(), is(1));
            ChildNode childNode = childNodes.iterator().next();
            assertFalse(childNode.hasChildren());
            Assert.assertEquals(primaryChildwithTags, childNode);
        }
    }

    @Test
    public void testNodeParserWithSecondaryChild() {
        Map<ParentNode, List<ChildNode>> returnMap =
            nodeParser.toNodeElements("0 @I0001@ INDI 1 NAME James 2 SUR Junior");
        assertNotNull(returnMap);
        for (Map.Entry<ParentNode, List<ChildNode>> entry : returnMap.entrySet()) {
            Assert.assertEquals(parentNode, entry.getKey());
            List<ChildNode> childNodes = entry.getValue();
            assertThat(childNodes.size(), is(1));
            ChildNode childNode = childNodes.iterator().next();
            assertTrue(childNode.hasChildren());
            Assert.assertEquals(primaryChildWithAttributes, childNode);
            List<ChildNode> childrenNodes = childNode.getChildren();
            assertThat(childrenNodes.size(), is(1));
            ChildNode secondaryChildNode = childrenNodes.iterator().next();
            Assert.assertEquals(secondaryChild, secondaryChildNode);
        }
    }

}
