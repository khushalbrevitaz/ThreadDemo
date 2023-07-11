package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class ByCompletableFuture {
    static Map<String,Integer> mergerdMap = new HashMap<>();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long time1= System.currentTimeMillis();

        String filePath = "/home/khushalkothari/Downloads/ThreadsDemoPractice/src/main/resources/demo.txt";
        int size=4;
        CompletableFuture<Void>[] randomNumberTasks = new CompletableFuture[size];

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
        String[] fileParts = new String[size];


        int startIndex=0;
        int endIndex=0;
        for (int i = 0; i < size; i++) {

            Map<String,Integer> uniqueWords = new ConcurrentHashMap<>();
            endIndex = (i+1)*partSize;
            if(endIndex>newContent.length()-1){
                endIndex=newContent.length();
            }
            while(endIndex<newContent.length() && newContent.charAt(endIndex+1)!=' ' ){
                endIndex+=1;
            }

            fileParts[i] = newContent.substring(startIndex, endIndex);
            FileReadThread task = new FileReadThread(fileParts[i],uniqueWords);
            try{
                randomNumberTasks[i] = CompletableFuture.runAsync(task)
                        .thenAccept((Void) -> mergeMap(mergerdMap, task.getMap()));
            }catch (NullPointerException nullPointerException){
                System.out.println("null pointer ");

            }
            startIndex=endIndex+1;
        }
        CompletableFuture.allOf(randomNumberTasks).join();
        long time2= System.currentTimeMillis();
        for (Map.Entry<String,Integer> values:mergerdMap.entrySet()){
            System.out.println(values.getKey() +
                    " : " + values.getValue());
        }
        System.out.println("time "+ (time2-time1));
    }
    Map<String,Integer> smallMap=new HashMap<>();
    public static void mergeMap(Map<String,Integer> mergeMap,Map<String,Integer> smallMap){

        long time6= System.currentTimeMillis();
        if (smallMap == null) {
            return;
        }
        for (Map.Entry<String,Integer> map: smallMap.entrySet()){
            if(mergeMap.containsKey(map.getKey())){
                mergeMap.put(map.getKey(),(map.getValue()+mergeMap.get(map.getKey())));
            }
            else{
                mergeMap.put(map.getKey(),map.getValue());
            }

        }
        long time2= System.currentTimeMillis();

    }

}
