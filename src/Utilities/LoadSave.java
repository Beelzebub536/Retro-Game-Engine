package Utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";

    public static BufferedImage getPlayerAtlas(String filename){
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

}
