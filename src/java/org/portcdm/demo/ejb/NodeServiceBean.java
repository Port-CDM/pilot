/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.portcdm.demo.ejb;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import org.portcdm.demo.model.Node;

/**
 *
 * @author Glenn
 */
@Singleton
public class NodeServiceBean {
    
    private static Logger logger = Logger.getLogger("org.portcdm.demo.ejb");
    private HashSet<Node> nodeDB = new HashSet();

    @PostConstruct
    public void initDB() {
        NodeServiceBean.debug("Init node service DB");
        Node root = new Node("Estimation", Node.NodeType.Root, NodeServiceBean.getRandomDate());
        Node root2 = new Node("Actual", Node.NodeType.Root, NodeServiceBean.getRandomDate());
        
        Node etb = new Node("ETB", Node.NodeType.ETB, NodeServiceBean.getRandomDate());
        Node etd = new Node("ETD", Node.NodeType.ETD, NodeServiceBean.getRandomDate());
        etb.setParentNode(root);
        etd.setParentNode(root);
        root.getChildNodes().add(etb);
        root.getChildNodes().add(etd);
        
        Node atb = new Node("ATB", Node.NodeType.ATB, NodeServiceBean.getRandomDate());
        atb.setParentNode(root2);
        root2.getChildNodes().add(atb);
        root.getSiblingNodes().add(root2);
        
        Node vessel1 = new Node("Amelia III", Node.NodeType.Vessel, NodeServiceBean.getRandomDate());
        Node vessel2 = new Node("North Start", Node.NodeType.Vessel, NodeServiceBean.getRandomDate());
        Node vessel3 = new Node("Stena Stone", Node.NodeType.Vessel, NodeServiceBean.getRandomDate());
        vessel1.setParentNode(etb);
        vessel2.setParentNode(etb);
        vessel2.setParentNode(atb);
        vessel3.setParentNode(etd);
        
        etb.getChildNodes().add(vessel1);
        etb.getChildNodes().add(vessel2);
        atb.getChildNodes().add(vessel2);
        etd.getChildNodes().add(vessel3);
        
        Node state1 = new Node("Confirmed Berth", Node.NodeType.State, NodeServiceBean.getRandomDate());
        Node state20 = new Node("Pilot On Board", Node.NodeType.State, NodeServiceBean.getRandomDate());
        Node state21 = new Node("Pilot On Board", Node.NodeType.State, NodeServiceBean.getRandomDate());
        Node state3 = new Node("Tug Arrived", Node.NodeType.State, NodeServiceBean.getRandomDate());
        Node state4 = new Node("Linesmen Arrived", Node.NodeType.State, NodeServiceBean.getRandomDate());
        Node state5 = new Node("Moring Completed", Node.NodeType.State, NodeServiceBean.getRandomDate());
        
        vessel1.getChildNodes().add(state1);
        vessel1.getChildNodes().add(state20);
        
        vessel2.getChildNodes().add(state1);
        vessel2.getChildNodes().add(state21);
        vessel2.getChildNodes().add(state3);
        vessel2.getChildNodes().add(state4);
        vessel2.getChildNodes().add(state5);
        
        Node action1 = new Node("Drinking Coffee", Node.NodeType.Action, NodeServiceBean.getRandomDate());
        Node action2 = new Node("Eating Cake", Node.NodeType.Action, NodeServiceBean.getRandomDate());
        
        action1.setParentNode(state20);
        action2.setParentNode(state20);
        
        state20.getChildNodes().add(action1);
        state20.getChildNodes().add(action2);
        
        nodeDB.add(root);        
    }
    
    public HashSet<Node> getNodeDB() {
        return nodeDB;
    }        

    public static void debug(String message) {
        logger.fine(message);
    }
    
    protected static Date getRandomDate(){
        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(2010, 2014);
        gc.set(GregorianCalendar.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

        gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);        
        gc.set(GregorianCalendar.HOUR_OF_DAY, randBetween(1, 24));
        gc.set(GregorianCalendar.MINUTE, randBetween(0, 59));

        return gc.getTime();
    }
    
    private static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
