package com.avalancherush.game.Interfaces;


import com.badlogic.gdx.math.Rectangle;

public interface Collidable extends Drawable {
    boolean collides();
    void update();
}
