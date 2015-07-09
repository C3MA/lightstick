/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.c3ma.lightsticks.perlinnoise;

import java.io.File;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 *
 * @author tobi
 */
public final class CommandLineValues {
    @Option(name = "-o", aliases = { "--output" }, required = false,
            usage = "output file for the sequence")
    private File output;

    public File getOutput() {
        return output;
    }
    
    @Option(name = "-r" , aliases = {"--remote"}, required = false, 
            usage = "ip address or hostname of the fc server")
    private String remoteAddress;

    public String getRemoteAddress() {
        return remoteAddress;
    }
    
    @Option(name = "-g", aliases = {"--gui"}, required = false,
            usage = "should the applicatin starts with gui")
    private boolean gui; 

    public boolean hasGui() {
        return gui;
    }
    
    @Option(name =  "-t", aliases = {"--time"}, required = false,
            usage = "time to run in miliseconds. 0 or default is inity")
    private long time = 0;

    public long getTime() {
        return time;
    }
    
    @Option(name = "-d", aliases = {"--dimm"}, required = false,
            usage = "dimm value 0.00 - 1.00. Default is 1.00")
    private double dimm = 1.00;

    public double getDimm() {
        return dimm;
    }
    
    @Option(name = "-h", aliases = {"--height"}, required = false,
            usage = "height in pixels. Default is 12 pixels.")
    private int height = 60;

    public int getHeight() {
        return height;
    }
    
    @Option(name = "-w", aliases = {"--width"}, required = false,
            usage = "width in pixels. Default is 10 pixels.")
    private int width = 10;

    public int getWidth() {
        return width;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getOsX() {
        return osX;
    }

    public float getOsY() {
        return osY;
    }

    public long getSleep() {
        return sleep;
    }
    
    @Option(name = "-x", aliases = {"--offsetx"}, required = false,
            usage = "x start postition.")
    private float offsetX = 42f;
    
    @Option(name = "-y", aliases = {"--offsety"}, required = false,
            usage = "y start position")
    private float offsetY = 23f;
    
    @Option(name = "-u", aliases = {"--osx"}, required = false,
            usage = "x oscilator")
    private float osX = 0.00001f;
    
    @Option(name = "-v", aliases = {"--osy"}, required = false,
            usage = "y oscilator")
    private float osY = 0.002f;
    
    @Option(name = "-s", aliases = {"--sleep"}, required = false,
            usage = "sleep time between frames")
    private long sleep = 100; 
        
    @Option(name = "-?", aliases = {"--help"}, required = false,
            usage = "print usage")
    private boolean help;
    
    public boolean isErrorFree() {
        return errorFree;
    }
    
    private boolean errorFree = false;
 
    public CommandLineValues(String... args) {
        CmdLineParser parser = new CmdLineParser(this);
        parser.setUsageWidth(80);
        try {
            parser.parseArgument(args);
            
            if(help) {
                parser.printUsage(System.err);
                System.exit(0);
            }
            
            errorFree = true;
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
    }
}
