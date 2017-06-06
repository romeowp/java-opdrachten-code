import java.util.Scanner;
import java.util.Random;

public class HigherLower {

	public static void main(String[] args ){
		Random random = new Random();
		int target = random.nextInt(50) + 1;
		Scanner in = new Scanner(System.in);
		int guess;
		for(int guesses = 10; guesses > 0; guesses--){
			System.out.print("Enter a number: ");
			guess = in.nextInt();
			if(guess < target){
				System.out.println("Higher");
			}else if(guess > target){
				System.out.println("Lower");
			}else{
				System.out.println("Correct!");
				break;
			}
		}
		in.close();
		System.out.println("You lost the game!");
	}
}