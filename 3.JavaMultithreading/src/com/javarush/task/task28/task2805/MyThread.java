package com.javarush.task.task28.task2805;

import java.util.concurrent.atomic.AtomicInteger;

public class MyThread extends Thread {
    private static AtomicInteger priority=new AtomicInteger(0);

    private synchronized void increase(){
        if(priority.get()>=Thread.MAX_PRIORITY){
       priority=new AtomicInteger(Thread.MIN_PRIORITY);
        } else {
            priority.incrementAndGet();
        }
        setPriority(priority.get());
    }

    public MyThread() {
        increase();
    }

    public MyThread(Runnable target) {
        super(target);
        increase();
    }

    public MyThread(ThreadGroup group, Runnable target) {
        super(group, target);
        increase();
    }

    public MyThread(String name) {
        super(name);
        increase();
    }

    public MyThread(ThreadGroup group, String name) {
        super(group, name);
        increase();
    }

    public MyThread(Runnable target, String name) {
        super(target, name);
        increase();
    }

    public MyThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
        increase();
    }

    public MyThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
        increase();
    }
}
