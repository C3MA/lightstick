package de.cccmannheim.lightstick.tracker;

import ibxm.INoteListener;
import ibxm.jme.IBXMAdvancedLoader;
import ibxm.jme.IBXMLoader;
import ibxm.jme.NoteInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.audio.AudioKey;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource.Status;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
import com.jme3.system.AppSettings;

public class TestIBMXPlayer extends SimpleApplication {

	private static final float	FADE_TIME	= 0.9f;

	public static void main(final String[] args) throws SocketException {
		final AppSettings settings = new AppSettings(true);
		// settings.setGammaCorrection(true);
		final TestIBMXPlayer t = new TestIBMXPlayer();
		t.setSettings(settings);
		t.start();
	}

	private static boolean							TEST_NODE	= false;
	final short[][]									packet		= new short[TestIBMXPlayer.STICK_COUNT][61 * 3 + 4];
	private AudioNode								anode;
	private float									playtime	= 0;
	protected ConcurrentHashMap<Float, NoteInfo>	todispatch	= new ConcurrentHashMap<>();

	final static int								STICK_COUNT	= 3;

	DatagramSocket									datagramSocket;
	private File									folder;
	private String									lastFilename;

	public TestIBMXPlayer() throws SocketException {
		this.setShowSettings(false);
		this.datagramSocket = new DatagramSocket();
	}

	@Override
	public void simpleInitApp() {
		this.folder = new File("/home/empire/Desktop/KEYGENMUSiC MusicPack");

		this.setPauseOnLostFocus(false);

		this.flyCam.setDragToRotate(true);
		this.assetManager.registerLoader(IBXMLoader.class, "mod");
		this.assetManager.registerLocator("de/cccmannheim/lightstick/tracker", ClasspathLocator.class);
		this.assetManager.registerLocator(this.folder.getAbsolutePath(), FileLocator.class);

		try {
			this.play("pinball_illusions.mod");
		} catch (final Exception e1) {
			e1.printStackTrace();
		}

		this.inputManager.addRawInputListener(new RawInputListener() {

			@Override
			public void onTouchEvent(final TouchEvent evt) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseMotionEvent(final MouseMotionEvent evt) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseButtonEvent(final MouseButtonEvent evt) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onKeyEvent(final KeyInputEvent evt) {
				if (evt.isReleased() && evt.getKeyCode() == Keyboard.KEY_SPACE) {
					TestIBMXPlayer.this.playRandom();
				}
				if (evt.isReleased() && evt.getKeyCode() == Keyboard.KEY_S) {
					try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./goodifiles.txt", true)))) {
						out.println(TestIBMXPlayer.this.lastFilename);
					} catch (final IOException e) {
					}
				}
			}

			@Override
			public void onJoyButtonEvent(final JoyButtonEvent evt) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onJoyAxisEvent(final JoyAxisEvent evt) {
				// TODO Auto-generated method stub

			}

			@Override
			public void endInput() {
				// TODO Auto-generated method stub

			}

			@Override
			public void beginInput() {
				// TODO Auto-generated method stub

			}
		});

	}

	private void playRandom() {
		final File[] folders = TestIBMXPlayer.this.folder.listFiles();
		final int folderindex = (int) Math.round(Math.random() * folders.length - 1);
		if (folderindex < 0) {
			return;
		}
		final File[] files = folders[folderindex].listFiles();
		final int fileindex = (int) Math.round(Math.random() * files.length - 1);
		if (fileindex < 0) {
			return;
		}
		final File file = files[fileindex];
		if (file.getName().endsWith(".xm") || file.getName().endsWith(".mod") || file.getName().endsWith(".s3m")) {
			final Path relative = TestIBMXPlayer.this.folder.toPath().relativize(file.toPath());
			try {
				TestIBMXPlayer.this.play(relative.toString());
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void play(final String fname) throws Exception {
		this.lastFilename = fname;
		Display.setTitle(fname + " trackerLight");

		if (this.anode != null) {
			this.anode.stop();
			this.anode = null;
		}
		this.playtime = 0;
		final AudioKey ak = new AudioKey(fname, true);
		final AssetInfo loadInfo = this.assetManager.locateAsset(ak);
		try {
			final IBXMAdvancedLoader al = new IBXMAdvancedLoader(loadInfo, new INoteListener() {
				@Override
				public void onNote(final float posInSec, final int note, final int volume, final int noteKey, final int fadeoutVol, final int instrumentid, final int panning) {
					TestIBMXPlayer.this.todispatch.put(posInSec, new NoteInfo(note, volume, noteKey, fadeoutVol, instrumentid, panning));
				}
			});
			this.anode = new AudioNode(al.getAudioData(), ak);
			this.anode.setPositional(false);
			this.anode.play();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void simpleUpdate(final float tpf) {
		this.playtime += tpf;
		if (this.anode == null || this.anode.getStatus() == Status.Stopped) {
			this.playRandom();
		}

		final List<Float> removeKeys = new ArrayList<>();

		for (final Entry<Float, NoteInfo> ee : this.todispatch.entrySet()) {
			if (ee.getKey() < this.playtime) {
				final NoteInfo note = ee.getValue();
				removeKeys.add(ee.getKey());

				final int stickid = note.note % TestIBMXPlayer.STICK_COUNT;
				final int channel = note.instrumentid % 3;
				final int ledid = note.panning % 10 * 3 + channel;
				final byte ncolor = (byte) ((note.volume / 64f * note.globalVolume / 64f) * 127);
				if (((this.packet[stickid][ledid]) + ncolor > 255)) {
					this.packet[stickid][ledid] = 255;
					this.packet[stickid][ledid + (10 * 3)] = 255;
					this.packet[stickid][ledid + (20 * 3)] = 255;
					this.packet[stickid][ledid + (30 * 3)] = 255;
					this.packet[stickid][ledid + (40 * 3)] = 255;
					this.packet[stickid][ledid + (50 * 3)] = 255;
				} else {
					this.packet[stickid][ledid] += ncolor;
					this.packet[stickid][ledid + (10 * 3)] += ncolor;
					this.packet[stickid][ledid + (20 * 3)] += ncolor;
					this.packet[stickid][ledid + (30 * 3)] += ncolor;
					this.packet[stickid][ledid + (40 * 3)] += ncolor;
					this.packet[stickid][ledid + (50 * 3)] += ncolor;

				}
			}
		}
		for (final Float r : removeKeys) {
			this.todispatch.remove(r);
		}

		for (int stickid = 0; stickid < TestIBMXPlayer.STICK_COUNT; stickid++) {
			this.packet[stickid][0] = (byte) (stickid + 1);
			for (int i = 3; i < this.packet[stickid].length; i++) {
				if (this.packet[stickid][i] > 10) {
					this.packet[stickid][i] = (short) (this.packet[stickid][i] - 10);
				}
				if (this.packet[stickid][i] > 0) {
					this.packet[stickid][i]--;
				}
			}

			try {
				final InetAddress address = InetAddress.getByName(TestIBMXPlayer.TEST_NODE ? "127.0.0.1" : ("192.168.23." + (stickid + 1)));
				final byte[] data = new byte[this.packet[stickid].length];
				for (int i = 0; i < data.length; i++) {
					data[i] = (byte) this.packet[stickid][i];
				}
				final DatagramPacket pp = new DatagramPacket(data, data.length, address, 2342);
				this.datagramSocket.send(pp);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
}
