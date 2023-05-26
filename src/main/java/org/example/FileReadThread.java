package org.example;

import java.util.Map;

class FileReadThread implements Runnable {
    private final String filePart;
    private final Map<String, Integer> uniqueWords;

    public FileReadThread(String filePart, Map<String, Integer> uniqueWords) {
        this.filePart = filePart;
        this.uniqueWords = uniqueWords;
    }

    @Override
    public void run() {
        long threadStartTime = System.currentTimeMillis();
        int i = 0;
        String threadName = "thread : " + i;
        i++;
        String[] words = filePart.split(" "); // Split by non-word characters
        for (String word : words) {
            if (uniqueWords.containsKey(word)) {
                int size = uniqueWords.get(word);
                uniqueWords.put(word, size + 1);
            } else {
                uniqueWords.put(word, 1);
            }
        }
        long threadEndTime = System.currentTimeMillis();
        System.out.println("threadName :" + "  " + (threadEndTime - threadStartTime));
    }
}
