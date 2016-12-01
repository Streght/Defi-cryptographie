package Code;

/**
 * The Vigenere class is used to run a Vigenere encryption.
 */
public class Vigenere {

    // Vigenere key.
    private static int[] m_aiKey;
    // Position in the key.
    private static int m_iKeyPosition;

    /**
     * Initialize Vigenere key value.
     *
     * @param p_aiKeyToUse The key value.
     */
    public static void initVigenere(int[] p_aiKeyToUse) {
        m_aiKey = p_aiKeyToUse;
        m_iKeyPosition = 0;
    }

    /**
     * Run the Vigenere encryption on the given letter.
     *
     * @param p_iMessage The letter to encrypt as an int.
     * @return The encrypted letter.
     */
    public static int encryptVigenere(int p_iMessage) {

        int iResult;

        if ((m_aiKey[m_iKeyPosition] & 1) == 0) {
            // +
            iResult = (p_iMessage + m_aiKey[m_iKeyPosition]) % 256;

        } else {
            // -
            iResult = (p_iMessage - m_aiKey[m_iKeyPosition]);
            if (iResult < 0) {
                iResult += 256;
            }
        }

        m_iKeyPosition = (m_iKeyPosition + 1) % m_aiKey.length;
        return iResult;
    }
}
