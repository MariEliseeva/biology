package spbau.eliseeva.bio;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Mass {
    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/main/resources/CAH_01_result_table.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert scanner != null;
        scanner.nextLine();
        ArrayList<String> answer = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] words = line.split("\\s");
            if (Double.parseDouble(words[words.length - 2]) < 10e-10) {
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
                    if (words[i].equals("SV=3")) {
                        end = i + 1;
                        break;
                    }
                }
                if (end == words.length) {
                    continue;
                }
                for (int i = 0; i < words[end + 3].length(); i++) {
                    int bg;
                    if (words[end + 3].charAt(i) == '[') {//[42.01]
                        bg = i;
                        while (words[end + 3].charAt(i) != ']') {
                            i++;
                        }
                        StringBuilder a = new StringBuilder();
                        for (int j = bg; j <= i; j++) {
                            a.append(words[end + 3].charAt(j));
                        }
                        answer.add(a.toString());
                    }
                }
            }
        }
        Map<String, Long> ans = answer.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/mass2.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert writer != null;
            for (Map.Entry<String, Long> a : ans.entrySet()) {
                writer.write(a.getKey() + ' ' + a.getValue() + '\n');
            }
            //for (String a : answer) {
              //  writer.write(a + '\n');
            //}
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}