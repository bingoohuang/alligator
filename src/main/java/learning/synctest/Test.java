package learning.synctest;

import static java.lang.Thread.sleep;

public class Test {
    private static boolean flag = false;
    private static int number = 0;

    public static class MyRun implements Runnable {
        @Override
        public void run() {
            System.out.println("new thread: " + Thread.currentThread());
            while (!flag) {
                System.out.println(" while number = " + number);
                Thread.yield();
            }
            System.out.println("number = " + number);
        }
    }

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new MyRun());
        t.start();
        sleep(1);
        number = 42;
        flag = true;
        System.out.println("before sleep: " + Thread.currentThread());
        sleep(0);
        System.out.println("after sleep: " + Thread.currentThread());

    }
}