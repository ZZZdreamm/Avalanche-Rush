package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.ObstacleType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Models.Obstacle;
import com.avalancherush.game.Models.Player;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.LANES;
import static com.avalancherush.game.Configuration.Textures.SINGLE_PLAYER;
import static com.avalancherush.game.Configuration.Textures.SINGLE_PLAYER_JUMPING;

import java.util.List;

public class PlayerController implements EventObserver {
    private List<Player> players;

    private List<Obstacle> obstacles;

    @Override
    public void notify(EventType eventType) {
        switch (eventType){
            case SLIDED_UP: {

                // 1. usunąć na ten moment obstacle w tym miesjcu
                // 2. wstawić playera
                // 3. prywrócić player i obstacle

                // to chyba do kontrollera gry, bo tam musi zostać usunięty ten obstacle
                Obstacle obstacleToRemove = null;
                Rectangle playerRectangle = players.get(0).getRectangle();
                // skąd wziac te obstacles ???
                // obstacles =
                for (Obstacle obstacle : obstacles) {
                    if (obstacle.getRectangle().overlaps(playerRectangle) && obstacle.getType() == ObstacleType.ROCK) {
                        obstacleToRemove = obstacle;
                        break; // No need to continue searching if we found one obstacle
                    }
                }
                // Remove the obstacle temporarily
                if (obstacleToRemove != null) {
                    obstacles.remove(obstacleToRemove);
                }

                players.get(0).setJumping(true); // Set a flag indicating that the player is jumping
                players.get(0).setTexture(SINGLE_PLAYER_JUMPING); // Set the player's texture to a jumping texture
                // Schedule a task to reset the player's texture after a short delay (e.g., 0.5 seconds)
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        players.get(0).setJumping(false); // Reset the jumping flag
                        players.get(0).setTexture(SINGLE_PLAYER); // Reset the player's texture to the normal texture

                        if (obstacleToRemove != null) {
                            obstacles.add(obstacleToRemove);
                        }
                    }
                }, 0.5f); // Adjust the delay as needed
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
