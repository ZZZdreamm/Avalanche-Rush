package com.avalancherush.game.Singletons;

import com.avalancherush.game.FirebaseInterface;
import com.avalancherush.game.Interfaces.PlayerGameThread;
import com.avalancherush.game.Models.GameMap;
import com.avalancherush.game.Server;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class MultiPlayerGameThread extends PlayerGameThread {
    private static MultiPlayerGameThread instance;
    private Server server;
    private String gameid;

    public static MultiPlayerGameThread getInstance() {
        if (instance == null) {
            instance = new MultiPlayerGameThread();

        }
        return instance;
    }

    private MultiPlayerGameThread(){
        super();
    }

    public void setServer(Server server){
        this.server = server;
    }
    public Server getServer(){
        return this.server;
    }

    public void setGameId(String id){
        this.gameid = id;
    }
    public String getGameid(){
        return this.gameid;
    }
}
