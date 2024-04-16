package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.GlobalVariables.BASE_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.BASE_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.heightScale;
import static com.avalancherush.game.Configuration.GlobalVariables.widthScale;
import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;

import com.avalancherush.game.Controllers.SinglePlayerController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.SinglePlayerGameThread;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Queue;

public class SinglePlayerView extends ScreenAdapter {

    private GameThread gameThread;
    private OrthographicCamera orthographicCamera;
    private SinglePlayerController singlePlayerController;
    private SinglePlayerGameThread singlePlayerGameThread;
    private SpriteBatch batch;
    private Rectangle playButton;
    private Rectangle homeButton;
    private BitmapFont fontTitle;
    private BitmapFont fontText;

    public SinglePlayerView() {
        this.gameThread = GameThread.getInstance();
        this.singlePlayerGameThread = SinglePlayerGameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.batch = new SpriteBatch();
        this.singlePlayerController = new SinglePlayerController();

        this.playButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth() * widthScale) / 2, (MyAvalancheRushGame.INSTANCE.getScreenHeight() - WOOD_BUTTON.getHeight() * heightScale) / 2, WOOD_BUTTON.getWidth() * widthScale, WOOD_BUTTON.getHeight() * heightScale);
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth() * widthScale, HOME_BUTTON.getHeight() * heightScale);

        this.fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.fontTitle.getData().setScale(1.2f * heightScale);

        this.fontText = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.fontText.getData().setScale(0.9f * heightScale);

        /*
        this.playButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth()) / 2, (MyAvalancheRushGame.INSTANCE.getScreenHeight() - WOOD_BUTTON.getHeight()) / 2, WOOD_BUTTON.getWidth(), WOOD_BUTTON.getHeight());
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth(), HOME_BUTTON.getHeight());

        this.fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.fontTitle.getData().setScale(1f);

        this.fontText = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.fontText.getData().setScale(0.9f);

         */
    }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Single Player");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        batch.draw(WOOD_BUTTON, playButton.x, playButton.y, playButton.getWidth(), playButton.getHeight());

        GlyphLayout singlePlayerLayout = new GlyphLayout(fontText, "Play");
        float singlePlayerTextX = playButton.x + (playButton.getWidth() - singlePlayerLayout.width) / 2;
        float singlePlayerTextY = playButton.y + (playButton.getHeight() + singlePlayerLayout.height) / 2;
        fontText.draw(batch, singlePlayerLayout, singlePlayerTextX, singlePlayerTextY);

        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y, homeButton.getWidth(), homeButton.getHeight());

        batch.end();
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                orthographicCamera.unproject(touchPos);

                if (playButton.contains(touchPos.x, touchPos.y)) {
                    singlePlayerGameThread.obstacles = new Queue<>();
                    singlePlayerGameThread.powerUps = new Queue<>();
                    singlePlayerGameThread.gameScore = 0;
                    singlePlayerController.notify(EventType.GAME_SINGLE_PLAYER_CLICK);
                    return true;

                } else if (homeButton.contains(touchPos.x, touchPos.y)) {
                   singlePlayerController.notify(EventType.HOME_BUTTON_CLICK);
                    return true;
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







