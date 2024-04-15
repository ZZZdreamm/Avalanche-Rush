package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_ROCK_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_TREE_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_WIDTH;
import static com.avalancherush.game.Configuration.Textures.LINE;
import static com.avalancherush.game.Configuration.Textures.MENU_BUTTON;
import static com.avalancherush.game.Configuration.Textures.SCOREBOARD;
import static com.badlogic.gdx.math.MathUtils.random;

import com.avalancherush.game.Controllers.GamePlayController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.ObstacleType;
import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.FirebaseInterface;
import com.avalancherush.game.Models.Obstacle;
import com.avalancherush.game.Models.Player;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Server;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.MultiPlayerGameThread;
import com.avalancherush.game.Singletons.ObstacleFactory;
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

public class GameViewMultiplayer extends ScreenAdapter {
    private FirebaseInterface database;
    private GameThread instance;
    private OrthographicCamera camera;
    private MultiPlayerGameThread gameThread;
    Server server;
    private SpriteBatch batch;
    public int addition, threshold;
    private float laneX[];
    private float scoreboardX, scoreboardY, totaltime;
    private Player player;
    private ObstacleFactory obstacleFactory;
    private Queue<Obstacle> obstacles;
    private float gameSpeed;
    private float gameScore;
    private BitmapFont scoreFont;


    public GameViewMultiplayer(){
        instance = GameThread.getInstance();
        database = instance.getDatabase();
        camera = instance.getCamera();
        gameThread = MultiPlayerGameThread.getInstance();
        server = gameThread.getServer();
        this.camera.position.set(new Vector3((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2, (float)MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2,0 ));
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
        Rectangle rectangle = new Rectangle(playerX, playerY, SINGLE_PLAYER_WIDTH, SINGLE_PLAYER_HEIGHT);
        this.player.setRectangle(rectangle);

        addition = 1;
        threshold = 10;
        this.totaltime = 0;
        this.gameSpeed = 30;

        this.obstacleFactory = ObstacleFactory.getInstance();
        this.obstacles = new Queue<>();

        this.gameScore = 0;

        this.scoreFont = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.scoreFont.getData().setScale(0.5f);
    }
    @Override
    public void render(float delta) {
        database.serverChangeListener(server);
        show();
        boolean collision = checkCollision();
        if(collision){
            MyAvalancheRushGame.INSTANCE.setScreen(new GameEndMultiplayerView());
        }
        float elapsedTime = Gdx.graphics.getDeltaTime();
        totaltime += elapsedTime;
        gameScore+=elapsedTime*10;
        gameSpeed = totaltime+50 > gameSpeed ? totaltime+50 : gameSpeed;
        generateObstacle(elapsedTime);
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(Obstacle obstacle: obstacles){
            obstacle.draw(batch);
        }

        player.draw(batch);

        batch.draw(LINE,MyAvalancheRushGame.INSTANCE.getScreenWidth()/3, 0 );
        batch.draw(LINE,MyAvalancheRushGame.INSTANCE.getScreenWidth()*2/3, 0 );
        batch.draw(SCOREBOARD, scoreboardX, scoreboardY, 100, 50);
        batch.draw(SCOREBOARD, 10, scoreboardY,100,50);
        if(server.CurrentPlayer.equalsIgnoreCase("PlayerA")){
            scoreFont.draw(batch,"YOU " + server.playerAScore,10,scoreboardY);
            scoreFont.draw(batch,"FRIEND " + server.playerBScore,scoreboardX,scoreboardY);
        } else {
            scoreFont.draw(batch,"YOU " + Integer.toString(server.playerBScore),10,scoreboardY);
            scoreFont.draw(batch,"FRIEND " + Integer.toString(server.playerAScore),scoreboardX,scoreboardY);
        }

        batch.end();

        if(server.CurrentPlayer.equalsIgnoreCase("PlayerA")){
            database.setValueToServerDataBase(server.id,"PlayerAScore", String.valueOf(gameScore));
        }
        else{
            database.setValueToServerDataBase(server.id,"PlayerBScore", String.valueOf(gameScore));
        }

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                camera.unproject(touchPos);

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
    }

    public void generateObstacle(float time){
        int size = obstacles.size;

        Queue<Obstacle> obstacleTemp = new Queue<>();

        Obstacle head = new Obstacle();
        Rectangle rectangle = new Rectangle(0,0,0,0);
        head.setRectangle(rectangle);
        float lastObstacleLane = 0;
        while(!obstacles.isEmpty()){
            head = obstacles.removeFirst();
            Rectangle headRectangle = head.getRectangle();
            lastObstacleLane = head.getTrack();
            if(headRectangle.y > -50){
                head.getRectangle().y = headRectangle.y - time * gameSpeed;
                obstacleTemp.addLast(head);
            }
        }

        if (size<threshold && head.getRectangle().y < (MyAvalancheRushGame.INSTANCE.getScreenHeight() - SINGLE_PLAYER_HEIGHT - OBSTACLE_HEIGHT)) {
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

            obstacleTemp.addLast(newObstacle);
        }

        obstacles = obstacleTemp;

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
