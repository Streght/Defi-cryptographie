package Code;

import java.util.Map;

public class Feistel {

    private static int[] feistelCypher(String method, int[] buffer, int generatorKey) {

        int[] result = new int[2];
        int temp = -1;

        if (method.equals("caesar")) {
            temp = Caesar.encryptCaesar(buffer[1]);
        }
        if (method.equals("affine")) {
            temp = Affine.encryptAffine(buffer[1]);
        }
        if (method.equals("vigenere")) {
            temp = Vigenere.encryptVigenere(buffer[1]);
        }

        result[0] = (buffer[0] ^ temp) ^ generatorKey ^ buffer[1];
        result[1] = buffer[0] ^ temp;

        return result;
    }

    private static int[] feistelDecypher(String method, int[] buffer, int generatorKey) {

        int[] result = new int[2];
        int temp = -1;

        if (method.equals("caesar")) {
            temp = Caesar.encryptCaesar(buffer[1] ^ buffer[0] ^ generatorKey);
        }
        if (method.equals("affine")) {
            temp = Affine.encryptAffine(buffer[1] ^ buffer[0] ^ generatorKey);
        }
        if (method.equals("vigenere")) {
            temp = Vigenere.encryptVigenere(buffer[1] ^ buffer[0] ^ generatorKey);
        }

        result[0] = buffer[1] ^ temp;
        result[1] = buffer[0] ^ generatorKey ^ buffer[1];

        return result;
    }

    public static int[] runFeistel(Map<String, int[]> data, String toDo) {

        if (data != null && !data.isEmpty()) {
            int[] result = new int[data.get("message").length];

            if (toDo.equals("cypher")) {

                Key_Generator.initMask(data.get("generatorkey"));
                int[] generatorKey = Key_Generator.generateMask();
                Affine.initAffine(data.get("affine"));
                Caesar.initCaesar(data.get("caesar")[0]);
                Vigenere.initVigenere(data.get("vigenere"));

                for (int i = 0; i < result.length - 1; i += 2) {

                    int[] temp = {data.get("message")[i], data.get("message")[i + 1]};
                    temp = feistelCypher("caesar", temp, generatorKey[0]);
                    temp = feistelCypher("affine", temp, generatorKey[1]);
                    temp = feistelCypher("vigenere", temp, generatorKey[2]);

                    result[i] = temp[0];
                    result[i + 1] = temp[1];
                }

            } else {
                if (toDo.equals("decypher")) {

                    Key_Generator.initMask(data.get("generatorkey"));
                    int[] generatorKey = Key_Generator.generateMask();
                    Affine.initAffine(data.get("affine"));
                    Caesar.initCaesar(data.get("caesar")[0]);
                    Vigenere.initVigenere(data.get("vigenere"));

                    Key_Generator.initMask(data.get("generatorkey"));

                    for (int i = 0; i < result.length - 1; i += 2) {

                        int[] temp = {data.get("message")[i], data.get("message")[i + 1]};
                        temp = feistelDecypher("vigenere", temp, generatorKey[2]);
                        temp = feistelDecypher("affine", temp, generatorKey[1]);
                        temp = feistelDecypher("caesar", temp, generatorKey[0]);

                        result[i] = temp[0];
                        result[i + 1] = temp[1];
                    }

                }
            }

            return result;
        }
        return null;
    }

}
