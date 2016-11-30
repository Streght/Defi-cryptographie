package Code;

public class Affine {
	static public int coef_A = 0;
	static public int coef_B = 0;
	
	static public void init(int clef_A, int clef_B) {
		coef_A = clef_A;
		coef_B = clef_B;
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
    static public int CryptAffineMessage(int pLetter) {
        int encryptedLetter = 0;

        if ((pLetter >= 0) && (pLetter <= 255)) {
            /* Then we put the ASCII code through the affine function, modulo 256 to go 
             * round the ASCII table */
            encryptedLetter = ((coef_A * pLetter + coef_B) % 256);
        }            
        return encryptedLetter;
    }

    // --------------------------------
    // Function : Page2::UncryptAffineMessage
    // --------------------------------
    // <summary> Uncrypts the input message using a affine function </summary>
    // <param name"cryptMess"> Crypted message to uncrypt </param name"cryptMess"> 
    // <param name"a" and "b"> Coefficient of the affine function </param name"a" and "b"> 
    // <returns> A string, corresponding to the clear message </returns>
    // <remarks> Only upper case, points "." and spaces " " are valid for the clear message </remarks>
    // --------------------------------
    
	static public int UncryptAffineMessage(int pLetter) {

		int decryptedLetter = 0;
	
	    if ((pLetter >= 0) && (pLetter <= 255)) {
	        /* Then we put the ASCII code through the affine function, modulo 256 to go 
	         * round the ASCII table */
	    	 /* Then we solve the equation : x = a^1 * (c - b) mod 26 ; where "x" is the clear letter sought, 
             * "c" the letter in the crypted message, and a^1 the modular inverse of coef a. */
	    	decryptedLetter = (GetModularInverse(coef_A, 256) * (pLetter - coef_B)) % 256;
	    }            
	    return decryptedLetter;
	}
	
	// Finds the modular inverse "u" as "a*u equiv=> 1 mod b" 
	private static int GetModularInverse(int a, int b) {
        int PGCD = -1;
        int r = -1;

        int u_un = 0;
        int u_deux = 1;
        int v_un = 1;
        int v_deux = 0;
        int quotient, coef_u, coef_v = -1;

        while (b > 0)
        {
            quotient = a / b;
            r = a % b;
            coef_u = u_deux - quotient * u_un;
            coef_v = v_deux - quotient * v_un;
            a = b;
            b = r;
            u_deux = u_un;
            u_un = coef_u;
            v_deux = v_un;
            v_un = coef_v;
        }
        PGCD = a;
        coef_u = u_deux;
        coef_v = v_deux;

        // For some reasons, sometimes the result is negative, 
        // so we need to add the b (number of letter in the alphabet used)
        // to retrieve the first positive solution (see Wikipedia you fool)
        if (coef_u < 0) {
        	coef_u += 256;
        }
        return coef_u;
    }
}
