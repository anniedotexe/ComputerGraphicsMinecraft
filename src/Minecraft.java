/**
 * File:            Minecraft.java 
 * Team:            Depressed Dinos
 * Author:          Annie Wu, Aatena Hasan 
 * Class:           CS 4450 - Computer Graphics
 *                  
 * Assignment:      Final Program 
 * Date:            18 April 2019 
 *                  
 * Purpose:         Create a window and initialize the graphics. 
 *                  
 */

import java.io.*;
import java.util.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class Minecraft {
    
    private DisplayMode displayMode;
    private CameraController cam;
    public FloatBuffer lightPosition;
    public FloatBuffer whiteLight;

    /**
     * Method: start
     * Purpose: Run the methods we need to draw with OpenGL
     */
    public void start() {
        
        //initialize our instance of our camera controller inside start method
        try {
            createWindow();
            initGL();
            cam = new CameraController(0f, 0f, 0f);
            cam.gameLoop(); //render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Method: createWindow
     * Purpose: Create a 640x840 window
     */
    private void createWindow() throws Exception {
        Display.setFullscreen(false);   // Not fullscreen
        DisplayMode d[] = Display.getAvailableDisplayModes();
        
        for (int i = 0; i < d.length; i++) {
            if(d[i].getWidth() == 640 && d[i].getHeight() == 480 && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;  
            }
        }
        
        Display.setDisplayMode(displayMode); 
        Display.setTitle("DEPRESSED DINOS DEPRESSED DINOS");  
        Display.create();  
    }
    
    /**
     * Method: initGL
     * Purpose: Initialize the graphics
     */
    private void initGL() {
        
        glEnable(GL_TEXTURE_2D);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);
        
        glClearColor(0.6f, 0.8f, 1.0f, 0.0f);           // sky blue color
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        GLU.gluPerspective(100.0f, (float)displayMode.getWidth()/(float)displayMode.getHeight(), 0.1f, 300.0f);

        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glEnable(GL_DEPTH_TEST);                        // hide the hidden faces of objects
        
        initLightArrays();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition); // sets light’s position
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);    // sets specular light
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);     // sets diffuse light
        glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);     // sets ambient light
        glEnable(GL_LIGHTING);                          // enables lighting
        glEnable(GL_LIGHT0);                            // enables light0
    }
    
    /**
     * Method: initiLightArrays
     * Purpose: Initialize light arrays 
     */
    private void initLightArrays() {
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(0).put(0).put(0).put(1.0f).flip();
        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();
    }
    
    public static void main(String[] args) {
        Minecraft game = new Minecraft();
        game.start();
    }
    
}
