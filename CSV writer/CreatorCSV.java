package LabCSV;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreatorCSV {
    void printCSV(LinkedHashMap<String, Integer> hashMap, long counterWords) {
        String path = "src/LabCSV/OutputFile.csv";
        try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter(path))) {
            String formatStr = "%-10s %-15s %-15s%n";
            buffWriter.write(String.format(formatStr, "Word", "Frequency", "Frequency in percentage"));

            double wordFrequency;
            formatStr = "%-14s %-19s %-18s%n";
            for (Map.Entry<String, Integer> iter : hashMap.entrySet()) {
                wordFrequency = (double) iter.getValue() * 100 / counterWords;

                buffWriter.write(String.format(formatStr
                        , iter.getKey()
                        , iter.getValue()
                        , String.format("%.2f", wordFrequency).replace(',', '.') + "%"));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
