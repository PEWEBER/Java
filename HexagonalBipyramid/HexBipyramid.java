/* ------------------------------------------------------------------------------------
Paige Weber, L22812475
Dr. Crawley, Computer Graphics
10/5/19
----------------------------------------------------------------------------------------
This program uses the Java libraries swing, awt and openGL to display a 3D hexagonal 
bipyramid. Each panel of the shape is a different color and the shape is slightly rotated
for visability.  To adjust the rotation see lines 52-57.
------------------------------------------------------------------------------------- */

import java.awt.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;


public class HexBipyramid extends GLJPanel implements GLEventListener{
    
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
        
        // gl.glClearColor(0,0,0,0);
        gl.glEnable(GL4.GL_DEPTH_TEST); // NEED THIS FOR IT TO DISPLAY CORRECTLY
          
          
//-------------------------------------------------------------------------------
      	// CHANGE ANGLE OF ROTATION HERE
      	// First number is angle of rotation in degrees the following are x,
      	// y, z values
      	gl.glRotatef(65, 1, 0, 0); 
//-------------------------------------------------------------------------------
        
        double[][] vertexList = { //hexagon
        						{0,-1, 0}, {.866025, -.5, 0}, {.866025, .5, 0},
        						{0, 1, 0}, {-.866025, .5,0},{-.866025, -.5,0}, 
        						
        						// upper point, lower point
        						{0,0,1}, {0,0,-1}}; 

		int[][] faceList =  {{0,1,6}, {0,1,7}, // upper, then lower
							 {1,2,6}, {1,2,7}, 
        					 {2,3,6}, {2,3,7}, 
        					 {3,4,6}, {3,4,7}, 
        					 {4,5,6}, {4,5,7}, 
        					 {5,0,6}, {5,0,7}};
		
		double[][] faceColors = {{1, 0, 1}, {0, 0, 1},  // pink  + blue
								{0, 0, 1}, {1, 0, 1}, 
								{1, 0, 1}, {0, 0, 1}, 
								{0, 0, 1}, {1, 0, 1},  
								{1, 0, 1}, {0, 0, 1}, 
								{0, 0, 1}, {1, 0, 1}};
								     
		
		
		for (int i = 0; i < faceList.length; i++) {
			gl.glColor3dv( faceColors[i], 0 ); // Set color for face number i. 
			gl.glBegin(GL2.GL_TRIANGLE_FAN); 
			
			for (int j = 0; j < faceList[i].length; j++) {
				int vertexNum = faceList[i][j]; // Index for vertex j of face i.
				double[] vertexCoords = vertexList[vertexNum]; // The vertex itself.
				gl.glVertex3dv( vertexCoords, 0 );
			}
			gl.glEnd(); /* !!!! MUST be in this position. Outside of the 
							brackets will cause rendering issues.*/
		} 
		
	
// HARD CODED VERSION ------------------------------------------------------------
        
        
        //hexagon
   		/*gl.glBegin(GL2.GL_POLYGON);
   		gl.glColor3f(1.0f, 1.0f, 1.0f); // red + blue + green
   		
   		gl.glVertex3f(0f, 1f ,0f);
   		gl.glVertex3f(.866025f, .5f ,0f);
   		gl.glVertex3f(.866025f, -.5f ,0f);
   		gl.glVertex3f(0f, -1f ,0f);
   		gl.glVertex3f(-0.866025f, -.5f ,0f);
   		gl.glVertex3f(-.866025f, .5f ,0f);
   		gl.glEnd();
   		
   		// triangle between (0, 1) and (.866025, .5)
   		
   		//upper
   		gl.glBegin(GL2.GL_TRIANGLES);
   		gl.glColor3f(1.0f, 0.0f, 0.0f ); // red
   		gl.glVertex3f(0f, 1f ,0f);
   		gl.glVertex3f(.866025f, .5f ,0f);
   		gl.glVertex3f(0f, 0f ,1f);
   		gl.glEnd();
   		
   		//lower
   		gl.glBegin(GL2.GL_TRIANGLES);
   		gl.glColor3f(1.0f, 0.0f, 1.0f ); // red + blue
   		gl.glVertex3f(0f, 1f ,0f);
   		gl.glVertex3f(.866025f, .5f ,0f);
   		gl.glVertex3f(0f, 0f ,-1f);
   		gl.glEnd();
   		
   		// triangle between (.866025, .5) and (.866025, -.5)
   		
   		//upper
   		gl.glBegin(GL2.GL_TRIANGLES);
   		gl.glColor3f(0.0f, 1.0f, 0.0f ); // green
   		gl.glVertex3f(.866025f, .5f ,0f);
   		gl.glVertex3f(.866025f, -.5f ,0f);
   		gl.glVertex3f(0f, 0f ,1f);
   		gl.glEnd();
   		
   		//lower
   		gl.glBegin(GL2.GL_TRIANGLES);
   		gl.glColor3f(0.0f, 1.0f, 1.0f ); // green + blue
   		gl.glVertex3f(.866025f, .5f ,0f);
   		gl.glVertex3f(.866025f, -.5f ,0f);
   		gl.glVertex3f(0f, 0f ,-1f);
   		gl.glEnd();
   		
   		// triangle between (.866025, -.5) and (0, -1)
   		
   		// upper
   		gl.glBegin(GL2.GL_TRIANGLES);
   		gl.glColor3f(0.0f, 0.0f, 1.0f ); // blue
   		gl.glVertex3f(.866025f, -.5f ,0f);
   		gl.glVertex3f(0f, -1f ,0f);
   		gl.glVertex3f(0f, 0f ,1f);
   		gl.glEnd();
   		
   		//lower
   		gl.glBegin(GL2.GL_TRIANGLES);
   		gl.glColor3f(1.0f, 1.0f, 0.0f ); // red + green
   		gl.glVertex3f(.866025f, -.5f ,0f);
   		gl.glVertex3f(0f, -1f ,0f);
   		gl.glVertex3f(0f, 0f ,-1f);
   		gl.glEnd();
   		
   		// triangle between (0, -1) and (-.866025, -.5)
   		
   		//upper
   		gl.glBegin(GL2.GL_TRIANGLES);
   		gl.glColor3f(1.0f, 1.0f, 0.0f ); // red + green
   		gl.glVertex3f(0f, -1f ,0f);
   		gl.glVertex3f(-.866025f, -.5f ,0f);
   		gl.glVertex3f(0f, 0f ,1f);
   		gl.glEnd();
   		
   		// lower
   		gl.glBegin(GL2.GL_TRIANGLES);
   		gl.glColor3f(1.0f, 0.0f, 0.0f ); // red
   		gl.glVertex3f(0f, -1f ,0f);
   		gl.glVertex3f(-.866025f, -.5f ,0f);
   		gl.glVertex3f(0f, 0f ,-1f);
   		gl.glEnd();
   		
   		// triangle between (-.866025, -.5) and (-.866025, .5)
   		
   		//upper
   		gl.glBegin(GL2.GL_TRIANGLES);
   		gl.glColor3f(0.0f, 1.0f, 1.0f ); // green + blue
   		gl.glVertex3f(-.866025f, -.5f ,0f);
   		gl.glVertex3f(-.866025f, .5f ,0f);
   		gl.glVertex3f(0f, 0f ,1f);
   		gl.glEnd();
   		
   		//lower
   		gl.glBegin(GL2.GL_TRIANGLES);
   		gl.glColor3f(0.0f, 1.0f, 0.0f ); // green
   		gl.glVertex3f(-.866025f, -.5f ,0f);
   		gl.glVertex3f(-.866025f, .5f ,0f);
   		gl.glVertex3f(0f, 0f ,-1f);
   		gl.glEnd();
   		
   		// triangle between (-.866025, .5) and (0, 1)
   		
   		// upper
   		gl.glBegin(GL2.GL_TRIANGLES);
   		gl.glColor3f(1.0f, 0.0f, 1.0f ); // red + blue
   		gl.glVertex3f(-.866025f, .5f ,0f);
   		gl.glVertex3f(0f, 1f ,0f);
   		gl.glVertex3f(0f, 0f ,1f);
   		gl.glEnd();
   		
   		// lower
   		gl.glBegin(GL2.GL_TRIANGLES);
   		gl.glColor3f(0.0f, 0.0f, 1.0f ); // blue
   		gl.glVertex3f(-.866025f, .5f ,0f);
   		gl.glVertex3f(0f, 1f ,0f);
   		gl.glVertex3f(0f, 0f ,-1f);
   		gl.glEnd();*/
   	
//-------------------------------------------------------------------------------
        
        
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