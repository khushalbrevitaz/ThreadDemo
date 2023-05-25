
package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Demo3 {
    public static void main(String[] args) throws InterruptedException {
        long time1= System.currentTimeMillis();
        String filePath = "/home/khushalkothari/dummy.txt";
        String[] parts=partFile(filePath,4);
        Map<String,Integer> uniqueWords = new ConcurrentHashMap<>();
        Thread[] threads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            threads[i] = new Thread(new FileRead2(parts[i],uniqueWords ));
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        long time2= System.currentTimeMillis();
        System.out.println("time "+ (time2-time1));
    }
    public static String[] partFile(String filepath,int size ){
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String content1 = content.toString();
        int partSize = content1.length() / size;
        String[] fileParts = new String[size];

        for (int i = 0; i < size; i++) {
            int startIndex = i * partSize;
            int endIndex = (i+1)*partSize;
            fileParts[i] = content.substring(startIndex, endIndex);
        }
        return fileParts;
    }
}


class FileRead2 implements Runnable{
    private final String filePart;
    private final Map<String,Integer> uniqueWords;

    public FileRead2(String filePart, Map<String, Integer> uniqueWords){
        this.filePart=filePart;
        this.uniqueWords=uniqueWords;
    }

    @Override
    public void run() {
        String[] words = filePart.split(" "); // Split by non-word characters
        for (String word : words) {
            if(uniqueWords.containsKey(word)){
                int size=uniqueWords.get(word);
                uniqueWords.put(word,size+1);
            }
            else{
                uniqueWords.put(word,1);
            }

            // Arrays.stream(words).map(s -> uniqueWords.add(s.toLowerCase()));
        }

        // Process the unique words
        for (Map.Entry<String,Integer> values:uniqueWords.entrySet()){
            System.out.println("Key = " + values.getKey() +
                    ", Value = " + values.getValue());
        }
    }
}

