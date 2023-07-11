package org.example;

import java.util.ArrayList;
import java.util.List;

public class ExtraPractice implements Runnable {
        // shared by all threads
        private List<Integer> list = new ArrayList<>();
    public ExtraPractice() {
// add some elements
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }
    }
    // might run concurrently, you cannot be sure
// to be safe you must assume it does
    public void run() {
        String tName = Thread.currentThread().getName();
        while (!list.isEmpty()) {
            System.out.println(tName + " removed " + list.remove(0));
        }
    }
    public static void main(String[] args) {
        ExtraPractice alr = new ExtraPractice();
        Thread t1 = new Thread(alr);
        Thread t2 = new Thread(alr); // shared Runnable
        t1.start();
        t2.start();
    }
}

