import java.io.*;
import java.util.*;

public class OC {
    public static void main(String [] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/main/resources/good_peptides"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert scanner != null;
        ArrayList<String> peptides = new ArrayList<>();
        String full = "MSHHWGYGKHNGPEHWHKDFPIANGERQSPVDIDTKAVVQDPALKPLALVYGEATSRRMVNNGHSFNVEYDDSQDKAVLKDGPLTGTYRLVQFHFHWGSSDDQGSEHTVDRKKYAAELHLVHWNTKYGDFGTAAQQPDGLAVVGVFLKVGDANPALQKVLDALDSIKTKGKSTDFPNFDPGSLLPNVLDYWTYPGSLTTPPLLESVTWIVLKEPISVSSQQMLKFRTLNFNAEGEPELLMLANWRPAQPLKNRQVRGFPK";
        while (scanner.hasNext()) {
            scanner.nextLine().trim();
            String peptide = scanner.nextLine().trim();
            int index = full.indexOf(peptide);
            if (index == -1) {
                continue;
            }
            peptide = spaces(index) + peptide;
            peptides.add(peptide);
        }
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/good_peptides"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert writer != null;
            assert peptides.size() == peptides.size();
            for (String peptide : peptides) {
                writer.write(peptide + '\n');
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String spaces(int index) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < index; i++) {
            result.append(" ");
        }
        return result.toString();
    }
}
