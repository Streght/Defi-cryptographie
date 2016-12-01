package Code;

import java.util.Map;

/**
 * Class used to run the Feistel diagram of the program.
 */
public class Feistel {

    /**
     * Execute a Feistel encryption step with a given encryption method on 2
     * letters.
     *
     * @param method The encryption method used for the Feistel step.
     * @param buffer The two letters buffer to encrypt.
     * @param generatorKey The key generated with the RC4 generator.
     * @return The encrypted two letters as an array.
     */
    private static int[] feistelCypher(String method, int[] buffer, int generatorKey) {

        int[] aiFeistelResult = new int[2];
        int iEncryptionResult = -1;

        if (method.equals("caesar")) {
            iEncryptionResult = Caesar.encryptCaesar(buffer[1]);
        }
        if (method.equals("affine")) {
            iEncryptionResult = Affine.encryptAffine(buffer[1]);
        }
        if (method.equals("vigenere")) {
            iEncryptionResult = Vigenere.encryptVigenere(buffer[1]);
        }

        // Apply the Feistel equations to encryption.
        aiFeistelResult[0] = (buffer[0] ^ iEncryptionResult) ^ generatorKey ^ buffer[1];
        aiFeistelResult[1] = buffer[0] ^ iEncryptionResult;

        return aiFeistelResult;
    }

    /**
     * Execute a Feistel decryption step with a given encryption method on 2
     * letters.
     *
     * @param method The encryption method used for the Feistel step.
     * @param buffer The two letters buffer to decrypt.
     * @param generatorKey The key generated with the RC4 generator.
     * @return The decrypted two letters as an array.
     */
    private static int[] feistelDecypher(String method, int[] buffer, int generatorKey) {

        int[] aiFeistelResult = new int[2];
        int iEncryptionResult = -1;

        if (method.equals("caesar")) {
            iEncryptionResult = Caesar.encryptCaesar(buffer[1] ^ buffer[0] ^ generatorKey);
        }
        if (method.equals("affine")) {
            iEncryptionResult = Affine.encryptAffine(buffer[1] ^ buffer[0] ^ generatorKey);
        }
        if (method.equals("vigenere")) {
            iEncryptionResult = Vigenere.encryptVigenere(buffer[1] ^ buffer[0] ^ generatorKey);
        }

        // Apply the Feistel equation to decryption.
        aiFeistelResult[0] = buffer[1] ^ iEncryptionResult;
        aiFeistelResult[1] = buffer[0] ^ generatorKey ^ buffer[1];

        return aiFeistelResult;
    }

    /**
     * Run the Feistel algorithm on a given dataset.
     *
     * @param p_mData The dataset to encrypt / decrypt.
     * @param p_sToDo What to do : encrypt or decrypt.
     * @return The encrypted / decrypted dataset.
     */
    public static int[] runFeistel(Map<String, int[]> p_mData, String p_sToDo) {
        if (p_mData != null && !p_mData.isEmpty()) {

            // The result buffer.
            int[] aiResultBuffer = new int[p_mData.get("message").length];

            // If we have to cipher
            if (p_sToDo.equals("cipher")) {
                // Init the different classes.
                Key_Generator.initMask(p_mData.get("generatorkey"));
                int[] aiGeneratorKey = Key_Generator.generateMask();
                Affine.initAffine(p_mData.get("affine"));
                Caesar.initCaesar(p_mData.get("caesar")[0]);
                Vigenere.initVigenere(p_mData.get("vigenere"));

                // Run the Feistel on each block of 2 letters.
                for (int i = 0; i < aiResultBuffer.length - 1; i += 2) {
                    int[] aiFeistelResult = {p_mData.get("message")[i], p_mData.get("message")[i + 1]};
                    aiFeistelResult = feistelCypher("caesar", aiFeistelResult, aiGeneratorKey[0]);
                    aiFeistelResult = feistelCypher("affine", aiFeistelResult, aiGeneratorKey[1]);
                    aiFeistelResult = feistelCypher("vigenere", aiFeistelResult, aiGeneratorKey[2]);

                    aiResultBuffer[i] = aiFeistelResult[0];
                    aiResultBuffer[i + 1] = aiFeistelResult[1];
                }
            }

            // If we have to decipher.
            if (p_sToDo.equals("decipher")) {
                // Init the different classes.
                Key_Generator.initMask(p_mData.get("generatorkey"));
                int[] aiGeneratorKey = Key_Generator.generateMask();
                Affine.initAffine(p_mData.get("affine"));
                Caesar.initCaesar(p_mData.get("caesar")[0]);
                Vigenere.initVigenere(p_mData.get("vigenere"));

                // Run the Feistel on each block of 2 letters.
                for (int i = 0; i < aiResultBuffer.length - 1; i += 2) {
                    int[] aiFeistelResult = {p_mData.get("message")[i], p_mData.get("message")[i + 1]};
                    aiFeistelResult = feistelDecypher("vigenere", aiFeistelResult, aiGeneratorKey[2]);
                    aiFeistelResult = feistelDecypher("affine", aiFeistelResult, aiGeneratorKey[1]);
                    aiFeistelResult = feistelDecypher("caesar", aiFeistelResult, aiGeneratorKey[0]);

                    aiResultBuffer[i] = aiFeistelResult[0];
                    aiResultBuffer[i + 1] = aiFeistelResult[1];
                }
            }

            return aiResultBuffer;
        }
        return null;
    }
}
