import java.util.Scanner;
import java.lang.Math;

public class Nim {

	static Scanner in = new Scanner(System.in);
	static int matchesRemaining = 11;
	static int matchesTaken;

	public static void main(String[] args ){
		int players = 0;
		while(players != 1 && players != 2){
			System.out.println("Welcome to Nim. Press 1 for one player or press 2 for two players.");
			players = in.nextInt();
		}
		if(players == 1){
			OnePlayer();
		}else if(players == 2){
			TwoPlayers();
		}
		in.close();
	}
		
	static void OnePlayer(){
		int computerMove;
		int[] computerMoves = {4, 1, 1, 2, 3};
		while(matchesRemaining > 0){
			System.out.print("There " + ((matchesRemaining == 1) ? "is " : "are ") + matchesRemaining + 
					" match" + ((matchesRemaining == 1) ? "" : "es") + ".\nHow many do you want to take? ");
			matchesTaken = in.nextInt();
			while(matchesTaken < 1 || matchesTaken > 4 || matchesTaken > matchesRemaining){
				System.out.print("Please select a number between 1 and " + Math.min(4, matchesRemaining) + ". ");
				matchesTaken = in.nextInt();
			}
			matchesRemaining = matchesRemaining - matchesTaken;
			if(matchesRemaining == 0){
				System.out.println("\nYou took the last match.\nYou lost!");
			}else{
				computerMove = computerMoves[matchesRemaining % 5];
				System.out.println("\nThere are " + matchesRemaining + " matches.\nComputer takes " + 
						computerMove +" match" + ((computerMove == 1) ? "" : "es") + ".\n");
				matchesRemaining = matchesRemaining - computerMove;
				if(matchesRemaining == 0){
					System.out.println("Computer took the last match.\nYou won!");
				}
			}
		}
	}
	
	static void TwoPlayers(){
		int turn = 0;
		while(matchesRemaining > 0){
			System.out.print("\nThere " + ((matchesRemaining == 1) ? "is " : "are ") + matchesRemaining + 
					" match" + ((matchesRemaining == 1) ? "" : "es") + ".\nPlayer " + (1 + (turn % 2)) + 
					", how many do you want to take? ");
			matchesTaken = in.nextInt();
			while(matchesTaken < 1 || matchesTaken > 4 || matchesTaken > matchesRemaining){
				System.out.print("Please select a number between 1 and " + Math.min(4, matchesRemaining) + ". ");
				matchesTaken = in.nextInt();
			}
			matchesRemaining = matchesRemaining - matchesTaken;
			if(matchesRemaining == 0){
				System.out.println("\nPlayer " + (1 + (turn % 2)) + " took the last match.\nPlayer " + 
						(1 + ((turn + 1) % 2)) + " wins!");
			}
			turn++;
		}
	}
	
}
