package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.ObstacleType;
import com.avalancherush.game.Enums.PowerUpType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Models.Obstacle;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.Models.TakenPowerUp;
import com.avalancherush.game.Singletons.GameThread;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.PowerUpFactory;
import com.badlogic.gdx.Game;

import static com.avalancherush.game.Configuration.GlobalVariables.POWER_UP_HELMET_TIME;
import static com.avalancherush.game.Configuration.GlobalVariables.POWER_UP_SNOWBOARD_TIME;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.LANES;
import static com.avalancherush.game.Configuration.Textures.SINGLE_PLAYER;
import static com.avalancherush.game.Configuration.Textures.SINGLE_PLAYER_JUMPING;

import java.util.ArrayList;
import java.util.List;

public class PlayerController implements EventObserver {
    private PowerUpFactory powerUpFactory;
    private List<Player> players;
    public PlayerController() {
        this.powerUpFactory = PowerUpFactory.getInstance();
    }

    @Override
    public void notify(EventType eventType) {
        switch (eventType){
            case SLIDED_UP: {
                players.get(0).setJumping(true); // Set a flag indicating that the player is jumping
                players.get(0).setTexture(SINGLE_PLAYER_JUMPING); // Set the player's texture to a jumping texture
                // Schedule a task to reset the player's texture after a short delay (e.g., 0.5 seconds)
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        players.get(0).setJumping(false); // Reset the jumping flag
                        players.get(0).setTexture(SINGLE_PLAYER); // Reset the player's texture to the normal texture

                    }
                }, 10.0f); // Adjust the delay as needed
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
                for (TakenPowerUp takenPowerUp: players.get(0).getPowerUps()){
                    if(takenPowerUp.getPowerUpType() == PowerUpType.HELMET){
                        takenPowerUp.setTime(POWER_UP_HELMET_TIME);
                        break;
                    }
                }
                players.get(0).addPowerUp(powerUpFactory.givePlayerPowerUp(PowerUpType.HELMET, POWER_UP_HELMET_TIME));
                break;
            }
            case TAKE_UP_SNOWBOARD_POWER_UP: {
                for (TakenPowerUp takenPowerUp: players.get(0).getPowerUps()){
                    if(takenPowerUp.getPowerUpType() == PowerUpType.SNOWBOARD){
                        takenPowerUp.setTime(POWER_UP_SNOWBOARD_TIME);
                        break;
                    }
                }
                players.get(0).addPowerUp(powerUpFactory.givePlayerPowerUp(PowerUpType.SNOWBOARD, POWER_UP_SNOWBOARD_TIME));
                break;
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
