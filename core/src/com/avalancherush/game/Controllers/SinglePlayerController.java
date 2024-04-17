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
import com.avalancherush.game.Singletons.SinglePlayerGameThread;
import com.avalancherush.game.Views.GameMenuView;
import com.avalancherush.game.Views.GameViewSinglePlayer;
import com.avalancherush.game.Views.MenuView;
import com.avalancherush.game.Views.SinglePlayerView;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.View;

public class SinglePlayerController implements EventObserver {

    private static GameViewSinglePlayer gameViewSinglePlayer = null;
    private GameThread gameThread;
    private SinglePlayerGameThread singlePlayerGameThread;
    private JsonEditor jsonEditor;
    public SinglePlayerController(){
        this.gameThread = GameThread.getInstance();
        this.singlePlayerGameThread = SinglePlayerGameThread.getInstance();
        this.jsonEditor = GameThread.getInstance().getJsonIntance();
    }
    @Override
    public void notify(EventType eventType, Object... object) {
        if(eventType == EventType.HOME_BUTTON_CLICK) {
            MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
        } else if (eventType == EventType.GAME_SINGLE_PLAYER_CLICK) {
            gameThread.gameSpeed = BASIC_GAME_SPEED;
            singlePlayerGameThread.obstacles = new Queue<>();
            singlePlayerGameThread.powerUps = new Queue<>();
            singlePlayerGameThread.gameScore = 0;
            GamePlayController gamePlayController = new GamePlayController();
            PlayerController playerController = new PlayerController();
            Player player = new Player();
            player.setTrack(2);
            player.setSkin(jsonEditor.getSkin() == "BASIC" ? SkinType.BASIC : SkinType.MASTER);
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
