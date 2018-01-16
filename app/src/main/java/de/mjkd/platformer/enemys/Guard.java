package de.mjkd.platformer.enemys;

import android.content.Context;

import de.mjkd.platformer.GameObject;

/**
 * Created by reaste on 04.10.17.
 */

public class Guard extends GameObject {

    private float waypointX1; // always on left
    private float waypointX2; // always on right
    private int currentWaypoint;
    final float MAX_X_VELOCITY = 3;

    public Guard(Context context, float worldStartX, float worldStartY, char type, int pixelsPerMetre) {

        final int ANIMATION_FPS = 8;
        final int ANIMATION_FRAME_COUNT = 5;
        final String BITMAP_NAME = "guard";
        final float HEIGHT = 2f;
        final float WIDTH = 1f;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        setBitmapName(BITMAP_NAME);

        setMoves(true);
        setActive(true);
        setVisible(true);

        setAnimFps(ANIMATION_FPS);
        setAnimFrameCount(ANIMATION_FRAME_COUNT);
        setAnimated(context, pixelsPerMetre, true);

        setWorldLocation(worldStartX, worldStartY, 0);
        setxVelocity(-MAX_X_VELOCITY);
        currentWaypoint = 1;
        setRectHitBox();
        setFacing(LEFT);
    }

    public void setWaypoints(float x1, float x2) {
        waypointX1 = x1;
        waypointX2 = x2;
    }


    @Override
    public void update(long fps, float gravitiy) {
        if (currentWaypoint == 1) {
            if (getWorldLocation().x <= waypointX1) {
                currentWaypoint = 2;
                setxVelocity(MAX_X_VELOCITY);
                setFacing(RIGHT);
            }
        }

        if (currentWaypoint == 2) {
            if (getWorldLocation().x >= waypointX2) {
                currentWaypoint = 1;
                setxVelocity(-MAX_X_VELOCITY);
                setFacing(LEFT);
            }
        }

        move(fps);

        setRectHitBox();
    }
}
