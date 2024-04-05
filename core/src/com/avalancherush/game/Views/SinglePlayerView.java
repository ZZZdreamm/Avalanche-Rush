package com.avalancherush.game.Views;

import com.avalancherush.game.MyAvalancheRushGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class SinglePlayerView extends ScreenAdapter {

    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Texture backGroundTexture;
    private Texture playButtonTexture;
    private Texture homeButtonTexture;
    private Texture singlePlayerLogo;
    private Texture playLogo;
    private Rectangle playButton;
    private Rectangle homeButton;

    public SinglePlayerView(OrthographicCamera orthographicCamera) {
        this.orthographicCamera = orthographicCamera;
        this.batch = new SpriteBatch();
        this.singlePlayerLogo = new Texture((Gdx.files.internal("singlePlayerLogo.png")));
        this.playLogo = new Texture((Gdx.files.internal("playLogo.png")));
        this.playButtonTexture = new Texture(Gdx.files.internal("buttonWood.png"));
        this.homeButtonTexture = new Texture(Gdx.files.internal("buttonHome.png"));
        this.backGroundTexture = new Texture(Gdx.files.internal("backGroundMountain.jpg"));

        this.playButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - playButtonTexture.getWidth()) / 2, (MyAvalancheRushGame.INSTANCE.getScreenHeight() - playButtonTexture.getHeight()) / 2, playButtonTexture.getWidth(), playButtonTexture.getHeight());
        this.homeButton = new Rectangle(50, 50, homeButtonTexture.getWidth(), homeButtonTexture.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(backGroundTexture, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        batch.draw(singlePlayerLogo, ((float)MyAvalancheRushGame.INSTANCE.getScreenWidth() - singlePlayerLogo.getWidth() + 100) / 2, MyAvalancheRushGame.INSTANCE.getScreenHeight() - singlePlayerLogo.getHeight() - 20);

        batch.draw(playButtonTexture, playButton.x, playButton.y);

        float playLogoX = playButton.x + (playButton.width - playLogo.getWidth() + 100) / 2;
        float playLogoY = playButton.y + (playButton.height - playLogo.getHeight()) / 2;
        batch.draw(playLogo, playLogoX, playLogoY);

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

                if (playButton.contains(touchPos.x, touchPos.y)) {

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
        playButtonTexture.dispose();
        homeButtonTexture.dispose();
        backGroundTexture.dispose();
        playLogo.dispose();
        singlePlayerLogo.dispose();
    }
}







