/*
 (C) COPYRIGHT 2016 TECHNOLUTION BV, GOUDA NL
| =======          I                   ==          I    =
|    I             I                    I          I
|    I   ===   === I ===  I ===   ===   I  I    I ====  I   ===  I ===
|    I  /   \ I    I/   I I/   I I   I  I  I    I  I    I  I   I I/   I
|    I  ===== I    I    I I    I I   I  I  I    I  I    I  I   I I    I
|    I  \     I    I    I I    I I   I  I  I   /I  \    I  I   I I    I
|    I   ===   === I    I I    I  ===  ===  === I   ==  I   ===  I    I
|                 +---------------------------------------------------+
+----+            |  +++++++++++++++++++++++++++++++++++++++++++++++++|
     |            |             ++++++++++++++++++++++++++++++++++++++|
     +------------+                          +++++++++++++++++++++++++|
                                                        ++++++++++++++|
                                                                 +++++|
 */
package nl.technolution.wvp.common;

import org.slf4j.Logger;

import nl.technolution.core.Log;

/**
 * Provides database utilities
 */
public final class DatabaseUtil {

    private static final Logger LOG = Log.getLogger();

    private DatabaseUtil() {
        // Utility class
    }

    /**
     * Converts a database identifier to a String.
     */
    public static String stringFromId(int id) {
        return Integer.toString(id);
    }

    /**
     * Converts a String to a database identifier.
     */
    public static int idFromString(String id) {
        if (id == null || id.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            LOG.warn("Could not parse id to an int", e);
            return 0;
        }
    }
    
}
