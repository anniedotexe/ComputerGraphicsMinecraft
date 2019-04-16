/**
 * File:            CameraController.java 
 * Team:            Depressed Dinos
 * Author:          Annie Wu, Aatena Hasan 
 * Class:           CS 4450 - Computer Graphics
 *                  
 * Assignment:      Final Program 
 * Date:            8 March 2019
 *                  
 * Purpose:         Camera Controller.
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
    private Camera position = null;
    private Camera lPosition = null;
    
    //rotation around Y axis of camera
    private float yaw = 0.0f;
    
    //rotation around X axis of camera
    private float pitch = 0.0f;
    
    private Chunk location;
    
    private Camera me;
    
    public CameraController (float x, float y, float z) {
        
       // location = new Chunk((int)x, (int)y, (int)z);

        //instantiate position of Camera to x y z parameters
        position = new Camera(x, y, z);
        lPosition = new Camera(x, y, z);
        lPosition.x = 0f;
        lPosition.y = 15f;
        lPosition.z = 0f;
       
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
    Method: 
    Purpose: 
    */
    public void gameLoop() {
        location = new Chunk(0, 0, 0);
        CameraController cam = new CameraController(0, 0, 0);
        
        float dx = 0.0f;
        float dy= 0.0f;
        float dt= 0.0f; //length of frame
        float lastTime= 0.0f; // when the last frame was
        long time = 0;
        float mouseSensitivity= 0.09f;
        float movementSpeed= .35f;
        
               
        //hide the mouse
        Mouse.setGrabbed(true);
        
        //keep looping till the display window is closed the ESC key is down 
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            time = Sys.getTime();
            lastTime = time;
            
            //distance in mouse movement //from the last getDX() call.
            dx = Mouse.getDX();
            
            //distance in mouse movement //from the last getDY() call.
            dy = Mouse.getDY();
            
            //when passing in the distance to move
            //we times the movementSpeed with this is a time scale
            //so if its a slow frame u move more then a fast frame
            //so on a slow computer you move just as fast as on a fast computer
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
            
            location.render(); 
            //draw the buffer to the screen
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }
    
    /*
    Method: render
    Purpose: render what we want to draw 
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
