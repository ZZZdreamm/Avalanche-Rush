package com.avalancherush.game.Singletons;


import com.avalancherush.game.Interfaces.PlayerGameThread;

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
