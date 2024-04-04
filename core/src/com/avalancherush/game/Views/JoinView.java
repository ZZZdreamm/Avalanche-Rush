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



public class JoinView extends ScreenAdapter{

    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture homeButtonTexture;
    private Rectangle homeButton;
    private Texture woodBeamTexture;
    private BitmapFont fontTitle;
    private Texture joinLogo;
    private String code = " ";
    private boolean editingCode = false;
    private StringBuilder codeBuilder = new StringBuilder();
    private Rectangle codeInputArea;
    float CodeX = ((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() - 150) / 2;
    float woodBeamY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - 250;
    public JoinView(OrthographicCamera orthographicCamera) {
        this.orthographicCamera = orthographicCamera;
        this.batch = new SpriteBatch();
        this.joinLogo = new Texture((Gdx.files.internal("joinLogo.png")));
        this.backgroundTexture = new Texture(Gdx.files.internal("backGroundMountain.jpg"));
        this.homeButtonTexture = new Texture(Gdx.files.internal("buttonHome.png"));
        this.homeButton = new Rectangle(50, 50, homeButtonTexture.getWidth(), homeButtonTexture.getHeight());
        this.woodBeamTexture = new Texture(Gdx.files.internal("buttonWood2.png"));
        this.fontTitle = new BitmapFont();
        this.fontTitle.setColor(Color.WHITE);
        this.fontTitle.getData().setScale(1);
        this.codeInputArea = new Rectangle(CodeX - 32, woodBeamY, 150 + 64, 74 );
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(backgroundTexture, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());
        batch.draw(joinLogo, ((float)MyAvalancheRushGame.INSTANCE.getScreenWidth() - joinLogo.getWidth() + 100) / 2, MyAvalancheRushGame.INSTANCE.getScreenHeight() - joinLogo.getHeight() - 20);

        batch.draw(woodBeamTexture, CodeX-32, woodBeamY, 150 + 64, 74);
        fontTitle.draw(batch, "Game code: " + code, CodeX, woodBeamY+50);
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
                } else if (codeInputArea.contains(touchPos.x, touchPos.y)) {
                    editingCode = true;
                    return true;
                }

                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                if (editingCode) {
                    if (character == '\n') {
                        code = codeBuilder.toString();
                        codeBuilder.setLength(0);
                        editingCode = false;
                        return true;
                    } else if (character == '\b' && codeBuilder.length() > 0) {
                        codeBuilder.deleteCharAt(codeBuilder.length() - 1);
                    } else if (Character.isDigit(character) && codeBuilder.length() < 5) {
                        codeBuilder.append(character);
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
        if (editingCode) {
            code = codeBuilder.toString();
            codeBuilder.setLength(0);
            editingCode = false;
        }
    }
}