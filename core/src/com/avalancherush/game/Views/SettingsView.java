package com.avalancherush.game.Views;

import com.avalancherush.game.Controllers.SettingsController;
import com.avalancherush.game.Enums.EventType;
import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.VOLUME_DOWN_BUTTON;
import static com.avalancherush.game.Configuration.Textures.VOLUME_UP_BUTTON;

import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.badlogic.gdx.Game;
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

public class SettingsView extends ScreenAdapter {

    private GameThread gameThread;
    private SettingsController settingsController;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Rectangle homeButton;
    private Rectangle volumeUpButton;
    private Rectangle volumeDownButton;
    private BitmapFont fontTitle;
    private Texture backGroundTexture;

    public SettingsView() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.settingsController = new SettingsController();
        this.batch = new SpriteBatch();
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth(), HOME_BUTTON.getHeight());
        this.backGroundTexture = new Texture(Gdx.files.internal("backGroundMountain.jpg"));
        int totalButtonHeight = VOLUME_UP_BUTTON.getHeight() + VOLUME_DOWN_BUTTON.getHeight() - 30;
        int startY = (MyAvalancheRushGame.INSTANCE.getScreenHeight() - totalButtonHeight) / 2 + VOLUME_DOWN_BUTTON.getHeight();

        this.volumeUpButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - VOLUME_UP_BUTTON.getWidth()) / 2, startY, VOLUME_UP_BUTTON.getWidth(), VOLUME_UP_BUTTON.getHeight());


        int buttonSpacing = 40;
        int volumeButtonWidth = VOLUME_UP_BUTTON.getWidth();
        int totalWidth = volumeButtonWidth * 2 + buttonSpacing;
        int startX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - totalWidth) / 2;
        this.volumeUpButton = new Rectangle(startX, startY, VOLUME_UP_BUTTON.getWidth(), VOLUME_UP_BUTTON.getHeight());
        this.volumeDownButton = new Rectangle(startX + volumeButtonWidth + buttonSpacing, startY, VOLUME_DOWN_BUTTON.getWidth(), VOLUME_DOWN_BUTTON.getHeight());
        fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        fontTitle.getData().setScale(1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());
        batch.draw(backGroundTexture, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());
        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Settings");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y);
        batch.draw(VOLUME_UP_BUTTON, volumeUpButton.x, volumeUpButton.y);
        batch.draw(VOLUME_DOWN_BUTTON, volumeDownButton.x, volumeDownButton.y);

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
                    settingsController.notify(EventType.HOME_BUTTON_CLICK);
                    return true;
                } else if (volumeUpButton.contains(touchPos.x, touchPos.y)) {
                    return true;
                } else if (volumeDownButton.contains(touchPos.x, touchPos.y)) {
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
        VOLUME_UP_BUTTON.dispose();
        VOLUME_DOWN_BUTTON.dispose();
        fontTitle.dispose();
    }
}


