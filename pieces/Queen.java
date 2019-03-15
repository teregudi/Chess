package pieces;

import board.Square;
import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece{
    
    public Queen(Color color, Square position, String sign) {
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
        
        xPos = this.getPosition().getX()+1;
        yPos = this.getPosition().getY()+1;
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
        
        xPos = this.getPosition().getX()+1;
        yPos = this.getPosition().getY()+1;
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
        }
        
        if (yPosKing==yPosThis){
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
        
        if (xPosKing<xPosThis && yPosKing<yPosThis){
            int xPos = xPosKing+1;
            int yPos = yPosKing+1;
            while (xPos<xPosThis && yPos<yPosThis){
                protectingSquares.add(new Square(xPos, yPos));
                xPos++;
                yPos++;
            }
        }
        
        if (xPosKing<xPosThis && yPosKing>yPosThis){
            int xPos = xPosKing+1;
            int yPos = yPosThis+1;
            while (xPos<xPosThis && yPos<yPosKing){
                protectingSquares.add(new Square(xPos, yPos));
                xPos++;
                yPos++;
            }
        }
        
        if (xPosKing>xPosThis && yPosKing<yPosThis){
            int xPos = xPosThis+1;
            int yPos = yPosKing+1;
            while (xPos<xPosKing && yPos<yPosThis){
                protectingSquares.add(new Square(xPos, yPos));
                xPos++;
                yPos++;
            }
        }
        
        if (xPosKing>xPosThis && yPosKing>yPosThis){
            int xPos = xPosThis+1;
            int yPos = yPosThis+1;
            while (xPos<xPosKing && yPos<yPosKing){
                protectingSquares.add(new Square(xPos, yPos));
                xPos++;
                yPos++;
            }
        }
        
        return protectingSquares;
    }
}
