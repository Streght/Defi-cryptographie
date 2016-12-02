package Code;

import java.util.Map;
import javax.swing.JOptionPane;

/**
 * The main class is the class run at the program execution.
 */
public class Main {

    /**
     * Program main.
     *
     * @param asArgs Starting arguments : request type (-enc/-dec), inputFile,
     * outPutFile.
     * @throws InterruptedException
     */
    public static void main(String[] asArgs) throws InterruptedException {

        try {
            // If we start an encryption.
            if (asArgs[0].equals("-enc")) {
                // Read the given file.
                Map<String, int[]> buffer = File_Interaction.readFileMessage(asArgs[1] + ".txt");
                // Encrypt it.
                int[] aiCipherResult = Feistel.runFeistel(buffer, "cipher");
                // Return encryption result in outputFile.
                File_Interaction.writeCryptogramOutput(aiCipherResult, asArgs[2]);
            }

            // If we start a decryption.
            if (asArgs[0].equals("-dec")) {
                // Read the given file.
                Map<String, int[]> buffer2 = File_Interaction.readFileMessage(asArgs[1] + ".enc");
                // Decrypt it.
                int[] aiDecipherResult = Feistel.runFeistel(buffer2, "decipher");
                // Return decryption result in outputFile.
                File_Interaction.writeUncryptedMessageOutput(aiDecipherResult, asArgs[2]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null,
                    "Please do not enter empty names for file names",
                    e.toString(),
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
