/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.portcdm.demo.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.portcdm.demo.model.Node;

/**
 *
 * @author Glenn
 */
@Stateless
public class NodeHandlerBean {
    
    @EJB
    public NodeServiceBean nodeService;

    public List<Node> retrieveCurrentTreeNodes() {
        Set<Node> nodeSet = nodeService.getNodeDB();
        ArrayList<Node> nodeList = new ArrayList(nodeSet);
        return nodeList;
    } 
}
