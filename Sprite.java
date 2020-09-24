import java.awt.Graphics;

enum Position {
	LEFT, RIGHT, ABOVE, BELOW
}

abstract class Sprite{

	static int scrollPosition = 0;
	public int x = 0;
	public int y = 0;
	public int width = 0;
	public int height = 0;
	public boolean isColliding = false;
	public int numberOfCollides = 0;
	public Position position;

	//MARK: Abstract methods
	abstract void update();
	abstract void draw(Graphics g);
	abstract Json marshall();
	abstract void unmarshall(Json ob);

	//MARK: Public methods that can be overriden
	public boolean isAbrick(){
		return false;
	}
	public boolean isAmario(){
		return false;

	}
	public boolean isAcoin(){
		return false;
	}
	public boolean isACoinBlock(){
		return false;
	}
	public void incrementXPosition(int xx){
		//System.out.println(x);
		x += xx;
	}
	public void decrementXPosition(int xx){
		//cleaSystem.out.println(x);
		x -= xx;
	}

	public void handleCollision(Sprite s, Model model){

		//Check if mario is colliding with Sprite
		if((s.x + s.width) < x - 240){
			isColliding = false;
		} else if (s.x > ((x - 240) + width)){
			isColliding = false;
		} else if ((s.y) > (y + height)){
			isColliding = false;
		} else if ((s.y + s.height) < y){
			isColliding = false;
		} else {
			isColliding = true;
		}

 		//MARK: Check position relative to the brick
		if ((s.x + s.width) <= (x - 240)){
				position = Position.RIGHT;
		}

		if (s.x >= ((x - 240) + width)){
			position = Position.LEFT;
		}

		if ((s.y) >= (y + height)){
			position = Position.ABOVE;
		}

		if ((s.y + s.height) <= y){
			position = Position.BELOW;
		}

		//MARK: Collision detection
 		//Sprite is to the left of the brick
		if (((s.x + s.width) >= x - 240) && position == Position.RIGHT && isColliding == true){
			//System.out.println("Mario hit the brick coming in from the left");
			//System.out.println("mario collided with the brick");
			s.x = (x - 240) - s.width;
			if(s.isAmario()){
				model.mario.isAboveABrick = false;
			}
		}
			
		// //Mario is to the right of the brick
		if (s.x <= ((x - 240) + width) && position == Position.LEFT && isColliding == true){
			//System.out.println("Mario hit the brick coming in from the right");
			s.x = (x - 230) + width;
			if(s.isAmario()){
				model.mario.isAboveABrick = false;
			}
		}

		//Mario is above the brick
		if ((s.y + s.height) >= y && position == Position.BELOW && isColliding == true){
			//System.out.println("Mario hit the brick coming in from the top");
			if(s.isAmario()){
				model.mario.isAboveABrick = true;
				model.mario.isNowInMidAir = false;
				model.mario.setMariosVerticalVelocity(0);
				model.mario.framesSinceOffGround = 0;
			}
			s.y = y - s.height;
		}

		// //Mario is below the brick
		if (((s.y) <= y + height) && position == Position.ABOVE && isColliding == true){
			//System.out.println("Mario hit the brick coming in from the bottom");
			if(s.isAmario()){
				model.mario.setMariosVerticalVelocity(5);
				model.mario.isAboveABrick = false;
				s.y = y + height;
			}
			if(this.isACoinBlock()){
				//System.out.println("Mario is hitting the bottom of the coin block yo");
				CoinBlock cb = (CoinBlock)this;
				cb.popOutCoinLOL(model);
			}
			numberOfCollides++;
		}

	}
}