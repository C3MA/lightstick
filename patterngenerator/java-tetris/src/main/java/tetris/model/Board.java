package tetris.model;

import java.awt.Point;

public class Board {

	private static final int	DROP_X		= 5;
	private static final int	DROP_Y		= 19;

	public static final int		WIDTH		= 24;
	public static final int		HEIGHT		= 60;

	private Point				pieceCenter	= new Point(Board.DROP_X, Board.DROP_Y);

	private Piece				currentPiece;

	private BoardCell[][]		board		= new BoardCell[Board.WIDTH][Board.HEIGHT];

	private int					fullLines	= 0;

	public Board() {
		this.board = this.createEmptyBoard();
	}

	public int getWidth() {
		return Board.WIDTH;
	}

	public int getHeight() {
		return Board.HEIGHT;
	}

	public int getFullLines() {
		return this.fullLines;
	}

	public BoardCell getBoardAt(final int x, final int y) {
		return this.board[x][y];
	}

	private boolean isRowFull(final int y) {
		for (int x = 0; x < Board.WIDTH; x++) {
			if (this.getBoardAt(x, y).isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public void removeFullRows() {
		final BoardCell[][] boardX = this.createEmptyBoard();

		int full = 0;
		for (int y = 0; y < Board.HEIGHT; y++) {
			if (this.isRowFull(y)) {
				full++;
			} else {
				this.copyRow(boardX, y, y - full);
			}
		}

		this.fullLines += full;
		this.board = boardX;
	}

	private void copyRow(final BoardCell[][] to, final int y, final int toy) {
		for (int x = 0; x < Board.WIDTH; x++) {
			to[x][toy] = this.board[x][y];
		}
	}

	private BoardCell[][] createEmptyBoard() {
		final BoardCell[][] boardX = new BoardCell[Board.WIDTH][Board.HEIGHT];

		for (int x = 0; x < Board.WIDTH; x++) {
			boardX[x] = BoardCell.getEmptyArray(Board.HEIGHT);
		}
		return boardX;
	}

	public void rotate() {
		final Piece rot = this.currentPiece.rotate();
		if (this.fit(rot.getPoints(), 0, 0)) {

			this.currentPiece = rot;
		}
	}

	public void moveLeft() {
		if (this.fit(this.currentPiece.getPoints(), -1, 0)) {
			this.mv(-1, 0);
		}
	}

	public void moveRight() {
		if (this.fit(this.currentPiece.getPoints(), 1, 0)) {
			this.mv(1, 0);
		}
	}

	public boolean canCurrentPieceMoveDown() {
		return this.fit(this.currentPiece.getPoints(), 0, -1);
	}

	public void moveDown() {
		if (this.canCurrentPieceMoveDown()) {
			this.mv(0, -1);
			this.removeFullRows();
		}
	}

	public boolean fit(final Point[] points, final int moveX, final int moveY) {
		for (final Point point : points) {
			final int x = this.pieceCenter.x + point.x + moveX;
			final int y = this.pieceCenter.y + point.y + moveY;

			if (x < 0 || x >= this.getWidth() || y >= this.getHeight() || y < 0) {
				return false;
			}

			if (!this.board[x][y].isEmpty()) {
				return false;
			}
		}

		return true;
	}

	public BoardCell[][] getBoardWithPiece() {
		final BoardCell[][] dest = new BoardCell[Board.WIDTH][Board.HEIGHT];

		for (int y = 0; y < Board.WIDTH; y++) {
			System.arraycopy(this.board[y], 0, dest[y], 0, this.board[0].length);
		}

		// add piece
		for (final Point point : this.currentPiece.getPoints()) {
			final int x = point.x + this.pieceCenter.x;
			final int y = point.y + this.pieceCenter.y;
			dest[x][y] = BoardCell.getCell(this.currentPiece.getType());
		}

		return dest;
	}

	private void addPieceToBoard() {
		for (final Point point : this.currentPiece.getPoints()) {
			final int x = this.pieceCenter.x + point.x;
			final int y = this.pieceCenter.y + point.y;
			this.board[x][y] = BoardCell.getCell(this.currentPiece.getType());
		}
	}

	private void mv(final int moveX, final int moveY) {
		this.pieceCenter = new Point(this.pieceCenter.x + moveX, this.pieceCenter.y + moveY);
	}

	public void setCurrentPiece(final Piece piece) {
		if (this.currentPiece != null) {
			this.addPieceToBoard();
		}
		this.currentPiece = piece;
		this.resetPieceCenter();
	}

	private void resetPieceCenter() {
		this.pieceCenter.x = Board.DROP_X;
		this.pieceCenter.y = Board.DROP_Y;
	}

}
