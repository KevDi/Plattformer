package de.mjkd.platformer.Level.StaticObjects;

import java.util.Random;

import de.mjkd.platformer.GameObject;

/**
 * Created by reaste on 05.10.17.
 */

public class Stalagmite extends GameObject {

    public Stalagmite(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 3;
        final float WIDTH = 2;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setType(type);
        setBitmapName("stalacmite");
        setActive(false);
        Random rand = new Random();
        if(rand.nextInt(2)==0) {
            setWorldLocation(worldStartX, worldStartY, -1);
        }else{
            setWorldLocation(worldStartX, worldStartY, 1);
        }
    }

    @Override
    public void update(long fps, float gravitiy) {
    }
}
