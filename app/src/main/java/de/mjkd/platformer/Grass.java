package de.mjkd.platformer;

/**
 * Created by reaste on 26.08.17.
 */

public class Grass extends GameObject {

    public Grass(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        setBitmapName("turf");

        setWorldLocation(worldStartX, worldStartY, 0);

        setRectHitBox();
    }

    @Override
    public void update(long fps, float gravitiy) {

    }
}
