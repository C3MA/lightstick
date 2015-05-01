package de.c3ma.emuStick;

import java.awt.FlowLayout;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class EmuStick {

	private static Map<Integer, Stick>	idToStick	= new HashMap<>();

	public static void main(final String[] args) throws InterruptedException, SocketException {
		final JFrame frame = new JFrame("Emulation");
		frame.setSize(1024, 600);
		frame.setVisible(true);

		frame.getContentPane().setLayout(new FlowLayout());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		@SuppressWarnings("resource")
		final DatagramSocket socket = new DatagramSocket(2342);

		EmuStick.getStick(1, frame);

		new Thread() {
			@Override
			public void run() {
				while (true) {

					final byte[] cache = new byte[1024 * 1024 * 1];
					final DatagramPacket packet = new DatagramPacket(cache, cache.length);
					try {
						socket.receive(packet);
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								try {
									EmuStick.processData(packet.getData(), packet.getLength(), frame);
								} catch (final Exception e) {
									e.printStackTrace();
								}
							}
						});
					} catch (final Exception e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
		while (true) {
			frame.pack();
			Thread.sleep(2);
			frame.getContentPane().repaint();

		}
	}

	private static void processData(final byte[] data, final int length, final JFrame frame) throws IOException {
		if (length < 7) {
			if (Stick.VERBOSE_ERROR_CHECKING) {
				System.err.println("Recived package under minimum length " + length);
			}
			return;
		}
		if (length < 4 + 3 * Stick.LEDCOUNT) {
			if (Stick.VERBOSE_ERROR_CHECKING) {
				System.err.println("Recived package not for ledCount " + length + " LEDCOUNT: " + Stick.LEDCOUNT);
			}
		}
		final DataInputStream read = new DataInputStream(new ByteArrayInputStream(data));
		final byte id = read.readByte();
		final Stick stick = EmuStick.getStick(id, frame);
		final byte delay = read.readByte();
		// TODO delay sometime
		final short timestamp = read.readShort();

		// header
		int restLenght = length - 4;
		int led = 0;
		while (restLenght >= 3) {
			final float r = (read.readUnsignedByte()) / 255f;
			final float g = (read.readUnsignedByte()) / 255f;
			final float b = (read.readUnsignedByte()) / 255f;

			if (led >= Stick.LEDCOUNT) {
				if (Stick.VERBOSE_ERROR_CHECKING) {
					System.err.println("Stick out of bound " + led + " for stick " + id);
				}
				return;
			}
			stick.setLed(led, r, g, b);

			led++;
			restLenght = restLenght - 3;
		}

	}

	private static Stick getStick(final int id, final JFrame frame) {
		Stick stick = EmuStick.idToStick.get(id);
		if (stick != null) {
			return stick;
		}
		stick = new Stick((byte) id);
		EmuStick.idToStick.put(id, stick);

		frame.getContentPane().removeAll();

		final List<Stick> sticks = new ArrayList<Stick>(EmuStick.idToStick.values());
		Collections.sort(sticks);
		for (final Stick s : sticks) {
			frame.getContentPane().add(s);
		}

		return stick;
	}

}
