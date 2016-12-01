package Code;

/**
 * The key generator class is the class used to simulate the RC4 algorithm.
 */
public class Key_Generator {

    // The buffer.
    private static int[] m_aiBuffer;
    // The buffer size.
    private static final int OUTPUT_SPACE_SIZE = 256;
    // The first counter.
    private static int m_iCpt1;
    // The second counter.
    private static int m_iCpt2;

    /**
     * We initialize the buffer state with the key.
     *
     * @param p_aiKey The key used to initialize.
     */
    public static void initMask(int[] p_aiKey) {

        m_aiBuffer = new int[OUTPUT_SPACE_SIZE];
        m_iCpt1 = 0;
        m_iCpt2 = 0;
        int j = 0;

        for (int i = 0; i < OUTPUT_SPACE_SIZE; i++) {
            m_aiBuffer[i] = i;
        }

        // Scramble the buffer with the key value.
        for (int i = 0; i < OUTPUT_SPACE_SIZE; i++) {
            j = (j + m_aiBuffer[i] + p_aiKey[i % p_aiKey.length]) % OUTPUT_SPACE_SIZE;

            int iPermutationTemp = m_aiBuffer[i];
            m_aiBuffer[i] = m_aiBuffer[j];
            m_aiBuffer[j] = iPermutationTemp;
        }
    }

    /**
     * Generate 3 key used for encryption / decryption.
     *
     * @return A int[3] with 3 pseudo-random-generated key.
     */
    public static int[] generateMask() {

        int[] aiResult = new int[3];

        for (int i = 0; i < 3; i++) {

            m_iCpt1 = (m_iCpt1 + 1) % OUTPUT_SPACE_SIZE;
            m_iCpt2 = (m_iCpt2 + m_aiBuffer[m_iCpt1]) % OUTPUT_SPACE_SIZE;

            int iPermutationTemp = m_aiBuffer[m_iCpt1];
            m_aiBuffer[m_iCpt1] = m_aiBuffer[m_iCpt2];
            m_aiBuffer[m_iCpt2] = iPermutationTemp;

            aiResult[i] = m_aiBuffer[(m_aiBuffer[m_iCpt1] + m_aiBuffer[m_iCpt2]) % OUTPUT_SPACE_SIZE];
        }
        return aiResult;
    }
}
