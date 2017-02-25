/*
 *  File name:  testScreen
 *  Date:       25.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui;

import cz.cvut.fel.keepass.DatabaseUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class FileSelectionPanel {
    public JPanel fileSelectionPanel;
    private JTextField pathField;
    private JPasswordField passwordField;
    private JLabel informationLabel;
    private JButton openFileButton;
    private JButton findPathButton;

    private String favouriteFilePath = "fav.txt";

    public FileSelectionPanel() {

        Action startSearch = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openKeepassDatabase();
            }
        };

        passwordField.addActionListener(startSearch);
        openFileButton.addActionListener(startSearch);

        findPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showOpenDialog(null);

                pathField.setText(jFileChooser.getSelectedFile().getAbsolutePath()); //writes path to the field
                passwordField.requestFocus();
            }
        });

        getLastPath();
    }

    private void getLastPath() {
        try { //reading last
            String path;
            FileReader fileReader = new FileReader(favouriteFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            path = bufferedReader.readLine();
            bufferedReader.close();

            pathField.setText(path);
            favouriteFilePath = path;
            passwordField.requestFocus();

        } catch (Exception e) {
            pathField.setText("");
        }
    }

    private String getPath() { //returns main file path
        return pathField.getText();
    }

    private char[] getPassword() {
        return passwordField.getPassword();
    }

    private void openKeepassDatabase() {
        String path = getPath();
        char[] password = getPassword();

        File f = new File(path);
        if (!(f.exists() && !f.isDirectory())) {
            JOptionPane.showMessageDialog(null, "You must enter the valid file!");
            return;
        }

        DatabaseUtils utils = new DatabaseUtils();

        try {
            utils.database = utils.open(path, password);

            PrintWriter writer = new PrintWriter(favouriteFilePath, "UTF-8");
            writer.println(path);
            writer.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            passwordField.setText("");
        }
    }
}
