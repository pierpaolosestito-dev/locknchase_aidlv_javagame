
package application.view;

import application.model.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;



public class GamePanel extends javax.swing.JPanel {

    private Game game;
    private char[][] matrixToPrint;
    private Image background;
    private Image wall;
    private Image coin;
    private Image heart;
    public GamePanel() {
        initComponents();
        Image bg = new ImageIcon("src/main/java/application/resources/index2.jpg").getImage();
        
        this.background= bg.getScaledInstance(ResponsiveManager.JFRAME_WIDTH,ResponsiveManager.JFRAME_HEIGHT, Image.SCALE_SMOOTH);
        
        this.wall = new ImageIcon("src/main/java/application/resources/gameobject/wall.jpg").getImage();
        this.coin = new ImageIcon("src/main/java/application/resources/gameobject/coin.png").getImage();
        this.heart = new ImageIcon("src/main/java/application/resources/gameobject/heart.png").getImage();
        this.setBackground(Color.lightGray);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(this.background,0,0,null);
        
        game = Game.getInstance();
        matrixToPrint = game.getMappa();
        
        int constant = 0;
        for(int i=0;i<ResponsiveManager.MATRIX_ROW;i++){
            
            for(int j=0;j<ResponsiveManager.MATRIX_COLUMN;j++){
                
                if(matrixToPrint[i][j]=='L'){
             
                    g.drawImage(game.getLock().getLockImage(),(j+constant)*ResponsiveManager.GAMEOBJECT_WIDTH, (i+constant)*ResponsiveManager.GAMEOBJECT_HEIGHT, null);
                }
                if(matrixToPrint[i][j]=='0'||matrixToPrint[i][j]=='1'||matrixToPrint[i][j]=='2'||matrixToPrint[i][j]=='3'){
                    g.drawImage(game.getMappaPoliziotti().get(Character.getNumericValue(matrixToPrint[i][j])).getImg(),(j+constant)*ResponsiveManager.GAMEOBJECT_WIDTH, (i+constant)*ResponsiveManager.GAMEOBJECT_HEIGHT, null);
                }
                if(matrixToPrint[i][j]=='C'){
                    g.drawImage(coin,(j+constant)*ResponsiveManager.GAMEOBJECT_WIDTH, (i+constant)*ResponsiveManager.GAMEOBJECT_HEIGHT, null);
                }
                if(matrixToPrint[i][j]=='W'){
                  
                    g.drawImage(wall,(j+constant)*ResponsiveManager.GAMEOBJECT_WIDTH, (i+constant)*ResponsiveManager.GAMEOBJECT_HEIGHT, null);
                }
                if(matrixToPrint[i][j] == 'G') {
                	g.drawImage(game.getGem().getImg(), (j+constant)*ResponsiveManager.GAMEOBJECT_WIDTH, (i+constant)*ResponsiveManager.GAMEOBJECT_HEIGHT, null);
                }
                if(matrixToPrint[i][j] == 'K') {
                	g.drawImage(game.getCiB().getImg(), (j+constant)*ResponsiveManager.GAMEOBJECT_WIDTH, (i+constant)*ResponsiveManager.GAMEOBJECT_HEIGHT, null);
                }
            }
            drawScore(g);
    }
        
        
    }
    
    private void drawScore(Graphics g){
        try {
          
             Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/application/resources/fonts/locknchasefont.TTF")).deriveFont(2*12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    //register the font
            ge.registerFont(customFont);
            g.setFont(customFont);
            
        } catch (FontFormatException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
          g.setColor(Color.red);
         
          g.drawString("SCORE: " + game.getLock().getActualScore(), 750, 200);//750,200
          //g.drawString("T: "+game.getLock().getTentativiRimasti(), 750,240);
                 
          int constant = 0;
          int partenza = 750;
          for(int i=0;i<game.getLock().getTentativiRimasti();i++) {
        	 g.drawImage(heart, 750+constant,225,null);
        	 constant = constant + 50;
          }
    }
/*if(matrixToPrint[i][j] == 'W'){
                    g.setColor(Color.darkGray);
                }
                if(matrixToPrint[i][j] == 'C'){
                    g.setColor(Color.yellow);
                }
                if(matrixToPrint[i][j] == 'L'){
                    g.setColor(Color.black);
                    
                    
                }
                if(matrixToPrint[i][j] == 'G'){
                    g.setColor(Color.white);
                }
                g.fillRect(i*ResponsiveManager.GAMEOBJECT_WIDTH+2, j*ResponsiveManager.GAMEOBJECT_HEIGHT+2,ResponsiveManager.GAMEOBJECT_WIDTH, ResponsiveManager.GAMEOBJECT_HEIGHT);*/
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
