package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main{
    public static void main(String[] args) throws IOException {
        String filePath = "/home/khushalkothari/dummy.txt";
        Set<String> uniqueWords = new HashSet<>();
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }

        // Split the file content into equal parts for each thread
        String[] fileParts = splitFile(fileContent.toString(), 4);

        Thread[] threads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            threads[i] = new Thread(new ThreadDemo(filePath,uniqueWords));
            threads[i].start();
        }
    }
    private static String[] splitFile(String filePath, int numParts) throws IOException{
        return null;
    }
}

class ThreadDemo implements Runnable{
    String filePart;
    private final Set<String> uniqueWords;

    public ThreadDemo(String filePart, Set<String> uniqueWords) {
        this.filePart = filePart;
        this.uniqueWords = uniqueWords;
    }

    @Override
    public void run() {
        String[] partData= filePart.split(" ");
//        for (String line : partData) {
//           line.stream(line.split(" ")).map(s -> uniqueWords.add(s.toLowerCase(Locale.ROOT)));
//
//        }
        Arrays.stream(partData).map(s -> uniqueWords.add(s.toLowerCase()));
    }
}
