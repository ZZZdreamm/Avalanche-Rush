package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Models.Player;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.LANES;

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
        }
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public List<Player> getPlayers() {
        return players;
    }
}
