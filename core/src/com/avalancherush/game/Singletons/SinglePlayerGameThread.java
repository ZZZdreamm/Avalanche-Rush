package com.avalancherush.game.Singletons;

import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Interfaces.PlayerGameThread;
import com.avalancherush.game.Models.GameMap;
import com.avalancherush.game.Models.Obstacle;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.Models.PowerUp;
import com.badlogic.gdx.utils.Queue;

public class SinglePlayerGameThread extends PlayerGameThread {
    private static SinglePlayerGameThread instance;
    public static SinglePlayerGameThread getInstance(){
        if(instance == null){
            instance = new SinglePlayerGameThread();
        }
        return instance;
    }

    private SinglePlayerGameThread(){
        super();
    }

}
