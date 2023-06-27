package chess;

import java.util.ArrayList;


public class Chess {

    Board board;
    
    public Chess() {
        this.board = new Board(2);

        boolean ok = true;

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
                System.out.print("move " + bestMove.toString() + "\n\n");
                board.move(bestMove);
                try {
                    Thread.sleep(1000);
                }
                catch(Exception e) {

                }
            }
        }



        // board.acceptMoveFromConsole();
        // board.print();
        // this.listOutMoves();
        
//        board.move(new ChessMove("e2","e4"));
//        board.print();
//        this.listOutMoves();
//        this.printBestMove();
//
//        board.move(new ChessMove("e7","e5"));
//        board.print();
//        this.listOutMoves();
//        this.printBestMove();
//
//        board.move(new ChessMove("f1","c4"));
//        board.print();
//        this.listOutMoves();
//        this.printBestMove();
//
//        board.move(new ChessMove("a7","a5"));
//        board.print();
//        this.listOutMoves();
//        this.printBestMove();
//
//        board.move(new ChessMove("a2","a4"));
//        board.print();
//        this.listOutMoves();
//        this.printBestMove();
//
//        board.move(new ChessMove("d7","d5"));
//        board.print();
//        this.listOutMoves();
//        this.printBestMove();
//
//        board.move(new ChessMove("e4","d5"));
//        board.print();
//        this.listOutMoves();
//        this.printBestMove();
//
//        board.move(new ChessMove("d8","d5"));
//        board.print();
//        this.listOutMoves();
//        this.printBestMove();
    }

    public void currentPlayerLoses() {
        System.out.println();

        if (board.whiteToPlay) {
            System.out.print("white has lost");
        }
        else {
            System.out.print("black has lost");
        }
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
            System.out.print("white's best move: " + chessMove);
        }
        else {
            System.out.print("black's best move: " + chessMove);
        }

        return chessMove;
    }

}


