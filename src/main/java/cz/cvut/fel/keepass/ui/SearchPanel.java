/*
 *  File name:  SearchPanel
 *  Date:       25.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui;

import cz.cvut.fel.keepass.DatabaseUtils;
import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.Group;
import de.slackspace.openkeepass.domain.KeePassFile;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

public class SearchPanel {
    private static DatabaseUtils utils = new DatabaseUtils();
    public JPanel panel;

    KeePassFile database;

    private JTextField searchField;
    private JList entriesList;
    private JTree groupsTree;
    private JButton searchButton;

    public SearchPanel() {

    }

    private boolean isValid() {
        if (utils.database == null) {
            if (database == null) {
                return false;
            } else {
                utils.database = database;
                return true;
            }
        } else {
            return true;
        }
    }

    private void searchInDatabase() {
        String search = searchField.getText();
        if (search.startsWith("!g") || search.startsWith("!G")) {
            searchGroups();
        } else {
            searchEntry(search);
        }
    }

    private void searchGroups() {
        if (!isValid()) {
            return;
        }

        List<Group> groups = utils.getGroups();
        String groupsNames = "";


        for (Group group : groups) {
            groupsNames += group.getName() + "\n";
            //databaseStructureTree
            //TODO every entry
        }
    }

    private void searchEntry(String search) {
        if (!isValid()) {
            return;
        }

        List<Entry> entries = utils.searchEntry(search);

        String userNames = "";

        if (entries.isEmpty()) {
            //Todo no entry
        } else {
            for (Entry entry : entries) {
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
