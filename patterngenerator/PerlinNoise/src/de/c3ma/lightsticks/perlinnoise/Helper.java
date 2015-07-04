/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.c3ma.lightsticks.perlinnoise;

import java.awt.Color;

/**
 *
 * @author tobi
 */
public class Helper {
    
    public static Color Dimm(Color color, float dimm) {
       Color val = new Color(
               (int) (dimm * color.getRed()),
               (int) (dimm * color.getGreen()),
               (int) (dimm * color.getBlue())
               );
        
       return val; 
    }
}
