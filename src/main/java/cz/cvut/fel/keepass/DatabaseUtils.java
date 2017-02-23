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

    public KeePassFile open(String path, char[] password) {
        return KeePassDatabase.getInstance(path).openDatabase(new String(password));
    }

    public List<Group> getGroup(KeePassFile database) {
        return database.getTopGroups();
    }

    public List<Entry> getEntries(KeePassFile database) {
        return database.getEntries();
    }


    public void printDatabase(String path, char[] password) {
        KeePassFile database = open(path, password);
        List<Entry> entries = getEntries(database);
        List<Group> groups = getGroup(database);

        for (Entry entry : entries) {
            System.out.println("Title: " + entry.getTitle() + " Password: " + entry.getPassword());
        }

        for (Group group : groups) {
            System.out.println(group.getName());
        }
    }
}
