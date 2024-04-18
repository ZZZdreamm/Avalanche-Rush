package com.avalancherush.game.Models;

import com.avalancherush.game.Interfaces.Drawable;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.badlogic.gdx.utils.Queue;


public class GameMap {
    public Queue<Obstacle> obstacles;
    public Queue<PowerUp> powerUps;
    public GameMap(){
        obstacles = new Queue<>();
        powerUps = new Queue<>();
    }
}
