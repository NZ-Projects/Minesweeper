public class Anzeige {
	final static int X = 12;
	final static int Y = 16;
	private char[][] anzeige = new char[X][Y];
	static final char LEER = '.';
	static final char MINE = '#';

	public Anzeige() {
		System.out.println("MINESWEEPER");
		for (int i = 0; i < X; i++) {
			for (int j = 0; j < Y; j++) {
				this.anzeige[i][j] = LEER; // LEER = "."
			}
		}
	}
	
	public char get(int x, int y) {
		return this.anzeige[x][y];
	}
	
	public void set(int x , int y, char value) {
		this.anzeige[x][y] = value;
	}
}


