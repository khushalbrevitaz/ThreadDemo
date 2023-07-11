package org.example;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorPractice implements Runnable{
     int count=0;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        ExecutorService executor1 = Executors.newFixedThreadPool(5);

        ExecutorPractice e= new ExecutorPractice();
        executor.execute(e);
        executor1.execute(e);
    }

    @Override
    public void run() {
        for(int i=0;i<100;i++){
            count++;
            System.out.println(count);
        }
    }
}
