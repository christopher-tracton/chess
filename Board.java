package chess;

import chess.ChessMove;
import chess.Piece;
import chess.Position;

import java.util.ArrayList;

public class Board {

    Piece board[][];
    boolean whiteToPlay = true;

    public Board() {
        board = new Piece[8][8];
        initialSetup();
        print();
    }


    public void takeIfOpposite(ArrayList<ChessMove> retval, int row1, int col1, int row2, int col2) {
        Piece target = board[row2][col2];
        Piece source = board[row1][col1];

        if (!target.isEmpty() && (target.color != source.color)) {
            Position startPos = new Position(row1,col1);
            Position stopPos = new Position(row2, col2);
            retval.add(new ChessMove(startPos, stopPos));
        }
    }

    public void moveIfEmpty(ArrayList<ChessMove> retval, int row1, int col1, int row2, int col2) {
        Piece target = board[row2][col2];
        if (target.isEmpty()) {
            Position startPos = new Position(row1,col1);
            Position stopPos = new Position(row2, col2);
            retval.add(new ChessMove(startPos, stopPos));
        }
    }

    boolean isEmpty(Position position) {
        return board[position.r][position.c].isEmpty();
    }

    boolean isOpposite(Position position, String color) {
        return board[position.r][position.c].color != color;
    }

    ArrayList<ChessMove> pawnMoves(int row, int col, String color) {
        ArrayList<ChessMove> retval = new ArrayList();

        // white pawns in 1 can move 2 if empty
        // black pawns in 6 can move 2 if empty
        // otherwise can move 1 if empty
        // can take diagnoally 1 if opposite color
        if (color == "black") {
            if (row > 0) {
                this.moveIfEmpty(retval,row,col,row-1,col);

                if (col > 0) {
                    this.takeIfOpposite(retval,row,col,row-1,col-1);
                }

                if (col < 7) {
                    this.takeIfOpposite(retval,row,col,row-1,col+1);
                }
            }
            if (row == 6) {
                Piece target = board[row-1][col];
                if (target.isEmpty()) {
                    this.moveIfEmpty(retval,row,col,row-2,col);
                }
            }
        } else if (color == "white") {
            if (row < 7) {
                this.moveIfEmpty(retval,row,col,row+1,col);

                if (col > 0) {
                    this.takeIfOpposite(retval,row,col,row+1,col-1);
                }

                if (col < 7) {
                    this.takeIfOpposite(retval,row,col,row+1,col+1);
                }
            }
            if (row == 1) {
                Piece target = board[row+1][col];
                if (target.isEmpty()) {
                    this.moveIfEmpty(retval,row,col,row+2,col);
                }
            }
        }

        return retval;
    }

    ArrayList<ChessMove> knightMoves(int row, int col, String color) {
        ArrayList<ChessMove> moves = new ArrayList();

        // a knight has eight moves: (r+1,c+2),(r+1,c-2),(r+2,c+1),(r+2,c-1),(r-1,c+2),(r-1,c-2),(r-2,c+1),(r-2,c-1)
        // some are not on the board and don't count
        // it can move if the target is empty, and take if the target is opposite

        // can this be done more elegantly?
        // generate a list of offsets, add those offsets to row and column, throw out all those with values < 0 or > 7
        // have a SINGLE function to generate a set of offsets for a given piece and location that can be filtered for "off-board" values
        // or have TWO functions board::moveOffsets(piece,row,col) and board::takeOffsets(piece,row,col)
        ArrayList<Position> offsets = new ArrayList();
        ArrayList<Position> targets = new ArrayList();

        // this would be different for each piece move to peice classes
        offsets.add(new Position(1, 2));
        offsets.add(new Position(1, -2));
        offsets.add(new Position(2, 1));
        offsets.add(new Position(2, -1));
        offsets.add(new Position(-1, 2));
        offsets.add(new Position(-1, -2));
        offsets.add(new Position(-2, 1));
        offsets.add(new Position(-2, -1));

        // for loop on offsets and add row,col.  Drop those off board
        for (Position position : offsets) {
            position.add(row,col);
            if (position.onBoard() && (this.isEmpty(position) || this.isOpposite(position,color))) {
                targets.add(position);
            }
        }

        for (Position target : targets) {
            Position start = new Position(row,col);
            ChessMove move = new ChessMove(start,target);
            moves.add(move);
        }

        return moves;
    }

    ArrayList<ChessMove> bishopMoves(int row, int col, String color) {
        System.out.println("\n in bishopMoves row " + row  + " col " + col);

        ArrayList<ChessMove> moves = new ArrayList();
        ArrayList<Position> targets = new ArrayList();

        // upper-left
        for (int steps = 1; steps < 8; steps++) {
            Position offset = new Position(steps, steps*-1);
            offset.add(row, col);
            if (!offset.onBoard() || (!this.isEmpty(offset) && !this.isOpposite(offset, color))) {
                System.out.println("\n upper left stopped at step " + steps);
                break;
            }

            targets.add(offset);

            if (!this.isEmpty(offset)) {
                break;
            }
        }

        // upper-right
        for (int steps = 1; steps < 8; steps++) {
            Position offset = new Position(steps, steps);
            offset.add(row, col);
            if (!offset.onBoard() || !this.isEmpty(offset) && !this.isOpposite(offset, color)) {
                System.out.println("\n upper right stopped at step " + steps);
                break;
            }

            targets.add(offset);

            if (!this.isEmpty(offset)) {
                break;
            }
        }

        // lower-left
        for (int steps = 1; steps < 8; steps++) {
            Position offset = new Position(steps*-1, steps*-1);
            offset.add(row, col);
            if (!offset.onBoard() || !this.isEmpty(offset) && !this.isOpposite(offset, color)) {
                System.out.println("\n lower left stopped at step " + steps);
                break;
            }

            targets.add(offset);

            if (!this.isEmpty(offset)) {
                break;
            }
        }

        // lower-right
        for (int steps = 1; steps < 8; steps++) {
            Position offset = new Position(steps*-1, steps);
            offset.add(row, col);
            if (!offset.onBoard() || !this.isEmpty(offset) && !this.isOpposite(offset, color)) {
                System.out.println("\n lower left stopped at step " + steps);
                break;
            }

            targets.add(offset);

            if (!this.isEmpty(offset)) {
                break;
            }
        }

        for (Position target : targets) {
            Position start = new Position(row,col);
            ChessMove move = new ChessMove(start,target);
            moves.add(move);
        }

        return moves;
    }


    public ArrayList<ChessMove> legalMoves () {
        ArrayList<ChessMove> retval = new ArrayList();

        String color;

        if (whiteToPlay) {
            color = "white";
        }
        else {
            color = "black";
        }


        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];

                // System.out.println("moves for " + piece.abbreviation());

                // white goes up board, black down
                if (piece.color == color) {

                    if (piece.isPawn()) {
                        retval.addAll(this.pawnMoves(row,col,color));
                    }
                    if (piece.isRook()) {
                        // System.out.println("figure moves for " + color + " " + piece.piece);
                    }
                    if (piece.isBishop()) {
                        retval.addAll(this.bishopMoves(row,col,color));
                    }
                    if (piece.isKnight()) {
                        retval.addAll(this.knightMoves(row,col,color));
                    }
                    if (piece.isQueen()) {
                        // System.out.println("figure moves for " + color + " " + piece.piece);
                    }
                    if (piece.isKing()) {
                        // System.out.println("figure moves for " + color + " " + piece.piece);
                    }
                }
            }
        }

        return retval;
    }

    public int convertToColumn(String position) {
        char row = position.charAt(0);

        int rowInt = (int)row - (int)'a';

        return rowInt;
    }

    public int convertToRow(String position) {
        char column = position.charAt(1);

        int colInt = (int)column - (int)'0' - 1;

        return colInt;
    }

    public void move(ChessMove theMove) {
        // for (int row=0; row<8; row++) {
        //     for(int col=0; col<8; col++) {
        //         System.out.println("(" + row + "," +  col + ") is " + board[row][col].abbreviation());
        //     }
        // }

        int row1 = convertToRow(theMove.start);
        int col1 = convertToColumn(theMove.start);
        int row2 = convertToRow(theMove.stop);
        int col2 = convertToColumn(theMove.stop);

        System.out.println("\n move : " + theMove.toString());
        System.out.println("\nmoving from (" + row1 + "," +  col1 + ") to (" + row2 + "," + col2 + ")");
        System.out.println("(" + row1 + "," +  col1 + ") was " + board[row1][col1].abbreviation() + " and (" + row2 + "," + col2 + ") was " + board[row2][col2].abbreviation());

        board[row2][col2] = board[row1][col1];
        board[row1][col1] = new Piece();

        System.out.println("(" + row1 + "," +  col1 + ") is now " + board[row1][col1].abbreviation() + " and (" + row2 + "," + col2 + ") is now " + board[row2][col2].abbreviation());

        whiteToPlay = !whiteToPlay;
    }

    public void print() {
        System.out.println("     A    B    C    D    E    F    G    H  ");
        System.out.println("  +----+----+----+----+----+----+----+----+");
        for (int row = 7; row >= 0; row--) {
            System.out.print(row+1 + " ");
            for (int column = 0; column < 8; column++) {
                System.out.print("| " + board[row][column].abbreviation() + " ");
            }
            System.out.println("| " + (row+1));
            System.out.println("  +----+----+----+----+----+----+----+----+");
        }
        System.out.println("     A    B    C    D    E    F    G    H  ");
        System.out.println("");
    }

    public void initialSetup() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                board[row][column] = new Piece();
            }
        }

        board[0][0] = new Piece("white","rook");
        board[0][1] = new Piece("white","knight");
        board[0][2] = new Piece("white","bishop");
        board[0][3] = new Piece("white","queen");
        board[0][4] = new Piece("white","king");
        board[0][5] = new Piece("white","bishop");
        board[0][6] = new Piece("white","knight");
        board[0][7] = new Piece("white","rook");
        board[7][0] = new Piece("black","rook");
        board[7][1] = new Piece("black","knight");
        board[7][2] = new Piece("black","bishop");
        board[7][3] = new Piece("black","queen");
        board[7][4] = new Piece("black","king");
        board[7][5] = new Piece("black","bishop");
        board[7][6] = new Piece("black","knight");
        board[7][7] = new Piece("black","rook");

        for (int column = 0; column < 8; column++) {
            board[1][column] = new Piece("white","pawn");
            board[6][column] = new Piece("black","pawn");
        }

        whiteToPlay = true;
    }

}
