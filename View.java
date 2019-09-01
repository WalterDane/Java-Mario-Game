import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;

//This class presents the model
class View extends JPanel
{
	//MARK:: Instance variables
	Model model;
	private static Image background_image = null;

	//MARK: Constructor
	View(Controller c, Model m)
	{
		this.model = m;
		c.setView(this);

		//Load background
		if(background_image == null){
				background_image = loadImage("background_image.png");
		  }

	}

	//MARK: public methods
	public void paintComponent(Graphics g)
	{
		//Draw the background
		int width = -940 * 2;
		for(int i = 1; i<5; i++){
			if (i % 2 == 1) {
				g.drawImage(this.background_image, width - Sprite.scrollPosition/2, 0, 940, 900, null);
				width += 940 * 2;
			} else {
				g.drawImage(this.background_image, width - Sprite.scrollPosition/2, 0, -940, 900, null);
			}
		}

		//Draw the ground
		g.setColor(new Color(15, 200, 64));
		g.fillRect(0, 475, 800, 475);
		g.setColor(new Color(0, 0, 0));

		//Draw the sprite
		for(int i = 0; i<model.sprites.size(); i++){
			//System.out.println(model.sprites.size());
			Sprite sprite = model.sprites.get(i);
			sprite.draw(g);
		}
	
	}

	//MARK: private methods
	private Image loadImage(String backgroundImagePNG){
		try{
			this.background_image = ImageIO.read(new File(backgroundImagePNG));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return background_image;
	}

}
