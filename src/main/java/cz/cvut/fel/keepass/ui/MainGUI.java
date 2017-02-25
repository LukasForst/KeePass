/*
 *  File name:  MainGUI
 *  Date:       24.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui;

import cz.cvut.fel.keepass.DatabaseUtils;
import de.slackspace.openkeepass.domain.KeePassFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainGUI {
    private JFrame mainFrame = new JFrame();
    private JPanel cardPanel;
    private CardLayout cardLayout = new CardLayout();

    private DatabaseUtils utils;

    public MainGUI() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(400, 400);
        mainFrame.setTitle("KeePass - 2nd branch");

        cardPanel = setCardPanel();


        mainFrame.add(cardPanel);
        mainFrame.setVisible(true);
    }

    private JPanel setCardPanel() {
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);

        FileSelectionPanel fileScreen = new FileSelectionPanel();
        cardPanel.add(fileScreen.panel, "fileSelection");

        SearchPanel searchPanel = new SearchPanel();
        cardPanel.add(searchPanel.panel, "searchScreen");


        Action startSearch = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                KeePassFile dat = fileScreen.openKeepassDatabase();

                if (dat != null) {
                    cardLayout.show(cardPanel, "searchScreen");
                    searchPanel.database = dat;
                }
            }
        };

        fileScreen.openFileButton.addActionListener(startSearch);
        fileScreen.passwordField.addActionListener(startSearch);


/*
                cardLayout.show(cardPanel, "searchScreen");
                mainFrame.setSize(500,500);
*/


        return cardPanel;
    }

}
