/* 
 * File:            CameraController.java  
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
 * Purpose:         Floating Point Camera Controller for movement of the camera. 
 *                  
 */

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;


public class CameraController {
    
    //3D vector to store camera position
    private Vector3f position = null;
    private Vector3f rotation = null; //lposition 
    
    //rotation around Y axis of camera
    private float yaw = 0.0f;
    
    //rotation around X axis of camera
    private float pitch = 0.0f;
    
    private Camera me;
    
    public CameraController (float x, float y, float z) {
        //instantiate position of Camera to x y z parameters
        position = new Vector3f(x, y, z);
        rotation = new Vector3f(x, y, z);
        rotation.x = 0f;
        rotation.y = 15f;
        rotation.z = 0f;
    }
    
    /*
    Method: yaw 
    Purpose: increment current yaw rotation by amount
    */
    public void yaw (float amount) {
        yaw += amount;
    }
    
    /*
    Method: pitch 
    Purpose: increment pitch by amount
    */
    public void pitch (float amount) {
        pitch -= amount;
    }
    
    /*
    Method: walkForward 
    Purpose: move camera forward relative to current rotation (yaw) 
    */
    public void walkForward (float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset= distance * (float)Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
    }
    
    /*
    Method: walkBackward 
    Purpose: move camera backward relative to current rotation (yaw) 
    */
    public void walkBackwards (float distance) {
        float xOffset= distance * (float) Math.sin(Math.toRadians(yaw));
        float zOffset= distance * (float) Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
    }
    
    /*
    Method: strafeLeft
    Purpose: strafe camera to the left relative to current rotation (yaw)
    */
    public void strafeLeft(float distance) {
        float xOffset= distance * (float) Math.sin(Math.toRadians(yaw - 90));
        float zOffset= distance * (float) Math.cos(Math.toRadians(yaw - 90));
        position.x -= xOffset;
        position.z += zOffset;
    }
    
    /*
    Method: strafeRight
    Purpose: strafe camera to the right relative to current rotation (yaw)
    */
    public void strafeRight(float distance) {
        float xOffset= distance * (float)Math.sin(Math.toRadians(yaw+90));
        float zOffset= distance * (float)Math.cos(Math.toRadians(yaw+90));
        position.x -= xOffset;
        position.z += zOffset;
    }
    
    /*
    Method: moveUp
    Purpose: move camera up
    */
    public void moveUp(float distance) {
        position.y -= distance;
    }
    
    /*
    Method: moveDown
    Purpose: move camera down
    */
    public void moveDown(float distance) {
        position.y += distance;
    }
    
    /*
    Method: lookThrough
    Purpose: this does basically what gluLookAt() does
    */
    public void lookThrough() {
        //roatate the pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //roatate the yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef(position.x, position.y, position.z);
    }
    
    /*
    Method: gameLoop
    Purpose: 
    */
    public void gameLoop() {
        CameraController cam = new CameraController(0, 0, 0);
        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f; //length of frame
        float lastTime = 0.0f; // when the last frame was
        long time = 0;
        float mouseSensitivity = 0.09f;
        float movementSpeed = .35f;
        
        Mouse.setGrabbed(true);
        
        while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            CameraController camera = new CameraController(0, 0, -10);
            time = Sys.getTime();
            lastTime = time;
            
            //distance in mouse movement from the last getDX() call.
            dx = Mouse.getDX();
            
            //distance in mouse movement from the last getDY() call.
            dy = Mouse.getDY();
            
            camera.yaw(dx + mouseSensitivity);
            camera.pitch(dy + mouseSensitivity);
            
            if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) { //forward = up arrow or W
                cam.walkForward(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) { //backwards = down arrow or S
                cam.walkBackwards(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) { //left = left arrow or A
                cam.strafeLeft(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) { //right = right arrow or D
                cam.strafeRight(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) { //up = space
                cam.moveUp(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) { //down = left shift
                cam.moveDown(movementSpeed);
            }
            
            glLoadIdentity();
            //look through the camera before you draw anything
            cam.lookThrough();
            
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            render();
            //draw the buffer to the screen
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }
    
    /*
    Method: render
    Purpose: render what we want to draw (cube) 
    */
    private void render() {
        try {
            
            glEnable(GL_DEPTH_TEST);
            
            glBegin(GL_QUADS);
            
            //Top 
            glColor3f(1.0f, 0.6f, 0.33f); //peach 
            glVertex3f(1.0f, 1.0f, -1.0f);
            glVertex3f(-1.0f, 1.0f, -1.0f);
            glVertex3f(-1.0f, 1.0f, 1.0f);
            glVertex3f(1.0f, 1.0f, 1.0f);
            
            //Bottom
            glColor3f(0.99f, 0.86f, 0.23f); //pineapple 
            glVertex3f(1.0f, -1.0f, 1.0f);
            glVertex3f(-1.0f, -1.0f, 1.0f);
            glVertex3f(-1.0f, -1.0f, -1.0f);
            glVertex3f(1.0f, -1.0f, -1.0f);
            
            //Front
            glColor3f(0.0f, 0.7f, 0.93f); //sky blue 
            glVertex3f(1.0f, 1.0f, 1.0f);
            glVertex3f(-1.0f, 1.0f, 1.0f);
            glVertex3f(-1.0f, -1.0f, 1.0f);
            glVertex3f(1.0f, -1.0f, 1.0f);
            
            //Back
            glColor3f(0.73f, 0.83f, 0.93f); //slate gray 
            glVertex3f(1.0f, -1.0f, -1.0f);
            glVertex3f(-1.0f, -1.0f, -1.0f);
            glVertex3f(-1.0f, 1.0f, -1.0f);
            glVertex3f(1.0f, 1.0f, -1.0f);
            
            //Left
            glColor3f(0.97f, 0.70f, 0.85f); //cotton candy 
            glVertex3f(-1.0f, 1.0f, 1.0f);
            glVertex3f(-1.0f, 1.0f, -1.0f);
            glVertex3f(-1.0f, -1.0f, -1.0f);
            glVertex3f(-1.0f, -1.0f, 1.0f);
            
            //Right
            glColor3f(0.8f, 0.41f, 0.8f); //orchid   
            glVertex3f(1.0f, 1.0f, -1.0f);
            glVertex3f(1.0f, 1.0f, 1.0f);
            glVertex3f(1.0f, -1.0f, 1.0f);
            glVertex3f(1.0f, -1.0f, -1.0f);
            
            glEnd();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
