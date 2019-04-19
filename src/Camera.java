/**
 * File:            Camera.java 
 * Team:            Depressed Dinos
 * Author:          Annie Wu, Aatena Hasan 
 * Class:           CS 4450 - Computer Graphics
 *                  
 * Assignment:      Final Program 
 * Date:            18 April 2019 
 *                  
 * Purpose:         A 3D vector to hold the camera position.
 *                  
 */

public class Camera {
    
    public float x, y, z;
    
    /**
     * Constructor: Camera
     * Purpose: Initialize Vector3Float variables
     * @param x
     * @param y
     * @param z 
     */
    public Camera (float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
