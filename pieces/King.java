package pieces;

import board.Square;
import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece{
    
    private List<ChessPiece> enemies;
    
    public void setEnemies(List<ChessPiece> enemies){
        this.enemies = enemies;
    }
    
    public King(Color color, Square position, String sign) {
        super(color, position, sign);
    }

    protected List<ChessPiece> calculateTargets (ChessPiece[][] board){
        List<ChessPiece> possibleTargets = new ArrayList<>();
        for (int xPos = this.getPosition().getX()-1; xPos <= this.getPosition().getX()+1; xPos++) {
            for (int yPos = this.getPosition().getY()-1; yPos <= this.getPosition().getY()+1; yPos++) {
                if (xPos>=0 && xPos<8 && yPos>=0 && yPos<8 && board[xPos][yPos]!=null &&
                        board[xPos][yPos].getColor()!=this.getColor()){
                    possibleTargets.add(board[xPos][yPos]);
                }
            }
        }
        List<ChessPiece> realTargets = new ArrayList<>();
        for (ChessPiece piece : possibleTargets) {
            int allClear = 0;
            for (ChessPiece enemy : enemies) {
                if (enemy instanceof King){
                    if (!((King)enemy).kingVersusKing(piece.getPosition())){
                        allClear++;
                    }
                    continue;
                }
                board[piece.getPosition().getX()][piece.getPosition().getY()] = this;
                board[this.getPosition().getX()][this.getPosition().getY()] = null;
                if (!enemy.canCaptureKing(board, this)){
                    allClear++;
                }
                board[piece.getPosition().getX()][piece.getPosition().getY()] = piece;
                board[this.getPosition().getX()][this.getPosition().getY()] = this;
            }
            if (allClear==enemies.size()){
                realTargets.add(piece);
            }
        }
        return realTargets;
    }
    
    protected List<Square> calculateMovement(ChessPiece[][] board){
        List<Square> possibleSquares = new ArrayList<>();
        
        for (int xPos = this.getPosition().getX()-1; xPos <= this.getPosition().getX()+1; xPos++) {
            for (int yPos = this.getPosition().getY()-1; yPos <= this.getPosition().getY()+1; yPos++) {
                if (xPos>=0 && xPos<8 && yPos>=0 && yPos<8 && board[xPos][yPos]==null){
                    possibleSquares.add(new Square(xPos, yPos));
                }
            }
        }
        List<Square> realSquares = new ArrayList<>();
        for (Square possSquare : possibleSquares) {
            int allClear = 0;
            for (ChessPiece enemy : enemies) {
                if (enemy instanceof King){
                    if (!((King)enemy).kingVersusKing(possSquare)){
                        allClear++;
                    }
                    continue;
                }
                board[possSquare.getX()][possSquare.getY()] = this;
                board[this.getPosition().getX()][this.getPosition().getY()] = null;
                if (!enemy.canCaptureKing(board, this)){
                    allClear++;
                }
                board[possSquare.getX()][possSquare.getY()] = null;
                board[this.getPosition().getX()][this.getPosition().getY()] = this;
            }
            if (allClear==enemies.size()){
                realSquares.add(possSquare);
            }
        }
        
        return realSquares;
    }
    
    public boolean canCaptureKing (ChessPiece[][] board, ChessPiece king){
        return false;
    }
    
    public ChessPiece saveKingByCapture(ChessPiece[][] board, ChessPiece threat){
        return null;
    }
    
    public boolean kingVersusKing(Square square){
        List<Square> possibleSquares = new ArrayList<>();
        for (int xPos = this.getPosition().getX()-1; xPos <= this.getPosition().getX()+1; xPos++) {
            for (int yPos = this.getPosition().getY()-1; yPos <= this.getPosition().getY()+1; yPos++) {
                if (xPos>=0 && xPos<8 && yPos>=0 && yPos<8){
                    possibleSquares.add(new Square(xPos, yPos));
                }
            }
        }
        if (possibleSquares.contains(square)) return true;
        return false;
    }
}
