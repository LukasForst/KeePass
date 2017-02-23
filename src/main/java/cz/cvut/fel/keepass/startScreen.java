/*
 *  File name:  startScreen.java
 *  Date:       23.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class startScreen {
    private JButton openFileButton;
    private JPanel panel1;
    private JLabel informationLabel;
    private JTextField pathField;
    private JPasswordField passwordField;
    private JButton findPathButton;

    private startScreen() {
        openFileButton.addActionListener(new ActionListener() { //opens whole keepass database
            public void actionPerformed(ActionEvent actionEvent) {
                openKeepassDatabase();
            }
        });

        findPathButton.addActionListener(new ActionListener() { //opens jFileChooser
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showOpenDialog(null);

                pathField.setText(jFileChooser.getSelectedFile().getAbsolutePath()); //writes path to the field

                passwordField.requestFocus();
            }
        });

        passwordField.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private String getPath() { //returns main file path
        String path = pathField.getText();

        File f = new File(path);
        if (!(f.exists() && !f.isDirectory())) {
            JOptionPane.showMessageDialog(null, "You must enter the valid file!");
        }
        return path;
    }

    private char[] getPassword() {
        return passwordField.getPassword();
    }


    private void openKeepassDatabase() {
        String path = getPath();
        char[] password = getPassword();

        DatabaseUtils utils = new DatabaseUtils();
        try {
            utils.printDatabase(path, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("KeePass - Lukas Forst");
        frame.setContentPane(new startScreen().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setLocationRelativeTo(null); //set location to the center of the screen
        frame.setVisible(true);

    }
}
