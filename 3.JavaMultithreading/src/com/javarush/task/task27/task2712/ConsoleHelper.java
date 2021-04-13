package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static List<Dish> getAllDishesForOrder(){
        List<Dish> list = new ArrayList<>();
        System.out.println(Dish.allDishesToString());
        while(true) {
            try {
                String order = readString().trim();
                if(order.equals("exit")){
                    break;
                }
                Dish dish = Dish.valueOf(order.toUpperCase());
                list.add(dish);
            } catch (Exception e) {
                writeMessage("Такого товара не сущствует");
            }
        }
        return list;
    }

    public static String readString() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }
}
