package com.avalancherush.game.Interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Drawable {
    protected Rectangle rectangle;
    public void draw(SpriteBatch spriteBatch, TextureRegion textureRegion) {
        spriteBatch.draw(textureRegion, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
//    void update(){
//
//    };
}
