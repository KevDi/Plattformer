package de.mjkd.platformer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by reaste on 26.08.17.
 */

public class LevelManager {

    private String level;

    int mapWidth;
    int mapHeight;

    Player player;
    int playerIndex;

    private boolean playing;
    float gravity;

    LevelData levelData;
    ArrayList<GameObject> gameObjects;

    ArrayList<Rect> currentButtons;
    Bitmap[] bitmapsArray;

    public LevelManager(Context context, int pixelsPerMetre, int screenWidth, InputController ic, String level, float px, float py) {
        this.level = level;

        switch (level) {
            case "LevelCave":
                levelData = new LevelCave();
                break;
        }

        gameObjects = new ArrayList<>();

        bitmapsArray = new Bitmap[25];

        loadMapData(context, pixelsPerMetre, px, py);

        setWaypoints();
    }

    public boolean isPlaying() {
        return playing;
    }

    public Bitmap getBitmap(char blockType) {
        int index;
        switch (blockType) {
            case '.':
                index = 0;
                break;
            case '1':
                index  = 1;
                break;
            case 'p':
                index = 2;
                break;
            case 'c':
                index = 3;
                break;
            case 'u':
                index = 4;
                break;
            case 'e':
                index = 5;
                break;
            case 'd':
                index = 6;
                break;
            case 'g':
                index = 7;
                break;
            default:
                index = 0;
                break;
        }
        return bitmapsArray[index];
    }

    public int getBitmapIndex(char blockType) {
        int index;
        switch (blockType) {
            case '.':
                index = 0;
                break;
            case '1':
                index  = 1;
                break;
            case 'p':
                index = 2;
                break;
            case 'c':
                index = 3;
                break;
            case 'u':
                index = 4;
                break;
            case 'e':
                index = 5;
                break;
            case 'd':
                index = 6;
                break;
            case 'g':
                index = 7;
                break;
            default:
                index = 0;
                break;
        }
        return index;
    }

    private void loadMapData(Context context, int pixelsPerMetre, float px, float py) {
        char c;

        int currentIndex = -1;

        mapHeight = levelData.tiles.size();
        mapWidth = levelData.tiles.get(0).length();

        for (int i = 0; i < levelData.tiles.size(); i++) {
            for (int j = 0; j < levelData.tiles.get(i).length(); j++) {
                c = levelData.tiles.get(i).charAt(j);
                if (c != '.') {
                    currentIndex++;
                    switch (c) {
                        case '1':
                            gameObjects.add(new Grass(j,i,c));
                            break;
                        case 'p':
                            gameObjects.add(new Player(context, px, py, pixelsPerMetre));
                            playerIndex = currentIndex;
                            player = (Player)gameObjects.get(playerIndex);
                            break;
                        case 'c':
                            gameObjects.add(new Coin(j,i,c));
                            break;
                        case 'u':
                            gameObjects.add(new MachineGunUpgrade(j,i,c));
                            break;
                        case 'e':
                            gameObjects.add(new ExtraLife(j,i,c));
                            break;
                        case 'd':
                            gameObjects.add(new Drone(j,i,c));
                            break;
                        case 'g':
                            gameObjects.add(new Guard(context,j,i,c, pixelsPerMetre));
                            break;
                    }
                    if (bitmapsArray[getBitmapIndex(c)] == null) {
                        bitmapsArray[getBitmapIndex(c)] =
                                gameObjects.get(currentIndex)
                                        .prepareBitmap(context,
                                                gameObjects.get(currentIndex)
                                                        .getBitmapName(),
                                                pixelsPerMetre);
                    }
                }
            }
        }
    }

    public void switchPlayingStatus() {
        playing = !playing;
        if (playing) {
            gravity = 6;
        } else {
            gravity = 0;
        }
    }


    public void setWaypoints() {
        for (GameObject guard : this.gameObjects) {
            if (guard.getType() == 'g') {
                // Set waypoints for this guard
                // find the tile beneath the guard
                // this relies on the designer putting
                // the guard in sensible location
                int startTileIndex = -1;
                int startGuardIndex = 0;
                float waypointX1 = -1;
                float waypointX2 = -1;

                for (GameObject tile : this.gameObjects) {
                    startTileIndex++;
                    if (tile.getWorldLocation().y == guard.getWorldLocation().y + 2) {
                        //Tile is two spaces below current guard
                        // Now see if has same x coordinate
                        if (tile.getWorldLocation().x == guard.getWorldLocation().x) {
                            // Found the tile the guard is "standing" on
                            // Now go left as far as possible
                            // before non traversable tile is found
                            // Either on guards row or tile row
                            // upto a maximum of 5 tiles.
                            // 5 is an arbitrary value you can change
                            for (int i = 0; i < 5; i++) {
                                if (!gameObjects.get(startTileIndex - i).isTraversable()) {
                                    waypointX1 = gameObjects.get(startTileIndex - (i+1)).getWorldLocation().x;
                                    break;
                                } else {
                                    // Set to max 5 tiles as
                                    // no non traversible tile found
                                    waypointX1 = gameObjects.get(startTileIndex - 5).getWorldLocation().x;
                                }
                            }

                            // Rigth Waypoint
                            for (int i = 0; i < 5; i++) {
                                if (!gameObjects.get(startTileIndex + i).isTraversable()) {
                                    waypointX2 = gameObjects.get(startTileIndex + (i-1)).getWorldLocation().x;
                                    break;
                                } else {
                                    // Set to max 5 tiles as
                                    // no non traversible tile found
                                    waypointX2 = gameObjects.get(startTileIndex + 5).getWorldLocation().x;
                                }
                            }
                            Guard g = (Guard) guard;
                            g.setWaypoints(waypointX1, waypointX2);
                        }
                    }
                }
            }
        }
    }
}
