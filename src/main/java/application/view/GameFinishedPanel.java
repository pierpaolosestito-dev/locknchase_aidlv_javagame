package application.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameFinishedPanel extends JPanel {

	private JButton restartGame,exit;
	private Image background;
	
	public GameFinishedPanel(int type) {
		
		if(type==3) {
			this.background = new ImageIcon("src/main/java/application/resources/perso.jpg").getImage().getScaledInstance(ResponsiveManager.JFRAME_WIDTH, ResponsiveManager.JFRAME_HEIGHT, Image.SCALE_SMOOTH);
		}else if(type==4) {
			this.background = new ImageIcon("src/main/java/application/resources/vinto.jpg").getImage().getScaledInstance(ResponsiveManager.JFRAME_WIDTH, ResponsiveManager.JFRAME_HEIGHT, Image.SCALE_SMOOTH);
		}
		//this.setLayout(new BorderLayout());
		/*JLabel j = new JLabel();
		ImageIcon a= new ImageIcon("src/main/java/application/resources/first_page.jpg");
		j.setIcon(a);
		this.add(j,BorderLayout.CENTER);*/
		this.restartGame = new JButton();
		this.exit = new JButton();
		this.restartGame.setOpaque(true);
		this.restartGame.setBorderPainted(false);
		this.restartGame.setIcon(new ImageIcon("src/main/java/application/resources/restart_game_button.png"));
		this.exit.setOpaque(true);
		this.exit.setBorderPainted(false);
		this.exit.setIcon(new ImageIcon("src/main/java/application/resources/exit_game_button.png"));
		Box box1 = Box.createHorizontalBox();
		box1.add(restartGame);
		box1.add(exit);
		box1.setLocation(500,500);
		this.add(box1,BorderLayout.PAGE_END);
		initializeButtonsActions();
		this.setBackground(Color.white);
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}
	private void initializeButtonsActions() {
		this.exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame m = MainFrame.getInstance();
				m.switchContent(2);
				
			}
		});
		this.restartGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame m = MainFrame.getInstance();
				m.switchContent(1);
				
			}
		});
	}
}
