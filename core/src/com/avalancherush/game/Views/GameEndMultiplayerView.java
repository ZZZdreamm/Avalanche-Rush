package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.Fonts.BIG_BLACK_FONT;
import static com.avalancherush.game.Configuration.GlobalVariables.heightScale;
import static com.avalancherush.game.Configuration.GlobalVariables.widthScale;
import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.LOST_BUTTON;

import com.avalancherush.game.Controllers.GameEndController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Interfaces.BasicView;
import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Server;
import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.MultiPlayerGameThread;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameEndMultiplayerView extends BasicView {
    private GameEndController gameEndController;
    private Rectangle homeButton;
    private BitmapFont scoreFont;
    private BitmapFont resultFont;
    private MultiPlayerGameThread multiPlayerGameThread;
    private Server server;

    public GameEndMultiplayerView() {
        this.gameEndController = new GameEndController();
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth() * widthScale, HOME_BUTTON.getHeight() * heightScale);
        this.scoreFont = BIG_BLACK_FONT;
        this.scoreFont.getData().setScale(1 * heightScale);
        this.resultFont = BIG_BLACK_FONT;
        this.resultFont.getData().setScale(3f * heightScale);
        multiPlayerGameThread = MultiPlayerGameThread.getInstance();
        server = multiPlayerGameThread.getServer();
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        String resultString = null;
        String personalScore;
        String enemyScore;
        if(server.CurrentPlayer.equalsIgnoreCase("PlayerA")){
            if(server.playerAScore < server.playerBScore){
                resultString = "YOU LOST";
            } else if(server.playerAScore == server.playerBScore) {
                resultString = "TIE";
            } else {
                resultString = "YOU WON";
            }

            personalScore = "YOU " + server.playerAScore;
            enemyScore = "FRIEND " + server.playerBScore;

        } else {
            if(server.playerAScore > server.playerBScore){
                resultString = "YOU LOST";
            } else if(server.playerAScore == server.playerBScore) {
                resultString = "TIE";
            } else {
                resultString = "YOU WON";
            }

            personalScore = "YOU " + server.playerBScore;
            enemyScore = "FRIEND " + server.playerAScore;
        }
        GlyphLayout resultLayout = new GlyphLayout(resultFont, resultString);
        float resultX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - resultLayout.width) / 2;
        float resultY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - resultLayout.height - 50;
        resultFont.draw(batch, resultString, resultX, resultY);

        float lostButtonWidth = Gdx.graphics.getHeight()/5;

        float buttonX = (float)(MyAvalancheRushGame.INSTANCE.getScreenWidth() - LOST_BUTTON.getWidth() * widthScale) / 2;
        float buttonY = (float)(MyAvalancheRushGame.INSTANCE.getScreenHeight() - lostButtonWidth) / 2;

        batch.draw(LOST_BUTTON, buttonX, buttonY, LOST_BUTTON.getWidth() * widthScale, lostButtonWidth);

        GlyphLayout personalLayout = new GlyphLayout(scoreFont, personalScore);
        while (personalLayout.width > LOST_BUTTON.getWidth() * widthScale - 50) {
            scoreFont.getData().setScale(scoreFont.getData().scaleX - 0.1f);
            personalLayout = new GlyphLayout(scoreFont, personalScore);
        }
        float personalTextX = buttonX + (LOST_BUTTON.getWidth() * widthScale - personalLayout.width) / 2;
        float personalTextY = (float) MyAvalancheRushGame.INSTANCE.getScreenHeight() /2  + personalLayout.height + 5;
        scoreFont.draw(batch, personalScore, personalTextX, personalTextY);

        GlyphLayout enemyLayout = new GlyphLayout(scoreFont, enemyScore);
        while (enemyLayout.width > (LOST_BUTTON.getWidth() * widthScale) - 50) {
            scoreFont.getData().setScale(scoreFont.getData().scaleX - 0.1f);
            enemyLayout = new GlyphLayout(scoreFont, enemyScore);
        }
        float enemyTextX = buttonX + (LOST_BUTTON.getWidth() * widthScale - enemyLayout.width) / 2;
        float enemyTextY = (float) Gdx.graphics.getHeight() /2 - enemyLayout.height - 5;
        scoreFont.draw(batch, enemyScore, enemyTextX, enemyTextY);

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

                if (homeButton.contains(touchPos.x, touchPos.y)) {
                    gameEndController.notify(EventType.HOME_BUTTON_CLICK);
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
        LOST_BUTTON.dispose();
        scoreFont.dispose();
        resultFont.dispose();
    }
}
