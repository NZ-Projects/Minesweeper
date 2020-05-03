public class Mine {
	private int xPosition;
	private int yPosition;

	public Mine(int x, int y) {
		xPosition = x;
		yPosition = y;
	}

	public int getX() {
		return this.xPosition;
	}

	public int getY() {
		return this.yPosition;
	}

	public boolean istAnPosition(Mine mine) {
		return mine.equals(this);
	}
}
