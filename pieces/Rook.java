package pieces;

import board.Square;
import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece{
    
    private boolean castling;
    
    public boolean getCastling(){
        return castling;
    }
    
    public void falseCastling(){
        castling = false;
    }
    
    public Rook(Color color, Square position) {
        super(color, position);
        castling = true;
    }

    public List<ChessPiece> calculateTargets (ChessPiece[][] board){
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
    
    public List<Square> calculateMovement(ChessPiece[][] board){
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
    
    public List<Square> calculatingProtectingSquares(ChessPiece king){
        List<Square> protectingSquares = new ArrayList<>();
        int xPosKing = king.getPosition().getX();
        int yPosKing = king.getPosition().getY();
        int xPosThis = this.getPosition().getX();
        int yPosThis = this.getPosition().getY();
        if (xPosKing==xPosThis){
            if (yPosKing<yPosThis){
                int yPos = yPosKing+1;
                while (yPos<yPosThis){
                    protectingSquares.add(new Square(xPosThis, yPos));
                    yPos++;
                }
            } else {
                int yPos = yPosThis+1;
                while (yPos<yPosKing){
                    protectingSquares.add(new Square(xPosThis, yPos));
                    yPos++;
                }
            }
        } else {
            if (xPosKing<xPosThis){
                int xPos = xPosKing+1;
                while (xPos<xPosThis){
                    protectingSquares.add(new Square(xPos, yPosThis));
                    xPos++;
                }
            } else {
                int xPos = xPosThis+1;
                while (xPos<xPosKing){
                    protectingSquares.add(new Square(xPos, yPosThis));
                    xPos++;
                }
            }
        }
        return protectingSquares;
    }
}
