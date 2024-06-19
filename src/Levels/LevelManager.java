package Levels;

import Utilities.LoadSave;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {
    private BufferedImage image;
    private final Game game;
    public LevelManager(Game game){
        this.game = game;
        image = LoadSave.getPlayerAtlas(LoadSave.LEVEL_ATLAS);
    }

    public void drawLevel(Graphics g){
        g.drawImage(image,0,0,null);
    }

    public void update(){

    }
}
