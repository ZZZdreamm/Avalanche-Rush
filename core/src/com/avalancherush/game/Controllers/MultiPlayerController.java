package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Views.GameViewMultiplayer;
import com.avalancherush.game.Views.GameViewSinglePlayer;
import com.avalancherush.game.Views.JoinView;
import com.avalancherush.game.Views.LobbyView;
import com.avalancherush.game.Views.MenuView;

public class MultiPlayerController implements EventObserver {
    @Override
    public void notify(EventType eventType) {
        if(eventType == EventType.HOME_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
        } else if (eventType == EventType.JOIN_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new JoinView());
        } else if (eventType == EventType.LOBBY_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new LobbyView());
        }
    }
}
