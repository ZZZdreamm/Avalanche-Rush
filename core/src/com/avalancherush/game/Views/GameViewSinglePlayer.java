package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.GlobalVariables.GAME_SPEED;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_ROCK_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_TREE_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_WIDTH;
import static com.avalancherush.game.Configuration.Textures.LINE;
import static com.avalancherush.game.Configuration.Textures.SCOREBOARD;
import static com.badlogic.gdx.math.MathUtils.random;

import com.avalancherush.game.Configuration.GlobalVariables;
import com.avalancherush.game.Enums.ObstacleType;
import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Models.Obstacle;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.ObstacleFactory;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.PriorityQueue;


public class GameViewSinglePlayer extends ScreenAdapter {

    private GameThread gameThread;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    public int addition, threshold;
    //    public Queue<Vector3> gameObstacle;
    private float laneX[];
    private float scoreboardX, scoreboardY, totaltime;
    private Player player;
    private ObstacleFactory obstacleFactory;
    private Queue<Obstacle> obstacles;

    public GameViewSinglePlayer() {
        this.orthographicCamera = GameThread.getInstance().getCamera();
        this.orthographicCamera.position.set(new Vector3((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2, (float)MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2,0 ));
        this.batch = new SpriteBatch();


        this.scoreboardX = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() - (SCOREBOARD.getWidth() / 2) - 10);
        this.scoreboardY = (float) (MyAvalancheRushGame.INSTANCE.getScreenHeight() - (SCOREBOARD.getHeight() / 2) - 10);

        this.laneX = new float[3];
        this.laneX[0] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() / 6);
        this.laneX[1] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2);
        this.laneX[2] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() * 5 / 6);

        this.player = new Player();
        this.player.setTrack(2);
        this.player.setSkin(SkinType.BASIC);
        this.player.setTexture(new Texture((Gdx.files.internal("ski_spritesheet.png"))));
        float playerY = (float)this.player.getTexture().getHeight()/2;
        float playerX = (float) laneX[1] - SINGLE_PLAYER_WIDTH/2;
        Rectangle rectangle = new Rectangle(playerX, playerY, SINGLE_PLAYER_HEIGHT, SINGLE_PLAYER_WIDTH);
        this.player.setRectangle(rectangle);

        addition = 1;
        threshold = 10;
        this.totaltime = 0;

        this.obstacleFactory = ObstacleFactory.getInstance();
        this.obstacles = new Queue<>();
    }

    @Override
    public void render(float dt) {
        show();
        boolean collision = checkCollision();
        if(collision){
            MyAvalancheRushGame.INSTANCE.setScreen(new GameEndView(orthographicCamera));
        }
        float elapsedTime = Gdx.graphics.getDeltaTime();
        totaltime += elapsedTime;
        GAME_SPEED = totaltime+50 > GAME_SPEED ? totaltime+50 : GAME_SPEED;

        generateObstacle(elapsedTime);
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        for(Obstacle obstacle: obstacles){
            obstacle.draw(batch);
        }

        player.draw(batch);

        batch.draw(LINE,MyAvalancheRushGame.INSTANCE.getScreenWidth()/3, 0 );
        batch.draw(LINE,MyAvalancheRushGame.INSTANCE.getScreenWidth()*2/3, 0 );
        batch.draw(SCOREBOARD, scoreboardX, scoreboardY, 100, 50);
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
                    player.setTrack(player.getTrack() - 1);
                    player.getRectangle().x = laneX[player.getTrack()-1] - SINGLE_PLAYER_WIDTH/2;
                } else if(screenX > playerRectangle.x){
                    if(player.getTrack() < 3){
                        player.setTrack(player.getTrack() + 1);
                        player.getRectangle().x = laneX[player.getTrack()-1] - SINGLE_PLAYER_WIDTH/2;
                    }
                }
                return true;
            }
        });

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void generateObstacle(float time){
        int size = obstacles.size;

        Obstacle head = new Obstacle();
        Rectangle rectangle = new Rectangle(0,0,0,0);
        head.setRectangle(rectangle);
        float lastObstacleLane = 0;
        while(!obstacles.isEmpty()){
            head = obstacles.removeFirst();
            Rectangle headRectangle = head.getRectangle();
            lastObstacleLane = headRectangle.x;
            if(headRectangle.y > -50){
                Obstacle newObstacle;
                int track = random.nextInt(1, 3);
                if(random.nextInt(2) == 1){
                    newObstacle = obstacleFactory.createObstacle(ObstacleType.ROCK, track, headRectangle.x, headRectangle.y-time*GAME_SPEED);
                }else{
                    newObstacle = obstacleFactory.createObstacle(ObstacleType.TREE, track, headRectangle.x, headRectangle.y-time*GAME_SPEED);
                }
                obstacles.addLast(newObstacle);
            }
        }


        if (size<threshold && head.getRectangle().y < (MyAvalancheRushGame.INSTANCE.getScreenHeight() - SINGLE_PLAYER_HEIGHT - 100) + 10) {
            int track = 0;
            do{
                track = random.nextInt(3) + 1;
            }while (track == lastObstacleLane);

            Obstacle newObstacle;
            if(random.nextInt(2) == 1){
                newObstacle = obstacleFactory.createObstacle(ObstacleType.ROCK, track, laneX[track - 1] - OBSTACLE_ROCK_WIDTH/2, MyAvalancheRushGame.INSTANCE.getScreenHeight());
            }else{
                newObstacle = obstacleFactory.createObstacle(ObstacleType.TREE, track, laneX[track - 1] - OBSTACLE_TREE_WIDTH/2, MyAvalancheRushGame.INSTANCE.getScreenHeight());
            }

            obstacles.addLast(newObstacle);
        }


    }
    public boolean checkCollision(){
        for(Obstacle obstacle: obstacles){
            if(obstacle.getTrack() != player.getTrack()){
                continue;
            }
            if(player.collides(obstacle.getRectangle())){
                return true;
            }
        }
        return false;
    }
}

