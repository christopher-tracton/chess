package chess;

import java.util.ArrayList;
import sun.misc.Signal;
import java.util.Scanner;


public class Chess {

    Board board;
    boolean ok = true;
    boolean humanPlaysWhite = true;

    public Chess() {
        this.board = new Board(2);
    }

    public void interruptStop() {
        ok = false;
        board.history.dumpHistory();
    }

    public void run() {

        Signal.handle(new Signal("INT"), signal -> interruptStop());
        Scanner myScanner = new Scanner(System.in);

        while (ok) {
            board.print();
            ArrayList<ChessMove> moves = board.legalMoves();

            if (moves.isEmpty()) {
                currentPlayerLoses();
                ok = false;
            }
            else {
                this.listOutMoves(moves);
                ChessMove bestMove = this.bestMove(moves);

                if (board.whiteToPlay && humanPlaysWhite) {
                    System.out.print("Your move? ");
                    String moveString = myScanner.nextLine();
                    ChessMove myMove = new ChessMove(moveString);
                    board.move(myMove);
                }
                else {
                    board.move(bestMove);
                }
                try {
                    Thread.sleep(500);
                }
                catch(Exception e) {

                }
            }
        }
    }

    public void currentPlayerLoses() {
        System.out.println();

        if (board.whiteToPlay) {
            System.out.print("white has lost");
        }
        else {
            System.out.print("black has lost");
        }

        board.history.dumpHistory();
    }
    public void listOutMoves(ArrayList<ChessMove> moves) {
        System.out.println();

        if (board.whiteToPlay) {
            System.out.print("white moves: ");
        }
        else {
            System.out.print("black moves: ");
        }

        for (ChessMove move : moves) {
            move.print();
            System.out.print(" ");
        }

    }

    public ChessMove bestMove(ArrayList<ChessMove> moves) {
        int max = Integer.MIN_VALUE;
        ChessMove chessMove = board.bestMove(moves);

        System.out.println();

        if (board.whiteToPlay) {
            System.out.print("white's best move: " + chessMove + "\n\n" );
        }
        else {
            System.out.print("black's best move: " + chessMove + "\n\nj");
        }

        return chessMove;
    }

}


