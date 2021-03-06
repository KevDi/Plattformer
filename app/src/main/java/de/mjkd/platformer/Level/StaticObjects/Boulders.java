package de.mjkd.platformer.Level.StaticObjects;

import java.util.Random;

import de.mjkd.platformer.GameObject;

/**
 * Created by reaste on 05.10.17.
 */

public class Boulders extends GameObject {

    public Boulders(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 1;
        final float WIDTH = 3;
        setHeight(HEIGHT); // 1 metre tall
        setWidth(WIDTH); // 1 metre wide
        setType(type);
        // Choose a Bitmap
        setBitmapName("boulder");
        setActive(false);//don't check for collisions etc
        // Randomly set the tree either just in front or just
        //behind the player -1 or 1
        Random rand = new Random();
        if(rand.nextInt(2)==0) {
            setWorldLocation(worldStartX, worldStartY, -1);
        }else{
            setWorldLocation(worldStartX, worldStartY, 1);//
        }
        //No hitbox!!
    }

    @Override
    public void update(long fps, float gravitiy) {

    }
}
