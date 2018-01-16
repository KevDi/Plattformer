package de.mjkd.platformer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import de.mjkd.platformer.Level.LevelManager;
import de.mjkd.platformer.Level.StaticObjects.Background;
import de.mjkd.platformer.Level.StaticObjects.Teleport;
import de.mjkd.platformer.enemys.Drone;
import de.mjkd.platformer.player.PlayerState;
import de.mjkd.platformer.util.Location;
import de.mjkd.platformer.util.RectHitBox;
import de.mjkd.platformer.util.SoundManager;
import de.mjkd.platformer.util.Viewport;

/**
 * Created by reaste on 24.08.17.
 */

public class PlatformView extends SurfaceView implements Runnable {

    private boolean debugging = false;
    private volatile boolean running;
    private Thread gameThread = null;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    Context context;

    private LevelManager lm;
    private Viewport vp;
    InputController ic;
    SoundManager sm;
    private PlayerState ps;

    long startFrameTime;
    long timeThisFrame;
    long fps;

    public PlatformView(Context context, int screenWidth, int screenHight) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        paint = new Paint();

        vp = new Viewport(screenWidth, screenHight);

        sm = new SoundManager();
        sm.loadSound(context);
        ps = new PlayerState();

        loadLevel("LevelCave", 1, 16);
    }

    public void loadLevel(String level, float px, float py) {
        lm = null;

        lm = new LevelManager(context, vp.getPixelsPerMetreX(), vp.getScreenWidth(), ic, level, px, py);

        ic = new InputController(vp.getScreenWidth(), vp.getScreenHeight());

        PointF location = new PointF(px, py);
        ps.saveLocation(location);
        lm.player.bfg.setRateOfFire(ps.getFireRate());

        vp.setWorldCentre(lm.gameObjects.get(lm.playerIndex)
                .getWorldLocation().x,
                lm.gameObjects.get(lm.playerIndex)
                        .getWorldLocation().y);

    }

    @Override
    public void run() {
        while(running) {
            startFrameTime = System.currentTimeMillis();

            update();

            draw();

            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000/timeThisFrame;
            }
        }
    }

    private void update() {
        for (GameObject go : lm.gameObjects) {
            if (go.isActive()) {
                if (!vp.clipObjects(go.getWorldLocation().x, go.getWorldLocation().y, go.getWidth(), go.getHeight())) {
                    go.setVisible(true);

                    int hit = lm.player.checkCollisions(go.getRectHitBox());
                    if (hit > 0) {
                        switch(go.getType()) {
                            case 'c':
                                sm.playSound("coin_pickup");
                                go.setActive(false);
                                go.setVisible(false);
                                ps.gotCredit();
                                if (hit != 2){
                                    lm.player.restorePreviousVelocity();
                                }
                                break;
                            case 'u':
                                sm.playSound("gun_upgrade");
                                go.setActive(false);
                                go.setVisible(false);
                                lm.player.bfg.updateRateOfFire();
                                ps.increaseFireRate();
                                if (hit != 2){
                                    lm.player.restorePreviousVelocity();
                                }
                                break;
                            case 'e':
                                //extralife
                                go.setActive(false);
                                go.setVisible(false);
                                sm.playSound("extra_life");
                                ps.addLife();
                                if (hit != 2){
                                    lm.player.restorePreviousVelocity();
                                }
                                break;
                            case 'd':
                                PointF location;
                                //hit by drone
                                sm.playSound("player_burn");
                                ps.loseLife();
                                location = new PointF(ps.loadLocation().x,
                                        ps.loadLocation().y);
                                lm.player.setWorldLocationX(location.x);
                                lm.player.setWorldLocationY(location.y);
                                lm.player.setxVelocity(0);
                                break;
                            case 'g':
                                // Hit by guard
                                sm.playSound("player_burn");
                                ps.loseLife();
                                location = new PointF(ps.loadLocation().x,
                                        ps.loadLocation().y);
                                lm.player.setWorldLocationX(location.x);
                                lm.player.setWorldLocationY(location.y);
                                lm.player.setxVelocity(0);
                                break;
                            case 'f':
                                sm.playSound("player_burn");
                                ps.loseLife();
                                location = new PointF(ps.loadLocation().x,
                                        ps.loadLocation().y);
                                lm.player.setWorldLocationX(location.x);
                                lm.player.setWorldLocationY(location.y);
                                lm.player.setxVelocity(0);
                                break;
                            case 't':
                                Teleport teleport = (Teleport)go;
                                Location t = teleport.getTarget();
                                loadLevel(t.level, t.x, t.y);
                                sm.playSound("teleport");
                                break;
                            default:
                                if (hit == 1) {
                                    lm.player.setxVelocity(0);
                                    lm.player.setPressingRight(false);
                                }

                                if (hit == 2) {
                                    lm.player.isFalling = false;
                                }
                                break;
                        }
                    }

                    for (int i = 0; i < lm.player.bfg.getNumBullets(); i++) {
                        // Make a hitbox out of the current bullet
                        RectHitBox r = new RectHitBox();
                        r.setLeft(lm.player.bfg.getBulletX(i));
                        r.setTop(lm.player.bfg.getBulletY(i));
                        r.setRight(lm.player.bfg.getBulletX(i) + .1f);
                        r.setBottom(lm.player.bfg.getBulletY(i) + .1f);

                        if (go.getRectHitBox().intersects(r)) {
                            // Collision detected
                            // make bullet disapper until it
                            // is respawned as a new bullet
                            lm.player.bfg.hideBullet(i);

                            if (go.getType() != 'g' && go.getType() != 'd') {
                                sm.playSound("ricochet");
                            } else if (go.getType() == 'g') {
                                go.setWorldLocationX(go.getWorldLocation().x + 2 * (lm.player.bfg.getDirection(i)));
                                sm.playSound("hit_guard");
                            } else if (go.getType() == 'd') {
                                sm.playSound("explode");
                                go.setWorldLocation(-100, -100, 0);
                            }
                        }
                    }

                    if (lm.isPlaying()) {
                        go.update(fps, lm.gravity);

                        if (go.getType() == 'd') {
                            Drone d = (Drone) go;
                            d.setWaypoint(lm.player.getWorldLocation());
                        }
                    }
                } else {
                    go.setVisible(false);
                }
            }
        }
        if (lm.isPlaying()) {
            //Reset the players location as the world centre of the viewport
            //if game is playing
            vp.setWorldCentre(lm.gameObjects.get(lm.playerIndex)
                            .getWorldLocation().x,
                    lm.gameObjects.get(lm.playerIndex)
                            .getWorldLocation().y);
            if (lm.player.getWorldLocation().x < 0 ||
                    lm.player.getWorldLocation().x > lm.mapWidth ||
                    lm.player.getWorldLocation().y > lm.mapHeight) {
                sm.playSound("player_burn");
                ps.loseLife();
                PointF location = new PointF(ps.loadLocation().x,
                        ps.loadLocation().y);
                lm.player.setWorldLocationX(location.x);
                lm.player.setWorldLocationY(location.y);
                lm.player.setxVelocity(0);
            }
            // Check if game is over
            if (ps.getLives() == 0) {
                ps = new PlayerState();
                loadLevel("LevelCave", 1, 16);
            }
        }
    }

    private void drawBackground(int start, int stop) {

        Rect fromRect1 = new Rect();
        Rect toRect1 = new Rect();
        Rect fromRect2 = new Rect();
        Rect toRect2 = new Rect();

        for (Background bg : lm.backgrounds) {
            if (bg.z < start && bg.z > stop) {
                // Is this layer in the Viewport?
                // Clip anything off-screen
                if (!vp.clipObjects(-1, bg.y, 1000, bg.height)) {
                    float floatstartY = ((vp.getyCenter() -
                            ((vp.getViewportWorldCentreY() - bg.y) *
                                    vp.getPixelsPerMetreY())));
                    int startY = (int)floatstartY;

                    float floatendY = ((vp.getyCenter() -
                            ((vp.getViewportWorldCentreY() - bg.endY) *
                            vp.getPixelsPerMetreY())));
                    int endY = (int)floatendY;

                    // Define what portion of bitmaps to capture
                    // and what coordinates to draw them at
                    fromRect1 = new Rect(0,0, bg.width - bg.xClip, bg.height);

                    toRect1 = new Rect(bg.xClip, startY, bg.width, endY);

                    fromRect2 = new Rect(bg.width - bg.xClip, 0, bg.width, bg.height);

                    toRect2 = new Rect(0, startY, bg.xClip, endY);
                }

                if (!bg.reversedFrist) {
                    canvas.drawBitmap(bg.bitmap,
                            fromRect1, toRect1, paint);
                    canvas.drawBitmap(bg.bitmapReversed,
                            fromRect2, toRect2, paint);
                } else {
                    canvas.drawBitmap(bg.bitmap,
                            fromRect2, toRect2, paint);
                    canvas.drawBitmap(bg.bitmapReversed,
                            fromRect1, toRect1, paint);
                }

                // Calculate the next value for the backgrounds
                // clipping position by modifying xClip
                // and switching which background is drawn first,
                // if necessary.
                bg.xClip -= lm.player.getxVelocity() / (20 / bg.speed);
                if (bg.xClip >= bg.width) {
                    bg.xClip = 0;
                    bg.reversedFrist = !bg.reversedFrist;
                } else if (bg.xClip <= 0) {
                    bg.xClip = bg.width;
                    bg.reversedFrist = !bg.reversedFrist;
                }
            }
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {

            canvas = surfaceHolder.lockCanvas();

            paint.setColor(Color.argb(255,0,0,0));
            canvas.drawColor(Color.argb(255,0,0,0));

            drawBackground(0, -3);

            Rect toScreen2d = new Rect();

            for (int layer = -1; layer <= 1; layer++) {
                for (GameObject go : lm.gameObjects) {
                    if (go.isVisible() && go.getWorldLocation().z == layer) {
                        toScreen2d.set(vp.worldToScreen
                                (go.getWorldLocation().x,
                                        go.getWorldLocation().y,
                                        go.getWidth(),
                                        go.getHeight()));

                        if (go.isAnimated()) {
                            //Get the next frame of the bitmap
                            //Rotate if necessary
                            if (go.getFacing() == 1) {
                                //Rotate
                                Matrix flipper = new Matrix();
                                flipper.preScale(-1,1);
                                Rect r = go.getRectToDraw(System.currentTimeMillis());
                                Bitmap b = Bitmap.createBitmap(lm.bitmapsArray[
                                        lm.getBitmapIndex(go.getType())],
                                        r.left,
                                        r.top,
                                        r.width(),
                                        r.height(),
                                        flipper,
                                        true);
                                canvas.drawBitmap(b,
                                        toScreen2d.left,
                                        toScreen2d.top, paint);
                            } else {
                                canvas.drawBitmap(lm.bitmapsArray[lm.getBitmapIndex(go.getType())],
                                        go.getRectToDraw(System.currentTimeMillis()),
                                        toScreen2d, paint);
                            }
                        } else {
                            canvas.drawBitmap(lm.bitmapsArray[lm.getBitmapIndex(go.getType())],
                                    toScreen2d.left,
                                    toScreen2d.top, paint);
                        }
                    }
                }
            }

            paint.setColor(Color.argb(255,255,255,255));
            for (int i = 0; i < lm.player.bfg.getNumBullets(); i++) {
                // Pass in the x and y coods as usual
                //then .25 and .05 for the bullet width and height
                toScreen2d.set(vp.worldToScreen(lm.player.bfg.getBulletX(i),lm.player.bfg.getBulletY(i),.25f,.05f));
                canvas.drawRect(toScreen2d, paint);
            }

            drawBackground(4, 0);

            int topSpace = vp.getPixelsPerMetreY() / 4;
            int iconSize = vp.getPixelsPerMetreX();
            int padding = vp.getPixelsPerMetreX() / 5;
            int centring = vp.getPixelsPerMetreY() / 6;
            paint.setTextSize(vp.getPixelsPerMetreY()/2);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(Color.argb(100, 0, 0, 0));
            canvas.drawRect(0,0,iconSize * 7.0f, topSpace*2 + iconSize,paint);
            paint.setColor(Color.argb(255, 255, 255, 0));
            canvas.drawBitmap(lm.getBitmap('e'), 0, topSpace, paint);
            canvas.drawText("" + ps.getLives(), (iconSize * 1) + padding,
                    (iconSize) - centring, paint);
            canvas.drawBitmap(lm.getBitmap('c'), (iconSize * 2.5f) + padding,
                    topSpace, paint);
            canvas.drawText("" + ps.getCredits(), (iconSize * 3.5f) + padding
                    * 2, (iconSize) - centring, paint);
            canvas.drawBitmap(lm.getBitmap('u'), (iconSize * 5.0f) + padding,
                    topSpace, paint);
            canvas.drawText("" + ps.getFireRate(), (iconSize * 6.0f) + padding
                    * 2, (iconSize) - centring, paint);

            if (debugging) {
                paint.setTextSize(16);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255,255,255,255));
                canvas.drawText("fps:" + fps, 10, 60, paint);
                canvas.drawText("num objects:" + lm.gameObjects.size(), 10, 80, paint);
                canvas.drawText("num clipped:" + vp.getNumClipped(), 10, 100, paint);
                canvas.drawText("playerX:" + lm.gameObjects.get(lm.playerIndex).getWorldLocation().x, 10, 120, paint);
                canvas.drawText("playerY:" + lm.gameObjects.get(lm.playerIndex).getWorldLocation().y, 10, 140, paint);
                canvas.drawText("Gravity:" +
                        lm.gravity, 10, 160, paint);
                canvas.drawText("X velocity:" +
                                lm.gameObjects.get(lm.playerIndex).getxVelocity(),
                        10, 180, paint);
                canvas.drawText("Y velocity:" +
                                lm.gameObjects.get(lm.playerIndex).getyVelocity(),
                        10, 200, paint);
                vp.resetNumClipped();
            }

            paint.setColor(Color.argb(80,255,255,255));
            ArrayList<Rect> buttonsToDraw;
            buttonsToDraw = ic.getButtons();

            for (Rect rect : buttonsToDraw) {
                RectF rf = new RectF(rect.left, rect.top, rect.right, rect.bottom);
                canvas.drawRoundRect(rf, 15f,15f,paint);
            }

            if (!this.lm.isPlaying()) {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.argb(255,255,255,255));

                paint.setTextSize(120);
                canvas.drawText("Paused", vp.getScreenWidth() /2, vp.getScreenHeight() / 2, paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (lm != null) {
            ic.handleInput(event, lm, sm, vp);
        }
        return true;
    }

    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException ex) {
            Log.e("error", "failed to pause thread");
        }
    }

    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
