package Code;

public class Caesar {

    private static int clef = 0;
    private static int directionOfShift = 1;
    //private static int directionOfUncryptShift = 1;

    public static void initCaesar(int pClef) {
        clef = pClef;
        if ((clef & 1) == 0) {
            directionOfShift = 1;
            //directionOfUncryptShift = 1;
        } else {
            directionOfShift = -1;
            //directionOfUncryptShift = -1;
        }
    }

    // --------------------------------
    // Function : encryptCaesar
    // --------------------------------
    // <summary> Crypts the input message via an improved Caesar cipher </summary>
    // <param name"pLetter"> integer format of the letter to crypt </param name"pLetter"> 
    // <returns> An int, corresponding to the encrypted clear letter </returns>
    // --------------------------------
    public static int encryptCaesar(int pLetter) {
        int cryptedLetterASCII = 0;

        // Our crypted letter has the position of the clear letter + the key
        // The variable "directionOfShift" alternates from 1 to -1 each turn to change
        // the direction of the shifting process
        cryptedLetterASCII = pLetter + clef * directionOfShift;
        // If the resulting position is negative ...
        if (cryptedLetterASCII < 0) {
            // ... we go round the ASCII table ( position 0 minus 1 must yield 255 )
            cryptedLetterASCII = pLetter + 256;
        }
        // If the resulting position is greater than 255 ...
        if (cryptedLetterASCII > 255) {
            // ... we also go round the ASCII table. This time a simple modulo is sufficient
            cryptedLetterASCII %= 256;
        }
        // Changes the direction of the shifting process for the next call of the method
        directionOfShift *= -1;

        return cryptedLetterASCII;
    }

    // --------------------------------
    // Function : uncryptCaesar
    // --------------------------------
    // <summary> Uncrypts the input message via an improved Caesar cipher </summary>
    // <param name"pCryptedLetter"> integer format of the letter to uncrypt </param name"pCryptedLetter"> 
    // <returns> An int, corresponding to the decrypted clear letter </returns>
    // --------------------------------
    /*
    static public int uncryptCaesar(int pCryptedLetter) {
        int clearLetterASCII = 0;

        // We make sure the encrypted message is contained in the ASCII table
        if ((pCryptedLetter >= 0) && (pCryptedLetter <= 255)) {
            // Our clear letter has the position of the encrypted letter plus the key
            // The variable "directionOfShift" alternates from 1 to -1 each turn to change
            // the direction of the shifting process
            clearLetterASCII = pCryptedLetter - clef * directionOfUncryptShift;
            // If the resulting position is negative ...
            if (clearLetterASCII < 0) {
                // ... we go round the ASCII table ( position 0 minus 1 must yield 255 )
                clearLetterASCII = pCryptedLetter + (256 - (Math.abs(clef) % 256));
            }
            // If the resulting position is greater than 255 ...
            if (clearLetterASCII > 255) {
                // ... we also go round the ASCII table. This time a simple modulo is sufficient
                clearLetterASCII %= 256;
            }
            // Changes the direction of the shifting process for the next call of the method
            directionOfUncryptShift *= -1;
        }
        return clearLetterASCII;
    }
    */
}
