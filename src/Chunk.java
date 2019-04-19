/**
 * File:            Chunk.java 
 * Team:            Depressed Dinos
 * Author:          Annie Wu, Aatena Hasan 
 * Class:           CS 4450 - Computer Graphics
 *                  
 * Assignment:      Final Program 
 * Date:            18 April 2019 
 *                  
 * Purpose:         Create chunks of blocks.
 *                  
 */

import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Chunk {
    
    static final int CHUNK_SIZE = 30;
    static final int CUBE_LENGTH = 2;
    private Block[][][] Blocks;
    private int VBOVertexHandle;
    private int VBOColorHandle;
    private int StartX, StartY, StartZ;
    private Random r;
    
    
    /**
     * Constructor: Chunk 
     * Purpose: Create a chunk of blocks and randomly add textures to each cube 
     * @param startX
     * @param startY
     * @param startZ 
     */
    public Chunk(int startX, int startY, int startZ) {
        r = new Random();
        Blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        
            for(int x = 0; x < CHUNK_SIZE; x++) {
                for(int y = 0; y < CHUNK_SIZE; y++) {
                    for(int z = 0; z < CHUNK_SIZE; z++) {
                       float n =  r.nextFloat();
                        if(n >  0.7f) {
                            Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Grass);
                        }
                        else if(n > 0.4f) {
                            Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Dirt);

                        }
                        else if(n > 0.2f) {
                            Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Water);
                        }
                        else {
                            Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Default);
                        }
                    }
                }
            }
            
            VBOColorHandle = glGenBuffers();
            VBOVertexHandle = glGenBuffers();
            StartX = startX;
            StartY = startY;
            StartZ = startZ;
            
            rebuildMesh(startX, startY, startZ);
    }


    /**
     * Method: render
     * Purpose: Render the graphics
     */
    public void render() {
        glPushMatrix();
            glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
            glVertexPointer(3, GL_FLOAT, 0, 0L);
            glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
            glColorPointer(3, GL_FLOAT, 0, 0L);
            glDrawArrays(GL_QUADS, 0, CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 24);
        glPopMatrix();
            
    }
    
    /**
     * Method: rebuildMesh 
     * Purpose: Create hills and valleys (variations in terrain) with SimplexNoise
     * @param startX
     * @param startY
     * @param startZ 
     */
    public void rebuildMesh(float startX, float startY, float startZ) {
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        
        for(float x = 0; x < CHUNK_SIZE; x+=1) {
            for(float z = 0; z < CHUNK_SIZE; z+=1) {
                for(float y = 0; y < CHUNK_SIZE; y++) {
                    VertexPositionData.put(createCube((float)(startX + x * CUBE_LENGTH),
                                                      (float)(y*CUBE_LENGTH + (int)(CHUNK_SIZE * 0.8)),
                                                      (float)(startZ + z * CUBE_LENGTH)));
                    
                    VertexColorData.put(createCubeVertexCol(getCubeColor(Blocks[(int)x][(int)y][(int)z])));
                    
                    float[] array = getCubeColor(Blocks[(int)x][(int)y][(int)z]);
                    
                    for(int i = 0; i < array.length; i++) {
                        System.out.println(array[i] + " ");
                    }
                }
            }
        }
        
        VertexColorData.flip();
        VertexPositionData.flip();
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexPositionData, GL_STATIC_DRAW);
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexColorData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
    }
    
    /**
     * Method: createCubeVertexCol 
     * Purpose: Create cube vertex col
     * @param CubeColorArray
     * @return 
     */
    private float[] createCubeVertexCol(float[] CubeColorArray) {
        float[] cubeColors = new float[CubeColorArray.length * 4* 6];
        for(int i = 0; i < cubeColors.length; i++) {
            cubeColors[i] = CubeColorArray[i % CubeColorArray.length];
        }
        return cubeColors;
    }
    
    /**
     * Method: createCube 
     * Purpose: Create a cube 
     * @param x
     * @param y
     * @param z
     * @return 
     */
    public static float[] createCube(float x, float y, float z) {
        int offset = CUBE_LENGTH / 2;
        return new float[] {
            //TOP QUAD
            x + offset, y + offset, z, 
            x - offset, y + offset, z, 
            z - offset, y + offset, z - CUBE_LENGTH, 
            x + offset, y + offset, z - CUBE_LENGTH,
            //BOTTOM QUAD
            x + offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z, 
            x + offset, y - offset, z,
            //FRONT QUAD
            x + offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z - CUBE_LENGTH, 
            x - offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,
            //BACK QUAD
            x + offset, y - offset, z,
            x - offset, y - offset, z, 
            x - offset, y + offset, z,
            x + offset, y + offset, z,
            //LEFT QUAD
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z,
            x - offset, y - offset, z,
            x - offset, y - offset, z - CUBE_LENGTH,
            //RIGHT QUAD
            x + offset, y + offset, z, 
            x + offset, y + offset, z - CUBE_LENGTH, 
            x + offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z
        };
                   
    }
    
    /**
     * Method: getCubeColor 
     * Purpose: Get the cube color 
     * @param block
     * @return 
     */
    private float[] getCubeColor(Block block) {
        switch(block.getID()) {
            case 1:
                return new float[] { 0, 1, 0 };
            case 2:
                return new float[] { 1, 0.5f, 0};
            case 3:
                return new float[] {0, 0f, 1f};
        }
         
        return new float[] {1, 1, 1};
                
    }

}
