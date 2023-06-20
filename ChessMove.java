package chess;

public class ChessMove {
    String start;
    String stop;

    public ChessMove (String start, String stop) {
        this.start = start;
        this.stop  = stop;
    }

    public ChessMove (Position start, Position stop) {
        this.start = start.makeString();
        this.stop  = stop.makeString();
    }

    public void print () {
        System.out.print(toString());
    }

    public String toString() {
        return this.start + "-" + this.stop;
    }

}
