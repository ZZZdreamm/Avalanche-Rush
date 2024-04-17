package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Models.JsonEditor;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Views.HighestScore;
import com.avalancherush.game.Views.MenuView;

public class ProfileController implements EventObserver {
    private JsonEditor jsonEditor;

    public ProfileController(){
        this.jsonEditor = GameThread.getInstance().getJsonIntance();
    }

    @Override
    public void notify(EventType eventType, Object... objects) {
        if(eventType == EventType.HOME_BUTTON_CLICK){
            MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
        }else if(eventType == EventType.CHANGE_SKIN){
            Object object = objects[0];
            if(object instanceof SkinType){
                if(object == SkinType.BASIC){
                    jsonEditor.setSkin("BASIC");
                }else if(object == SkinType.MASTER){
                    jsonEditor.setSkin("MASTER");
                }
                GameThread.getInstance().setData(jsonEditor.getName(), jsonEditor.getSkin());
            }
        }
        else if (eventType == EventType.HIGH_SCORE){
            MyAvalancheRushGame.INSTANCE.setScreen(new HighestScore());
        }
    }
}
