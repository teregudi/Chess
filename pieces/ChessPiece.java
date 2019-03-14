package pieces;

import board.Square;
import java.util.List;

public abstract class ChessPiece {
    
    private Color color;
    private Square position;
    private String sign;
    
    public ChessPiece(Color color, Square position, String sign){
        this.color = color;
        this.position = position;
        this.sign = sign;
    }
    
    public ChessPiece capture(ChessPiece[][] board) {
        List<ChessPiece> possibleTargets = calculateTargets(board);
        if (!possibleTargets.isEmpty()){
            int random = (int)(Math.random()*possibleTargets.size());
            this.setPosition(possibleTargets.get(random).getPosition());
            return possibleTargets.get(random);
        }
        return null;
    }
    
    public Square move (ChessPiece[][] board){
        List<Square> possibleSquares = calculateMovement(board);
        if (!possibleSquares.isEmpty()){
            int random = (int)(Math.random()*possibleSquares.size());
            this.setPosition(possibleSquares.get(random));
            return possibleSquares.get(random);
        }
        return null;
    }
    
    protected abstract List<ChessPiece> calculateTargets(ChessPiece[][] board);
    
    protected abstract List<Square> calculateMovement(ChessPiece[][] board);
    
    public abstract boolean canCaptureKing (ChessPiece[][] board, ChessPiece king);
    
    public abstract ChessPiece saveKingByCapture(ChessPiece[][] board, ChessPiece threat);

    public Color getColor() {
        return color;
    }
    
    public String getSign(){
        return sign;
    }

    public Square getPosition() {
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return color + " " + this.getClass().getSimpleName();
    }

}
