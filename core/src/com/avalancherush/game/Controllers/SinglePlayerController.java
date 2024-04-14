package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Views.GameMenuView;
import com.avalancherush.game.Views.GameViewSinglePlayer;
import com.avalancherush.game.Views.MenuView;
import com.avalancherush.game.Views.SinglePlayerView;

import javax.swing.text.View;

public class SinglePlayerController implements EventObserver {

    private static GameViewSinglePlayer gameViewSinglePlayer = null;
    @Override
    public void notify(EventType eventType) {
        if(eventType == EventType.HOME_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
        } else if (eventType == EventType.GAME_SINGLE_PLAYER_CLICK) {
            gameViewSinglePlayer = new GameViewSinglePlayer();
            MyAvalancheRushGame.INSTANCE.setScreen(gameViewSinglePlayer);
            MyAvalancheRushGame.INSTANCE.getMusicMenu().pause();
            MyAvalancheRushGame.INSTANCE.getMusicGame().play();
        }
    }
    public static GameViewSinglePlayer getGameViewSinglePlayer() {return gameViewSinglePlayer;}
    public static void setGameViewSinglePlayer(GameViewSinglePlayer gameViewSinglePlayer) {SinglePlayerController.gameViewSinglePlayer = gameViewSinglePlayer;}
}
