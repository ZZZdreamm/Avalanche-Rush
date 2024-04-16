package com.avalancherush.game.Views;

import com.avalancherush.game.Controllers.ProfileController;
import com.avalancherush.game.Enums.EventType;
import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.MODIFY_BUTTON;
import static com.avalancherush.game.Configuration.Textures.TABLE_LOBBY;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;

import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;



public class ProfileView extends ScreenAdapter implements Input.TextInputListener {

    private GameThread gameThread;
    private ProfileController profileController;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Rectangle homeButton;
    private BitmapFont font;
    public static String username;
    private Rectangle changeUsernameButton;
    private BitmapFont fontTitle;

    public ProfileView() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.profileController = new ProfileController();
        this.batch = new SpriteBatch();
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth(), HOME_BUTTON.getHeight());
        username = "Default Username";
        this.font = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.font.getData().setScale(0.5f);
        this.changeUsernameButton = new Rectangle(50, 150, 100, 50);
        this.fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.fontTitle.getData().setScale(1);

    }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        float usernameX = ((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() - 150) / 2;

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Profile");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        float woodBeamY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - 200;
        batch.draw(WOOD_BUTTON, usernameX - 32, woodBeamY, 150 + 64, 74);
        batch.draw(WOOD_BUTTON,usernameX - 32,woodBeamY - 100,150+64,74);
        batch.draw(TABLE_LOBBY,usernameX - 32,woodBeamY - 300, TABLE_LOBBY.getWidth(),TABLE_LOBBY.getHeight());
        font.draw(batch,"HIGHEST SCORE",usernameX,woodBeamY - 100 + 50);

        float changeButtonX = usernameX + 150 + 50;
        float changeButtonY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - 200;
        changeUsernameButton.setPosition(changeButtonX, changeButtonY);

        batch.draw(MODIFY_BUTTON, changeUsernameButton.x, changeUsernameButton.y);
        font.draw(batch, "USERNAME\n" + getUsername(), usernameX, MyAvalancheRushGame.INSTANCE.getScreenHeight() - 150);
        font.draw(batch,"GAME RULES\nThe aim of the game is to get the highest score\npossible while avoiding trees and rocks\n(you can also jump over them by double tapping)\nYou won't be alone because thanks to the\nsnowboard your score will be doubled while the\nhelmet will allow you to hit an obstacle without\ndying\nHAVE FUN",usernameX,woodBeamY - TABLE_LOBBY.getHeight() - 32);
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

                if (homeButton.contains(touchPos.x, touchPos.y)) {
                    profileController.notify(EventType.HOME_BUTTON_CLICK);
                    return true;
                }

                if (changeUsernameButton.contains(touchPos.x, touchPos.y)) {
                    Gdx.input.getTextInput(new ProfileView(),"Enter Username", "", "Default_Username");
                    Gdx.app.log("Text", username);
                    return true;
                }

                return false;
            }

        });
    }

    public static String getUsername() {
        return username;
    }

    @Override
    public void dispose() {
        batch.dispose();
        BACKGROUND.dispose();
        HOME_BUTTON.dispose();
        MODIFY_BUTTON.dispose();
        font.dispose();
        fontTitle.dispose();
    }

    @Override
    public void input(String text) {
        this.username = text;
    }

    @Override
    public void canceled() {

    }
}