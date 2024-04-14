package com.avalancherush.game;

public class Server {
    public String id;
    public String playerA, playerB;
    public int playerAScore, playerBScore;
    public String playerAStatus, playerBStatus;
    public String CurrentPlayer;
    public Server(String id){
        this.id = id;
        playerAScore = 0;
        playerBScore = 0;
        playerAStatus = "False";
        playerBStatus = "False";
        playerA = "";
        playerB = "";
    }
}
