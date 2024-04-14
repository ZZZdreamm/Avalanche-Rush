package com.avalancherush.game.Singletons;

import static com.avalancherush.game.Configuration.GlobalVariables.POWER_UP_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.POWER_UP_WIDTH;

import com.avalancherush.game.Configuration.GlobalVariables;
import com.avalancherush.game.Configuration.Textures;
import com.avalancherush.game.Enums.PowerUpType;
import com.avalancherush.game.Models.PowerUp;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import org.w3c.dom.Text;


public class PowerUpFactory {
    private static PowerUpFactory instance;
    public static PowerUpFactory getInstance(){
        if(instance == null){
            instance = new PowerUpFactory();
        }
        return instance;
    }

    public PowerUp createPowerUp (PowerUpType powerUpType, int track, float x, float y, int time){
        PowerUp powerUp = new PowerUp();
        try {
            powerUp.setTrack(track);
        }catch (Exception e){
            System.out.println(e);
        }
        Rectangle rectangle = new Rectangle(x, y, POWER_UP_WIDTH, POWER_UP_HEIGHT);
        powerUp.setRectangle(rectangle);
        powerUp.setTime(time);
        if(powerUpType == PowerUpType.HELMET){
            powerUp.setType(PowerUpType.HELMET);
            powerUp.setTexture(Textures.HELMET);
        } if(powerUpType == PowerUpType.SNOWBOARD){
            powerUp.setType(PowerUpType.SNOWBOARD);
            powerUp.setTexture(Textures.SNOWBOARD);
        }
        return powerUp;
    };
}
