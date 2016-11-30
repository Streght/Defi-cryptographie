package Code;

public class Vigenere {

    private static int[] key;
    private static int keyPosition;

    public static void initVigenere(int[] keyToUse) {
        key = keyToUse;
        keyPosition = 0;
    }

    public static int encryptVigenere(int message) {

        int result;

        if ((key[keyPosition] & 1) == 0) {
            // +
            result = (message + key[keyPosition]) % 256;

        } else {
            // -
            result = (message - key[keyPosition]);
            if (result < 0) {
                result += 256;
            }
        }

        keyPosition = (keyPosition + 1) % key.length;
        return result;
    }
}
