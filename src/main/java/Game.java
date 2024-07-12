/* Stores game board,
 * Handles player turns and piece movement
 */

public class Game {
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    private Space[][] board = new Space[8][8];
    private int turn;
    private Player[] players = new Player[2];

    public Game() {
        /* Set checkered pattern of spaces on board */
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)) {
                    board[i][j] = new Space(BLACK, i, j);
                } else {
                    board[i][j] = new Space(WHITE, i, j);
                }
            }
        }

        players[WHITE] = new Player();
        players[BLACK] = new Player();

        /* Set pieces on board */
        for (int i = 0; i < 8; i++) {
            board[i][1].setPiece(new Pawn(WHITE, i, 1));
            board[i][6].setPiece(new Pawn(BLACK, i, 6));
        }
        board[0][0].setPiece(new Rook(WHITE, 0, 0));
        board[1][0].setPiece(new Knight(WHITE, 1, 0));
        board[2][0].setPiece(new Bishop(WHITE, 2, 0));
        board[3][0].setPiece(new Queen(WHITE, 3, 0));
        board[4][0].setPiece(new King(WHITE, 4, 0));
        board[5][0].setPiece(new Bishop(WHITE, 5, 0));
        board[6][0].setPiece(new Knight(WHITE, 6, 0));
        board[7][0].setPiece(new Rook(WHITE, 7, 0));

        board[0][7].setPiece(new Rook(BLACK, 0, 7));
        board[1][7].setPiece(new Knight(BLACK, 1, 7));
        board[2][7].setPiece(new Bishop(BLACK, 2, 7));
        board[3][7].setPiece(new Queen(BLACK, 3, 7));
        board[4][7].setPiece(new King(BLACK, 4, 7));
        board[5][7].setPiece(new Bishop(BLACK, 5, 7));
        board[6][7].setPiece(new Knight(BLACK, 6, 7));
        board[7][7].setPiece(new Rook(BLACK, 7, 7));

        turn = WHITE;
    }

    public int getTurn() {
        return this.turn;
    }
    public void finishTurn() {
        this.turn = (this.turn == WHITE) ? BLACK : WHITE;
    }
    public Space spaceAt(int x, int y) {
        return this.board[x][y];
    }
    public Space spaceAt(String coords) {
        assert(coords.matches("[A-Ha-h][1-8]"));
        return this.board[(int)(coords.charAt(0) - 65)][(int)(coords.charAt(1) - 49)];
    }
    public Player playerOf(int color) {
        return this.players[color];
    }

    public void movePiece(int srcX, int srcY, int destX, int destY) {
        Piece p = spaceAt(srcX, srcY).getPiece();
        spaceAt(srcX, srcY).removePiece();
        p.moveTo(destX, destY);
        spaceAt(destX, destY).setPiece(p);
    }
}