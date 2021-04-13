package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;
import java.util.List;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet){
        this.tablet = tablet;
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    @Override
    public String toString() {
        return "Your order: "+dishes.toString()+" of "+tablet.toString()+", cooking time "+getTotalCookingTime()+"min";
    }

    public int getTotalCookingTime(){
        int sum=0;
        for(Dish dish : dishes){
            sum+=dish.getDuration();
        }
        return sum;
    }

    public boolean isEmpty(){
        return dishes.size()<=0;
    }
}
