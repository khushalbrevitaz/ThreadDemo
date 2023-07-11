
package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ByMergeMap {
    static Map<String,Integer> mergerdMap = new HashMap<>();
    public static void main(String[] args) throws InterruptedException {
        long time1= System.currentTimeMillis();
        int size=4;
        String filePath = "/home/khushalkothari/demo.txt";
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

        FileReadThread[] threads = new FileReadThread[size];
        int startIndex=0;
        int endIndex=0;

        for (int i = 0; i < size; i++) {
            Map<String,Integer> uniqueWords = new HashMap<>();
            endIndex = (i+1)*partSize;
            if(endIndex>content.length()-1){
                System.out.println(endIndex+" inside" + content.length());
                endIndex=content.length();
                System.out.println("last "+endIndex);
            }
            while(content.toString().charAt(endIndex-1)!=' ' && endIndex<content.length()){
                endIndex+=1;
            }

            fileParts[i] = content.substring(startIndex, endIndex);
            System.out.println("end index :"+endIndex);
            threads[i] = new FileReadThread(fileParts[i],uniqueWords);
            threads[i].start();
            startIndex=endIndex+1;

            System.out.println("start index : "+startIndex);
        }


        for (FileReadThread thread : threads) {
            thread.join();
        }

        for (FileReadThread thread : threads){
            mergeMap(mergerdMap,thread.getMap());
        }
        // Arrays.stream(threads).map(thread -> thread.join());
        long time2= System.currentTimeMillis();
        for (Map.Entry<String,Integer> values:mergerdMap.entrySet()){
            System.out.println(values.getKey() +
                    " : " + values.getValue());
        }
         System.out.println("time "+ (time2-time1));
    }
    public static void mergeMap(Map<String,Integer> mergeMap,Map<String,Integer> smallMap){
        for (Map.Entry<String,Integer> map: smallMap.entrySet()){
            System.out.println("map : "+map.getKey());
            if(mergeMap.containsKey(map.getKey())){
                mergeMap.put(map.getKey(),(map.getValue()+mergeMap.get(map.getKey())));
            }
        else{
                mergeMap.put(map.getKey(),map.getValue());
            }
            System.out.println("values "+ map.getValue() );
        }
    }

}
