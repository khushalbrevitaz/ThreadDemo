package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Counter {
    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Lock lock1 = new ReentrantLock();


    public void increment() {
        lock.lock();
        try {
            for (int i = 0; i < 50; i++) {
                count++;
                System.out.println(getCount());
            }

        } finally {
            lock.unlock();
        }
    }
    public void increment1() {
        lock1.lock();
        try {
            for (int i = 0; i < 50; i++) {
                //count++;
                System.out.println("kfggggg");
            }

        } finally {
            lock1.unlock();
        }
    }

    public int getCount() {
        return count;
    }
}

public class LockPractice2 {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        // Create multiple threads that increment the counter
        Thread t1 = new Thread(() -> {
            counter.increment();
        });

        Thread t2 = new Thread(() -> {
            counter.increment1();
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Count: " + counter.getCount());
    }
}
