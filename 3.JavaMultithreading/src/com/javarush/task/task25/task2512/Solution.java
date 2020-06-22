package com.javarush.task.task25.task2512;

import java.util.ArrayList;
import java.util.List;

/*
Живем своим умом
*/
public class Solution implements Thread.UncaughtExceptionHandler {
    List<String> m = new ArrayList<>();

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        t.interrupt();
        if(e.getCause()!=null){
            m.add(e.getClass().getName()+": "+e.getMessage());
            uncaughtException(t,e.getCause());
        } else {
            m.add(e.getClass().getName()+": "+e.getMessage());
            for(int i=m.size()-1;i>=0;i--){
                System.out.println(m.get(i));
            }
        }
    }

    public static void main(String[] args) {
    }
}
