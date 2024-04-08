package com.avalancherush.game.Singletons;

import com.avalancherush.game.Enums.PowerUpType;
import com.avalancherush.game.Models.PowerUp;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PowerUpFactory {
    private static PowerUpFactory instance;
    public PowerUpFactory getInstance(){
        if(instance == null){
            instance = new PowerUpFactory();
        }
        return instance;
    }

    public PowerUp createPowerUp (PowerUpType powerUpType, int track, int time){
        PowerUp powerUp = new PowerUp();
        try {
            powerUp.setTrack(track);
        }catch (Exception e){
            System.out.println(e);
        }
        if(powerUpType == PowerUpType.HELMET){
            Image image = new Image();
            powerUp.setImage(image);
            powerUp.setType(PowerUpType.HELMET);
            powerUp.setTime(time);
        }else if(powerUpType == PowerUpType.SNOWBOARD){
            Image image = new Image();
            powerUp.setImage(image);
            powerUp.setType(PowerUpType.SNOWBOARD);
            powerUp.setTime(time);
        }
        return powerUp;
    };
}
