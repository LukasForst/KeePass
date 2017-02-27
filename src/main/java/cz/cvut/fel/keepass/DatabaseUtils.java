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
    private KeePassFile database;

    public DatabaseUtils(KeePassFile dat) {
        database = dat;
    }

    public KeePassFile open(String path, char[] password) {
        KeePassFile dat = KeePassDatabase.getInstance(path).openDatabase(new String(password));
        database = dat;
        return dat;
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
}
