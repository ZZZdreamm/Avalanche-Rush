package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_ROCK_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_TREE_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.POWER_UP_HELMET_TIME;
import static com.avalancherush.game.Configuration.GlobalVariables.POWER_UP_SNOWBOARD_TIME;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.LANES;
import static com.avalancherush.game.Configuration.Textures.LINE;
import static com.avalancherush.game.Configuration.Textures.MENU_BUTTON;
import static com.avalancherush.game.Configuration.Textures.SCOREBOARD;
import static com.avalancherush.game.Configuration.Textures.SINGLE_PLAYER;
import static com.badlogic.gdx.math.MathUtils.random;
import com.avalancherush.game.Controllers.GamePlayController;
import com.avalancherush.game.Controllers.PlayerController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.ObstacleType;
import com.avalancherush.game.Enums.PowerUpType;
import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Interfaces.RenderNotifier;
import com.avalancherush.game.Interfaces.RenderObserver;
import com.avalancherush.game.Models.Obstacle;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.Models.PowerUp;
import com.avalancherush.game.Models.TakenPowerUp;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.ObstacleFactory;
import com.avalancherush.game.Singletons.PowerUpFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GameViewSinglePlayer extends RenderNotifier {
    private GameThread gameThread;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private float scoreboardX, scoreboardY, totaltime;
    private Player player;
    private Rectangle menuButton;
    private Vector3 initialTouchPos = new Vector3();
    private float gameSpeed;

    private long lastTouchTime = 0;                                 ///////// new
    private static final long DOUBLE_TAP_TIME_DELTA = 200;          ///////// new

    public GameViewSinglePlayer() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = GameThread.getInstance().getCamera();
        this.orthographicCamera.position.set(new Vector3((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2, (float)MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2,0 ));
        this.batch = new SpriteBatch();


        this.scoreboardX = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() - (SCOREBOARD.getWidth() / 2) - 10);
        this.scoreboardY = (float) (MyAvalancheRushGame.INSTANCE.getScreenHeight() - (SCOREBOARD.getHeight() / 2) - 10);

        LANES[0] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() / 6);
        LANES[1] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2);
        LANES[2] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() * 5 / 6);

        this.player = new Player();
        this.player.setTrack(2);
        this.player.setSkin(SkinType.BASIC);
//        this.player.setTexture(new Texture((Gdx.files.internal("ski_spritesheet.png"))));
        this.player.setTexture(SINGLE_PLAYER);
        float playerY = (float)this.player.getTexture().getHeight()/2;
        float playerX = LANES[1] - SINGLE_PLAYER_WIDTH/2;
        Rectangle rectangle = new Rectangle(playerX, playerY, SINGLE_PLAYER_WIDTH, SINGLE_PLAYER_HEIGHT);
        this.player.setRectangle(rectangle);

        this.totaltime = 0;

        this.menuButton = new Rectangle(10, MyAvalancheRushGame.INSTANCE.getScreenHeight() - MENU_BUTTON.getHeight() - 10, MENU_BUTTON.getWidth(), MENU_BUTTON.getHeight());

        GamePlayController gamePlayController = new GamePlayController();
        PlayerController playerController = new PlayerController();
        playerController.setPlayers(Collections.singletonList(this.player));
        observers = new ArrayList<>();
        observers.add(gamePlayController);
        observers.add(playerController);
        renderObservers = new ArrayList<>();
        renderObservers.add(gamePlayController);

    }

    @Override
    public void render(float dt) {
        show();
        boolean collision = checkCollision();
        if(collision){
            MyAvalancheRushGame.INSTANCE.setScreen(new GameEndView());
            MyAvalancheRushGame.INSTANCE.getMusicGame().pause();
            MyAvalancheRushGame.INSTANCE.getMusicMenu().play();
        }
        PowerUpType catchedPowerUpType = checkGettingPowerUp();
        if(catchedPowerUpType == PowerUpType.HELMET){
            notifyObservers(Collections.singletonList(observers.get(1)), EventType.TAKE_UP_HELMET_POWER_UP);
        }else if(catchedPowerUpType == PowerUpType.SNOWBOARD){
            notifyObservers(Collections.singletonList(observers.get(1)), EventType.TAKE_UP_SNOWBOARD_POWER_UP);
        }
        float elapsedTime = Gdx.graphics.getDeltaTime();
        float vehicleMultiplier = 1;
        for (TakenPowerUp powerUp: player.getPowerUps()){
            powerUp.setTime(powerUp.getTime() - elapsedTime);
            if(powerUp.getTime() < 0){
                player.getPowerUps().remove(powerUp);
            }
            if(powerUp.getPowerUpType() == PowerUpType.SNOWBOARD){
                vehicleMultiplier = 1.50f;
            }
        }

        totaltime += elapsedTime;
        gameThread.gameSpeed = totaltime+50 > gameThread.gameSpeed ? (totaltime+50) * vehicleMultiplier : gameThread.gameSpeed * vehicleMultiplier;
        notifyRenderObservers(renderObservers, elapsedTime);
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();
        for(Obstacle obstacle: gameThread.obstacles){
            obstacle.draw(batch);
        }
        for (PowerUp powerUp: gameThread.powerUps){
            powerUp.draw(batch);
        }

        player.draw(batch);

        batch.draw(LINE,MyAvalancheRushGame.INSTANCE.getScreenWidth()/3, 0 );
        batch.draw(LINE,MyAvalancheRushGame.INSTANCE.getScreenWidth()*2/3, 0 );
        batch.draw(SCOREBOARD, scoreboardX, scoreboardY, 100, 50);
        batch.draw(MENU_BUTTON, menuButton.x, menuButton.y);
        batch.end();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                orthographicCamera.unproject(touchPos);

                Rectangle playerRectangle = player.getRectangle();
                if(screenX < playerRectangle.x){
                    notifyObservers(Collections.singletonList(observers.get(1)), EventType.SLIDED_LEFT);
                } else if(screenX > playerRectangle.x){
                    notifyObservers(Collections.singletonList(observers.get(1)), EventType.SLIDED_RIGHT);
                }

                long currentTime = System.currentTimeMillis();
                if (currentTime - lastTouchTime < DOUBLE_TAP_TIME_DELTA) {
                    notifyObservers(Collections.singletonList(observers.get(1)), EventType.SLIDED_UP);
                }
                lastTouchTime = currentTime;

                if (menuButton.contains(touchPos.x, touchPos.y)) {
                    notifyObservers(observers, EventType.GAME_MENU_BUTTON);
                    return true;
                }
                return true;
            }

            // addjust strenght of sliding --- have to connect to a phone
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                Vector3 currentTouchPos = new Vector3(screenX, screenY, 0);
                orthographicCamera.unproject(currentTouchPos);

                float deltaX = currentTouchPos.x - initialTouchPos.x;   //// to chyab musi być na coś ustawiane to inital
                float deltaY = currentTouchPos.y - initialTouchPos.y;

                // Set a threshold to consider it a slide
                float slideThreshold = 150; // Adjust this threshold as needed

//                if (Math.abs(deltaX) > slideThreshold || Math.abs(deltaY) > slideThreshold) {
//                    Rectangle playerRectangle = player.getRectangle();
//                    if (deltaX < 0) {
//                        notifyObservers(Collections.singletonList(observers.get(1)), EventType.SLIDED_LEFT);
//                    } else if (deltaX > 0) {
//                        notifyObservers(Collections.singletonList(observers.get(1)), EventType.SLIDED_RIGHT);
//                    } else if (Math.abs(deltaY) > slideThreshold && deltaY > 0)  {
//                        notifyObservers(Collections.singletonList(observers.get(1)), EventType.SLIDED_UP);
//                        initialTouchPos.y = screenY; /// ?
//                    }
//                    initialTouchPos.set(currentTouchPos);
//                }

                return true;
            }

        });
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
    public boolean checkCollision(){
        for(Obstacle obstacle: gameThread.obstacles){
            if(obstacle.getTrack() != player.getTrack()){
                continue;
            }
            if(player.collides(obstacle.getRectangle())){
                if(obstacle.getType() == ObstacleType.ROCK){
                    if (player.getJumping()){
                        return false;
                    }
                    for (TakenPowerUp powerUp : player.getPowerUps()){
                        if(powerUp.getPowerUpType() == PowerUpType.HELMET){
                            gameThread.obstacles.removeValue(obstacle, true);
                            player.removePowerUp(powerUp);
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
    public PowerUpType checkGettingPowerUp(){
        for(PowerUp powerUp: gameThread.powerUps){
            if(player.collides(powerUp.getRectangle())){
                gameThread.powerUps.removeValue(powerUp, true);
                return powerUp.getType();
            }
        }
        return null;
    }


}

