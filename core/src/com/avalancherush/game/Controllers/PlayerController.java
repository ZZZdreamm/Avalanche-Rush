package com.avalancherush.game.Controllers;

import com.avalancherush.game.Configuration.Textures;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.ObstacleType;
import com.avalancherush.game.Enums.PowerUpType;
import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Models.Obstacle;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.Models.TakenPowerUp;
import com.avalancherush.game.Singletons.GameThread;
import com.badlogic.gdx.graphics.Texture;
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
import static com.avalancherush.game.Configuration.Textures.SKIN;
import static com.avalancherush.game.Configuration.Textures.SKIN_JUMP;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PlayerController implements EventObserver {
    private PowerUpFactory powerUpFactory;
    private Player player;
    public PlayerController() {
        this.powerUpFactory = PowerUpFactory.getInstance();
    }

    @Override
    public void notify(EventType eventType, Object... object) {
        switch (eventType){
            case SLIDED_UP: {
                player.setJumping(true);
//                Texture textureBeforeJump = player.getTexture();
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
            case TAKE_UP_HELMET_POWER_UP: {
                for (TakenPowerUp takenPowerUp: player.getPowerUps()){
                    if(takenPowerUp.getPowerUpType() == PowerUpType.HELMET){
                        takenPowerUp.setTime(POWER_UP_HELMET_TIME);
                        return;
                    }
                }
                player.addPowerUp(powerUpFactory.givePlayerPowerUp(PowerUpType.HELMET, POWER_UP_HELMET_TIME));
                break;
            }
            case TAKE_UP_SNOWBOARD_POWER_UP: {
                for (TakenPowerUp takenPowerUp: player.getPowerUps()){
                    if(takenPowerUp.getPowerUpType() == PowerUpType.SNOWBOARD){
                        takenPowerUp.setTime(POWER_UP_SNOWBOARD_TIME);
                        return;
                    }
                }
                player.addPowerUp(powerUpFactory.givePlayerPowerUp(PowerUpType.SNOWBOARD, POWER_UP_SNOWBOARD_TIME));
                break;
            }
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
