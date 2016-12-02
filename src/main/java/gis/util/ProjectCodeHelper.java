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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

/**
 * Helper class for handling project codes.
 * 
 * Project codes are made of digits and uppercase letters. 
 * The project code must contain a at least one digit greater than zero and at least one uppercase letter.
 */
public final class ProjectCodeHelper {
    private static final Pattern PROJECT_CODE_PATTERN = Pattern.compile("([0-9]+)([A-Z]+)");
    private static final int LETTERS_IN_ALPHABET = 26;
    
    private ProjectCodeHelper() {
        // Private constructor for utility class.
    }
    
    /**
     * Match the project code with the pattern
     * @param projectCode A String value
     * @return true if the project code is of the correct format.
     */
    public static boolean matches(String projectCode) {
        Matcher m = PROJECT_CODE_PATTERN.matcher(projectCode);
        return m.matches();
    }
    
    /**
     * Strip the organisation code from the project code.
     * 
     * @param projectCode The project code containing the organisation code.
     * @return A Hwbp code
     * @throws IllegalArgumentException if the project code is invalid.
     */
    public static Integer getOrganisationHwbpCode(String projectCode) throws IllegalArgumentException {
        Matcher m = PROJECT_CODE_PATTERN.matcher(projectCode);
        
        Preconditions.checkArgument(m.matches(), "Project code %s is invalid", projectCode);
        
        String organisation = m.group(1);
        
        return Integer.parseInt(organisation);
    }
    
    /**
     * Strip the sequence letters from the project code.
     *  
     * @param projectCode The project code containing the sequence letters.
     * @return A string containing the sequence letters.
     * @throws IllegalArgumentException if the project code is invalid.
     */
    public static String getSequenceLetters(String projectCode) throws IllegalArgumentException {
        Matcher m = PROJECT_CODE_PATTERN.matcher(projectCode);
        
        Preconditions.checkArgument(m.matches(), "Project code %s is invalid", projectCode);
        
        return m.group(2);
    }

    /**
     * Strip the sequence letters from the project code and convert it to a number (A=1, B=2, ..., AA=27, AB=28, ...).
     *  
     * @param projectCode The project code containing the sequence letters.
     * @return An integer representing the sequence number.
     * @throws IllegalArgumentException if the project code is invalid.
     */
    public static int getSequenceAsNumber(String projectCode) throws IllegalArgumentException {
        String sequenceLetters = getSequenceLetters(projectCode);
        
        return getSequenceNumber(sequenceLetters);
    }

    private static int getSequenceNumber(String sequenceLetters) {
        int length = sequenceLetters.length();
        
        if (length == 1) {
            return getSequenceNumber(sequenceLetters.charAt(0));
        } else {
            return LETTERS_IN_ALPHABET * getSequenceNumber(sequenceLetters.substring(0, length - 1)) + 
                    getSequenceNumber(sequenceLetters.charAt(length - 1));
        }
        
    }

    private static int getSequenceNumber(char sequenceLetter) {
        return sequenceLetter - 'A' + 1;
    }

    /**
     * Constructs a project code from the given organisation code and sequence number.
     * 
     * @param organisationHwpbCode HWBP organisation code to use. 
     * @param sequenceNumber Sequence number to use.
     * @return The project code.
     */
    public static String buildProjectCode(int organisationHwpbCode, int sequenceNumber) {
        Preconditions.checkArgument(organisationHwpbCode > 0, "Organisation code needs to be at least 1");
        Preconditions.checkArgument(sequenceNumber > 0, "Sequence number needs to be at least 1");
        
        return String.format("%02d", organisationHwpbCode) + toSequenceLetters(sequenceNumber);
    }

    /**
     * Creates an alphabetic sequence from the given sequence number.
     *  
     * @param sequenceNumber The sequence to convert.
     * @return The corresponding letter sequence.
     */
    private static String toSequenceLetters(int sequenceNumber) {
        Preconditions.checkArgument(sequenceNumber > 0, "Sequence number needs to be at least 1");
        
        // Take into account that we have a 1-based sequence. 
        int subtractedSequenceNumber = sequenceNumber - 1;
        if (subtractedSequenceNumber < LETTERS_IN_ALPHABET) {
            // Convert only the last letter if the code represents one character.
            return Character.toString(getLastSequenceLetter(subtractedSequenceNumber));
        } else {
            // Otherwise, convert all sequence letters except the last one and then the last one itself.
            // Note that we need to use the subtracted sequence number here as well to translate each letter correctly.
            // 
            // Example: sequence number 52 (AZ) results in toSequenceLetters(51/26) + getLastSequenceLetter(51) 
            // = toSequenceLetters(1) + getLastSequenceLetter(51)
            // = "A" + "Z"
            // This would have been "BZ" if we would use the original sequence number.
            return toSequenceLetters(subtractedSequenceNumber / LETTERS_IN_ALPHABET) + 
                   getLastSequenceLetter(subtractedSequenceNumber);
        }
    }

    private static char getLastSequenceLetter(int zeroBasedSequenceNumber) {
        // Obtain the sequence number for the last letter
        int lastLetterZeroBasedsequenceNumber = zeroBasedSequenceNumber % LETTERS_IN_ALPHABET;
        
        return (char)('A' + lastLetterZeroBasedsequenceNumber);
    }
}
