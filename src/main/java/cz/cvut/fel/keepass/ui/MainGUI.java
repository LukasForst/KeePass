/*
 *  File name:  MainGUI
 *  Date:       24.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui;

import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.KeePassFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class MainGUI {
    private JFrame mainFrame = new JFrame();
    private JPanel cardPanel;
    private CardLayout cardLayout = new CardLayout();

    private KeePassFile database;

    public MainGUI() {
        cardPanel = setCardPanel();

        mainFrame.add(cardPanel);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 400);
        mainFrame.setTitle("KeePass");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private JPanel setCardPanel() {
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);

        FileSelectionPanel fileScreen = new FileSelectionPanel();
        cardPanel.add(fileScreen.panel, "fileSelection");

        SearchPanel searchPanel = new SearchPanel();
        cardPanel.add(searchPanel.panel, "searchScreen");

        Action openSearchScreen = new AbstractAction() { // opens database
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                KeePassFile dat = getDatabase(fileScreen.getPath(), fileScreen.getPassword());

                if (dat == null) {
                    fileScreen.passwordField.setText("");
                } else {
                    saveLastPath(fileScreen.getFavouriteFilePath(), fileScreen.getPath());

                    searchPanel.setDatabase(dat);
                    searchPanel.setGroupsList();

                    mainFrame.setVisible(false);
                    cardLayout.show(cardPanel, "searchScreen");
                    mainFrame.setSize(700, 700);
                    mainFrame.setLocationRelativeTo(null);
                    mainFrame.setVisible(true);
                }
            }
        };

        fileScreen.openFileButton.addActionListener(openSearchScreen);
        fileScreen.passwordField.addActionListener(openSearchScreen);

        return cardPanel;
    }

    private KeePassFile getDatabase(String path, String pass) {
        File f = new File(path);
        if (!(f.exists() && !f.isDirectory())) {
            JOptionPane.showMessageDialog(null, "You must enter the valid file!");
            return null;
        }

        try {
            return KeePassDatabase.getInstance(path).openDatabase(pass);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    private boolean saveLastPath(String favouriteFilePath, String databasePath) {
        try {
            PrintWriter writer = new PrintWriter(favouriteFilePath, "UTF-8");
            writer.print(databasePath);
            writer.close();
            return true;

        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
    }
}
