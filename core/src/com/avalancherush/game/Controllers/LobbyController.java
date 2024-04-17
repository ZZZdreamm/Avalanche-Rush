package com.avalancherush.game.Controllers;

import static com.avalancherush.game.Configuration.GlobalVariables.BASIC_GAME_SPEED;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Interfaces.RenderObserver;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.SinglePlayerGameThread;
import com.avalancherush.game.Views.GameViewMultiplayer;
import com.avalancherush.game.Views.MenuView;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.List;

public class LobbyController implements EventObserver{
    private SinglePlayerGameThread singlePlayerGameThread;
    @Override
    public void notify(EventType eventType, Object... object) {
        if(eventType == EventType.HOME_BUTTON_CLICK){
            MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
        } else if(eventType == EventType.GAME_MULTI_PLAYER) {
            MyAvalancheRushGame.INSTANCE.getMusicMenu().pause();
            MyAvalancheRushGame.INSTANCE.getMusicGame().play();
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
            this.singlePlayerGameThread = SinglePlayerGameThread.getInstance();
            GameThread.getInstance().gameSpeed = BASIC_GAME_SPEED;
            singlePlayerGameThread.obstacles = new Queue<>();
            singlePlayerGameThread.powerUps = new Queue<>();
            singlePlayerGameThread.gameScore = 0;
            MyAvalancheRushGame.INSTANCE.setScreen(new GameViewMultiplayer(player, eventObserverList, renderObserverList));
        }
    }
}
