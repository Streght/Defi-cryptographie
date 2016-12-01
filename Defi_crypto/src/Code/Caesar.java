package Code;

/**
 * The Caesar class is used to run the Caesar encryption of a letter
 */
public class Caesar {

    // Caesar key.
    private static int m_iCaesarKey = 0;
    // Direction of shift, changed for each letter.
    private static int m_iDirectionOfShift = 1;

    /**
     * Initialize Caesar key with given value.
     *
     * @param p_iCaesarKey The given Caesar key.
     */
    public static void initCaesar(int p_iCaesarKey) {
        m_iCaesarKey = p_iCaesarKey;
        if ((m_iCaesarKey & 1) == 0) {
            m_iDirectionOfShift = 1;
        } else {
            m_iDirectionOfShift = -1;
        }
    }

    /**
     * Crypts the input message via an improved Caesar cipher.
     *
     * @param p_iLetter Integer format of the letter to crypt.
     * @return An int, corresponding to the encrypted clear letter.
     */
    public static int encryptCaesar(int p_iLetter) {
        int iCryptedLetterASCII = 0;

        /*
         * Our crypted letter has the position of the clear letter + the key
         * The variable "directionOfShift" alternates from 1 to -1 each turn to change
         * the direction of the shifting process.
         */
        iCryptedLetterASCII = p_iLetter + m_iCaesarKey * m_iDirectionOfShift;
        // If the resulting position is negative ...
        while (iCryptedLetterASCII < 0) {
            // ... we add 256 until it's positive.
            iCryptedLetterASCII += 256;
        }
        // If the resulting position is greater than 255 ...
        if (iCryptedLetterASCII > 255) {
            // ... we go round the ASCII table. This time a simple modulo is sufficient.
            iCryptedLetterASCII %= 256;
        }
        // Changes the direction of the shifting process for the next call of the method.
        m_iDirectionOfShift *= -1;

        return iCryptedLetterASCII;
    }
}
