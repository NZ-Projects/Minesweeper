public class Positionen {
	public static final int MINEN = 8;
	private Mine[] arrayOfMines = new Mine[MINEN];

	public Positionen() {
		int[] randomX = randomZahl(Anzeige.X);// [10, 4 ,7 ,6]
		int[] randomY = randomZahl(Anzeige.Y);// [6, 7, 4, 10]
		for (int i = 0; i < Positionen.MINEN; i++) {
			this.arrayOfMines[i] = new Mine(randomX[i], randomY[i]);
		}
	}

	public boolean istHierMine(int x, int y) {
		for (int i = 0; i < MINEN; i++) {
			if (arrayOfMines[i].getX() == x && arrayOfMines[i].getY() == y) {
				return true;
			}
		}
		return false;
	}

	public Mine get(int index) {
		return arrayOfMines[index];
	}

	public static int[] randomZahl(int number) {
		java.util.Random zufall = new java.util.Random();
		int[] arrayPos = new int[number];
		for (int i = 0; i < number; i++) {
			arrayPos[i] = zufall.nextInt(number);
			for (int j = 0; j < i; j++) {
				if (arrayPos[i] == arrayPos[j]) {
					i = i - 1;
				}
			}
		}
		return arrayPos;
	}

}

