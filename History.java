package chess;

import java.util.ArrayList;

public class History {
    ArrayList<ChessMove> theGame;

    public History () {
        theGame = new ArrayList<ChessMove>();
    }
    public History (History historyToCopy) {
        theGame = new ArrayList<ChessMove>();

        for (ChessMove move : historyToCopy.theGame) {
            theGame.add(move);
        }
    }
    public void add(ChessMove nextMove) {
        theGame.add(nextMove);
    }

    public void dumpHistory() {
        System.out.print("\n\n");
        System.out.print("white\t\tblack\n");
        System.out.print("----------------------\n");
        for (int index = 0; index < theGame.size(); index += 2) {
            if (theGame.size() > index+1) {
                System.out.print(theGame.get(index) + "\t" + theGame.get(index+1) + "\n");
            }
            else {
                System.out.print(theGame.get(index) + "\n");
            }
        }
    }
}
