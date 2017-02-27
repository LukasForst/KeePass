/*
 *  File name:  FileSelectionPanel
 *  Date:       25.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass.ui
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.ui;

import cz.cvut.fel.keepass.DatabaseUtils;
import de.slackspace.openkeepass.domain.KeePassFile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class FileSelectionPanel {
    public JPanel panel;

    JPasswordField passwordField;
    JButton openFileButton;

    private JTextField pathField;
    private JLabel informationLabel;
    private JButton findPathButton;

    private String favouriteFilePath = "fav";   //path where is stored favourite file path

    public FileSelectionPanel() {
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
        try { //reading last used path to the kdbx file
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

    KeePassFile openKeepassDatabase() {
        String path = getPath();
        char[] password = getPassword();

        File f = new File(path);
        if (!(f.exists() && !f.isDirectory())) {
            JOptionPane.showMessageDialog(null, "You must enter the valid file!");
            return null;
        }

        DatabaseUtils utils = new DatabaseUtils();

        KeePassFile file = null;

        try {
            file = utils.open(path, password);

            PrintWriter writer = new PrintWriter(favouriteFilePath, "UTF-8");
            writer.println(path);
            writer.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            passwordField.setText("");
        }

        return file;
    }
}
