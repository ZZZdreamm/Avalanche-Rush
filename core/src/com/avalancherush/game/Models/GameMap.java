package com.avalancherush.game.Models;

import com.avalancherush.game.Interfaces.Drawable;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.List;


public class GameMap {

    private Image image;

    private List<Obstacle> obstacles;

    private List<PowerUp> powerUps;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void addObstacle(Obstacle obstacle) {
        this.obstacles.add(obstacle);
    }

    public void removeObstacle(int obstacleIdx) {
        obstacles.remove(obstacleIdx);
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void addPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    public void removePowerUp(int powerUpIdx) {
        powerUps.remove(powerUpIdx);
    }

    public void draw() {

    }
}
