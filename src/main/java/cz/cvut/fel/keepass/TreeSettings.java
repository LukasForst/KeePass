/*
 *  File name:  TreeSettings
 *  Date:       24.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeSettings {

    public static DefaultMutableTreeNode TreeSettings() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        DefaultMutableTreeNode vegetableNode = new DefaultMutableTreeNode("Vegetables");
        DefaultMutableTreeNode fruitNode = new DefaultMutableTreeNode("Fruits");

        root.add(vegetableNode);
        root.add(fruitNode);

        return root;
    }
}
