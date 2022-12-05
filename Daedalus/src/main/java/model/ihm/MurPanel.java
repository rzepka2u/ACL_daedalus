package model.ihm;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

public class MurPanel extends JPanel{
    
    private BufferedImage imgFond;

    public MurPanel(){
        super();
        try{
            imgFond = ImageIO.read(getClass().getResource("/assets/mur.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        int tileWidth = imgFond.getWidth();
        int tileHeight = imgFond.getHeight();
        for (int y = 0; y < getHeight(); y += tileHeight) {
            for (int x = 0; x < getWidth(); x += tileWidth) {
                g2d.drawImage(imgFond, x, y, this);
            }
        }
        g2d.dispose();
    }

}