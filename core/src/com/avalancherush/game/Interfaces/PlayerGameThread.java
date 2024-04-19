package com.avalancherush.game.Interfaces;

import static com.avalancherush.game.Configuration.GlobalVariables.BASIC_GAME_SPEED;

import com.avalancherush.game.Configuration.GlobalVariables;
import com.avalancherush.game.Models.GameMap;
import com.avalancherush.game.Models.Player;

public abstract class PlayerGameThread {
    private GameMap gameMap;
    private Player player;
    public float gameScore;
    public float gameSpeed;
    public PlayerGameThread(){
        this.gameMap = new GameMap();
        this.gameScore = 0;
        this.gameSpeed = BASIC_GAME_SPEED;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
