package com.avalancherush.game.Singletons;

import com.avalancherush.game.FirebaseInterface;
import com.avalancherush.game.Server;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class MultiPlayerGameThread {
    private static MultiPlayerGameThread instance;
    private Server server;

    public static MultiPlayerGameThread getInstance() {
        if (instance == null) {
            instance = new MultiPlayerGameThread();
        }
        return instance;
    }

    public void setServer(Server server){
        this.server = server;
    }
    public Server getServer(){
        return this.server;
    }
}
