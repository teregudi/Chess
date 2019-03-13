package view;

import board.ChessBoard;
import pieces.Color;

public class ChessMain {

    public static void main(String[] args) {
        
        ChessBoard chessboard = new ChessBoard();
        Color winner = chessboard.playTheGame();
        System.out.println("The winner is " + winner);
    }
    
}
