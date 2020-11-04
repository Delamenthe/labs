package bsu.rfe.java.group6.lab2.Pomoz.varC16;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImgPanel extends javax.swing.JPanel {
    BufferedImage image = null;

    public void setImage (BufferedImage img){
        image = img;
    }

    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
