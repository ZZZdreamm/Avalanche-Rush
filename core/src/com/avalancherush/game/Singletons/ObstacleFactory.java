package com.avalancherush.game.Singletons;

import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_HEIGHT;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_ROCK_WIDTH;
import static com.avalancherush.game.Configuration.GlobalVariables.OBSTACLE_TREE_WIDTH;

import com.avalancherush.game.Configuration.Textures;
import com.avalancherush.game.Enums.ObstacleType;
import com.avalancherush.game.Models.Obstacle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ObstacleFactory {
    private static ObstacleFactory instance;
    public static ObstacleFactory getInstance(){
        if(instance == null){
            instance = new ObstacleFactory();
        }
        return instance;
    }

    public Obstacle createObstacle(ObstacleType obstacleType, int track, float x, float y){
        Obstacle obstacle = new Obstacle();
        try{
            obstacle.setTrack(track);
        }catch (Exception e){
            System.out.println(e);
        }
        if(obstacleType == ObstacleType.ROCK){
            obstacle.setType(ObstacleType.ROCK);
            obstacle.setJumpable(true);
            Rectangle rectangle = new Rectangle(x, y, OBSTACLE_ROCK_WIDTH, OBSTACLE_HEIGHT);
            obstacle.setRectangle(rectangle);
            obstacle.setTexture(Textures.ROCK);
        }else if (obstacleType == ObstacleType.TREE){
            obstacle.setType(ObstacleType.TREE);
            obstacle.setJumpable(false);
            Rectangle rectangle = new Rectangle(x, y, OBSTACLE_TREE_WIDTH, OBSTACLE_HEIGHT);
            obstacle.setRectangle(rectangle);
            obstacle.setTexture(Textures.TREE);
        }
        return obstacle;
    }
}
