package com.avalancherush.game;

import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Views.MenuView;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyAvalancheRushGame extends Game {

	public static MyAvalancheRushGame INSTANCE;
	private OrthographicCamera orthographicCamera;
	private int screenWidth, screenHeight;
	private GameThread instance;
	private Music music;
	private FirebaseInterface database;
	public MyAvalancheRushGame(FirebaseInterface database) {
		INSTANCE = this;
		this.database = database;
	}

	@Override
	public void create() {
		this.screenWidth = Gdx.graphics.getWidth();
		this.screenHeight = Gdx.graphics.getHeight();
		this.orthographicCamera = new OrthographicCamera();
		this.orthographicCamera.setToOrtho(false, screenWidth, screenHeight);
		this.music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();
		instance = GameThread.getInstance();
		instance.setCamera(orthographicCamera);
		instance.setDatabase(this.database);
		setScreen(new MenuView());
	}

	public int getScreenWidth() {
		return screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public Music getMusic() {
		return music;
	}
	@Override
	public void dispose() {
		music.dispose();
	}
}
