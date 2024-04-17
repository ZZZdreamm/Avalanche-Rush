package com.avalancherush.game.Models;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

import com.badlogic.gdx.utils.Json;

import java.io.FileWriter;
import java.io.IOException;

public class JsonEditor {

    public JsonEditor(){

    }
    private String name, skin;
    public void setName(String name){
        this.name = name;
    }
    public void setSkin(String skin){
        this.skin = skin;
    }
    public String  getName(){
        return this.name;
    }
    public String getSkin(){
        return this.skin;
    }
//    public void SaveJson(JsonEditor instance){
//        Json json = new Json();
//        try (FileWriter file = new FileWriter("data.json")) {
//            String jsonStr = json.toJson(instance);
//            file.write(jsonStr);
//            System.out.println("Data has been written to file.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
