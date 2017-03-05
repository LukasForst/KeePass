/*
 *  File name:  File
 *  Date:       4.3.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui.menus
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui.menus;

import cz.cvut.fel.keepass.MainGUI;
import cz.cvut.fel.keepass.utils.DataUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class File {
    private JMenu fileMenu = new JMenu("File:");

    JMenu getFileMenu() {
        fileMenu.add(openRecentDatabase());
        fileMenu.add(openNewDatabase());
        fileMenu.add(newFile());
        fileMenu.add(closeDatabase());
        fileMenu.add(exitProgram());

        return fileMenu;
    }

    private JMenu openRecentDatabase() {
        JMenu openRecentDatabaseMenu = new JMenu("Recent files");

        for (int i = 0; i < 3; i++) {
            String il = "" + i;
            openRecentDatabaseMenu.add(new JMenuItem(il));
        }
        // TODO - add recent records
        return openRecentDatabaseMenu;
    }

    private JMenu newFile() {
        JMenu newFilesMenu = new JMenu("New");
        JMenuItem newDatabase = new JMenuItem("KeePass file");
        newFilesMenu.add(newDatabase);

        // TODO - create new database

        return newFilesMenu;
    }

    private JMenuItem closeDatabase() {
        /** closes current database and shows file screen*/
        JMenuItem close = new JMenuItem("Close database");
        close.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainGUI.showPanel("fileScreen");
            }
        });

        return close;
    }

    private JMenuItem exitProgram() {
        /** ends program */
        JMenuItem exitProgram = new JMenuItem("Exit");
        exitProgram.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        return exitProgram;
    }

    private JMenuItem openNewDatabase() {
        /** opens new database */
        JMenuItem open = new JMenuItem("Open...");
        open.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(null);

                if (fileChooser.getSelectedFile() != null) {
                    DataUtils.setCurrentDatabasePath(fileChooser.getSelectedFile().getAbsolutePath());
                    MainGUI.showPanel("fileScreen");
                }
            }
        });

        return open;
    }
}
