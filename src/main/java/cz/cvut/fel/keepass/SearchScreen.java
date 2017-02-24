
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
    private static JFrame frame;
    private JTextField searchField;
    private JPanel panel;
    private JTree databaseStructureTree;
    private JList entriesList;

    private DatabaseUtils utils;

    public SearchScreen(DatabaseUtils ut) {
        utils = ut;
        prepareGUI();
    }

    public void showWindow() {
        frame = new JFrame("KeePass");
        frame.setContentPane(new SearchScreen(utils).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null); //set location to the center of the screen
        frame.setVisible(true);

        searchField.requestFocus();
    }

    private void prepareGUI(){
        searchField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                searchInDatabase();
            }
        });
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
            //databaseStructureTree
            //TODO every entry
        }
    }

    private void searchEntry(String search) {
        List<Entry> entries = utils.searchEntry(search);

        String userNames = "";
        if(entries.isEmpty()) {
            //Todo no entry
        } else {
            for(Entry entry : entries) {
                userNames += entry.getUsername() + "\n";
                //TODO add entry
            }
        }
    }

    private void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        clip.setContents(stringSelection, null);

    }
}
