package com.javarush.task.task25.task2506;

public class LoggingStateThread extends Thread {
    Thread thread;

    public LoggingStateThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        State sr =thread.getState();
        System.out.println(sr);
        while(!thread.isInterrupted()){
            if(sr!=thread.getState()) {
                sr = thread.getState();
                System.out.println(sr);
                if(sr.equals(State.TERMINATED)){
                    break;
                }
            }
        }
    }
}
