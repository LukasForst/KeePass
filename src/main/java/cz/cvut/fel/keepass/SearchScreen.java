/*
 *  File name:  searchScreen
 *  Date:       23.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass;

import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.Group;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchScreen {
    private JFrame frame;
    private JTextField searchField;
    private JPanel panel;
    private JTextArea nameArea;
    private JTextArea passArea;
    private JButton searchButton;

    private DatabaseUtils utils;

    public SearchScreen(DatabaseUtils ut) {
        utils = ut;

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                searchEntry();
            }
        });
        searchField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                searchEntry();
            }
        });
    }

    public void showWindow() {
        frame = new JFrame("KeePass");
        frame.setContentPane(new SearchScreen(utils).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null); //set location to the center of the screen
        frame.setVisible(true);

        searchButton.requestFocus();
    }

    private void searchEntry() {
        List<Entry> entries = utils.searchEntry(searchField.getText());

        String userNames = "";
        String passWords = "";

        for(Entry entry : entries) {
            userNames += entry.getUsername() + "\n";
            passWords += entry.getPassword() + "\n";

            nameArea.setText(userNames);
            passArea.setText(passWords);
        }
    }
}
