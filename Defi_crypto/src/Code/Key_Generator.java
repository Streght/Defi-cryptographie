package Code;

public class Key_Generator {

	private static int[] S;
	private static int output_space_size;
	private static int cpt1;
	private static int cpt2;

	public static void initMask(int[] key) {

		S = new int[256];
		output_space_size = 256;
		cpt1 = 0;
		cpt2 = 0;
		int j = 0;

		for (int i = 0; i < output_space_size; i++) {
			S[i] = i;
		}

		for (int i = 0; i < output_space_size; i++) {
			j = (j + S[i] + key[i % key.length]) % output_space_size;
			
			int temp = S[i];
			S[i] = S[j];
			S[j] = temp;
		}
	}

	public static int generateMask() {

		cpt1 = (cpt1 + 1) % output_space_size;
		cpt2 = (cpt2 + S[cpt1]) % output_space_size;
		
		int temp = S[cpt1];
		S[cpt1] = S[cpt2];
		S[cpt2] = temp;

		return S[(S[cpt1] + S[cpt2]) % output_space_size];
	}

}
