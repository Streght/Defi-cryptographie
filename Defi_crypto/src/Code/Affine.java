package Code;

/**
 * The affine class is used to run the affine encryption of a letter.
 */
public class Affine {

    // Affine coefficient a.
    public static int m_iCoeffA;
    // Affine coefficient b.
    public static int m_iCoeffB;

    // Init affine encryption with keys value.
    public static void initAffine(int[] p_aiKeys) {
        m_iCoeffA = p_aiKeys[0];
        m_iCoeffB = p_aiKeys[1];
    }

    /**
     * Crypts the input message using a affine function.
     *
     * @param p_iLetter The letter to encrypt.
     * @return The encrypted letter.
     */
    public static int encryptAffine(int p_iLetter) {
        int iEncryptedLetter = 0;
        /* 
         * Then we put the ASCII code through the affine function, modulo 256 to go 
         * round the ASCII table
         */
        iEncryptedLetter = ((m_iCoeffA * p_iLetter + m_iCoeffB) % 256);
        return iEncryptedLetter;
    }
}
