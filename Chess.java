package chess;

public class Start {

    public static void main(String[] args) {
        Chess chess = new Chess();
    }
}

public class Board {

    Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        initialSetup();
        print();
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

    public void move(String start, String stop) {
        // for (int row=0; row<8; row++) {
        //     for(int col=0; col<8; col++) {
        //         System.out.println("(" + row + "," +  col + ") is " + board[row][col].abbreviation());
        //     }
        // }

        int row1 = convertToRow(start);
        int col1 = convertToColumn(start);
        int row2 = convertToRow(stop);
        int col2 = convertToColumn(stop);

        System.out.println("moving from (" + row1 + "," +  col1 + ") to (" + row2 + "," + col2 + ")");
        System.out.println("(" + row1 + "," +  col1 + ") was " + board[row1][col1].abbreviation() + " and (" + row2 + "," + col2 + ") was " + board[row2][col2].abbreviation());

        board[row2][col2] = board[row1][col1];
        board[row1][col1] = new Piece(); 

        System.out.println("(" + row1 + "," +  col1 + ") is now " + board[row1][col1].abbreviation() + " and (" + row2 + "," + col2 + ") is now " + board[row2][col2].abbreviation());
    }

    public void print() {
        System.out.println("+----+----+----+----+----+----+----+----+");
        for (int row = 7; row >= 0; row--) {
            for (int column = 0; column < 8; column++) {
                System.out.print("| " + board[row][column].abbreviation() + " ");
            }
            System.out.println("|");
            System.out.println("+----+----+----+----+----+----+----+----+");
        }
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
    }

}

public class Chess {

    Board board;
    
    public Chess() {
        this.board = new Board();
        
        board.move("e2","e4");
        board.print();
    }

}

public class Piece {
    String color;
    String piece;
    
    public Piece(String color, String piece) {
        this.color = color;
        this.piece = piece;
    }

    public Piece() {
        this.color = "EMPTY";
        this.piece = "EMPTY";
    }

    public String shortColor() {
        if (this.color == "white") {
            return "W";
        } else if (this.color == "black") {
            return "B";
        } else {
            return " ";
        }
    }

    public String shortPiece() {
        if (this.piece == "pawn") {
            return "P";
        } else if (this.piece == "rook") {
            return "R";
        } else if (this.piece == "bishop") {
            return "B";
        } else if (this.piece == "knight") {
            return "N";
        } else if (this.piece == "queen") {
            return "Q";
        } else if (this.piece == "king") {
            return "K";
        } else {
            return " ";
        }
    }

    public String abbreviation() {
        return (shortColor() + shortPiece());
    }

}

