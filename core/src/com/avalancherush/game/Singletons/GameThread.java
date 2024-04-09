package com.avalancherush.game.Singletons;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameThread {
    private static GameThread instance;
    private OrthographicCamera camera;
    public static GameThread getInstance(){
        if(instance == null){
            instance = new GameThread();
        }
        return instance;
    }

    public void setCamera(OrthographicCamera inputCamera) {
        camera = inputCamera;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
