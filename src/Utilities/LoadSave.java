package Utilities;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {//This class loads images and sends their data
    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";

    public static final String LEVEL_ONE = "level_one_data.png";

    public static BufferedImage getSpriteAtlas(String filename){
        BufferedImage image;
        InputStream iS = LoadSave.class.getResourceAsStream("/"+filename);
        try {
            image = ImageIO.read(iS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(iS != null) {
                try {
                    iS.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
        return image;
    }

    public static int[][] getLevelDate(){
        int[][] levelData = new int[Game.GAME_HEIGHT][Game.GAME_WIDTH];
        BufferedImage image = getSpriteAtlas(LEVEL_ONE);

        for (int j = 0; j<image.getHeight(); j++){
            for (int i = 0; i<image.getWidth(); i++){
                Color color = new Color(image.getRGB(i,j));
                int value = color.getRed();
                if(value >= 48)
                    value = 0;
                levelData[j][i] = value;
            }
        }
        return levelData;
    }

}
