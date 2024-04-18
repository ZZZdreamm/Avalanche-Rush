package com.avalancherush.game.Singletons;

import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Models.GameMap;
import com.avalancherush.game.Models.Obstacle;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.Models.PowerUp;
import com.badlogic.gdx.utils.Queue;

public class SinglePlayerGameThread {
    private static SinglePlayerGameThread instance;
//    public Queue<Obstacle> obstacles;
//    public Queue<PowerUp> powerUps;
    private GameMap gameMap;
    public float gameScore;
    public static SinglePlayerGameThread getInstance(){
        if(instance == null){
            instance = new SinglePlayerGameThread();
        }
        return instance;
    }

    private SinglePlayerGameThread(){
//        this.obstacles = new Queue<>();
//        this.powerUps = new Queue<>();
        this.gameMap = new GameMap();
        this.gameScore = 0;
    }

    public GameMap getGameMap(){
        return this.gameMap;
    }
}
