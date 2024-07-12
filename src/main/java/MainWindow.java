/* Main program
 * Handles UI and user input
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame {
    private Game game = new Game();
    private static final Color LIGHT_SQUARE_COLOR = new Color(180, 160, 110);
    private static final Color DARK_SQUARE_COLOR = new Color(130, 100, 80);
    private JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel[] xAxisLabels = new JLabel[8];
    private JLabel[] yAxisLabels = new JLabel[8];
    private JButton[][] boardSpaces = new JButton[8][8];
    private Image[][] pieceIcons = new Image[2][6];
    //private boolean boardReversed;

    public MainWindow(String title) {
        super(title);
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(9, 9));

        initBoard();
        //boardReversed = false;
        
        try {
            pieceIcons[0][0] = ImageIO.read(new File("src//main//resources//whiteKing.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            pieceIcons[0][1] = ImageIO.read(new File("src//main//resources//whiteQueen.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            pieceIcons[0][2] = ImageIO.read(new File("src//main//resources//whiteRook.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            pieceIcons[0][3] = ImageIO.read(new File("src//main//resources//whiteBishop.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            pieceIcons[0][4] = ImageIO.read(new File("src//main//resources//whiteKnight.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            pieceIcons[0][5] = ImageIO.read(new File("src//main//resources//whitePawn.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);

            pieceIcons[1][0] = ImageIO.read(new File("src//main//resources//blackKing.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            pieceIcons[1][1] = ImageIO.read(new File("src//main//resources//blackQueen.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            pieceIcons[1][2] = ImageIO.read(new File("src//main//resources//blackRook.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            pieceIcons[1][3] = ImageIO.read(new File("src//main//resources//blackBishop.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            pieceIcons[1][4] = ImageIO.read(new File("src//main//resources//blackKnight.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            pieceIcons[1][5] = ImageIO.read(new File("src//main//resources//blackPawn.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);

        } catch (IOException e) {
            System.out.println("Piece images missing");
            System.exit(1);
        }

        refreshBoard();

        setVisible(true);
    }

    private void initBoard() {
        getContentPane().add(statusLabel);

        /* Set axis labels */
        int i = 0;
        for (char c = 'A'; c < 'I'; c++) {
            JLabel l = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            xAxisLabels[i] = l;
            getContentPane().add(xAxisLabels[i]);
            i++;
        }

        /* Set board spaces */
        for (int j = 7; j > -1; j--) {
            JLabel l = new JLabel(Integer.toString(j + 1), SwingConstants.CENTER);
            yAxisLabels[j] = l;
            getContentPane().add(yAxisLabels[j]);
            for (int k = 0; k < 8; k++) {
                JButton b = new JButton();
                if ((j % 2 != 0 && k % 2 == 0) || (j % 2 == 0 && k % 2 != 0)) {
                    b.setBackground(LIGHT_SQUARE_COLOR);
                } else {
                    b.setBackground(DARK_SQUARE_COLOR);
                }
                b.addActionListener(new ButtonListener());
                boardSpaces[k][j] = b;
                getContentPane().add(boardSpaces[k][j]);
            }
        }
    }
    /*
    public void orientBoard(boolean invert) {
        if ((!boardReversed && invert) || (boardReversed && !invert)) {
            JButton[][] tempBoard = new JButton[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    tempBoard[i][j] = boardSpaces[Math.abs(i - 7)][Math.abs(j - 7)];
                }
            }
            boardSpaces = tempBoard;
            int i = 0;
            if (boardReversed) {
                for (char c = 'A'; c < 'I'; c++) {
                    xAxisLabels[i].setText(String.valueOf(c));
                    yAxisLabels[i].setText(String.valueOf(i + 1));
                    i++;
                }
            } else {
                for (char c = 'H'; c >= 'A'; c--) {
                    xAxisLabels[i].setText(String.valueOf(c));
                    yAxisLabels[i].setText(String.valueOf(Math.abs(i - 8)));
                    i++;
                }
            }
            boardReversed = !boardReversed;
        }
    }
    */

    /* Update UI board to match the board in the Game class */
    public void refreshBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.spaceAt(i, j).empty()) {
                    boardSpaces[i][j].setIcon(null);
                } else {
                    boardSpaces[i][j].setIcon(new ImageIcon(pieceIcons[game.spaceAt(i, j).getPiece().color()][game.spaceAt(i, j).getPiece().type()]));
                }
            }
        }

        statusLabel.setText((game.getTurn() == game.WHITE) ? "White to move" : "Black to move");
    }

    public static void main(String[] args) {
        MainWindow gameWindow = new MainWindow("Chess");


    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (e.getSource() == boardSpaces[i][j]) {


                        int playerTurn = game.getTurn();
                        Player player = game.playerOf(playerTurn);
                        Space selectedSpace = game.spaceAt(i, j);
                        Piece selectedPiece = selectedSpace.getPiece();
                        Piece playerSelectedPiece = player.getSelectedPiece();

                        //System.out.print((char)('A' + i) + String.valueOf(j + 1));
                        //if (selectedPiece == null) {
                        //    System.out.println(": __");
                        //} else {
                        //    System.out.println(": " + selectedPiece);
                        //}

                        if (!selectedSpace.empty()) { /* Selecting an occupied space */
                            assert selectedPiece != null;
                            if (selectedPiece.color() == playerTurn) { /* Selecting own piece */
                                if (playerSelectedPiece == null) {
                                    player.selectPiece(selectedPiece); /* Selecting own piece to move */
                                    System.out.println("Selected: " + selectedPiece);
                                } else { /* Selecting own piece as target (try castling) */
                                    /* TODO: Check for castling
                                     * King selected
                                     * Rook targeted
                                     */

                                    if (selectedPiece == playerSelectedPiece) {
                                        player.clearPiece();
                                        System.out.println("Deselected: " + selectedPiece);
                                    }
                                }

                            } else { /* Selecting opponent's piece */
                                if (playerSelectedPiece != null) { /* Capture attempt */
                                    /* TODO: Check if piece can be captured */

                                    /* TEMP: capture targeted piece without checking if valid */
                                    System.out.println(playerSelectedPiece + " captures " + selectedPiece);
                                    game.movePiece(playerSelectedPiece.x(), playerSelectedPiece.y(), selectedSpace.x(), selectedSpace.y());
                                    player.clearPiece();
                                    game.finishTurn();
                                } else {
                                    return;
                                }
                            }
                        } else { /* Selecting an empty space */
                            if (playerSelectedPiece == null) {
                                return; /* Selecting empty space with no piece currently selected */
                            } else {
                                /* TODO: Check if piece can be moved to empty space */

                                /* TEMP: move selected piece to empty space without checking if valid */
                                System.out.println("Moving " + playerSelectedPiece + " to " + Character.toString('A' + selectedSpace.x()) + Integer.toString(1 + selectedSpace.y()));
                                game.movePiece(playerSelectedPiece.x(), playerSelectedPiece.y(), selectedSpace.x(), selectedSpace.y());
                                player.clearPiece();
                                game.finishTurn();
                            }
                        }


                    }
                }
            }

            refreshBoard();
        }
    }
}
