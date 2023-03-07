package LabCSV;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Hierarchy {
    private LinkedHashMap<String, Integer> hashMap;
    private long counterWords = 0;

    public LinkedHashMap<String, Integer> getHashMap() {
        return hashMap;
    }

    public long getCounterWords() {
        return counterWords;
    }

    void makeHierarchy(String path) throws IOException {
        try (BufferedReader buffReader = new BufferedReader(new FileReader(path))) {
            hashMap = new LinkedHashMap<>();
            StringBuilder stringBuilder = new StringBuilder();

            int symbol;
            while ((symbol = buffReader.read()) != -1) {
                if (Character.isLetterOrDigit(symbol)) {
                    stringBuilder.append((char) symbol);
                } else if (!stringBuilder.isEmpty()) {
                    hashMap.merge(stringBuilder.toString(), 1, Integer::sum);
                    stringBuilder.setLength(0);
                    ++counterWords;
                }
            }
            if (!stringBuilder.isEmpty()) {
                hashMap.merge(stringBuilder.toString(), 1, Integer::sum);
                stringBuilder.setLength(0);
                ++counterWords;
            }
        }
    }

    void sortHierarchy() {
        hashMap = hashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
}
