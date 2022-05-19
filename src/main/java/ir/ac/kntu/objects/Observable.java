package ir.ac.kntu.objects;

import ir.ac.kntu.ContentManager;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Observable extends ImageView {
    public enum Direction {UP, DOWN, RIGHT, LEFT}

    private int xInMap;

    private int yInMap;

    private int point;

    private int shootToBlock = 3;

    private double velocity = 5;

    private Direction direction = Direction.RIGHT;

    public void setXInMap(int xInMap){
        this.xInMap = xInMap;
    }

    public void setYInMap(int yInMap){
        this.yInMap = yInMap;
    }

    public void setEventHandler(Scene scene){
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case UP:
                    moveInMap(0, -1);
                    direction = Direction.UP;
                    break;
                case DOWN:
                    moveInMap(0, 1);
                    direction = Direction.DOWN;
                    break;
                case RIGHT:
                    moveInMap(1, 0);
                    direction = Direction.RIGHT;
                    break;
                case LEFT:
                    moveInMap(-1, 0);
                    direction = Direction.LEFT;
                    break;
                default:
                    break;
            }
            ContentManager.updateScoreboard();

            if (keyEvent.getCode().equals(KeyCode.UP) || keyEvent.getCode().equals(KeyCode.DOWN) ||
                    keyEvent.getCode().equals(KeyCode.RIGHT) || keyEvent.getCode().equals(KeyCode.LEFT)){
                move(velocity);
                ContentManager.getGame().checkRocks();
                ContentManager.ifTouchRandom();
                setAnimation();
            } else if (keyEvent.getCode().equals(KeyCode.SPACE)){
                shoot(shootToBlock);
            }

            if (keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.S)){
                System.out.println("data saved");
                ContentManager.saveDataToFile();
            }
        });
    }

    private void shoot(int block) {
        Rectangle bullet = new Rectangle(5, 2, Color.YELLOW);
        bullet.setTranslateX(getTranslateX());
        bullet.setTranslateY(getTranslateY());
        ContentManager.getMap().getChildren().add(bullet);

        ContentManager.disableEventHandler();

        for (int i = 1;i <= block;i++){
            switch (direction) {
                case RIGHT:
                    bullet.setTranslateX(bullet.getTranslateX() + (i * 25));
                    if (hit()) {
                        break;
                    }
                    break;
                case LEFT:
                    bullet.setTranslateX(bullet.getTranslateX() - (i * 25));
                    if (hit()) {
                        break;
                    }
                    break;
                case DOWN:
                    bullet.setRotate(90);
                    bullet.setTranslateY(bullet.getTranslateY() + (i * 25));
                    if (hit()) {
                        break;
                    }
                    break;
                case UP:
                    bullet.setRotate(90);
                    bullet.setTranslateY(bullet.getTranslateY() - (i * 25));
                    if (hit()) {
                        break;
                    }
                default:
                    break;
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ContentManager.enableEventHandler();
    }

    private boolean hit() {
        for (Balloon balloon : ContentManager.getBalloons()){
            if ((getXInMap() >= balloon.getXInMap() && getXInMap() <= balloon.getXInMap()+3) &&
                    (getYInMap() >= balloon.getYInMap() && getYInMap() <= balloon.getYInMap()+3)){
                balloon.hitt();
                System.out.println("hit");
                return true;
            }
        }
        
        return false;
    }


    private void moveInMap(int moveX, int moveY){
        try {
            ContentManager.setMapItem(xInMap, yInMap, 0);
        } catch (Exception ignored){

        }

        xInMap = (int) (getTranslateX()/25) + moveX;
        yInMap = (int) (getTranslateY()/25 - 3) + moveY;

        try {
            if (ContentManager.getMapItem(xInMap, yInMap) == 1){
                ContentManager.setMapItem(xInMap, yInMap, 2);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ya went too far");
        }
    }

    public void move(double velocity){
        new Thread(() -> {
            switch (direction) {
                case UP:
                    setTranslateY(getTranslateY()-velocity);
                    break;
                case DOWN:
                    setTranslateY(getTranslateY()+velocity);
                    break;
                case LEFT:
                    setTranslateX(getTranslateX()-velocity);
                    break;
                case RIGHT:
                    setTranslateX(getTranslateX()+velocity);
                    break;
                default:
                    break;
            }
            ContentManager.getBackground().dig(getTranslateX(), getTranslateY(), getTranslateX()+25, getTranslateY()+25);
        }).start();
    }

    public void setAnimation(){

    }

    public Direction getDirection() {
        return direction;
    }

    public int getXInMap() {
        return xInMap;
    }

    public int getYInMap() {
        return yInMap;
    }

    public void setLocation(int x, int y){
        try {
            ContentManager.setMapItem(x, y, 3);

            ContentManager.setTranslateX(this, x);
            ContentManager.setTranslateY(this, y);

            ContentManager.getBackground().dig(getTranslateX(), getTranslateY(), getTranslateX()+25, getTranslateY()+25);
            ContentManager.setMapItem(xInMap, yInMap, 0);

            xInMap = x;
            yInMap = y;
        } catch (ArrayIndexOutOfBoundsException e){
            ContentManager.setMapItem(xInMap, yInMap, 0);
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getPoints() {
        return point;
    }

    public void touch(){

    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setShootToBlock(int shootToBlock) {
        this.shootToBlock = shootToBlock;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
