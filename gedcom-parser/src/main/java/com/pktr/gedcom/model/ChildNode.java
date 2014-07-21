package com.pktr.gedcom.model;

import java.util.List;

/**
 * Class representing a node in the input data
 * 
 * @author pkumar
 */
public class ChildNode {

    private String tagName;

    private String tagValue;

    private String attributeName;

    private String attributeValue;

    private List<ChildNode> children;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public List<ChildNode> getChildren() {
        return children;
    }

    public void setChildren(List<ChildNode> children) {
        this.children = children;
    }

    public boolean hasChildren() {
        return children != null && children.size() > 0;
    }

}
