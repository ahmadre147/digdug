package ir.ac.kntu.gui;

import ir.ac.kntu.ContentManager;
import ir.ac.kntu.Timer;
import ir.ac.kntu.objects.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameMap extends Pane {
    private final Block background;

    public GameMap() {
        ContentManager.newMap(this);

        ContentManager.loadMapFromFile();

        setPrefSize(ContentManager.getSize()*25, (ContentManager.getSize()+3)*25);
        background = new Block(ContentManager.getSize()*25, (ContentManager.getSize()+3)*25);
        ContentManager.setBackground(background);

        getChildren().add(new ImageView(background));

        setLayoutX(100);
        setLayoutY(50);

        initiator();
        initRocks();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setScoreboard();
        ContentManager.healthInit();
        showHealth();
        initTimer();
    }

    private void initTimer() {
        Timer timer = new Timer();
        timer.setFont(Font.loadFont("file:src/main/resources/fonts/AtlantisInternational.ttf", 25));
        timer.setFill(Color.WHITE);
        timer.setLayoutX(495);
        timer.setLayoutY(-20);

        getChildren().add(timer);
    }

    public void showHealth() {
        for (ImageView img : ContentManager.getHealthPlayer()){
            if (!getChildren().contains(img)){
                getChildren().add(img);
            }
        }
    }

    private void initiator() {
        for (int i = 0;i < ContentManager.getSize();i++){
            for (int j = 0;j < ContentManager.getSize();j++){
                switch (ContentManager.getMapItem(i, j)){
                    case 1:
                        background.setBlock(25*i, 75+25*j);
                        break;
                    case 2:
                        ContentManager.setPlayer(new Player(i, j));
                        getChildren().add(ContentManager.getPlayer());
                        ContentManager.getPlayer().setTranslateX(25*i);
                        ContentManager.getPlayer().setTranslateY(75+(25*j));
                        break;
                    case 3:
                        Balloon balloon = new Balloon(i, j);
                        ContentManager.addBalloon(balloon);
                        getChildren().add(balloon);
                        balloon.setTranslateX(25*i);
                        balloon.setTranslateY(75+(25*j));
                        break;
                    case 4:
                        FireBalloon fireBalloon = new FireBalloon(i, j);
                        ContentManager.addBalloon(fireBalloon);
                        getChildren().add(fireBalloon);
                        fireBalloon.setTranslateX(25*i);
                        fireBalloon.setTranslateY(75+(25*j));
                        break;
                    default:
                        break;
                }
            }
        }

        background.randomDotize();
    }

    private void initRocks(){
        Rock rock1 = new Rock(11, 9);
        Rock rock2 = new Rock(3, 13);
        Rock rock3 = new Rock(4, 3);

        getChildren().add(rock1);
        ContentManager.setTranslateX(rock1, rock1.getXInMap());
        ContentManager.setTranslateY(rock1, rock1.getYInMap());

        getChildren().add(rock2);
        ContentManager.setTranslateX(rock2, rock2.getXInMap());
        ContentManager.setTranslateY(rock2, rock2.getYInMap());

        getChildren().add(rock3);
        ContentManager.setTranslateX(rock3, rock3.getXInMap());
        ContentManager.setTranslateY(rock3, rock3.getYInMap());

        ContentManager.addRock(rock1);
        ContentManager.addRock(rock2);
        ContentManager.addRock(rock3);
    }

    private void setScoreboard(){
        Text text = new Text("Score:");
        text.setLayoutX(490);
        text.setLayoutY(15);
        text.setFont(Font.loadFont("file:src/main/resources/fonts/AtlantisInternational.ttf", 40));
        text.setFill(Color.ORANGE);
        getChildren().add(text);

        Text scoreboard = new Text(String.valueOf(ContentManager.getPlayer().getPoints()));
        scoreboard.setLayoutX(590);
        scoreboard.setLayoutY(15);
        scoreboard.setFont(Font.loadFont("file:src/main/resources/fonts/AtlantisInternational.ttf", 30));
        scoreboard.setFill(Color.ORANGE);
        getChildren().add(scoreboard);
        ContentManager.setScoreboard(scoreboard);
    }
}
