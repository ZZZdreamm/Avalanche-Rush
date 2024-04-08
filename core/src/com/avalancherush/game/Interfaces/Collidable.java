package com.avalancherush.game.Interfaces;


import com.badlogic.gdx.math.Rectangle;

public abstract class Collidable implements Drawable {
    private Rectangle rectangle;
    boolean collides(Rectangle rectangle){
        if(this.rectangle.overlaps(rectangle)){
            return true;
        }
        return false;
    };


}
