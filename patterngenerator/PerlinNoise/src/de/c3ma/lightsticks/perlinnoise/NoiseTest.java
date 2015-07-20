/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.c3ma.lightsticks.perlinnoise;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.texgen.gui.TextureImage;
import org.texgen.textures.Texture;

import de.c3ma.animation.noise.PerlinNoise;
import de.c3ma.lightsticks.lightsticks.Dynamic;

/**
 *
 * @author tobi
 * @author ollo
 */
public class NoiseTest extends JFrame implements Runnable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3503501169676229128L;
	
	private BufferedImage image;
    private int width;
    private int height;
    private float dimm;
    private float offsetX;
    private float offsetY;
    private float osX;
    private long sleep; 
    private TextureImage texture;
    private Texture texturePattern;
    private long timeToRun ;
	private Dynamic dynamic;

	private boolean mHasGUI = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException, CmdLineException {
        CommandLineValues values = new CommandLineValues(args);
        CmdLineParser parser = new CmdLineParser(values);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.exit(1);
        }

        NoiseTest frame = new NoiseTest(values);
        frame.mHasGUI = values.hasGui();
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        if (frame.mHasGUI) {
            frame.setVisible(true);
        }

    }

    public NoiseTest(CommandLineValues values) throws UnknownHostException, IOException {
        width = values.getWidth();
        height = values.getHeight();
        timeToRun = values.getTime();
        dimm = (float) values.getDimm();
        offsetX = values.getOffsetX();
        offsetY = values.getOffsetY();
        osX = values.getOsX();
        sleep = values.getSleep(); 

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        texture = new TextureImage(width, height);
        texturePattern = new TestTexture();
        ((TestTexture) texturePattern).dimm = dimm;
        
        ((TestTexture) texturePattern).offsetU = offsetX;
        ((TestTexture) texturePattern).offsetV = offsetY;

        if (values.getRemoteAddress() != null) {
            dynamic = new Dynamic(values.getRemoteAddress(), width, height);
            System.out.println("Generate content with " + width + "x" + height + " pixels, or for " + width + " lightsticks.");
        }

        new Thread(this).start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        /* only draw something, if necessary */
        if (this.mHasGUI)
        {
        	g.drawImage(image,
                10, 30, 480, 770,
                0, 0, width, height,
                this);
        }
    }

    @Override
    public void run() {
        double fooX = 0, fooY = 0;
        long starttime = new Date().getTime();
        
        while (true) {
            System.gc();
            //texture.renderAndWait(texturePattern);
            texture.run(texturePattern);

            image = texture.getImage();


            ((TestTexture) texturePattern).offsetU += 0.01 * Math.sin(fooX + PerlinNoise.noise(fooX, fooX / 2, 3.0));
            ((TestTexture) texturePattern).offsetV += 0.01 * Math.cos( fooY + PerlinNoise.noise(fooY / 2, fooY, 5));
            fooX += osX;
            fooY += osX;
            //System.out.println("x: " + ((TestTexture) texturePattern).offsetU + "; y: " + ((TestTexture) texturePattern).offsetV );
            

            this.repaint();

            if (dynamic != null) {
            	
            	for(int y = 0; y < height; y++) {
                    for(int x = 0; x < width; x++) { 
                        Color c = new Color(image.getRGB(x, y));
                        dynamic.updatePixel((byte) c.getRed(), (byte) c.getGreen(), (byte) c.getBlue(), x, y);
                    }
                }
            	try {
					dynamic.sendImage();
				} catch (IOException e) {
					Logger.getLogger(NoiseTest.class.getName()).log(Level.SEVERE, "Communication Error", e.getMessage());
				}
            }
            
            try {
                if (sleep > 0)
                {
                    Thread.sleep(sleep);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(NoiseTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /* if requested stopp, after a certain time */
            if (timeToRun > 0) {
                if ((starttime + timeToRun) < new Date().getTime()) {
                    break;
                }
            }
        }
        System.out.println("Finished!");
        

        System.exit(0);
    }
    
}
