package tetris.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tetris.input.KeyboardInput;
import tetris.model.Board;
import tetris.model.BoardCell;
import tetris.model.Game;
import tetris.model.PieceType;

public class Tetris extends Canvas {
	private boolean					TEST_MODE		= true;

	private Game					game			= new Game();
	private final BufferStrategy	strategy;

	private final int				BOARD_CORNER_X	= 300;
	private final int				BOARD_CORNER_Y	= 50;

	private final KeyboardInput		keyboard		= new KeyboardInput();
	private long					lastIteration	= System.currentTimeMillis();
	private DatagramSocket			datagramSocket;

	private static final int		PIECE_WIDTH		= 20;

	private static final int[][]	imageData		= new int[24][60 * 3 + 4];

	public Tetris() throws SocketException {
		this.datagramSocket = new DatagramSocket();

		final JFrame container = new JFrame("Tetris");
		final JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(800, 600));
		panel.setLayout(null);

		this.setBounds(0, 0, 800, 600);
		panel.add(this);
		this.setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);
		container.setVisible(true);

		container.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				System.exit(0);
			}
		});

		this.addKeyListener(this.keyboard);
		this.requestFocus();

		this.createBufferStrategy(2);
		this.strategy = this.getBufferStrategy();
	}

	void gameLoop() {
		while (true) {
			if (this.keyboard.newGame()) {
				this.game = new Game();
				this.game.startGame();
			}
			if (this.game.isPlaying()) {

				if (!this.game.isPaused()) {
					this.tetrisLoop();
				}
				if (this.keyboard.pauseGame()) {
					this.game.pauseGame();
				}
				try {
					Thread.sleep(20);
				} catch (final Exception e) {
				}
			}
			this.draw();
		}
	}

	void tetrisLoop() {
		if (this.game.isDropping()) {
			this.game.moveDown();
		} else if (System.currentTimeMillis() - this.lastIteration >= this.game.getIterationDelay()) {
			this.game.moveDown();
			this.lastIteration = System.currentTimeMillis();
		}
		if (this.keyboard.rotate()) {
			this.game.rotate();
		} else if (this.keyboard.left()) {
			this.game.moveLeft();
		} else if (this.keyboard.right()) {
			this.game.moveRight();
		} else if (this.keyboard.drop()) {
			this.game.drop();
		}
	}

	public void draw() {
		final Graphics2D g = this.getGameGraphics();
		this.drawEmptyBoard(g);
		this.drawHelpBox(g);
		this.drawPiecePreviewBox(g);

		if (this.game.isPlaying()) {
			this.drawCells(g);
			this.drawPiecePreview(g, this.game.getNextPiece().getType());

			if (this.game.isPaused()) {
				this.drawGamePaused(g);
			}
		}

		if (this.game.isGameOver()) {
			this.drawCells(g);
			this.drawGameOver(g);
		}

		this.drawStatus(g);
		this.drawPlayTetris(g);

		g.dispose();
		this.strategy.show();
	}

	private Graphics2D getGameGraphics() {
		return (Graphics2D) this.strategy.getDrawGraphics();
	}

	private void drawCells(final Graphics2D g) {
		final BoardCell[][] cells = this.game.getBoardCells();
		for (int i = 0; i < Board.WIDTH; i++) {
			for (int j = 0; j < Board.HEIGHT; j++) {

				final Color color = this.getBoardCellColor(cells[i][j]);
				this.drawBlock(g, this.BOARD_CORNER_X + i * 20, this.BOARD_CORNER_Y + (19 - j) * 20, color);

				Tetris.imageData[i][4 + j * 3] = color.getRed() / 2;
				Tetris.imageData[i][5 + j * 3] = color.getGreen() / 2;
				Tetris.imageData[i][6 + j * 3] = color.getBlue() / 2;

			}
		}

		for (int y = 0; y < 24; y++) {
			final int[] stick = Tetris.imageData[y];
			final byte[] ddata = new byte[stick.length];

			Tetris.copy(y, ddata);
			ddata[0] = (byte) y;
			ddata[1] = 0;
			ddata[2] = 0;
			ddata[3] = 0;

			try {
				InetAddress address = InetAddress.getByName("192.168.23." + (y + 1));
				if (this.TEST_MODE) {
					address = InetAddress.getByName("127.0.0.1");
				}
				final DatagramPacket pp = new DatagramPacket(ddata, ddata.length, address, 2342);
				this.datagramSocket.send(pp);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

	}

	static private void copy(final int stickid, final byte[] data) {
		for (int i = 4; i < data.length; i = i + 3) {
			{
				final int value = (Tetris.imageData[stickid][i]);
				data[i] = (byte) (value * 2);
			}
			{
				final int value = (Tetris.imageData[stickid][i + 1]);
				data[i + 1] = (byte) (value * 2);
			}
			{
				final int value = (Tetris.imageData[stickid][i + 2]);
				data[i + 2] = (byte) (value * 2);
			}
		}

	}

	private void drawEmptyBoard(final Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
		g.setColor(Color.GRAY);
		g.drawRect(this.BOARD_CORNER_X - 1, this.BOARD_CORNER_Y - 1, 10 * Tetris.PIECE_WIDTH + 2, 20 * Tetris.PIECE_WIDTH + 2);
	}

	private void drawStatus(final Graphics2D g) {
		g.setFont(new Font("Dialog", Font.PLAIN, 16));
		g.setColor(Color.RED);
		g.drawString(this.getLevel(), 10, 20);
		g.drawString(this.getLines(), 10, 40);
		g.drawString(this.getScore(), 20, 80);
	}

	private void drawGameOver(final Graphics2D g) {
		final Font font = new Font("Dialog", Font.PLAIN, 16);
		g.setFont(font);
		g.setColor(Color.RED);
		g.drawString("GAME OVER", 350, 550);
	}

	private void drawGamePaused(final Graphics2D g) {
		final Font font = new Font("Dialog", Font.PLAIN, 16);
		g.setFont(font);
		g.setColor(Color.YELLOW);
		g.drawString("GAME PAUSED", 350, 550);
	}

	private void drawPlayTetris(final Graphics2D g) {
		final Font font = new Font("Dialog", Font.PLAIN, 16);
		g.setFont(font);
		g.setColor(Color.RED);
		g.drawString("Play TETRIS !", 350, 500);
	}

	private String getLevel() {
		return String.format("Your level: %1s", this.game.getLevel());
	}

	private String getLines() {
		return String.format("Full lines: %1s", this.game.getLines());
	}

	private String getScore() {
		return String.format("Score     %1s", this.game.getTotalScore());
	}

	private void drawPiecePreviewBox(final Graphics2D g) {
		g.setFont(new Font("Dialog", Font.PLAIN, 16));
		g.setColor(Color.RED);
		g.drawString("Next:", 50, 420);
	}

	private void drawHelpBox(final Graphics2D g) {
		g.setFont(new Font("Dialog", Font.PLAIN, 16));
		g.setColor(Color.RED);
		g.drawString("H E L P", 50, 140);
		g.drawString("F1: Pause Game", 10, 160);
		g.drawString("F2: New Game", 10, 180);
		g.drawString("UP: Rotate", 10, 200);
		g.drawString("ARROWS: Move left/right", 10, 220);
		g.drawString("SPACE: Drop", 10, 240);
	}

	private void drawPiecePreview(final Graphics2D g, final PieceType type) {
		for (final Point p : type.getPoints()) {
			this.drawBlock(g, 60 + p.x * Tetris.PIECE_WIDTH, 380 + (3 - p.y) * 20, this.getPieceColor(type));
		}
	}

	private Color getBoardCellColor(final BoardCell boardCell) {
		if (boardCell.isEmpty()) {
			return Color.BLACK;
		}
		return this.getPieceColor(boardCell.getPieceType());
	}

	private Color getPieceColor(final PieceType pieceType) {
		switch (pieceType) {
		case I:
			return Color.RED;
		case J:
			return Color.GRAY;
		case L:
			return Color.CYAN;
		case O:
			return Color.BLUE;
		case S:
			return Color.GREEN;
		default:
			return Color.MAGENTA;
		}
	}

	private void drawBlock(final Graphics g, final int x, final int y, final Color color) {
		g.setColor(color);
		g.fillRect(x, y, Tetris.PIECE_WIDTH, Tetris.PIECE_WIDTH);
		g.drawRect(x, y, Tetris.PIECE_WIDTH, Tetris.PIECE_WIDTH);
	}

	public static void main(final String[] args) throws SocketException {
		new Tetris().gameLoop();
	}

}
