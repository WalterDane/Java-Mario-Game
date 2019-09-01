import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

class Coin extends Sprite{
	
	//MARK: Member variables
	static Image coin = null;
	static double randz = Math.random();
	double horizontalVelocity = (Math.random()*((5 - (-5))+5))+(-5);
	double verticalVelocity = -5.1;
	private int framesSinceCoinCameOutOfBlock = 0;

	//MARK: Constructor
	Coin(int xx, int yy){

		if(coin == null){
			coin = loadImage("coin.png");
		}

		x = xx;
		y = yy;
	}

	//MARK: Method overriding
	public void draw(Graphics g){
		g.drawImage(coin, x - Sprite.scrollPosition, y, null);
	}

	public void update(){
		framesSinceCoinCameOutOfBlock++;
		y += verticalVelocity;
		x += horizontalVelocity;
		if(framesSinceCoinCameOutOfBlock < 5){
			verticalVelocity -= 2.9;
		} else {
			verticalVelocity += 7.1;
		}
	}

	private Image loadImage(String imagePNG){
		Image image = null;
		try
		{
			image = ImageIO.read(new File(imagePNG));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return image;
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