package com.avalancherush.game.Controllers;

import static com.avalancherush.game.Configuration.GlobalVariables.BASIC_GAME_SPEED;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Interfaces.RenderObserver;
import com.avalancherush.game.Models.JsonEditor;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.MultiPlayerGameThread;
import com.avalancherush.game.Singletons.SinglePlayerGameThread;
import com.avalancherush.game.Views.GameViewMultiplayer;
import com.avalancherush.game.Views.MenuView;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.List;

public class LobbyController implements EventObserver{
    private JsonEditor jsonEditor;
    public LobbyController(){
        this.jsonEditor = GameThread.getInstance().getJsonIntance();
    }
    @Override
    public void notify(EventType eventType, Object... object) {
        if(eventType == EventType.HOME_BUTTON_CLICK){
            MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
        } else if(eventType == EventType.GAME_MULTI_PLAYER) {
            MyAvalancheRushGame.INSTANCE.getMusicMenu().pause();
            MyAvalancheRushGame.INSTANCE.getMusicGame().play();
            GamePlayController gamePlayController = new GamePlayController(MultiPlayerGameThread.getInstance());
            PlayerController playerController = new PlayerController(MultiPlayerGameThread.getInstance());
            Player player = new Player();
            player.setTrack(2);
            player.setSkin(jsonEditor.getSkin() == "BASIC" ? SkinType.BASIC : SkinType.MASTER);
            playerController.setPlayer(player);
            List<EventObserver> eventObserverList = new ArrayList<>();
            eventObserverList.add(gamePlayController);
            eventObserverList.add(playerController);
            List<RenderObserver> renderObserverList = new ArrayList<>();
            renderObserverList.add(gamePlayController);
            renderObserverList.add(playerController);
            MyAvalancheRushGame.INSTANCE.setScreen(new GameViewMultiplayer(player, eventObserverList, renderObserverList));
        }
    }
}
