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

import java.io.StringWriter;

/**
 * Outputs comma separated value formatted data 
 */
public final class CSVOutput {
    
    private final String separator;
    private final String newline;
    private final StringWriter writer = new StringWriter();

    public CSVOutput(String separator, String newline) {
        this.separator = separator;
        this.newline = newline;
    }
    
    /**
     * Add new row to the CSV output
     * @param fields
     */
    public void addRow(String... fields) {
        if (fields.length > 0) {
            for (int i = 0; i < fields.length - 1; i++) {
                writer.write(fields[i]);
                writer.write(separator);
            }
            writer.write(fields[fields.length - 1]);
        }
        writer.write(newline);
    }
    
    @Override
    public String toString() {
        return writer.toString();
    }
    
}
