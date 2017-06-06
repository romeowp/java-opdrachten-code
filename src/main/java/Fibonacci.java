import java.util.Scanner;

public class Fibonacci {

	public static void main(String[] args ){
		int n1, n2, evenSum;
		n1 = n2 = evenSum = 0;
		Scanner in = new Scanner(System.in);
		System.out.print("Give a number: ");
		int number = in.nextInt();
		in.close();
		if(number > 1){
			n2 = 1;
		}
		for(int n = 3; n <= number; n++){
			n2 = n1+n2;
			n1 = n2-n1;
			if(n2%2 == 0){
				evenSum = evenSum+n2;
			}
		}
		System.out.println("The value of the " + number + "th term of the Fibonacci sequence is " + n2 + ".");
		System.out.println("The sum of all even values of the first " + number + " terms is " + evenSum + ".");
	}
	
}
