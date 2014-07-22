package com.pktr.gedcom.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pktr.gedcom.model.ChildNode;
import com.pktr.gedcom.model.ParentNode;

import static com.pktr.gedcom.model.GedcomConstants.*;
import static com.pktr.gedcom.util.FileXMLUtil.*;

/**
 * Class for converting the TXT file contents to a Map representation
 * 
 * @author pkumar
 */
public class NodeParser {


    /**
     * @param fileData - TXT file contents in the form of String
     * @return Map containing XML data in the form of Parent-Child, where Child might have children
     */
    public Map<ParentNode, List<ChildNode>> toNodeElements(String fileData) {
        Map<ParentNode, List<ChildNode>> parentChildMap = new HashMap<ParentNode, List<ChildNode>>();
        if (isEmpty(fileData)) {
            return parentChildMap;
        }
        String[] parents = fileData.split(PARENT_DELIMITER);
        if (parents.length > 0) {
            for (String parent : parents) {
                if (!isEmpty(parent)) {
                    ParentNode parentNode = createParentNode(parent);
                    if (parent.indexOf(PRIMARY_CHILD_DELIMITER) != -1) {
                        List<ChildNode> childNodeList = createChildNode(parent);
                        parentChildMap.put(parentNode, childNodeList);
                    } else {
                        parentChildMap.put(parentNode, Collections.<ChildNode> emptyList());
                    }
                }
            }
        }
        return parentChildMap;
    }

    private List<ChildNode> createChildNode(String parent) {
        String unsplitChildren = parent.substring(parent.indexOf(PRIMARY_CHILD_DELIMITER));
        String[] children = unsplitChildren.split(PRIMARY_CHILD_DELIMITER);
        if (children.length > 0) {
            List<ChildNode> childList = new ArrayList<ChildNode>();
            for (String child : children) {
                if (!isEmpty(child)) {
                    boolean hasSecondaryChildren = false;
                    String childDetails = child.substring(0, getChildIndexIfExist(child, SECONDARY_CHILD_DELIMITER));
                    String[] childrenElements = childDetails.split(" ");
                    if (child.indexOf(SECONDARY_CHILD_DELIMITER) != -1) {
                        hasSecondaryChildren = true;
                    }
                    ChildNode childNode = new ChildNode();
                    if(hasSecondaryChildren) {
                        childNode.setAttributeName(ATTRIBUTE_NAME);
                        if (childrenElements.length > 1) {
                            childNode.setAttributeValue(childrenElements[1]);
                        }
                    }
                    else {
                        childNode.setTagName(childrenElements[0]);
                        if (childrenElements.length > 1) {
                            childNode.setTagValue(childrenElements[1]);
                        }
                    }
                    if(hasSecondaryChildren) {
                        childNode.setChildren(createSecondaryChildren(child));
                    }
                    childList.add(childNode);
                }

            }
            return childList;
        }
        return Collections.emptyList();
    }

    private List<ChildNode> createSecondaryChildren(String child) {
        String unsplitChildren = child.substring(child.indexOf(SECONDARY_CHILD_DELIMITER));
        String[] children = unsplitChildren.split(SECONDARY_CHILD_DELIMITER);
        if (children.length > 0) {
            List<ChildNode> childList = new ArrayList<ChildNode>();
            for (String secondaryChild : children) {
                if (!isEmpty(secondaryChild)) {
                    String[] childrenElements = secondaryChild.split(" ");
                    ChildNode secondaryChildNode = new ChildNode();
                    secondaryChildNode.setTagName(childrenElements[0]);
                    if(childrenElements.length > 1) {
                        secondaryChildNode.setTagValue(childrenElements[1]);
                    }
                    childList.add(secondaryChildNode);
                }
            }
            return childList;
        }
        return Collections.emptyList();
    }

    private ParentNode createParentNode(String parent) {
        ParentNode parentNode = new ParentNode();
        String parentDetails = parent.substring(0, getChildIndexIfExist(parent, PRIMARY_CHILD_DELIMITER));
        String[] parentAttributes = parentDetails.split(" ");

        parentNode.setAttributeName(PARENT_ATTRIBUTE_ID);
        parentNode.setAttributeValue(parentAttributes[0]);
        if(parentAttributes.length > 1) {
            parentNode.setTagName(parentAttributes[1]);
        }
        return parentNode;
    }

    private int getChildIndexIfExist(String nodeElement, String delimiter) {
        return nodeElement.indexOf(delimiter) != -1 ? nodeElement.indexOf(delimiter) : nodeElement.length();
    }
    
}
