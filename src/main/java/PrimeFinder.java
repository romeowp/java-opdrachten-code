import java.util.Scanner;

public class PrimeFinder {

	public static void main(String[] args ){
		boolean isPrime;
		int primeCount = 1;
		int iterator = 2;
		Scanner in = new Scanner(System.in);
		System.out.println("Enter a number.");
		int number = in.nextInt();
		in.close();
		int[] primeSeen = new int[number];
		primeSeen[0] = 2;
		while(primeCount < number){
			isPrime = true;
			iterator++;
			for(int n = 0; n < primeCount; n++){
				if(iterator%primeSeen[n] == 0){
					isPrime = false;
				}
			}
			if(isPrime){
				primeSeen[primeCount] = iterator;
				primeCount++;
			}
		}
		System.out.println("Prime number " + number + " is " + iterator + ".");
	}
	
}
