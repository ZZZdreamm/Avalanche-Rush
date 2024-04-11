package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Views.GameMenuView;


public class GamePlayController implements EventObserver {
    @Override
    public void notify(EventType eventType) {
        if(eventType == EventType.GAME_MENU_BUTTON){
            MyAvalancheRushGame.INSTANCE.setScreen(new GameMenuView());
        }
    }

}
