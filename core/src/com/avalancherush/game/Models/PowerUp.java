package com.avalancherush.game.Models;

import com.avalancherush.game.Enums.PowerUpType;
import com.avalancherush.game.Interfaces.Collidable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PowerUp extends Collidable {

    public PowerUp(){

    }
    private PowerUpType type;

    private int track;

    private int time;
    private Image image;

    public PowerUpType getType() {
        return type;
    }

    public void setType(PowerUpType type) {
        this.type = type;
    }

    public int getTime() { return time; }

    public void setTime(int time) {
        this.time = time;
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
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
