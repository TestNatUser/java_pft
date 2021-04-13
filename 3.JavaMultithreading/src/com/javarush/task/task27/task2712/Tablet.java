package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet extends Observable {
    private static Logger logger = Logger.getLogger(Tablet.class.getName());

    @Override
    public String toString() {
        return "Tablet{number="+number+"}";
    }

    public Tablet(int number) {
        this.number = number;
    }

    private final int number;

    public Order createOrder() {
        Order order = null;
            try {
                order = new Order(this);
                if(!order.isEmpty()) {
                    ConsoleHelper.writeMessage(order.toString());
                    AdvertisementManager manager = new AdvertisementManager(order.getTotalCookingTime()*60);
                    try {
                        manager.processVideos();
                    } catch(NoVideoAvailableException e){
                        logger.log(Level.INFO, "No video is available for the order " + order);
                    }
                    setChanged();
                    notifyObservers(order);
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Console is unavailable.");
            }
        return order;
    }
}
