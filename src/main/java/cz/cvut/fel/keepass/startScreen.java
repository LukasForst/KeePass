/*
 *  File name:  appgui
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

    private String keepassDatabasePath = "";
    private char[] keepassPassword;

    private startScreen() {
        openFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(keepassDatabasePath.equals("")) {
                    keepassDatabasePath = pathField.getText();
                }

                File f = new File(keepassDatabasePath);
                if (!(f.exists() && !f.isDirectory())) {
                    JOptionPane.showMessageDialog(null, "You must enter the valid file!");
                    return;
                }

                keepassPassword = passwordField.getPassword();

                openKeepassDatabase(keepassDatabasePath, keepassPassword);
            }
        });

        findPathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showOpenDialog(null);

                keepassDatabasePath = jFileChooser.getSelectedFile().getAbsolutePath();

                pathField.setText(keepassDatabasePath);

                passwordField.requestFocus();
            }
        });
    }

    private void openKeepassDatabase(String path, char[] password) {
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
