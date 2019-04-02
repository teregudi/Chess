package view;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BackGroundImage extends Canvas{
    
    BufferedImage img;
    int width;
    int height;
    
    public BackGroundImage(String path, int width, int height) throws IOException{
        img = ImageIO.read(new File(path));
        this.width = width*8;
        this.height = height*8;
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(img, 0, 0, width, height, this);
    }
}
