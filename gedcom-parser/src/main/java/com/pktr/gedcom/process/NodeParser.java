package com.pktr.gedcom.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pktr.gedcom.model.ChildNode;
import com.pktr.gedcom.model.ParentNode;

/**
 * Class for converting the TXT file contents to a Map representation
 * @author pkumar
 *
 */
public class NodeParser  {

    /**
     * @param fileData - TXT file contents in the form of String
     * @return Map containing XML data in the form of Parent-Child, where Child might have children 
     */
    public Map<ParentNode, List<ChildNode>> toNodeElements(String fileData) {
        // TODO Auto-generated method stub
        return null;
    }
}
