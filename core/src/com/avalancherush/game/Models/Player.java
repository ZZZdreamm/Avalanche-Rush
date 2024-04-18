package com.avalancherush.game.Models;

import static com.avalancherush.game.Configuration.Textures.SINGLE_PLAYER;
import static com.avalancherush.game.Configuration.Textures.SKIN;

import com.avalancherush.game.Configuration.Textures;
import com.avalancherush.game.Enums.PowerUpType;
import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Interfaces.Collidable;


import java.util.ArrayList;
import java.util.List;

public class Player extends Collidable {
    private int playerId;
    private int track;
    private boolean jumping;
    private SkinType skin;
    private List<PowerUp> powerUps;
    public Player(){
        this.powerUps = new ArrayList<>();
    }
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track){
        if(track < 1 || track > 5) return;
        this.track = track;
    }

    public boolean getJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping){
        this.jumping = jumping;
    }

    public SkinType getSkin() {
        return skin;
    }
    public void setSkin(SkinType skin) {
        this.skin = skin;
        if(skin == SkinType.BASIC){
            this.texture = SINGLE_PLAYER;
        }else if(skin == SkinType.MASTER){
            this.texture = SKIN;
        }

    }

    public List<PowerUp> getPowerUps(){
        return powerUps;
    }

    public void addPowerUp(PowerUp powerUp) {
        this.powerUps.add(powerUp);
    }

    public void removePowerUp(PowerUp powerUp) {
        this.powerUps.remove(powerUp);
    }

}
