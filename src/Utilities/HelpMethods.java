package Utilities;

import main.Game;

import java.awt.geom.Rectangle2D;

public class HelpMethods {
    public static boolean canMoveHere(float x, float y, float width, float height, int[][] levelData){
        if (!isSolid(x,y,levelData))
            if (!isSolid(x+width,y+height,levelData))
                if (!isSolid(x+width,y,levelData))
                    if (!isSolid(x,y+height,levelData))
                        return true;
        return false;
    }

    public static boolean isSolid(float x, float y, int[][] levelData){
        if(x<0||x> Game.GAME_WIDTH)//checks if inside window
            return true;
        if (y<0||y>Game.GAME_HEIGHT)//checks if inside window
            return true;

        int value = levelData[(int) (y/Game.TILE_SIZE)][(int) (x/Game.TILE_SIZE)];//finds what tiles I am in
        if(value<0 || value>=48 || value != 11)
            return true;
        return false;
    }

    public static float getEntityXPositionNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int) (hitBox.x / Game.TILE_SIZE);
        if(xSpeed > 0){//going to the right
            int tileXPosition = (currentTile * Game.TILE_SIZE);
            int xOffset = (int) (Game.TILE_SIZE - hitBox.width);
            return tileXPosition+xOffset-1;
        }else {
            return currentTile*Game.TILE_SIZE;
        }
    }

    public static float getEntityYPositionUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int) (hitBox.y / Game.TILE_SIZE);
        if(airSpeed > 0){//falling
            int tileYPosition = (currentTile * Game.TILE_SIZE);
            int yOffset = (int) (Game.TILE_SIZE - hitBox.height);
            return tileYPosition+yOffset-1;
        } else{// jumping
            return currentTile*Game.TILE_SIZE;
        }
    }

    public static boolean isEntityInAir(Rectangle2D.Float hitBox, int[][] levelData) {
        if(!isSolid(hitBox.x, hitBox.y+ hitBox.height+1, levelData)){
            if(!isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height+1, levelData)){
                return false;
            }
        }
        return true;
    }
}
