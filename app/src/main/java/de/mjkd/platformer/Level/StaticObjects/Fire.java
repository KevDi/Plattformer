package de.mjkd.platformer.Level.StaticObjects;

import android.content.Context;

import de.mjkd.platformer.GameObject;

/**
 * Created by reaste on 05.10.17.
 */

public class Fire extends GameObject {

    Fire(Context context, float worldStartX, float worldStartY, char type, int pixelsPerMetre) {

        final int ANIMATION_FPS = 3;
        final int ANIMATION_FRAME_COUNT = 3;
        final String BITMAP_NAME = "fire";

        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        setMoves(false);
        setActive(true);
        setVisible(true);

        setBitmapName(BITMAP_NAME);

        setAnimFps(ANIMATION_FPS);
        setAnimFrameCount(ANIMATION_FRAME_COUNT);
        setAnimated(context, pixelsPerMetre, true);

        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitBox();


    }

    @Override
    public void update(long fps, float gravitiy) {

    }
}
