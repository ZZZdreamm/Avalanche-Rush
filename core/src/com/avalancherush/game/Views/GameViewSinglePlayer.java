package com.avalancherush.game.Views;

import static com.badlogic.gdx.math.MathUtils.random;

import com.avalancherush.game.MyAvalancheRushGame;
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

    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    public Texture singlePlayer, line, scoreboard, stone, tree;    //
    public int addition, threshold, singlePlayerHeight, singlePlayerWidth;
    public float obstacleHeight;
    public Queue<Vector3> gameObstacle;

    private float laneX[];
    public int obstacleWidth[];

    private float scoreboardX, scoreboardY, playerX, playerY, totaltime, gameSpeed;

    public GameViewSinglePlayer(OrthographicCamera orthographicCamera) {
        this.orthographicCamera = orthographicCamera;
        this.orthographicCamera.position.set(new Vector3((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2, (float)MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2,0 ));
        this.batch = new SpriteBatch();

        this.singlePlayer = new Texture((Gdx.files.internal("ski_spritesheet.png")));
        this.singlePlayer = new Texture((Gdx.files.internal("ski_spritesheet.png")));
        this.scoreboard = new Texture((Gdx.files.internal("buttonWood.png")));
        this.stone = new Texture((Gdx.files.internal("rock.png")));
        this.line = new Texture((Gdx.files.internal("Line.png")));
        this.tree = new Texture((Gdx.files.internal("treewinter.png")));

        this.gameObstacle = new Queue<Vector3>();
        this.singlePlayerHeight = 100;
        this.singlePlayerWidth = 70;
        this.gameSpeed = 50;
        this.obstacleWidth = new int[2];
        this.obstacleWidth[0] = 70;
        this.obstacleWidth[1] = 60;
        this.obstacleHeight = 100;

        this.playerY = (float) singlePlayer.getHeight() / 2;
        this.scoreboardX = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() - (scoreboard.getWidth() / 2) - 10);
        this.scoreboardY = (float) (MyAvalancheRushGame.INSTANCE.getScreenHeight() - (scoreboard.getHeight() / 2) - 10);

        this.laneX = new float[3];
        this.laneX[0] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() / 6);

        this.laneX[1] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2);

        this.laneX[2] = (float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() * 5 / 6);

        playerX = (float) laneX[1];
        addition = 1;
        threshold = 10;
        gameObstacle = new Queue<Vector3>();
        this.totaltime = 0;
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
        gameSpeed = totaltime+50 > gameSpeed ? totaltime+50 : gameSpeed;

        generateObstacle(elapsedTime);
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        for(Vector3 vector: gameObstacle){
            if(vector.z == 1){
                batch.draw(stone, laneX[(int)vector.x]  - 35, vector.y, 70, obstacleHeight);
            }
            else if(vector.z == 2){
                batch.draw(tree, laneX[(int)vector.x]  - 30, vector.y, 60, obstacleHeight);
            }
        }
//
        batch.draw(singlePlayer, playerX - singlePlayerWidth/2, playerY, singlePlayerWidth, singlePlayerHeight);
        batch.draw(line,MyAvalancheRushGame.INSTANCE.getScreenWidth()/3, 0 );
        batch.draw(line,MyAvalancheRushGame.INSTANCE.getScreenWidth()*2/3, 0 );
        batch.draw(scoreboard, scoreboardX, scoreboardY, 100, 50);
        batch.end();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                orthographicCamera.unproject(touchPos);

                if (screenX < MyAvalancheRushGame.INSTANCE.getScreenWidth()/2) {
                    if(playerX == laneX[1]){
                        playerX = laneX[0];
                    }
                    else if(playerX == laneX[2]){
                        playerX = laneX[1];
                    }
                    return true;
                }
                else{
                    if(playerX == laneX[0]){
                        playerX = laneX[1];
                    }
                    else if(playerX == laneX[1]){
                        playerX = laneX[2];
                    }
                    return true;

                }
            }
        });

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void generateObstacle(float time){
        Queue<Vector3> coordinates = new Queue<Vector3>();
        coordinates = gameObstacle;
        int size = coordinates.size;
        Queue<Vector3> newObstacles = new Queue<Vector3>();

        Vector3 head = new Vector3(0, 0, 0);
        float lastObstacleLane = 0;
        while(!coordinates.isEmpty()){
            head = coordinates.removeFirst();
            lastObstacleLane = head.x;
            if(head.y > -50){
                Vector3 vector = new Vector3(head.x, head.y-time*gameSpeed, head.z);
                newObstacles.addLast(vector);
            }
        }


        if (size<threshold && head.y < (MyAvalancheRushGame.INSTANCE.getScreenHeight() - singlePlayerHeight- 100) + 10) {
            int x = 0;
            int y = random.nextInt(2) + 1;
            do {
                x = random.nextInt(3); // Generates random number between 0 (inclusive) and 3 (exclusive)
            } while (x == lastObstacleLane);

            Vector3 obstabcle = new Vector3(x, MyAvalancheRushGame.INSTANCE.getScreenHeight(), y);
            newObstacles.addLast(obstabcle);
        }

        gameObstacle = newObstacles;

    }
    public boolean checkCollision(){

        float playerHead = playerY + singlePlayerHeight/2;
        for(Vector3 vector: gameObstacle){
            if(laneX[(int)vector.x] != playerX){
                continue;
            }
            float obstacleBottom = vector.y - obstacleHeight/2;
            if(obstacleBottom <= playerHead){
                return true;
            }
        }
        return false;
    }
}

