package com.avalancherush.game.Models;

import com.avalancherush.game.Enums.PowerUpType;

public class TakenPowerUp {
    private PowerUpType powerUpType;
    private float time;

    public TakenPowerUp(PowerUpType powerUpType, float time){
        this.powerUpType = powerUpType;
        this.time = time;
    }

    public PowerUpType getPowerUpType() {
        return powerUpType;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
