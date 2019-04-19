/**
 * File:            Block.java 
 * Team:            Depressed Dinos
 * Author:          Annie Wu, Aatena Hasan 
 * Class:           CS 4450 - Computer Graphics
 *                  
 * Assignment:      Final Program 
 * Date:            18 April 2019 
 *                  
 * Purpose:         Block Information. 
 *                  
 */

public class Block {
    
    private boolean isActive;
    private BlockType Type;
    private float x, y, z;

    public enum BlockType {
        BlockType_Grass(0),
        BlockType_Sand(1),
        BlockType_Water(2),
        BlockType_Dirt(3),
        BlockType_Stone(4),
        BlockType_Bedrock(5),
        BlockType_Default(6);
           
        private int BlockID;   
        
        BlockType(int i) {
            BlockID = i;
        }

        public int getID() {
            return BlockID;
        }

        public void setID(int i) {
            BlockID = i;
        }
    }
    
    /**
     * Constructor: Block 
     * Purpose: Create a block of this type 
     * @param type 
     */
    public Block(BlockType type) {
        Type = type;
        isActive = false;
    }

    /**
     * Method: setCoords
     * Purpose: Set coordinates of the block
     * @param x
     * @param y
     * @param z 
     */
    public void setCoords(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Method: isActive
     * Purpose: Return true if the block is active 
     * @return 
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Method: setActive
     * Purpose: Set a block to be active or not active
     * @param active 
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Method: getID
     * Purpose: Get the ID of a block
     * @return 
     */
    public int getID() {
        return Type.getID();
    }

    /**
     * Method: setID
     * Purpose: Set the ID of a block
     * @param type 
     */
    public void setID(BlockType type) {
        Type = type;
    }
    
}