package ibxm.jme;

import ibxm.Channel;
import ibxm.IBXM;
import ibxm.INoteListener;
import ibxm.Instrument;
import ibxm.Module;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class IBXMAdvancedLoader {
	private static final int	SAMPLE_RATE	= 18000, FADE_SECONDS = 16, REVERB_MILLIS = 50;
	private int					duration;
	private Module				module;
	private IBXM				ibxm;
	private Thread				playThread;
	private volatile boolean	playing;
	protected SourceDataLine	audioLine;

	public IBXMAdvancedLoader(final File assetInfo, final INoteListener listener) throws IOException {
		if (assetInfo == null) {
			throw new RuntimeException("Supplied assetinfo invalid " + assetInfo);
		}

		// module loading
		final InputStream inputStream = new FileInputStream(assetInfo);
		try {
			this.module = new Module(inputStream);
			final IBXM ibxm = new IBXM(this.module, IBXMAdvancedLoader.SAMPLE_RATE);
			ibxm.setInterpolation(Channel.NEAREST);
			this.duration = ibxm.calculateSongDuration();
			final String songName = this.module.songName.trim();
			System.out.println("Loading " + songName);
			System.out.println("Song size estimated: " + (ibxm.calculateSongDuration() / IBXMAdvancedLoader.SAMPLE_RATE));
			final Instrument[] instruments = this.module.instruments;
			for (int idx = 0, len = instruments.length; idx < len; idx++) {
				final String name = instruments[idx].name;
				if (name.trim().length() > 0) {
					System.out.println("Instrument:" + String.format("%03d: %s", idx, name));
				}
			}
			this.ibxm = ibxm;
			this.ibxm.addNoteListener(listener);

		} finally {
			inputStream.close();
		}

		try {
			this.playing = true;
			this.playThread = new Thread(new Runnable() {
				@Override
				public void run() {
					final int[] mixBuf = new int[IBXMAdvancedLoader.this.ibxm.getMixBufferLength()];
					final byte[] outBuf = new byte[mixBuf.length * 2];
					AudioFormat audioFormat = null;
					IBXMAdvancedLoader.this.audioLine = null;
					try {
						audioFormat = new AudioFormat(IBXMAdvancedLoader.SAMPLE_RATE, 16, 2, true, true);
						IBXMAdvancedLoader.this.audioLine = AudioSystem.getSourceDataLine(audioFormat);
						IBXMAdvancedLoader.this.audioLine.open(audioFormat, 8000);
						IBXMAdvancedLoader.this.audioLine.start();
						while (IBXMAdvancedLoader.this.playing) {

							final int count = IBXMAdvancedLoader.this.ibxm.getAudio(mixBuf);
							int outIdx = 0;
							for (int mixIdx = 0, mixEnd = count * 2; mixIdx < mixEnd; mixIdx++) {
								int ampl = mixBuf[mixIdx];
								if (ampl > 32767) {
									ampl = 32767;
								}
								if (ampl < -32768) {
									ampl = -32768;
								}
								outBuf[outIdx++] = (byte) (ampl >> 8);
								outBuf[outIdx++] = (byte) ampl;
							}
							IBXMAdvancedLoader.this.audioLine.write(outBuf, 0, outIdx);
							if (IBXMAdvancedLoader.this.ibxm.isEnd()) {
								IBXMAdvancedLoader.this.playing = false;
							}
						}
						IBXMAdvancedLoader.this.audioLine.drain();
					} catch (final Exception e) {
						e.printStackTrace();
					} finally {
						if (IBXMAdvancedLoader.this.audioLine != null && IBXMAdvancedLoader.this.audioLine.isOpen()) {
							IBXMAdvancedLoader.this.audioLine.close();
							IBXMAdvancedLoader.this.playing = false;
						}
					}
				}
			});
			this.playThread.start();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void terminate() {
		this.playing = false;
		System.out.println("Before join");
		try {
			this.playThread.join();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		this.playThread.checkAccess();
	}

	public boolean isFinsihed() {
		return !this.playing;
	}

	public int determineTracks() {
		return this.ibxm.determineTrackCount();
	}
}
