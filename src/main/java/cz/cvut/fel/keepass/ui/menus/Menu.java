/*
 *  File name:  Menus
 *  Date:       4.3.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui.menus
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui.menus;

import javax.swing.*;

public class Menu {
    public static JMenu getFileMenu() {
        return new File().getFileMenu();
    }

    public static JMenu getCreateMenu() {
        return new Create().getCreateMenu();
    }

    public static JMenu getSettingsMenu() {
        return new Settings().getSettingsMenu();
    }

    public static JMenu getRemoteMenu() {
        return new Remote().getRemoteMenu();
    }

    public static JMenu getHelpMenu() {
        return new Help().getHelpMenu();
    }
}
