package Code;

public class Caesar {

    private static int clef = 0;
    private static int directionOfShift = 1;

    public static void initCaesar(int pClef) {
        clef = pClef;
        if ((clef & 1) == 0) {
            directionOfShift = 1;
        } else {
            directionOfShift = -1;
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
}
