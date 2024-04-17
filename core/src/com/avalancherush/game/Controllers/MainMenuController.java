package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Views.GameEndView;
import com.avalancherush.game.Views.GameMenuView;
import com.avalancherush.game.Views.MultiPlayerView;
import com.avalancherush.game.Views.ProfileView;
import com.avalancherush.game.Views.SettingsView;
import com.avalancherush.game.Views.SinglePlayerView;


public class MainMenuController implements EventObserver {
    @Override
    public void notify(EventType eventType, Object... object) {
        if(eventType == EventType.SINGLE_PLAYER_BUTTON_CLICK){
            MyAvalancheRushGame.INSTANCE.setScreen(new SinglePlayerView());
        } else if (eventType == EventType.MULTIPLAYER_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new MultiPlayerView());
        } else if (eventType == EventType.PROFILE_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new ProfileView());
        } else if (eventType == EventType.SETTINGS_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new SettingsView());
        }
    }
}
