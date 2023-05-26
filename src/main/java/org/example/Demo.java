package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        long size1=System.currentTimeMillis();
        String filePath = "/home/khushalkothari/smalldummy.txt";
        Map<String,Integer> uniqueWords = new ConcurrentHashMap<>();

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String fileParts = content.toString().toLowerCase();
        FileReadThread fileRead = new FileReadThread(fileParts,uniqueWords);
        Thread thread2=new Thread(fileRead);

        thread2.start();
        thread2.join();
        long size2=System.currentTimeMillis();
        for (Map.Entry<String,Integer> values:uniqueWords.entrySet()){
            System.out.println(values.getKey() +
                    " : " + values.getValue());
        }
        System.out.println("time" +(size2-size1));
    }
}

