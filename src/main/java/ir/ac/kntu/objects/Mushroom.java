package ir.ac.kntu.objects;

import javafx.scene.image.Image;

public class Mushroom extends Observable{
    public Mushroom(int xInMap, int yInMap){
        setImage(new Image("/assets/mushroom.png"));
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
        setVelocity(getVelocity()*1.5);
        setVisible(false);
    }
}
