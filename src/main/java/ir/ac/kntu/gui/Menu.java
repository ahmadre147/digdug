package ir.ac.kntu.gui;

import ir.ac.kntu.ContentManager;
import ir.ac.kntu.Game;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class Menu extends Pane {
    private Pane root;

    private final Scene scene;

    public Menu(Scene scene){
        setStyle("-fx-background-color: #FF8C00");
        this.scene = scene;
        setControls();

        scene.setRoot(this);
    }

    private void setControls() {
        Button start = new Button();
        Button highScores = new Button();
        Button exit = new Button();

        start.setText("NEW GAME");
        start.setFont(Font.loadFont("file:src/main/resources/fonts/AtlantisInternational.ttf", 30));
        start.setTranslateX(250);
        start.setTranslateY(200);
        start.setOnAction(keyEvent -> {
            root = new Pane();

            root.setStyle("-fx-border-width: 0 0 5 0;");
            root.setStyle("-fx-background-color: #000000");

            GameMap map = new GameMap();
            root.getChildren().add(map);

            scene.setRoot(root);
            ContentManager.setEventHandlers(scene);

            ContentManager.setGame(new Game());
        });

        highScores.setText("HIGH SCORES");
        highScores.setFont(Font.loadFont("file:src/main/resources/fonts/AtlantisInternational.ttf", 30));
        highScores.setTranslateX(250);
        highScores.setTranslateY(270);
        highScores.setOnAction(keyEvent -> {
            scene.setRoot(new HighScore());
        });

        exit.setText("EXIT");
        exit.setFont(Font.loadFont("file:src/main/resources/fonts/AtlantisInternational.ttf", 30));
        exit.setTranslateX(250);
        exit.setTranslateY(340);
        exit.setOnAction(keyEvent -> System.exit(200));

        getChildren().addAll(start, highScores, exit);
    }

    public void restart(){
        scene.setRoot(this);
    }

    public void gameOver() {
        GameOver gameOverPane = new GameOver();
        scene.setRoot(gameOverPane);
    }
}
