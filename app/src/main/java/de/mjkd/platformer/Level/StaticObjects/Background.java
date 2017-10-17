package de.mjkd.platformer.Level.StaticObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Created by reaste on 06.10.17.
 */

public class Background {

    public Bitmap bitmap;
    public Bitmap bitmapReversed;

    public int width;
    public int height;

    public boolean reversedFrist;
    public int xClip; //controls where we clip the bitmaps each frame
    public float y;
    public float endY;
    public int z;

    public float speed;
    public boolean isParallax; //Not currently used

    Background(Context context, int yPixelsPerMetre, int screenWidth, BackgroundData data) {
        int resID = context.getResources().getIdentifier(data.bitmapName, "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), resID);

        // Which version of background (reversed or regular) is
        // currently drawn first (on left)
        reversedFrist = false;

        //Initialize animation variables
        xClip = 0; // always start at zero
        y = data.startY;
        endY = data.endY;
        z = data.layer;
        isParallax = data.isParallax;
        speed = data.speed; // Scrolling Background speed

        bitmap = Bitmap.createScaledBitmap(bitmap, screenWidth, data.height * yPixelsPerMetre, true);

        width = bitmap.getWidth();
        height = bitmap.getHeight();

        //Create a mirror image of the background
        Matrix matrix = new Matrix();
        matrix.setScale(-1,1); // Horizontal mirror effect
        bitmapReversed = Bitmap.createBitmap(bitmap, 0,0, width, height, matrix, true);
    }

}
