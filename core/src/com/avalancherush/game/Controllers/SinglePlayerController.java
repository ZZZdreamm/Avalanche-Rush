package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Interfaces.RenderObserver;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Views.GameMenuView;
import com.avalancherush.game.Views.GameViewSinglePlayer;
import com.avalancherush.game.Views.MenuView;
import com.avalancherush.game.Views.SinglePlayerView;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.View;

public class SinglePlayerController implements EventObserver {

    private static GameViewSinglePlayer gameViewSinglePlayer = null;
    @Override
    public void notify(EventType eventType, Object... object) {
        if(eventType == EventType.HOME_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
        } else if (eventType == EventType.GAME_SINGLE_PLAYER_CLICK) {
            GamePlayController gamePlayController = new GamePlayController();
            PlayerController playerController = new PlayerController();
            Player player = new Player();
            player.setTrack(2);
            player.setSkin(SkinType.BASIC);
            playerController.setPlayer(player);
            List<EventObserver> eventObserverList = new ArrayList<>();
            eventObserverList.add(gamePlayController);
            eventObserverList.add(playerController);
            List<RenderObserver> renderObserverList = new ArrayList<>();
            renderObserverList.add(gamePlayController);
            gameViewSinglePlayer = new GameViewSinglePlayer(player, eventObserverList, renderObserverList);

            MyAvalancheRushGame.INSTANCE.setScreen(gameViewSinglePlayer);
            MyAvalancheRushGame.INSTANCE.getMusicMenu().pause();
            MyAvalancheRushGame.INSTANCE.getMusicGame().play();
        }
    }
    public static GameViewSinglePlayer getGameViewSinglePlayer() {return gameViewSinglePlayer;}
    public static void setGameViewSinglePlayer(GameViewSinglePlayer gameViewSinglePlayer) {SinglePlayerController.gameViewSinglePlayer = gameViewSinglePlayer;}
}
