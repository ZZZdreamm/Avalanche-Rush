package com.avalancherush.game.Views;

import com.avalancherush.game.MyAvalancheRushGame;
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



public class MenuView extends ScreenAdapter {

    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Texture woodButtonTexture;
    private Texture backGroundTexture;
    private Texture profileButtonTexture;
    private Texture settingsButtonTexture;

    private Rectangle singlePlayerButton;
    private Rectangle multiPlayerButton;
    private Rectangle profileButton;
    private Rectangle settingsButton;
    private BitmapFont fontText;
    private BitmapFont fontTitle;
    public MenuView(OrthographicCamera orthographicCamera) {
        this.orthographicCamera = orthographicCamera;
        this.orthographicCamera.position.set(new Vector3((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2, (float)MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2,0 ));
        this.batch = new SpriteBatch();
        this.woodButtonTexture = new Texture((Gdx.files.internal("buttonWood.png")));
        this.backGroundTexture = new Texture(Gdx.files.internal("backGroundMountain.jpg"));
        this.profileButtonTexture = new Texture(Gdx.files.internal("buttonProfile.png"));
        this.settingsButtonTexture = new Texture(Gdx.files.internal("buttonSettings.png"));

        float buttonY = (float) MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2 + 30;
        float buttonSpacing = 12;

        this.singlePlayerButton = new Rectangle(((float)MyAvalancheRushGame.INSTANCE.getScreenWidth() - woodButtonTexture.getWidth()) / 2, buttonY, woodButtonTexture.getWidth(), woodButtonTexture.getHeight());
        this.multiPlayerButton = new Rectangle(singlePlayerButton.x, singlePlayerButton.y - woodButtonTexture.getHeight() - buttonSpacing, woodButtonTexture.getWidth(), woodButtonTexture.getHeight());
        this.profileButton = new Rectangle(50, 50, profileButtonTexture.getWidth(), profileButtonTexture.getHeight());

        this.settingsButton = new Rectangle(MyAvalancheRushGame.INSTANCE.getScreenWidth() - settingsButtonTexture.getWidth() - 50, 50, settingsButtonTexture.getWidth(), settingsButtonTexture.getHeight());

        fontText = new BitmapFont(Gdx.files.internal("font2.fnt"));
        fontText.getData().setScale(0.65f);

        fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        fontTitle.getData().setScale(1);
    }

    @Override
    public void render(float dt) {
        show();

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();
        batch.draw(backGroundTexture, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Avalanche Rush");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);


        batch.draw(woodButtonTexture, singlePlayerButton.x, singlePlayerButton.y);
        batch.draw(woodButtonTexture, multiPlayerButton.x, multiPlayerButton.y);

        GlyphLayout singlePlayerLayout = new GlyphLayout(fontText, "single player");
        float singlePlayerTextX = singlePlayerButton.x + (singlePlayerButton.getWidth() - singlePlayerLayout.width) / 2;
        float singlePlayerTextY = singlePlayerButton.y + (singlePlayerButton.getHeight() + singlePlayerLayout.height) / 2;
        fontText.draw(batch, singlePlayerLayout, singlePlayerTextX, singlePlayerTextY);


        GlyphLayout multiPlayerLayout = new GlyphLayout(fontText, "multiplayer");
        float multiPlayerTextX = multiPlayerButton.x + (multiPlayerButton.getWidth() - multiPlayerLayout.width) / 2;
        float multiPlayerTextY = multiPlayerButton.y + (multiPlayerButton.getHeight() + multiPlayerLayout.height) / 2;
        fontText.draw(batch, multiPlayerLayout, multiPlayerTextX, multiPlayerTextY);

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
                return true;
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        woodButtonTexture.dispose();
        backGroundTexture.dispose();
        profileButtonTexture.dispose();
        settingsButtonTexture.dispose();
        fontTitle.dispose();
        fontText.dispose();
    }
}

