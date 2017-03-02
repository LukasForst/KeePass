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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.List;


public class SearchPanel {
    private JPanel panel;
    private KeePassFile database = null;
    private JTextField searchField;
    private JList groupsList;
    private JButton searchButton;
    private JTable entriesTable;
    private String[] columnTableNames = {"Title", "UserName", "Password"};


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

        groupsList.add(new JScrollPane());

        entriesTable.setCellSelectionEnabled(true);
        entriesTable.add(new JScrollPane());
    }

    public JPanel getPanel() {
        return panel;
    }

    public KeePassFile getDatabase() {
        return database;
    }

    public void setDatabase(KeePassFile database) {
        this.database = database;
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
        if (database != null) {
            groupsList.setListData(database.getGroups().toArray());
            searchField.requestFocus();
        }
    }

    private void searchForEntriesInGroup(String groupName) {
        Group group = database.getGroupByName(groupName);
        List<Entry> entries = group.getEntries();

        if (entries.size() == 0) {
            entriesTable.setModel(new DefaultTableModel(new String[][]{{"Nothing found!"}}, new String[]{" "}));
            return;
        }

        String[][] data = new String[entries.size()][3];
        int arrayIndex = 0;
        for (Entry entry : entries) {
            data[arrayIndex][0] = entry.getTitle();
            data[arrayIndex][1] = entry.getUsername();
            data[arrayIndex][2] = entry.getPassword();
            arrayIndex++;
        }
        entriesTable.setModel(new DefaultTableModel(data, columnTableNames));
    }

    private void searchForSpecificEntry(String phrase) {
        List<Entry> entries = database.getEntries();
        String[][] data = new String[entries.size()][3];
        int arrayIndex = 0;

        for (Entry entry : entries) {
            if (entry.getTitle().contains(phrase) || entry.getUsername().contains(phrase)) {
                data[arrayIndex][0] = entry.getTitle();
                data[arrayIndex][1] = entry.getUsername();
                data[arrayIndex][2] = entry.getPassword();
                arrayIndex++;
            }
        }

        if (arrayIndex == 0) {
            entriesTable.setModel(new DefaultTableModel(new String[][]{{"Nothing found!"}}, new String[]{" "}));
        } else {
            String[][] finalStringArray = new String[arrayIndex][3];

            for (int i = 0; i < arrayIndex; i++) {
                finalStringArray[i][0] = data[i][0];
                finalStringArray[i][1] = data[i][2];
                finalStringArray[i][2] = data[i][2];
            }

            entriesTable.setModel(new DefaultTableModel(finalStringArray, columnTableNames));
        }
    }


    private void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        clip.setContents(stringSelection, null);
    }

}
