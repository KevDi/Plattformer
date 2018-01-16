package de.mjkd.platformer.Level.collectables;

import de.mjkd.platformer.GameObject;

/**
 * Created by reaste on 03.10.17.
 */

public class ExtraLife extends GameObject {

    public ExtraLife(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = .8f;
        final float WIDTH = .65f;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        setBitmapName("life");

        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitBox();
    }


    @Override
    public void update(long fps, float gravitiy) {

    }
}
