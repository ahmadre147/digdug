package ir.ac.kntu.objects;

import javafx.scene.image.Image;

public class FireBalloon extends Balloon {
    private int indicator = 1;

    public FireBalloon(int xInMap, int yInMap){
        setFitWidth(24);
        setFitHeight(24);

        setXInMap(xInMap);
        setYInMap(yInMap);

        setAnimation();

        fire();
    }

    private void fire() {
        switch (getDirection()) {
            case RIGHT:
                setImage(new Image("/assets/fireballoon/fb_right_flaming.png"));
                setRotate(0);
            case LEFT:
                setImage(new Image("/assets/fireballoon/fb_left_flaming.png"));
                setRotate(0);
                break;
            case DOWN:
                setImage(new Image("/assets/fireballoon/fb_right_flaming.png"));
                setRotate(90);
                break;
            case UP:
                setImage(new Image("/assets/fireballoon/fb_right_flaming.png"));
                setRotate(-90);
                break;
            default:
                break;
        }
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

    @Override
    public void setImage() {
        if (indicator % 10 == 0){
            fire();
            indicator = 1;
            return;
        }
        switch (getHealth()){
            case 1:
                setImage(new Image("/assets/fireballoon/fb_left_g.png"));
                break;
            case 2:
                if (getDirection().equals(Direction.LEFT)){
                    setImage(new Image("/assets/fireballoon/fb_left_g.png"));
                } else {
                    setImage(new Image("/assets/fireballoon/fb_right_g.png"));
                }
                break;
            case 3:
                if (getDirection().equals(Direction.LEFT)){
                    setImage(new Image("/assets/fireballoon/fb_left_s.png"));
                } else {
                    setImage(new Image("/assets/fireballoon/fb_right_s.png"));
                }
                break;
            default:
                break;
        }
        indicator++;
    }
}
