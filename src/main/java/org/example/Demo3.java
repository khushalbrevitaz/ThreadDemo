
package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Demo3 {
    public static void main(String[] args) throws InterruptedException {
        long time1= System.currentTimeMillis();
        int size=4;
        String filePath = "/home/khushalkothari/smalldummy.txt";
       // String filePath = "/home/khushalkothari/dummyData";

        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String newContent = content.toString().toLowerCase();
        int partSize = newContent.length() / size;
        System.out.println(partSize);
        String[] fileParts = new String[size];
        Map<String,Integer> uniqueWords = new ConcurrentHashMap<>();
        Thread[] threads = new Thread[size];
        int startIndex=0;
        int endIndex=0;
        for (int i = 0; i < size; i++) {

            endIndex = (i+1)*partSize;
            while(content.toString().charAt(endIndex-1)!=' '){
                endIndex+=1;
            }

            fileParts[i] = content.substring(startIndex, endIndex);
            System.out.println("end index :"+endIndex+1);
            threads[i] = new Thread(new FileReadThread(fileParts[i],uniqueWords ));
            threads[i].start();
            startIndex=endIndex;
        }

        for (Thread thread : threads) {
            thread.join();
        }
       // Arrays.stream(threads).map(thread -> thread.join());
        long time2= System.currentTimeMillis();
        for (Map.Entry<String,Integer> values:uniqueWords.entrySet()){
            System.out.println(values.getKey() +
                    " : " + values.getValue());
        }
       // System.out.println("time "+ (time2-time1));
          }

}

