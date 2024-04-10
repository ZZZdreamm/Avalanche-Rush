package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.VOLUME_DOWN_BUTTON;
import static com.avalancherush.game.Configuration.Textures.VOLUME_UP_BUTTON;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;

import com.avalancherush.game.Controllers.GameMenuController;
import com.avalancherush.game.Controllers.MainMenuController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameMenuView extends ScreenAdapter {

    private OrthographicCamera orthographicCamera;
    private GameThread gameThread;
    private GameMenuController gameMenuController;
    private SpriteBatch batch;
    private Rectangle resumeButton;
    private Rectangle volumeUpButton;
    private Rectangle volumeDownButton;
    private Rectangle homeButton;
    private BitmapFont fontTitle;
    private BitmapFont fontText;

    public GameMenuView() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.batch = new SpriteBatch();
        this.gameMenuController = new GameMenuController();

        float screenWidth = MyAvalancheRushGame.INSTANCE.getScreenWidth();
        float screenHeight = MyAvalancheRushGame.INSTANCE.getScreenHeight();

        this.resumeButton = new Rectangle((screenWidth - WOOD_BUTTON.getWidth()) / 2, (screenHeight - WOOD_BUTTON.getHeight()) / 2 + 50, WOOD_BUTTON.getWidth(), WOOD_BUTTON.getHeight());
        this.volumeUpButton = new Rectangle((screenWidth - WOOD_BUTTON.getWidth()) / 2, resumeButton.y - WOOD_BUTTON.getHeight() - 20, VOLUME_UP_BUTTON.getWidth(), VOLUME_UP_BUTTON.getHeight());
        this.volumeDownButton = new Rectangle((screenWidth - WOOD_BUTTON.getWidth()) / 2 + 150, volumeUpButton.y, VOLUME_DOWN_BUTTON.getWidth(), VOLUME_DOWN_BUTTON.getHeight());
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth(), HOME_BUTTON.getHeight());

        fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        fontTitle.getData().setScale(1f);

        fontText = new BitmapFont(Gdx.files.internal("font2.fnt"));
        fontText.getData().setScale(0.90f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Game Menu");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        batch.draw(WOOD_BUTTON, resumeButton.x, resumeButton.y);
        GlyphLayout resumeLayout = new GlyphLayout(fontText, "Resume");
        float resumeTextX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - resumeLayout.width) / 2;
        float resumeTextY = resumeButton.y + (resumeButton.getHeight() + resumeLayout.height - 30) / 2 + 20;
        fontText.draw(batch, resumeLayout, resumeTextX, resumeTextY);

        batch.draw(VOLUME_UP_BUTTON, volumeUpButton.x, volumeUpButton.y);
        batch.draw(VOLUME_DOWN_BUTTON, volumeDownButton.x, volumeDownButton.y);

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

                if (resumeButton.contains(touchPos.x, touchPos.y)) {
                    gameMenuController.notify(EventType.RESUME_BUTTON_CLICK);
                } else if (homeButton.contains(touchPos.x, touchPos.y)) {
                    gameMenuController.notify(EventType.HOME_BUTTON_CLICK);
                } else if (volumeUpButton.contains(touchPos.x, touchPos.y)) {
                    gameMenuController.notify(EventType.VOLUME_UP);
                } else if (volumeDownButton.contains(touchPos.x, touchPos.y)) {
                    gameMenuController.notify(EventType.VOLUME_DOWN);
                }
                return false;
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        fontText.dispose();
        fontTitle.dispose();
        HOME_BUTTON.dispose();
        WOOD_BUTTON.dispose();
    }
}

