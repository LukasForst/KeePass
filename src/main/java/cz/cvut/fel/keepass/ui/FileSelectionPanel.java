/*
 *  File name:  FileSelectionPanel
 *  Date:       25.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui;

import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.KeePassFile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class FileSelectionPanel {
    private JPanel panel;
    private JPasswordField passwordField;
    private JButton openFileButton;
    private JTextField pathField;
    private JLabel informationLabel;
    private JButton findPathButton;
    private String storedFavouritePath = "fav.txt";   //path where is stored favourite file path

    private KeePassFile database = null;

    public FileSelectionPanel() {
        getLastPath(); // gets last used keepass database file path
        setListeners();
    }

    public KeePassFile getDatabase() {
        return database;
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
                KeePassFile dat = getDatabase(pathField.getText(), passwordField.getPassword());
                if (dat == null) {
                    passwordField.setText("");
                } else {
                    saveLastPath();
                    MainGUI.showPanel("searchScreen");
                    passwordField.setText("");
                }
            }
        };

        openFileButton.addActionListener(openSearchScreen);
        passwordField.addActionListener(openSearchScreen);
    }

    private void getLastPath() {
        try { //reading last used path to the kdbx file
            String path;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(storedFavouritePath));
            path = bufferedReader.readLine();
            bufferedReader.close();
            pathField.setText(path);
        } catch (Exception e) {
            pathField.setText("");
        }
    }

    private void saveLastPath() {
        try { //reading last used path to the kdbx file
            PrintWriter writer = new PrintWriter(storedFavouritePath, "UTF-8");
            writer.print(pathField.getText());
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getMessage() + " -- " + e.getCause());
        }
    }

    private KeePassFile getDatabase(String path, char[] pass) {
        String password = new String(pass);
        File f = new File(path);
        if (!(f.exists() && !f.isDirectory())) {
            JOptionPane.showMessageDialog(null, "You must enter the valid file!");
            return null;
        }

        try {
            database = KeePassDatabase.getInstance(path).openDatabase(password);
            return database;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

}
