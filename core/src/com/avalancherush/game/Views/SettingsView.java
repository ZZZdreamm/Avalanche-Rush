package com.avalancherush.game.Views;

import com.avalancherush.game.MyAvalancheRushGame;
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

    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture homeButtonTexture;
    private Texture volumeUpButtonTexture;
    private Texture volumeDownButtonTexture;
    private Rectangle homeButton;
    private Rectangle volumeUpButton;
    private Rectangle volumeDownButton;
    private BitmapFont fontTitle;

    public SettingsView(OrthographicCamera orthographicCamera) {
        this.orthographicCamera = orthographicCamera;
        this.batch = new SpriteBatch();
        this.backgroundTexture = new Texture(Gdx.files.internal("backGroundMountain.jpg"));
        this.homeButtonTexture = new Texture(Gdx.files.internal("buttonHome.png"));
        this.volumeUpButtonTexture = new Texture(Gdx.files.internal("buttonVolumeUp.png"));
        this.volumeDownButtonTexture = new Texture(Gdx.files.internal("buttonVolumeDown.png"));
        this.homeButton = new Rectangle(50, 50, homeButtonTexture.getWidth(), homeButtonTexture.getHeight());
        int totalButtonHeight = volumeUpButtonTexture.getHeight() + volumeDownButtonTexture.getHeight() - 30;
        int startY = (MyAvalancheRushGame.INSTANCE.getScreenHeight() - totalButtonHeight) / 2 + volumeDownButtonTexture.getHeight();

        this.volumeUpButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - volumeUpButtonTexture.getWidth()) / 2, startY, volumeUpButtonTexture.getWidth(), volumeUpButtonTexture.getHeight());


        int buttonSpacing = 40;
        int volumeButtonWidth = volumeUpButtonTexture.getWidth();
        int totalWidth = volumeButtonWidth * 2 + buttonSpacing;
        int startX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - totalWidth) / 2;
        this.volumeUpButton = new Rectangle(startX, startY, volumeUpButtonTexture.getWidth(), volumeUpButtonTexture.getHeight());
        this.volumeDownButton = new Rectangle(startX + volumeButtonWidth + buttonSpacing, startY, volumeDownButtonTexture.getWidth(), volumeDownButtonTexture.getHeight());
        fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        fontTitle.getData().setScale(1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(backgroundTexture, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Settings");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        batch.draw(homeButtonTexture, homeButton.x, homeButton.y);
        batch.draw(volumeUpButtonTexture, volumeUpButton.x, volumeUpButton.y);
        batch.draw(volumeDownButtonTexture, volumeDownButton.x, volumeDownButton.y);

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
                    MyAvalancheRushGame.INSTANCE.setScreen(new MenuView(orthographicCamera));
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
        backgroundTexture.dispose();
        homeButtonTexture.dispose();
        volumeUpButtonTexture.dispose();
        volumeDownButtonTexture.dispose();
        fontTitle.dispose();
    }
}


