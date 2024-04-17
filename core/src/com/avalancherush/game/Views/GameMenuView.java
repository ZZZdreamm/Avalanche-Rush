package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.GlobalVariables.heightScale;
import static com.avalancherush.game.Configuration.GlobalVariables.widthScale;
import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.VOLUME_DOWN_BUTTON;
import static com.avalancherush.game.Configuration.Textures.VOLUME_UP_BUTTON;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;

import com.avalancherush.game.Controllers.GameMenuController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameMenuView extends ScreenAdapter {

    private OrthographicCamera orthographicCamera;
    private GameThread gameThread;
    private GameMenuController gameMenuController;
    private SpriteBatch batch;
    private Rectangle resumeButton;
    private Rectangle volumeUpButton;
    private Rectangle volumeDownButton;
    private Rectangle homeButton;
    private BitmapFont fontTitle;
    private BitmapFont fontText;

    public GameMenuView() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.batch = new SpriteBatch();
        this.gameMenuController = new GameMenuController();

        this.resumeButton = new Rectangle(((float)MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth() * widthScale) / 2, ((float)MyAvalancheRushGame.INSTANCE.getScreenHeight() - WOOD_BUTTON.getHeight() * heightScale) / 2 + 50, WOOD_BUTTON.getWidth() * widthScale, WOOD_BUTTON.getHeight() * heightScale);
        this.volumeUpButton = new Rectangle(((float)MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth() * widthScale) / 2, resumeButton.y - WOOD_BUTTON.getHeight() *heightScale - 20, VOLUME_UP_BUTTON.getWidth() * widthScale, VOLUME_UP_BUTTON.getHeight() *heightScale);
        this.volumeDownButton = new Rectangle(((float)MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth() * widthScale) / 2 + 150 * widthScale, volumeUpButton.y, VOLUME_DOWN_BUTTON.getWidth() * widthScale, VOLUME_DOWN_BUTTON.getHeight() *heightScale);
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth() *widthScale, HOME_BUTTON.getHeight() *heightScale);

        this.fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.fontTitle.getData().setScale(3f * heightScale);
        this.fontText = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.fontText.getData().setScale(1.5f * heightScale);
    }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Game Menu");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        batch.draw(WOOD_BUTTON, resumeButton.x, resumeButton.y, resumeButton.width, resumeButton.height);
        GlyphLayout resumeLayout = new GlyphLayout(fontText, "Resume");
        float resumeTextX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - resumeLayout.width) / 2;
        float resumeTextY = resumeButton.y + (resumeButton.getHeight() + resumeLayout.height - 30) / 2 + 20;
        fontText.draw(batch, resumeLayout, resumeTextX, resumeTextY);

        batch.draw(VOLUME_UP_BUTTON, volumeUpButton.x, volumeUpButton.y, volumeUpButton.width, volumeUpButton.height);
        batch.draw(VOLUME_DOWN_BUTTON, volumeDownButton.x, volumeDownButton.y, volumeDownButton.width, volumeDownButton.height);

        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y, homeButton.width, homeButton.height);

        batch.end();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                orthographicCamera.unproject(touchPos);

                if (resumeButton.contains(touchPos.x, touchPos.y)) {
                    gameMenuController.notify(EventType.RESUME_BUTTON_CLICK);
                } else if (homeButton.contains(touchPos.x, touchPos.y)) {
                    gameMenuController.notify(EventType.HOME_BUTTON_CLICK);
                } else if (volumeUpButton.contains(touchPos.x, touchPos.y)) {
                    gameMenuController.notify(EventType.VOLUME_UP);
                } else if (volumeDownButton.contains(touchPos.x, touchPos.y)) {
                    gameMenuController.notify(EventType.VOLUME_DOWN);
                }
                return false;
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        fontText.dispose();
        fontTitle.dispose();
        HOME_BUTTON.dispose();
        WOOD_BUTTON.dispose();
    }
}

