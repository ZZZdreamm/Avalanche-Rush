package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Views.MultiPlayerView;
import com.avalancherush.game.Views.ProfileView;
import com.avalancherush.game.Views.SettingsView;
import com.avalancherush.game.Views.SinglePlayerView;
import com.badlogic.gdx.graphics.OrthographicCamera;

import javax.swing.SortOrder;

public class MainMenuController implements EventObserver {
    private GameThread gameThread;
    @Override
    public void notify(EventType eventType) {
        if(eventType == EventType.SINGLE_PLAYER_BUTTON_CLICK){
            MyAvalancheRushGame.INSTANCE.setScreen(new SinglePlayerView(gameThread.getCamera()));
        } else if (eventType == EventType.MULTIPLAYER_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new MultiPlayerView(gameThread.getCamera()));
        } else if (eventType == EventType.PROFILE_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new ProfileView(gameThread.getCamera()));
        } else if (eventType == EventType.SETTINGS_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new SettingsView(gameThread.getCamera()));
        }
    }
}
