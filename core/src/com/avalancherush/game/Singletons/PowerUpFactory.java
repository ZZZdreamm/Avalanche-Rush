package com.avalancherush.game.Singletons;

public class PowerUpFactory {
    private static PowerUpFactory instance;
    public PowerUpFactory getInstance(){
        if(instance == null){
            instance = new PowerUpFactory();
        }
        return instance;
    }
}
