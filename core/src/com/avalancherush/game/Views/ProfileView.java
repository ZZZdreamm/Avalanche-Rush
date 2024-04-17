package com.avalancherush.game.Views;

import com.avalancherush.game.Controllers.ProfileController;
import com.avalancherush.game.Enums.EventType;

import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.SINGLE_PLAYER_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.heightScale;
import static com.avalancherush.game.Configuration.GlobalVariables.widthScale;
import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.MODIFY_BUTTON;
import static com.avalancherush.game.Configuration.Textures.SINGLE_PLAYER;
import static com.avalancherush.game.Configuration.Textures.SKIN;
import static com.avalancherush.game.Configuration.Textures.TABLE_LOBBY;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;

import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Models.JsonEditor;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class ProfileView extends ScreenAdapter implements Input.TextInputListener {

    private GameThread gameThread;
    private ProfileController profileController;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Rectangle homeButton;
    private BitmapFont font;
    public static String username;
    private Rectangle changeUsernameButton;
    private BitmapFont fontTitle;
    private BitmapFont gameRulesFont;
    private Rectangle basicSkin;
    private Rectangle masterSkin;


    public ProfileView() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.profileController = new ProfileController();
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth() * widthScale, HOME_BUTTON.getHeight() * heightScale);
        username = gameThread.getJsonIntance().getName();
        this.font = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.font.getData().setScale(0.9f * heightScale);
        this.changeUsernameButton = new Rectangle((float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() + WOOD_BUTTON.getWidth() * widthScale + MODIFY_BUTTON.getWidth() * widthScale) / 2, (float) MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2 + 30, MODIFY_BUTTON.getWidth() * widthScale, MODIFY_BUTTON.getHeight() * heightScale);
        this.fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.fontTitle.getData().setScale(1 * heightScale);
        this.gameRulesFont = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.gameRulesFont.getData().setScale(0.6f * heightScale);
        this.basicSkin = (new Rectangle(250,20, SINGLE_PLAYER_WIDTH, SINGLE_PLAYER_HEIGHT));
        this.masterSkin = (new Rectangle(250 + SINGLE_PLAYER_WIDTH + 10, 20, SINGLE_PLAYER_WIDTH, SINGLE_PLAYER_HEIGHT));
    }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        float usernameX = ((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth() * widthScale) / 2;

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Profile");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        float woodBeamY = (float) MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2 + 30;
        batch.draw(WOOD_BUTTON, usernameX, woodBeamY, WOOD_BUTTON.getWidth() * widthScale, WOOD_BUTTON.getHeight() * heightScale);
        batch.draw(WOOD_BUTTON,usernameX,woodBeamY - WOOD_BUTTON.getHeight() * heightScale - 12,WOOD_BUTTON.getWidth() * widthScale, WOOD_BUTTON.getHeight() * heightScale);
        batch.draw(TABLE_LOBBY,usernameX,woodBeamY - 2 * WOOD_BUTTON.getHeight() * heightScale - TABLE_LOBBY.getHeight() * heightScale /2 - 12, TABLE_LOBBY.getWidth() * widthScale,TABLE_LOBBY.getHeight() * heightScale);

        GlyphLayout highestScoreLayout = new GlyphLayout(font, "HIGHEST SCORE");
        float highestScoreX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - highestScoreLayout.width) / 2;
        float highestScoreY = woodBeamY - WOOD_BUTTON.getHeight() * heightScale - 12 + WOOD_BUTTON.getHeight() * heightScale / 2 + highestScoreLayout.height / 2;
        font.draw(batch, highestScoreLayout, highestScoreX, highestScoreY);
        //font.draw(batch,"HIGHEST SCORE",usernameX + (WOOD_BUTTON.getWidth() * widthScale - )/ 2,(woodBeamY - WOOD_BUTTON.getHeight() * heightScale - 12) + WOOD_BUTTON.getHeight() * heightScale / 2);

        //float changeButtonX = usernameX + WOOD_BUTTON.getWidth() * widthScale + 20;
        //float changeButtonY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - MODIFY_BUTTON.getHeight() * heightScale;
        //float changeButtonY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - 200 * heightScale;
        //changeUsernameButton.setPosition(changeButtonX, changeButtonY);

        batch.draw(MODIFY_BUTTON, changeUsernameButton.x, changeUsernameButton.y, changeUsernameButton.width, changeUsernameButton.height);
        GlyphLayout usernameLayout = new GlyphLayout(font, "USERNAME\n" + getUsername());
        float usernameTextX = usernameX + (WOOD_BUTTON.getWidth() * widthScale - usernameLayout.width) / 2;
        float usernameTextY = woodBeamY + WOOD_BUTTON.getHeight() * heightScale / 2 + usernameLayout.height / 2;
        font.draw(batch, usernameLayout, usernameTextX, usernameTextY);
        //font.draw(batch, "USERNAME\n" + getUsername(), usernameX, woodBeamY);

        GlyphLayout gameRulesLayout = new GlyphLayout(gameRulesFont, "GAME RULES\nThe aim of the game is to get the highest score\npossible while avoiding trees and rocks\n(you can also jump over them by double tapping)\nYou won't be alone because thanks to the\nsnowboard your score will be doubled while the\nhelmet will allow you to hit an obstacle without\ndying\nHAVE FUN");
        while (gameRulesLayout.width >TABLE_LOBBY.getWidth() * widthScale - 12) {
            gameRulesFont.getData().setScale(gameRulesFont.getData().scaleX * 0.9f);
            gameRulesLayout = new GlyphLayout(gameRulesFont, "GAME RULES\nThe aim of the game is to get the highest score\npossible while avoiding trees and rocks\n(you can also jump over them by double tapping)\nYou won't be alone because thanks to the\nsnowboard your score will be doubled while the\nhelmet will allow you to hit an obstacle without\ndying\nHAVE FUN");
        }
        float gameRulesX = usernameX + (TABLE_LOBBY.getWidth() * widthScale - gameRulesLayout.width) / 2;
        float gameRulesY = woodBeamY - 2 * WOOD_BUTTON.getHeight() * heightScale - TABLE_LOBBY.getHeight() * heightScale /2 - 12 + (TABLE_LOBBY.getHeight() * heightScale + gameRulesLayout.height) / 2;
        gameRulesFont.draw(batch, gameRulesLayout, gameRulesX, gameRulesY);
        //font.draw(batch,"GAME RULES\nThe aim of the game is to get the highest score\npossible while avoiding trees and rocks\n(you can also jump over them by double tapping)\nYou won't be alone because thanks to the\nsnowboard your score will be doubled while the\nhelmet will allow you to hit an obstacle without\ndying\nHAVE FUN",usernameX,woodBeamY - 2 * WOOD_BUTTON.getHeight() * heightScale - 12);
        font.draw(batch,"Choose skin", basicSkin.x + 30, basicSkin.y + 75);
        String skinJsonName = gameThread.getJsonIntance().getSkin();
        if(skinJsonName.equals("BASIC")){
            batch.draw(TABLE_LOBBY, basicSkin.x, basicSkin.y, basicSkin.width/2, basicSkin.height/2);
        }else if (skinJsonName.equals("MASTER")){
            batch.draw(TABLE_LOBBY, masterSkin.x, masterSkin.y, basicSkin.width/2, basicSkin.height/2);
        }
        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y, homeButton.width, homeButton.height);
        batch.draw(SINGLE_PLAYER, basicSkin.x, basicSkin.y);
        batch.draw(SKIN, masterSkin.x, masterSkin.y);


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
                    Gdx.input.getTextInput(new ProfileView(),"Enter Username", gameThread.getJsonIntance().getName(), "");
                    Gdx.app.log("Text", username);
                    return true;
                }

                if(basicSkin.contains(touchPos.x, touchPos.y)){
                    profileController.notify(EventType.CHANGE_SKIN, SkinType.BASIC);
                    return true;
                }
                if(masterSkin.contains(touchPos.x, touchPos.y)){
                    profileController.notify(EventType.CHANGE_SKIN, SkinType.MASTER);
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
        shapeRenderer.dispose();
        BACKGROUND.dispose();
        HOME_BUTTON.dispose();
        MODIFY_BUTTON.dispose();
        font.dispose();
        fontTitle.dispose();
    }

    @Override
    public void input(String text) {
        this.username = text;
        JsonEditor jsonEditor = gameThread.getJsonIntance();
        jsonEditor.setName(text);
        gameThread.setData(jsonEditor.getName(), jsonEditor.getSkin());

    }

    @Override
    public void canceled() {

    }
}