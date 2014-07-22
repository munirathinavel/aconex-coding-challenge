package com.pktr.gedcom;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.pktr.gedcom.model.ChildNode;
import com.pktr.gedcom.model.ParentNode;
import static com.pktr.gedcom.model.GedcomConstants.*;

public class GedcomTestBase {
    
    

    protected ChildNode createChildNode(boolean hasChildren) {
        ChildNode childNode = createChildNode("name", EMPTY_STRING, "value", "Pradeep Kumar /Thopae/");
        if(hasChildren) {
            ChildNode secondaryChild = createChildNode("sur", "Buck", EMPTY_STRING, EMPTY_STRING);
            childNode.setChildren(Arrays.asList(secondaryChild));
        }
        return childNode;
    }

    protected ChildNode createChildNode(String tagName, String tagValue, String attributeName, String attributeValue) {
        ChildNode childNode = new ChildNode();
        childNode.setTagName(tagName);
        childNode.setTagValue(tagValue);
        childNode.setAttributeName(attributeName);
        childNode.setAttributeValue(attributeValue);
        return childNode;
    }

    protected ParentNode createParentNode(String tagName, String attributeName, String attributeValue) {
        ParentNode parentNode = new ParentNode();
        parentNode.setTagName(tagName);
        parentNode.setAttributeName(attributeName);
        parentNode.setAttributeValue(attributeValue);
        return parentNode;
    }
    
    protected void assertFileContents(String location, Map<ParentNode, List<ChildNode>> xmlMap) {
        // TODO Auto-generated method stub
        
    }
}
