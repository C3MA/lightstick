package de.c3ma.lightsticks.lightsticks;

public class Dynamic {

	private final static int COLOR_WIDTH = 3;
	private static final int RED_OFFSET = 0;
	private static final int GREEN_OFFSET = 1;
	private static final int BLUE_OFFSET = 2;
	
	private String mBaseIP="";
	
	private int[][] mColorArray;
	
	public Dynamic(String ip, int width, int height) {
		this.mBaseIP = ip;
		this.mColorArray = new int[width][height * COLOR_WIDTH];
	}

	/**
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param x		starts at zero!
	 * @param y		starts at zero!
	 */
	public void updatePixel(int red, int green, int blue, int x, int y) {
		if (mColorArray == null) {
			return;
		}
		
		if (mColorArray.length <= x) {
			throw new IndexOutOfBoundsException("Width is too larg: " + x + " (Maximum is: " + mColorArray.length + ")");
		}
		
		if (mColorArray[x].length <= ( (y*COLOR_WIDTH) + BLUE_OFFSET)) {
			throw new IndexOutOfBoundsException("Height is too larg: " + (y*COLOR_WIDTH) + BLUE_OFFSET
					+ " (Maximum is: " + mColorArray[x].length + ")");
		}
		
		mColorArray[x][(y*COLOR_WIDTH) + RED_OFFSET] = red;
		mColorArray[x][(y*COLOR_WIDTH) + GREEN_OFFSET] = green;
		mColorArray[x][(y*COLOR_WIDTH) + BLUE_OFFSET] = blue;
	}

	public void sendImage() {
		// TODO Auto-generated method stub
		
	}

}
