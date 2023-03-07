package LabCSV;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("No input file");
            return;
        }
        String path = args[0];

        Hierarchy hierarchy = new Hierarchy();
        hierarchy.makeHierarchy(path);
        hierarchy.sortHierarchy();

        CreatorCSV creatorCSV = new CreatorCSV();
        creatorCSV.printCSV(hierarchy.getHashMap(), hierarchy.getCounterWords());
    }
}
