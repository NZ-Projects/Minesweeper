/**
 * Date: 3-05-2020
 * Description: This is a simple Java application for Minesweeper game
 * @author Zouhair IJAAD
 *
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;  
import javax.swing.JPanel;

public class SpielFeld extends JPanel implements MouseListener,  ComponentListener{
	private static final long serialVersionUID = 1L;
	private Spiel mySpiel = new Spiel();	
	private int mousePressX = 0;
	private int mousePresseY = 0;
	
	private boolean gameOver = false;
	private boolean gameWon = false;
	
	private ArrayList<Mine> JPanelMines = new ArrayList<Mine>(Positionen.MINEN);
	private ArrayList<Mine> JPanelLeers = new ArrayList<Mine>(Anzeige.X*Anzeige.Y - Positionen.MINEN);
	private boolean [][] clickedAnzeige = new boolean[Anzeige.X][Anzeige.Y]; 
	
	private int width, height;

	public SpielFeld() {
		this.addMouseListener(this);
		this.addComponentListener(this);
	}

	public void paint(Graphics gr) {
		this.width = getWidth();
		this.height = getHeight();
		
		super.paint(gr);
		gr.setColor(Color.black);
		enableAA(gr);
		drawField(gr);
		
		// If game over stop the game, display all remaining mines and set background color to RED
		if (this.gameOver) {
			drawMines(gr);
			setBackground(Color.red);
		}
		
		// If game won stop the game, display all mines and set background color to GREEN
		if (this.gameWon) {
			drawMines(gr);
			setBackground(Color.green);
		}
	}

		
	private void drawField(Graphics myGraphic) {
		int squareWidth = getWidth() / Anzeige.X, squareHeight = getHeight() / Anzeige.Y;
		int xsquare = 0, ysquare = 0;

		for (int i = 0; i < Anzeige.X; i++) {
			for (int j = 0; j < Anzeige.Y; j++) { 
				
				myGraphic.setColor(Color.black);
				mySpiel.Suche(i, j);

				xsquare = i * (squareWidth);
				ysquare = j * (squareHeight);
				myGraphic.drawRect(xsquare, ysquare, squareWidth, squareHeight);
				
				String str = String.valueOf(mySpiel.get(i, j));		
				//Draw clicked rectangles
				if (this.clickedAnzeige[i][j]) {
					// Draw clicked rectangles that have 0 neighbors mines
					if (str.equals("0")) {
						if (this.gameWon) myGraphic.setColor(Color.green);
						else if(this.gameOver) myGraphic.setColor(Color.red);
						else myGraphic.setColor(Color.orange);
						myGraphic.fillRect(xsquare+1, ysquare+1, squareWidth-1, squareHeight-1);
					}
					// Draw clicked rectangles that have 1 or more neighbors mines
					else {
						int x = i * (squareWidth) + (squareWidth / 2);
						int y = (j + 1) * (squareHeight) - (squareHeight / 2);
						
						if (this.gameWon) myGraphic.setColor(Color.green);
						else if(this.gameOver) myGraphic.setColor(Color.red);
						else myGraphic.setColor(Color.orange);
						myGraphic.fillRect(xsquare+1, ysquare+1, squareWidth-1, squareHeight-1);
						
						myGraphic.setColor(Color.black);
						myGraphic.drawString(str, x, y);				
					}
				}
				
				//Construct the JPanel mines and leers positions
				if (str.charAt(0)==Anzeige.MINE)
					JPanelMines.add(new Mine(xsquare, ysquare));
				else
					JPanelLeers.add(new Mine(xsquare, ysquare));
			}
		}
	}
	
	private void drawMines(Graphics myGraphic)
	{
		setBackground(Color.black);
		int squareWidth = getWidth() / Anzeige.X, squareHeight = getHeight() / Anzeige.Y;
		
		for(int i = 0; i < Positionen.MINEN; i++)
		{
			Mine mine = mySpiel.getMinenPositionen().get(i);
			
			int x = mine.getX() * (squareWidth) + (squareWidth / 2);
			int y = (mine.getY() + 1) * (squareHeight) - (squareHeight / 2);
			
			myGraphic.fillOval(x, y, 10, 10);
		}
	}
	
	private boolean IsMine(int x, int y) {
		int squareWidth = getWidth() / Anzeige.X;
		int squareHeight = getHeight() / Anzeige.Y;
		
		for(int i=0 ; i < JPanelMines.size() ; i++) {
			int minX = JPanelMines.get(i).getX();
			int maxX = JPanelMines.get(i).getX() + squareWidth;
			
			int minY = JPanelMines.get(i).getY();
			int maxY = JPanelMines.get(i).getY() + squareHeight;
			
			if ((minX<=x && x<=maxX) && (minY<=y && y<=maxY))
				return true;
		}
		
		return false;
	}
	
	private boolean IsWon() {
		int counter = 0;
		for (int i=0; i<this.clickedAnzeige.length; i++) {
			for(int j=0; j<this.clickedAnzeige[i].length; j++)
			if (!this.clickedAnzeige[i][j]) {
				counter++;
			}			
		}
		
		if(counter==Positionen.MINEN)
			return true;
		
		return false;
	}
	
	private void ShowNeighbors(int x, int y) {		
		while((x>=0 && x<this.clickedAnzeige.length-1) && (y>=0 && y<this.clickedAnzeige[0].length-1)) {
			char nachbarMines = mySpiel.get(x, y);
			if(nachbarMines=='0') {
				this.clickedAnzeige[x][y] = true;
				
				if(x != 0 && y != 0) {
					this.clickedAnzeige[x - 1][y - 1] = true;
					this.clickedAnzeige[x - 1][y] = true;
					this.clickedAnzeige[x - 1][y + 1] = true;
				}
				if(y != 0){
					this.clickedAnzeige[x][y - 1] = true;
					this.clickedAnzeige[x + 1][y - 1] = true;
				}				
				this.clickedAnzeige[x][y + 1] = true;
				this.clickedAnzeige[x + 1][y] = true;
				this.clickedAnzeige[x + 1][y + 1] = true;
			}
			x++;
			y++;
		}
		
		while((x>=0 && x<this.clickedAnzeige.length-1) && (y>=0 && y<this.clickedAnzeige[0].length-1)) {
			char nachbarMines = mySpiel.get(x, y);
			if(nachbarMines=='0') {
				this.clickedAnzeige[x][y] = true;
				
				if(x != 0 && y != 0) {
					this.clickedAnzeige[x - 1][y - 1] = true;
					this.clickedAnzeige[x - 1][y] = true;
					this.clickedAnzeige[x - 1][y + 1] = true;
				}
				if(y != 0){
					this.clickedAnzeige[x][y - 1] = true;
					this.clickedAnzeige[x + 1][y - 1] = true;
				}				
				this.clickedAnzeige[x][y + 1] = true;
				this.clickedAnzeige[x + 1][y] = true;
				this.clickedAnzeige[x + 1][y + 1] = true;
			}
			x--;
			y--;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!this.gameOver && !this.gameWon) {
			this.mousePressX = e.getX();
			this.mousePresseY = e.getY();

			if(IsMine(this.mousePressX, this.mousePresseY)) {
				this.gameOver = true;
			}
			else {
				int squareWidth = getWidth() / Anzeige.X, squareHeight = getHeight() / Anzeige.Y;
				
				for(int i=0 ; i < JPanelLeers.size() ; i++) {
					int minX = JPanelLeers.get(i).getX();
					int maxX = JPanelLeers.get(i).getX() + squareWidth;
					
					int minY = JPanelLeers.get(i).getY();
					int maxY = JPanelLeers.get(i).getY() + squareHeight;
					
					if ((minX<=this.mousePressX && this.mousePressX<=maxX) 
							&& (minY<=this.mousePresseY && this.mousePresseY<=maxY)) {
						
						int x = minX / (squareWidth);
						int y = minY / (squareHeight);
						this.clickedAnzeige[x][y] = true;
						this.ShowNeighbors(x, y);
					}			
				}
				
				if(IsWon())
					this.gameWon = true;
			}	
		}
		repaint();
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		int squareWidth = getWidth() / Anzeige.X, squareHeight = getHeight() / Anzeige.Y;
		
		for(int i=0; i<this.JPanelMines.size(); i++) {			
			int x = this.JPanelMines.get(i).getX() / this.width;
			int y = this.JPanelMines.get(i).getY() / this.height;
			
			x = x * (squareWidth);
			y = y * (squareHeight);
			this.JPanelMines.set(i, new Mine(x, y));	
		}
		
		for(int i=0; i<this.JPanelLeers.size(); i++) {			
			int x = this.JPanelLeers.get(i).getX() / this.width;
			int y = this.JPanelLeers.get(i).getY() / this.height;
			
			x = x * (squareWidth);
			y = y * (squareHeight);
			this.JPanelLeers.set(i, new Mine(x, y));	
		}
	}

	private void enableAA(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

}