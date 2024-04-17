package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.GlobalVariables.BASE_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.BASE_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.calculateScales;
import static com.avalancherush.game.Configuration.GlobalVariables.heightScale;
import static com.avalancherush.game.Configuration.GlobalVariables.widthScale;
import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.PROFILE_BUTTON;
import static com.avalancherush.game.Configuration.Textures.SETTINGS_BUTTON;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;

import com.avalancherush.game.Controllers.MainMenuController;
import com.avalancherush.game.Controllers.SinglePlayerController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
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
    private GameThread gameThread;
    private MainMenuController mainMenuController;
    private SpriteBatch batch;
    private Rectangle singlePlayerButton;
    private Rectangle multiPlayerButton;
    private Rectangle tutorialButton;
    private Rectangle profileButton;
    private Rectangle settingsButton;
    private BitmapFont fontText;
    private BitmapFont fontTitle;

    public MenuView() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.mainMenuController = new MainMenuController();
        this.orthographicCamera.position.set(new Vector3((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2, (float)MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2,0 ));
        this.batch = new SpriteBatch();

        calculateScales();
        this.singlePlayerButton = new Rectangle(((float)MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth() * widthScale) / 2, (float)MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2 + 30, WOOD_BUTTON.getWidth() * widthScale, WOOD_BUTTON.getHeight() * heightScale);
        this.multiPlayerButton = new Rectangle(singlePlayerButton.x, singlePlayerButton.y - WOOD_BUTTON.getHeight() * heightScale - 12, WOOD_BUTTON.getWidth() * widthScale, WOOD_BUTTON.getHeight() * heightScale);
        this.profileButton = new Rectangle(50, 50, PROFILE_BUTTON.getWidth() * widthScale, PROFILE_BUTTON.getHeight() * heightScale);
        this.settingsButton = new Rectangle(MyAvalancheRushGame.INSTANCE.getScreenWidth() - SETTINGS_BUTTON.getWidth() * widthScale - 50, 50, SETTINGS_BUTTON.getWidth() * widthScale, SETTINGS_BUTTON.getHeight() * heightScale);
        this.tutorialButton = new Rectangle(multiPlayerButton.x, multiPlayerButton.y - WOOD_BUTTON.getHeight() * heightScale - 12, WOOD_BUTTON.getWidth() * widthScale, WOOD_BUTTON.getHeight() * heightScale);

        /*
        this.singlePlayerButton = new Rectangle(((float)MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth()) / 2, (float)MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2 + 30, WOOD_BUTTON.getWidth(), WOOD_BUTTON.getHeight());
        this.multiPlayerButton = new Rectangle(singlePlayerButton.x, singlePlayerButton.y - WOOD_BUTTON.getHeight() - 12, WOOD_BUTTON.getWidth(), WOOD_BUTTON.getHeight());
        this.profileButton = new Rectangle(50, 50, PROFILE_BUTTON.getWidth(), PROFILE_BUTTON.getHeight());
        this.settingsButton = new Rectangle(MyAvalancheRushGame.INSTANCE.getScreenWidth() - SETTINGS_BUTTON.getWidth() - 50, 50, SETTINGS_BUTTON.getWidth(), SETTINGS_BUTTON.getHeight());

         */

        this.fontText = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.fontText.getData().setScale(1.2f * heightScale);
        this.fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.fontTitle.getData().setScale(1 * heightScale);

        /*
        this.fontText = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.fontText.getData().setScale(0.65f);
        this.fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.fontTitle.getData().setScale(1);

         */
    }

    @Override
    public void render(float dt) {
        show();

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Avalanche Rush");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        batch.draw(WOOD_BUTTON, singlePlayerButton.x, singlePlayerButton.y, singlePlayerButton.getWidth(), singlePlayerButton.getHeight());
        batch.draw(WOOD_BUTTON, multiPlayerButton.x, multiPlayerButton.y, multiPlayerButton.getWidth(), multiPlayerButton.getHeight());
        batch.draw(WOOD_BUTTON, tutorialButton.x, tutorialButton.y, tutorialButton.getWidth(), tutorialButton.getHeight());

        //batch.draw(WOOD_BUTTON, singlePlayerButton.x, singlePlayerButton.y);
        //batch.draw(WOOD_BUTTON, multiPlayerButton.x, multiPlayerButton.y);

        GlyphLayout singlePlayerLayout = new GlyphLayout(fontText, "single player");
        float singlePlayerTextX = singlePlayerButton.x + (singlePlayerButton.getWidth() - singlePlayerLayout.width) / 2;
        float singlePlayerTextY = singlePlayerButton.y + (singlePlayerButton.getHeight() + singlePlayerLayout.height) / 2;
        fontText.draw(batch, singlePlayerLayout, singlePlayerTextX, singlePlayerTextY);

        GlyphLayout multiPlayerLayout = new GlyphLayout(fontText, "multiplayer");
        float multiPlayerTextX = multiPlayerButton.x + (multiPlayerButton.getWidth() - multiPlayerLayout.width) / 2;
        float multiPlayerTextY = multiPlayerButton.y + (multiPlayerButton.getHeight() + multiPlayerLayout.height) / 2;
        fontText.draw(batch, multiPlayerLayout, multiPlayerTextX, multiPlayerTextY);

        GlyphLayout tutorialLayout = new GlyphLayout(fontText, "tutorial");
        float tutorialX = tutorialButton.x + (tutorialButton.getWidth() - tutorialLayout.width) / 2;
        float tutorialY = tutorialButton.y + (tutorialButton.getHeight() + tutorialLayout.height) / 2;
        fontText.draw(batch, tutorialLayout, tutorialX, tutorialY);

        batch.draw(PROFILE_BUTTON, profileButton.x, profileButton.y, profileButton.getWidth(), profileButton.getHeight());
        batch.draw(SETTINGS_BUTTON, settingsButton.x, settingsButton.y, settingsButton.getWidth(), settingsButton.getHeight());
        //batch.draw(PROFILE_BUTTON, profileButton.x, profileButton.y);
        //batch.draw(SETTINGS_BUTTON, settingsButton.x, settingsButton.y);

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
                    mainMenuController.notify(EventType.SINGLE_PLAYER_BUTTON_CLICK);
                    return true;
                } else if (multiPlayerButton.contains(touchPos.x, touchPos.y)) {
                    mainMenuController.notify(EventType.MULTIPLAYER_BUTTON_CLICK);
                    return true;
                } else if (tutorialButton.contains(touchPos.x, touchPos.y)) {
                    mainMenuController.notify(EventType.TUTORIAL_BUTTON_CLICK);
                    return true;
                } else if (profileButton.contains(touchPos.x, touchPos.y)) {
                    mainMenuController.notify(EventType.PROFILE_BUTTON_CLICK);
                    return true;
                } else if (settingsButton.contains(touchPos.x, touchPos.y)) {
                    mainMenuController.notify(EventType.SETTINGS_BUTTON_CLICK);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        WOOD_BUTTON.dispose();
        BACKGROUND.dispose();
        PROFILE_BUTTON.dispose();
        SETTINGS_BUTTON.dispose();
        fontTitle.dispose();
        fontText.dispose();
    }
}

