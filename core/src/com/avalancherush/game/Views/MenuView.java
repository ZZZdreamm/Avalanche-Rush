package com.avalancherush.game.Views;

import com.avalancherush.game.MyAvalancheRushGame;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class MenuView extends ScreenAdapter {

    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Texture texture;
    private Texture backGroundTexture;
    private Texture profileButtonTexture;
    private Texture settingsButtonTexture;
    private Rectangle singlePlayerButton;
    private Rectangle multiPlayerButton;
    private Rectangle profileButton;
    private Rectangle settingsButton;


    private BitmapFont fontAvalancheGame;
    private BitmapFont fontButton;

    public MenuView(OrthographicCamera orthographicCamera) {
        this.orthographicCamera = orthographicCamera;
        this.orthographicCamera.position.set(new Vector3((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2, (float)MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2,0 ));
        this.batch = new SpriteBatch();
        this.texture = new Texture((Gdx.files.internal("buttonWood.png")));
        this.backGroundTexture = new Texture(Gdx.files.internal("backGroundMountain.jpg"));
        this.profileButtonTexture = new Texture(Gdx.files.internal("buttonProfile.png"));
        this.settingsButtonTexture = new Texture(Gdx.files.internal("buttonSettings.png"));

        float buttonY = (float) MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2 + 30;
        float buttonSpacing = 12;

        this.singlePlayerButton = new Rectangle(((float)MyAvalancheRushGame.INSTANCE.getScreenWidth() - texture.getWidth()) / 2, buttonY, texture.getWidth(), texture.getHeight());
        this.multiPlayerButton = new Rectangle(singlePlayerButton.x, singlePlayerButton.y - texture.getHeight() - buttonSpacing, texture.getWidth(), texture.getHeight());
        this.profileButton = new Rectangle(50, 50, profileButtonTexture.getWidth(), profileButtonTexture.getHeight());

        // Adjust the position of the settings button to the bottom right corner
        this.settingsButton = new Rectangle(MyAvalancheRushGame.INSTANCE.getScreenWidth() - settingsButtonTexture.getWidth() - 50, 50, settingsButtonTexture.getWidth(), settingsButtonTexture.getHeight());

        this.fontAvalancheGame = new BitmapFont();
        this.fontButton = new BitmapFont();
        this.fontAvalancheGame.setColor(Color.BLACK);
        this.fontAvalancheGame.getData().setScale(5);
        this.fontButton.getData().setScale(2);
        this.fontButton.setColor(Color.BLACK);
    }

    @Override
    public void render(float dt) {
        show();

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(backGroundTexture, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        fontAvalancheGame.draw(batch,"Avalanche Rush",((float)MyAvalancheRushGame.INSTANCE.getScreenWidth() - 525) / 2, MyAvalancheRushGame.INSTANCE.getScreenHeight() - 20);

        batch.draw(texture, singlePlayerButton.x, singlePlayerButton.y);
        GlyphLayout firstButtonLayout = new GlyphLayout(fontButton,"Single Player");
        fontButton.draw(batch, "Single Player", singlePlayerButton.x + (singlePlayerButton.width - firstButtonLayout.width) / 2, singlePlayerButton.y + (singlePlayerButton.height + firstButtonLayout.height + 15) / 2);

        batch.draw(texture, multiPlayerButton.x, multiPlayerButton.y);
        GlyphLayout secondButtonLayout = new GlyphLayout(fontButton, "Multiplayer");
        fontButton.draw(batch, "Multiplayer", multiPlayerButton.x + (multiPlayerButton.width - secondButtonLayout.width) / 2, multiPlayerButton.y + (multiPlayerButton.height + secondButtonLayout.height + 15) / 2);

        batch.draw(profileButtonTexture, profileButton.x, profileButton.y);

        batch.draw(settingsButtonTexture, settingsButton.x, settingsButton.y);

        batch.end();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                orthographicCamera.unproject(touchPos);

                if (singlePlayerButton.contains(touchPos.x, touchPos.y)) {
                    MyAvalancheRushGame.INSTANCE.setScreen(new SinglePlayerView(orthographicCamera));
                } else if (multiPlayerButton.contains(touchPos.x, touchPos.y)) {
                    MyAvalancheRushGame.INSTANCE.setScreen(new MultiPlayerView(orthographicCamera));
                } else if (profileButton.contains(touchPos.x, touchPos.y)) {
                    MyAvalancheRushGame.INSTANCE.setScreen(new ProfileView(orthographicCamera));
                } else if (settingsButton.contains(touchPos.x, touchPos.y)) {
                    MyAvalancheRushGame.INSTANCE.setScreen(new SettingsView(orthographicCamera));
                }
                return true; // Indicate that the touch event was handled
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
        fontAvalancheGame.dispose();
        backGroundTexture.dispose();
        profileButtonTexture.dispose();
        settingsButtonTexture.dispose();
    }
}

