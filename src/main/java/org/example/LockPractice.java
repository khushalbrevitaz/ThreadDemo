package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockPractice {
    static int count=0;
    public static void getLock(){
        // Lock lock2 = new ReentrantLock();
        Lock lock1 = new ReentrantLock();
        boolean aq1 = lock1.tryLock();

        try{
            if(aq1){

                for (int i=0;i<10;i++){
                      System.out.println(i);
                    count++;
                }
                System.out.println(count);
            }
        }finally{
            if(aq1) lock1.unlock();

        }
    }





    public static void main(String[] args) throws InterruptedException {

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {

                getLock();
            }
        });

        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {

                getLock();
            }
        });
        thread.start();
        thread1.start();
//        thread.join();
//        thread1.join();
    }


}