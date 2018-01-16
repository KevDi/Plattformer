package de.mjkd.platformer.Level.collectables;

import de.mjkd.platformer.GameObject;

/**
 * Created by reaste on 03.10.17.
 */

public class MachineGunUpgrade extends GameObject {

    public MachineGunUpgrade(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = .5f;
        final float WIDTH = .5f;

        setWidth(WIDTH);
        setHeight(HEIGHT);

        setType(type);

        setBitmapName("clip");

        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitBox();
    }

    @Override
    public void update(long fps, float gravitiy) {

    }
}
