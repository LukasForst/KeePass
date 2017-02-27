/*
 *  File name:  SearchPanel
 *  Date:       25.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui;

import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.Group;
import de.slackspace.openkeepass.domain.KeePassFile;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.List;


public class SearchPanel {
    public JPanel panel;

    KeePassFile database = null;

    private JTextField searchField;
    private JList groupsList;
    private JButton searchButton;
    private JList entriesList;

    SearchPanel() {
        Action searchEntries = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                searchInDatabase();
            }
        };
        searchButton.addActionListener(searchEntries);
        searchField.addActionListener(searchEntries);

        groupsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                searchForEntriesInGroup(groupsList.getSelectedValue().toString());
            }
        });
    }

    private void searchInDatabase() {
        String search = searchField.getText();
        if (search.startsWith("!g") || search.startsWith("!G")) {
            setGroupsList();
        } else {
            searchForSpecificEntry(search);
        }
    }

    void setGroupsList() { // finds all groups
        groupsList.setListData(database.getGroups().toArray());

    }

    private void searchForEntriesInGroup(String groupName) {
        Group group = database.getGroupByName(groupName);
        List<Entry> entries = group.getEntries();

        if (entries.size() == 0) {
            entriesList.removeAll();
            return;
        }

        String[] data = new String[entries.size()];
        int i = 0;
        for (Entry entry : entries) {
            data[i] += entry.getTitle() + " ------- " + entry.getUsername() + " ------- " + entry.getPassword() + "\n";
            i++;
        }

        entriesList.setListData(data);
    }

    private void searchForSpecificEntry(String phrase) {
        List<Entry> entries = database.getEntries();
        List<Entry> fin = database.getEntries();

        for (Entry entry : entries) {
            if (entry.getUsername().contains(phrase) || entry.getTitle().contains(phrase) || entry.getNotes().contains(phrase)) {
                continue;
            } else {
                fin.remove(entry);
            }
        }
        entriesList.setListData(fin.toArray());
    }

    private void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        clip.setContents(stringSelection, null);

    }

}
