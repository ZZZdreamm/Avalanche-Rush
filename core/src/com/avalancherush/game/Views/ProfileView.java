package com.avalancherush.game.Views;

import com.avalancherush.game.Controllers.ProfileController;
import com.avalancherush.game.Enums.EventType;

import static com.avalancherush.game.Globals.Fonts.BIG_BLACK_FONT;
import static com.avalancherush.game.Globals.GlobalVariables.SINGLE_PLAYER_HEIGHT;
import static com.avalancherush.game.Globals.GlobalVariables.SINGLE_PLAYER_WIDTH;
import static com.avalancherush.game.Globals.GlobalVariables.heightScale;
import static com.avalancherush.game.Globals.GlobalVariables.widthScale;
import static com.avalancherush.game.Globals.Textures.BACKGROUND;
import static com.avalancherush.game.Globals.Textures.HOME_BUTTON;
import static com.avalancherush.game.Globals.Textures.MODIFY_BUTTON;
import static com.avalancherush.game.Globals.Textures.SINGLE_PLAYER;
import static com.avalancherush.game.Globals.Textures.SKIN;
import static com.avalancherush.game.Globals.Textures.TABLE_LOBBY;
import static com.avalancherush.game.Globals.Textures.WOOD_BUTTON;

import com.avalancherush.game.Enums.SkinType;
import com.avalancherush.game.Interfaces.BasicView;
import com.avalancherush.game.Models.JsonEditor;
import com.avalancherush.game.MyAvalancheRushGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class ProfileView extends BasicView implements Input.TextInputListener {
    private ProfileController profileController;
    private ShapeRenderer shapeRenderer;
    private Rectangle homeButton;
    private BitmapFont font;
    public static String username;
    private Rectangle changeUsernameButton;
    private BitmapFont fontTitle;
    private Rectangle basicSkin;
    private Rectangle masterSkin, high;


    public ProfileView() {
        this.profileController = new ProfileController();
        this.shapeRenderer = new ShapeRenderer();
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth() * widthScale, HOME_BUTTON.getHeight() * heightScale);
        username = gameThread.getJsonIntance().getName();
        this.font = BIG_BLACK_FONT;
        this.font.getData().setScale(1.2f * heightScale);
        this.changeUsernameButton = new Rectangle((float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() + WOOD_BUTTON.getWidth() * widthScale + MODIFY_BUTTON.getWidth() * widthScale) / 2, (float) MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2 + 30, MODIFY_BUTTON.getWidth() * widthScale, MODIFY_BUTTON.getHeight() * heightScale);
        this.fontTitle = BIG_BLACK_FONT;
        this.fontTitle.getData().setScale(1 * heightScale);
        this.basicSkin = (new Rectangle((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2 - (SINGLE_PLAYER_WIDTH - 5) * widthScale, (float) MyAvalancheRushGame.INSTANCE.getScreenHeight() / 3 - SINGLE_PLAYER_HEIGHT * heightScale, SINGLE_PLAYER_WIDTH * widthScale , SINGLE_PLAYER_HEIGHT * heightScale));
        this.masterSkin = (new Rectangle((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2 + 5 * widthScale, (float) MyAvalancheRushGame.INSTANCE.getScreenHeight() / 3 - SINGLE_PLAYER_HEIGHT * heightScale, SINGLE_PLAYER_WIDTH * widthScale, SINGLE_PLAYER_HEIGHT * heightScale));
        float usernameX = ((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth() * widthScale) / 2;
        float woodBeamY = (float) MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2 + 30;
        this.high = new Rectangle(usernameX,woodBeamY - WOOD_BUTTON.getHeight() * heightScale - 12,WOOD_BUTTON.getWidth() * widthScale, WOOD_BUTTON.getHeight() * heightScale);

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

        GlyphLayout highestScoreLayout = new GlyphLayout(font, "HIGHEST SCORE");
        float highestScoreX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - highestScoreLayout.width) / 2;
        float highestScoreY = woodBeamY - WOOD_BUTTON.getHeight() * heightScale - 12 + WOOD_BUTTON.getHeight() * heightScale / 2 + highestScoreLayout.height / 2;
        font.draw(batch, highestScoreLayout, highestScoreX, highestScoreY);

        batch.draw(MODIFY_BUTTON, changeUsernameButton.x, changeUsernameButton.y, changeUsernameButton.width, changeUsernameButton.height);
        GlyphLayout usernameLayout = new GlyphLayout(font, "USERNAME\n" + getUsername());
        float usernameTextX = usernameX + (WOOD_BUTTON.getWidth() * widthScale - usernameLayout.width) / 2;
        float usernameTextY = woodBeamY + WOOD_BUTTON.getHeight() * heightScale / 2 + usernameLayout.height / 2;
        font.draw(batch, usernameLayout, usernameTextX, usernameTextY);

        GlyphLayout chooseSkinLayout = new GlyphLayout(font, "Choose skin");
        font.draw(batch,"Choose skin", usernameX + usernameX/2, woodBeamY - WOOD_BUTTON.getHeight() * heightScale - chooseSkinLayout.height - 10);
        String skinJsonName = gameThread.getJsonIntance().getSkin();
        if(skinJsonName.equals("BASIC")){
            batch.draw(TABLE_LOBBY, basicSkin.x, basicSkin.y, basicSkin.width, basicSkin.height);
        }else if (skinJsonName.equals("MASTER")){
            batch.draw(TABLE_LOBBY, masterSkin.x, masterSkin.y, basicSkin.width, basicSkin.height);
        }
        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y, homeButton.width, homeButton.height);
        batch.draw(SINGLE_PLAYER, basicSkin.x, basicSkin.y, basicSkin.width, basicSkin.height);
        batch.draw(SKIN, masterSkin.x, masterSkin.y, masterSkin.width, masterSkin.height);

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
                if(high.contains(touchPos.x, touchPos.y)){
                    profileController.notify(EventType.HIGH_SCORE);
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

    }

    @Override
    public void canceled() {

    }
}