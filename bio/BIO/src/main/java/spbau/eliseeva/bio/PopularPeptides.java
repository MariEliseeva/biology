package spbau.eliseeva.bio;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PopularPeptides {
    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/main/resources/good_peptides"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert scanner != null;
        HashMap<String, String> names = new HashMap<>();
        ArrayList<String> peptides = new ArrayList<>();
        while (scanner.hasNext()) {
            String name = scanner.nextLine().trim();
            String peptide = scanner.nextLine().trim();
            names.put(peptide, name);
            peptides.add(peptide);
        }
        scanner.close();
        List<Map.Entry<String, Long>> result = peptides.stream().collect(Collectors.groupingBy(Function.identity(),
                Collectors.counting())).entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/popular_peptides"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert writer != null;
            for (Map.Entry<String, Long> entry : result) {
                writer.write(entry.toString() + '\n');
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> proteins = new HashMap<>();
        scanner = null;
        try {
            scanner = new Scanner(new File("src/main/resources/cah.fasta"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert scanner != null;
        while (scanner.hasNext()) {
            String name = scanner.nextLine().trim();
            String protein = scanner.nextLine().trim();
            proteins.put(name.substring(1), protein);
            //System.out.println(name.substring(1) + ' ' + (name.length() - 1));
        }
        scanner.close();
        //Map<String, ArrayList<Boolean>> proteinsFill = new HashMap<>();
        /*for (Map.Entry<String, String> entry : proteins.entrySet()) {
            proteinsFill.put(entry.getKey(), new ArrayList<>());
            for (int i = 0; i < entry.getValue().length(); i++) {
                proteinsFill.get(entry.getKey()).add(false);
            }
        }*/
        /*ArrayList<String> answers = new ArrayList<>();

        for (Map.Entry<String, String> entry : names.entrySet()) {
            String protein = proteins.get(entry.getValue());
            String peptide = entry.getKey();
            if (protein == null) {
                continue;
            }
            int begin = cmp(protein, peptide);
            //System.out.println(protein);
            //System.out.println(peptide);
           // System.out.println(begin);
            //System.out.println();
            //System.out.println(proteinsFill.get(entry.getValue()).size());
            //System.out.println(p);
            String ans = "";
            for (int i = 0; i < begin; i++) {
                ans += ' ';
            }
            for (int i = begin; i < begin + peptide.length() && i < protein.length(); i++) {
                ans += peptide.charAt(i);
            }
            answers.add(ans);
            //System.out.println(proteinsFill.get(entry.getValue()));
           // System.out.println();
        }
        /*for (Map.Entry<String, String> entry : proteins.entrySet()) {
            String protein = entry.getValue();
            //ArrayList<Boolean> proteinFill = proteinsFill.get(entry.getKey());
            //System.out.println(proteinFill);
            StringBuilder answer = new StringBuilder();
            for (int i = 0; i < proteinFill.size(); i++) {
                if (proteinFill.get(i)) {
                    answer.append(Character.toLowerCase(protein.charAt(i)));
                } else {
                    answer.append(protein.charAt(i));
                }
            }
            answers.add(entry.getKey() + '\n' + answer.toString());
        }*/
        writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/peptides_fill"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert writer != null;
            for (String name : proteins.keySet()) {
                writer.write(name + '\n');
                writer.write(proteins.get(name) + '\n');
                for (Map.Entry<String, String> entry: names.entrySet()) {
                    if (entry.getValue().equals(name)) {
                        writer.write(entry.getKey() + '\n');
                    }
                }
                writer.write("\n\n\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
