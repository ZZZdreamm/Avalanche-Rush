package com.avalancherush.game.Models;

import com.avalancherush.game.Interfaces.Drawable;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Queue;


public class GameMap extends Drawable {
    public Queue<Obstacle> obstacles;
    public Queue<PowerUp> powerUps;
    public GameMap(){
        obstacles = new Queue<>();
        powerUps = new Queue<>();
    }

    @Override
    public void draw(SpriteBatch batch){
        for(Obstacle obstacle: obstacles){
            obstacle.draw(batch);
        }
        for (PowerUp powerUp: powerUps){
            powerUp.draw(batch);
        }
    }
}
