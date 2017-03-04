/*
 *  File name:  SearchPanel
 *  Date:       25.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui;

import cz.cvut.fel.keepass.ui.menus.Menu;
import cz.cvut.fel.keepass.utils.DataUtils;
import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.Group;
import de.slackspace.openkeepass.domain.KeePassFile;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class SearchPanel {
    private JPanel panel;
    private JTextField searchField;
    private JList groupsList;
    private JButton searchButton;
    private JTable entriesTable;
    private JScrollPane tableScrollPane;
    private JMenu fileMenu;
    private JMenu settingsMenu;
    private JMenu createMenu;
    private JMenuBar menuBar;

    private KeePassFile database = null;
    private String[] columnTableNames = {"Title", "UserName", "Password"};
    private String[][] currentDisplayedData;

    public SearchPanel() {
        entriesTable.setCellSelectionEnabled(true); //table config
        entriesTable.setColumnSelectionAllowed(true);

        setListeners();
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setDatabase(KeePassFile database) {
        this.database = database;
        setGroupsList();
        entriesTable.setModel(new DefaultTableModel(new String[][]{{" "}}, new String[]{})); //show empty table
        currentDisplayedData = null;
    }

    private void setGroupsList() { // finds all groups
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
        int arrayIndex = -1;
        for (Entry entry : entries) {
            arrayIndex++;
            data[arrayIndex][0] = entry.getTitle();
            data[arrayIndex][1] = entry.getUsername();
            data[arrayIndex][2] = entry.getPassword();
        }
        currentDisplayedData = DataUtils.deepCopy(data);

        DataUtils.hidePasswords(data, arrayIndex);

        entriesTable.setModel(new DefaultTableModel(data, columnTableNames));
    }

    private void searchForSpecificEntry(String phrase) { //phrase is in lowercase, because of case sensitivity
        List<Entry> entries = database.getEntries();
        String[][] data = new String[entries.size()][3];

        int arrayIndex = -1;
        for (Entry entry : entries) {
            if (entry.toString().toLowerCase().contains(phrase)) {
                arrayIndex++;
                data[arrayIndex][0] = entry.getTitle();
                data[arrayIndex][1] = entry.getUsername();
                data[arrayIndex][2] = entry.getPassword();
            }
        }

        if (arrayIndex == -1) {
            currentDisplayedData = null;
            entriesTable.setModel(new DefaultTableModel(new String[][]{{"Nothing found!"}}, new String[]{""}));
        } else {
            String[][] finalStringArray = new String[arrayIndex + 1][3];

            for (int i = 0; i <= arrayIndex; i++) {
                finalStringArray[i][0] = data[i][0];
                finalStringArray[i][1] = data[i][1];
                finalStringArray[i][2] = data[i][2];
            }

            currentDisplayedData = DataUtils.deepCopy(finalStringArray);
            DataUtils.hidePasswords(finalStringArray, arrayIndex);

            entriesTable.setModel(new DefaultTableModel(finalStringArray, columnTableNames));
        }
    }

    private void setListeners() { //method with all listeners
        entriesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);

                if (currentDisplayedData == null) return; // Nothing found statement

                int x = entriesTable.getSelectedRow();
                int y = entriesTable.getSelectedColumn();

                DataUtils.copyToClipboard(currentDisplayedData[x][y]); // gets value at coordinates
            }
        });

        groupsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                try {
                    if (groupsList.getSelectedValue() != null) {
                        Object e = groupsList.getSelectedValue();
                        searchForEntriesInGroup(e.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        Action searchEntries = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                searchForSpecificEntry(searchField.getText().toLowerCase());
            }
        };
        searchButton.addActionListener(searchEntries);
        searchField.addActionListener(searchEntries);
    }

    private void createUIComponents() {
        fileMenu = Menu.getFileMenu();
        createMenu = Menu.getCreateMenu();
        settingsMenu = Menu.getSettingsMenu();
    }
}
