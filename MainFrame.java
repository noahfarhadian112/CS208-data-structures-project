package noahHashGame;
import javax.swing.*;

public class MainFrame extends JFrame {
	
	public MainFrame()	{
		setTitle("Map Explorer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		add(new GameWindow());
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainFrame();

	}

}
