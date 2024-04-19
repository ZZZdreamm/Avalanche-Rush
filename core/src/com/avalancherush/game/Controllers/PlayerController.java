package com.avalancherush.game.Controllers;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.PowerUpType;
import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Interfaces.PlayerGameThread;
import com.avalancherush.game.Interfaces.RenderObserver;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.Models.PowerUp;
import com.badlogic.gdx.utils.Timer;

import com.avalancherush.game.Singletons.PowerUpFactory;

import static com.avalancherush.game.Configuration.GlobalVariables.POWER_UP_HELMET_TIME;
import static com.avalancherush.game.Configuration.GlobalVariables.POWER_UP_SNOWBOARD_TIME;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.LANES;
import static com.avalancherush.game.Configuration.Textures.SINGLE_PLAYER;
import static com.avalancherush.game.Configuration.Textures.SINGLE_PLAYER_JUMPING;
import static com.avalancherush.game.Configuration.Textures.SKIN;
import static com.avalancherush.game.Configuration.Textures.SKIN_JUMP;

import java.util.ArrayList;
import java.util.List;

public class PlayerController implements RenderObserver, EventObserver {
    private PowerUpFactory powerUpFactory;
    private PlayerGameThread playerGameThread;
    private Player player;
    public PlayerController(PlayerGameThread playerGameThread) {
        this.powerUpFactory = PowerUpFactory.getInstance();
        this.playerGameThread = playerGameThread;
    }

    @Override
    public void notify(EventType eventType, Object... object) {
        switch (eventType){
            case SLIDED_UP: {
                player.setJumping(true);
                player.setTexture(player.getSkin() == SkinType.BASIC ? SINGLE_PLAYER_JUMPING : SKIN_JUMP);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        player.setJumping(false);
                        player.setTexture(player.getSkin() == SkinType.BASIC ? SINGLE_PLAYER : SKIN);

                    }
                }, 2.5f);
                break;
            }
            case SLIDED_LEFT: {
                player.setTrack(player.getTrack() - 1);
                player.getRectangle().x = LANES[player.getTrack()-1] - SINGLE_PLAYER_WIDTH/2;
                break;
            }
            case SLIDED_RIGHT: {
                if(player.getTrack() < 3){
                    player.setTrack(player.getTrack() + 1);
                    player.getRectangle().x = LANES[player.getTrack()-1] - SINGLE_PLAYER_WIDTH/2;
                    break;
                }
            }
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void notifyRender(float elapsedTime) {
        PowerUpType catchedPowerUpType = checkGettingPowerUp();
        if(catchedPowerUpType == PowerUpType.HELMET){
            playerTakesPowerUp(EventType.TAKE_UP_HELMET_POWER_UP);
        }else if(catchedPowerUpType == PowerUpType.SNOWBOARD){
            playerTakesPowerUp(EventType.TAKE_UP_SNOWBOARD_POWER_UP);
        }
        updatePlayersPowerUps(elapsedTime);
    }

    private void updatePlayersPowerUps(float elapsedTime){
        List<PowerUp> powerUpsToRemove = new ArrayList<>();
        for (PowerUp powerUp: player.getPowerUps()){
            powerUp.setTime(powerUp.getTime() - elapsedTime);
            if(powerUp.getTime() < 0){
                powerUpsToRemove.add(powerUp);
            }
        }
        for(PowerUp powerUpToRemove: powerUpsToRemove){
            player.getPowerUps().remove(powerUpToRemove);
        }
    }

    private void playerTakesPowerUp(EventType eventType){
        switch (eventType){
            case TAKE_UP_HELMET_POWER_UP: {
                for (PowerUp takenPowerUp: player.getPowerUps()){
                    if(takenPowerUp.getType() == PowerUpType.HELMET){
                        takenPowerUp.setTime(POWER_UP_HELMET_TIME);
                        return;
                    }
                }
                player.addPowerUp(powerUpFactory.givePlayerPowerUp(PowerUpType.HELMET, POWER_UP_HELMET_TIME));
                break;
            }
            case TAKE_UP_SNOWBOARD_POWER_UP: {
                for (PowerUp takenPowerUp: player.getPowerUps()){
                    if(takenPowerUp.getType() == PowerUpType.SNOWBOARD){
                        takenPowerUp.setTime(POWER_UP_SNOWBOARD_TIME);
                        return;
                    }
                }
                player.addPowerUp(powerUpFactory.givePlayerPowerUp(PowerUpType.SNOWBOARD, POWER_UP_SNOWBOARD_TIME));
                break;
            }
        }
    }

    private PowerUpType checkGettingPowerUp(){
        for(PowerUp powerUp: playerGameThread.getGameMap().powerUps){
            if(playerGameThread.getPlayer().collides(powerUp.getRectangle())){
                playerGameThread.getGameMap().powerUps.removeValue(powerUp, true);
                return powerUp.getType();
            }
        }
        return null;
    }
}
