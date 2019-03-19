package pieces;

import board.Square;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece{
    
    private boolean isFirstMove;
    private final int DIRECTION;
    
    public Pawn(Color color, Square position) {
        super(color, position);
        isFirstMove = true;
        if (this.getColor()==Color.WHITE) DIRECTION=-1;
        else DIRECTION=1;
    }

    public List<ChessPiece> calculateTargets (ChessPiece[][] board){
        List<ChessPiece> possibleTargets = new ArrayList<>();
        int xPos = this.getPosition().getX()+DIRECTION;
        for (int yPos = this.getPosition().getY()-1; yPos <= this.getPosition().getY()+1; yPos += 2) {
            if (xPos>=0 && xPos<8 && yPos>=0 && yPos<8 && board[xPos][yPos]!=null &&
                    board[xPos][yPos].getColor()!=this.getColor()){
                possibleTargets.add(board[xPos][yPos]);
            }
        }
        return possibleTargets;
    }
    
    public List<Square> calculateMovement(ChessPiece[][] board){
        List<Square> possibleSquares = new ArrayList<>();
        
        int xPos = this.getPosition().getX()+DIRECTION;
        int yPos = this.getPosition().getY();
        if (xPos>=0 && xPos<8 && board[xPos][yPos]==null){
            possibleSquares.add(new Square(xPos, yPos));
        }
        if ((this.getColor()==Color.WHITE && xPos==6) || (this.getColor()==Color.BLACK && xPos==1)){
            xPos = xPos + DIRECTION;
            if (xPos>=0 && xPos<8 && board[xPos][yPos]==null){
                possibleSquares.add(new Square(xPos, yPos));
            }
        }
        
        return possibleSquares;
    }
}
