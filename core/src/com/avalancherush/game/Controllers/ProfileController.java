package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Views.MenuView;
import com.avalancherush.game.Views.SinglePlayerView;

public class ProfileController implements EventObserver {

    @Override
    public void notify(EventType eventType, Object... objects) {
        if(eventType == EventType.HOME_BUTTON_CLICK){
            MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
        }else if(eventType == EventType.CHANGE_SKIN){
            Object object = objects[0];
            if(object instanceof SkinType){
                if(object == SkinType.BASIC){

                }else if(object == SkinType.MASTER){

                }
            }
        }
    }
}
