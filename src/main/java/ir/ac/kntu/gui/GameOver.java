package ir.ac.kntu.gui;

import ir.ac.kntu.ContentManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;

public class GameOver extends Pane {

    public GameOver() {
        Rectangle rect = new Rectangle(800, 600);
        rect.setEffect(new BoxBlur(60, 60, 3));
        getChildren().add(rect);

        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setFont(Font.loadFont("file:src/main/resources/fonts/AtlantisInternational.ttf", 100));
        gameOverLabel.setTextFill(Color.WHITE);
        gameOverLabel.setLayoutX(200);
        gameOverLabel.setLayoutY(150);
        getChildren().add(gameOverLabel);

        initField();
    }

    private void initField() {
        Text text = new Text("Name: ");
        text.setFont(Font.loadFont("file:src/main/resources/fonts/AtlantisInternational.ttf", 20));
        text.setFill(Color.WHITE);
        TextField textField = new TextField();
        textField.setLayoutX(300);
        textField.setLayoutY(400);
        text.setLayoutX(250);
        text.setLayoutY(420);

        Button saveButton = new Button("SAVE");
        saveButton.setFont(Font.loadFont("file:src/main/resources/fonts/AtlantisInternational.ttf", 20));
        saveButton.setLayoutX(330);
        saveButton.setLayoutY(470);
        saveButton.setOnAction(actionEvent -> {
            try (FileWriter writer = new FileWriter("src/main/resources/assets/scores/scores.txt", true)){
                BufferedWriter bw = new BufferedWriter(writer);
                PrintWriter out = new PrintWriter(bw);
                out.println(textField.getText() + " "+ ContentManager.getPlayer().getPoints());
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ContentManager.getMenu().restart();
        });

        getChildren().addAll(text, textField, saveButton);
    }
}
