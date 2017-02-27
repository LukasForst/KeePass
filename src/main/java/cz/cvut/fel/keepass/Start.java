/*
 *  File name:  Start
 *  Date:       23.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass;

import cz.cvut.fel.keepass.ui.MainGUI;

import java.awt.*;

public class Start {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MainGUI();
            }
        });


    }
}
