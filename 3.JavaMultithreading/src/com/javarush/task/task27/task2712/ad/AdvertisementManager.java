package com.javarush.task.task27.task2712.ad;
public class AdvertisementManager {
    final AdvertisementStorage storage = AdvertisementStorage.getInstance();

    public AdvertisementManager(int timeSeconds) {

        this.timeSeconds = timeSeconds;
    }

    private int timeSeconds;

    public int getTimeSeconds() {
        return timeSeconds;
    }

    public void processVideos()
    {
        if(storage.list().size()==0){
            throw new NoVideoAvailableException();
        }
    }
}
