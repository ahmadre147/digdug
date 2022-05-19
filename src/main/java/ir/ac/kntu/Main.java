package ir.ac.kntu;

import ir.ac.kntu.gui.Menu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(new Pane(), 800, 600, Color.BLACK);

        Menu menu = new Menu(scene);
        ContentManager.setMenu(menu);

        // Setting stage properties
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("DigDig");

        stage.setOnCloseRequest(windowEvent -> System.exit(0));

        stage.setScene(scene);
        stage.show();
    }
}
