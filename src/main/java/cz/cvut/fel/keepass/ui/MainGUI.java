/*
 *  File name:  MainGUI
 *  Date:       24.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui;

import de.slackspace.openkeepass.domain.KeePassFile;

import javax.swing.*;
import java.awt.*;

public class MainGUI {
    private static JFrame mainFrame = new JFrame();
    private static JPanel cardPanel;
    private static CardLayout cardLayout = new CardLayout();
    private static FileSelectionPanel fileSelectionPanel;
    private static SearchPanel searchPanel;
    private KeePassFile database;

    public MainGUI() {
        cardPanel = setCardPanel();
        mainFrame.add(cardPanel);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("KeePass");
    }

    public static void showPanel(String name) {
        if (name.contains("search")) {
            showSearchScreen();
        } else if (name.contains("file")) {
            showFileScreen();
        }
    }

    private static void showFileScreen() {
        mainFrame.setVisible(false);
        cardLayout.show(cardPanel, "fileScreen");
        mainFrame.setSize(400, 400);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private static void showSearchScreen() {
        mainFrame.setVisible(false);
        searchPanel.setDatabase(fileSelectionPanel.getDatabase());
        cardLayout.show(cardPanel, "searchScreen");
        mainFrame.setSize(700, 700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private JPanel setCardPanel() {
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);

        cardPanel.add(setSearchScreen(), "searchScreen");
        cardPanel.add(setFileScreen(), "fileScreen");
        showFileScreen();
        return cardPanel;
    }

    private JPanel setFileScreen() {
        fileSelectionPanel = new FileSelectionPanel();
        return fileSelectionPanel.getPanel();
    }

    private JPanel setSearchScreen() {
        searchPanel = new SearchPanel();
        return searchPanel.getPanel();
    }


}
