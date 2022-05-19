package ir.ac.kntu.objects;

import ir.ac.kntu.ContentManager;
import javafx.scene.image.Image;

public class Player extends Observable {
    private boolean isAlive;

    private int health;

    private final int startingPointX;

    private final int startingPointY;

    public Player(int xInMap, int yInMap) {
        setAnimation();
        setFitWidth(24);
        setFitHeight(24);
        setTranslateX(5);

        setXInMap(xInMap);
        setYInMap(yInMap);

        startingPointX = xInMap;
        startingPointY = yInMap;

        health = 3;
        isAlive = true;
    }

    @Override
    public void setAnimation(){
        switch (getDirection()){
            case RIGHT:
                setImage(new Image("/assets/player/player_still_right.png"));
                setRotate(0);
                break;
            case LEFT:
                setImage(new Image("/assets/player/player_still_left.png"));
                setRotate(0);
                break;
            case DOWN:
                setImage(new Image("/assets/player/player_still_right.png"));
                setRotate(90);
                break;
            case UP:
                setImage(new Image("/assets/player/player_still_right.png"));
                setRotate(-90);
                break;
            default:
                break;
        }
    }


    public int getHealth() {
        return health;
    }

    public void reduceHeath() {
        health--;

        if (health == 0){
            isAlive = false;
        }
    }

    public void addHealth(){
        health++;
        ContentManager.addHealthPlayer();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void resurrect() {
        setXInMap(startingPointX);
        setYInMap(startingPointY);

        ContentManager.setTranslateX(this, startingPointX);
        ContentManager.setTranslateY(this, startingPointY);

        ContentManager.setMapItem(startingPointX, startingPointY, 2);
    }

    public void addPoint(int i) {
        setPoint(getPoints()+i);
    }
}
