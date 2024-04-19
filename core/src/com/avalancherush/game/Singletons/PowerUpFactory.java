package com.avalancherush.game.Singletons;

import static com.avalancherush.game.Globals.GlobalVariables.POWER_UP_HEIGHT;
import static com.avalancherush.game.Globals.GlobalVariables.POWER_UP_HELMET_TIME;
import static com.avalancherush.game.Globals.GlobalVariables.POWER_UP_SNOWBOARD_TIME;
import static com.avalancherush.game.Globals.GlobalVariables.POWER_UP_WIDTH;
import static com.avalancherush.game.Globals.GlobalVariables.heightScale;
import static com.avalancherush.game.Globals.GlobalVariables.widthScale;

import com.avalancherush.game.Globals.Textures;
import com.avalancherush.game.Enums.PowerUpType;
import com.avalancherush.game.Models.PowerUp;
import com.badlogic.gdx.math.Rectangle;


public class PowerUpFactory {
    private static PowerUpFactory instance;
    public static PowerUpFactory getInstance(){
        if(instance == null){
            instance = new PowerUpFactory();
        }
        return instance;
    }
    public PowerUp createPowerUp (PowerUpType powerUpType, int track, float x, float y){
        PowerUp powerUp = new PowerUp();
        try {
            powerUp.setTrack(track);
        }catch (Exception e){
            System.out.println(e);
        }
        Rectangle rectangle = new Rectangle(x, y, POWER_UP_WIDTH * widthScale, POWER_UP_HEIGHT * heightScale);
        powerUp.setRectangle(rectangle);
        if(powerUpType == PowerUpType.HELMET){
            powerUp.setType(PowerUpType.HELMET);
            powerUp.setTexture(Textures.HELMET);
            powerUp.setTime(POWER_UP_HELMET_TIME);
        } if(powerUpType == PowerUpType.SNOWBOARD){
            powerUp.setType(PowerUpType.SNOWBOARD);
            powerUp.setTexture(Textures.SNOWBOARD);
            powerUp.setTime(POWER_UP_SNOWBOARD_TIME);
        }
        return powerUp;
    };

    public PowerUp givePlayerPowerUp(PowerUpType powerUpType, float time){
        PowerUp powerUp = new PowerUp();
        powerUp.setType(powerUpType);
        powerUp.setTime(time);
        return powerUp;
    }
}
