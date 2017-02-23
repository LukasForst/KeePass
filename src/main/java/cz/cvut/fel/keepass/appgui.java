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

public class appgui {
    private JButton button1;
    private JPanel panel1;
    private JLabel informationLabel;

    private appgui() {
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "Hello");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App Gui");
        frame.setContentPane(new appgui().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
