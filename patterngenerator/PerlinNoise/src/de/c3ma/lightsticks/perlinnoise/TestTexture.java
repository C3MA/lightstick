/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.c3ma.lightsticks.perlinnoise;

import de.c3ma.animation.color.RainbowColor;
import java.awt.Color;
import org.texgen.textures.AbstractTexture;
import org.texgen.utils.GraphUtils;
import org.texgen.utils.RGBAColor;

/**
 *
 * @author tobi
 */
public class TestTexture extends AbstractTexture {

    public double offsetU = 200;
    public double offsetV = 300;
    public double scale = 1.2f;
    public double dimm = 1.00;
    
    @Override
    public void getColor(double u, double v, RGBAColor value) {
        double height = noise.fbmNoise2((offsetU + u) *  scale, (offsetV + v) *  scale, 5);
        height = height * 4.2;
        height = height - 1.7;
        height = GraphUtils.clamp(height);
        
        Color color =  RainbowColor.mapRainbowColor(
                (float) height,
                0f,
                1f);
        
        color = new Color(
                (int) (dimm * color.getRed()),
                (int) (dimm * color.getGreen()),
                (int) (dimm * color.getBlue()));
        
        value.setColor(color.getRed(), color.getGreen(), color.getBlue());
    }

    public TestTexture() {
        super();
        
    }
    
}
