package ir.ac.kntu.objects;

import ir.ac.kntu.ContentManager;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Random;

public class Block extends WritableImage {
    
    public Block(int x, int y) {
        super(x, y);

        initiateSky();
    }

    public void randomDotize() {
        Random random = new Random();
        random.setSeed(69);

        for (int i = 0, randomX, randomY;i < 100;i++){
            randomX = random.nextInt(ContentManager.getSize()*25);
            randomY = random.nextInt(75 + ContentManager.getSize()*25);

            if (randomY <= 75){
                continue;
            }

            drawCircle(randomX, randomY);

        }
    }

    private void drawCircle(int positionX, int positionY) {
        for (int i = -1;i <= 1;i++){
            for (int j = -1;j <= 1;j++){
                if ((i == -1 && j == -1) || (i == 1 && j == -1) || (i == -1 && j == 1) || (i == 1 && j == 1)){
                    continue;
                }

                getPixelWriter().setColor(positionX + i, positionY + j, Color.YELLOW);

            }
        }
    }

    private void initiateSky(){
        for (int i = 0;i < ContentManager.getSize()*25;i++){
            for (int j = 0;j < 75;j++){
                getPixelWriter().setColor(i, j, Color.BLUE);
            }
        }
    }

    public void setBlock(int x, int y) {
        for (int i = 0;i < 25;i++){
            for (int j = 0;j < 25;j++){
                if (y+j >= 75 + 25d*3*ContentManager.getSize()/4){
                    getPixelWriter().setColor(x+i, y+j, Color.web("C67D00"));
                } else if (y+j >= 75 + 25d*ContentManager.getSize()/2){
                    getPixelWriter().setColor(x+i, y+j, Color.web("C65C00"));
                } else if (y+j >= 75 + 25d*ContentManager.getSize()/4){
                    getPixelWriter().setColor(x+i, y+j, Color.web("C64200"));
                } else if (y+j >= 75){
                    getPixelWriter().setColor(x+i, y+j, Color.web("DB0000"));
                }
            }
        }
    }

    public void dig(double x1, double y1, double x2, double y2) {
        if (y2 < 100){
            return;
        }

        for (int i = (int) x1;i < x2;i++){
            for (int j = (int) y1;j < y2;j++){
                getPixelWriter().setColor(i, j, Color.BLACK);
            }
        }
    }
}
