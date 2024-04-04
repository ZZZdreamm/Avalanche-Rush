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
    private Texture multiPlayerLogo;
    private Texture createLogo;
    private Texture joinLogo;
    private Rectangle joinButton;
    private Rectangle createButton;
    private Rectangle homeButton;


    public MultiPlayerView(OrthographicCamera orthographicCamera) {
        this.orthographicCamera = orthographicCamera;
        this.batch = new SpriteBatch();
        this.multiPlayerLogo = new Texture((Gdx.files.internal("multiPlayerLogo.png")));
        this.joinLogo = new Texture((Gdx.files.internal("joinLogo.png")));
        this.createLogo = new Texture((Gdx.files.internal("createLogo.png")));
        this.joinButtonTexture = new Texture(Gdx.files.internal("buttonWood.png"));
        this.createButtonTexture = new Texture(Gdx.files.internal("buttonWood.png"));
        this.homeButtonTexture = new Texture(Gdx.files.internal("buttonHome.png"));
        this.backGroundTexture = new Texture(Gdx.files.internal("backGroundMountain.jpg"));

        this.joinButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - joinButtonTexture.getWidth()) / 2, (MyAvalancheRushGame.INSTANCE.getScreenHeight() - joinButtonTexture.getHeight()) / 2 + 50, joinButtonTexture.getWidth(), joinButtonTexture.getHeight());
        this.createButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - createButtonTexture.getWidth()) / 2, joinButton.y - joinButtonTexture.getHeight() - 20, createButtonTexture.getWidth(), createButtonTexture.getHeight());
        this.homeButton = new Rectangle(50, 50, homeButtonTexture.getWidth(), homeButtonTexture.getHeight());

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(backGroundTexture, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());
        batch.draw(multiPlayerLogo, ((float)MyAvalancheRushGame.INSTANCE.getScreenWidth() - multiPlayerLogo.getWidth() + 100) / 2, MyAvalancheRushGame.INSTANCE.getScreenHeight() - multiPlayerLogo.getHeight() - 20);

        batch.draw(joinButtonTexture, joinButton.x, joinButton.y);
        batch.draw(createButtonTexture, createButton.x, createButton.y);

        float joinLogoX = joinButton.x + (joinButtonTexture.getWidth() - joinLogo.getWidth() + 100) / 2;
        float joinLogoY = joinButton.y + (joinButtonTexture.getHeight() - joinLogo.getHeight() - 10) / 2;
        batch.draw(joinLogo, joinLogoX, joinLogoY);

        float createLogoX = createButton.x + (createButtonTexture.getWidth() - createLogo.getWidth() + 100) / 2;
        float createLogoY = createButton.y + (createButtonTexture.getHeight() - createLogo.getHeight() - 10) / 2;
        batch.draw(createLogo, createLogoX, createLogoY);

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
                    MyAvalancheRushGame.INSTANCE.setScreen(new JoinView(orthographicCamera));
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
        backGroundTexture.dispose();
    }
}

