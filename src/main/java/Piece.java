/* Base class for all piece types
 * Holds type, color, position on board
 */

public class Piece {

    public static final int KING = 0;
    public static final int QUEEN = 1;
    public static final int ROOK = 2;
    public static final int BISHOP = 3;
    public static final int KNIGHT = 4;
    public static final int PAWN = 5;

    protected final int pieceType;
    protected final int pieceColor;
    protected int x;
    protected int y;

    public Piece(int type, int color, int x, int y) {
        this.pieceType = type;
        this.pieceColor = color;
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }
    public int y() {
        return this.y;
    }
    public void moveTo(int X, int Y) {
        this.x = X;
        this.y = Y;
    }
    public boolean isWhite() {
        return pieceColor == Game.WHITE;
    }
    public int type() {
        return this.pieceType;
    }
    public int color() {
        return pieceColor;
    }

    @Override
    public String toString() {
        return getPieceName(this.pieceType) + " @ " + Character.toString('A' + this.x) + Integer.toString(1 + this.y);
    }
    private String getPieceName(int p) {
        switch (p) {
            case KING:
                return "King";
            case QUEEN:
                return "Queen";
            case ROOK:
                return "Rook";
            case BISHOP:
                return "Bishop";
            case KNIGHT:
                return "Knight";
            case PAWN:
                return "Pawn";
            default:
                return "Null";
        }
    }
}
