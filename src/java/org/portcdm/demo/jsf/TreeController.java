/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.portcdm.demo.jsf;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.portcdm.demo.ejb.NodeHandlerBean;
import org.portcdm.demo.ejb.NodeServiceBean;
import org.portcdm.demo.model.Node;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Glenn
 */
@Named("treeController")
@ViewScoped
public class TreeController implements Serializable {

    @EJB
    protected NodeHandlerBean nodeHandler;
    private TreeNode currentTree;
    private DefaultTreeNode currentSelectedNode;
    private Node currentNode;
    private String controllerName = "treeController";

    public TreeController() {
        NodeServiceBean.debug("TreeController constructor");
    }

    public void buildTreeNode() {
        List<Node> nodeList = nodeHandler.retrieveCurrentTreeNodes();
        if (nodeList == null) {
            JsfUtil.addErrorMessage("Node node persisted");
            return;
        }
        currentTree = new DefaultTreeNode("root", null);
        Node startNode = nodeList.get(0);
        recursiveTreeBuilder(currentTree, nodeList);
        if(startNode != null &&  !startNode.getSiblingNodes().isEmpty()) {
            recursiveTreeBuilder(currentTree, startNode.getSiblingNodes());
        }
    }

    private void recursiveTreeBuilder(TreeNode parentTreeNode, List<Node> childTreeNodeList) {
        for (Node node : childTreeNodeList) {
            DefaultTreeNode treeNode = new DefaultTreeNode(node.getNodeType().toString(), node, parentTreeNode);
            if (!node.getChildNodes().isEmpty()) {
                this.recursiveTreeBuilder(treeNode, node.getChildNodes());
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        currentNode = (Node) event.getTreeNode().getData();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected ", currentSelectedNode.toString());
        FacesContext.getCurrentInstance().addMessage(controllerName, message);
    }

    public TreeNode getCurrentTree() {
        return currentTree;
    }

    public DefaultTreeNode getCurrentSelectedNode() {
        return currentSelectedNode;
    }

    public void setCurrentSelectedNode(DefaultTreeNode currentSelectedNodes) {
        this.currentSelectedNode = currentSelectedNodes;
    }

    public String getControllerName() {
        return controllerName;
    }

    public Node getCurrentNode() {
        return currentNode;
    }
}
