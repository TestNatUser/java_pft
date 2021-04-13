package com.javarush.task.task27.task2712.kitchen;

public enum Dish {
    FISH(25),
    STEAK(30),
    SOUP(15),
    JUICE(5),
    WATER(3);

    public static String allDishesToString() {
        StringBuilder sb = new StringBuilder();
        for (Dish dish : Dish.values()) {
            sb.append(dish.name()+", ");
        }
        return sb.delete(sb.length()-2,sb.length()-1).toString().trim();
    }

    public int getDuration() {
        return duration;
    }

    private int duration;
    Dish(int duration) {
        this.duration = duration;
    }
}
