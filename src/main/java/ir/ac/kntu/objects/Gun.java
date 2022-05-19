package ir.ac.kntu.objects;

import ir.ac.kntu.ContentManager;
import javafx.scene.image.Image;

public class Gun extends Observable{
    public Gun(int xInMap, int yInMap){
        setImage(new Image("/assets/gun.png"));
        setFitHeight(24);
        setFitWidth(24);

        setXInMap(xInMap);
        setYInMap(yInMap);

        new Thread(() -> {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setVisible(false);
        }).start();
    }

    @Override
    public void touch() {
        ContentManager.getPlayer().setShootToBlock(5);
        setVisible(false);
    }
}
