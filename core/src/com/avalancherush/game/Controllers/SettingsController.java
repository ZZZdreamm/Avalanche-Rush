package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Views.MenuView;

public class SettingsController implements EventObserver {

    @Override
    public void notify(EventType eventType, Object... object) {
        if(eventType == EventType.HOME_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
        } else if (eventType == EventType.VOLUME_UP) {
            MyAvalancheRushGame.INSTANCE.getMusicMenu().play();
        } else if (eventType == EventType.VOLUME_DOWN) {
            MyAvalancheRushGame.INSTANCE.getMusicMenu().pause();
        }
    }
}
