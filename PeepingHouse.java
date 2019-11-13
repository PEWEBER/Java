/* ------------------------------------------------------------------------------------
Paige Weber, L22812475
Dr. Crawley, Computer Graphics
11/13/19
----------------------------------------------------------------------------------------
This program uses the Java libraries swing, awt and openGL to display a 3D house with
open windows at night time. Through the window you can see a table with a 
hexagonal bipyramid on top. Dr. Eck's camera function is used so the user can rotate
the objects with their mouse.
------------------------------------------------------------------------------------- */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import com.jogamp.opengl.awt.GLJPanel;

import javax.swing.*;
import com.jogamp.opengl.*;

import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;



public class PeepingHouse extends GLJPanel implements GLEventListener{
    
    public Camera camera;

    private int currentObject;  // Code for the object  displayed in the panel.

    
    public static void main(String[] args) {
        JFrame window = new JFrame("Peeping House");
        PeepingHouse panel = new PeepingHouse();
        window.setContentPane(panel);
        window.pack();
        window.setLocation(50,50);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
    
    public PeepingHouse() {
        super( new GLCapabilities(null) ); // Makes a panel with default OpenGL "capabilities".
        setPreferredSize( new Dimension(500,500) );
        addGLEventListener(this); // A listener is essential! The listener is where the OpenGL programming lives.
    }
    // HOUSE DRAWING FUNCTION ----------------------------------------
    public void drawHouse(GL2 gl){
          
        double[][] vertexList = {{0, .25, .25}, {0, .25, 0}, {0, .20, 0}, {0, .20, .25},
        						{0, .2, .2}, {0, .1, .2}, {0, .1, .25},
        						{0, .1, 0}, {0, -.1, 0}, {0, -.1, .25},
        						{0, -.1, .2}, {0, -.2, .2}, {0, -.2, .25},
        						{0, .2, .1}, {0, .1, .1}, {0, -.1, .1}, {0, -.2, .1},
        						{0, -.25, .25}, {0, -.25, 0}, {0, -.2, 0}, // front face
        						
        						// other face points
        						{.25, .25, 0}, {.25, -.25, 0},
        						{.25, .25, .25}, {.25, -.25, .25},
        						
        						{.125, 0, .5}, // tip of roof
        						
        						{-.001, .05, .15}, {-.001, .05, 0}, {-.001, -.05, 0}, {-.001, -.05, .15} //door
        						};
        
        						
        							
        int[][] faceList = {{0,1,2,3}, {3, 4, 5, 6}, {6, 7, 8, 9}, {9, 10, 11, 12},
        {13, 2, 7, 14}, {15, 8, 19, 16}, {12, 19, 18, 17}, {22, 20, 1, 0}, {22, 20, 21, 23}, {17, 18, 21, 23}, {24, 22, 0}, {24, 0, 17}, {24, 17, 23}, {24, 23, 22}, {21, 18, 1, 20}, {25, 26, 27, 28}};
        					
        
        
       double[][] normalVectors = 		
			{{-0.0125,-0.0,-0.0},{-0.005,-0.0,-0.0},{-0.05,-0.0,-0.0},
			{-0.005,-0.0,-0.0},{-0.01,-0.0,-0.0},{-0.01,-0.0,-0.0},
			{-0.0125,-0.0,-0.0},{0.0,0.0625,0.0},{0.125,-0.0,-0.0},
			{0.0,-0.0625,0.0},{0.0,0.0625,0.0625},{-0.125,-0.0,0.0625},
			{0.0,-0.0625,0.0625},{0.125,-0.0,0.0625},{0.0,-0.0,-0.125},
			{-0.015,-0.0,-0.0}};	
		
		double[][] faceColors = {{.5, .5, .75}, {.5, .5, .75},  
					{.5, .5, .75}, {.5, .5, .75}, 
					{.5, .5, .75}, {.5, .5, .75}, 
					{.5, .5, .75}, {.5, .5, .75},  
					{.5, .5, .75}, {.5, .5, .75},
					{.75, .5, .5}, {.75, .5, .5},  
					{.75, .5, .5}, {.75, .5, .5},  
					{.75, .5, .5}, {.75, .5, .5}};
		
	float[] rgba = {1f, 1f, 1f}; // shape is originally black
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, rgba, 0);
        //gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, 0);
	
	// SET VERTICES ----------------------------------------------------	
		for (int i = 0; i < faceList.length; i++) {
			gl.glColor3dv( faceColors[i], 0 );
			gl.glBegin(GL2.GL_TRIANGLE_FAN); 		
			for (int j = 0; j < faceList[i].length; j++) {
				int vertexNum = faceList[i][j]; // Index for vertex j of face i.
				double[] vertexCoords = vertexList[vertexNum]; // The vertex itself.
        		gl.glNormal3dv(normalVectors[i], 0);
				gl.glVertex3dv( vertexCoords, 0 );
			}
			gl.glEnd();  //!!!! MUST be in this position. Outside of the 
							//brackets will cause rendering error.
		} 
        gl.glEnable(GL2.GL_NORMALIZE);
    //-------------------------------------------------------------------
    
    
    }
    // TABLE DRAWING FUNCTION ----------------------------------------
     public void drawTable(GL2 gl){
      
            double[][] vertexList = {{-.25, .25, .25}, {-.25, -.25, .25}, 
        						 {.25, -.25, .25}, {.25, .25, .25}, // top of table
        							
        							// legs
        						{-.25, .20, .25}, {-.20, .20, .25},{-.20, .25, .25}, //top
        						{-.25, .20, 0}, {-.20, .20, 0},{-.20, .25, 0}, //bottom
        						
        						{-.20, -.25, .25}, {-.20, -.20, .25}, {-.25, -.20, .25}, //top
        						{-.20, -.25, 0}, {-.20, -.20, 0}, {-.25, -.20, 0}, //bottom
        						
        						{.25, -.20, .25}, {.20, -.20, .25}, {.20, -.25, .25}, //top
        						{.25, -.20, 0}, {.20, -.20, 0}, {.20, -.25, 0}, //bottom
        						
        						{.20, .25, .25}, {.20, .20, .25}, {.25, .20, .25}, //top //22
        						{.20, .25, 0}, {.20, .20, 0}, {.25, .20, 0}, //bottom
        						
        						
        						{-.25, .25, 0}, {-.25, -.25, 0}, 
        						 {.25, -.25, 0}, {.25, .25, 0}, // bottom corners of table
        						
        						{-.25, .25, .30}, {-.25, -.25, .30}, 
        						 {.25, -.25, .30}, {.25, .25, .30}};
        						
        							
        int[][] faceList = {{0, 1, 2, 3}, {32, 33, 34, 35}, {32, 0, 1, 33}, {33, 1, 2, 34}, {34, 2, 3, 35}, {35, 3, 0, 32},// top of table
        	{0, 28, 7, 4}, {6, 9, 28, 0}, {5, 8, 9, 6}, {4, 7, 8, 5}, {28, 7, 8, 9}, // upper left leg
        	{1, 29, 13, 10}, {10, 13, 14, 11}, {11, 14, 15, 12}, {12, 15, 29, 1}, {29, 13, 14, 15}, // bottom left leg
        	{2, 30, 19, 16}, {16, 19, 20, 17}, {17, 20, 21, 18}, {18, 21, 30, 2}, {20, 21, 30, 19}, // bottom right leg 
        	{3, 31, 27, 24}, {24, 27, 26, 23}, {22, 25, 26, 23}, {22, 25, 31, 3}, {25, 26, 27, 31}}; // upper right leg
        
        
       double[][] normalVectors = 	
					{{0.0, -0.0, 0.25}, {0.0, -0.0, 0.25}, {-0.025, -0.0, -0.0},
					{0.0, -0.025, 0.0}, {0.025, -0.0, 0.0}, {0.0, 0.025, 0.0}, {-0.0125, -0.0, -0.0},
					{0.0, 0.0125, 0.0}, {0.0125, -0.0, 0.0}, {0.0, -0.0125, 0.0}, {0.0, -0.0, 0.0025},
					{0.0, -0.0125, 0.0}, {0.0125, -0.0, 0.0}, {0.0, 0.0125, 0.0}, {-0.0125, -0.0, -0.0},
					{0.0, -0.0, 0.0025}, {0.0125, -0.0, 0.0}, {0.0, 0.0125, 0.0}, {-0.0125, -0.0, -0.0},
					{0.0, -0.0125, 0.0}, {0.0, -0.0, 0.0025}, {-0.0125, -0.0, -0.0}, {0.0, 0.0125, 0.0},
					{-0.0125, -0.0, -0.0}, {0.0, -0.0125, 0.0}, {0.0, -0.0, 0.0025}};
		
		
		 float[] rgba = {1f, 0f, 0f}; // shape is originally black
        //gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, rgba, 0);
        //gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, 0);	

	
	// SET VERTICES ----------------------------------------------------	
		for (int i = 0; i < faceList.length; i++) {
			gl.glColor3d(0.5f, 0.35f, 0.05f); // Set color for face number i.
			
			gl.glBegin(GL2.GL_TRIANGLE_FAN); 		
			for (int j = 0; j < faceList[i].length; j++) {
				int vertexNum = faceList[i][j]; // Index for vertex j of face i.
				double[] vertexCoords = vertexList[vertexNum]; // The vertex itself.
        		gl.glNormal3dv(normalVectors[i], 0);
				gl.glVertex3dv( vertexCoords, 0 );
			}
			gl.glEnd();  //!!!! MUST be in this position. Outside of the 
							//brackets will cause rendering error.
		} 
        gl.glEnable(GL2.GL_NORMALIZE);
    //-------------------------------------------------------------------
    
    }
    //-------------------------------------------------------------------
    
    // HEXAGONAL BIPYRAMID DRAWING FUNCTION ----------------------------------------
    public void drawHexBipyramid(GL2 gl)
    {

    
    	 double[][] vertexList = { //hexagon
        						{0,-1, 0}, {.866, -.5, 0}, {.866, .5, 0},
        						{0, 1, 0}, {-.866, .5,0},{-.866, -.5,0}, 
        						
        						// upper point, lower point
        						{0,0,1}, {0,0,-1}}; 

		int[][] faceList =  {{6,0,1}, {7,1,0}, // upper, then lower
							 {6,1,2}, {7,2,1}, 
        					 {6,2,3}, {7,3,2}, 
        					 {6,3,4}, {7,4,3}, 
        					 {6,4,5}, {7,5,4}, 
        					 {6,5,0}, {7,0,5}};
	
					
		// found with linear algebra			
		double[][] normalVectors = {{0.5,-0.866,0.866}, {0.5,-0.866,-0.866},
									{1.0,-0.0,0.866}, {1.0,-0.0,-0.866},
									{0.5,0.866,0.866}, {0.5,0.866,-0.866},
									{-0.5,0.866,0.866}, {-0.5,0.866,-0.866},
									{-1.0,-0.0,0.866}, {-1.0,-0.0,-0.866},
									{-0.5,-0.866,0.866}, {-0.5,-0.866,-0.866}};
	// Set material
        float[] rgba = {1f, 1f, 1f}; 
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, 0);
	
	// SET VERTICES ----------------------------------------------------	
		for (int i = 0; i < faceList.length; i++) {
			gl.glColor3d( 1, 0, 0); // Set color for face number i. 
			// gl.glEnable(GL2.GL_NORMALIZE);
			
			gl.glBegin(GL2.GL_TRIANGLE_FAN); 		
			for (int j = 0; j < faceList[i].length; j++) {
				int vertexNum = faceList[i][j]; // Index for vertex j of face i.
				double[] vertexCoords = vertexList[vertexNum]; // The vertex itself.
        		gl.glNormal3dv(normalVectors[i], 0);
				gl.glVertex3dv( vertexCoords, 0 );
			}
			gl.glEnd();  //!!!! MUST be in this position. Outside of the 
							//brackets will cause rendering error.
		} 
        gl.glEnable(GL2.GL_NORMALIZE);
    //-------------------------------------------------------------------
    }
    
    
    
    public void display(GLAutoDrawable drawable) {    
       
        GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        
        // Clear The Screen And The Depth Buffer
        gl.glClear (GL2.GL_COLOR_BUFFER_BIT |  GL2.GL_DEPTH_BUFFER_BIT );
        
        gl.glLoadIdentity();  // Reset The View 

        gl.glEnable(GL4.GL_DEPTH_TEST); // NEED THIS FOR IT TO DISPLAY CORRECTLY
        
        camera.apply(gl);
        
        //----------------------------------------------
       	gl.glEnable(GL2.GL_LIGHTING); // turn lighting on
		
        // SET LIGHT0
        float[] lightColorDiffuse = {.015f, .015f, .015f, 0f}; // white
        
        //gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, lightColorDiffuse, 0);

        // SET LIGHT1
        float ambient[] = {.5f, .5f, .5f, 0.0f};       //low ambient light
        float diffuse[]   = {1.0f, 1.0f, 1.0f, 0.0f};
       
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, diffuse, 0);
        
        float[] position = {0f, 0f, 0f, 0f};
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, position, 0);

        // ENABLE LIGHTS
       	gl.glEnable(GL2.GL_LIGHT0);
       	gl.glEnable(GL2.GL_LIGHT1);
        //---------------------------------------------------

       	gl.glScalef(2f, 2f, 2f);
       	drawHouse(gl);
       	gl.glScalef(.2f, .2f, .2f);
        gl.glTranslatef(.6f, .5f, 0.001f);
        drawTable(gl);
        gl.glScalef(.1f, .1f, .1f);
        gl.glTranslatef(0f, 0f, 4f);
        drawHexBipyramid(gl);
        
    } // end display()

    public void init(GLAutoDrawable drawable) {
           // called when the panel is created
           	camera = new Camera();
        	camera.lookAt(2,2,6, 0,0,0, 0,1,0);
        	camera.setScale(1.2);
        	camera.installTrackball(this);	
        
    }

    public void dispose(GLAutoDrawable drawable) {
            // called when the panel is being disposed
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            // called when user resizes the window
    }
    
}