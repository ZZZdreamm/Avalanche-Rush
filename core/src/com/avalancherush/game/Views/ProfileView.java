package com.avalancherush.game.Views;

<<<<<<< HEAD
import com.avalancherush.game.Controllers.ProfileController;
import com.avalancherush.game.Enums.EventType;
=======
import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.MODIFY_BUTTON;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;

>>>>>>> af723c9 (Made All Texture as GLOBAL VARIABLES)
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Inpgit utAdapter;
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



public class ProfileView extends ScreenAdapter {

    private GameThread gameThread;
    private ProfileController profileController;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Rectangle homeButton;
    private BitmapFont font;
    private String username;
    private boolean editingUsername = false;
    private StringBuilder usernameBuilder;
    private Rectangle changeUsernameButton;
    private BitmapFont fontTitle;

    public ProfileView() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.profileController = new ProfileController();
        this.batch = new SpriteBatch();
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth(), HOME_BUTTON.getHeight());
        this.username = "Default Username";
        this.usernameBuilder = new StringBuilder();
        this.font = new BitmapFont();
        this.font.setColor(Color.WHITE);
        this.font.getData().setScale(1);
        this.changeUsernameButton = new Rectangle(50, 150, 100, 50);

        fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        fontTitle.getData().setScale(1);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());



        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "Username: " + username);


        float usernameX = ((float) MyAvalancheRushGame.INSTANCE.getScreenWidth() - 150) / 2;

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Profile");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);


        float woodBeamY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - 200;
        batch.draw(WOOD_BUTTON, usernameX-32, woodBeamY, 150 + 64, 74);

        float changeButtonX = usernameX + 150 + 50;
        float changeButtonY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - 200;
        changeUsernameButton.setPosition(changeButtonX, changeButtonY);


        batch.draw(MODIFY_BUTTON, changeUsernameButton.x, changeUsernameButton.y);

        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y);


        if (editingUsername) {
            font.draw(batch, "NEW USERNAME:\n" + usernameBuilder.toString(), usernameX, MyAvalancheRushGame.INSTANCE.getScreenHeight() - 150);
        } else {
            font.draw(batch, "USERNAME:\n" + username, usernameX, MyAvalancheRushGame.INSTANCE.getScreenHeight() - 150);
        }

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
                    editingUsername = true;
                    return true;
                }

                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                if (editingUsername) {
                    if (character == '\n') {
                        username = usernameBuilder.toString();
                        usernameBuilder.setLength(0);
                        editingUsername = false;
                        return true;
                    } else if (character == '\b' && usernameBuilder.length() > 0) {
                        usernameBuilder.deleteCharAt(usernameBuilder.length() - 1);
                    } else if (character != '\b' && usernameBuilder.length() < 16) {
                        usernameBuilder.append(character);
                    }
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

    @Override
    public void hide() {
        if (editingUsername) {
            username = usernameBuilder.toString();
            usernameBuilder.setLength(0);
            editingUsername = false;
        }
    }
}