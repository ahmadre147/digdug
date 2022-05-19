package ir.ac.kntu.gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class HighScore extends Pane {
    private HashMap<String, Integer> scores;

    public HighScore(){
        scores = new HashMap<>();
        loadFromFile();

        setStyle("-fx-background-color: #000000");

        System.out.println(scores);

        int i = 0;
        for (String name : scores.keySet()){
            Text score = new Text(name + "   :   " + scores.get(name));
            score.setFill(Color.WHITE);
            score.setFont(Font.loadFont("file:src/main/resources/fonts/AtlantisInternational.ttf", 50));
            score.setLayoutX(15);
            score.setLayoutY(45 + 50*i);
            getChildren().add(score);
            i++;
        }
    }

    public void loadFromFile() {
        File file = new File("src/main/resources/assets/scores/scores.txt");
        if (file.exists()) {
            try (Scanner in = new Scanner(file)) {
                while (in.hasNext()){
                    scores.put(in.next(), in.nextInt());
                    in.skip("[\\s\\n]+");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
