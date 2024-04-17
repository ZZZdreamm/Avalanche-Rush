package com.avalancherush.game;

public interface FirebaseInterface {
    public void setValueToServerDataBase(String id, String key,String value );
    public void serverChangeListener(Server server);
    public void setValueToDataBase(String key, String value);
    public void idChangeListener(String key);

    public void ScoreChangeListener();

}
