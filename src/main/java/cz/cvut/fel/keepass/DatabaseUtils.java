/*
 *  File name:  printDatabase
 *  Date:       23.2.17
 *  Author:     Lukas Forst
 *  Package:    cz.cvut.fel.keepass
 *  Project:    KeePass
 */

package cz.cvut.fel.keepass;
import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.Group;
import de.slackspace.openkeepass.domain.KeePassFile;

import java.util.List;


public class DatabaseUtils {
    public KeePassFile database;

    public KeePassFile open(String path, char[] password) {
        database = KeePassDatabase.getInstance(path).openDatabase(new String(password));
        return database;
    }

    public List<Group> getGroups() {
        return database.getTopGroups();
    }

    public List<Entry> getEntries() {
        return database.getEntries();
    }

    public List<Entry> searchEntry(String searchPhrase) {
        return database.getEntriesByTitle(searchPhrase, false);
    }

    public void printDatabase(String path, char[] password) {
        database = open(path, password);

        List<Entry> entries = getEntries();
        List<Group> groups = getGroups();

        for (Entry entry : entries) {
            System.out.println("Title: " + entry.getTitle() + " Password: " + entry.getPassword());
        }

        for (Group group : groups) {
            System.out.println(group.getName());
        }
    }
}
