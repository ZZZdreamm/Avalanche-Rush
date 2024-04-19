package com.avalancherush.game.Views;

import static com.avalancherush.game.Globals.Fonts.BIG_BLACK_FONT;
import static com.avalancherush.game.Globals.GlobalVariables.heightScale;
import static com.avalancherush.game.Globals.GlobalVariables.widthScale;
import static com.avalancherush.game.Globals.Textures.BACKGROUND;
import static com.avalancherush.game.Globals.Textures.HOME_BUTTON;
import static com.avalancherush.game.Globals.Textures.PLAY_BUTTON;
import static com.avalancherush.game.Globals.Textures.WOOD_BUTTON;

import com.avalancherush.game.Controllers.JoinController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.FirebaseInterface;
import com.avalancherush.game.Interfaces.BasicView;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Server;
import com.avalancherush.game.Singletons.MultiPlayerGameThread;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class JoinView extends BasicView implements Input.TextInputListener {
    private JoinController joinController;
    private Rectangle homeButton, playButton;
    private BitmapFont fontTitle;
    private String code = "";
    private float CodeX;
    private float woodBeamY;
    private BitmapFont fontText;
    private MultiPlayerGameThread instance;
    private FirebaseInterface database;
    private Server server;

    public JoinView() {
        this.joinController = new JoinController();
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth() * widthScale, HOME_BUTTON.getHeight() * heightScale);
        float woodBeamWidth = (150 + 64) * widthScale;
        float totalWidth = woodBeamWidth + PLAY_BUTTON.getWidth() * widthScale;
        float woodBeamX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - totalWidth) / 2;
        float buttonPlayX = woodBeamX + woodBeamWidth;
        this.CodeX = ((float)MyAvalancheRushGame.INSTANCE.getScreenWidth() - 150) / 2;
        this.woodBeamY = (float) MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2;
        this.playButton = new Rectangle(buttonPlayX, woodBeamY, PLAY_BUTTON.getWidth() * widthScale, PLAY_BUTTON.getHeight() * heightScale);

        this.fontTitle = BIG_BLACK_FONT;
        this.fontTitle.getData().setScale(3f * heightScale);
        this.fontText = BIG_BLACK_FONT;
        this.fontText.getData().setScale(1 * heightScale);
        instance = MultiPlayerGameThread.getInstance();
        this.database = gameThread.getDatabase();
        this.server = new Server(code);

        Gdx.input.getTextInput(this,"Enter Game Code", "", "23");
        Gdx.app.log("Text", code);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        float woodBeamWidth = (150 + 64) * widthScale;
        float totalWidth = woodBeamWidth + PLAY_BUTTON.getWidth() * widthScale;
        float woodBeamX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - totalWidth) / 2;

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Join");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height + 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        batch.draw(WOOD_BUTTON, woodBeamX, woodBeamY, woodBeamWidth, 74 * heightScale);

        GlyphLayout insertCodeLayout = new GlyphLayout(fontText, "Insert code: " + code);
        float insertCodeX = woodBeamX + (woodBeamWidth - insertCodeLayout.width) / 2;
        float insertCodeY = woodBeamY + (74 * heightScale + insertCodeLayout.height) / 2;
        fontText.draw(batch, insertCodeLayout, insertCodeX, insertCodeY);
        //fontText.draw(batch, "Insert code: " + code, CodeX - 30 * widthScale, woodBeamY + 50 * heightScale);
        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y, homeButton.width, homeButton.height);
        batch.draw(PLAY_BUTTON, playButton.x, playButton.y, playButton.width, 74 * heightScale);

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
                    joinController.notify(EventType.HOME_BUTTON_CLICK);
                    return true;
                }
                else if (playButton.contains(touchPos.x, touchPos.y)) {
                    server.id = code;
                    server.CurrentPlayer = "PlayerB";
                    database.serverChangeListener(server);
                    database.setValueToServerDataBase(server.id, "playerB", gameThread.getJsonIntance().getName());
                    instance.setServer(server);
                    joinController.notify(EventType.LOBBY_BUTTON_CLICK);

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
        WOOD_BUTTON.dispose();
        PLAY_BUTTON.dispose();
        fontTitle.dispose();
        fontText.dispose();
    }

    @Override
    public void input(String text) {
        this.code = text;
    }

    @Override
    public void canceled() {

    }
}