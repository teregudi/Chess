package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pieces.Bishop;
import pieces.ChessPiece;
import pieces.Color;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

public class ChessBoard {
    
    private ChessPiece[][] board;
    
    private void setup(){
        board = new ChessPiece[8][8];
        board[0][0] = new Rook(Color.BLACK, new Square(0,0), "BR");
        board[0][1] = new Knight(Color.BLACK, new Square(0,1), "BL");
        board[0][2] = new Bishop(Color.BLACK, new Square(0,2), "BB");
        board[0][3] = new Queen(Color.BLACK, new Square(0,3), "BQ");
        board[0][4] = new King(Color.BLACK, new Square(0,4), "BK");
        board[0][5] = new Bishop(Color.BLACK, new Square(0,5), "BB");
        board[0][6] = new Knight(Color.BLACK, new Square(0,6), "BL");
        board[0][7] = new Rook(Color.BLACK, new Square(0,7), "BR");
        board[1][0] = new Pawn(Color.BLACK, new Square(1,0), "BP");
        board[1][1] = new Pawn(Color.BLACK, new Square(1,1), "BP");
        board[1][2] = new Pawn(Color.BLACK, new Square(1,2), "BP");
        board[1][3] = new Pawn(Color.BLACK, new Square(1,3), "BP");
        board[1][4] = new Pawn(Color.BLACK, new Square(1,4), "BP");
        board[1][5] = new Pawn(Color.BLACK, new Square(1,5), "BP");
        board[1][6] = new Pawn(Color.BLACK, new Square(1,6), "BP");
        board[1][7] = new Pawn(Color.BLACK, new Square(1,7), "BP");
        
        board[6][0] = new Pawn(Color.WHITE, new Square(6,0), "WP");
        board[6][1] = new Pawn(Color.WHITE, new Square(6,1), "WP");
        board[6][2] = new Pawn(Color.WHITE, new Square(6,2), "WP");
        board[6][3] = new Pawn(Color.WHITE, new Square(6,3), "WP");
        board[6][4] = new Pawn(Color.WHITE, new Square(6,4), "WP");
        board[6][5] = new Pawn(Color.WHITE, new Square(6,5), "WP");
        board[6][6] = new Pawn(Color.WHITE, new Square(6,6), "WP");
        board[6][7] = new Pawn(Color.WHITE, new Square(6,7), "WP");
        board[7][0] = new Rook(Color.WHITE, new Square(7,0), "WR");
        board[7][1] = new Knight(Color.WHITE, new Square(7,1), "WL");
        board[7][2] = new Bishop(Color.WHITE, new Square(7,2), "WB");
        board[7][3] = new Queen(Color.WHITE, new Square(7,3), "WQ");
        board[7][4] = new King(Color.WHITE, new Square(7,4), "WK");
        board[7][5] = new Bishop(Color.WHITE, new Square(7,5), "WB");
        board[7][6] = new Knight(Color.WHITE, new Square(7,6), "WL");
        board[7][7] = new Rook(Color.WHITE, new Square(7,7), "WR");
    }
    
    private void draw(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j]!=null){
                    System.out.print(board[i][j].sign);
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    public Color playTheGame(){
        setup();
        draw();
        int counter = 1;
        do {
            System.out.println("round "+counter+", white turn");
            boolean white = letTheRoundBegin(Color.WHITE);
            if (!white) return Color.BLACK;
            draw();
            System.out.println("round "+counter+", black turn");
            boolean black = letTheRoundBegin(Color.BLACK);
            if (!black) return Color.WHITE;
            draw();
            counter++;
        } while (counter<100);
        return null;
    }
    
    private boolean letTheRoundBegin (Color color){
        
        //collecting friendly and hostile pieces
        List<ChessPiece> friendlyPieces = new ArrayList();
        List<ChessPiece> hostilePieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j]!=null){
                    if (board[i][j].getColor()==color){
                        friendlyPieces.add(board[i][j]);
                    } else {
                        hostilePieces.add(board[i][j]);
                    }
                }
            }
        }
        
        //giving all the friendly pieces to the hostile king
        int i = 0;
        while (!(hostilePieces.get(i) instanceof King) && i<hostilePieces.size()){
            i++;
        }
        ((King)hostilePieces.get(i)).setEnemies(friendlyPieces);
        
        //giving all the hostile pieces to the friendly king
        ChessPiece friendlyKing = null;
        i = 0;
        while (i<friendlyPieces.size() && !(friendlyPieces.get(i) instanceof King)){
            i++;
        }
        friendlyKing = friendlyPieces.get(i);
        ((King)friendlyKing).setEnemies(hostilePieces);
        
        //calculating if it is check or not, and reacting
        i = 0;
        while (i<hostilePieces.size() && hostilePieces.get(i).canCaptureKing(board, friendlyKing)==false){
            i++;
        }
        if (i<hostilePieces.size()){
            System.out.println("CHECK");
            if (!attemptToCapture(friendlyKing)){
                return attemptToMove(friendlyKing);
            } else return true;
        }
        
        //shuffling the pieces and attempting to capture or move
        Collections.shuffle(friendlyPieces);
        for (ChessPiece actualPiece : friendlyPieces) {
            if (attemptToCapture(actualPiece)) return true;
        }
        for (ChessPiece actualPiece : friendlyPieces) {
            if (attemptToMove(actualPiece)) return true;
        }
        return false;
    }
    
    private boolean attemptToCapture(ChessPiece activePiece){
        Square actualPosition = activePiece.getPosition();
        ChessPiece captured = activePiece.capture(board);
        if (captured!=null){
            System.out.println(activePiece + " captures " + captured);
            board[actualPosition.getX()][actualPosition.getY()] = null;
            board[captured.getPosition().getX()][captured.getPosition().getY()] = activePiece;
            return true;
        }
        return false;
    }
    
    private boolean attemptToMove(ChessPiece activePiece){
        Square actualPosition = activePiece.getPosition();
        Square newPosition = activePiece.move(board);
        if (newPosition!=null){
            System.out.println(activePiece + " moves to " + newPosition);
            board[actualPosition.getX()][actualPosition.getY()] = null;
            board[newPosition.getX()][newPosition.getY()] = activePiece;
            return true;
        }
        return false;
    }
}
