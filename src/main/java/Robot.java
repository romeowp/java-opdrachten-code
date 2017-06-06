import java.util.ArrayList;

public class Robot {
	
	static String[] cardinal = {"North", "East", "South", "West"};
	enum Command {GO_FORWARD, GO_BACKWARD, TURN_LEFT, TURN_RIGHT};
	
	int xpos, ypos, direction;
	ArrayList<Command> instructions = new ArrayList<Command>();
	
	Robot(){
		xpos = ypos = direction = 0;
	}
	
	Robot(int xpos, int ypos, int direction){
		if(direction < 0 || direction > 3){
			throw new IllegalArgumentException("Invalid direction setting. 0 = North, 1 = East, 2 = South, and 3 = West.");
		}else{
			this.xpos =  xpos;
			this.ypos =  ypos;
			this.direction = direction;
		}
	}
	
	public static void main(String[] args){
		// Sample instructions
		Robot isaac = new Robot();
		Robot asimov = new Robot(3, 5, 3);
		asimov.turnRight();
		asimov.turnRight();
		asimov.backward();
		asimov.forward(3);
		asimov.printState();
		asimov.execute();
		asimov.printState();
		isaac.printState();
	}
	
	void printState(){
		System.out.println("On position (" + this.xpos + "," + this.ypos + "), facing " + cardinal[this.direction] + ".");
	}

	void turnLeft(){
		instructions.add(Command.TURN_LEFT);
	}

	void turnRight(){
		instructions.add(Command.TURN_RIGHT);
	}

	void forward(){
		this.forward(1);
	}

	void forward(int speed){
		if(speed < 1 || speed > 3){
			throw new IllegalArgumentException("Invalid speed setting. Speed can be between 1 and 3.");
		}else{
			for(int i = 0; i < speed; i++){
				instructions.add(Command.GO_FORWARD);
			}
		}
	}

	void backward(){
		instructions.add(Command.GO_BACKWARD);
	}
	
	void execute(){
		for(Command command: this.instructions){
			switch(command){
				case GO_FORWARD:	executeForward();
									break;
				case GO_BACKWARD:	executeBackward();
									break;
				case TURN_LEFT:		executeTurnLeft();
									break;
				case TURN_RIGHT:	executeTurnRight();
									break;
			}
		}
	}
	
	void executeTurnLeft(){
		if(this.direction == 0){
			this.direction = 3;
		}else{
			this.direction--;
		}
	}

	void executeTurnRight(){
		if(this.direction == 3){
			this.direction = 0;
		}else{
			this.direction++;
		}
	}

	void executeForward(){
		switch(this.direction){
			case 0:	this.ypos++;
					break;
			case 1: this.xpos++;
					break;
			case 2: this.ypos--;
					break;
			case 3: this.xpos--;
					break;
		}
	}

	void executeBackward(){
		switch(this.direction){
			case 0:	this.ypos--;
					break;
			case 1: this.xpos--;
					break;
			case 2: this.ypos++;
					break;
			case 3: this.xpos++;
					break;
		}
	}

}
