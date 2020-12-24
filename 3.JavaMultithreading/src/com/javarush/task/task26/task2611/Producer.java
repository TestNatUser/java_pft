package com.javarush.task.task26.task2611;

import java.util.concurrent.ConcurrentHashMap;

public class Producer implements Runnable {

    private ConcurrentHashMap<String, String> map;

    public Producer(ConcurrentHashMap<String, String> map) {
        this.map = map;
    }

    @Override
    public void run() {
        try {
            int i=1;
            while (true) {
                Thread.sleep(300);
                map.put(String.valueOf(i),"Some text for "+i);
                i++;
            }
        } catch (InterruptedException e) {
            System.out.println(String.format("[%s]thread was terminated", Thread.currentThread().getName()));
        }

    }
}
