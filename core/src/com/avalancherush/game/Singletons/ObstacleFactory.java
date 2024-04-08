package com.avalancherush.game.Singletons;

import com.avalancherush.game.Enums.ObstacleType;
import com.avalancherush.game.Models.Obstacle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ObstacleFactory {
    private static ObstacleFactory instance;
    public ObstacleFactory getInstance(){
        if(instance == null){
            instance = new ObstacleFactory();
        }
        return instance;
    }

    public Obstacle createObstacle(ObstacleType obstacleType, int track){
        Obstacle obstacle = new Obstacle();
        try{
            obstacle.setTrack(track);
        }catch (Exception e){
            System.out.println(e);
        }
        if(obstacleType == ObstacleType.ROCK){
            Image image = new Image();
            obstacle.setImage(image);
            obstacle.setType(ObstacleType.ROCK);
            obstacle.setJumpable(true);
        }else if (obstacleType == ObstacleType.TREE){
            Image image = new Image();
            obstacle.setImage(image);
            obstacle.setType(ObstacleType.TREE);
            obstacle.setJumpable(false);
        }
        return obstacle;
    }
}
