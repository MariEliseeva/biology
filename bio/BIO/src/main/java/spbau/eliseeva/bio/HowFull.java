package spbau.eliseeva.bio;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HowFull {
    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/main/resources/peptides_fill"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert scanner != null;
        ArrayList<String> answer = new ArrayList<>();
        while (scanner.hasNext()) {
            String line1 = scanner.nextLine();
            String line2 = scanner.nextLine();
            if (scanner.hasNext()) {
                scanner.nextLine();
            }
            int good = 0;
            int total = line2.length();
            for (int i = 0; i < line2.length(); i++) {
                if (line2.charAt(i) >= 'a' && line2.charAt(i) <= 'z') {
                    good++;
                }
            }
            answer.add(line1);
            answer.add(line2);
            answer.add(good + " of " + total);
            answer.add("\n");
        }
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/result"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert writer != null;
        try {
            for (String string : answer) {
                writer.write(string + '\n');
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
