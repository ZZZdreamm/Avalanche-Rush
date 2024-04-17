package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.Fonts.BIG_BLACK_FONT;
import static com.avalancherush.game.Configuration.GlobalVariables.LANES;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_ROCK_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_TREE_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.POWER_UP_HELMET_TIME;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.heightScale;
import static com.avalancherush.game.Configuration.GlobalVariables.widthScale;
import static com.avalancherush.game.Configuration.Textures.LINE;
import static com.avalancherush.game.Configuration.Textures.MENU_BUTTON;
import static com.avalancherush.game.Configuration.Textures.SCOREBOARD;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;
import static com.badlogic.gdx.math.MathUtils.random;

import com.avalancherush.game.Configuration.Textures;
import com.avalancherush.game.Controllers.GamePlayController;
import com.avalancherush.game.Controllers.PlayerController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.ObstacleType;
import com.avalancherush.game.Enums.PowerUpType;
import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.FirebaseInterface;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Interfaces.RenderNotifier;
import com.avalancherush.game.Interfaces.RenderObserver;
import com.avalancherush.game.Models.Obstacle;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.Models.PowerUp;
import com.avalancherush.game.Models.TakenPowerUp;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Server;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.MultiPlayerGameThread;
import com.avalancherush.game.Singletons.ObstacleFactory;
import com.avalancherush.game.Singletons.SinglePlayerGameThread;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameViewMultiplayer extends RenderNotifier {
    private FirebaseInterface database;
    private GameThread gameThread;
    private OrthographicCamera orthographicCamera;
    private MultiPlayerGameThread multiPlayerGameThread;
    Server server;
    private SpriteBatch batch;
    public int addition, threshold;
    private float laneX[];
    private float scoreboardX, scoreboardY, totaltime;
    private Player player;
//    private Rectangle menuButton;
    private BitmapFont scoreFont;
    private SinglePlayerGameThread singlePlayerGameThread;
    private long lastTouchTime;
    private static final long DOUBLE_TAP_TIME_DELTA = 200;


    public GameViewMultiplayer(Player player, List<EventObserver> eventObserverList, List<RenderObserver> renderObserverList){
        gameThread = GameThread.getInstance();
        database = gameThread.getDatabase();
        this.singlePlayerGameThread = SinglePlayerGameThread.getInstance();
        multiPlayerGameThread = MultiPlayerGameThread.getInstance();
        server = multiPlayerGameThread.getServer();
        this.orthographicCamera = GameThread.getInstance().getCamera();
        this.orthographicCamera.position.set(new Vector3((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2, (float)MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2,0 ));
        this.batch = new SpriteBatch();
        this.scoreFont = BIG_BLACK_FONT;
        this.scoreFont.getData().setScale(0.60f * heightScale);

        this.scoreboardX = (float) ((MyAvalancheRushGame.INSTANCE.getScreenWidth() * 4 / 6) + 10);
        this.scoreboardY = (float) (MyAvalancheRushGame.INSTANCE.getScreenHeight() - (SCOREBOARD.getHeight() * heightScale / 2) - 10);

        LANES[0] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() / 6);
        LANES[1] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2);
        LANES[2] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() * 5 / 6);

        this.player = player;
        float playerY = (float)this.player.getTexture().getHeight() * heightScale/2;
        float playerX = LANES[1] - SINGLE_PLAYER_WIDTH * widthScale/2;
        Rectangle rectangle = new Rectangle(playerX, playerY, SINGLE_PLAYER_WIDTH * widthScale, SINGLE_PLAYER_HEIGHT * heightScale);
        this.player.setRectangle(rectangle);

        this.totaltime = 0;

//        this.menuButton = new Rectangle(10, MyAvalancheRushGame.INSTANCE.getScreenHeight() - MENU_BUTTON.getHeight() - 10, MENU_BUTTON.getWidth(), MENU_BUTTON.getHeight());

        this.observers = eventObserverList;
        this.renderObservers = renderObserverList;
    }
    @Override
    public void render(float delta) {
        database.serverChangeListener(server);
        show();
        boolean collision = checkCollision();
        if(collision){
            MyAvalancheRushGame.INSTANCE.setScreen(new GameEndMultiplayerView());
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
        float vehicleMultiplier = 1.0f;
        List<TakenPowerUp> powerUpsToRemove = new ArrayList<>();
        for (TakenPowerUp powerUp: player.getPowerUps()){
            powerUp.setTime(powerUp.getTime() - elapsedTime);
            if(powerUp.getTime() < 0){
                powerUpsToRemove.add(powerUp);
            }
            if(powerUp.getPowerUpType() == PowerUpType.SNOWBOARD){
                vehicleMultiplier = 2.0f;
            }
        }
        for(TakenPowerUp powerUpToRemove: powerUpsToRemove){
            player.getPowerUps().remove(powerUpToRemove);
        }
        totaltime += elapsedTime;
        singlePlayerGameThread.gameScore += elapsedTime * 10 * vehicleMultiplier;
        gameThread.gameSpeed += elapsedTime * 3;
        notifyRenderObservers(renderObservers, elapsedTime);
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();
        batch.draw(LINE, (float) MyAvalancheRushGame.INSTANCE.getScreenWidth() /3, 0 , LINE.getWidth() * widthScale, MyAvalancheRushGame.INSTANCE.getScreenHeight());
        batch.draw(LINE, (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() * 2) /3, 0 , LINE.getWidth() * widthScale, MyAvalancheRushGame.INSTANCE.getScreenHeight());
        for(Obstacle obstacle: singlePlayerGameThread.obstacles){
            obstacle.draw(batch);
        }
        for (PowerUp powerUp: singlePlayerGameThread.powerUps){
            powerUp.draw(batch);
        }
        player.draw(batch);

        for (int i = 0; i < player.getPowerUps().size(); i++) {
            TakenPowerUp takenPowerUp = player.getPowerUps().get(i);
            int yOffset = 50 * i + 100;
            if (takenPowerUp.getPowerUpType() == PowerUpType.HELMET) {
                batch.draw(Textures.HELMET, 10, Gdx.graphics.getHeight() - yOffset, 30 * widthScale, 30 * heightScale);
            } else if (takenPowerUp.getPowerUpType() == PowerUpType.SNOWBOARD) {
                batch.draw(Textures.SNOWBOARD, 10, Gdx.graphics.getHeight() - yOffset, 30 * widthScale, 30 * heightScale);
                batch.draw(Textures.X2_SPEED, scoreboardX - 50, scoreboardY, 50 * widthScale, 50 * heightScale);
                scoreFont.draw(batch, "X2", scoreboardX - 40, scoreboardY + 35);
            }

            float timePercentage = takenPowerUp.getTime() / POWER_UP_HELMET_TIME;
            if (timePercentage <= 0.25){
                batch.draw(Textures.POWER_UP_BAR_1, 40, Gdx.graphics.getHeight() - yOffset, 150 * widthScale, 30 * heightScale);
            }
            else if (timePercentage <= 0.5){
                batch.draw(Textures.POWER_UP_BAR_2, 40, Gdx.graphics.getHeight() - yOffset, 150 * widthScale, 30 * heightScale);
            }
            else if(timePercentage <= 0.75) {
                batch.draw(Textures.POWER_UP_BAR_3, 40, Gdx.graphics.getHeight() - yOffset, 150 * widthScale, 30 * heightScale);
            }
            else{
                batch.draw(Textures.POWER_UP_BAR_4, 40, Gdx.graphics.getHeight() - yOffset, 150 * widthScale, 30 * heightScale);
            }
        }

        batch.draw(SCOREBOARD, scoreboardX, scoreboardY, MyAvalancheRushGame.INSTANCE.getScreenWidth() / 3 - 20, 50 * heightScale);
        batch.draw(SCOREBOARD, scoreboardX, scoreboardY - 65,MyAvalancheRushGame.INSTANCE.getScreenWidth() / 3 - 20,50 * heightScale);
        if(server.CurrentPlayer.equalsIgnoreCase("PlayerA")){
            scoreFont.draw(batch,"YOU " + server.playerAScore,scoreboardX + (SCOREBOARD.getWidth() * widthScale / 10),scoreboardY + (SCOREBOARD.getHeight() * heightScale/ 3));
            scoreFont.draw(batch,"FRIEND " + server.playerBScore,scoreboardX + (SCOREBOARD.getWidth() * widthScale / 10),scoreboardY + (SCOREBOARD.getHeight() * heightScale / 3) - 65);
        } else {
            scoreFont.draw(batch,"YOU " + server.playerBScore,scoreboardX + (SCOREBOARD.getWidth() * widthScale/ 10),scoreboardY + (SCOREBOARD.getHeight() * heightScale/ 3) - 65);
            scoreFont.draw(batch,"FRIEND " + server.playerAScore,scoreboardX + (SCOREBOARD.getWidth() * widthScale / 10),scoreboardY + (SCOREBOARD.getHeight() * heightScale / 3));
        }
//        batch.draw(MENU_BUTTON, menuButton.x, menuButton.y);
        batch.end();

        if(server.CurrentPlayer.equalsIgnoreCase("PlayerA")){
            database.setValueToServerDataBase(server.id,"PlayerAScore", String.valueOf(singlePlayerGameThread.gameScore));
        }
        else{
            database.setValueToServerDataBase(server.id,"PlayerBScore", String.valueOf(singlePlayerGameThread.gameScore));
        }

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                orthographicCamera.unproject(touchPos);

                int currentTrack = player.getTrack();
                float laneWidth = LANES[1] - LANES[0];
                float laneRightPosition = LANES[currentTrack - 1] + laneWidth/2;
                float laneLeftPosition =  laneRightPosition - laneWidth;

                if(screenX < laneLeftPosition){
                    notifyObservers(Collections.singletonList(observers.get(1)), EventType.SLIDED_LEFT);
                } else if(screenX > laneRightPosition){
                    notifyObservers(Collections.singletonList(observers.get(1)), EventType.SLIDED_RIGHT);
                }

                long currentTime = System.currentTimeMillis();
                if (currentTime - lastTouchTime < DOUBLE_TAP_TIME_DELTA) {
                    notifyObservers(Collections.singletonList(observers.get(1)), EventType.SLIDED_UP);
                }
                lastTouchTime = currentTime;

//                if (menuButton.contains(touchPos.x, touchPos.y)) {
//                    notifyObservers(observers, EventType.GAME_MENU_BUTTON);
//                    return true;
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
        for(Obstacle obstacle: singlePlayerGameThread.obstacles){
            if(obstacle.getTrack() != player.getTrack()){
                continue;
            }
            if(player.collides(obstacle.getRectangle())){
                if(obstacle.getType() == ObstacleType.ROCK && player.getJumping()){
                    return false;
                }
                for (TakenPowerUp powerUp : player.getPowerUps()){
                    if(powerUp.getPowerUpType() == PowerUpType.HELMET){
                        singlePlayerGameThread.obstacles.removeValue(obstacle, true);
                        player.removePowerUp(powerUp);
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public PowerUpType checkGettingPowerUp(){
        for(PowerUp powerUp: singlePlayerGameThread.powerUps){
            if(player.collides(powerUp.getRectangle())){
                singlePlayerGameThread.powerUps.removeValue(powerUp, true);
                return powerUp.getType();
            }
        }
        return null;
    }
}
