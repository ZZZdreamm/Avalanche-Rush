package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Views.MenuView;

public class JoinController implements EventObserver {

    @Override
    public void notify(EventType eventType) {
        if(eventType == EventType.HOME_BUTTON_CLICK){
            MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
        }
    }
}
