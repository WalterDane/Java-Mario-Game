import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;

public class Mario extends Sprite
{
	//MARK: Member variables
	Model model;
	public int marioImagePosition = 0;
	public int framesSinceOffGround = 0;
	public double verticalVelocity = 0.0;
	public boolean isJumping = false;
	public boolean isNowInMidAir = false;
	public boolean isAboveABrick = false;
	static Image[] mario_images = null;
	static Image mario_image = null;

	//MARK: Constructor
	Mario(Model m){

		this.model = m;
		scrollPosition = 240;
		x = 240;
		y = 0;
		width = 60;
		height = 95;
		if(mario_images == null && mario_image == null){
			//System.out.println("Initilizing images");
			mario_images = new Image[5];
			mario_images[0] = loadImage("mario1.png");
			mario_images[1] = loadImage("mario2.png");
			mario_images[2] = loadImage("mario3.png");
			mario_images[3] = loadImage("mario4.png");
			mario_images[4] = loadImage("mario5.png");
		}
	}

	//MARK: Load Image
	private Image loadImage(String marioImagePNG){
		try
		{
			this.mario_image = ImageIO.read(new File(marioImagePNG));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return mario_image;
	}

	//MARK: Encapsulation methods
	public void incrementMariosYPosition(int y){
		this.y += y;
	}
	public void decrementMariosYPosition(int y){
		this.y -= y;
	}
	public void setMariosXPosition(int x){
		this.x = x;
	}
	public void setMariosVerticalVelocity(int verticalVelocity){
		this.verticalVelocity = verticalVelocity;
	}
	public void setMariosYPosition(int y){
		this.y = y;
	}
	public int getMariosXPosition(){
		return x;
	}
	public int getMariosYPosition(){
		return y;
	}
	public int getMariosWidth(){
		return width;
	}
	public int getMariosHeight(){
		return height;
	}

	//MARK: Other public methods
	//Method overriding
	public void update()
	{

		int mariosYAndHeight = y + height;
		//If mario is above the brick and in mid air
		if(isAboveABrick == true && isNowInMidAir == true){
			setMariosVerticalVelocity(-20);
			isJumping = false; //Reset: No longer jumping
			isAboveABrick = false; //Reset (if he lands this will be true)
		}

		//Stop when mario hits the ground
		if ((mariosYAndHeight >= 475 || y + height + verticalVelocity + 1.2 >= 475)) {
				if(isJumping == false){
					verticalVelocity = 0.0;
					setMariosYPosition(475 - getMariosHeight()); 
					framesSinceOffGround = 0;
					isNowInMidAir = false;
				} else {
					isJumping = false;
					setMariosVerticalVelocity(-20);
					verticalVelocity = verticalVelocity + 1.4;
					setMariosYPosition((int)(y + verticalVelocity));
				}
		} else {
				framesSinceOffGround++;
				verticalVelocity = verticalVelocity + 1.4;
				setMariosYPosition((int)(y + verticalVelocity));
		}
	}

	public void draw(Graphics g){
		
		//"Looping" through images
		int mariosCurrentXPosition = getMariosXPosition();
		int mariosCurrentYPosition = getMariosYPosition();

		int currentMarioImagePosition = marioImagePosition % 5;
		g.drawImage(mario_images[currentMarioImagePosition], 240, mariosCurrentYPosition, null);
	}

	@Override public boolean isAmario(){
		return true;
	}

	//MARK: JSON
	public Json marshall(){
		Json ob = Json.newObject();
		ob.add("x", x);
		ob.add("y", y);
		ob.add("w", width);
		ob.add("h", height);
		return ob;
	}

	public void unmarshall(Json ob){
		x = (int)ob.getLong("x"); 
		y = (int)ob.getLong("y");
		width = (int)ob.getLong("w");
		height = (int)ob.getLong("h");
	}
}
