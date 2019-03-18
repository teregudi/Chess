package pieces;

import board.Square;
import java.util.List;

public abstract class ChessPiece {
    
    private final Color COLOR;
    private Square position;
    private final String SIGN;
    
    public ChessPiece(Color color, Square position, String sign){
        this.COLOR = color;
        this.position = position;
        this.SIGN = sign;
    }
    
    public abstract List<ChessPiece> calculateTargets(ChessPiece[][] board);
    
    public abstract List<Square> calculateMovement(ChessPiece[][] board);
    
    public boolean canCaptureKing (ChessPiece[][] board, ChessPiece king){
        List<ChessPiece> possibleTargets = calculateTargets(board);
        return possibleTargets.contains(king);
    }

    public Color getColor() {
        return COLOR;
    }
    
    public String getSign(){
        return SIGN;
    }

    public Square getPosition() {
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return COLOR + " " + this.getClass().getSimpleName();
    }

}
