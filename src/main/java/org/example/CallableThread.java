package org.example;

import java.util.Map;
import java.util.concurrent.Callable;

public class CallableThread implements Callable<Map<String,Integer>> {
    private final String filePart;
    private Map<String, Integer> uniqueWords;

    public CallableThread(String filePart,Map<String,Integer> uniqueWords) {
        this.filePart = filePart;
        this.uniqueWords = uniqueWords;
    }

    @Override
    public Map<String, Integer> call() throws Exception {
        long threadStartTime = System.currentTimeMillis();


        String[] words = filePart.split("[ .,]"); // Split by non-word characters // Split by non-word characters


        for (String word : words) {
//            if(word.endsWith(".")){
//                word=word.substring(0,word.length()-1);
//            }
            if (uniqueWords.containsKey(word)) {
                int size = uniqueWords.get(word);
                uniqueWords.put(word, size + 1);
            } else {
                uniqueWords.put(word, 1);
            }
        }
        long threadEndTime = System.currentTimeMillis();
        System.out.println("threadName :" + "  " + (threadEndTime - threadStartTime));

        return uniqueWords;
    }



}
