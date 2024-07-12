/* Stores a piece occupying the space
 *
 */

public class Space {
    public final int spaceColor;
    private final int x;
    private final int y;
    private Piece piece = null;

    public Space(int color, int x, int y) {
        this.spaceColor = color;
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }
    public int y() {
        return this.y;
    }
    public boolean empty() {
        return this.piece == null;
    }
    public Piece getPiece() {
        return this.piece;
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    public void removePiece() {
        this.piece = null;
    }
}
