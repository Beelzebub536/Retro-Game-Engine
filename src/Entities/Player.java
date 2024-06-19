package Entities;

import Utilities.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Utilities.Constants.PlayerConstants.*;

public class Player extends Entity{

    private BufferedImage image;
    private BufferedImage[][] playerAnimations;
    private int animationSpeed = 15, animationIndex, animationTick;
    private int playerAction = IDLE;
    private boolean moving , attack1 = false;
    private boolean north, south, east, west;
    private float playerSpeed = 2.0f;

    public Player(float x, float y) {
        super(x, y);
        loadAnimation();
    }

    public void setAttack1(boolean attack1) {
        this.attack1 = attack1;
    }

    public void setNorth(boolean north) {
        this.north = north;
    }

    public void setSouth(boolean south) {
        this.south = south;
    }

    public void setEast(boolean east) {
        this.east = east;
    }

    public void setWest(boolean west) {
        this.west = west;
    }

    public void loadAnimation() {
        image = LoadSave.getPlayerAtlas(LoadSave.PLAYER_ATLAS);
        playerAnimations = new BufferedImage[9][6];
        for (int j = 0; j < playerAnimations.length; j++) {
            for (int i = 0; i < playerAnimations[j].length; i++) {
                playerAnimations[j][i] = image.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    public void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(playerAction)) { // Ensure animationIndex resets correctly
                animationIndex = 0;
                attack1 = false;
            }
        }
    }

    public void resetAnimation(){
        animationTick =0;
        animationIndex=0;
    }

    private void setAnimation() {
        int previousAction = playerAction;
        if(moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
        if (attack1)
            playerAction=ATTACK_1;
        if (previousAction != playerAction)
            resetAnimation();
    }

    private void updatePosition() {
        moving = false;
        if(north && !south) {
            moving=true;
            y -= playerSpeed;
        }else if (south && !north){
            moving=true;
            y += playerSpeed;
        }

        if(east && !west) {
            moving=true;
            x += playerSpeed;
        }else if (west && !east){
            moving=true;
            x -= playerSpeed;
        }
    }

    public void updateGame() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g){
        if (image != null) {
            g.drawImage(playerAnimations[playerAction][animationIndex], (int) x, (int) y, 128, 80, null);
        } else {
            System.err.println("Image is not loaded.");
        }
    }

    public void resetBooleans() {
        north = false;
        south = false;
        east = false;
        west = false;
    }
}
