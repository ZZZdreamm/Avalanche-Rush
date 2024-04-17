package com.avalancherush.game;

import com.avalancherush.game.Models.Obstacle;
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
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;


public class MyAvalancheRushGame extends Game {

	public static MyAvalancheRushGame INSTANCE;
	private OrthographicCamera orthographicCamera;
	private int screenWidth, screenHeight;
	private GameThread instance;
	private Music musicMenu;
	private Music musicGame;
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
		this.musicMenu = Gdx.audio.newMusic(Gdx.files.internal("menu.mp3"));
		this.musicMenu.setLooping(true);
		this.musicMenu.setVolume(1);
		this.musicMenu.play();
		this.musicGame = Gdx.audio.newMusic(Gdx.files.internal("game.mp3"));
		this.musicGame.setLooping(true);
		this.musicGame.setVolume(1);
		this.musicGame.pause();
		this.instance = GameThread.getInstance();
		this.instance.setCamera(orthographicCamera);
		instance.setDatabase(this.database);
		System.out.println("Going to Menu View.........................................");
		setScreen(new MenuView());
	}

	public int getScreenWidth() {
		return screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public Music getMusicMenu() {
		return musicMenu;
	}
	public Music getMusicGame() {return musicGame;}

	@Override
	public void dispose() {
		musicMenu.dispose();
	}
}
