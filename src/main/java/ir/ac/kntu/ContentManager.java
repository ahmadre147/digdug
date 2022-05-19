package ir.ac.kntu;

import ir.ac.kntu.gui.GameMap;
import ir.ac.kntu.gui.Menu;
import ir.ac.kntu.objects.*;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public final class ContentManager {
    private static int[][] items;

    private static Player player;

    private static ArrayList<Rock> rocks;

    private static ArrayList<Balloon> balloons;

    private static ArrayList<ImageView> healthPlayer;

    private static ArrayList<Observable> randomObject;

    private static Block background;

    private static GameMap map;

    private static int size;

    private static Game game;

    private static Text scoreboard;

    private static Scene scene;

    private static Menu menu;

    public static void newMap(GameMap map) {
        ContentManager.map = map;
        rocks = new ArrayList<>();
        balloons = new ArrayList<>();
        healthPlayer = new ArrayList<>();
        randomObject = new ArrayList<>();
    }

    public static void loadMapFromFile() {
        File file = new File("src/main/resources/maps/map1.txt");
        if (file.exists()) {
            try (Scanner in = new Scanner(file)) {
                size = in.nextInt();
                items = new int[size][size];
                for (int i = 0;i < items.length;i++) {
                    for (int j = 0;j < items[i].length;j++) {
                        items[i][j] = in.nextInt();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveDataToFile() {
        File fOld = new File("src/main/resources/maps/map1.txt");
        fOld.delete();
        File fNew = new File("src/main/resources/maps/map1.txt");
        FileWriter writer;
        try {
            writer = new FileWriter(fNew);
            writer.write(""+size+"\n");
            for (int i = 0;i < size;i++){
                for (int j = 0;j < size;j++){
                    writer.write(items[i][j] + " ");
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateScoreboard(){
        scoreboard.setText(String.valueOf(ContentManager.getPlayer().getPoints()));
    }

    public static int getSize() {
        return size;
    }

    public static void addRock(Rock rock) {
        rocks.add(rock);
    }

    public static void addBalloon(Balloon balloon) {
        balloons.add(balloon);
    }

    public static int getMapItem(int x, int y) {
        return items[y][x];
    }

    public static void setMapItem(int x, int y, int val) {
        items[y][x] = val;
    }

    public synchronized static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        ContentManager.player = player;
    }

    public static void setEventHandlers(Scene scene){
        player.setEventHandler(scene);
        ContentManager.scene = scene;
    }

    public static void enableEventHandler(){
        player.setEventHandler(scene);
    }

    public static void disableEventHandler(){
        scene.setOnKeyPressed(keyEvent -> {});
    }

    public static ArrayList<Rock> getRocks() {
        return rocks;
    }

    public static ArrayList<Balloon> getBalloons() {
        return balloons;
    }

    public static void setScoreboard(Text scoreboard) {
        ContentManager.scoreboard = scoreboard;
    }

    public static void healthInit(){
        for (int i = 0;i < player.getHealth();i++){
            ImageView imageView = new ImageView("/assets/player/player_still_left.png");
            imageView.setLayoutX(size*25 + 30*(i+1));
            imageView.setLayoutY(size*25 - 45);
            imageView.setFitHeight(28);
            imageView.setFitWidth(24);
            healthPlayer.add(imageView);
        }
    }

    public static void addHealthPlayer(){
        ImageView imageView = new ImageView("/assets/player/player_still_left.png");
        imageView.setLayoutX(size*25 + 30*(healthPlayer.size()+1));
        imageView.setLayoutY(size*25 - 45);
        imageView.setFitHeight(28);
        imageView.setFitWidth(24);
        healthPlayer.add(imageView);
        map.showHealth();
    }

    public static ArrayList<ImageView> getHealthPlayer() {
        return healthPlayer;
    }

    public static void setGame(Game game) {
        ContentManager.game = game;
    }

    public static Game getGame() {
        return game;
    }

    public static void setBackground(Block background) {
        ContentManager.background = background;
    }

    public static Block getBackground() {
        return background;
    }

    public static void setTranslateX(Node node, int xInMap){
        node.setTranslateX(25*xInMap);
    }

    public static void setTranslateY(Node node, int yInMap){
        node.setTranslateY(75 + 25*yInMap);
    }

    public static void setMenu(Menu menu) {
        ContentManager.menu = menu;
    }

    public static Menu getMenu() {
        return menu;
    }

    public static GameMap getMap() {
        return map;
    }

    public static void ifTouchRandom(){
        new Thread(() -> {
            ArrayList<Observable> valuesToRemove = new ArrayList<>();
            for (Observable randomObj : randomObject) {
                if (player.getXInMap() == randomObj.getXInMap() && player.getYInMap() == randomObj.getYInMap()) {
                    Platform.runLater(randomObj::touch);
                    valuesToRemove.add(randomObj);
                }
            }
            randomObject.removeAll(valuesToRemove);
        }).start();
    }

    public static void addRandomObj(Observable observable){
        randomObject.add(observable);
    }
}
