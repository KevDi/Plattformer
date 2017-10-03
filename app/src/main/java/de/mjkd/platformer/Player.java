package de.mjkd.platformer;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by reaste on 26.08.17.
 */

public class Player extends GameObject {

    RectHitBox rectHitBoxFeet;
    RectHitBox rectHitBoxHead;
    RectHitBox rectHitBoxLeft;
    RectHitBox rectHitBoxRight;

    final float MAX_X_VELOCITY = 10;
    boolean isPressingRight = false;
    boolean isPressingLeft = false;

    public boolean isFalling;
    private boolean isJumping;
    private long jumpTime;
    private long maxJumpTime = 700;

    public MachineGun bfg;

    public Player(Context context, float worldStartX, float worldStartY, int pixelsPerMetre) {
        final float HEIGHT = 2;
        final float WIDTH = 1;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setxVelocity(0);
        setyVelocity(0);
        setFacing(LEFT);
        isFalling = false;

        setMoves(true);
        setActive(true);
        setVisible(true);

        setType('p');

        setBitmapName("player");

        final int ANIMATION_FPS = 16;
        final int ANIMATION_FRAME_COUNT = 5;

        setAnimFps(ANIMATION_FPS);
        setAnimFrameCount(ANIMATION_FRAME_COUNT);
        setAnimated(context, pixelsPerMetre, true);

        setWorldLocation(worldStartX,worldStartY,0);

        rectHitBoxFeet = new RectHitBox();
        rectHitBoxHead = new RectHitBox();
        rectHitBoxLeft = new RectHitBox();
        rectHitBoxRight = new RectHitBox();

        bfg = new MachineGun();
    }

    @Override
    public void update(long fps, float gravitiy) {
        if (isPressingRight) {
            this.setxVelocity(MAX_X_VELOCITY);
        } else if (isPressingLeft) {
            this.setxVelocity(-MAX_X_VELOCITY);
        } else {
            this.setxVelocity(0);
        }

        if (this.getxVelocity() > 0) {
            setFacing(RIGHT);
        } else if (this.getxVelocity() < 0) {
            setFacing(LEFT);
        }

        if (isJumping)  {
            long timeJumping = System.currentTimeMillis() - jumpTime;
            if (timeJumping < maxJumpTime) {
                if (timeJumping < maxJumpTime / 2) {
                    this.setyVelocity(-gravitiy);
                } else if (timeJumping > maxJumpTime / 2) {
                    this.setyVelocity(gravitiy);
                }
            } else {
                isJumping = false;
            }
        } else {
            this.setyVelocity(gravitiy);
            isFalling = true;
        }
        bfg.update(fps, gravitiy);

        this.move(fps);

        Vector2Point5D location = getWorldLocation();
        float lx = location.x;
        float ly = location.y;

        rectHitBoxFeet.top = ly + getHeight() * .95f;
        rectHitBoxFeet.left = lx + getWidth() * .2f;
        rectHitBoxFeet.bottom = ly + getHeight() * .98f;
        rectHitBoxFeet.right = lx + getWidth() * .8f;
// Update player head hitbox
        rectHitBoxHead.top = ly;
        rectHitBoxHead.left = lx + getWidth() * .4f;
        rectHitBoxHead.bottom = ly + getHeight() * .2f;
        rectHitBoxHead.right = lx + getWidth() * .6f;
// Update player left hitbox
        rectHitBoxLeft.top = ly + getHeight() * .2f;
        rectHitBoxLeft.left = lx + getWidth() * .2f;
        rectHitBoxLeft.bottom = ly + getHeight() * .8f;
        rectHitBoxLeft.right = lx + getWidth() * .3f;
// Update player right hitbox
        rectHitBoxRight.top = ly + getHeight() * .2f;
        rectHitBoxRight.left = lx + getWidth() * .8f;
        rectHitBoxRight.bottom = ly + getHeight() * .8f;
        rectHitBoxRight.right = lx + getWidth() * .7f;
    }

    public int checkCollisions(RectHitBox rectHitBox) {
        int collided = 0;

        if (this.rectHitBoxLeft.intersects(rectHitBox)) {
            this.setWorldLocationX(rectHitBox.right - getWidth() * .2f);
            collided = 1;
        }

        if (this.rectHitBoxRight.intersects(rectHitBox)) {
            this.setWorldLocationX(rectHitBox.left - getWidth() * .8f);
            collided = 1;
        }

        if (this.rectHitBoxFeet.intersects(rectHitBox)) {
            this.setWorldLocationY(rectHitBox.top - getHeight());
            collided = 2;
        }

        if (this.rectHitBoxHead.intersects(rectHitBox)) {
            this.setWorldLocationY(rectHitBox.bottom);
            collided = 3;
        }
        return collided;
    }

    public void setPressingRight(boolean isPressingRight) {
        this.isPressingRight = isPressingRight;
    }

    public void setPressingLeft(boolean isPressingLeft) {
        this.isPressingLeft = isPressingLeft;
    }

    public void startJump(SoundManager sm) {
        if (!isFalling) {
            if(!isJumping) {
                isJumping = true;
                jumpTime = System.currentTimeMillis();
                sm.playSound("jump");
            }
        }
    }

    public boolean pullTrigger() {
        return bfg.shoot(this.getWorldLocation().x, this.getWorldLocation().y, getFacing(), getHeight());
    }
}

