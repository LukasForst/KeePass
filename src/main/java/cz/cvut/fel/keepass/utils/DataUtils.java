/*
 *  File name:  DataUtils
 *  Date:       4.3.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass.utils;

import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.KeePassFile;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class DataUtils {
    private static final String storedFavouritePath = "path";   //path where is stored favourite file path
    private static final boolean binaryReadWrite = true;
    private static KeePassFile database = null;
    private static String currentDatabasePath = "";

    public static String getCurrentDatabasePath() {
        if (currentDatabasePath.equals("")) {
            if (loadLastPath() != null) {
                currentDatabasePath = loadLastPath();
            }
        }
        return currentDatabasePath;
    }

    public static void setCurrentDatabasePath(String currentDatabasePath) {
        DataUtils.currentDatabasePath = currentDatabasePath;
    }

    public static KeePassFile getDatabase() {
        return database;
    }

    public static void setDatabase(KeePassFile database) {
        DataUtils.database = database;
    }

    public static KeePassFile openDatabaseFromFile(String path, char[] pass) {
        String password = new String(pass);
        File f = new File(path);
        if (!(f.exists() && !f.isDirectory())) {
            JOptionPane.showMessageDialog(null, "You must enter the valid file!");
            return null;
        }

        try {
            KeePassFile dat = KeePassDatabase.getInstance(path).openDatabase(password);
            DataUtils.database = dat;
            return dat;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static void saveLastPath(String text) {
        if (binaryReadWrite) {
            try {
                Path path = Paths.get(storedFavouritePath);
                Files.write(path, text.getBytes());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            try { //reading last used path to the kdbx file
                PrintWriter writer = new PrintWriter(storedFavouritePath, "UTF-8");
                writer.print(text);
                writer.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static String loadLastPath() {
        if (binaryReadWrite) {
            Path path = Paths.get(storedFavouritePath);
            try {
                return new String(Files.readAllBytes(path));
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return null;
            }
        } else {
            try { //reading last used path to the kdbx file
                String path;
                BufferedReader bufferedReader = new BufferedReader(new FileReader(storedFavouritePath));
                path = bufferedReader.readLine();
                bufferedReader.close();
                return path;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static String[][] deepCopy(String[][] source) {
        /**@returns copied string array */

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
