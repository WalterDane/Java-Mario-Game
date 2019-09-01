import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

//This class allows the user to manipulate the model by keyboard and mouse functions
class Controller implements ActionListener, MouseListener, KeyListener
{

	//MARK: Instance variables: can be accessed by all methods
	View view;
	Model model;
	private boolean keyLeft;
	private boolean keyRight;
	private boolean keySpace;
	private int mousePressedXPos = 0;
	private int mousePressedYPos = 0;
	private boolean spaceFlag = false;

	// MARK: Constructor - initializes variables and methods
	Controller(Model m) {
		model = m;
	}

	// MARK: Public methods
	public void actionPerformed(ActionEvent e) {

	}

	public void setView(View v) {
		view = v;
	}

	// MARK: Mouse listener methods
	public void mousePressed(MouseEvent e) {
		mousePressedXPos = e.getX();
		mousePressedYPos = e.getY();
	}

	public void mouseReleased(MouseEvent e) {

		int x1 = mousePressedXPos;
		int x2 = e.getX();
		int y1 = mousePressedYPos;
		int y2 = e.getY();
		int left = Math.min(x1, x2);
		int right = Math.max(x1, x2);
		int top = Math.min(y1, y2);
		int bottom = Math.max(y1, y2);

		/*System.out.println(left + Sprite.scrollPosition);
		System.out.println(top);
		System.out.println(right - left);
		System.out.println(bottom - top);*/
		model.addBrick(left + Sprite.scrollPosition, top, right - left, bottom - top);
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {

	}

	// MARK: Key listener methods
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			keyRight = true;
			break;
		case KeyEvent.VK_LEFT:
			keyLeft = true;
			break;
		case KeyEvent.VK_UP:
			break;
		case KeyEvent.VK_DOWN:
			break;
		case KeyEvent.VK_SPACE:
			keySpace = true;
			spaceFlag = true;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			keyRight = false;
			break;
		case KeyEvent.VK_LEFT:
			keyLeft = false;
			break;
		case KeyEvent.VK_UP:
			break;
		case KeyEvent.VK_DOWN:
			break;
			case KeyEvent.VK_S: model.save("map.json"); break; //Saving JSON information
			// case KeyEvent.VK_L: Json loadObject = Json.load("map.json"); //Loading JSON information
			// 					model.load(loadObject); break;
			case KeyEvent.VK_SPACE: keySpace = false; break;

	}
}

	public void keyTyped(KeyEvent e)
	{
	}

	public void update(){
		if(keyRight){
			model.mario.marioImagePosition++;
			model.mario.incrementXPosition(10);
			Sprite.scrollPosition = model.mario.getMariosXPosition();
		}

		if(keyLeft){
			 model.mario.marioImagePosition++;
			 model.mario.decrementXPosition(10);
			 Sprite.scrollPosition = model.mario.getMariosXPosition();
		}

		if(keySpace == true || spaceFlag == true){
			if (model.mario.isNowInMidAir == false) {
				model.mario.isJumping = true;
				model.mario.isNowInMidAir = true;
			}

			if(model.mario.framesSinceOffGround < 5){
				model.mario.verticalVelocity -= 2.1;
			}

			spaceFlag = false;
		}
	}

}
