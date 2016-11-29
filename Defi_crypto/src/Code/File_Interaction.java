package Code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class File_Interaction {

	private static Map<String, int[]> input = null;
	private static String keysAsString;

	public static Map<String, int[]> readFileMessage(String path) {

		BufferedReader br = null;

		try {
			input = new HashMap<>();
			br = new BufferedReader(new FileReader(path));

			// Read the first line.
			keysAsString = br.readLine();

			// Isolate each key.
			String[] keys = keysAsString.split(",");
			// Add the Cesar key to the map.
			input.put("cesar", new int[] { Integer.parseInt(keys[0]) });
			// Add the affine keys to the map.
			input.put("affine", new int[] { Integer.parseInt(keys[1]), Integer.parseInt(keys[2]) });

			// Add the Vigenere key converted to int[] to the map
			byte[] vigenereKeyRead = keys[3].getBytes();
			int[] vigenereKey = new int[vigenereKeyRead.length];
			for (byte i : vigenereKeyRead) {
				vigenereKey[i] = vigenereKeyRead[i];
			}
			input.put("vigenere", vigenereKey);

			// Add the generator key converted to int[] to the map
			String temp = keys[0] + keys[3] + keys[1] + keys[2];
			byte[] generatorKeyRead = temp.getBytes();
			int[] generatorKey = new int[generatorKeyRead.length];
			for (byte i : generatorKeyRead) {
				generatorKey[i] = generatorKeyRead[i];
			}
			input.put("generatorkey", generatorKey);

			byte[] SecondLine = br.readLine().getBytes();
			byte[] messageRead = null;

			// Bourrage si la longueur du message n'est pas paire.
			if ((SecondLine.length & 1) != 0) {
				messageRead = new byte[SecondLine.length + 1];
				System.arraycopy(SecondLine, 0, messageRead, 0, SecondLine.length);
				messageRead[SecondLine.length] = 0;
			} else {
				messageRead = SecondLine;
			}

			int[] message = new int[messageRead.length];
			for (byte i : messageRead) {
				message[i] = messageRead[i];
			}
			input.put("message", message);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return input;
	}

	public static void writeCryptogramOutput(int[] buffer) {

		try {
			PrintWriter writer = new PrintWriter("cryptogramme.txt", "UTF-8");
			writer.println(keysAsString);

			for (int i = 0; i < buffer.length; i++) {
				writer.print(Integer.toHexString(buffer[i]));
			}

			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeUncryptedMessageOutput(int[] buffer) {

		try {
			byte[] temp = new byte[buffer.length];

			for (int i = 0; i < buffer.length; i++) {
				temp[i] = (byte) buffer[i];
			}

			PrintWriter writer = new PrintWriter("message_uncrypted.txt", "UTF-8");
			writer.println(keysAsString);
			writer.print(new String(temp, "UTF-8"));
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
