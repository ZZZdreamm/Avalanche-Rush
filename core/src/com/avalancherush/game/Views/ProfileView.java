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

public class ProfileView extends ScreenAdapter {

    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture homeButtonTexture;
    private Rectangle homeButton;
    private Texture woodBeamTexture;
    private BitmapFont fontTitle;
    private String username = "Default_Username";
    private Rectangle changeUsernameButton;
    private boolean editingUsername = false;
    private StringBuilder usernameBuilder = new StringBuilder();

    public ProfileView(OrthographicCamera orthographicCamera) {
        this.orthographicCamera = orthographicCamera;
        this.batch = new SpriteBatch();
        this.backgroundTexture = new Texture(Gdx.files.internal("backGroundMountain.jpg"));
        this.homeButtonTexture = new Texture(Gdx.files.internal("buttonHome.png"));
        this.homeButton = new Rectangle(50, 50, homeButtonTexture.getWidth(), homeButtonTexture.getHeight());
        this.woodBeamTexture = new Texture(Gdx.files.internal("buttonWood2.png"));
        this.fontTitle = new BitmapFont();
        this.fontTitle.setColor(Color.WHITE);
        this.fontTitle.getData().setScale(1);
        this.changeUsernameButton = new Rectangle(50, 150, 100, 50);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(backgroundTexture, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());


        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(fontTitle, "Username: " + username);
        float usernameWidth = glyphLayout.width;


        float usernameX = ((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() - usernameWidth) / 2;


        float woodBeamY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - 120;
        batch.draw(woodBeamTexture, usernameX-16, woodBeamY, usernameWidth+32, 74);


        //fontTitle.draw(batch, "Username: " + username, usernameX, MyAvalancheRushGame.INSTANCE.getScreenHeight() - 70);


        float changeButtonX = usernameX + usernameWidth + 20;
        float changeButtonY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - 120;
        changeUsernameButton.setPosition(changeButtonX, changeButtonY);


        batch.draw(homeButtonTexture, changeUsernameButton.x, changeUsernameButton.y);


        batch.draw(homeButtonTexture, homeButton.x, homeButton.y);


        if (editingUsername) {
            fontTitle.draw(batch, "New Username: " + usernameBuilder.toString(), usernameX, MyAvalancheRushGame.INSTANCE.getScreenHeight() - 70);
        } else {
            fontTitle.draw(batch, "Username: " + username, usernameX, MyAvalancheRushGame.INSTANCE.getScreenHeight() - 70);
        }

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

                if (changeUsernameButton.contains(touchPos.x, touchPos.y)) {
                    editingUsername = true;
                    return true;
                }

                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                if (editingUsername) {
                    if (character == '\b' && usernameBuilder.length() > 0) {
                        usernameBuilder.deleteCharAt(usernameBuilder.length() - 1);
                    } else if (character != '\b') {
                        usernameBuilder.append(character);
                    }
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
        fontTitle.dispose();
    }

    @Override
    public void hide() {
        if (editingUsername) {
            username = usernameBuilder.toString();
            usernameBuilder.setLength(0);
            editingUsername = false;
        }
    }
}