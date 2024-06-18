package Entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Utilities.Constants.Directions.*;
import static Utilities.Constants.Directions.LEFT;
import static Utilities.Constants.PlayerConstants.*;

public abstract class Entity {
    protected float x,y;
    public Entity(float x, float y) {
        this.x=x;
        this.y=y;
    }

}
