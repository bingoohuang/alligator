package learning.java;

/**
 * @author bingoohuang [bingoohuang@gmail.com] Created on 2016/11/28.
 */
public class MyThread extends Thread {
    @Override public void run() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getClass()); // class learning.java.MyThread
    }

    public static void main(String[] args) {
        new MyThread().start();
    }
}
