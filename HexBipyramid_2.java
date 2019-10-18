/* ------------------------------------------------------------------------------------
Paige Weber, L22812475
Dr. Crawley, Computer Graphics
10/17/19
----------------------------------------------------------------------------------------
This program uses the Java libraries swing, awt and openGL to display a 3D hexagonal 
bipyramid. Each panel of the shape appears a slightly different color due to 
the use of lighting and normal vectors.  To adjust the rotation see lines 52-57.
------------------------------------------------------------------------------------- */

import java.awt.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;


public class HexBipyramid_2 extends GLJPanel implements GLEventListener{


	// this function returns the normal vector of a plane defined by 3 points in a list
	public static double[] findNormalVector(double list[][])
	{
		// distinguish different points in the list
		double[] vector1 = list[0];
		double[] vector2 = list[1];
		double[] vector3 = list[2];
		
		// subtract the x, y, and z coordinates of the first and second
		double[] vectorA = {vector1[0] - vector2[0], 
							-1 * (vector1[1] - vector2[1]), 
							vector1[2] - vector2[2]};
							
		// subtract the x, y, and z coordinates of the third and second					
		double[] vectorB = {vector3[0] - vector2[0], 
							-1 * (vector3[1] - vector2[1]), 
							vector3[2] - vector2[2]};
	
		// based off of linear algebra method that uses matrices
		double xVal = (vectorA[1] * vectorB[2]) - (vectorA[2] * vectorB[1]);
		double yVal = (vectorA[0] * vectorB[2]) - (vectorA[2] * vectorB[0]);
		double zVal = (vectorA[0] * vectorB[1]) - (vectorA[1] * vectorB[0]);
	
		// define new vector based on findings
		double newVector[] = {xVal, yVal, zVal};
		
		return newVector;
	}
    
    public float rotate; 
    
    public static void main(String[] args) {
        JFrame window = new JFrame("Hexagonal Bipyramid");
        HexBipyramid panel = new HexBipyramid();
        window.setContentPane(panel);
        window.pack();
        window.setLocation(50,50);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
    
    public HexBipyramid() {
        super( new GLCapabilities(null) ); // Makes a panel with default OpenGL "capabilities".
        setPreferredSize( new Dimension(500,500) );
        addGLEventListener(this); // A listener is essential! The listener is where the OpenGL programming lives.
    }
    
    public void display(GLAutoDrawable drawable) {    
       
        GL2 gl = drawable.getGL().getGL2();
        
        // Clear The Screen And The Depth Buffer
        gl.glClear (GL2.GL_COLOR_BUFFER_BIT |  GL2.GL_DEPTH_BUFFER_BIT );
        
        gl.glLoadIdentity();  // Reset The View 

        gl.glEnable(GL4.GL_DEPTH_TEST); // NEED THIS FOR IT TO DISPLAY CORRECTLY
          
//-------------------------------------------------------------------------------
      	// CHANGE ANGLE OF ROTATION HERE
      	// First number is angle of rotation in degrees the following are x,
      	// y, z values
      	gl.glRotatef(65 , 1, 0, 0); 
//-------------------------------------------------------------------------------
        
        double[][] vertexList = { //hexagon
        						{0,-1, 0}, {.866, -.5, 0}, {.866, .5, 0},
        						{0, 1, 0}, {-.866, .5,0},{-.866, -.5,0}, 
        						
        						// upper point, lower point
        						{0,0,1}, {0,0,-1}}; 

		int[][] faceList =  {{0,1,6}, {0,1,7}, // upper, then lower
							 {1,2,6}, {1,2,7}, 
        					 {2,3,6}, {2,3,7}, 
        					 {3,4,6}, {3,4,7}, 
        					 {4,5,6}, {4,5,7}, 
        					 {5,0,6}, {5,0,7}};
	

		gl.glEnable(GL2.GL_LIGHTING); // turn lighting on
		
        float[] lightPos = {2, -2, 20, 0};
        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f}; // very dim
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f}; 
        float[] lightColorDiffuse = {1f, 0f, 1f, 0f}; // pink

        // Set light
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, lightColorDiffuse, 0);

        // declare light
        gl.glEnable(GL2.GL_LIGHT1);

        // Set material
        float[] rgba = {0f, 0f, 0f}; // black
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_DIFFUSE, 0);
		
		
		for (int i = 0; i < faceList.length; i++) {
			gl.glColor3d( 0, 0, 0); // Set color for face number i. 
			
			// user function to find normal vector
			gl.glNormal3dv(findNormalVector[faceList[i]]); 
			
			gl.glBegin(GL2.GL_TRIANGLE_FAN); 		
			for (int j = 0; j < faceList[i].length; j++) {
				int vertexNum = faceList[i][j]; // Index for vertex j of face i.
				double[] vertexCoords = vertexList[vertexNum]; // The vertex itself.
        		
				gl.glVertex3dv( vertexCoords, 0 );
			}
			gl.glEnd(); /* !!!! MUST be in this position. Outside of the 
							brackets will cause rendering error.*/
		} 
        
        
    } // end display()

    public void init(GLAutoDrawable drawable) {
           // called when the panel is created
           
    }

    public void dispose(GLAutoDrawable drawable) {
            // called when the panel is being disposed
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            // called when user resizes the window
    }
    
}