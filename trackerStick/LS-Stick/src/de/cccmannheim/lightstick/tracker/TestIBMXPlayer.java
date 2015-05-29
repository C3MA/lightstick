package de.cccmannheim.lightstick.tracker;

import ibxm.INoteListener;
import ibxm.jme.IBXMAdvancedLoader;
import ibxm.jme.IBXMLoader;
import ibxm.jme.NoteInfo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.audio.AudioKey;
import com.jme3.audio.AudioNode;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
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

	private AudioNode								anode;
	private float									playtime	= -1;
	protected ConcurrentHashMap<Float, NoteInfo>	todispatch	= new ConcurrentHashMap<>();
	private Material[]								mat;

	final static int								STICK_COUNT	= 3;
	final ColorRGBA[]								sticks;

	DatagramSocket									datagramSocket;

	public TestIBMXPlayer() throws SocketException {
		this.sticks = new ColorRGBA[TestIBMXPlayer.STICK_COUNT];
		this.mat = new Material[TestIBMXPlayer.STICK_COUNT];
		for (int i = 0; i < TestIBMXPlayer.STICK_COUNT; i++) {
			this.sticks[i] = new ColorRGBA();
		}
		this.setShowSettings(false);
		this.datagramSocket = new DatagramSocket();
	}

	@Override
	public void simpleInitApp() {
		this.setPauseOnLostFocus(false);

		this.flyCam.setDragToRotate(true);
		this.assetManager.registerLoader(IBXMLoader.class, "mod");
		this.assetManager.registerLocator("de/cccmannheim/lightstick/tracker", ClasspathLocator.class);

		final AudioKey ak = new AudioKey("tiara (1).xm", true);
		final AssetInfo loadInfo = this.assetManager.locateAsset(ak);
		try {
			final IBXMAdvancedLoader al = new IBXMAdvancedLoader(loadInfo, new INoteListener() {
				@Override
				public void onNote(final float posInSec, final int note, final int volume, final int noteKey, final int fadeoutVol, final int instrumentid) {
					TestIBMXPlayer.this.todispatch.put(posInSec, new NoteInfo(note, volume, noteKey, fadeoutVol, instrumentid));
				}
			});
			this.anode = new AudioNode(al.getAudioData(), ak);
			this.anode.setPositional(false);
			this.anode.play();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		for (int stickid = 0; stickid < TestIBMXPlayer.STICK_COUNT; stickid++) {
			final Geometry geom = new Geometry("Box", new Box(0.5f, 0.5f, 0.5f));
			this.mat[stickid] = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			geom.setMaterial(this.mat[stickid]);
			geom.setLocalTranslation((stickid - TestIBMXPlayer.STICK_COUNT / 2f), 0, 0);
			this.rootNode.attachChild(geom);
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

				final int stickid = note.instrumentid % TestIBMXPlayer.STICK_COUNT;
				final int channel = note.note % 3;
				if (channel == 0) {
					this.sticks[stickid].b = note.volume / 64f * note.globalVolume / 64f;
				}
				if (channel == 1) {
					this.sticks[stickid].g = note.volume / 64f * note.globalVolume / 64f;
				}
				if (channel == 2) {
					this.sticks[stickid].r = note.volume / 64f * note.globalVolume / 64f;
				}
				System.out.println(note.note + " " + note.noteKey + " " + note.globalVolume);
			}
		}
		for (final Float r : removeKeys) {
			this.todispatch.remove(r);
		}

		for (int stickid = 0; stickid < TestIBMXPlayer.STICK_COUNT; stickid++) {

			this.sticks[stickid].r = this.sticks[stickid].r * TestIBMXPlayer.FADE_TIME;
			this.sticks[stickid].g = this.sticks[stickid].g * TestIBMXPlayer.FADE_TIME;
			this.sticks[stickid].b = this.sticks[stickid].b * TestIBMXPlayer.FADE_TIME;

			final byte[] packet = new byte[61 * 3 + 4];

			for (int i = 3; i < 60 * 3 + 4; i = i + 3) {
				packet[i] = (byte) (this.sticks[stickid].b * 128);
				packet[i + 1] = (byte) (this.sticks[stickid].r * 128);
				packet[i + 2] = (byte) (this.sticks[stickid].g * 128);
			}

			packet[0] = (byte) (stickid + 1);

			try {
				final InetAddress address = InetAddress.getByName("192.168.23." + (stickid + 1));
				final DatagramPacket pp = new DatagramPacket(packet, packet.length, address, 2342);
				this.datagramSocket.send(pp);
			} catch (final IOException e) {
				e.printStackTrace();
			}

			this.mat[stickid].setColor("Color", this.sticks[stickid]);
		}
	}
}
