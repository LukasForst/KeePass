/*
 *  File name:  FileSelectionPanel
 *  Date:       25.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui;

import cz.cvut.fel.keepass.MainGUI;
import cz.cvut.fel.keepass.utils.DataUtils;
import de.slackspace.openkeepass.domain.KeePassFile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FileSelectionPanel {
    private JPanel panel;
    private JPasswordField passwordField;
    private JButton openFileButton;
    private JTextField pathField;
    private JLabel informationLabel;
    private JButton findPathButton;

    public FileSelectionPanel() {
        pathField.setText(DataUtils.getCurrentDatabasePath());
        setListeners();
    }

    public void setPathFieldText(String path) {
        pathField.setText(path);
    }

    public JPanel getPanel() {
        return panel;
    }

    private void setListeners() {//sets all listeners
        panel.addPropertyChangeListener(new PropertyChangeListener() { //focus for the password field
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                passwordField.requestFocus();
            }
        });

        findPathButton.addActionListener(new ActionListener() {  //opens file dialog
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showOpenDialog(null);

                if (jFileChooser.getSelectedFile() != null) {
                    pathField.setText(jFileChooser.getSelectedFile().getAbsolutePath()); //writes path to the path field
                }
                passwordField.requestFocusInWindow();
            }
        });

        Action openSearchScreen = new AbstractAction() { // opens database
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                KeePassFile dat = DataUtils.openDatabaseFromFile(pathField.getText(), passwordField.getPassword());
                if (dat == null) {
                    passwordField.setText("");
                } else {
                    DataUtils.saveLastPath(pathField.getText()); //saves last used kdbx path

                    passwordField.setText("");

                    String[] name = pathField.getText().split("/");
                    MainGUI.changeTitle(name[name.length - 1]);
                    MainGUI.showPanel("searchScreen");
                }
            }
        };

        openFileButton.addActionListener(openSearchScreen);
        passwordField.addActionListener(openSearchScreen);
    }

    public void setFocusOnPasswordField() {
        passwordField.requestFocus();
    }

}
