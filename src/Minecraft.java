/* 
 * File:            Minecraft.java  
 *
 * Team:            Depressed Dinos  
 * Members:         Annie Wu 
 *                  Aatena Hasan  
 * 
 * Class:           CS 4450 - Computer Graphics 
 *                  
 * Assignment:      Final Program 
 * Date:            21 March 2019 
 *                  
 * Purpose:         Main class. Create a window, initialize GL, and render the graphics. 
 *                  
 */

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

public class Minecraft {
    
    private CameraController camera;
    private DisplayMode displayMode;

    /*
    Method: start
    Purpose: run the methods we need to draw with OpenGL
    */
    public void start() {
        try {
            camera = new CameraController(0.0f, 0.0f, 0.0f);
            createWindow();
            initGL();
            camera.gameLoop(); //render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
    Method: createWindow
    Purpose: create a 640x840 window
    */
    private void createWindow() throws Exception {
        Display.setFullscreen(false); //windowed 
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (int i= 0; i< d.length; i++) {
            if (d[i].getWidth() == 640 && d[i].getHeight() == 480 && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }
        Display.setDisplayMode(displayMode); 
        Display.setTitle(" ~ Depressed Dinos ~ ");
        Display.create();
    }
    
    /*
    Method: initGL
    Purpose: initialize the graphics
    */
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100.0f, (float) displayMode.getWidth() / (float) displayMode.getHeight(), 0.1f, 300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    public static void main(String[] args) {
        Minecraft game = new Minecraft();
        game.start();
    }
    
}
