package com.avalancherush.game.Singletons;


import com.avalancherush.game.FirebaseInterface;

import com.avalancherush.game.Models.JsonEditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Json;


public class GameThread {
    private static GameThread instance;
    private OrthographicCamera camera;
    private FirebaseInterface database;
    private JsonEditor jsonInstance;
    public String score1Name, score2Name, score3Name;
    public String score1Score, score2Score, score3Score;

    public static GameThread getInstance() {
        if (instance == null) {
            instance = new GameThread();
            FileHandle file = Gdx.files.local("assets/data.json");
            Json json = new Json();
            JsonEditor jsonStr = json.fromJson(JsonEditor.class, file);
            instance.setJsonInstance(jsonStr);
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
        this.database = database;
    }

    public FirebaseInterface getDatabase() {
        return this.database;
    }

    public JsonEditor getJsonIntance() {
        return this.jsonInstance;
    }

    public void setJsonInstance(JsonEditor instance) {
        this.jsonInstance = instance;
    }

    public void setData(String name, String skin) {
        FileHandle file = Gdx.files.local("assets/data.json");
        Json json = new Json();
//            FileHandle file = Gdx.files.local("data.json");
        instance.getJsonIntance().setName(name);
        instance.getJsonIntance().setSkin(skin);
        System.out.println(json.prettyPrint(instance.jsonInstance));
        String jsonStr = json.toJson(instance.getJsonIntance());
        file.writeString(jsonStr, false);
    }

    public void UpdateHighScore(){
        float score = SinglePlayerGameThread.getInstance().gameScore;
        if(score > Integer.parseInt(score1Score)){
            System.out.println("1----------------------------------------------------------->");
            database.setValueToDataBase("score3/name", score2Name);
            database.setValueToDataBase("score3/score", score2Score);
            database.setValueToDataBase("score2/name", score1Name);
            database.setValueToDataBase("score2/score", score1Score);
            database.setValueToDataBase("score1/name", this.jsonInstance.getName());
            database.setValueToDataBase("score1/score", String.valueOf((int)(score)));
        }
        else if(score > Integer.parseInt(score2Score)){

            System.out.println("2----------------------------------------------------------->");
            database.setValueToDataBase("score3/name", score2Name);
            database.setValueToDataBase("score3/score", score2Score);
            database.setValueToDataBase("score2/name", this.jsonInstance.getName());
            database.setValueToDataBase("score2/score", String.valueOf((int)(score)));
        }
        else if(score > Integer.parseInt(score3Score)){

            System.out.println("3----------------------------------------------------------->");
            database.setValueToDataBase("score3/name", this.jsonInstance.getName());
            database.setValueToDataBase("score3/score", String.valueOf((int)(score)));
        }
        database.ScoreChangeListener();
    }
}
