package Entities;

import Utilities.HelpMethods;
import Utilities.LoadSave;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static Utilities.Constants.PlayerConstants.*;

public class Player extends Entity {

    private BufferedImage image;
    private BufferedImage[][] playerAnimations;
    private int animationSpeed = 15, animationIndex, animationTick;
    private int playerAction = IDLE;
    private boolean moving, attack1 = false;
    private boolean north, south, east, west, jump, inAir = true;
    private float playerSpeed = 1.5f;
    private int[][] levelData;
    private float offsetX = 21 * Game.SCALE;
    private float offsetY = 4 * Game.SCALE;

    //gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float fallSpeed = 0.5f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;


    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        initHitBox(x, y, 20 * Game.SCALE, 27 * Game.SCALE);
        loadAnimation();
    }

    public void loadAnimation() {
        image = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);
        playerAnimations = new BufferedImage[9][6];
        for (int j = 0; j < playerAnimations.length; j++) {
            for (int i = 0; i < playerAnimations[j].length; i++) {
                playerAnimations[j][i] = image.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
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

    public void resetAnimation() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void setAnimation() {
        int previousAction = playerAction;
        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
        if (inAir) {
            if (airSpeed > 0)
                playerAction = JUMP;
            else
                previousAction = FALLING;
        }
        if (attack1)
            playerAction = ATTACK_1;
        if (previousAction != playerAction)
            resetAnimation();
    }

    private void updatePosition() {
        moving = false;
        if (jump)
            jump();

        if (!inAir && !east && !west)
            return;

        float xSpeed = 0;
        if (east)
            xSpeed = playerSpeed;
        if (west)
            xSpeed = -playerSpeed;

        if (!inAir) {
            if (!HelpMethods.isEntityInAir(hitBox, levelData))
                inAir = true;
        }

        if (inAir)
            updateYPosition(xSpeed);
        else
            updateXPosition(xSpeed);

        moving = true;
    }

    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void updateYPosition(float xSpeed) {
        if (HelpMethods.canMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
            hitBox.y += airSpeed;
            airSpeed += gravity;
            updateXPosition(xSpeed);
        } else {
            hitBox.y = HelpMethods.getEntityYPositionUnderRoofOrAboveFloor(hitBox, airSpeed);
            if (airSpeed > 0)
                resetInAir();
            else
                airSpeed = fallSpeed;
            updateXPosition(xSpeed);
        }
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPosition(float xSpeed) {
        if (HelpMethods.canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {
            hitBox.x += xSpeed;
        } else {
            hitBox.x = HelpMethods.getEntityXPositionNextToWall(hitBox, xSpeed);
        }
    }

    public void updateGame() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        if (image != null) {
            g.drawImage(playerAnimations[playerAction][animationIndex], (int) (hitBox.x - offsetX), (int) (hitBox.y - offsetY), width, height, null);
            drawHitBox(g);
        } else {
            System.out.println("Image is not loaded.");
        }
    }

    public void resetBooleans() {
        north = false;
        south = false;
        east = false;
        west = false;
    }

    public void setAttack1(boolean attack1) {
        this.attack1 = attack1;
    }

    public void setEast(boolean east) {
        this.east = east;
    }

    public void setWest(boolean west) {
        this.west = west;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
