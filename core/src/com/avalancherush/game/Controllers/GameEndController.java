package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Views.GameViewSinglePlayer;
import com.avalancherush.game.Views.MenuView;

public class GameEndController implements EventObserver {
    @Override
    public void notify(EventType eventType) {
        if(eventType == EventType.HOME_BUTTON_CLICK) {
            SinglePlayerController.setGameViewSinglePlayer(null);
            MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
        }
    }
}
