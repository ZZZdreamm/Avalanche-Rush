package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.LOST_BUTTON;

import com.avalancherush.game.Controllers.GameEndController;
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
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Rectangle homeButton;
    private BitmapFont scoreFont;
    private BitmapFont gameOverFont;

    public GameEndView() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.gameEndController = new GameEndController();
        this.batch = new SpriteBatch();
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth(), HOME_BUTTON.getHeight());
        this.scoreFont = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.scoreFont.getData().setScale(0.75f);
        this.gameOverFont = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.gameOverFont.getData().setScale(1.5f);
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

        float buttonX = (float)(MyAvalancheRushGame.INSTANCE.getScreenWidth() - LOST_BUTTON.getWidth()) / 2;
        float buttonY = (float)(MyAvalancheRushGame.INSTANCE.getScreenHeight() - LOST_BUTTON.getHeight()) / 2;

        batch.draw(LOST_BUTTON, buttonX, buttonY);

        GlyphLayout layout = new GlyphLayout(scoreFont, "SCORE ");
        float textX = buttonX + (LOST_BUTTON.getWidth() - layout.width - 80) / 2;
        float textY = buttonY + (LOST_BUTTON.getHeight() + layout.height) / 2;

        scoreFont.draw(batch, "SCORE  " + Math.round(SinglePlayerGameThread.getInstance().gameScore) , textX, textY);

        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y);

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

