package com.avalancherush.game.Singletons;

import com.avalancherush.game.FirebaseInterface;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameThread {
    private static GameThread instance;
    private OrthographicCamera camera;
    private FirebaseInterface database;
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
    public void setDatabase(FirebaseInterface database) {
        System.out.println("Gdshbds...........................................");
        System.out.println(database.hashCode());
        this.database = database;
    }
    public FirebaseInterface getDatabase() {
        return this.database;
    }
}
