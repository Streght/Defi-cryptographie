package Code;

public class Affine {

    static public int coef_A = 0;
    static public int coef_B = 0;

    static public void initAffine(int[] key) {
        coef_A = key[0];
        coef_B = key[1];
    }

    // --------------------------------
    // Function : Page2::CryptAffineMessage
    // --------------------------------
    // <summary> Crypts the input message using a affine function </summary>
    // <param name"clearMess"> Clear message to crypt </param name"clearMess"> 
    // <param name"a" and "b"> Coefficient of the affine function </param name"a" and "b"> 
    // <returns> A string, corresponding to the encrypted message </returns>
    // <remarks> Only upper case, points "." and spaces " " are valid for the clear message </remarks>
    // --------------------------------
    static public int encryptAffine(int pLetter) {
        int encryptedLetter = 0;

        /* 
         * Then we put the ASCII code through the affine function, modulo 256 to go 
         * round the ASCII table
         */
        encryptedLetter = ((coef_A * pLetter + coef_B) % 256);
        return encryptedLetter;
    }
}
