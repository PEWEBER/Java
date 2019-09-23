/* -----------------------------------------
Paige Weber, L22812475
Dr. Crawley, Computer Graphics
9/20/19
--------------------------------------------
This program uses the Java libraries 
swing and awt to draw a house including
two windows, landscaping, and a sun
with the implementation of translation,
rotation, and scaling.
----------------------------------------- */


import java.awt.*;       
import java.awt.geom.*;

import javax.swing.*;

public class House extends JPanel {
    public static void main(String[] args) {
        JFrame window;
        window = new JFrame("Java Graphics");  // The parameter shows in the window title bar.
        window.setContentPane( new House() ); // Show a Graphics starter in the window.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // End program when window closes.
        window.pack();  // Set window size based on the preferred sizes of its contents.
        window.setResizable(false); // Don't let user resize window.
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation( // Center window on screen.
                (screen.width - window.getWidth())/2, 
                (screen.height - window.getHeight())/2 );
        window.setVisible(true); // Open the window, making it visible on the screen.
    }
    
    private float pixelSize;  

    public House() {
        setPreferredSize( new Dimension(800,600) ); // Set size of drawing area, in pixels.
    }
    

    protected void paintComponent(Graphics g) {
        
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // custom colors
        Color myBlue = new Color(135,206,235);
        Color myGreen = new Color(49,99,0);
        Color myPink = new Color(200,33,120);
        Color myGray = new Color(100, 100, 100);
        Color myDarkGreen = new Color(44,73,45);
        Color myBrown = new Color(100,42,42);

        // sky
        g2.setPaint(myBlue);
        g2.fillRect(0,0,800,500); 
        
        // grass
        g2.setPaint(myGreen);
        g2.fillRect(0,500,800,200);
        
        applyapplyWindowToViewportTransformation(g2, -10, 10, -10, 10, true);
        
        // house
        g2.setPaint(myPink);
        g2.fillRect(0, -8, 8, 8);
        g2.translate(4, 0);

        // roof
        g2.setPaint(myGray);
        Polygon poly = new Polygon(new int[] { 3, -1, -1 }, new int[] { 0, -6, 6 }, 3);
        g2.rotate(Math.toRadians(90));
        g2.translate(1, 0);
        g2.fillPolygon(poly);

        // door
        g2.setPaint(myGray);
        g2.translate(-9, 1);
        g2.fillRect(0, -2, 3, 2);

        // windows
        g2.setPaint(Color.GRAY);
        g2.translate(5, 2);
        g2.fillRect(0, -2, 2, 2);
        g2.translate(0, -4);
        g2.fillRect(0, -2, 2, 2);

        // chimney
        g2.setPaint(Color.PINK);
        g2.translate(4, 5);
        g2.fillRect(0, -2, 2, 1);

        // tree
        g2.setPaint(myDarkGreen);
        Polygon poly2 = new Polygon(new int[] { 5, -1, -1 }, new int[] { 0, -2, 2 }, 3);
        g2.translate(-5, 5);
        g2.fillPolygon(poly2);
        // tree 2
        g2.scale(.75, .75);
        g2.translate(-1, 5);
        g2.fillPolygon(poly2);

        // trunk
        g2.setPaint(myBrown);
        g2.translate(-3, 0);
        g2.fillRect(0, -1, 2, 2);
        // trunk 2
        g2.translate(0, -5);
        g2.scale(1.332, 1);
        g2.fillRect(0, -1, 2, 2);

        // sun
        g2.setPaint(Color.YELLOW);
        g2.fillOval(12, 5, 3, 4);
        
    }
    
    private void applyapplyWindowToViewportTransformation(Graphics2D g2,
            double left, double right, double bottom, double top, 
            boolean preserveAspect) {
        int width = getWidth();   // The width of this drawing area, in pixels.
        int height = getHeight(); // The height of this drawing area, in pixels.
        if (preserveAspect) {
            // Adjust the limits to match the aspect ratio of the drawing area.
            double displayAspect = Math.abs((double)height / width);
            double requestedAspect = Math.abs(( bottom-top ) / ( right-left ));
            if (displayAspect > requestedAspect) {
                // Expand the viewport vertically.
                double excess = (bottom-top) * (displayAspect/requestedAspect - 1);
                bottom += excess/2;
                top -= excess/2;
            }
            else if (displayAspect < requestedAspect) {
                // Expand the viewport vertically.
                double excess = (right-left) * (requestedAspect/displayAspect - 1);
                right += excess/2;
                left -= excess/2;
            }
        }
        g2.scale( width / (right-left), height / (bottom-top) );
        g2.translate( -left, -top );
        double pixelWidth = Math.abs(( right - left ) / width);
        double pixelHeight = Math.abs(( bottom - top ) / height);
        pixelSize = (float)Math.max(pixelWidth,pixelHeight);
    }
    
}