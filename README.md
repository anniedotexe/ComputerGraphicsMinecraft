# Computer Graphics Minecraft Project
This is a Minecraft world that is created with Java and a lightweight graphics library called OpenGL. <br>

## Blocks
There are 6 types of blocks that are randomly generated:
  * Grass
  * Sand
  * Water
  * Dirt  
  * Stone
  * Bedrock

### Terrain
The top layer must be either grass, sand, or water. <br>
The very bottom layer must be bedrock. <br>

The varying height levels of the terrain is created by a noise generator that randomizes the heights based on persistance (amount of variation). <br>

Our implementation makes sure that bodies of water are generated being surrounded by sand, instead of having randomly placed grass, sand, and water blocks on the top level of the terrain.

## Camera
The camera is the user's point of view of the world. The camera position is stored in a 3D vector. <br>

Camera movements:
  * Yaw - rotate camera around y-axis 
  * Pitch - rotate camera around x-axis
  * Walk forward - move camera relative to current rotation (yaw)
  * Walk backward - move camera relative to current rotation (yaw)
  * Strafe left - strafe camera left relative to current rotation (yaw)
  * Strafe right - strafe camera right relative to current rotation (yaw)
  * Ascend - move camera's y position up
  * Descend - move camera's y position down
  
### Key Bindings 
  * W / Up Arrow - FORWARD
  * A / Left Arrow - LEFT
  * S / Down Arrow - BACKWARD
  * D / Right Arrow - RIGHT
  * Spacebar - UP
  * Left Shift - DOWN
  * R - Reset (generate new) terrain

### Light Source
The light source moves opposite of the camera movement. <br>
  i.e moving towards the light will illuminate more of the terrain

