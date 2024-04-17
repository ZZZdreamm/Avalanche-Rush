package com.avalancherush.game.Controllers;

import static com.avalancherush.game.Configuration.GlobalVariables.LANES;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_ROCK_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_TREE_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.POWER_UP_HELMET_TIME;
import static com.avalancherush.game.Configuration.GlobalVariables.POWER_UP_SNOWBOARD_TIME;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_HEIGHT;
import static com.badlogic.gdx.math.MathUtils.random;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.ObstacleType;
import com.avalancherush.game.Enums.PowerUpType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Interfaces.RenderObserver;
import com.avalancherush.game.Models.Obstacle;
import com.avalancherush.game.Models.PowerUp;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.ObstacleFactory;
import com.avalancherush.game.Singletons.PowerUpFactory;
import com.avalancherush.game.Singletons.SinglePlayerGameThread;
import com.avalancherush.game.Views.GameMenuView;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Queue;


public class GamePlayController implements EventObserver, RenderObserver {
    private GameThread gameThread;
    private SinglePlayerGameThread singlePlayerGameThread;
    private ObstacleFactory obstacleFactory;
    private PowerUpFactory powerUpFactory;
    private int obstaclesSpawned = 0;
    private int lastTrackObstacleSpawned;
    private int obstaclesThreshold, obstaclesPerPowerUp;
    public GamePlayController(){
        this.gameThread = GameThread.getInstance();
        this.singlePlayerGameThread = SinglePlayerGameThread.getInstance();
        this.obstacleFactory = ObstacleFactory.getInstance();
        this.powerUpFactory = PowerUpFactory.getInstance();
        this.lastTrackObstacleSpawned = 0;
        this.obstaclesThreshold = 10;
        this.obstaclesPerPowerUp = 10;
    }
    @Override
    public void notify(EventType eventType, Object... object) {
        if(eventType == EventType.GAME_MENU_BUTTON){
            MyAvalancheRushGame.INSTANCE.setScreen(new GameMenuView());
        }
    }
    @Override
    public void notifyRender(float elapsedTime) {
        generateObstacle(elapsedTime);
        generatePowerUp(elapsedTime);
    }

    public void generateObstacle(float time){
        int size = singlePlayerGameThread.obstacles.size;

        Queue<Obstacle> obstacleTemp = new Queue<>();

        Obstacle head = new Obstacle();
        Rectangle rectangle = new Rectangle(0,0,0,0);
        head.setRectangle(rectangle);
        while(!singlePlayerGameThread.obstacles.isEmpty()){
            head = singlePlayerGameThread.obstacles.removeFirst();
            Rectangle headRectangle = head.getRectangle();
            if(headRectangle.y > -50){
                head.getRectangle().y = headRectangle.y - time * gameThread.gameSpeed;
                obstacleTemp.addLast(head);
            }
        }

        if (size<obstaclesThreshold && head.getRectangle().y < (MyAvalancheRushGame.INSTANCE.getScreenHeight() - SINGLE_PLAYER_HEIGHT - OBSTACLE_HEIGHT - 50)) {
            int track;
            do{
                track = random.nextInt(3) + 1;
            }while (track == lastTrackObstacleSpawned);

            Obstacle newObstacle;
            if(random.nextInt(2) == 1){
                newObstacle = obstacleFactory.createObstacle(ObstacleType.ROCK, track, LANES[track - 1] - OBSTACLE_ROCK_WIDTH/2, MyAvalancheRushGame.INSTANCE.getScreenHeight());
            }else{
                newObstacle = obstacleFactory.createObstacle(ObstacleType.TREE, track, LANES[track - 1] - OBSTACLE_TREE_WIDTH/2, MyAvalancheRushGame.INSTANCE.getScreenHeight());
            }
            lastTrackObstacleSpawned = newObstacle.getTrack();

            obstacleTemp.addLast(newObstacle);
            obstaclesSpawned++;
        }

        singlePlayerGameThread.obstacles = obstacleTemp;
    }

    public void generatePowerUp(float time){
        Queue<PowerUp> powerUpTemp = new Queue<>();

        PowerUp head = new PowerUp();
        Rectangle rectangle = new Rectangle(0,0,0,0);
        head.setRectangle(rectangle);
        while(!singlePlayerGameThread.powerUps.isEmpty()){
            head = singlePlayerGameThread.powerUps.removeFirst();
            Rectangle headRectangle = head.getRectangle();
            if(headRectangle.y > -50){
                head.getRectangle().y = headRectangle.y - time * gameThread.gameSpeed;
                powerUpTemp.addLast(head);
            }
        }

        if(obstaclesSpawned >= obstaclesPerPowerUp) {
            int track;
            do {
                track = random.nextInt(3) + 1;
            } while (track == lastTrackObstacleSpawned);

            PowerUp newPowerUp;
            if (random.nextInt(2) == 1) {
                newPowerUp = powerUpFactory.createPowerUp(PowerUpType.HELMET, track, LANES[track - 1] - OBSTACLE_ROCK_WIDTH / 2, MyAvalancheRushGame.INSTANCE.getScreenHeight(), POWER_UP_HELMET_TIME);
            } else {
                newPowerUp = powerUpFactory.createPowerUp(PowerUpType.SNOWBOARD, track, LANES[track - 1] - OBSTACLE_TREE_WIDTH / 2, MyAvalancheRushGame.INSTANCE.getScreenHeight(), POWER_UP_SNOWBOARD_TIME);
            }

            powerUpTemp.addLast(newPowerUp);
            obstaclesSpawned = 0;
        }

        singlePlayerGameThread.powerUps = powerUpTemp;
    }

}
