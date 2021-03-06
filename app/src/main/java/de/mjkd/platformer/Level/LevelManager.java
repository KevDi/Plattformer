package de.mjkd.platformer.Level;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;

import de.mjkd.platformer.Level.StaticObjects.Background;
import de.mjkd.platformer.Level.StaticObjects.BackgroundData;
import de.mjkd.platformer.Level.StaticObjects.Boulders;
import de.mjkd.platformer.Level.StaticObjects.Brick;
import de.mjkd.platformer.Level.StaticObjects.Cart;
import de.mjkd.platformer.Level.StaticObjects.Coal;
import de.mjkd.platformer.Level.collectables.Coin;
import de.mjkd.platformer.Level.StaticObjects.Concrete;
import de.mjkd.platformer.enemys.Drone;
import de.mjkd.platformer.Level.collectables.ExtraLife;
import de.mjkd.platformer.Level.StaticObjects.Fire;
import de.mjkd.platformer.GameObject;
import de.mjkd.platformer.Level.StaticObjects.Grass;
import de.mjkd.platformer.enemys.Guard;
import de.mjkd.platformer.InputController;
import de.mjkd.platformer.Level.StaticObjects.Lampost;
import de.mjkd.platformer.Level.collectables.MachineGunUpgrade;
import de.mjkd.platformer.player.Player;
import de.mjkd.platformer.Level.StaticObjects.Scorched;
import de.mjkd.platformer.Level.StaticObjects.Snow;
import de.mjkd.platformer.Level.StaticObjects.Stalactite;
import de.mjkd.platformer.Level.StaticObjects.Stalagmite;
import de.mjkd.platformer.Level.StaticObjects.Stone;
import de.mjkd.platformer.Level.StaticObjects.Teleport;
import de.mjkd.platformer.Level.StaticObjects.Tree;
import de.mjkd.platformer.Level.StaticObjects.Tree2;

/**
 * Created by reaste on 26.08.17.
 */

public class LevelManager {

    private String level;

    public int mapWidth;
    public int mapHeight;

    public Player player;
    public int playerIndex;

    private boolean playing;
    public float gravity;

    public LevelData levelData;
    public ArrayList<GameObject> gameObjects;
    public ArrayList<Background> backgrounds;

    public ArrayList<Rect> currentButtons;
    public Bitmap[] bitmapsArray;

    public LevelManager(Context context, int pixelsPerMetre, int screenWidth, InputController ic, String level, float px, float py) {
        this.level = level;

        switch (level) {
            case "LevelCave":
                levelData = new LevelCave();
                break;
            case "LevelCity":
                levelData = new LevelCity();
                break;
            case "LevelForest":
                levelData = new LevelForest();
                break;
            case "LevelMountain":
                levelData = new LevelMountain();
                break;
        }

        gameObjects = new ArrayList<>();

        bitmapsArray = new Bitmap[25];

        loadMapData(context, pixelsPerMetre, px, py);
        loadBackgrounds(context, pixelsPerMetre, screenWidth);

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
            case 'f':
                index = 8;
                break;
            case '2':
                index = 9;
                break;
            case '3':
                index = 10;
                break;
            case '4':
                index = 11;
                break;
            case '5':
                index = 12;
                break;
            case '6':
                index = 13;
                break;
            case '7':
                index = 14;
                break;
            case 'w':
                index = 15;
                break;
            case 'x':
                index = 16;
                break;
            case 'l':
                index = 17;
                break;
            case 'r':
                index = 18;
                break;
            case 's':
                index = 19;
                break;
            case 'm':
                index = 20;
                break;
            case 'z':
                index = 21;
                break;
            case 't':
                index = 22;
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
            case 'f':
                index = 8;
                break;
            case '2':
                index = 9;
                break;
            case '3':
                index = 10;
                break;
            case '4':
                index = 11;
                break;
            case '5':
                index = 12;
                break;
            case '6':
                index = 13;
                break;
            case '7':
                index = 14;
                break;
            case 'w':
                index = 15;
                break;
            case 'x':
                index = 16;
                break;
            case 'l':
                index = 17;
                break;
            case 'r':
                index = 18;
                break;
            case 's':
                index = 19;
                break;
            case 'm':
                index = 20;
                break;
            case 'z':
                index = 21;
                break;
            case 't':
                index = 22;
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
        int teleportIndex = -1;

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
                        case 'f':
                            gameObjects.add(new Fire(context, j,i,c,pixelsPerMetre));
                            break;
                        case '2':
                            // Add a tile to the gameObjects
                            gameObjects.add(new Snow(j, i, c));
                            break;
                        case '3':
                            // Add a tile to the gameObjects
                            gameObjects.add(new Brick(j, i, c));
                            break;
                        case '4':
                            // Add a tile to the gameObjects
                            gameObjects.add(new Coal(j, i, c));
                            break;
                        case '5':
                            // Add a tile to the gameObjects
                            gameObjects.add(new Concrete(j, i, c));
                            break;
                        case '6':
                            // Add a tile to the gameObjects
                            gameObjects.add(new Scorched(j, i, c));
                            break;
                        case '7':
                            // Add a tile to the gameObjects
                            gameObjects.add(new Stone(j, i, c));
                            break;
                        case 'w':
                            // Add a tree to the gameObjects
                            gameObjects.add(new Tree(j, i, c));
                            break;
                        case 'x':
                            // Add a tree2 to the gameObjects
                            gameObjects.add(new Tree2(j, i, c));
                            break;
                        case 'l':
                            // Add a tree to the gameObjects
                            gameObjects.add(new Lampost(j, i, c));
                            break;
                        case 'r':
                            // Add a stalactite to the gameObjects
                            gameObjects.add(new Stalactite(j, i, c));
                            break;
                        case 's':
                            // Add a stalagmite to the gameObjects
                            gameObjects.add(new Stalagmite(j, i, c));
                            break;
                        case 'm':
                            // Add a cart to the gameObjects
                            gameObjects.add(new Cart(j, i, c));
                            break;
                        case 'z':
                            // Add a boulders to the gameObjects
                            gameObjects.add(new Boulders(j, i, c));
                            break;
                        case 't':
                            // Add a teleport to the gameObjects
                            teleportIndex++;
                            gameObjects.add(new Teleport(j,i,c,levelData.locations.get(teleportIndex)));
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

    private void loadBackgrounds(Context context, int pixelsPerMetre, int screenWidth) {
        backgrounds = new ArrayList<>();
        for (BackgroundData bgData : levelData.backgroundDataList) {
            backgrounds.add(new Background(context, pixelsPerMetre, screenWidth, bgData));
        }
    }
}
