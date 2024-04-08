package com.avalancherush.game.Models;

import com.avalancherush.game.Interfaces.Collidable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Obstacle extends Collidable {

    private int type;

    private int track; // new thing - on which track is this obstacle

    private Image image;

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
