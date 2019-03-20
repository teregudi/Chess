package pieces;

import board.Square;
import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece{
    
    private boolean castling;
    
    public boolean getCastling(){
        return castling;
    }
    
    public void falseCastling(){
        castling = false;
    }
    
    public King(Color color, Square position) {
        super(color, position);
        castling = true;
    }

    public List<ChessPiece> calculateTargets (ChessPiece[][] board){
        List<ChessPiece> possibleTargets = new ArrayList<>();
        for (int xPos = this.getPosition().getX()-1; xPos <= this.getPosition().getX()+1; xPos++) {
            for (int yPos = this.getPosition().getY()-1; yPos <= this.getPosition().getY()+1; yPos++) {
                if (xPos>=0 && xPos<8 && yPos>=0 && yPos<8 && board[xPos][yPos]!=null &&
                        board[xPos][yPos].getColor()!=this.getColor()){
                    possibleTargets.add(board[xPos][yPos]);
                }
            }
        }
        return possibleTargets;
    }
    
    public List<Square> calculateMovement(ChessPiece[][] board){
        List<Square> possibleSquares = new ArrayList<>();
        for (int xPos = this.getPosition().getX()-1; xPos <= this.getPosition().getX()+1; xPos++) {
            for (int yPos = this.getPosition().getY()-1; yPos <= this.getPosition().getY()+1; yPos++) {
                if (xPos>=0 && xPos<8 && yPos>=0 && yPos<8 && board[xPos][yPos]==null){
                    possibleSquares.add(new Square(xPos, yPos));
                }
            }
        }
        return possibleSquares;
    }
    
    @Override
    public boolean canCaptureKing (ChessPiece[][] board, ChessPiece king){
        return false;
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
