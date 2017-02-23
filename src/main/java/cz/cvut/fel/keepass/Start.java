/*
 *  File name:  Start
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

public class Start {
    public static void main(String[] args) {
        KeePassFile database = KeePassDatabase.getInstance("/home/lukas/GITS/test.kdbx").openDatabase("Ahoj123");
        List<Entry> entries = database.getEntries();

        for (Entry entry : entries) {
            System.out.println("Title: " + entry.getTitle() + " Password: " + entry.getPassword());
        }

        List<Group> groups = database.getTopGroups();
        for (Group group : groups) {
            System.out.println(group.getName());
        }
    }
}
