package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.PLAY_BUTTON;
import static com.avalancherush.game.Configuration.Textures.TABLE_LOBBY;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;

import com.avalancherush.game.Controllers.LobbyController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
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



public class LobbyView extends ScreenAdapter {
    private GameThread gameThread;
    private LobbyController lobbyController;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;

    private Rectangle playButton;
    private BitmapFont fontTitle;

    private float CodeX;
    private float woodBeamY;
    private BitmapFont fontText;
    private String username;

    public LobbyView() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.lobbyController = new LobbyController();
        this.batch = new SpriteBatch();

        float buttonX = 214 + ((MyAvalancheRushGame.INSTANCE.getScreenWidth() - (PLAY_BUTTON.getWidth()+214)) / 2);
        float buttonY = (MyAvalancheRushGame.INSTANCE.getScreenHeight() - PLAY_BUTTON.getHeight()) / 2;
        this.playButton = new Rectangle(buttonX, buttonY, PLAY_BUTTON.getWidth(), PLAY_BUTTON.getHeight());

        CodeX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - 150) / 2;
        woodBeamY = (MyAvalancheRushGame.INSTANCE.getScreenHeight() - 140) / 2;


        username = ProfileView.getUsername();
        if(username == null)
            username = "Default Username";

        Gdx.input.setInputProcessor(new MyInputAdapter());
        fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        fontTitle.getData().setScale(1f);

        this.fontText = new BitmapFont();
        this.fontText.setColor(Color.WHITE);
        this.fontText.getData().setScale(1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        float woodBeamWidth = 150 + 64; // Width of the WOOD_BUTTON including margins
        float buttonPlayWidth = PLAY_BUTTON.getWidth(); // Width of the buttonPlay.png

        float totalWidth = woodBeamWidth + buttonPlayWidth;
        float woodBeamX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - totalWidth) / 2;
        float buttonPlayX = woodBeamX + woodBeamWidth;

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Lobby");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        batch.draw(TABLE_LOBBY, woodBeamX, woodBeamY, woodBeamWidth, 140);
        batch.draw(PLAY_BUTTON, buttonPlayX, woodBeamY+35, buttonPlayWidth, 74);

        GlyphLayout usernameLayout = new GlyphLayout(fontText, username);
        float usernameX = woodBeamX + (woodBeamWidth - usernameLayout.width) / 2;
        float usernameY = woodBeamY + (woodBeamY + usernameLayout.height) / 2;
        fontText.draw(batch, usernameLayout, usernameX, usernameY+15);

        GlyphLayout player2Layout = new GlyphLayout(fontText, "PLAYER2");
        float player2X = usernameX + (usernameLayout.width - player2Layout.width) / 2;
        float player2Y = usernameY - player2Layout.height - 35;
        fontText.draw(batch, player2Layout, player2X, player2Y);

        batch.end();
    }

    private class MyInputAdapter extends InputAdapter {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 touchPos = new Vector3(screenX, screenY, 0);
            orthographicCamera.unproject(touchPos);

            if (playButton.contains(touchPos.x, touchPos.y)) {
                lobbyController.notify(EventType.HOME_BUTTON_CLICK); //change HOME_BUTTON with PLAY_BUTTON
                return true;
            }

            return false;
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        BACKGROUND.dispose();
        HOME_BUTTON.dispose();
        WOOD_BUTTON.dispose();
        PLAY_BUTTON.dispose();
        fontTitle.dispose();
        fontText.dispose();
    }
}