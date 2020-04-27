import javax.swing.JFrame;

public class MineFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private SpielFeld spielfeld = new SpielFeld();

	public MineFrame() {
		this.add(spielfeld);
		this.setSize(300,400);
		this.setVisible(true);		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {	
		MineFrame frame = new MineFrame();
		
	}
}
