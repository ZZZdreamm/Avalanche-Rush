package com.avalancherush.game.Views;

import com.avalancherush.game.Controllers.TutorialController;
import com.avalancherush.game.Enums.EventType;

import static com.avalancherush.game.Configuration.Fonts.BIG_BLACK_FONT;
import static com.avalancherush.game.Configuration.GlobalVariables.heightScale;
import static com.avalancherush.game.Configuration.GlobalVariables.widthScale;
import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.IMG_TUTORIAL;

import com.avalancherush.game.Interfaces.BasicView;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class TutorialView extends BasicView {
    private TutorialController tutorialController;
    private Rectangle homeButton;
    private BitmapFont font;
    private BitmapFont fontTitle;
    public TutorialView() {
        this.tutorialController = new TutorialController();

        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth() * widthScale, HOME_BUTTON.getHeight() * heightScale);

        this.font = BIG_BLACK_FONT;
        this.font.getData().setScale(0.9f * heightScale);

        this.fontTitle = BIG_BLACK_FONT;
        this.fontTitle.getData().setScale(1 * heightScale);


    }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Tutorial");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);


        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y, homeButton.width, homeButton.height);

        Texture imgTutorial = IMG_TUTORIAL;
        float imgTutorialWidth = imgTutorial.getWidth() * 0.4f * widthScale;
        float imgTutorialHeight = imgTutorial.getHeight() * 0.4f * heightScale;
        float imgTutorialX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - imgTutorialWidth) / 2;
        float imgTutorialY = gameLogoY - imgTutorialHeight - 70;
        batch.draw(imgTutorial, imgTutorialX, imgTutorialY, imgTutorialWidth, imgTutorialHeight);


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
                    tutorialController.notify(EventType.HOME_BUTTON_CLICK);
                    return true;
                }

                return false;
            }

        });
    }


    @Override
    public void dispose() {
        batch.dispose();
        BACKGROUND.dispose();
        HOME_BUTTON.dispose();
        font.dispose();
        fontTitle.dispose();
    }
}