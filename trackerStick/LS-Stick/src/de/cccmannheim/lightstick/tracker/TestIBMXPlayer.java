package de.cccmannheim.lightstick.tracker;

import ibxm.INoteListener;
import ibxm.jme.IBXMAdvancedLoader;
import ibxm.jme.IBXMLoader;
import ibxm.jme.NoteInfo;

import java.io.File;
import java.io.IOException;
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

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.audio.AudioKey;
import com.jme3.audio.AudioNode;
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

	final byte[][]									packet		= new byte[TestIBMXPlayer.STICK_COUNT][61 * 3 + 4];
	private AudioNode								anode;
	private float									playtime	= 0;
	protected ConcurrentHashMap<Float, NoteInfo>	todispatch	= new ConcurrentHashMap<>();

	final static int								STICK_COUNT	= 3;

	DatagramSocket									datagramSocket;
	private File									folder;

	public TestIBMXPlayer() throws SocketException {
		this.setShowSettings(false);
		this.datagramSocket = new DatagramSocket();
	}

	@Override
	public void simpleInitApp() {
		this.folder = new File("./music");

		this.setPauseOnLostFocus(false);

		this.flyCam.setDragToRotate(true);
		this.assetManager.registerLoader(IBXMLoader.class, "mod");
		this.assetManager.registerLocator("de/cccmannheim/lightstick/tracker", ClasspathLocator.class);
		this.assetManager.registerLocator(this.folder.getAbsolutePath(), FileLocator.class);

		this.play("pinball_illusions.mod");

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
					final File[] files = TestIBMXPlayer.this.folder.listFiles();
					final int fileindex = (int) Math.round(Math.random() * files.length - 1);
					if (fileindex < 0) {
						return;
					}
					final File file = files[fileindex];
					if (file.getName().endsWith(".xm") || file.getName().endsWith(".mod") || file.getName().endsWith(".s3m")) {
						final Path relative = TestIBMXPlayer.this.folder.toPath().relativize(file.toPath());
						TestIBMXPlayer.this.play(relative.toString());
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

	private void play(final String fname) {
		if (this.anode != null) {
			this.anode.stop();
			this.anode = null;
		}
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
		final List<Float> removeKeys = new ArrayList<>();

		for (final Entry<Float, NoteInfo> ee : this.todispatch.entrySet()) {
			if (ee.getKey() < this.playtime) {
				final NoteInfo note = ee.getValue();
				removeKeys.add(ee.getKey());

				final int stickid = note.note % TestIBMXPlayer.STICK_COUNT;
				final int channel = note.instrumentid % 3;
				final int ledid = note.note * note.panning % 20 * 3 + channel;
				this.packet[stickid][ledid] = (byte) ((note.volume / 64f * note.globalVolume / 64f) * 128);
				this.packet[stickid][ledid + (20 * 3)] = (byte) ((note.volume / 64f * note.globalVolume / 64f) * 128);
				this.packet[stickid][ledid + (40 * 3)] = (byte) ((note.volume / 64f * note.globalVolume / 64f) * 128);
			}
		}
		for (final Float r : removeKeys) {
			this.todispatch.remove(r);
		}

		for (int stickid = 0; stickid < TestIBMXPlayer.STICK_COUNT; stickid++) {
			this.packet[stickid][0] = (byte) (stickid + 1);
			for (int i = 3; i < this.packet[stickid].length; i++) {
				this.packet[stickid][i] = (byte) Math.floor((this.packet[stickid][i] * 0.8f));
			}

			try {
				final InetAddress address = InetAddress.getByName("127.0.0.1");
				// final InetAddress address = InetAddress.getByName("192.168.23." + (stickid + 1));
				final DatagramPacket pp = new DatagramPacket(this.packet[stickid], this.packet[stickid].length, address, 2342);
				this.datagramSocket.send(pp);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
}
