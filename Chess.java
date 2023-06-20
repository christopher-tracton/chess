package chess;

import java.util.ArrayList;

import javax.lang.model.util.ElementScanner14;
import javax.swing.border.EmptyBorder;


public class Chess {

    Board board;
    
    public Chess() {
        this.board = new Board();
        this.listOutMoves();
        // board.acceptMoveFromConsole();
        // board.print();
        // this.listOutMoves();
        
        board.move(new ChessMove("e2","e4"));
        board.print();
        this.listOutMoves();

        board.move(new ChessMove("e7","e5"));
        board.print();
        this.listOutMoves();
    }

    public void listOutMoves() {

        if (board.whiteToPlay) {
            System.out.println();
            ArrayList<ChessMove> whiteMoves = board.legalMoves();
            System.out.print("white moves: ");
            for (ChessMove move : whiteMoves) {
                move.print();
                System.out.print(" ");
            }
        }
        else {
            System.out.println();
            ArrayList<ChessMove> blackMoves = board.legalMoves();
            System.out.print("black moves: ");
            for (ChessMove move : blackMoves) {
                move.print();
                System.out.print(" ");
            }        
        }
    }

}


