package ir.ac.kntu.objects;

import ir.ac.kntu.ContentManager;
import javafx.scene.image.Image;

public class Rock extends Observable {

    public Rock(int xInMap, int yInMap){
        setImage(new Image("/assets/rock.png"));
        setFitHeight(21);
        setFitWidth(24);

        setXInMap(xInMap);
        setYInMap(yInMap);
    }

    public void fallDown() {
        setYInMap(getYInMap()+1);
        ContentManager.setTranslateY(this, getYInMap());
    }
}
