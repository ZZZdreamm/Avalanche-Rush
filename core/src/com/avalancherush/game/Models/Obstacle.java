package com.avalancherush.game.Models;

import com.avalancherush.game.Enums.ObstacleType;
import com.avalancherush.game.Interfaces.Collidable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Obstacle extends Collidable {
    private ObstacleType type;
    private int track; // new thing - on which track is this obstacle
    private boolean jumpable;

    public Obstacle(){

    }

    public ObstacleType getType(){
        return type;
    }

    public void setType(ObstacleType type){
        this.type = type;
    }
    public int getTrack() {
        return track;
    }

    public void setTrack(int track) throws Exception {
        if (track >= 1 && track <= 5) {
            this.track = track;
        } else {
            throw new Exception("Invalid track number. Track number must be between 1 and 5.");
        }
    }

    public boolean getJumpable(){
        return jumpable;
    }

    public void setJumpable(boolean jumpable){
        this.jumpable = jumpable;
    }
}
