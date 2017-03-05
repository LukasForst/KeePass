/*
 *  File name:  About
 *  Date:       4.3.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui.menus
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui.menus;

import javax.swing.*;

public class Help {
    private JMenu help = new JMenu("Help");

    JMenu getHelpMenu() {
        help.add(checkUpdates());
        help.add(about());

        return help;
    }

    private JMenuItem checkUpdates() {
        JMenuItem checkUpdatesItem = new JMenuItem("Check updates");
        // TODO - check updates
        return checkUpdatesItem;
    }

    private JMenuItem about() {
        JMenuItem about = new JMenuItem("About");
        // TODO - about
        return about;
    }

}
