package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Views.GameViewSinglePlayer;
import com.avalancherush.game.Views.MenuView;

public class GameMenuController implements EventObserver {
    @Override
    public void notify(EventType eventType) {
        if(eventType == EventType.HOME_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
        } else if (eventType == EventType.RESUME_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new GameViewSinglePlayer());
        } else if (eventType == EventType.VOLUME_UP) {
            MyAvalancheRushGame.INSTANCE.getMusic().play();
        } else if (eventType == EventType.VOLUME_DOWN) {
            MyAvalancheRushGame.INSTANCE.getMusic().pause();
        }
    }

}
