package view;

import board.ChessBoard;
import java.awt.Container;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import pieces.ChessPiece;
import pieces.Color;

public class MainFrame extends javax.swing.JFrame {
    
    private final int SQUARE_SIZE;
    private final int ROUNDS;
    private final int TIMELAG;
    private ChessBoard chessboard;
    private ChessPiece[][] board;
    private JLabel[][] labels;
    private BackGroundImage bgImage;
    private Icon wpawn;
    private Icon wrook;
    private Icon wknight;
    private Icon wbishop;
    private Icon wking;
    private Icon wqueen;
    private Icon bpawn;
    private Icon brook;
    private Icon bknight;
    private Icon bbishop;
    private Icon bking;
    private Icon bqueen;

    public MainFrame() throws InterruptedException, IOException {
        SQUARE_SIZE = 80;
        TIMELAG = 2000;
        ROUNDS = 100;
        makingImages();
        createWindow();
        prepareField();
    }
    
    private void makingImages() throws IOException{
        bgImage = new BackGroundImage("pics/board.png",SQUARE_SIZE,SQUARE_SIZE);
        wpawn = new ImageIcon(ImageScaler.resize("pics/wpawn.jpg",SQUARE_SIZE,SQUARE_SIZE));
        wrook = new ImageIcon(ImageScaler.resize("pics/wrook.jpg",SQUARE_SIZE,SQUARE_SIZE));
        wknight = new ImageIcon(ImageScaler.resize("pics/wknight.jpg",SQUARE_SIZE,SQUARE_SIZE));
        wbishop = new ImageIcon(ImageScaler.resize("pics/wbishop.jpg",SQUARE_SIZE,SQUARE_SIZE));
        wking = new ImageIcon(ImageScaler.resize("pics/wking.jpg",SQUARE_SIZE,SQUARE_SIZE));
        wqueen = new ImageIcon(ImageScaler.resize("pics/wqueen.jpg",SQUARE_SIZE,SQUARE_SIZE));
        bpawn = new ImageIcon(ImageScaler.resize("pics/bpawn.jpg",SQUARE_SIZE,SQUARE_SIZE));
        brook = new ImageIcon(ImageScaler.resize("pics/brook.jpg",SQUARE_SIZE,SQUARE_SIZE));
        bknight = new ImageIcon(ImageScaler.resize("pics/bknight.jpg",SQUARE_SIZE,SQUARE_SIZE));
        bbishop = new ImageIcon(ImageScaler.resize("pics/bbishop.jpg",SQUARE_SIZE,SQUARE_SIZE));
        bking = new ImageIcon(ImageScaler.resize("pics/bking.jpg",SQUARE_SIZE,SQUARE_SIZE));
        bqueen = new ImageIcon(ImageScaler.resize("pics/bqueen.jpg",SQUARE_SIZE,SQUARE_SIZE));
    }
    
    private void createWindow() {
        JFrame frame = new JFrame("El ajedrez");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToPane(frame.getContentPane());
        frame.add(bgImage);
        Insets insets = frame.getInsets();
        frame.setSize((SQUARE_SIZE*8+15) + insets.left, (SQUARE_SIZE*8+40) + insets.top);
        frame.setVisible(true);
    }
    
    private void addComponentsToPane(Container pane) {
        Insets insets = pane.getInsets();
        
        labels = new JLabel[8][8];
        
        int y = 0;
        for (int i = 0; i < 8; i++) {
            int x = 0;
            for (int j = 0; j < 8; j++) {
                JLabel label = new JLabel();
                labels[i][j] = label;
                labels[i][j].setBounds(x + insets.left, y + insets.top, SQUARE_SIZE, SQUARE_SIZE);
                pane.add(label);
                x += SQUARE_SIZE;
            }
            y += SQUARE_SIZE;
        }
    }
    
    private void prepareField() throws InterruptedException{
        chessboard = new ChessBoard();
        board = chessboard.setup();
        draw(board);
        Thread.sleep(TIMELAG);
    }
    
    private void draw(ChessPiece[][] board){
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                try {
                    switch (board[i][j].getSign()){
                        case "WP": labels[i][j].setIcon(wpawn);
                                   labels[i][j].setVisible(true);
                                   break;
                        case "WR": labels[i][j].setIcon(wrook);
                                   labels[i][j].setVisible(true);
                                   break;
                        case "WL": labels[i][j].setIcon(wknight);
                                   labels[i][j].setVisible(true);
                                   break;
                        case "WB": labels[i][j].setIcon(wbishop);
                                   labels[i][j].setVisible(true);
                                   break;
                        case "WK": labels[i][j].setIcon(wking);
                                   labels[i][j].setVisible(true);
                                   break;
                        case "WQ": labels[i][j].setIcon(wqueen);
                                   labels[i][j].setVisible(true);
                                   break;
                        case "BP": labels[i][j].setIcon(bpawn);
                                   labels[i][j].setVisible(true);
                                   break;
                        case "BR": labels[i][j].setIcon(brook);
                                   labels[i][j].setVisible(true);
                                   break;
                        case "BL": labels[i][j].setIcon(bknight);
                                   labels[i][j].setVisible(true);
                                   break;
                        case "BB": labels[i][j].setIcon(bbishop);
                                   labels[i][j].setVisible(true);
                                   break;
                        case "BK": labels[i][j].setIcon(bking);
                                   labels[i][j].setVisible(true);
                                   break;
                        case "BQ": labels[i][j].setIcon(bqueen);
                                   labels[i][j].setVisible(true);
                                   break;
                    }
                } catch (NullPointerException e) {
                    labels[i][j].setVisible(false);
                }
            }
        }
    }
    
    public void playGame() throws InterruptedException{
        int counter = 1;
        do {
            boolean b = chessboard.letTheRoundBegin(Color.WHITE);
            if (!b) break;
            draw(chessboard.getBoard());
            Thread.sleep(TIMELAG);
            b = chessboard.letTheRoundBegin(Color.BLACK);
            if (!b) break;
            draw(chessboard.getBoard());
            Thread.sleep(TIMELAG);
            counter++;
        } while (counter<ROUNDS);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
