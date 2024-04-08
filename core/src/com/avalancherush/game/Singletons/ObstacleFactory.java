package com.avalancherush.game.Singletons;

public class ObstacleFactory {
    private static ObstacleFactory instance;
    public ObstacleFactory getInstance(){
        if(instance == null){
            instance = new ObstacleFactory();
        }
        return instance;
    }
}
