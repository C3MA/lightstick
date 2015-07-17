package de.c3ma.lightsticks.lightsticks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Dynamic {

	private final static int COLOR_WIDTH = 3;
	private static final int RED_OFFSET = 0;
	private static final int GREEN_OFFSET = 1;
	private static final int BLUE_OFFSET = 2;

	
	/**
	 * The header consist out of the following information:
	 * id 			(first byte)
	 * delay 		(second byte)
	 * timestamp	(third and fourth byte)
	 * Source: https://github.com/C3MA/lightstick/blob/master/README.md
	 */
	private static final int HEADER_SIZE = 4;	
	private static final int IMAGE_UDP_PORT = 2342;
	
	private InetAddress[] mTargets;

	private byte[][] mColorArray;

	/**
	 * Initialize a new Communication module, to talk to the sticks or the
	 * emulator.
	 * 
	 * The sticks and emulator is only differentiated by the
	 * parameter @link{ip}. When the Sticks are used, the parameter ip is set
	 * like <code>192.168.23.</code> Using the emulator, simply give the system
	 * the IP of the emulator, like <code>192.168.23.240</code>
	 * 
	 * @param ip
	 *            IP-Base for the lightsticks, or the IP address of the emulator
	 *            to use.
	 * @param width
	 *            amount of light sticks
	 * @param height
	 *            amount of LEDs in on stick, normally this value should be 60,
	 *            as we use 1 Meter of the LED stripe
	 * @throws UnknownHostException 	When the parameter ip is not a correct IP address
	 */
	public Dynamic(String ip, int width, int height) throws UnknownHostException {

		mTargets = new InetAddress[width];

		if (ip.endsWith(".")) { /* Configure the IP address of each light stick */
			for (int i = 1; i <= width; i++) {
				mTargets[i - 1] = InetAddress.getByName(ip + i);
			}
		} else { /* Emulator is used, so every stick has the same IP */
			for (int i = 1; i <= width; i++) {
				mTargets[i - 1] = InetAddress.getByName(ip);
			}
		}

		this.mColorArray = new byte[width][height * COLOR_WIDTH];
	}

	/**
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param x
	 *            starts at zero!
	 * @param y
	 *            starts at zero!
	 */
	public void updatePixel(byte red, byte green, byte blue, int x, int y) {
		if (mColorArray == null) {
			return;
		}

		if (mColorArray.length <= x) {
			throw new IndexOutOfBoundsException(
					"Width is too larg: " + x + " (Maximum is: " + mColorArray.length + ")");
		}

		if (mColorArray[x].length <= ((y * COLOR_WIDTH) + BLUE_OFFSET)) {
			throw new IndexOutOfBoundsException("Height is too larg: " + (y * COLOR_WIDTH) + BLUE_OFFSET
					+ " (Maximum is: " + mColorArray[x].length + ")");
		}

		mColorArray[x][(y * COLOR_WIDTH) + RED_OFFSET] = red;
		mColorArray[x][(y * COLOR_WIDTH) + GREEN_OFFSET] = green;
		mColorArray[x][(y * COLOR_WIDTH) + BLUE_OFFSET] = blue;
	}

	public void sendImage() throws IOException {
		
		for (int stickNr=0; stickNr < mColorArray.length; stickNr++)
		{
			byte[] sendData = new byte[HEADER_SIZE + mColorArray[stickNr].length];
			/* Set the header (only the ID is required) */
			sendData[0] = (byte) (stickNr + 1);
			System.arraycopy(mColorArray[stickNr], 0, sendData, HEADER_SIZE, mColorArray[stickNr].length);
			DatagramSocket clientSocket = new DatagramSocket();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, mTargets[stickNr], IMAGE_UDP_PORT);
		      clientSocket.send(sendPacket);
		    clientSocket.close();
		}
		
	}

}
