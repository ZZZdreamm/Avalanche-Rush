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

public class MultiPlayerView extends ScreenAdapter {

    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Texture backGroundTexture;
    private Texture joinButtonTexture;
    private Texture createButtonTexture;
    private Texture homeButtonTexture;
    private Rectangle joinButton;
    private Rectangle createButton;
    private Rectangle homeButton;
    private BitmapFont fontMultiPlayer;
    private BitmapFont fontButton;

    public MultiPlayerView(OrthographicCamera orthographicCamera) {
        this.orthographicCamera = orthographicCamera;
        this.batch = new SpriteBatch();
        this.joinButtonTexture = new Texture(Gdx.files.internal("buttonWood.png"));
        this.createButtonTexture = new Texture(Gdx.files.internal("buttonWood.png"));
        this.homeButtonTexture = new Texture(Gdx.files.internal("buttonHome.png"));
        this.backGroundTexture = new Texture(Gdx.files.internal("backGroundMountain.jpg"));

        this.joinButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - joinButtonTexture.getWidth()) / 2, (MyAvalancheRushGame.INSTANCE.getScreenHeight() - joinButtonTexture.getHeight()) / 2 + 50, joinButtonTexture.getWidth(), joinButtonTexture.getHeight());
        this.createButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - createButtonTexture.getWidth()) / 2, joinButton.y - joinButtonTexture.getHeight() - 20, createButtonTexture.getWidth(), createButtonTexture.getHeight());
        this.homeButton = new Rectangle(50, 50, homeButtonTexture.getWidth(), homeButtonTexture.getHeight());

        this.fontMultiPlayer = new BitmapFont();
        this.fontButton = new BitmapFont();
        this.fontMultiPlayer.setColor(Color.BLACK);
        this.fontButton.setColor(Color.BLACK);
        this.fontMultiPlayer.getData().setScale(5);
        this.fontButton.getData().setScale(2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(backGroundTexture, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        fontMultiPlayer.draw(batch, "Multiplayer", ((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() - 400 + 80) / 2, MyAvalancheRushGame.INSTANCE.getScreenHeight() - 20);

        batch.draw(joinButtonTexture, joinButton.x, joinButton.y);
        GlyphLayout joinLayout = new GlyphLayout(fontButton, "Join Lobby");
        fontButton.draw(batch, "Join Lobby", joinButton.x + (joinButton.width - joinLayout.width) / 2, joinButton.y + (joinButton.height + joinLayout.height + 15) / 2);

        batch.draw(createButtonTexture, createButton.x, createButton.y);
        GlyphLayout createLayout = new GlyphLayout(fontButton, "Create Lobby");
        fontButton.draw(batch, "Create Lobby", createButton.x + (createButton.width - createLayout.width) / 2, createButton.y + (createButton.height + createLayout.height + 15) / 2);

        batch.draw(homeButtonTexture, homeButton.x, homeButton.y);

        batch.end();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                orthographicCamera.unproject(touchPos);

                if (joinButton.contains(touchPos.x, touchPos.y)) {
                    return true;
                } else if (createButton.contains(touchPos.x, touchPos.y)) {
                    return true;
                } else if (homeButton.contains(touchPos.x, touchPos.y)) {
                    MyAvalancheRushGame.INSTANCE.setScreen(new MenuView(orthographicCamera));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        joinButtonTexture.dispose();
        createButtonTexture.dispose();
        homeButtonTexture.dispose();
        fontMultiPlayer.dispose();
        fontButton.dispose();
        backGroundTexture.dispose();
    }
}

