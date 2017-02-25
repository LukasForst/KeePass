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

public class MainGUI{
    private JFrame mainFrame = new JFrame();
    private JPanel cardPanel, searchScreen, fileSelectionScreen;
    private CardLayout cardLayout = new CardLayout();

    private JLabel l1, l2;
    private JButton changePanelButton;

    public MainGUI() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setTitle("KeePass - 2nd branch");

        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        searchScreen = new JPanel();
        fileSelectionScreen = new JPanel();

        l1 = new JLabel("Search Screen");
        l2 = new JLabel("File Screen");

        searchScreen.add(l1);
        fileSelectionScreen.add(l2);

        cardPanel.add(fileSelectionScreen, "fileSelection");
        cardPanel.add(searchScreen, "searchScreen");

        changePanelButton = new JButton("Change panel");
        changePanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cardLayout.show(cardPanel, "searchScreen");
                mainFrame.setSize(500,500);
            }
        });

        fileSelectionScreen.add(changePanelButton);

        mainFrame.add(cardPanel);
        mainFrame.pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                MainGUI m = new MainGUI();
                m.mainFrame.setVisible(true);
            }
        });
    }

}
