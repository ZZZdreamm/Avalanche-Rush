package com.avalancherush.game.Singletons;

public class GameThread {
    private static GameThread instance;
    public GameThread getInstance(){
        if(instance == null){
            instance = new GameThread();
        }
        return instance;
    }
}
