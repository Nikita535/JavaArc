package Pr1;

import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Main {

    private static ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) {
//        Thread Ping = new Thread(new MyThread(),"Ping");
//        Thread Pong = new Thread(new MyThread(),"Pong");
//        Ping.start();
//        Pong.start();

        Object LOCK_OBJECT = new Object();
        Thread Ping2 = new Thread(new MyThread2(LOCK_OBJECT,"Ping"));
        Thread Pong2 = new Thread(new MyThread2(LOCK_OBJECT,"Pong"));
        Ping2.start();
        Pong2.start();
    }


    static class MyThread implements Runnable{
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName());
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}

class MyThread2 implements Runnable {

    private Object LOCK_OBJECT;
    private String name;

    public MyThread2(Object LOCK_OBJECT, String name) {
        this.LOCK_OBJECT = LOCK_OBJECT;
        this.name = name;
    }

    @Override
    public void run() {
        synchronized (LOCK_OBJECT) {
            while (true) {
                System.out.println(name);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                LOCK_OBJECT.notify();

                try {
                    LOCK_OBJECT.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}