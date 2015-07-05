package de.cccmannheim.lightstick.tracker;

import ibxm.INoteListener;
import ibxm.jme.IBXMAdvancedLoader;
import ibxm.jme.NoteInfo;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class TestIBMXPlayer {
	static File															folder		= new File("./music");
	private static boolean												TEST_NODE	= false;
	static final int[][]												packet		= new int[TestIBMXPlayer.STICK_COUNT][61 * 3 + 4];
	static private float												playtime	= 0;
	static protected ConcurrentHashMap<Float, Map<NoteInfo, NoteInfo>>	todispatch	= new ConcurrentHashMap<>();

	final static int													STICK_COUNT	= 3;

	private static Color[]												colorMap;
	static DatagramSocket												datagramSocket;
	static private List<File>											fileList;
	static int															fileId		= -1;
	private static IBXMAdvancedLoader									anode;
	static InetAddress[]												addresses;

	public static void main(final String[] args) throws SocketException, UnknownHostException {
		TestIBMXPlayer.colorMap = new Color[] { Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE };
		TestIBMXPlayer.datagramSocket = new DatagramSocket();

		final File[] files = TestIBMXPlayer.folder.listFiles();
		TestIBMXPlayer.fileList = Arrays.asList(files);
		Collections.shuffle(TestIBMXPlayer.fileList);

		TestIBMXPlayer.addresses = new InetAddress[TestIBMXPlayer.STICK_COUNT];
		TestIBMXPlayer.configureStickIPs(TestIBMXPlayer.addresses);

		for (int stickid = 0; stickid < TestIBMXPlayer.STICK_COUNT; stickid++) {
			System.out.println(stickid + "-> " + TestIBMXPlayer.addresses[stickid]);
			if (TestIBMXPlayer.addresses[stickid] == null) {
				System.out.println("Missing adress");
				return;
			}
		}

		final Scanner cin = new Scanner(System.in);
		new Thread() {
			@Override
			public void run() {
				while (true) {
					if (cin.hasNextLine()) {
						final String line = cin.nextLine();
						if (line.isEmpty()) {
							TestIBMXPlayer.playRandom();
						}
					}
				}
			};
		}.start();

		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				TestIBMXPlayer.update();
			}
		}, 1000 / 15, 1000 / 15);

	}

	private static void configureStickIPs(final InetAddress[] addresses2) throws UnknownHostException {
		addresses2[0] = InetAddress.getByName("192.168.1.3");
		addresses2[1] = InetAddress.getByName("192.168.1.1");
		addresses2[2] = InetAddress.getByName("192.168.1.2");
	}

	protected static void update() {
		TestIBMXPlayer.playtime += 1 / 15f;
		if (TestIBMXPlayer.anode == null || TestIBMXPlayer.anode.isFinsihed()) {
			TestIBMXPlayer.playRandom();
		}

		final List<Float> removeKeys = new ArrayList<>();

		for (final Entry<Float, Map<NoteInfo, NoteInfo>> ee : TestIBMXPlayer.todispatch.entrySet()) {
			if (ee.getKey() < TestIBMXPlayer.playtime) {
				removeKeys.add(ee.getKey());
				if (ee.getValue().keySet().size() > 200) {
					// final song time final calculation events!
					continue;
				}

				for (final NoteInfo note : ee.getValue().keySet()) {
					final int stickid = Math.abs(note.id) % TestIBMXPlayer.STICK_COUNT;
					final Color color = TestIBMXPlayer.colorMap[note.instrumentid % TestIBMXPlayer.colorMap.length];
					// final int channel = Math.abs(note.instrumentid) % 3;
					final int ledid = 4 + (Math.abs(note.noteKey + (note.panning / 15)) % 15) * 3;
					short ncolor = (short) ((note.volume / 64f * note.globalVolume / 64f) * 125);
					if (ncolor > 125) {
						ncolor = 125;
					}

					for (int x = 0; x < 60 * 3; x += 45) {
						final int newValueR = (TestIBMXPlayer.packet[stickid][ledid + x] + ncolor * (color.getRed() / 2));
						final int newValueG = (TestIBMXPlayer.packet[stickid][ledid + x + 1] + ncolor * (color.getGreen() / 2));
						final int newValueB = (TestIBMXPlayer.packet[stickid][ledid + x + 2] + ncolor * (color.getBlue() / 2));

						if (newValueR > 125) {
							TestIBMXPlayer.packet[stickid][ledid + x] = 125;
						} else {
							TestIBMXPlayer.packet[stickid][ledid + x] = newValueR;
						}
						if (newValueG > 125) {
							TestIBMXPlayer.packet[stickid][ledid + 1 + x] = 125;
						} else {
							TestIBMXPlayer.packet[stickid][ledid + 1 + x] = newValueG;
						}
						if (newValueB > 125) {
							TestIBMXPlayer.packet[stickid][ledid + 2 + x] = 125;
						} else {
							TestIBMXPlayer.packet[stickid][ledid + 2 + x] = newValueB;
						}
					}
				}

			}
		}
		for (final Float r : removeKeys) {
			TestIBMXPlayer.todispatch.remove(r);
		}

		for (int stickid = 0; stickid < TestIBMXPlayer.STICK_COUNT; stickid++) {

			TestIBMXPlayer.packet[stickid][0] = (byte) (stickid + 1);
			try {

				final byte[] data = new byte[TestIBMXPlayer.packet[stickid].length];
				TestIBMXPlayer.copy(stickid, data);

				// dont copy the metadata!
				data[0] = (byte) TestIBMXPlayer.packet[stickid][0];
				data[1] = 0;
				data[2] = 0;
				data[3] = 0;
				final DatagramPacket pp = new DatagramPacket(data, data.length, TestIBMXPlayer.addresses[stickid], 2342);
				TestIBMXPlayer.datagramSocket.send(pp);
			} catch (final IOException e) {
				e.printStackTrace();
			}
			for (int i = 4; i < TestIBMXPlayer.packet[stickid].length; i++) {
				if (TestIBMXPlayer.packet[stickid][i] > 30) {
					TestIBMXPlayer.packet[stickid][i] -= 30;
				} else {
					TestIBMXPlayer.packet[stickid][i] = 0;
				}
			}
		}
	}

	protected synchronized static void playRandom() {
		TestIBMXPlayer.fileId++;
		if (TestIBMXPlayer.fileId > TestIBMXPlayer.fileList.size() - 1) {
			TestIBMXPlayer.fileId = 0;
		}
		final File file = TestIBMXPlayer.fileList.get(TestIBMXPlayer.fileId);
		if (file.getName().endsWith(".xm") || file.getName().endsWith(".mod") || file.getName().endsWith(".s3m")) {
			TestIBMXPlayer.play(file);
		}
	}

	static synchronized private void play(final File fname) {
		System.out.println(fname + " trackerLight");
		if (TestIBMXPlayer.anode != null) {
			TestIBMXPlayer.anode.terminate();
			TestIBMXPlayer.anode = null;
		}
		TestIBMXPlayer.playtime = 0;
		try {
			TestIBMXPlayer.anode = new IBXMAdvancedLoader(fname, new INoteListener() {
				@Override
				public void onNote(final float posInSec, final int id, final int volume, final int noteKey, final int fadeoutVol, final int instrumentid, final int panning, final int freq) {
					Map<NoteInfo, NoteInfo> notelist = TestIBMXPlayer.todispatch.get(posInSec);
					if (notelist == null) {
						notelist = new ConcurrentHashMap<>();
						TestIBMXPlayer.todispatch.put(posInSec, notelist);
					}
					final NoteInfo vv = new NoteInfo(id, volume, noteKey, fadeoutVol, instrumentid, panning, freq);
					notelist.put(vv, vv);
				}
			});
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	static private void copy(final int stickid, final byte[] data) {
		for (int i = 4; i < data.length; i++) {
			final int value = (TestIBMXPlayer.packet[stickid][i] * 2);
			data[i] = (byte) (value);
		}
	}

}