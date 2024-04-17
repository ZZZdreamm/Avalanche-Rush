package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.Fonts.BIG_BLACK_FONT;
import static com.avalancherush.game.Configuration.GlobalVariables.heightScale;
import static com.avalancherush.game.Configuration.GlobalVariables.widthScale;
import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;

import com.avalancherush.game.Controllers.MultiPlayerController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.FirebaseInterface;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Server;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.MultiPlayerGameThread;
import com.badlogic.gdx.Game;
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

public class MultiPlayerView extends ScreenAdapter {

    private GameThread gameThread;
    private MultiPlayerController multiPlayerController;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Rectangle joinButton;
    private Rectangle createButton;
    private Rectangle homeButton;
    private BitmapFont fontText;
    private BitmapFont fontTitle;

    private MultiPlayerGameThread instance;

    Server server;


    public MultiPlayerView() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.multiPlayerController = new MultiPlayerController();
        this.batch = new SpriteBatch();
        this.instance = MultiPlayerGameThread.getInstance();

        this.joinButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth() * widthScale) / 2, (MyAvalancheRushGame.INSTANCE.getScreenHeight() - WOOD_BUTTON.getHeight() * heightScale) / 2 + 50, WOOD_BUTTON.getWidth() * widthScale, WOOD_BUTTON.getHeight() * heightScale);
        this.createButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth() * widthScale) / 2, joinButton.y - WOOD_BUTTON.getHeight() * heightScale - 20, WOOD_BUTTON.getWidth() * widthScale, WOOD_BUTTON.getHeight() * heightScale);
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth() * widthScale, HOME_BUTTON.getHeight() * heightScale);

        this.fontTitle = BIG_BLACK_FONT;
        this.fontTitle.getData().setScale(1.2f * heightScale);

        this.fontText = BIG_BLACK_FONT;
        this.fontText.getData().setScale(1.2f * heightScale);

        /*
        this.joinButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth()) / 2, (MyAvalancheRushGame.INSTANCE.getScreenHeight() - WOOD_BUTTON.getHeight()) / 2 + 50, WOOD_BUTTON.getWidth(), WOOD_BUTTON.getHeight());
        this.createButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth()) / 2, joinButton.y - WOOD_BUTTON.getHeight() - 20, WOOD_BUTTON.getWidth(), WOOD_BUTTON.getHeight());
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth(), HOME_BUTTON.getHeight());

        fontText = new BitmapFont(Gdx.files.internal("font2.fnt"));
        fontText.getData().setScale(0.9f);

        fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        fontTitle.getData().setScale(1f);
         */
    }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        batch.draw(WOOD_BUTTON, joinButton.x, joinButton.y, joinButton.width, joinButton.height);
        batch.draw(WOOD_BUTTON, createButton.x, createButton.y, createButton.width, createButton.height);

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Multiplayer");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        GlyphLayout joinLayout = new GlyphLayout(fontText, "join");
        float joinTextX = joinButton.x + (WOOD_BUTTON.getWidth() * widthScale - joinLayout.width) / 2;
        float joinTextY = joinButton.y + (WOOD_BUTTON.getHeight() * heightScale + joinLayout.height) / 2;
        fontText.draw(batch, "join", joinTextX, joinTextY);

        GlyphLayout createLayout = new GlyphLayout(fontText, "create");
        float createTextX = createButton.x + (WOOD_BUTTON.getWidth() * widthScale - createLayout.width) / 2;
        float createTextY = createButton.y + (WOOD_BUTTON.getHeight() * heightScale + createLayout.height) / 2;
        fontText.draw(batch, "create", createTextX, createTextY);

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

                if (joinButton.contains(touchPos.x, touchPos.y)) {
                    multiPlayerController.notify(EventType.JOIN_BUTTON_CLICK);
                    return true;
                } else if (createButton.contains(touchPos.x, touchPos.y)) {
                    FirebaseInterface database = gameThread.getDatabase();
                    database.idChangeListener("id");
                    String id = instance.getGameid();
                    int idnum = Integer.parseInt(id);

                    idnum +=1;
                    server = new Server(id);
                    server.CurrentPlayer = "PlayerA";
                    instance.setServer(server);

                    database.setValueToDataBase("id", String.valueOf(idnum));
                    multiPlayerController.notify(EventType.LOBBY_BUTTON_CLICK);
                    return true;
                } else if (homeButton.contains(touchPos.x, touchPos.y)) {
                    multiPlayerController.notify(EventType.HOME_BUTTON_CLICK);
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
        HOME_BUTTON.dispose();
        BACKGROUND.dispose();
        fontText.dispose();
        fontTitle.dispose();
    }
}

