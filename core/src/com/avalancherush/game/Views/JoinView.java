package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.PLAY_BUTTON;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;

import com.avalancherush.game.Controllers.JoinController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;



public class JoinView extends ScreenAdapter {

    private GameThread gameThread;
    private JoinController joinController;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Rectangle homeButton;
    private BitmapFont fontTitle;
    private String code = "";
    private float CodeX;
    private float woodBeamY;
    private BitmapFont fontText;
    private static final int CODE_LENGTH = 5;

    public JoinView() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.joinController = new JoinController();
        this.batch = new SpriteBatch();
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth(), HOME_BUTTON.getHeight());
        this.CodeX = ((float)MyAvalancheRushGame.INSTANCE.getScreenWidth() - 150) / 2;
        this.woodBeamY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - 250;

        this.fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        this.fontTitle.getData().setScale(1f);
        this.fontText = new BitmapFont();
        this.fontText.setColor(Color.WHITE);
        this.fontText.getData().setScale(1);
    }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        float woodBeamWidth = 150 + 64;
        float totalWidth = woodBeamWidth + PLAY_BUTTON.getWidth();
        float woodBeamX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - totalWidth) / 2;
        float buttonPlayX = woodBeamX + woodBeamWidth;

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Join");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        batch.draw(WOOD_BUTTON, woodBeamX, woodBeamY, woodBeamWidth, 74);
        batch.draw(PLAY_BUTTON, buttonPlayX, woodBeamY, PLAY_BUTTON.getWidth(), 74);
        fontText.draw(batch, "INSERT CODE: " + code, CodeX - 30, woodBeamY + 50);
        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y);

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

                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                if (Character.isDigit(character) && code.length() < CODE_LENGTH) {
                    code += character;
                    return true;
                }
                else if (character == '\b' && code.length() > 0) {
                    code = code.substring(0, code.length() - 1);
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
}