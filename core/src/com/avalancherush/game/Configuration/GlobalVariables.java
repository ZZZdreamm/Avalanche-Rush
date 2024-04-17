package com.avalancherush.game.Configuration;

import com.badlogic.gdx.Gdx;

public class GlobalVariables {
    public static float SINGLE_PLAYER_HEIGHT = 110;
    public static float SINGLE_PLAYER_WIDTH = 70;
    public static float OBSTACLE_HEIGHT = 100;
    public static float OBSTACLE_TREE_WIDTH = 70;
    public static float OBSTACLE_ROCK_WIDTH = 60;
    public static int POWER_UP_HELMET_TIME = 20;
    public static int POWER_UP_SNOWBOARD_TIME = 60;
    public static float POWER_UP_HEIGHT = 40;
    public static float POWER_UP_WIDTH = 40;
    public static float[] LANES = new float[3];
    public static final float BASE_WIDTH = 480;
    public static final float BASE_HEIGHT = 820;
    public static final float BASIC_GAME_SPEED = 200;

    public static float widthScale;
    public static float heightScale;

    public static void calculateScales() {
        widthScale = Gdx.graphics.getWidth() / BASE_WIDTH;
        heightScale = Gdx.graphics.getHeight() / BASE_HEIGHT;
    }

}
