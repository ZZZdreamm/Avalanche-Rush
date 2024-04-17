package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Views.GameViewSinglePlayer;
import com.avalancherush.game.Views.MenuView;
import com.avalancherush.game.Views.SinglePlayerView;

public class GameMenuController implements EventObserver {
    @Override
    public void notify(EventType eventType, Object... object) {
        if (eventType == EventType.HOME_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
            MyAvalancheRushGame.INSTANCE.getMusicGame().pause();
            MyAvalancheRushGame.INSTANCE.getMusicMenu().play();
        } else if (eventType == EventType.RESUME_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(SinglePlayerController.getGameViewSinglePlayer());
        } else if (eventType == EventType.VOLUME_UP) {
            MyAvalancheRushGame.INSTANCE.getMusicGame().play();
        } else if (eventType == EventType.VOLUME_DOWN) {
            MyAvalancheRushGame.INSTANCE.getMusicGame().pause();
        }
    }

}
