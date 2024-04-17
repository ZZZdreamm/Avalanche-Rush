package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.GlobalVariables.heightScale;
import static com.avalancherush.game.Configuration.GlobalVariables.widthScale;
import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.PLAY_BUTTON;
import static com.avalancherush.game.Configuration.Textures.TABLE_LOBBY;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;

import com.avalancherush.game.Controllers.LobbyController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.FirebaseInterface;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Server;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.MultiPlayerGameThread;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class LobbyView extends ScreenAdapter {
    private MultiPlayerGameThread instance;
    private GameThread gameThread;
    private LobbyController lobbyController;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;

    private Rectangle playButton;
    private Rectangle homeButton;
    private BitmapFont fontTitle;
    private FirebaseInterface database;

    private float woodBeamY;
    private BitmapFont fontText;
    private String username;
    public String code;
    private Server server;

    public LobbyView() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.lobbyController = new LobbyController();
        this.batch = new SpriteBatch();

        float buttonX = MyAvalancheRushGame.INSTANCE.getScreenWidth() - HOME_BUTTON.getWidth() * widthScale - 20;
        float buttonY = (MyAvalancheRushGame.INSTANCE.getScreenHeight() - PLAY_BUTTON.getHeight() * heightScale) / 2;
        this.playButton = new Rectangle(buttonX, buttonY, PLAY_BUTTON.getWidth() * widthScale, PLAY_BUTTON.getHeight() * heightScale);
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth() * widthScale, HOME_BUTTON.getHeight() * heightScale);


        woodBeamY = (float) (MyAvalancheRushGame.INSTANCE.getScreenHeight() - 140) / 2;


        username = ProfileView.getUsername();
        if(username == null)
            username = "Default Username";

        Gdx.input.setInputProcessor(new MyInputAdapter());

        fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        fontTitle.getData().setScale(3f * heightScale);

        fontText = new BitmapFont(Gdx.files.internal("font2.fnt"));
        fontText.getData().setScale(1.0f * heightScale);


        this.database = gameThread.getDatabase();
        instance = MultiPlayerGameThread.getInstance();
        this.server = instance.getServer();
        code = (server.id);
        database.serverChangeListener(this.server);
        if(server.CurrentPlayer.equalsIgnoreCase("PlayerA")){
            database.setValueToServerDataBase(server.id, "playerA", gameThread.getJsonIntance().getName());
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();
        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());
        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y, homeButton.width, homeButton.height);
        float woodBeamWidth = (150 + 150) * widthScale;
        float buttonPlayWidth = PLAY_BUTTON.getWidth() * widthScale;
        float totalWidth = woodBeamWidth + buttonPlayWidth;
        float woodBeamX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - totalWidth) / 2;
        batch.draw(TABLE_LOBBY, woodBeamX, ((float) MyAvalancheRushGame.INSTANCE.getScreenHeight() - TABLE_LOBBY.getHeight() * heightScale)/ 2, woodBeamWidth, 140 * heightScale);

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Lobby");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height + 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);
        float CenterX = (float) MyAvalancheRushGame.INSTANCE.getScreenWidth() / 2;
        float CenterY = (float) MyAvalancheRushGame.INSTANCE.getScreenHeight() / 2;

        float playerX = (float) MyAvalancheRushGame.INSTANCE.getScreenWidth()/4;

        GlyphLayout ready = new GlyphLayout(fontText, "Ready");
        GlyphLayout playerALayout = new GlyphLayout(fontText, server.playerA);
        GlyphLayout playerBLayout = new GlyphLayout(fontText, server.playerB);
        fontText.draw(batch, playerALayout, playerX, CenterY + playerALayout.height + 5);
        fontText.draw(batch, playerBLayout, playerX, CenterY - playerBLayout.height - 5);


        if(server.CurrentPlayer.equalsIgnoreCase("PlayerA") && (!server.playerAStatus.equals("True"))){
            batch.draw(PLAY_BUTTON, playButton.x, playButton.y, buttonPlayWidth, PLAY_BUTTON.getHeight() * heightScale);
        }
        else if(server.CurrentPlayer.equalsIgnoreCase("PlayerB") && (!server.playerBStatus.equals("True"))){
            batch.draw(PLAY_BUTTON, playButton.x,playButton.y, buttonPlayWidth, PLAY_BUTTON.getHeight() * heightScale);
        }
        if(server.playerAStatus.equals("True")) {
            fontText.draw(batch, ready,playerX + playerALayout.width + 15, CenterY + playerALayout.height + 5);
        }
        else if((server.playerBStatus.equals("True"))){
            fontText.draw(batch, ready,+ playerX + playerBLayout.width + 15,CenterY - playerBLayout.height - 5);
        }

        GlyphLayout serverId = new GlyphLayout(fontText, ("Lobby ID : "+server.id));
        System.out.println(server.id);
        float serverIdX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - serverId.width) / 2;
        float serverIdY = gameLogoY - (gameLogoLayout.height + serverId.height);
        fontText.draw(batch, serverId, serverIdX, serverIdY);
        batch.end();

        if(server.playerBStatus.equalsIgnoreCase("True") && server.playerAStatus.equalsIgnoreCase("True")){
            System.out.println("Entered");
            lobbyController.notify(EventType.GAME_MULTI_PLAYER);

        }

    }

    private class MyInputAdapter extends InputAdapter {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 touchPos = new Vector3(screenX, screenY, 0);
            orthographicCamera.unproject(touchPos);

            if (playButton.contains(touchPos.x, touchPos.y)) {
                if(server.CurrentPlayer.equalsIgnoreCase("PlayerA")){
                    database.setValueToServerDataBase(server.id, "playerAStatus", "True");
                }
                else{
                    database.setValueToServerDataBase(server.id, "playerBStatus", "True");
                }

            }

            if (homeButton.contains(touchPos.x, touchPos.y)) {
                lobbyController.notify(EventType.HOME_BUTTON_CLICK);
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