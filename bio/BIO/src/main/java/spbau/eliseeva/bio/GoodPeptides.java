package spbau.eliseeva.bio;

import java.io.*;
import java.util.*;

public class GoodPeptides {
    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/main/resources/CAH_03_result_table.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert scanner != null;
        scanner.nextLine();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> peptides = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] words = line.split("\\s");
            if (Double.parseDouble(words[words.length - 2]) < 10e-6) {
                int begin = 0;
                for (int i = 0; i < words.length; i++) {
                    if (words[i].charAt(0) == 's' && words[i].charAt(1) == 'p' ||
                            words[i].charAt(0) == 'g' && words[i].charAt(1) == 'i') {
                        begin = i;
                        break;
                    }
                }
                int end = words.length;
                for (int i = begin; i < words.length; i++) {
                    if (words[i].equals("taurus]") || words[i].equals("SV=3") ||
                            words[i].equals("SV=2") || words[i].equals("B(5)") ||
                            words[i].equals("1") && words[i - 1].equals("protein") &&
                            words[i - 2].equals("domain-containing ") ||
                            words[i].equals("Full=Lys-C") || words[i].equals("Precursor") ||
                            words[i].equals("sapiens]")) {
                        end = i + 1;
                        break;
                    }
                }
                if (end == words.length) {
                    continue;
                }
                boolean isEnd = false;
                for (int i = 0; i < words[end + 3].length(); i++) {
                    if (words[end + 3].charAt(i) == '[') {//[42.01]
                        if (words[end + 3].charAt(i + 1) == '4' && words[end + 3].charAt(i + 2) == '2' &&
                                words[end + 3].charAt(i + 3) == '.' && words[end + 3].charAt(i + 4) == '0' &&
                                words[end + 3].charAt(i + 5) == '1' && words[end + 3].charAt(i + 6) == ']') {
                            continue;
                        }
                        isEnd = true;
                        break;
                    }
                }
                if (isEnd) {
                    continue;
                }
                StringBuilder strAdd = new StringBuilder();
                for (int i = begin; i < end; i++) {
                    strAdd.append(words[i]).append(' ');
                }
                if (words[end + 3].charAt(0) == '.') {
                    begin = 1;
                } else {
                    begin = 2;
                }
                StringBuilder peptideLine = new StringBuilder();
                int i;
                for (i = begin; i < words[end + 3].length(); i++) {
                    if (words[end + 3].charAt(i) == '[') {//[42.01]
                        i += 6;
                        continue;
                    }
                    if (words[end + 3].charAt(i) == '(' || words[end + 3].charAt(i) == ')') {
                        continue;
                    }
                    if (words[end + 3].charAt(i) == '.') {
                        break;
                    }
                    peptideLine.append(words[end + 3].charAt(i));
                }
                names.add(strAdd.toString());
                peptides.add(String.valueOf(peptideLine));
            }
        }

        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/good_peptides"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert writer != null;
            assert names.size() == peptides.size();
            for (int i = 0; i < names.size(); i++) {
                writer.write(names.get(i) + '\n' + peptides.get(i) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}