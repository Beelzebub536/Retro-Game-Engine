package Levels;

import Utilities.LoadSave;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {//gets and initializes level info and draws them
    private BufferedImage[] levelSprite;
    private final Game game;
    private Level levelOne;
    public LevelManager(Game game){
        this.game = game;
        importOutsideSprite();
        levelOne = new Level(LoadSave.getLevelDate());
    }

    public Level getLevelOne() {
        return levelOne;
    }

    private void importOutsideSprite() {
        BufferedImage image = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int j = 0;j< 4; j++){
            for (int i = 0; i<12; i++){
                levelSprite[j*12+i] = image.getSubimage(i*32,j*32,32,32);
            }
        }
    }

    public void drawLevel(Graphics g){
        for (int j =0; j<Game.TILES_IN_HEIGHT; j++){
            for (int i=0; i<Game.TILES_IN_WIDTH;i++){
                int index = levelOne.getSpriteIndex(i,j);
                g.drawImage(levelSprite[index],Game.TILE_SIZE*i,Game.TILE_SIZE*j,Game.TILE_SIZE,Game.TILE_SIZE,null);
            }
        }
    }

    public void update(){

    }
}
