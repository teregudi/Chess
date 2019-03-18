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
                    System.out.print(board[i][j].getSign());
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
        
        //collecting friendly and hostile pieces, then shuffling the friendly for random order
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
        Collections.shuffle(friendlyPieces);
        
        //instantiating friendly king
        ChessPiece friendlyKing = null;
        int i = 0;
        while (i<friendlyPieces.size() && !(friendlyPieces.get(i) instanceof King)){
            i++;
        }
        friendlyKing = friendlyPieces.get(i);
        
        //calculating if it is check or not, and reacting to it
        List<ChessPiece> threats = new ArrayList();
        for (ChessPiece hostilePiece : hostilePieces) {
            if (hostilePiece.canCaptureKing(board, friendlyKing)){
                threats.add(hostilePiece);
                System.out.println("CHECK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
        if (threats.size()==1){
            ChessPiece threat = threats.get(0);
            for (ChessPiece piece : friendlyPieces) {
                if (!(piece instanceof King)){
                    List<ChessPiece> targets = gatherLegalTargets(piece, hostilePieces, friendlyKing);
                    if (targets.contains(threat)){
                        board[threat.getPosition().getX()][threat.getPosition().getY()] = piece;
                        board[piece.getPosition().getX()][piece.getPosition().getY()] = null;
                        piece.setPosition(threat.getPosition());
                        System.out.println("1111");
                        System.out.println(piece + " captures " + threat);
                        return true;
                    }
                }
            }
            if (threat instanceof Bishop || threat instanceof Rook || threat instanceof Queen){
                List<Square> squaresBetweenKingAndThreat =
                        calculateSquaresInCheck(friendlyKing, threat);
                if (squaresBetweenKingAndThreat!=null){
                    for (ChessPiece piece : friendlyPieces) {
                        if (!(piece instanceof King)){
                            List<Square> legalSquares = gatherLegalMoves(piece, hostilePieces, friendlyKing);
                            if (tryToIntervene(legalSquares, squaresBetweenKingAndThreat, piece)){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        if (!threats.isEmpty()){
            if (!attemptToCapture(friendlyKing, hostilePieces, friendlyKing)){
                return attemptToMove(friendlyKing, hostilePieces, friendlyKing);
            } else return true;
        }
        
        //attempting to capture or move
        for (ChessPiece actualPiece : friendlyPieces) {
            if (attemptToCapture(actualPiece, hostilePieces, friendlyKing)) return true;
        }
        for (ChessPiece actualPiece : friendlyPieces) {
            if (attemptToMove(actualPiece, hostilePieces, friendlyKing)) return true;
        }
        return false;
    }
    
    private boolean attemptToCapture(ChessPiece activePiece, List<ChessPiece> hostilePieces, ChessPiece king){
        List<ChessPiece> realTargets = gatherLegalTargets(activePiece, hostilePieces, king);
            if (!realTargets.isEmpty()){
                int random = (int)(Math.random()*realTargets.size());
                ChessPiece victim = realTargets.get(random);
                board[victim.getPosition().getX()][victim.getPosition().getY()] = activePiece;
                board[activePiece.getPosition().getX()][activePiece.getPosition().getY()] = null;
                activePiece.setPosition(victim.getPosition());
                System.out.println(activePiece + " captures " + victim);
                return true;
            }
        return false;
    }
    
    private boolean attemptToMove(ChessPiece activePiece, List<ChessPiece> hostilePieces, ChessPiece king){
        List<Square> realSquares = gatherLegalMoves(activePiece, hostilePieces, king);
            if (!realSquares.isEmpty()){
                int random = (int)(Math.random()*realSquares.size());
                Square newSquare = realSquares.get(random);
                board[newSquare.getX()][newSquare.getY()] = activePiece;
                board[activePiece.getPosition().getX()][activePiece.getPosition().getY()] = null;
                activePiece.setPosition(newSquare);
                System.out.println(activePiece + " moves to " + newSquare);
                return true;
            }
        return false;
    }
    
    private List<ChessPiece> gatherLegalTargets(ChessPiece activePiece, List<ChessPiece> hostilePieces, ChessPiece king){
        List<ChessPiece> possibleTargets = activePiece.calculateTargets(board);
        List<ChessPiece> realTargets = new ArrayList<>();
        if (!possibleTargets.isEmpty()){
            Collections.shuffle(possibleTargets);
            for (ChessPiece possTarget : possibleTargets) {
                int allClear = 0;
                board[possTarget.getPosition().getX()][possTarget.getPosition().getY()] = activePiece;
                board[activePiece.getPosition().getX()][activePiece.getPosition().getY()] = null;
                for (ChessPiece hostPiece : hostilePieces) {
                    if (activePiece instanceof King && hostPiece instanceof King){
                        if (!((King)hostPiece).kingVersusKing(possTarget.getPosition())){
                            allClear++;
                        }
                        continue;
                    }
                    if (hostPiece.equals(possTarget)){
                        allClear++;
                        continue;
                    }
                    if (!hostPiece.canCaptureKing(board, king)){
                        allClear++;
                    }
                }
                board[possTarget.getPosition().getX()][possTarget.getPosition().getY()] = possTarget;
                board[activePiece.getPosition().getX()][activePiece.getPosition().getY()] = activePiece;
                if (allClear==hostilePieces.size()){
                    realTargets.add(possTarget);
                }
            }
        }
        return realTargets;
    }
    
    private List<Square> gatherLegalMoves(ChessPiece activePiece, List<ChessPiece> hostilePieces, ChessPiece king){
        List<Square> possibleSquares = activePiece.calculateMovement(board);
        List<Square> realSquares = new ArrayList<>();
        if (!possibleSquares.isEmpty()){
            Collections.shuffle(possibleSquares);
            for (Square possSquare : possibleSquares) {
                int allClear = 0;
                board[possSquare.getX()][possSquare.getY()] = activePiece;
                board[activePiece.getPosition().getX()][activePiece.getPosition().getY()] = null;
                for (ChessPiece hostPiece : hostilePieces) {
                    if (activePiece instanceof King && hostPiece instanceof King){
                        if (!((King)hostPiece).kingVersusKing(possSquare)){
                            allClear++;
                        }
                        continue;
                    }
                    if (!hostPiece.canCaptureKing(board, king)){
                        allClear++;
                    }
                }
                board[possSquare.getX()][possSquare.getY()] = null;
                board[activePiece.getPosition().getX()][activePiece.getPosition().getY()] = activePiece;
                if (allClear==hostilePieces.size()){
                    realSquares.add(possSquare);
                }
            }
        }
        return realSquares;
    }
    
    private boolean tryToIntervene(List<Square> legalSquares, List<Square> interveningSquares, ChessPiece piece){
        List<Square> okaySquares = new ArrayList<>();
        for (Square legalSquare : legalSquares) {
            for (Square interveningSquare : interveningSquares) {
                if (legalSquare.equals(interveningSquare)){
                    okaySquares.add(legalSquare);
                    continue;
                }
            }
        }
        if (!okaySquares.isEmpty()){
            int random = (int)(Math.random()*okaySquares.size());
            board[okaySquares.get(random).getX()][okaySquares.get(random).getY()] = piece;
            board[piece.getPosition().getX()][piece.getPosition().getY()] = null;
            piece.setPosition(okaySquares.get(random));
            System.out.println(piece + " moves to " + okaySquares.get(random));
            return true;
        }
        return false;
    }
    
    private List<Square> calculateSquaresInCheck(ChessPiece king, ChessPiece threat){
        List<Square> protectingSquares = null;
        if (threat instanceof Rook){
            protectingSquares = ((Rook)threat).calculatingProtectingSquares(king);
        }
        if (threat instanceof Bishop){
            protectingSquares = ((Bishop)threat).calculatingProtectingSquares(king);
        }
        if (threat instanceof Queen){
            protectingSquares = ((Queen)threat).calculatingProtectingSquares(king);
        }
        return protectingSquares;
    }
}