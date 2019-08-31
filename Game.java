import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{

	//Creating objects: calling the constructor method of these classes
	Model model = new Model();
	Controller controller = new Controller(model); //the controller can update the model and mario
	View view = new View(controller, model);

	//Constructor - initializes variables and methods and has same name of class
	public Game()
	{
		this.setTitle("Drawing Bricks");
		this.setSize(800, 550);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		view.addMouseListener(controller);
		this.addKeyListener(controller);
	}

	//MARK: Public Methods
	//Main program
	public static void main(String[] args)
	{
		Game g = new Game(); //allocating memory for a new game object called "g"
		g.run();
	}

	public void run() {
	while(true) {
		controller.update();
		model.update();
		view.repaint(); // Indirectly calls View.paintComponent : repaints the screen
		Toolkit.getDefaultToolkit().sync(); // Updates screen

		// Go to sleep for 40 miliseconds
		try
		{
			Thread.sleep(50); // this now updates at a rate of 25 frames per second
			} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
			}
		}
	}

}
