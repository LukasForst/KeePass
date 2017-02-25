/*
 *  File name:  Start
 *  Date:       23.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass;

import cz.cvut.fel.keepass.ui.MainGUI;
import sun.applet.Main;

import javax.xml.crypto.Data;
import java.awt.*;

public class Start {
    public static void main(String[] args) {
        /*
        FileSelectionScreen s = new FileSelectionScreen();
        s.showWindow();
        */
        DatabaseUtils u = new DatabaseUtils();
        char[] pass = {'A', 'h', 'o', 'j', '1', '2', '3'};
        u.open("/home/lukas/GITS/test.kdbx",pass);


        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                /*SearchScreen ss = new SearchScreen(u);
                ss.showWindow();*/
                MainGUI m = new MainGUI();
            }
        });


    }
}
