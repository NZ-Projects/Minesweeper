import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MineFrame extends JFrame  implements ActionListener{
	private static final long serialVersionUID = 1L;
	private SpielFeld spielFeld = new SpielFeld();
	private JButton neuStartButton = new JButton("Neues Spiel");

	public MineFrame() {		
		this.neuStartButton.setFocusPainted(false);
		this.neuStartButton.setAlignmentX(0.5F);
		this.neuStartButton.setAlignmentY(0.5F);
		this.neuStartButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		this.neuStartButton.addActionListener(this);
		
		this.setLayout(new BorderLayout());
		this.getContentPane().add(this.spielFeld, BorderLayout.CENTER);
		this.getContentPane().add(this.neuStartButton, BorderLayout.SOUTH);
		this.setSize(300,400);
		this.setVisible(true);		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("test");	
		this.setVisible(false);
		@SuppressWarnings("unused")
		MineFrame frame = new MineFrame();
	}
	
	public static void main(String[] args) {	
		@SuppressWarnings("unused")
		MineFrame frame = new MineFrame();		
	}
}
