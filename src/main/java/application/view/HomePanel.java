/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package application.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Utente
 */
public class HomePanel extends javax.swing.JPanel {

    /**
     * Creates new form HomePanel
     */

	private JButton startGameButton;
    private Image logoRender;
    public HomePanel() {
    	this.startGameButton = new JButton();
    	this.startGameButton.setOpaque(true);
    	this.startGameButton.setBorderPainted(false);
    	this.startGameButton.setIcon(new ImageIcon("src/main/java/application/resources/start_game_button.png"));
    	this.startGameButton.setLocation(500,500);
    	add(startGameButton);
       Image background = new ImageIcon("src/main/java/application/resources/first_page.jpg").getImage();
       this.logoRender = background.getScaledInstance(ResponsiveManager.JFRAME_WIDTH,ResponsiveManager.JFRAME_HEIGHT,Image.SCALE_SMOOTH);
       initializeClickEventOnStart();
    }
    
    private void initializeClickEventOnStart(){
        
    this.startGameButton.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
       MainFrame g = MainFrame.getInstance();
       g.switchContent(0);
        //Funzione per cambiare il pannello da quello homepage a quello del gioco
        //E tutte le operazioni per far partire il gioco
    }
});
    
    
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        g.drawImage(logoRender, 0, 0, null);
        
    }

    
    
    
    
   


   

}
