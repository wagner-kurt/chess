/* Handles player moves and piece selection for each turn
 *
 */

import java.util.Stack;

public class Player {
    private Stack<Move> moves = new Stack<>();
    private Piece selectedPiece = null;

    public void addMove(Move m) {
        this.moves.push(m);
    }
    public Move getLastMove() {
        return this.moves.peek();
    }
    public void selectPiece(Piece p) {
        this.selectedPiece = p;
    }
    public Piece getSelectedPiece() {
        return this.selectedPiece;
    }
    public void clearPiece() {
        this.selectedPiece = null;
    }
}
