package Code;

public class Vigenere {

    private static int[] key;
    private static int keyPosition;

    public static void initVigenere(int[] keyToUse) {
        key = keyToUse;
        keyPosition = 0;
    }

    public static int Encrypt(int message) {

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

    /*
    public static int Decrypt(int cryptogramme) {

        int result;

        if ((key[keyPosition] & 1) == 0) {
            // -
            result = (cryptogramme - key[keyPosition]);
            if (result < 0) {
                result += 256;
            }

        } else {
            // +
            result = (cryptogramme + key[keyPosition]) % 256;
        }

        keyPosition = (keyPosition + 1) % key.length;
        return result;

    }
     */
}
