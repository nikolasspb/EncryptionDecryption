package encryptdecrypt;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        int len = args.length;
        for (int i = 0; i < len - 1; i++) {
            map.put(args[i], args[i + 1]);
        }
        String operation = map.getOrDefault("-mode", "enc");
        String message = map.getOrDefault("-data", "");
        int key = Integer.parseInt(map.getOrDefault("-key", "0"));
        String in = map.getOrDefault("-in", "");
        String out = map.getOrDefault("-out", "");
        String alg = map.getOrDefault("-alg", "shift");
        if (!"".equals(in)) {
            String inFile = "";
            try {
                FileReader fr = new FileReader(in);
                int i;
                while ((i = fr.read()) != -1) {
                    inFile += (char) i;
                }
                fr.close();
                message = inFile;
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        String outMessage = "";
        if ("dec".equals(operation)) {
            key = -key;
        }
        for (int i = 0; i < message.length(); i++) {
            int c = message.charAt(i);
            if ("shift".equals(alg)) {
                int firstLetter = 65;
                if (c >= 97 && c <= 122) {
                    firstLetter = 97;
                }
                if (c != 32) {
                    int originalAlphabetPosition = c - firstLetter;
                    int newAlphabetPosition = (originalAlphabetPosition + key) % 26;
                    if (newAlphabetPosition < 0) {
                        newAlphabetPosition = 26 + newAlphabetPosition;
                    }
                    c = firstLetter + newAlphabetPosition;
                }
            } else {
                c += key;
                if (c > 127) {
                    c -= 127;
                }
            }
            outMessage += (char) c;
        }
        if ("".equals(out)) {
            System.out.println(outMessage);
        } else {
            try (FileWriter writer = new FileWriter(out)) {
                writer.write(outMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
