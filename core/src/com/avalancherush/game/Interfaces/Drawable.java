package com.avalancherush.game.Interfaces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Drawable {
    protected Rectangle rectangle;
    protected Texture texture;
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
