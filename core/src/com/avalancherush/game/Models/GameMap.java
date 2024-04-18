package com.avalancherush.game.Models;

import com.badlogic.gdx.utils.Queue;


public class GameMap {
    public Queue<Obstacle> obstacles;
    public Queue<PowerUp> powerUps;
    public GameMap(){
        obstacles = new Queue<>();
        powerUps = new Queue<>();
    }
}
