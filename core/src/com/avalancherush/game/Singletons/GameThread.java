package com.avalancherush.game.Singletons;


import com.avalancherush.game.FirebaseInterface;

import com.avalancherush.game.Models.Obstacle;
import com.avalancherush.game.Models.PowerUp;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Queue;


public class GameThread {
    private static GameThread instance;
    private OrthographicCamera camera;
    private FirebaseInterface database;
    public float gameSpeed;
    public static GameThread getInstance(){
        if(instance == null){
            instance = new GameThread();
        }
        return instance;
    }

    private GameThread(){
        this.gameSpeed = 150;
    }
    public void setCamera(OrthographicCamera inputCamera) {
        camera = inputCamera;
    }
    public OrthographicCamera getCamera() {
        return camera;
    }
    public void setDatabase(FirebaseInterface database) {
        this.database = database;
    }
    public FirebaseInterface getDatabase() {
        return this.database;
    }
}
