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

public class SinglePlayerView extends ScreenAdapter {

    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Texture backGroundTexture;
    private Texture playButtonTexture;
    private Texture homeButtonTexture;
    private Rectangle playButton;
    private Rectangle homeButton;
    private BitmapFont fontSinglePlayer;
    private BitmapFont fontPlayButton;

    public SinglePlayerView(OrthographicCamera orthographicCamera) {
        this.orthographicCamera = orthographicCamera;
        this.batch = new SpriteBatch();
        this.playButtonTexture = new Texture(Gdx.files.internal("buttonWood.png"));
        this.homeButtonTexture = new Texture(Gdx.files.internal("buttonHome.png"));
        this.backGroundTexture = new Texture(Gdx.files.internal("backGroundMountain.jpg"));

        this.playButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - playButtonTexture.getWidth()) / 2, (MyAvalancheRushGame.INSTANCE.getScreenHeight() - playButtonTexture.getHeight()) / 2, playButtonTexture.getWidth(), playButtonTexture.getHeight());
        this.homeButton = new Rectangle(50, 50, homeButtonTexture.getWidth(), homeButtonTexture.getHeight());

        this.fontSinglePlayer = new BitmapFont();
        this.fontPlayButton = new BitmapFont();
        this.fontSinglePlayer.setColor(Color.BLACK);
        this.fontPlayButton.setColor(Color.BLACK);
        this.fontSinglePlayer.getData().setScale(5);
        this.fontPlayButton.getData().setScale(3);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(backGroundTexture, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        fontSinglePlayer.draw(batch, "Single Player", ((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() - 400) / 2, MyAvalancheRushGame.INSTANCE.getScreenHeight() - 20);

        batch.draw(playButtonTexture, playButton.x, playButton.y);
        GlyphLayout layout = new GlyphLayout(fontSinglePlayer, "Play");
        fontPlayButton.draw(batch, "Play", playButton.x + (playButton.width - layout.width + 60) / 2, playButton.y + (playButton.height + layout.height - 12) / 2);

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
                    // Handle play button click
                    return true;
                } else if (homeButton.contains(touchPos.x, touchPos.y)) {
                    // Return to the MenuView
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
        fontSinglePlayer.dispose();
        backGroundTexture.dispose();
    }
}







