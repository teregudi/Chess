package pieces;

import board.Square;
import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece{
    
    public Knight(Color color, Square position) {
        super(color, position);
    }

    public List<ChessPiece> calculateTargets (ChessPiece[][] board){
        List<ChessPiece> possibleTargets = new ArrayList<>();
        
        int xPos = this.getPosition().getX()-2;
        while (xPos<=this.getPosition().getX()+2) {
            int yPos = this.getPosition().getY()-1;
            while (yPos<=this.getPosition().getY()+1){
                if (xPos>=0 && xPos<8 && yPos>=0 && yPos<8 && board[xPos][yPos]!=null &&
                        board[xPos][yPos].getColor()!=this.getColor()){
                    possibleTargets.add(board[xPos][yPos]);
                }
                yPos += 2;
            }
            xPos += 4;
        }
        
        xPos = this.getPosition().getX()-1;
        while (xPos<=this.getPosition().getX()+1) {
            int yPos = this.getPosition().getY()-2;
            while (yPos<=this.getPosition().getY()+2){
                if (xPos>=0 && xPos<8 && yPos>=0 && yPos<8 && board[xPos][yPos]!=null &&
                        board[xPos][yPos].getColor()!=this.getColor()){
                    possibleTargets.add(board[xPos][yPos]);
                }
                yPos += 4;
            }
            xPos += 2;
        }
        return possibleTargets;
    }
    
    public List<Square> calculateMovement(ChessPiece[][] board){
        List<Square> possibleSquares = new ArrayList<>();
        
        int xPos = this.getPosition().getX()-2;
        while (xPos<=this.getPosition().getX()+2) {
            int yPos = this.getPosition().getY()-1;
            while (yPos<=this.getPosition().getY()+1){
                if (xPos>=0 && xPos<8 && yPos>=0 && yPos<8 && board[xPos][yPos]==null){
                    possibleSquares.add(new Square(xPos, yPos));
                }
                yPos += 2;
            }
            xPos += 4;
        }
        
        xPos = this.getPosition().getX()-1;
        while (xPos<=this.getPosition().getX()+1) {
            int yPos = this.getPosition().getY()-2;
            while (yPos<=this.getPosition().getY()+2){
                if (xPos>=0 && xPos<8 && yPos>=0 && yPos<8 && board[xPos][yPos]==null){
                    possibleSquares.add(new Square(xPos, yPos));
                }
                yPos += 4;
            }
            xPos += 2;
        }
        
        return possibleSquares;
    }
}
