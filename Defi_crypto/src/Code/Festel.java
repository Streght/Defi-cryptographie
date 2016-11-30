package Code;

import java.util.Map;

public class Festel {

    private static int generatorKey = 2;

    private static int[] feistelCypher(String method, int[] buffer) {

        int[] result = new int[2];
        int temp = -1;

        if (method.equals("cesar")) {

        }
        if (method.equals("affine")) {

        }
        if (method.equals("vigenere")) {
            temp = Vigenere.Encrypt(buffer[1]);
        }

        result[0] = (buffer[0] ^ temp) ^ generatorKey ^ buffer[1];
        result[1] = buffer[0] ^ temp;

        return result;
    }

    private static int[] feistelDecypher(String method, int[] buffer) {

        int[] result = new int[2];
        int temp=-1;
        
        if (method.equals("cesar")) {

        }
        if (method.equals("affine")) {

        }
        if (method.equals("vigenere")) {
            temp = Vigenere.Encrypt(buffer[1] ^ buffer[0] ^ generatorKey);
        }
        
        result[0] = buffer[1] ^ temp;
        result[1] = buffer[0] ^ generatorKey ^ buffer[1];

        return result;
    }

    public static int[] runFestel(Map<String, int[]> data, String toDo) {

        int[] result = new int[data.get("message").length];

        if (toDo.equals("cypher")) {

            Key_Generator.initMask(data.get("generatorkey"));
            //Affine.init(data.get("affine"));
            //Cesar.init(data.get("cesar"));
            Vigenere.initVigenere(data.get("vigenere"));

            for (int i = 0; i < result.length - 1; i += 2) {

                int[] temp = {data.get("message")[i], data.get("message")[i + 1]};
                //temp = festelCypher("cesar", temp);
                //temp = festelCypher("affine", temp);
                temp = feistelCypher("vigenere", temp);

                result[i] = temp[0];
                result[i + 1] = temp[1];
            }

        } else {
            if (toDo.equals("decypher")) {

                Key_Generator.initMask(data.get("generatorkey"));
                //Affine.init(data.get("affine"));
                //Cesar.init(data.get("cesar"));
                Vigenere.initVigenere(data.get("vigenere"));

                Key_Generator.initMask(data.get("generatorkey"));

                for (int i = 0; i < result.length - 1; i += 2) {

                    int[] temp = {data.get("message")[i], data.get("message")[i + 1]};
                    //temp = festelDecypher("cesar", temp);
                    //temp = festelDecypher("affine", temp);
                    temp = feistelDecypher("vigenere", temp);

                    result[i] = temp[0];
                    result[i + 1] = temp[1];
                }

            }
        }

        return result;
    }

}
