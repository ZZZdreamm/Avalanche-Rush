package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Interfaces.RenderObserver;
import com.avalancherush.game.Models.JsonEditor;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.SinglePlayerGameThread;
import com.avalancherush.game.Views.GameViewSinglePlayer;
import com.avalancherush.game.Views.MenuView;

import java.util.ArrayList;
import java.util.List;

public class SinglePlayerController implements EventObserver {

    private static GameViewSinglePlayer gameViewSinglePlayer = null;
    private JsonEditor jsonEditor;
    public SinglePlayerController(){
        this.jsonEditor = GameThread.getInstance().getJsonIntance();
    }
    @Override
    public void notify(EventType eventType, Object... object) {
        if(eventType == EventType.HOME_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
        } else if (eventType == EventType.GAME_SINGLE_PLAYER_CLICK) {
            GamePlayController gamePlayController = new GamePlayController(SinglePlayerGameThread.getInstance());
            PlayerController playerController = new PlayerController(SinglePlayerGameThread.getInstance());
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
            gameViewSinglePlayer = new GameViewSinglePlayer(player, eventObserverList, renderObserverList);

            MyAvalancheRushGame.INSTANCE.setScreen(gameViewSinglePlayer);
            MyAvalancheRushGame.INSTANCE.getMusicMenu().pause();
            MyAvalancheRushGame.INSTANCE.getMusicGame().play();
        }
    }
    public static GameViewSinglePlayer getGameViewSinglePlayer() {return gameViewSinglePlayer;}
}
