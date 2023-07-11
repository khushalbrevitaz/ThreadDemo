package org.example;

public class SharedThread extends Thread {
    public static void main(String[] args) {
            StringBuffer sharedBuffer = new StringBuffer("A");

            SharedThread thread1 = new SharedThread(sharedBuffer);
            SharedThread thread2 = new SharedThread(sharedBuffer);
            SharedThread thread3 = new SharedThread(sharedBuffer);

            thread1.start();
            thread2.start();
            thread3.start();
    }
    KK k = new KK();

    private StringBuffer sharedBuffer;

    public SharedThread(StringBuffer sharedBuffer) {
        this.sharedBuffer = sharedBuffer;
    }
     static class KK{
         public String mm(){
            System.out.println("hello");
            return "ggg";
        }
    }
    @Override
    public void run() {
        synchronized (sharedBuffer) {
            for (int i = 0; i < 100; i++) {
                System.out.println(sharedBuffer);
            }
            char currentChar = sharedBuffer.charAt(0);

            sharedBuffer.setCharAt(0, ++currentChar);
        }

        for(int i=0;i<5;i++){
            System.out.println("khushal");
        }
    }
}
