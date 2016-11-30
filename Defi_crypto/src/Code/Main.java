package Code;

import java.util.Map;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        if (args[0].equals("-enc")) {
            Map<String, int[]> buffer = File_Interaction.readFileMessage("message.txt");
            int[] result = Feistel.runFeistel(buffer, "cypher");
            File_Interaction.writeCryptogramOutput(result);
        }

        if (args[0].equals("-dec")) {
            Map<String, int[]> buffer2 = File_Interaction.readFileMessage("cryptogramme.txt");
            int[] result2 = Feistel.runFeistel(buffer2, "decypher");
            File_Interaction.writeUncryptedMessageOutput(result2);
        }
    }
}
