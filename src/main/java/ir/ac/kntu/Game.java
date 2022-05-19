package ir.ac.kntu;

import ir.ac.kntu.objects.*;
import javafx.application.Platform;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Game {

    public Game() {

//        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
//        poolExecutor.execute(ContentManager.getBalloons().get(0)::chase);
//        poolExecutor.execute(ContentManager.getBalloons().get(1)::chase);
//        poolExecutor.execute(ContentManager.getBalloons().get(2)::chase);

        new Thread(() -> {
            while (ContentManager.getPlayer().isAlive()){
                for (Balloon balloon : ContentManager.getBalloons()) {
                    balloon.chase();
                }
            }
        }).start();

        putRandomObjects();
    }

    public synchronized void endGame(){
        Platform.runLater(() -> {
            ContentManager.disableEventHandler();
            ContentManager.getMenu().gameOver();
        });
    }

    public void checkRocks(){
        new Thread(() -> {
            for (Rock rock : ContentManager.getRocks()) {
                while (ContentManager.getMapItem(rock.getXInMap(), rock.getYInMap() + 1) != 1) {
                    rock.fallDown();
                }
            }
        }).start();
    }

    public void putRandomObjects(){
        Random random = new Random();
        random.setSeed(420);
        new Thread(() -> {
            while (ContentManager.getPlayer().isAlive()){
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int randomX = random.nextInt(ContentManager.getSize());
                int randomY = random.nextInt(ContentManager.getSize());

                Platform.runLater(() -> initRandomObject(randomX, randomY, random.nextInt(3)));
            }
        }).start();
    }

    public void initRandomObject(int x, int y, int type){
        switch (type) {
            case 0:
                Gun gun = new Gun(x, y);
                ContentManager.setTranslateX(gun, x);
                ContentManager.setTranslateY(gun, y);
                ContentManager.addRandomObj(gun);
                ContentManager.getMap().getChildren().add(gun);
                break;
            case 1:
                Heart heart = new Heart(x, y);
                ContentManager.setTranslateX(heart, x);
                ContentManager.setTranslateY(heart, y);
                ContentManager.addRandomObj(heart);
                ContentManager.getMap().getChildren().add(heart);
                break;
            case 2:
                Mushroom mushroom = new Mushroom(x, y);
                ContentManager.setTranslateX(mushroom, x);
                ContentManager.setTranslateY(mushroom, y);
                ContentManager.addRandomObj(mushroom);
                ContentManager.getMap().getChildren().add(mushroom);
                break;
            default:
                break;
        }
    }
}
