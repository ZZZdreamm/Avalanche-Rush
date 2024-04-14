package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.PowerUpType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.PowerUpFactory;
import com.badlogic.gdx.Game;

import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.LANES;

import java.util.List;

public class PlayerController implements EventObserver {
    private PowerUpFactory powerUpFactory;
    private List<Player> players;

    public PlayerController(){
        this.powerUpFactory = PowerUpFactory.getInstance();
    }
    @Override
    public void notify(EventType eventType) {
        switch (eventType){
            case SLIDED_UP: {
                break;
            }
            case SLIDED_LEFT: {
                players.get(0).setTrack(players.get(0).getTrack() - 1);
                players.get(0).getRectangle().x = LANES[players.get(0).getTrack()-1] - SINGLE_PLAYER_WIDTH/2;
                break;
            }
            case SLIDED_RIGHT: {
                if(players.get(0).getTrack() < 3){
                    players.get(0).setTrack(players.get(0).getTrack() + 1);
                    players.get(0).getRectangle().x = LANES[players.get(0).getTrack()-1] - SINGLE_PLAYER_WIDTH/2;
                    break;
                }
            }
            case TAKE_UP_HELMET_POWER_UP: {
                players.get(0).addPowerUp(powerUpFactory.givePlayerPowerUp(PowerUpType.HELMET, 60));
            }
            case TAKE_UP_SNOWBOARD_POWER_UP: {
                players.get(0).addPowerUp(powerUpFactory.givePlayerPowerUp(PowerUpType.SNOWBOARD, 60));
            }
        }
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public List<Player> getPlayers() {
        return players;
    }
}
