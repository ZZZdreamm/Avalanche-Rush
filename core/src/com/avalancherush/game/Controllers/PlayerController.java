package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Models.Player;

import java.util.List;

public class PlayerController implements EventObserver {
    private List<Player> players;
    @Override
    public void notify(EventType eventType) {
        switch (eventType){
            case SLIDED_UP: {
                break;
            }
            case SLIDED_LEFT: {

            }
            case SLIDED_RIGHT: {

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
