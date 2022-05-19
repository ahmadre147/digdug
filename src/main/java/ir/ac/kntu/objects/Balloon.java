package ir.ac.kntu.objects;

import ir.ac.kntu.ContentManager;
import javafx.scene.image.Image;

import java.util.Arrays;

public class Balloon extends Observable {
    private int health = 3;

    public Balloon(){

    }

    public Balloon(int xInMap, int yInMap){
        setFitWidth(24);
        setFitHeight(24);

        setXInMap(xInMap);
        setYInMap(yInMap);

        setAnimation();
    }

    @Override
    public void setAnimation() {
        switch (getDirection()){
            case RIGHT:
            case LEFT:
                setImage();
                setRotate(0);
                break;
            case DOWN:
                setImage();
                setRotate(90);
                break;
            case UP:
                setImage();
                setRotate(-90);
                break;
            default:
                break;
        }
    }

    private void closestMove(int x, int y){
        int[][] movers = sortMovers(x, y);

        for (int i = 0;i < 4;i++){
            if (ContentManager.getMapItem(getXInMap()+movers[i][0], getYInMap()+movers[i][1]) != 1){
                checkDirection(movers[i]);
                setAnimation();

                setLocation(getXInMap()+movers[i][0], getYInMap()+movers[i][1]);
                return;
            }
        }
    }

    private void checkDirection(int[] mover) {
        int[][] movers = new int[4][2];
        movers[0] = new int[]{-1, 0};
        movers[1] = new int[]{1, 0};
        movers[2] = new int[]{0, 1};
        movers[3] = new int[]{0, -1};

        if (Arrays.equals(mover, movers[0])){
            setDirection(Direction.LEFT);
        } else if (Arrays.equals(mover, movers[1])){
            setDirection(Direction.RIGHT);
        } else if (Arrays.equals(mover, movers[2])){
            setDirection(Direction.DOWN);
        } else if (Arrays.equals(mover, movers[3])){
            setDirection(Direction.UP);
        }
    }

    private int[][] sortMovers(int x, int y){
        int[][] movers = new int[4][2];
        movers[0] = new int[]{-1, 0};
        movers[1] = new int[]{1, 0};
        movers[2] = new int[]{0, 1};
        movers[3] = new int[]{0, -1};

        int[] swap;
        for (int i = 0;i < 3;i++){
            for (int j = 0;j < 3-i;j++){
                if (Math.abs(getXInMap() + movers[j][0] - x) >= Math.abs(getXInMap() + movers[j+1][0] - x) && Math.abs(getYInMap() + movers[j][1] - y) >= Math.abs(getYInMap() + movers[j+1][1] - y)) {
                    swap = movers[j];
                    movers[j] = movers[j+1];
                    movers[j+1] = swap;
                }
            }
        }

        return movers;
    }

    public void chase(){
        closestMove(ContentManager.getPlayer().getXInMap(), ContentManager.getPlayer().getYInMap());

        if (getXInMap() == ContentManager.getPlayer().getXInMap() && getYInMap() == ContentManager.getPlayer().getYInMap()){
            ContentManager.getPlayer().reduceHeath();
            ContentManager.getHealthPlayer().get(0).setVisible(false);
            ContentManager.getHealthPlayer().remove(0);
            if (ContentManager.getPlayer().isAlive()){
                ContentManager.getPlayer().resurrect();
            } else {
                ContentManager.getGame().endGame();
            }
        }

        try {
            Thread.sleep(90);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void hitt() {
        health--;

        if (health == 0){
            setVisible(false);
            if (this instanceof FireBalloon){
                ContentManager.getPlayer().addPoint(15);
            } else {
                ContentManager.getPlayer().addPoint(10);
            }
        }

        setAnimation();
    }

    public int getHealth() {
        return health;
    }

    public void reduceHealth() {
        health--;
    }

    public void setImage(){
        switch (health){
            case 1:
                setImage(new Image("/assets/balloon/large_balloon.png"));
                break;
            case 2:
                setImage(new Image("/assets/balloon/larger_sballoon.png"));
                break;
            case 3:
                if (getDirection().equals(Direction.LEFT)){
                    setImage(new Image("/assets/balloon/balloon_little_left.png"));
                } else {
                    setImage(new Image("/assets/balloon/balloon_little_right.png"));
                }
                break;
            default:
                break;
        }
    }
}
