import java.util.Calendar;

public class LCD {
	
	static char[][] segments = {{'-', '|', '|', ' ', '|', '|', '-'},
								{' ', ' ', '|', ' ', ' ', '|', ' '},
								{'-', ' ', '|', '-', '|', ' ', '-'},
								{'-', ' ', '|', '-', ' ', '|', '-'},
								{' ', '|', '|', '-', ' ', '|', ' '},
								{'-', '|', ' ', '-', ' ', '|', '-'},
								{'-', '|', ' ', '-', '|', '|', '-'},
								{'-', ' ', '|', ' ', ' ', '|', ' '},
								{'-', '|', '|', '-', '|', '|', '-'},
								{'-', '|', '|', '-', ' ', '|', '-'},
								{'-', '|', '|', '-', '|', '|', ' '},
								{'-', '|', '|', '-', '|', ' ', ' '}};
	
	int size = 2;
	boolean hour12 = false;
	int period, digit1, digit2, digit3, digit4;
		
	public static void main(String[] args){
		LCD clock = new LCD(args);
		clock.displayLCD();
	}
	
	LCD(String[] args){
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-s") && i < args.length-1){
				i++;
				this.size = Integer.parseInt(args[i]);
				if(this.size < 1 | this.size >5){
					throw new IllegalArgumentException("Invalid size setting. Size can be between 1 and 5.");
				}
			}else if(args[i].equals("-12")){
				this.hour12 = true;
			}
		}
	}
	
	static void printChars(char character, int number){
		for(int i = 0; i < number; i++){
			System.out.print(character);
		}
	}

	void printHorizontal(int digit, int segmentNumber){
		char segmentChar = segments[digit][segmentNumber];
		System.out.print(' ');
		printChars(segmentChar, this.size);
		System.out.print("  ");
	}
	
	void printVertical(int digit, int segmentNumber){
		System.out.print(segments[digit][segmentNumber]);
		printChars(' ', this.size);
		System.out.print(segments[digit][segmentNumber+1]);
		System.out.print(' ');
	}

	void displayLCD(){
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		if(this.hour12){
			this.period = 10 + cal.get(Calendar.AM_PM);
			hour = hour % 12;
			if(hour == 0){
				hour = 12;
			}
		}
		this.digit1 = hour / 10;
		this.digit2 = hour % 10;
		this.digit3 = minute / 10;
		this.digit4 = minute % 10;
		for(int segmentNumber = 0; segmentNumber < 7; segmentNumber++){
			if(segmentNumber % 3 == 0){
				if(this.hour12){
					printHorizontal(this.period, segmentNumber);
				}
				printHorizontal(this.digit1, segmentNumber);
				printHorizontal(this.digit2, segmentNumber);
				printChars(' ', this.size + 1);
				printHorizontal(this.digit3, segmentNumber);
				printHorizontal(this.digit4, segmentNumber);
				System.out.println();
			}else{
				for(int row = 0; row < this.size; row++){
					if(this.hour12){
						printVertical(this.period, segmentNumber);
					}
					printVertical(this.digit1, segmentNumber);
					printVertical(this.digit2, segmentNumber);
					printChars('-', this.size);
					System.out.print(' ');
					printVertical(this.digit3, segmentNumber);
					printVertical(this.digit4, segmentNumber);
					System.out.println();
				}
				segmentNumber++;
			}
		}
	}
}
