package de.mjkd.platformer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by reaste on 24.08.17.
 */

public abstract class GameObject {

    private RectHitBox rectHitBox = new RectHitBox();
    private float xVelocity;
    private float yVelocity;
    final int LEFT = -1;
    final int RIGHT = -1;
    private int facing;
    private boolean moves = false;

    private Vector2Point5D worldLocation;

    private float height;
    private float width;

    private boolean active = true;
    private boolean visible = true;
    private int animFrameCount = 1;
    private char type;

    private String bitmapName;

    private Animation anim = null;
    private boolean animated;
    private int animFps = 1;

    public abstract void update(long fps, float gravitiy);

    public String getBitmapName() {
        return bitmapName;
    }

    public Bitmap prepareBitmap(Context context, String bitmapName, int pixelsPerMetre) {
        int resID = context.getResources().getIdentifier(bitmapName, "drawable", context.getPackageName());

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resID);

        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (width * animFrameCount * pixelsPerMetre),
                (int) (height * pixelsPerMetre),
                false);
        return bitmap;
    }

    public Vector2Point5D getWorldLocation() {
        return worldLocation;
    }

    public void setWorldLocation(float x, float y, int z) {
        this.worldLocation = new Vector2Point5D();
        this.worldLocation.x = x;
        this.worldLocation.y = y;
        this.worldLocation.z = z;
    }

    public void setBitmapName(String bitmapName) {
        this.bitmapName = bitmapName;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isVisible() {
        return visible;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public float getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(float xVelocity) {
        if (moves) {
            this.xVelocity = xVelocity;
        }
    }

    public float getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(float yVelocity) {
        if (moves) {
            this.yVelocity = yVelocity;
        }
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public boolean isMoves() {
        return moves;
    }

    public void setMoves(boolean moves) {
        this.moves = moves;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    void move(long fps) {
        if (xVelocity != 0) {
            this.worldLocation.x += xVelocity / fps;
        }

        if (yVelocity != 0) {
            this.worldLocation.y += yVelocity /fps;
        }
    }

    public void setRectHitBox() {
        rectHitBox.setTop(worldLocation.y);
        rectHitBox.setLeft(worldLocation.x);
        rectHitBox.setBottom(worldLocation.y + height);
        rectHitBox.setRight(worldLocation.x + width);
    }

    public RectHitBox getRectHitBox() {
        return rectHitBox;
    }

    public void setWorldLocationY(float y) {
        this.worldLocation.y = y;
    }

    public void setWorldLocationX(float x) {
        this.worldLocation.x = x;
    }

    public void setAnimFps(int animFps) {
        this.animFps = animFps;
    }

    public void setAnimFrameCount(int animFrameCount) {
        this.animFrameCount = animFrameCount;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(Context context, int pixelsPerMetre, boolean animated) {
        this.animated = animated;
        this.anim = new Animation(context, bitmapName, height, width, animFps, animFrameCount, pixelsPerMetre);
    }

    public Rect getRectToDraw(long deltaTime) {
        return anim.getCurrentFrame(deltaTime, xVelocity, isMoves());
    }
}
