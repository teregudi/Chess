package pieces;

import board.Square;
import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece{
    
    public Rook(Color color, Square position, String sign) {
        super(color, position, sign);
    }

    protected List<ChessPiece> calculateTargets (ChessPiece[][] board){
        List<ChessPiece> possibleTargets = new ArrayList<>();
        
        int xPos = this.getPosition().getX()+1;
        int yPos = this.getPosition().getY();
        while (xPos<8) {
            if (board[xPos][yPos]!=null){
                if (board[xPos][yPos].getColor()!=this.getColor()){
                    possibleTargets.add(board[xPos][yPos]);
                }
                break;
            }
            xPos++;
        }
        
        xPos = this.getPosition().getX()-1;
        yPos = this.getPosition().getY();
        while (xPos>=0) {
            if (board[xPos][yPos]!=null){
                if (board[xPos][yPos].getColor()!=this.getColor()){
                    possibleTargets.add(board[xPos][yPos]);
                }
                break;
            }
            xPos--;
        }
        
        xPos = this.getPosition().getX();
        yPos = this.getPosition().getY()+1;
        while (yPos<8) {
            if (board[xPos][yPos]!=null){
                if (board[xPos][yPos].getColor()!=this.getColor()){
                    possibleTargets.add(board[xPos][yPos]);
                }
                break;
            }
            yPos++;
        }
        
        xPos = this.getPosition().getX();
        yPos = this.getPosition().getY()-1;
        while (yPos>=0) {
            if (board[xPos][yPos]!=null){
                if (board[xPos][yPos].getColor()!=this.getColor()){
                    possibleTargets.add(board[xPos][yPos]);
                }
                break;
            }
            yPos--;
        }
        return possibleTargets;
    }
    
    protected List<Square> calculateMovement(ChessPiece[][] board){
        List<Square> possibleSquares = new ArrayList<>();
        
        int xPos = this.getPosition().getX()+1;
        int yPos = this.getPosition().getY();
        while (xPos<8){
            if (board[xPos][yPos]==null){
                possibleSquares.add(new Square(xPos, yPos));
            } else break;
            xPos++;
        }
        
        xPos = this.getPosition().getX()-1;
        yPos = this.getPosition().getY();
        while (xPos>=0){
            if (board[xPos][yPos]==null){
                possibleSquares.add(new Square(xPos, yPos));
            } else break;
            xPos--;
        }
        
        xPos = this.getPosition().getX();
        yPos = this.getPosition().getY()+1;
        while (yPos<8){
            if (board[xPos][yPos]==null){
                possibleSquares.add(new Square(xPos, yPos));
            } else break;
            yPos++;
        }
        
        xPos = this.getPosition().getX();
        yPos = this.getPosition().getY()-1;
        while (yPos>=0){
            if (board[xPos][yPos]==null){
                possibleSquares.add(new Square(xPos, yPos));
            } else break;
            yPos--;
        }
        
        return possibleSquares;
    }
    
    public boolean canCaptureKing (ChessPiece[][] board, ChessPiece king){
        List<ChessPiece> possibleTargets = calculateTargets(board);
        if (possibleTargets.contains(king)) return true;
        return false;
    }
    
    public ChessPiece saveKingByCapture(ChessPiece[][] board, ChessPiece threat){
        List<ChessPiece> possibleTargets = calculateTargets(board);
        if (possibleTargets.contains(threat)){
            this.setPosition(threat.getPosition());
            return threat;
        }
        return null;
    }
}
