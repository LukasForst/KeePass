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

public class FileSelectionPanel {
    public JPanel panel;
    JPasswordField passwordField;
    JButton openFileButton;
    private JTextField pathField;
    private JLabel informationLabel;
    private JButton findPathButton;
    private String favouriteFilePath = "fav.txt";   //path where is stored favourite file path

    FileSelectionPanel() {
        findPathButton.addActionListener(new ActionListener() {
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
        getLastPath();
        panel.addPropertyChangeListener(new PropertyChangeListener() { //focus for the password field
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                passwordField.requestFocus();
            }
        });
    }

    public String getFavouriteFilePath() {
        return favouriteFilePath;
    }

    private void getLastPath() {
        try { //reading last used path to the kdbx file
            String path;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(favouriteFilePath));
            path = bufferedReader.readLine();
            bufferedReader.close();

            pathField.setText(path);
            favouriteFilePath = path;

        } catch (Exception e) {
            pathField.setText("");
        }
    }

    String getPath() { //returns main file path
        return pathField.getText();
    }

    String getPassword() {
        return new String(passwordField.getPassword());
    }

}
