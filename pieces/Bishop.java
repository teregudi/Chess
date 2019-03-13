package pieces;

import board.Square;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece{
    
    public Bishop(Color color, Square position, String sign) {
        super(color, position, sign);
    }

    protected List<ChessPiece> calculateTargets (ChessPiece[][] board){
        
        List<ChessPiece> possibleTargets = new ArrayList<>();
        
        int xPos = this.getPosition().getX()+1;
        int yPos = this.getPosition().getY()+1;
        while (xPos<8 && yPos<8) {
            if (board[xPos][yPos]!=null){
                if (board[xPos][yPos].getColor()!=this.getColor()){
                    possibleTargets.add(board[xPos][yPos]);
                }
                break;
            }
            xPos++;
            yPos++;
        }
        
        xPos = this.getPosition().getX()-1;
        yPos = this.getPosition().getY()+1;
        while (xPos>=0 && yPos<8) {
            if (board[xPos][yPos]!=null){
                if (board[xPos][yPos].getColor()!=this.getColor()){
                    possibleTargets.add(board[xPos][yPos]);
                }
                break;
            }
            xPos--;
            yPos++;
        }
        
        xPos = this.getPosition().getX()+1;
        yPos = this.getPosition().getY()-1;
        while (xPos<8 && yPos>=0) {
            if (board[xPos][yPos]!=null){
                if (board[xPos][yPos].getColor()!=this.getColor()){
                    possibleTargets.add(board[xPos][yPos]);
                }
                break;
            }
            xPos++;
            yPos--;
        }
        
        xPos = this.getPosition().getX()-1;
        yPos = this.getPosition().getY()-1;
        while (xPos>=0 && yPos>=0) {
            if (board[xPos][yPos]!=null){
                if (board[xPos][yPos].getColor()!=this.getColor()){
                    possibleTargets.add(board[xPos][yPos]);
                }
                break;
            }
            xPos--;
            yPos--;
        }
        return possibleTargets;
    }
    
    protected List<Square> calculateMovement(ChessPiece[][] board){
        List<Square> possibleSquares = new ArrayList<>();
        
        int xPos = this.getPosition().getX()+1;
        int yPos = this.getPosition().getY()+1;
        while (xPos<8 && yPos<8){
            if (board[xPos][yPos]==null){
                possibleSquares.add(new Square(xPos, yPos));
            } else break;
            xPos++;
            yPos++;
        }
        
        xPos = this.getPosition().getX()-1;
        yPos = this.getPosition().getY()+1;
        while (xPos>=0 && yPos<8){
            if (board[xPos][yPos]==null){
                possibleSquares.add(new Square(xPos, yPos));
            } else break;
            xPos--;
            yPos++;
        }
        
        xPos = this.getPosition().getX()+1;
        yPos = this.getPosition().getY()-1;
        while (xPos<8 && yPos>=0){
            if (board[xPos][yPos]==null){
                possibleSquares.add(new Square(xPos, yPos));
            } else break;
            xPos++;
            yPos--;
        }
        
        xPos = this.getPosition().getX()-1;
        yPos = this.getPosition().getY()-1;
        while (xPos>=0 && yPos>=0){
            if (board[xPos][yPos]==null){
                possibleSquares.add(new Square(xPos, yPos));
            } else break;
            xPos--;
            yPos--;
        }
        
        return possibleSquares;
    }
    
    public boolean canCaptureKing (ChessPiece[][] board, ChessPiece king){
        List<ChessPiece> possibleTargets = calculateTargets(board);
        if (possibleTargets.contains(king)) return true;
        return false;
    }
}
