/*
 *  File name:  MainGUI
 *  Date:       24.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    public JFrame mainFrame = new JFrame();
    private JPanel cardPanel, searchScreen;
    private CardLayout cardLayout = new CardLayout();

    private JLabel l1, l2;
    private JButton changePanelButton;

    public MainGUI() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(400, 400);
        mainFrame.setTitle("KeePass - 2nd branch");

        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);

        searchScreen = new JPanel();

        l1 = new JLabel("Search Screen");
        searchScreen.add(l1);

        FileSelectionPanel fileScreen = new FileSelectionPanel();

        cardPanel.add(fileScreen.fileSelectionPanel, "fileSelection");
        cardPanel.add(searchScreen, "searchScreen");


        Action startSearch = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileScreen.openKeepassDatabase();
                cardLayout.show(cardPanel, "searchScreen");
            }
        };
        fileScreen.openFileButton.addActionListener(startSearch);
        fileScreen.passwordField.addActionListener(startSearch);


        changePanelButton = new JButton("Change panel");

        changePanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cardLayout.show(cardPanel, "searchScreen");
                mainFrame.setSize(500,500);
            }
        });

        mainFrame.add(cardPanel);
        mainFrame.setVisible(true);
    }

/*
    private JPanel setCardPanel(){

    }
*/
}
