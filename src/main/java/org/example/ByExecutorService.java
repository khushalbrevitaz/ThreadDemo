
package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class ByExecutorService {
    static Map<String,Integer> mergerdMap = new HashMap<>();
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long time1= System.currentTimeMillis();
        int size=4;
        String filePath = "/home/khushalkothari/demo.txt";


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

        FutureTask[] randomNumberTasks = new FutureTask[size];
        ExecutorService service= Executors.newFixedThreadPool(size);
        String[] fileParts = new String[size];


        int startIndex=0;
        int endIndex=0;

        for (int i = 0; i < size; i++) {
            Map<String,Integer> uniqueWords = new HashMap<>();
            endIndex = (i+1)*partSize;
            if(endIndex>newContent.length()-1){
                endIndex=newContent.length();
            }
            while(newContent.toString().charAt(endIndex-1)!=' ' && endIndex<newContent.length()){
                endIndex+=1;
            }

            fileParts[i] = newContent.substring(startIndex, endIndex);

            Callable callable = new CallableThread(fileParts[i],uniqueWords);
            // Create the FutureTask with Callable
            randomNumberTasks[i] = new FutureTask(callable);
            service.execute(randomNumberTasks[i]);

            startIndex=endIndex+1;
        }

       for (int i=0;i<size;i++){
            mergeMap(mergerdMap, (Map<String, Integer>) randomNumberTasks[i].get());
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
            if(mergeMap.containsKey(map.getKey())){
                mergeMap.put(map.getKey(),(map.getValue()+mergeMap.get(map.getKey())));
            }
            else{
                mergeMap.put(map.getKey(),map.getValue());
            }

        }
    }

}
