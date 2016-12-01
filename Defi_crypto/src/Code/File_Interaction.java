package Code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * Class used to handle interaction with file.
 */
public class File_Interaction {

    // Map used to store read data.
    private static Map<String, int[]> m_mInput = null;
    // String used to store the keys.
    private static String m_sKeysAsString;

    /**
     * Check if the affine coefficients are correct i.e. GCD(a,b) = 0 and a > 0.
     *
     * @param p_aiAffineCoeff An array with the 2 affine coeff to check.
     * @return True if the 2 coefficients are correct, false otherwise.
     */
    public static boolean checkAffine(int[] p_aiAffineCoeff) {

        BigInteger b1 = BigInteger.valueOf(p_aiAffineCoeff[0]);
        BigInteger b2 = BigInteger.valueOf(256);
        BigInteger gcd = b1.gcd(b2);

        return gcd.equals(BigInteger.valueOf(1)) && p_aiAffineCoeff[0] > 0;
    }

    /**
     * Read the .txt / .enc file at the path given and return a map containing
     * all the file informations.
     *
     * @param p_sPath The file path.
     * @return A map containing the file information : key + message.
     */
    public static Map<String, int[]> readFileMessage(String p_sPath) {

        BufferedReader br = null;

        try {
            m_mInput = new HashMap<>();
            br = new BufferedReader(new FileReader(p_sPath));

            // Read the first line.
            m_sKeysAsString = br.readLine();
            if (m_sKeysAsString != null) {

                // Isolate each key.
                m_sKeysAsString = m_sKeysAsString.replace(" ", "");
                String[] asKeys = m_sKeysAsString.split(",");

                try {
                    // Add the Cesar key to the map.
                    m_mInput.put("caesar", new int[]{Integer.parseInt(asKeys[0])});
                    // Add the affine keys to the map.
                    int[] aiAffineCoeff = {Integer.parseInt(asKeys[1]), Integer.parseInt(asKeys[2])};

                    if (checkAffine(aiAffineCoeff)) {
                        m_mInput.put("affine", aiAffineCoeff);
                    } else {
                        // Dectect if the affine coefficients aren't correct.
                        JOptionPane.showMessageDialog(null,
                                "Please check the affine encryption coefficients (a>0 and 0<b<256)",
                                "Wrong affine coefficients",
                                JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                } catch (NumberFormatException e) {
                    // Dectect if the numbers are to big or contains letters.
                    JOptionPane.showMessageDialog(null,
                            "Please check you correctly entered the keys value at the beginning of the file",
                            "Incorrect or missing keys",
                            JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                // Add the Vigenere key converted to int[] to the map
                byte[] abVigenereKeyRead = asKeys[3].getBytes("UTF-8");
                int[] aiVigenereKey = new int[abVigenereKeyRead.length];
                for (int i = 0; i < abVigenereKeyRead.length; i++) {
                    aiVigenereKey[i] = abVigenereKeyRead[i];
                }
                m_mInput.put("vigenere", aiVigenereKey);

                // Add the generator key converted to int[] to the map
                String temp = asKeys[0] + asKeys[3] + asKeys[1] + asKeys[2];
                byte[] abGeneratorKeyRead = temp.getBytes("UTF-8");
                int[] aiGeneratorKey = new int[abGeneratorKeyRead.length];
                for (int i = 0; i < abGeneratorKeyRead.length; i++) {
                    aiGeneratorKey[i] = abGeneratorKeyRead[i];
                }
                m_mInput.put("generatorkey", aiGeneratorKey);

                // If the file is an uncrypted file.
                if (p_sPath.contains(".txt")) {

                    // Read the message.
                    String sStringRead = "";
                    String sCurrentLine;
                    while ((sCurrentLine = br.readLine()) != null) {
                        sStringRead += sCurrentLine + "\r\n";
                    }

                    // get byte with UTF-8 encoding.
                    byte[] abLine = sStringRead.getBytes("UTF-8");
                    byte[] abMessageRead;

                    // Add the null caracter if the string length isn't even.
                    if ((abLine.length & 1) != 0) {
                        abMessageRead = new byte[abLine.length + 1];
                        System.arraycopy(abLine, 0, abMessageRead, 0, abLine.length);
                        abMessageRead[abLine.length] = 0;
                    } else {
                        abMessageRead = abLine;
                    }

                    // Takes care of the extended ASCII caracters.
                    int[] aiMessage = new int[abMessageRead.length];
                    for (int i = 0; i < abMessageRead.length; i++) {
                        aiMessage[i] = abMessageRead[i] & 0xFF;
                    }
                    m_mInput.put("message", aiMessage);
                }

                // If the file is an encrypted file.
                if (p_sPath.contains(".enc")) {

                    String sLine = "";
                    String sCurrentLine;
                    while ((sCurrentLine = br.readLine()) != null) {
                        // Remove unwanted spaces.
                        sCurrentLine = sCurrentLine.replace(" ", "");
                        sLine = sLine + sCurrentLine;
                    }

                    // Split the message every 2 hexadecimal character.
                    String[] asMessageSplit = sLine.split("(?<=\\G..)");

                    int[] aiCryptogram = new int[asMessageSplit.length];

                    for (int i = 0; i < asMessageSplit.length; i++) {
                        aiCryptogram[i] = Integer.parseInt(asMessageSplit[i], 16);
                    }

                    m_mInput.put("message", aiCryptogram);
                }
            } else {
                throw new IOException();
            }

        } catch (IOException e) {
            // Detect if the filename isn't correct.
            JOptionPane.showMessageDialog(null,
                    "Please check the imported file (incorrect path or missing content)",
                    "Import file problem",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }

        return m_mInput;
    }

    /**
     * Write the cryptogram output in hexadecimal base.
     *
     * @param p_aiDataBuffer The data buffer to write.
     * @param p_sOutputFileName The output file name.
     */
    public static void writeCryptogramOutput(int[] p_aiDataBuffer, String p_sOutputFileName) {

        if (p_aiDataBuffer != null) {
            PrintWriter prWriter;

            try {
                prWriter = new PrintWriter(p_sOutputFileName + ".enc", "UTF-8");

                // Write as hexadecimal numbers, 8 blocks of 2 letters per line.
                // Each 2 letters is separated with a space.
                int iIndexLine = 0;
                int iIndexBloc = 0;
                for (int i = 0; i < p_aiDataBuffer.length; i++) {
                    if (p_aiDataBuffer[i] < 16) {
                        prWriter.print("0");
                    }
                    // Write as hexadecimal numbers.
                    prWriter.print(Integer.toHexString(p_aiDataBuffer[i]));
                    iIndexLine++;
                    iIndexBloc++;
                    if (iIndexBloc == 2) {
                        prWriter.print(" ");
                        iIndexBloc = 0;
                    }
                    if (iIndexLine == 16) {
                        prWriter.print("\r\n");
                        iIndexLine = 0;
                    }
                }

                prWriter.close();

            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }
    }

    /**
     * Write the uncrypted text out.
     *
     * @param p_aiDataBuffer The databuffer to write.
     * @param p_sOutputFileName The output file name.
     */
    public static void writeUncryptedMessageOutput(int[] p_aiDataBuffer, String p_sOutputFileName) {

        if (p_aiDataBuffer != null) {
            PrintWriter writer;

            try {
                byte[] abBufferToWrite;

                // Delete the character used to make the length even.
                if (p_aiDataBuffer[p_aiDataBuffer.length - 1] == 0) {
                    abBufferToWrite = new byte[p_aiDataBuffer.length - 1];
                } else {
                    abBufferToWrite = new byte[p_aiDataBuffer.length];
                }

                // Fill the buffer to write down.
                for (int i = 0; i < abBufferToWrite.length; i++) {
                    abBufferToWrite[i] = (byte) p_aiDataBuffer[i];
                }

                // Write down text with UTF-8 encoding.
                writer = new PrintWriter(p_sOutputFileName + ".txt", "UTF-8");
                writer.print(new String(abBufferToWrite, "UTF-8"));
                writer.close();

            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }
    }
}
