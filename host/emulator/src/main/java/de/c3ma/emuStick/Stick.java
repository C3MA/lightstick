package de.c3ma.emuStick;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Stick extends JPanel implements Comparable<Stick> {
	public static boolean	VERBOSE_ERROR_CHECKING	= false;
	public static int		LEDSIZE_HEIGHT			= 4;
	public static int		LEDSIZE_WIDTH			= 20;
	public static int		LEDCOUNT				= 63;
	public static int		FONTSIZE				= 12;
	public static int		LEDGAP					= 1;

	private byte			id						= -1;
	private Color[]			data;

	public Stick(final byte id) {
		final Dimension size = new Dimension(Stick.LEDSIZE_WIDTH + Stick.LEDGAP, Stick.FONTSIZE + Stick.LEDCOUNT * (Stick.LEDSIZE_HEIGHT + Stick.LEDGAP));
		this.setSize(size);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.data = new Color[Stick.LEDCOUNT];
		for (int i = 0; i < Stick.LEDCOUNT; i++) {
			this.data[i] = Color.BLUE;
		}
		this.id = id;
	}

	@Override
	public void paint(final Graphics g2) {
		super.paint(g2);
		final Graphics2D g = (Graphics2D) g2;
		g.setColor(Color.black);
		g.drawString(String.valueOf(this.id), 0, 12);

		for (int y = 0; y < Stick.LEDCOUNT; y++) {
			final int ypos = Stick.FONTSIZE + y * (Stick.LEDSIZE_HEIGHT + Stick.LEDGAP);
			g.setColor(this.data[y]);
			g.fillRect(0, ypos, Stick.LEDSIZE_WIDTH, Stick.LEDSIZE_HEIGHT);
		}
	}

	@Override
	public int compareTo(final Stick o) {
		return (int) Math.signum(this.id - o.id);
	}

	public void setLed(final int led, final float r, final float g, final float b) {
		this.data[led] = new Color(r, g, b);
	}
}
