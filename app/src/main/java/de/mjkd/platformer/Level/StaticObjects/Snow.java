package de.mjkd.platformer.Level.StaticObjects;

import de.mjkd.platformer.GameObject;

/**
 * Created by reaste on 05.10.17.
 */

public class Snow extends GameObject {

    Snow(float worldStartX, float worldStartY, char type) {
        setTraversable();
        final float HEIGHT = 1;
        final float WIDTH = 1;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setType(type);
        setBitmapName("snow");
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitBox();
    }

    @Override
    public void update(long fps, float gravitiy) {

    }
}
