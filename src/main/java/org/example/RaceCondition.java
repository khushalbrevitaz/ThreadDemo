package org.example;


import java.util.concurrent.atomic.AtomicInteger;

public class RaceCondition {
   private AtomicInteger count= new AtomicInteger(0);
   public AtomicInteger getcount(){
       count.getAndIncrement();
       return count;
   }

    public static void main(String[] args) {
       RaceCondition raceCondition=new RaceCondition();

        for(int i=0;i<100;i++){
            Thread thread1 =new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(raceCondition.getcount());
                }
            });
            thread1.start();
        }

    }
}
