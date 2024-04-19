package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.Fonts.BIG_BLACK_FONT;
import static com.avalancherush.game.Configuration.GlobalVariables.LANES;
import static com.avalancherush.game.Configuration.GlobalVariables.POWER_UP_HELMET_TIME;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.heightScale;
import static com.avalancherush.game.Configuration.GlobalVariables.widthScale;
import static com.avalancherush.game.Configuration.Textures.LINE;
import static com.avalancherush.game.Configuration.Textures.SCOREBOARD;
import static com.avalancherush.game.Configuration.Textures.X2_SPEED;
import static com.badlogic.gdx.math.MathUtils.random;

import com.avalancherush.game.Configuration.Textures;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.ObstacleType;
import com.avalancherush.game.Enums.PowerUpType;
import com.avalancherush.game.FirebaseInterface;
import com.avalancherush.game.Interfaces.EventObserver;
import com.avalancherush.game.Interfaces.RenderNotifier;
import com.avalancherush.game.Interfaces.RenderObserver;
import com.avalancherush.game.Models.GameMap;
import com.avalancherush.game.Models.Obstacle;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.Models.PowerUp;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Server;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.MultiPlayerGameThread;
import com.avalancherush.game.Singletons.SinglePlayerGameThread;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameViewMultiplayer extends RenderNotifier {
    private FirebaseInterface database;
    private MultiPlayerGameThread multiPlayerGameThread;
    Server server;
    private float scoreboardX, scoreboardY;
    private Player player;
    private BitmapFont scoreFont;
    private GameMap gameMap;
    private long lastTouchTime;
    private static final long DOUBLE_TAP_TIME_DELTA = 200;
    GlyphLayout scoreYou;
    GlyphLayout scoreFriend;


    public GameViewMultiplayer(Player player, List<EventObserver> eventObserverList, List<RenderObserver> renderObserverList){
        database = gameThread.getDatabase();
        multiPlayerGameThread = MultiPlayerGameThread.getInstance();
        this.gameMap = multiPlayerGameThread.getGameMap();
        server = multiPlayerGameThread.getServer();
        this.orthographicCamera.position.set(new Vector3((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2, (float)MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2,0 ));
        this.scoreFont = BIG_BLACK_FONT;
        this.scoreFont.getData().setScale(0.60f * heightScale);

        this.scoreboardX = (float) ((MyAvalancheRushGame.INSTANCE.getScreenWidth() * 4 / 6) + 10);
        this.scoreboardY = (float) (MyAvalancheRushGame.INSTANCE.getScreenHeight() - (SCOREBOARD.getHeight() * heightScale / 2) - 10);

        LANES[0] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() / 6);
        LANES[1] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2);
        LANES[2] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() * 5 / 6);

        float playerY = (float)player.getTexture().getHeight() * heightScale/2;
        float playerX = LANES[1] - SINGLE_PLAYER_WIDTH * widthScale/2;
        Rectangle rectangle = new Rectangle(playerX, playerY, SINGLE_PLAYER_WIDTH * widthScale, SINGLE_PLAYER_HEIGHT * heightScale);
        player.setRectangle(rectangle);
        multiPlayerGameThread.setPlayer(player);
        this.player = player;



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
        float elapsedTime = Gdx.graphics.getDeltaTime();
        notifyRenderObservers(renderObservers, elapsedTime);
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();
        batch.draw(LINE, (float) MyAvalancheRushGame.INSTANCE.getScreenWidth() /3, 0 , LINE.getWidth() * widthScale, MyAvalancheRushGame.INSTANCE.getScreenHeight());
        batch.draw(LINE, (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() * 2) /3, 0 , LINE.getWidth() * widthScale, MyAvalancheRushGame.INSTANCE.getScreenHeight());
        for(Obstacle obstacle: gameMap.obstacles){
            obstacle.draw(batch);
        }
        for (PowerUp powerUp: gameMap.powerUps){
            powerUp.draw(batch);
        }
        player.draw(batch);

        for (int i = 0; i < player.getPowerUps().size(); i++) {
            PowerUp takenPowerUp = player.getPowerUps().get(i);
            int yOffset = MyAvalancheRushGame.INSTANCE.getScreenWidth()/20 * i + 100;
            if (takenPowerUp.getType() == PowerUpType.HELMET) {
                batch.draw(Textures.HELMET, 10 + MyAvalancheRushGame.INSTANCE.getScreenWidth()/8, yOffset, MyAvalancheRushGame.INSTANCE.getScreenWidth()/20, MyAvalancheRushGame.INSTANCE.getScreenWidth()/20);
            } else if (takenPowerUp.getType() == PowerUpType.SNOWBOARD) {
                batch.draw(Textures.SNOWBOARD, 10 + MyAvalancheRushGame.INSTANCE.getScreenWidth()/8, yOffset, MyAvalancheRushGame.INSTANCE.getScreenWidth()/20, MyAvalancheRushGame.INSTANCE.getScreenWidth()/20);
                batch.draw(Textures.X2_SPEED, scoreboardX - 50, scoreboardY, X2_SPEED.getWidth() * widthScale, X2_SPEED.getHeight() * heightScale);
                GlyphLayout x2 = new GlyphLayout(scoreFont,"x2");
                scoreFont.draw(batch, "X2", Gdx.graphics.getWidth() - x2.width - 20, scoreboardY - x2.height+20 - 10);
            }

            float timePercentage = takenPowerUp.getTime() / POWER_UP_HELMET_TIME;
//           POWER_UP_BAR_1.getWidth()
//            POWER_UP_BAR_1.getHeight()
            if (timePercentage <= 0.25){
                batch.draw(Textures.POWER_UP_BAR_1, 10, yOffset, MyAvalancheRushGame.INSTANCE.getScreenWidth()/8  , MyAvalancheRushGame.INSTANCE.getScreenWidth()/20);
            }
            else if (timePercentage <= 0.5){
                batch.draw(Textures.POWER_UP_BAR_2, 10, yOffset, MyAvalancheRushGame.INSTANCE.getScreenWidth()/8 , MyAvalancheRushGame.INSTANCE.getScreenWidth()/20 );
            }
            else if(timePercentage <= 0.75) {
                batch.draw(Textures.POWER_UP_BAR_3, 10, yOffset, MyAvalancheRushGame.INSTANCE.getScreenWidth()/8, MyAvalancheRushGame.INSTANCE.getScreenWidth()/20);
            }
            else{
                batch.draw(Textures.POWER_UP_BAR_4, 10, yOffset, MyAvalancheRushGame.INSTANCE.getScreenWidth()/8, MyAvalancheRushGame.INSTANCE.getScreenWidth()/20);
            }
        }
        float scoreboardWidth = MyAvalancheRushGame.INSTANCE.getScreenWidth() / 3 - 20;
        float scoreboardHeight = 50 * heightScale;

        batch.draw(SCOREBOARD, scoreboardX, scoreboardY, scoreboardWidth, scoreboardHeight);
        batch.draw(SCOREBOARD, scoreboardX, scoreboardY - scoreboardHeight - 5,scoreboardWidth, scoreboardHeight);
        if(server.CurrentPlayer.equalsIgnoreCase("PlayerA")){
            scoreYou = new GlyphLayout(scoreFont, "YOU " + server.playerAScore);
            scoreFriend = new GlyphLayout(scoreFont, "Friend " + server.playerBScore);

        } else {
            scoreYou = new GlyphLayout(scoreFont, "YOU " + server.playerBScore);
            scoreFriend = new GlyphLayout(scoreFont, "Friend " + server.playerAScore);
        }
        scoreFont.draw(batch,scoreYou,scoreboardX+scoreboardWidth/2 - scoreYou.width/2,scoreboardY + scoreboardHeight/2 + scoreYou.height/2);
        scoreFont.draw(batch, scoreFriend,scoreboardX+scoreboardWidth/2 - scoreFriend.width/2,scoreboardY + scoreFriend.height/2 - scoreboardHeight/2 - 5 );
//        batch.draw(MENU_BUTTON, menuButton.x, menuButton.y);
        batch.end();

        if(server.CurrentPlayer.equalsIgnoreCase("PlayerA")){
            database.setValueToServerDataBase(server.id,"PlayerAScore", String.valueOf(multiPlayerGameThread.gameScore));
        }
        else{
            database.setValueToServerDataBase(server.id,"PlayerBScore", String.valueOf(multiPlayerGameThread.gameScore));
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
        for(Obstacle obstacle: gameMap.obstacles){
            if(obstacle.getTrack() != player.getTrack()){
                continue;
            }
            if(player.collides(obstacle.getRectangle())){
                if(obstacle.getType() == ObstacleType.ROCK && player.getJumping()){
                    return false;
                }
                for (PowerUp powerUp : player.getPowerUps()){
                    if(powerUp.getType() == PowerUpType.HELMET){
                        gameMap.obstacles.removeValue(obstacle, true);
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
        for(PowerUp powerUp: gameMap.powerUps){
            if(player.collides(powerUp.getRectangle())){
                gameMap.powerUps.removeValue(powerUp, true);
                return powerUp.getType();
            }
        }
        return null;
    }
}
