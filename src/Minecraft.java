/**
 * File:            Minecraft.java 
 * Team:            Depressed Dinos
 * Author:          Annie Wu, Aatena Hasan 
 * Class:           CS 4450 - Computer Graphics
 *                  
 * Assignment:      Final Program 
 * Date:            8 March 2019
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

public class Minecraft {
    
    private DisplayMode displayMode;

    /*
    Method: start
    Purpose: run the methods we need to draw with OpenGL
    */
    public void start() {
        //initialize our instance of our camera controller inside start method
        
        try {
            CameraController cam = new CameraController(0f,0f,0f);
            createWindow();
            initGL();
           
            cam.gameLoop();//render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
    Method: createWindow
    Purpose: create a 640x840 window
    */
    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (int i= 0; i< d.length; i++) {
            if (d[i].getWidth() == 640 && d[i].getHeight() == 480 && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }
        Display.setDisplayMode(displayMode); 
        Display.setTitle("Minecraft");
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
        
        //Chunk part
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);
    }
    
    public static void main(String[] args) {
        Minecraft game = new Minecraft();
        game.start();
    }
    
}
