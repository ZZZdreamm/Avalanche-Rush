package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.GlobalVariables.heightScale;
import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.MENU_BUTTON;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;

import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.FirebaseInterface;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class HighestScore  extends ScreenAdapter {

    private GameThread gameThread;

    private OrthographicCamera orthographicCamera;

    private SpriteBatch batch;

    private FirebaseInterface database;
    private Rectangle highScore1, highScore2, highScore3, backButton;
    int scoreHeight, scoreWidth;
    int centerY, centerX;
    String score1[];
    String score2[];
    String score3[];
    private BitmapFont fontDraw;

    public HighestScore(){
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.batch = new SpriteBatch();
        this.database = gameThread.getDatabase();
        scoreHeight = Gdx.graphics.getHeight()/10;
        scoreWidth = (int)( Gdx.graphics.getWidth()*0.8f);
        centerY = Gdx.graphics.getHeight()/2;
        centerX = Gdx.graphics.getWidth()/2;
        highScore1 = new Rectangle(centerX - scoreWidth/2, centerY + scoreHeight + 5, scoreWidth, scoreHeight);

        highScore2 = new Rectangle(centerX - scoreWidth/2, centerY , scoreWidth, scoreHeight);

        highScore3 = new Rectangle(centerX - scoreWidth/2, centerY - scoreHeight - 5, scoreWidth, scoreHeight);

        backButton = new Rectangle(10, Gdx.graphics.getHeight()-10 - Gdx.graphics.getWidth()/10, Gdx.graphics.getWidth()/10, Gdx.graphics.getWidth()/10);

        score1 = new String[2];
        score2 = new String[2];
        score3 = new String[2];
        this.fontDraw = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.fontDraw.getData().setScale(1 * heightScale);

    }


    @Override
    public void render(float delta) {
        database.ScoreChangeListener();
        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();
        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        batch.draw(WOOD_BUTTON, highScore1.x, highScore1.y, highScore1.width, highScore1.height);
        batch.draw(WOOD_BUTTON, highScore2.x, highScore2.y, highScore2.width, highScore2.height);
        batch.draw(WOOD_BUTTON, highScore3.x, highScore3.y, highScore3.width, highScore3.height);
        batch.draw(MENU_BUTTON, backButton.x, backButton.y, backButton.width, backButton.height);

        GlyphLayout score1Name = new GlyphLayout(fontDraw, gameThread.score1Name + ":  " + gameThread.score1Score);
        GlyphLayout score2Name = new GlyphLayout(fontDraw, gameThread.score2Name + ":  " + gameThread.score2Score);
        GlyphLayout score3Name = new GlyphLayout(fontDraw, gameThread.score3Name + ":  " + gameThread.score3Score);

        fontDraw.draw(batch, score1Name, centerX - score1Name.width/2, centerY + scoreHeight + 5 + scoreHeight/2 + score1Name.height/2);
        fontDraw.draw(batch, score2Name, centerX - score1Name.width/2, centerY + scoreHeight + 5 + scoreHeight/2 + score1Name.height/2 - 5 - scoreHeight);
        fontDraw.draw(batch, score3Name, centerX - score1Name.width/2, centerY + scoreHeight + 5 + scoreHeight/2 + score1Name.height/2 - 10 - scoreHeight*2);

        batch.end();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                orthographicCamera.unproject(touchPos);

                if (backButton.contains(touchPos.x, touchPos.y)) {
                    MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
                    return true;
                }
                return false;
            }

        });
    }

    @Override
    public void dispose() {
    }
}
