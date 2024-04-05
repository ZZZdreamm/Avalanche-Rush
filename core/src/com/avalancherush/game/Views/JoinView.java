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



public class JoinView extends ScreenAdapter {

    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture homeButtonTexture;
    private Rectangle homeButton;
    private Texture woodBeamTexture;
    private BitmapFont fontTitle;
    private Texture joinLogo;
    private String code = "";
    private boolean editingCode = false;
    private Rectangle woodBeamBounds;
    private float CodeX;
    private float woodBeamY;
    private Texture buttonPlayTexture;

    public JoinView(OrthographicCamera orthographicCamera) {
        this.orthographicCamera = orthographicCamera;
        this.batch = new SpriteBatch();
        this.joinLogo = new Texture(Gdx.files.internal("joinLogo.png"));
        this.backgroundTexture = new Texture(Gdx.files.internal("backGroundMountain.jpg"));
        this.homeButtonTexture = new Texture(Gdx.files.internal("buttonHome.png"));
        this.homeButton = new Rectangle(50, 50, homeButtonTexture.getWidth(), homeButtonTexture.getHeight());
        this.woodBeamTexture = new Texture(Gdx.files.internal("buttonWood2.png"));
        this.buttonPlayTexture = new Texture(Gdx.files.internal("buttonPlay.png"));
        this.fontTitle = new BitmapFont();
        this.fontTitle.setColor(Color.WHITE);
        this.fontTitle.getData().setScale(1);

        CodeX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - 150) / 2;
        woodBeamY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - 250;

        this.woodBeamBounds = new Rectangle(CodeX - 32, woodBeamY, 150 + 64, 74);

        // Input handling
        Gdx.input.setInputProcessor(new MyInputAdapter());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(backgroundTexture, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());
        batch.draw(joinLogo, (MyAvalancheRushGame.INSTANCE.getScreenWidth() - joinLogo.getWidth() + 100) / 2, MyAvalancheRushGame.INSTANCE.getScreenHeight() - joinLogo.getHeight() - 20);

        float woodBeamWidth = 150 + 64; // Width of the woodBeamTexture including margins
        float buttonPlayWidth = buttonPlayTexture.getWidth(); // Width of the buttonPlay.png

        float totalWidth = woodBeamWidth + buttonPlayWidth;
        float woodBeamX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - totalWidth) / 2;
        float buttonPlayX = woodBeamX + woodBeamWidth;

        batch.draw(woodBeamTexture, woodBeamX, woodBeamY, woodBeamWidth, 74);
        batch.draw(buttonPlayTexture, buttonPlayX, woodBeamY, buttonPlayWidth, 74);
        fontTitle.draw(batch, "Insert: " + code, CodeX, woodBeamY + 50);
        batch.draw(homeButtonTexture, homeButton.x, homeButton.y);

        batch.end();
    }

    private class MyInputAdapter extends InputAdapter {
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

    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        homeButtonTexture.dispose();
        woodBeamTexture.dispose();
        buttonPlayTexture.dispose();
        fontTitle.dispose();
    }
}