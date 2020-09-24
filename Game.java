import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
	/*
	model updates the view
	view sees the user
	user uses the controller
	controller manipulates the model
	*/
	Model model = new Model();
	Controller controller = new Controller(model);
	View view = new View(controller, model);

	public Game()
	{
		this.setTitle("Super Mario");
		this.setSize(800, 550);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		view.addMouseListener(controller);
		this.addKeyListener(controller);
	}

	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}

	public void run() {
	while(true) {
		controller.update();
		model.update();
		view.repaint();
		Toolkit.getDefaultToolkit().sync();

		try
		{
			Thread.sleep(35);
			} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
			}
		}
	}

}