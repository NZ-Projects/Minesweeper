import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;  

import javax.swing.JPanel;


public class SpielFeld extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	private Spiel mySpiel = new Spiel();	
	private int mousePressX = 0;
	private int mousePresseY = 0;
	
	private boolean gameOver = false;
	private ArrayList<Mine> JPanelMines = new ArrayList<Mine>(Positionen.MINEN);
	private ArrayList<Mine> JPanelLeers = new ArrayList<Mine>(Anzeige.X*Anzeige.Y - Positionen.MINEN);
	private boolean [][] clickedAnzeige = new boolean[Anzeige.X][Anzeige.Y]; 

	public SpielFeld() {
		this.addMouseListener(this);
	}

	public void paint(Graphics gr) {
		super.paint(gr);
		gr.setColor(Color.red);
		enableAA(gr);
		drawField(gr);
		
		if (this.gameOver)
			drawMines(gr);
	}
		
	private void drawField(Graphics myGraphic) {
		int squareWidth = getWidth() / Anzeige.X, squareHeight = getHeight() / Anzeige.Y;
		int xsquare = 0, ysquare = 0;

		for (int i = 0; i < Anzeige.X; i++) {
			for (int j = 0; j < Anzeige.Y; j++) { 
				mySpiel.Suche(i, j);

				xsquare = i * (squareWidth);
				ysquare = j * (squareHeight);
				myGraphic.drawRect(xsquare, ysquare, squareWidth, squareHeight);
				
				String str = String.valueOf(mySpiel.get(i, j));
				
				if (this.clickedAnzeige[i][j]) {
					int x = i * (squareWidth) + (squareWidth / 2);
					int y = (j + 1) * (squareHeight) - (squareHeight / 2);
					myGraphic.drawString(str, x, y);
				}
				
				if (str.charAt(0)==mySpiel.MINE)
					JPanelMines.add(new Mine(xsquare, ysquare));
				else
					JPanelLeers.add(new Mine(xsquare, ysquare));
			}
		}
	}
	
	private void drawMines(Graphics myGraphic)
	{
		int squareWidth = getWidth() / Anzeige.X, squareHeight = getHeight() / Anzeige.Y;
		
		for(int i = 0; i < Positionen.MINEN; i++)
		{
			Mine mine = mySpiel.getMinenPositionen().get(i);
			
			int x = mine.getX() * (squareWidth) + (squareWidth / 2);
			int y = (mine.getY() + 1) * (squareHeight) - (squareHeight / 2);
			
			String str = String.valueOf(mySpiel.MINE);
			myGraphic.drawString(str, x, y);
		}
	}
	
	private boolean isMine(int x, int y) {
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

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!this.gameOver) {
			this.mousePressX = e.getX();
			this.mousePresseY = e.getY();

			if(isMine(this.mousePressX, this.mousePresseY)) {
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
						System.out.println(x + "," + y);
						this.clickedAnzeige[x][y] = true;		
					}			
				}
			}	
		}
		repaint();
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

}

// l idee li 3ndi hia n9ado f depart tableau li fih min w max dial kola point fih mine, apres ghadinparcouriw hadak tableau w nchofo wach
// les coordonees dial la souris x w y kynin wst min w max la kano rah mine la makanoch rach machi mine .
// eetape suivante hia nchofo les données li 3ndna lihom access men had classe meinJpanel w nchofo chno n9dro ndiro bihom
// 7na bghina n9ado tablea dial les mines 79i9iyin donc khasna tableau li fih les mines 3adiyin bach ntbazaw 3lih ok?
// hadak tableau dial mine 3adiyin private donc man9drouch nst3ml
// mais 7na deja kanrsmo les mines fach kanrsmoh f panel
