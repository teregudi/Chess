package pieces;

import board.Square;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece{
    
    private boolean isFirstMove;
    private final int DIRECTION;
    
    public Pawn(Color color, Square position, String sign) {
        super(color, position, sign);
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
                isFirstMove = false;
            }
        }
        return possibleTargets;
    }
    
    public List<Square> calculateMovement(ChessPiece[][] board){
        List<Square> possibleSquares = new ArrayList<>();
        
        int xPos = this.getPosition().getX()+DIRECTION;
        int yPos = this.getPosition().getY();
        while (true) {
            if (xPos>=0 && xPos<8 && yPos>=0 && yPos<8 && board[xPos][yPos]==null){
                possibleSquares.add(new Square(xPos, yPos));
            }
            if (!isFirstMove) break;
            isFirstMove = false;
            xPos++;
        }
        
        return possibleSquares;
    }
}
