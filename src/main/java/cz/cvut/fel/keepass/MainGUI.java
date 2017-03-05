/*
 *  File name:  MainGUI
 *  Date:       24.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass;

import cz.cvut.fel.keepass.ui.FileSelectionPanel;
import cz.cvut.fel.keepass.ui.SearchPanel;
import cz.cvut.fel.keepass.utils.DataUtils;

import javax.swing.*;
import java.awt.*;

public class MainGUI {
    private static JFrame mainFrame = new JFrame();
    private static JPanel cardPanel;
    private static CardLayout cardLayout = new CardLayout();
    private static FileSelectionPanel fileSelectionPanel;
    private static SearchPanel searchPanel;

    public MainGUI() {
        cardPanel = configureCardPanel();
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

    public static void changeTitle(String newTitle) {
        mainFrame.setTitle("KeePass - " + newTitle);
    }

    private static void showFileScreen() {
        mainFrame.setVisible(false);
        mainFrame.setTitle("KeePass");
        mainFrame.setSize(400, 400);
        mainFrame.setLocationRelativeTo(null);

        fileSelectionPanel.setPathFieldText(DataUtils.getCurrentDatabasePath());
        cardLayout.show(cardPanel, "fileScreen");

        mainFrame.setVisible(true);
        mainFrame.requestFocus();
        fileSelectionPanel.setFocusOnPasswordField();
    }

    private static void showSearchScreen() {
        mainFrame.setVisible(false);
        cardLayout.show(cardPanel, "searchScreen");
        mainFrame.setSize(700, 700);
        mainFrame.setLocationRelativeTo(null);

        searchPanel.configureUI(DataUtils.getDatabase());

        mainFrame.setVisible(true);
    }

    private JPanel configureCardPanel() {
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);

        cardPanel.add(configureSearchScreen(), "searchScreen");
        cardPanel.add(configureFileScreen(), "fileScreen");
        showFileScreen();
        return cardPanel;
    }

    private JPanel configureFileScreen() {
        fileSelectionPanel = new FileSelectionPanel();
        return fileSelectionPanel.getPanel();
    }

    private JPanel configureSearchScreen() {
        searchPanel = new SearchPanel();
        return searchPanel.getPanel();
    }
}
