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

public class GameEndView extends ScreenAdapter {

    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture homeButtonTexture;
    private Texture lostButtonTexture;
    private Rectangle homeButton;
    private BitmapFont scoreFont;
    private BitmapFont gameOverFont;

    public GameEndView(OrthographicCamera orthographicCamera) {
        this.orthographicCamera = orthographicCamera;
        this.batch = new SpriteBatch();
        this.backgroundTexture = new Texture(Gdx.files.internal("backGroundMountain.jpg"));
        this.homeButtonTexture = new Texture(Gdx.files.internal("buttonHome.png"));
        this.lostButtonTexture = new Texture(Gdx.files.internal("buttonWood2.png"));
        this.homeButton = new Rectangle(50, 50, homeButtonTexture.getWidth(), homeButtonTexture.getHeight());
        this.scoreFont = new BitmapFont();
        this.scoreFont.setColor(Color.WHITE);
        this.scoreFont.getData().setScale(2);
        this.gameOverFont = new BitmapFont();
        this.gameOverFont.setColor(Color.BLACK);
        this.gameOverFont.getData().setScale(4);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(backgroundTexture, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        // Draw "GAME OVER" text at the top center
        GlyphLayout gameOverLayout = new GlyphLayout(gameOverFont, "GAME OVER");
        float gameOverX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameOverLayout.width) / 2;
        float gameOverY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameOverLayout.height - 50; // Adjusted to leave some space from the top
        gameOverFont.draw(batch, "GAME OVER", gameOverX, gameOverY);

        // Calculate the position to center the lost button
        float buttonX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - lostButtonTexture.getWidth()) / 2;
        float buttonY = (MyAvalancheRushGame.INSTANCE.getScreenHeight() - lostButtonTexture.getHeight()) / 2;

        batch.draw(lostButtonTexture, buttonX, buttonY);

        // Calculate the position to draw the text inside the button
        GlyphLayout layout = new GlyphLayout(scoreFont, "Score: ");
        float textX = buttonX + (lostButtonTexture.getWidth() - layout.width - 80) / 2;
        float textY = buttonY + (lostButtonTexture.getHeight() + layout.height) / 2;

        scoreFont.draw(batch, "Score: ", textX, textY);

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

                if (homeButton.contains(touchPos.x, touchPos.y)) {
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
        backgroundTexture.dispose();
        homeButtonTexture.dispose();
        scoreFont.dispose();
        gameOverFont.dispose();
    }
}

