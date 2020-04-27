
public class Spiel extends Anzeige {
	private Positionen minenPositionen = new Positionen();

	public Spiel() {
		super();
		for (int i = 0; i < minenPositionen.MINEN; i++) {
			this.set(minenPositionen.get(i).getX(), minenPositionen.get(i).getY(), MINE);
		}
	}
	
	public Positionen getMinenPositionen() {
		return this.minenPositionen;	
	}

	public void Suche(int x, int y) {
		if (!minenPositionen.istHierMine(x, y))
			this.set(x, y, ZaelherNachbar(x, y));
	}

	private char ZaelherNachbar(int x, int y) {
		int count = 0;

		if (minenPositionen.istHierMine(x - 1, y - 1))
			count++;
		if (minenPositionen.istHierMine(x - 1, y))
			count++;
		if (minenPositionen.istHierMine(x - 1, y + 1))
			count++;
		if (minenPositionen.istHierMine(x, y - 1))
			count++;
		if (minenPositionen.istHierMine(x, y + 1))
			count++;
		if (minenPositionen.istHierMine(x + 1, y - 1))
			count++;
		if (minenPositionen.istHierMine(x + 1, y))
			count++;
		if (minenPositionen.istHierMine(x + 1, y + 1))
			count++;

		return Integer.toString(count).charAt(0);
	}
}
