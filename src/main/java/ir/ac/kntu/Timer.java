package ir.ac.kntu;

import javafx.scene.text.Text;

import java.time.Duration;
import java.time.Instant;

public class Timer extends Text {
    public Timer(){
        Instant start = Instant.now();
        new Thread(() -> {
            while (true){
                setText(String.valueOf(Duration.between(start, Instant.now()).toSeconds()));
            }
        }).start();
    }
}
