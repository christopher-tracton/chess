package chess;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    Piece board[][];
    boolean whiteToPlay = true;
    int layer = 1;
    int maxLayers;

    public Board(int maxLayers) {
        board = new Piece[8][8];
        initialSetup();
        this.maxLayers = maxLayers;
    }

    public Board(Board boardToCopy) {
        board = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = boardToCopy.board[row][col];
            }
        }

        whiteToPlay = boardToCopy.whiteToPlay;
        layer = boardToCopy.layer + 1;
    }

    public void takeIfOpposite(ArrayList<ChessMove> retval, int row1, int col1, int row2, int col2) {
        int moveValue;
        Piece target = board[row2][col2];
        Piece source = board[row1][col1];

        if (!target.isEmpty() && (target.color != source.color)) {
            Position startPos = new Position(row1,col1);
            Position stopPos  = new Position(row2,col2);

            moveValue = this.value(stopPos);
            ChessMove move = new ChessMove(startPos, stopPos, moveValue);
            move.value -= this.bestResponseValue(move);
            retval.add(move);
        }
    }

    public void moveIfEmpty(ArrayList<ChessMove> retval, int row1, int col1, int row2, int col2) {
        int moveValue;
        Piece target = board[row2][col2];


        if (target.isEmpty()) {
            Position startPos = new Position(row1,col1);
            Position stopPos = new Position(row2, col2);


            moveValue = this.value(stopPos);
            ChessMove move = new ChessMove(startPos, stopPos, moveValue);
            move.value -= this.bestResponseValue(move);
            retval.add(move);
        }
    }

    boolean isOpposite(Position position) {
        if (whiteToPlay)
            return board[position.r][position.c].color.equals("black");
        else
            return board[position.r][position.c].color.equals("white");
    }

    int value(Position position) {
        if (isOpposite(position))
            return board[position.r][position.c].value();
        else
            return 0;
    }


    ArrayList<ChessMove> pawnMoves(int row, int col, String color) {

        ArrayList<ChessMove> retval = new ArrayList();

        // white pawns in 1 can move 2 if empty
        // black pawns in 6 can move 2 if empty
        // otherwise can move 1 if empty
        // can take diagnoally 1 if opposite color
        if (color.equals("black")) {
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
        } else if (color.equals("white")) {
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


    ArrayList<ChessMove> pieceMoves(int row, int col) {

//        System.out.println("\n in bishopMoves row " + row  + " col " + col);
        int moveValue = 0;

        ArrayList<ChessMove> moves = new ArrayList();
        ArrayList<Position> targets = new ArrayList();

        Piece piece = board[row][col];
        ArrayList<Position> directions = piece.directions();
        int maxSteps = piece.maxSteps();

        for (Position direction: directions) {
            for (int steps = 1; steps < maxSteps; steps++) {
                Position offset = direction.mutli(steps);
                offset = offset.add(row, col);

                if (!offset.onBoard()) {
                    break;
                }

                Piece pieceAtOffset = board[offset.r][offset.c];

                if (!pieceAtOffset.isEmpty() && !this.isOpposite(offset)) {
                    break;
                }

                targets.add(offset);

                if (!pieceAtOffset.isEmpty()) {
                    break;
                }
            }
        }

        for (Position target : targets) {
            moveValue = this.value(target);
            Position start = new Position(row,col);
            ChessMove move = new ChessMove(start,target,moveValue);
            move.value -= this.bestResponseValue(move);

            moves.add(move);
        }

        return moves;
    }

    public ChessMove bestMove(ArrayList<ChessMove> moves) {
        int max = Integer.MIN_VALUE;
        ArrayList<ChessMove> chessMoves = new ArrayList<ChessMove>();

        for (ChessMove move : moves) {
            if (move.value > max) {
                max = move.value;
                chessMoves.clear();
                chessMoves.add(move);
            }
            if (move.value == max) {
                chessMoves.add(move);
            }
        }

        Random rand = new Random();
        return chessMoves.get(rand.nextInt(chessMoves.size()));
    }

    public int bestResponseValue(ChessMove possibleMove) {
        int bestResponseValue = 0;

        if (this.layer < this.maxLayers) {
            Board possibleBoard = new Board(this);
            possibleBoard.move(possibleMove);
            ArrayList<ChessMove> possibleResponses = possibleBoard.legalMoves();
            ChessMove bestResponse = this.bestMove(possibleResponses);
            bestResponseValue = bestResponse.value;

            if (bestResponseValue > 0) {
                System.out.print("\nafter " + possibleMove + "opponent is able to " + bestResponse + " with value " + bestResponseValue);
            }
        }

        return bestResponseValue;
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
                if (piece.color.equals(color)) {

                    if (piece.isPawn()) {
                        retval.addAll(this.pawnMoves(row,col,color));
                    } else if (!piece.isEmpty()) {
                        retval.addAll(this.pieceMoves(row,col));
                    }
                }
            }
        }

        return retval;
    }

    public int convertToColumn(String position) {
        char row = position.charAt(0);

        return (int)row - (int)'a';
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

//        System.out.println("\n move : " + theMove.toString());
//        System.out.println("\nmoving from (" + row1 + "," +  col1 + ") to (" + row2 + "," + col2 + ")");
//        System.out.println("(" + row1 + "," +  col1 + ") was " + board[row1][col1].abbreviation() + " and (" + row2 + "," + col2 + ") was " + board[row2][col2].abbreviation());

        board[row2][col2] = board[row1][col1];
        board[row1][col1] = new Piece();

//        System.out.println("(" + row1 + "," +  col1 + ") is now " + board[row1][col1].abbreviation() + " and (" + row2 + "," + col2 + ") is now " + board[row2][col2].abbreviation());

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
