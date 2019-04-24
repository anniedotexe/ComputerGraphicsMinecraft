/**
 * File:            CameraController.java 
 * Team:            Depressed Dinos
 * Author:          Annie Wu, Aatena Hasan 
 * Class:           CS 4450 - Computer Graphics
 *                  
 * Assignment:      Final Program 
 * Date:            24 April 2019 
 *                  
 * Purpose:         3D vector to store camera position.
 *                  
 */

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;

public class CameraController {
    
    private Vector3f position = null;   // 3D vector to store the camera's position in
    private Vector3f lPosition = null;
    private float yaw = 0.0f;           // rotation around Y axis of camera
    private float pitch = 0.0f;         // rotation around X axis of camera
    private Camera me;
    private Chunk chunk;
        
    /**
     * Constructor: CameraController
     * Purpose: Initialize variables of camera 
     * @param x
     * @param y
     * @param z 
     */
    public CameraController(float x, float y, float z) {
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(0f, 0f, 0f);
	chunk = new Chunk(0, 0, 0);      
    }
    
    /**
     * Method: yaw
     * Purpose: Increment current yaw rotation by amount
    * @param amount
    */
    public void yaw (float amount) {
        yaw += amount;
    }
    
    /**
     * Method: pitch 
     * Purpose: increment pitch by amount
     * @param amount 
     */
    public void pitch (float amount) {
        pitch -= amount;
        
        // Stop user from turning camera upside down
        if (pitch < -90) {
            pitch = -90;
        }
        if (pitch > 90) {
            pitch = 90;
        }
    }
    
    /**
     * Method: walkForward 
     * Purpose: Move camera forward relative to current rotation (yaw) 
     * @param distance 
     */
    public void walkForward (float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
        
        //FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        //lightPosition.put(lPosition.x-=xOffset).put(lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
        //glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
        
    /**
     * Method: walkBackward 
     * Purpose: Move camera backward relative to current rotation (yaw) 
     * @param distance 
     */
    public void walkBackwards (float distance) {
        float xOffset= distance * (float) Math.sin(Math.toRadians(yaw));
        float zOffset= distance * (float) Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
        
        //FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        //lightPosition.put(lPosition.x+=xOffset).put(lPosition.y).put(lPosition.z-=zOffset).put(1.0f).flip();
        //glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    /**
     * Method: strafeLeft
     * Purpose: Strafe camera to the left relative to current rotation (yaw)
     * @param distance 
     */
    public void strafeLeft(float distance) {
        float xOffset= distance * (float) Math.sin(Math.toRadians(yaw - 90));
        float zOffset= distance * (float) Math.cos(Math.toRadians(yaw - 90));
        position.x -= xOffset;
        position.z += zOffset;
        
        //FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        //lightPosition.put(lPosition.x-=xOffset).put(lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
        //glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    /**
     * Method: strafeRight
     * Purpose: Strafe camera to the right relative to current rotation (yaw)
     * @param distance 
     */
    public void strafeRight(float distance) {
        float xOffset= distance * (float)Math.sin(Math.toRadians(yaw + 90));
        float zOffset= distance * (float)Math.cos(Math.toRadians(yaw + 90));
        position.x -= xOffset;
        position.z += zOffset;
        
        //FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        //lightPosition.put(lPosition.x-=xOffset).put(lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
        //glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }

    /**
     * Method: moveUp
     * Purpose: Move camera up
     * @param distance 
     */
    public void moveUp(float distance) {
        position.y -= distance;
    }

    /**
     * Method: moveDown
     * Purpose: Move camera down
     * @param distance 
     */
    public void moveDown(float distance) {
        position.y += distance;
    }
    
    /**
     * Method: lookThrough
     * Purpose: This does basically what gluLookAt() does
     */
    public void lookThrough() {
        //roatate the pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //roatate the yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's chunk
        glTranslatef(position.x, position.y, position.z);
        
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x + 35).put(lPosition.y).put(lPosition.z + 35).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    /*
    Method: gameLoop
    Purpose: Main loop for running the program 
    */
    public void gameLoop() {

        CameraController cam = new CameraController(-15, 0, 20);

        float dx = 0.0f;
        float dy= 0.0f;
        float dt= 0.0f;                 // length of frame
        float lastTime= 0.0f;           // when the last frame was
        long time = 0;                  // current time
        float mouseSensitivity= 0.09f;  // how fast you look around
        float movementSpeed= .35f;      // how fast you move 
        cam.yaw(-90);
        
        //hide the mouse
        Mouse.setGrabbed(true);
        
        //keep looping till the display window is closed or the ESC key is down 
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            time = Sys.getTime();
            lastTime = time;
            
            
            dx = Mouse.getDX(); // distance in mouse movement from the last getDX() call.
            dy = Mouse.getDY(); // distance in mouse movement from the last getDY() call.
            
            cam.yaw(dx * mouseSensitivity);      // Updates the yaw with the new position of the mouse
            cam.pitch(dy * mouseSensitivity);    // Updates the pitch with the new postion of the mouse
            
            if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {    // forward = up arrow or W
                cam.walkForward(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {  // backwards = down arrow or S
                cam.walkBackwards(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {  // left = left arrow or A
                cam.strafeLeft(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) { // right = right arrow or D
                cam.strafeRight(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {   // up = space
                cam.moveUp(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {  // down = left shift
                cam.moveDown(movementSpeed);
            }
            
            glLoadIdentity();   // set the modelview matrix back to the identity 
            cam.lookThrough();  // look through the camera before you draw anything 
            glEnable(GL_DEPTH_TEST);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            chunk.render(); //render();
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }
    
    /*
    Method: render
    Purpose: Render what we want to draw 
    */
    private void render() {
        try {
            glBegin(GL_QUADS);
                glColor3f(1.0f,0.0f,1.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f); 
            glEnd();
        } catch(Exception e) {
        }
    }
}
