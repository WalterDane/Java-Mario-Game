import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;

class CoinBlock extends Sprite{

	//MARK: Instance variables
	static Image activeCoinBlockImage = null;
	static Image inactiveCoinBlockImage = null;

	CoinBlock(){

	}

	CoinBlock(int xx, int yy){

		x = xx;
		y = yy;
		width = 89;
		height = 83;

		if (activeCoinBlockImage == null && inactiveCoinBlockImage == null){
			//System.out.println("loading coin images");
			activeCoinBlockImage = loadImage("active_block.png");
			inactiveCoinBlockImage = loadImage("inactive_block.png");
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

	//Method overriding
	@Override public boolean isACoinBlock(){
		return true;
	}

	public void draw(Graphics g){
		if(numberOfCollides < 5){
			g.drawImage(activeCoinBlockImage, x - Sprite.scrollPosition, y, width, height, null);
		} else {
			g.drawImage(inactiveCoinBlockImage, x - Sprite.scrollPosition, y, width, height, null);
		}
	}

	public void update(){
		
	}

	//MARK: Other methods
	public void popOutCoinLOL(Model model){
		if(numberOfCollides < 5){
			Coin coin = new Coin(x,y);
			model.sprites.add(coin);
		}
	}

	//MARK: JSON
	Json marshall(){
		Json ob = Json.newObject();
		ob.add("x", x);
		ob.add("y", y);
		ob.add("w", width);
		ob.add("h", height);
		return ob;
	}
	
	void unmarshall(Json ob){
		x = (int)ob.getLong("x"); 
		y = (int)ob.getLong("y");
		width = (int)ob.getLong("w");
		height = (int)ob.getLong("h");
	}
}