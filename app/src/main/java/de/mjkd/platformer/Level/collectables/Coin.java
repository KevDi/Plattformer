package de.mjkd.platformer.Level.collectables;

import de.mjkd.platformer.GameObject;

/**
 * Created by reaste on 03.10.17.
 */

public class Coin extends GameObject {

    public Coin(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = .5f;
        final float WIDTH = .5f;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        setBitmapName("coin");

        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitBox();
    }

    public void update(long fps, float gravity) {}
}
