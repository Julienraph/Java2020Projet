package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ExportCSV {
    public void exportCSV(String[][] data, String[] columnName) {
        try {
            FileWriter fw = new FileWriter("fic.csv");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < columnName.length; i++) {
                sb.append(String.format("\"%s\",", columnName[i]));
            }
            pw.println(sb);
            for (int i = 0; i < data.length; i++) {
                sb = new StringBuilder();
                for (int j = 0; j < data[0].length; j++) {
                    sb.append(String.format("\"%s\",", data[i][j]));
                }
                pw.println(sb);
            }

            pw.close();

        } catch (IOException ex) {
            System.err.println("Erreur sur le fichier");
        }
    }
}
