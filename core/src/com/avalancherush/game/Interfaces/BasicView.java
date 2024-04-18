package com.avalancherush.game.Interfaces;

import com.avalancherush.game.Singletons.GameThread;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class BasicView extends ScreenAdapter {
    protected GameThread gameThread;
    protected OrthographicCamera orthographicCamera;
    protected SpriteBatch batch;
    protected BasicView(){
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.batch = new SpriteBatch();
    }
}
