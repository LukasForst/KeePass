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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class FileSelectionScreen {
    private JFrame frame;
    private JButton openFileButton;
    private JPanel panel1;
    private JLabel informationLabel;
    private JTextField pathField;
    private JPasswordField passwordField;
    private JButton findPathButton;

    private String favouriteFilePath = "favourite.txt";

    public FileSelectionScreen() {
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

        passwordField.addActionListener(new AbstractAction() { //opens database after Enter hit
            public void actionPerformed(ActionEvent actionEvent) {
                openKeepassDatabase();
            }
        });
        getLastPath();
    }

    public void showWindow() {
        frame = new JFrame("KeePass - Lukas Forst");
        frame.setContentPane(new FileSelectionScreen().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); //set location to the center of the screen
        frame.setVisible(true);
    }

    private void getLastPath() {
        try { //reading last
            String path;
            FileReader fileReader = new FileReader(favouriteFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            path = bufferedReader.readLine();
            bufferedReader.close();

            pathField.setText(path);
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
            return;
        }
        startSearchWindow(utils);
    }

    private void startSearchWindow(DatabaseUtils utils) {
        try {
            SearchScreen s = new SearchScreen(utils);
            s.showWindow();

            this.frame.setVisible(false); //TODO    not working yet
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
