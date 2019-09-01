import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class Brick extends Sprite {
	
	//MARK: Instance variables
	//Lazy loading
	static Image brick_image = null;

	//MARK: Constructor
	Brick(int _x, int _y, int _w, int _h){

		if(brick_image == null) {
			brick_image = loadBrickImage("brick_image.png");
			//System.out.println("loading brick image");
		}

		//System.out.println("creating a new brick");
		x = _x;
		y = _y;
		width = _w;
		height = _h;
	}

	//Method overriding 
	public void draw(Graphics g){

		g.drawImage(brick_image, x - Sprite.scrollPosition, y, width, height, null);
	}

	@Override public boolean isAbrick(){
		return true;
	}

	public void update(){

	}

	//MARK: JSON marshalling and unmarshalling
	Brick(Json ob){
		//Unmarshalling
		x = (int)ob.getLong("x"); 
		y = (int)ob.getLong("y");
		width = (int)ob.getLong("w");
		height = (int)ob.getLong("h");
		unmarshall(ob);
	}

	private Image loadBrickImage(String brickImagePNG){
		try
		{
			this.brick_image = ImageIO.read(new File(brickImagePNG));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return brick_image;
	}

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
