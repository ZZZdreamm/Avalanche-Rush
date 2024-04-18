package com.avalancherush.game.Interfaces;

import com.avalancherush.game.Models.GameMap;

public abstract class PlayerGameThread {
    private GameMap gameMap;
    public float gameScore;
    public PlayerGameThread(){
        this.gameMap = new GameMap();
        this.gameScore = 0;
    }

    public GameMap getGameMap() {
        return gameMap;
    }
}
