package com.avalancherush.game.Models;

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
}
