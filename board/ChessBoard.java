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
    private boolean checkFlag;
    private List<ChessPiece> friendlyPieces;
    private List<ChessPiece> hostilePieces;
    private ChessPiece friendlyKing;
    
    public ChessPiece[][] setup(){
        board = new ChessPiece[8][8];
        board[0][0] = new Rook(Color.BLACK, new Square(0,0));
        board[0][1] = new Knight(Color.BLACK, new Square(0,1));
        board[0][2] = new Bishop(Color.BLACK, new Square(0,2));
        board[0][3] = new Queen(Color.BLACK, new Square(0,3));
        board[0][4] = new King(Color.BLACK, new Square(0,4));
        board[0][5] = new Bishop(Color.BLACK, new Square(0,5));
        board[0][6] = new Knight(Color.BLACK, new Square(0,6));
        board[0][7] = new Rook(Color.BLACK, new Square(0,7));
        board[1][0] = new Pawn(Color.BLACK, new Square(1,0));
        board[1][1] = new Pawn(Color.BLACK, new Square(1,1));
        board[1][2] = new Pawn(Color.BLACK, new Square(1,2));
        board[1][3] = new Pawn(Color.BLACK, new Square(1,3));
        board[1][4] = new Pawn(Color.BLACK, new Square(1,4));
        board[1][5] = new Pawn(Color.BLACK, new Square(1,5));
        board[1][6] = new Pawn(Color.BLACK, new Square(1,6));
        board[1][7] = new Pawn(Color.BLACK, new Square(1,7));
        
        board[6][0] = new Pawn(Color.WHITE, new Square(6,0));
        board[6][1] = new Pawn(Color.WHITE, new Square(6,1));
        board[6][2] = new Pawn(Color.WHITE, new Square(6,2));
        board[6][3] = new Pawn(Color.WHITE, new Square(6,3));
        board[6][4] = new Pawn(Color.WHITE, new Square(6,4));
        board[6][5] = new Pawn(Color.WHITE, new Square(6,5));
        board[6][6] = new Pawn(Color.WHITE, new Square(6,6));
        board[6][7] = new Pawn(Color.WHITE, new Square(6,7));
        board[7][0] = new Rook(Color.WHITE, new Square(7,0));
        board[7][1] = new Knight(Color.WHITE, new Square(7,1));
        board[7][2] = new Bishop(Color.WHITE, new Square(7,2));
        board[7][3] = new Queen(Color.WHITE, new Square(7,3));
        board[7][4] = new King(Color.WHITE, new Square(7,4));
        board[7][5] = new Bishop(Color.WHITE, new Square(7,5));
        board[7][6] = new Knight(Color.WHITE, new Square(7,6));
        board[7][7] = new Rook(Color.WHITE, new Square(7,7));
        
        checkFlag = false;
        
        friendlyPieces = new ArrayList();
        hostilePieces = new ArrayList<>();
        
        return board;
    }
    
    public ChessPiece[][] getBoard(){
        return board;
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
   
    public boolean letTheRoundBegin (Color color){
        
        //collecting friendly and hostile pieces, then shuffling the friendly for random order
        friendlyPieces.clear();
        hostilePieces.clear();
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
        int i = 0;
        while (i<friendlyPieces.size() && !(friendlyPieces.get(i) instanceof King)){
            i++;
        }
        friendlyKing = friendlyPieces.get(i);
        
        //calculating if it is check or not
        List<ChessPiece> threats = new ArrayList();
        for (ChessPiece hostilePiece : hostilePieces) {
            if (hostilePiece.canCaptureKing(board, friendlyKing)){
                threats.add(hostilePiece);
            }
        }
        if (!threats.isEmpty()){
            return handleCheck(threats);
        }
        
        //if there's no check, regular round takes place
        checkFlag = false;
        for (ChessPiece actualPiece : friendlyPieces) {
            if (attemptToCapture(actualPiece)) return true;
        }
        for (ChessPiece actualPiece : friendlyPieces) {
            if (attemptToMove(actualPiece)) return true;
        }
        return false;
    }
    
    private boolean handleCheck(List<ChessPiece> threats){
        checkFlag = true;
        System.out.println("CHECK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //try to capture the threat
        if (threats.size()==1){
            for (ChessPiece actualPiece : friendlyPieces) {
                if (!(actualPiece instanceof King)){
                    List<ChessPiece> targets = gatherLegalTargets(actualPiece);
                    if (targets.contains(threats.get(0))){
                        capturePiece(actualPiece, threats);
                        return true;
                    }
                }
            }
            //try to move between king and threat
            if (threats.get(0) instanceof Bishop || threats.get(0) instanceof Rook ||
                    threats.get(0) instanceof Queen){
                List<Square> squaresBetweenKingAndThreat = calculateSquaresInCheck(threats.get(0));
                if (squaresBetweenKingAndThreat!=null){
                    for (ChessPiece actualPiece : friendlyPieces) {
                        List<Square> legalSquares = gatherLegalMoves(actualPiece);
                        if (tryToIntervene(legalSquares, squaresBetweenKingAndThreat, actualPiece)){
                            return true;
                        }
                    }
                }
            }
        }
        //king tries to handle check on his own
        return attemptToCapture(friendlyKing)? true : attemptToMove(friendlyKing);
    }
    
    private boolean attemptToCapture(ChessPiece activePiece){
        List<ChessPiece> legalTargets = gatherLegalTargets(activePiece);
        if (!legalTargets.isEmpty()){
            capturePiece(activePiece, legalTargets);
            return true;
        }
        return false;
    }
    
    private boolean attemptToMove(ChessPiece activePiece){
        List<Square> legalSquares = gatherLegalMoves(activePiece);
        if (activePiece instanceof King && !checkFlag && ((King)activePiece).getCastling()){
            legalSquares = possibleCastling(activePiece, legalSquares);
        }
        if (!legalSquares.isEmpty()){
            movePiece(activePiece, legalSquares);
            return true;
        }
        return false;
    }
    
    private List<ChessPiece> gatherLegalTargets(ChessPiece activePiece){
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
                    if (!hostPiece.canCaptureKing(board, friendlyKing)){
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
    
    private List<Square> gatherLegalMoves(ChessPiece activePiece){
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
                    if (!hostPiece.canCaptureKing(board, friendlyKing)){
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
    
    private void capturePiece(ChessPiece activePiece, List<ChessPiece> legalTargets){
        int random = (int)(Math.random()*legalTargets.size());
        ChessPiece victim = legalTargets.get(random);
        board[victim.getPosition().getX()][victim.getPosition().getY()] = activePiece;
        board[activePiece.getPosition().getX()][activePiece.getPosition().getY()] = null;
        activePiece.setPosition(victim.getPosition());
        System.out.println(activePiece + " captures " + victim);
        if (activePiece instanceof Pawn && (activePiece.getPosition().getX()==0 ||
                activePiece.getPosition().getX()==7)){
            promotion(activePiece);
        }
        if (activePiece instanceof King || activePiece instanceof Rook){
            try {
                ((King)activePiece).falseCastling();
            } catch (Exception e){
                ((Rook)activePiece).falseCastling();
            }
        }
    }
    
    private void movePiece(ChessPiece activePiece, List<Square> legalSquares){
        int random = (int)(Math.random()*legalSquares.size());
        Square newSquare = legalSquares.get(random);
        board[newSquare.getX()][newSquare.getY()] = activePiece;
        board[activePiece.getPosition().getX()][activePiece.getPosition().getY()] = null;
        activePiece.setPosition(newSquare);
        System.out.println(activePiece + " moves to " + newSquare);
        if (activePiece instanceof Pawn && (activePiece.getPosition().getX()==0 ||
                activePiece.getPosition().getX()==7)){
            promotion(activePiece);
        }
        if (activePiece instanceof King || activePiece instanceof Rook){
            try {
                ((King)activePiece).falseCastling();
            } catch (Exception e){
                ((Rook)activePiece).falseCastling();
            }
        }
    }
    
    private boolean tryToIntervene(List<Square> legalSquares, List<Square> interveningSquares,
            ChessPiece activePiece){
        List<Square> okaySquares = new ArrayList<>();
        for (Square legalSquare : legalSquares) {
            for (Square interveningSquare : interveningSquares) {
                if (legalSquare.equals(interveningSquare)){
                    okaySquares.add(legalSquare);
                }
            }
        }
        if (!okaySquares.isEmpty()){
            movePiece(activePiece, okaySquares);
            return true;
        }
        return false;
    }
    
    private List<Square> calculateSquaresInCheck(ChessPiece threat){
        List<Square> protectingSquares = null;
        if (threat instanceof Rook){
            protectingSquares = ((Rook)threat).calculatingProtectingSquares(friendlyKing);
        }
        if (threat instanceof Bishop){
            protectingSquares = ((Bishop)threat).calculatingProtectingSquares(friendlyKing);
        }
        if (threat instanceof Queen){
            protectingSquares = ((Queen)threat).calculatingProtectingSquares(friendlyKing);
        }
        return protectingSquares;
    }
    
    private void promotion(ChessPiece pawn){
        int choice = (int)(Math.random()*8+1);
        ChessPiece piece = null;
        switch (choice){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5: piece = new Queen(pawn.getColor(), pawn.getPosition());
                    board[pawn.getPosition().getX()][pawn.getPosition().getY()] = piece;
                    break;
            case 6: piece = new Rook(pawn.getColor(), pawn.getPosition());
                    board[pawn.getPosition().getX()][pawn.getPosition().getY()] = piece;
                    break;
            case 7: piece = new Bishop(pawn.getColor(), pawn.getPosition());
                    board[pawn.getPosition().getX()][pawn.getPosition().getY()] = piece;
                    break;
            case 8: piece = new Knight(pawn.getColor(), pawn.getPosition());
                    board[pawn.getPosition().getX()][pawn.getPosition().getY()] = piece;
                    break;
        }
        System.out.println(pawn+" has been promoted to "+piece);
    }
    
    private List<Square> possibleCastling(ChessPiece activePiece, List<Square> legalSquares){
        int xPos = activePiece.getPosition().getX();
        int yPos = activePiece.getPosition().getY();
        
        if (board[xPos][yPos-1]==null && board[xPos][yPos-2]==null && board[xPos][yPos-3]==null &&
                board[xPos][0] instanceof Rook && ((Rook)board[xPos][0]).getCastling() &&
                legalSquares.contains(new Square(xPos, yPos-1))){
            int allClear = 0;
            board[xPos][yPos] = null;
            board[xPos][yPos-2] = activePiece;
            for (ChessPiece hostPiece : hostilePieces) {
                if (hostPiece instanceof King){
                    if (!((King)hostPiece).kingVersusKing(new Square(xPos, yPos-2))){
                        allClear++;
                    }
                    continue;
                }
                if (!hostPiece.canCaptureKing(board, activePiece)){
                    allClear++;
                }
            }
            board[xPos][yPos] = activePiece;
            board[xPos][yPos-2] = null;
            if (allClear==hostilePieces.size()){
                legalSquares.clear();
                legalSquares.add(new Square(xPos, yPos-2));
                ChessPiece rook = board[xPos][0];
                board[xPos][0] = null;
                board[xPos][yPos-1] = rook;
                rook.setPosition(new Square(xPos, yPos-1));
                ((King)activePiece).falseCastling();
                System.out.println("Castling");
                return legalSquares;
            }
        }
        
        if (board[xPos][yPos+1]==null && board[xPos][yPos+2]==null &&
                board[xPos][7] instanceof Rook && ((Rook)board[xPos][7]).getCastling() &&
                legalSquares.contains(new Square(xPos, yPos+1))){
            int allClear = 0;
            board[xPos][yPos] = null;
            board[xPos][yPos+2] = activePiece;
            for (ChessPiece hostPiece : hostilePieces) {
                if (hostPiece instanceof King){
                    if (!((King)hostPiece).kingVersusKing(new Square(xPos, yPos+2))){
                        allClear++;
                    }
                    continue;
                }
                if (!hostPiece.canCaptureKing(board, activePiece)){
                    allClear++;
                }
            }
            board[xPos][yPos] = activePiece;
            board[xPos][yPos+2] = null;
            if (allClear==hostilePieces.size()){
                legalSquares.clear();
                legalSquares.add(new Square(xPos, yPos+2));
                ChessPiece rook = board[xPos][7];
                board[xPos][7] = null;
                board[xPos][yPos+1] = rook;
                rook.setPosition(new Square(xPos, yPos+1));
                ((King)activePiece).falseCastling();
                System.out.println("Castling");
                return legalSquares;
            }
        }
        
        return legalSquares;
    }
}