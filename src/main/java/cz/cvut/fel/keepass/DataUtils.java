/*
 *  File name:  DataUtils
 *  Date:       4.3.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass;

import de.slackspace.openkeepass.domain.KeePassFile;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;

public class DataUtils {
    private static KeePassFile database = null;

    public static KeePassFile getDatabase() {
        return database;
    }

    public static void setDatabase(KeePassFile database) {
        DataUtils.database = database;
    }

    public static String[][] deepCopy(String[][] source) {
        String[][] target = new String[source.length][];
        for (int i = 0; i < source.length; i++) {
            target[i] = Arrays.copyOf(source[i], source[i].length);
        }

        return target;
    }

    public static void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        clip.setContents(stringSelection, null);
    }

    public static void hidePasswords(String[][] data, int arrayIndex) { // use * for hiding passwords
        for (int i = 0; i <= arrayIndex; i++) {
            String format = "";
            for (int u = 0; u < data[i][2].length(); u++) {
                format += "*";
            }
            data[i][2] = format;
        }
    }
}
