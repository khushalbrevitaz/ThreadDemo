package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        long size1=System.currentTimeMillis();
       // MyThread thread = new MyThread();
       //  Thread thread1=new Thread(thread);
        //  thread1.start();
        FileRead fileRead = new FileRead();
        Thread thread2=new Thread(fileRead);

        thread2.start();
        thread2.join();
        long size2=System.currentTimeMillis();
        System.out.println("time" +(size2-size1));
    }
}
class MyThread implements Runnable{
    @Override
    public void run() {
        for (int i=0;i<10;i++){
            System.out.println(i+" hello");
        }
    }
}
class FileRead implements Runnable{
    @Override
    public void run() {
        String filePath = "/home/khushalkothari/dummy.txt";
        Map<String,Integer> uniqueWords = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" "); // Split by non-word characters
                for (String word : words) {
                       if(uniqueWords.containsKey(word)){
                           int size=uniqueWords.get(word);
                           uniqueWords.put(word,size+1);
                       }
                       else{
                           uniqueWords.put(word,1);
                       }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process the unique words
        for (Map.Entry<String,Integer> values:uniqueWords.entrySet()){
            System.out.println("Key = " + values.getKey() +
                    ", Value = " + values.getValue());
        }
    }
    }

