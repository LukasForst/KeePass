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
import java.awt.datatransfer.*;
import java.awt.Toolkit;

public class SearchScreen {
    private JFrame frame;
    private JTextField searchField;
    private JPanel panel;
    private JTextArea nameArea;
    private JTextArea passArea;
    private JButton searchButton;
    private JScrollBar scrollBar1;
    private JScrollBar scrollBar2;

    private DatabaseUtils utils;

    public SearchScreen(DatabaseUtils ut) {
        utils = ut;

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                searchInDatabase();
            }
        });
        searchField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                searchInDatabase();
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

    private void searchInDatabase() {
        String search = searchField.getText();
        if(search.startsWith("!g") || search.startsWith("!G")) {
            searchGroups();
        } else {
            searchEntry(search);
        }
    }

    private void searchGroups() {
        List<Group> groups = utils.getGroups();
        String groupsNames = "";

        for(Group group : groups) {
            groupsNames += group.getName() + "\n";
            nameArea.setText(groupsNames);
        }
    }

    private void searchEntry(String search) {
        List<Entry> entries = utils.searchEntry(search);

        String userNames = "";
        String passWords = "";

        for(Entry entry : entries) {
            userNames += entry.getUsername() + "\n";
            passWords += entry.getPassword() + "\n";

            nameArea.setText(userNames);
            passArea.setText(passWords);
        }
    }

    private void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        clip.setContents(stringSelection, null);

    }
}
