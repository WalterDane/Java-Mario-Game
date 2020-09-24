import java.util.ArrayList;
import java.util.*;

class Model
{
	//MARK: Instance variables
	ArrayList<Sprite> sprites;
	Mario mario = new Mario(this); //redundant mario

	//MARK: Constructor - initializes variables and methods
	Model()
	{
		sprites = new ArrayList<Sprite>();
		sprites.add(mario); //add a redundant mario to sprite class	
		addCoinBlocks(); //add some random coin blocks	
		addBricks(); //add some random bricks
	}

	public void update()
	{
		//Updating all the sprites in the array list
		for(int i = 0; i < sprites.size(); i++){
			Sprite sprite = sprites.get(i);
			sprite.update();

			if(sprite.isAbrick()){
					sprite.handleCollision(mario, this);
			}
			if(sprite.isACoinBlock()){
				sprite.handleCollision(mario, this);
			}
		}
	}

	//Add the brick to array list
	//Called in the controller when a brick is added in the mouse
	public void addBrick(int x, int y, int w, int h){

		Brick b = new Brick(x, y, w, h);
		sprites.add(b);
	}

	//MARK: JSON
	//JSON into bricks
	void unmarshall(Json ob){
		//bricks.clear();
		sprites.clear();

		Json json_bricks = ob.get("bricks");
		Json coin_blocks = ob.get("coin_blocks");
		Json mario = ob.get("mario");

		for(int i = 0; i < json_bricks.size(); i++){
			Json j = json_bricks.get(i);
			Brick b = new Brick(j);
			sprites.add(b);
		}

		for(int i = 0; i < coin_blocks.size(); i++){
			Json j = coin_blocks.get(i);
			CoinBlock cb = new CoinBlock();
			cb.unmarshall(j);
			sprites.add(cb);
		}

		for(int i = 0; i < mario.size(); i++){
			Json j = mario.get(i);
			Mario m = new Mario(this);
			m.unmarshall(j);
			sprites.add(m);
		}

	}

	Json marshall(){
		Json ob = Json.newObject(); //Declares a JSON object
		Json json_bricks = Json.newList(); //creates a new list
		Json coin_blocks = Json.newList();
		Json m = Json.newList();
		ob.add("bricks", json_bricks); //adds the name with the type of list within the Json ob
		ob.add("coin_blocks", coin_blocks);
		ob.add("mario", m);

		for(int i = 0; i < sprites.size(); i++){
			Sprite sprite = sprites.get(i);

			//If the sprite is a brick, remove at the index
			if(sprite.isAbrick() == true){
				Json j = sprite.marshall();
				json_bricks.add(j);
			}
			if(sprite.isACoinBlock() == true){
				Json j = sprite.marshall();
				coin_blocks.add(j);
			}
			if(sprite.isAmario() == true){
				Json j = sprite.marshall();
				m.add(j);
			}
		}

		return ob;
	}

	//Save into a JSON file
	void save(String filename){
		System.out.println("saving objects");
		Json ob = marshall(); //Calls the marshall function which returns a json object
		ob.save(filename); //saves the json objects
	}

	//Extract from a JSON file
	void load(Json ob){
		System.out.println("Loading object");
		unmarshall(ob);
	}

	//MARK: Add coinblocks and bricks
	public void addCoinBlocks(){

		//Add some random coinblocks
		CoinBlock coinblock = new CoinBlock(240, 120);
		CoinBlock coinblock2 = new CoinBlock(300, 180);
		CoinBlock coinblock3 = new CoinBlock(630, 150);
		CoinBlock coinblock4 = new CoinBlock(730, 210);

		sprites.add(coinblock);
		sprites.add(coinblock2);
		sprites.add(coinblock3);
		sprites.add(coinblock4);
	}

	public void addBricks(){
		Brick b = new Brick(594, 347, 130, 130);
		sprites.add(b);
	}
}
