/*
 *  File name:  Remote
 *  Date:       4.3.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui.menus
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui.menus;

import javax.swing.*;

public class Remote {
    //TODO - implementing API for OneDrive, Google Drive etc.
    private JMenu remoteMenu = new JMenu("Remote");

    JMenu getRemoteMenu() {
        remoteMenu.add(oneDrive());
        remoteMenu.add(googleDrive());
        remoteMenu.add(dropBox());

        return remoteMenu;
    }

    private JMenuItem oneDrive() {
        JMenuItem oneDriveItem = new JMenuItem("OneDrive");

        //TODO - implement OneDrive api

        return oneDriveItem;
    }

    private JMenuItem googleDrive() {
        JMenuItem googleDriveItem = new JMenuItem("Google Drive");

        //TODO - implement Google Drive api

        return googleDriveItem;
    }

    private JMenuItem dropBox() {
        JMenuItem dropBoxItem = new JMenuItem("DropBox");

        //TODO - implement DropBox api

        return dropBoxItem;
    }
}
