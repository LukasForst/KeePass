/*
 *  File name:  FileSelectionPanel
 *  Date:       25.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
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

    public FileSelectionPanel() {
        getLastPath(); // gets last used keepass database file path
        setListeners();
    }

    private void setListeners() {
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
    } //sets all listeners

    public JPanel getPanel() {
        return panel;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(String text) {
        passwordField.setText(text);
    }

    public JButton getOpenFileButton() {
        return openFileButton;
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

    public String getPath() { //returns main file path
        saveLastPath();
        return pathField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

}
