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

public class MainGUI {
    private JFrame mainFrame = new JFrame();
    private JPanel cardPanel;
    private CardLayout cardLayout = new CardLayout();

    private KeePassFile database;

    private FileSelectionPanel fileSelectionPanel;
    private SearchPanel searchPanel;

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

        cardPanel.add(setSearchScreen(), "searchScreen");
        cardPanel.add(setFileScreen(), "fileSelection");
        cardLayout.show(cardPanel, "fileSelection");

        return cardPanel;
    }

    private JPanel setFileScreen() {
        fileSelectionPanel = new FileSelectionPanel();

        Action openSearchScreen = new AbstractAction() { // opens database
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                KeePassFile dat = getDatabase(fileSelectionPanel.getPath(), fileSelectionPanel.getPassword());
                if (dat == null) {
                    fileSelectionPanel.setPasswordField("");
                } else {
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

        fileSelectionPanel.getOpenFileButton().addActionListener(openSearchScreen);
        fileSelectionPanel.getPasswordField().addActionListener(openSearchScreen);

        return fileSelectionPanel.getPanel();
    }

    private JPanel setSearchScreen() {
        searchPanel = new SearchPanel();


        return searchPanel.getPanel();
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

}
