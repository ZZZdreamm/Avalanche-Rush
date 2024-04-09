package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;

import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class SinglePlayerView extends ScreenAdapter {

    private GameThread gameThread;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Rectangle playButton;
    private Rectangle homeButton;
    private BitmapFont fontTitle;
    private BitmapFont fontText;

    public SinglePlayerView() {
<<<<<<< HEAD
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.orthographicCamera = orthographicCamera;
=======
        this.orthographicCamera = GameThread.getInstance().getCamera();
>>>>>>> af723c9 (Made All Texture as GLOBAL VARIABLES)
        this.batch = new SpriteBatch();


        this.playButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth()) / 2, (MyAvalancheRushGame.INSTANCE.getScreenHeight() - WOOD_BUTTON.getHeight()) / 2, WOOD_BUTTON.getWidth(), WOOD_BUTTON.getHeight());
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

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Single Player");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);


        batch.draw(WOOD_BUTTON, playButton.x, playButton.y);

        GlyphLayout multiPlayerLayout = new GlyphLayout(fontText, "play");
        float multiPlayerTextX = playButton.x + (playButton.getWidth() - multiPlayerLayout.width) / 2;
        float multiPlayerTextY = playButton.y + (playButton.getHeight() + multiPlayerLayout.height) / 2;
        fontText.draw(batch, multiPlayerLayout, multiPlayerTextX, multiPlayerTextY);

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

                if (playButton.contains(touchPos.x, touchPos.y)) {
                    MyAvalancheRushGame.INSTANCE.setScreen(new GameViewSinglePlayer(orthographicCamera));
                    return true;

                } else if (homeButton.contains(touchPos.x, touchPos.y)) {
                    MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        WOOD_BUTTON.dispose();
        BACKGROUND.dispose();
        fontText.dispose();
        fontTitle.dispose();
    }
}







