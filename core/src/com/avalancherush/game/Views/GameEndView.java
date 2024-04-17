package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.Fonts.BIG_BLACK_FONT;
import static com.avalancherush.game.Configuration.GlobalVariables.heightScale;
import static com.avalancherush.game.Configuration.GlobalVariables.widthScale;
import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.LOST_BUTTON;
import static com.avalancherush.game.Configuration.Textures.MODIFY_BUTTON;

import com.avalancherush.game.Controllers.GameEndController;
import com.avalancherush.game.Controllers.SinglePlayerController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.SinglePlayerGameThread;
import com.avalancherush.game.Views.SinglePlayerView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameEndView extends ScreenAdapter {

    private GameThread gameThread;
    private GameEndController gameEndController;
    private SinglePlayerController singlePlayerController;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Rectangle homeButton;
    private Rectangle restartButton;
    private BitmapFont scoreFont;
    private BitmapFont gameOverFont;

    public GameEndView() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.gameEndController = new GameEndController();
        this.singlePlayerController = new SinglePlayerController();
        this.batch = new SpriteBatch();
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth() * widthScale, HOME_BUTTON.getHeight() * heightScale);
        this.restartButton = new Rectangle(Gdx.graphics.getWidth() * widthScale/3, 140, 50, 50);
        this.scoreFont = BIG_BLACK_FONT;
        this.scoreFont.getData().setScale(0.75f * heightScale);
        this.gameOverFont = BIG_BLACK_FONT;
        this.gameOverFont.getData().setScale(1.5f * heightScale);
    }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();


        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        GlyphLayout gameOverLayout = new GlyphLayout(gameOverFont, "GAME OVER");
        float gameOverX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameOverLayout.width) / 2;
        float gameOverY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameOverLayout.height - 50;
        gameOverFont.draw(batch, "GAME OVER", gameOverX, gameOverY);

        float buttonX = (float)(MyAvalancheRushGame.INSTANCE.getScreenWidth() - LOST_BUTTON.getWidth() * widthScale) / 2;
        float buttonY = (float)(MyAvalancheRushGame.INSTANCE.getScreenHeight() - LOST_BUTTON.getHeight() * heightScale) / 2;

        batch.draw(LOST_BUTTON, buttonX, buttonY, LOST_BUTTON.getWidth() * widthScale, LOST_BUTTON.getHeight() * heightScale);

        GlyphLayout layout = new GlyphLayout(scoreFont, "SCORE ");
        float textX = buttonX + (LOST_BUTTON.getWidth() * widthScale - layout.width - 80 * widthScale) / 2;
        float textY = buttonY + (LOST_BUTTON.getHeight() * heightScale + layout.height) / 2;

        scoreFont.draw(batch, "SCORE  " + Math.round(SinglePlayerGameThread.getInstance().gameScore) , textX, textY);

        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y, homeButton.width, homeButton.height );
        batch.draw(MODIFY_BUTTON, restartButton.x, restartButton.y, restartButton.width, restartButton.height);

        batch.end();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                orthographicCamera.unproject(touchPos);

                if (homeButton.contains(touchPos.x, touchPos.y)) {
                    gameEndController.notify(EventType.HOME_BUTTON_CLICK);
                    return true;
                }
                if(restartButton.contains(touchPos.x, touchPos.y)){
                    singlePlayerController.notify(EventType.GAME_SINGLE_PLAYER_CLICK);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        BACKGROUND.dispose();
        HOME_BUTTON.dispose();
        LOST_BUTTON.dispose();
        scoreFont.dispose();
        gameOverFont.dispose();
    }
}

