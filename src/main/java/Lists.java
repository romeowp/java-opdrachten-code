import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays; 

public class Lists {

	public static void main(String[] args ){
		int[] randomArray = new int[10];
		Random random = new Random();
		for(int n = 0; n < 10; n++){
			randomArray[n] = random.nextInt(101);
		}
		System.out.println("A list of 10 random numbers between 0 and 100 has been created.");
		All(randomArray);
		Highest(randomArray);
		LowestTwo(randomArray);
		Filter(randomArray);
		Split(randomArray);
		Sort(randomArray);
	}
	
	// Display all numbers.
	static void All(int[] array){
		System.out.print("The full list of numbers is as follows: ");
		for(int n = 0; n < array.length-1; n++){
			System.out.print(array[n] + ", ");
		}
		System.out.println(array[array.length-1] + ".");
	}
	
	// Display the highest number.
	static void Highest(int[] array){
		int max = array[0];
		for(int n = 1; n < array.length; n++){
			max = Math.max(max, array[n]);
		}
		System.out.println("The highest number is " + max + ".");
	}
	
	// Display the lowest two numbers.
	static void LowestTwo(int[] array){
		int min1 = Math.min(array[0], array[1]);
		int min2 = Math.max(array[0], array[1]);
		for(int n = 2; n < array.length; n++){
			if(array[n] <= min1){
				min2 = min1;
				min1 = array[n];
			}else if(array[n] <= min2){
				min2 = array[n];
			}
		}
		System.out.println("The two lowest numbers are " + min1 + " and " + min2 + ".");
	}
	
	//  Display all even values (redundant after next exercise).
	static void Filter(int[] array){
		System.out.print("The even numbers are: ");
		int previous = 1;
		for(int n = 0; n < array.length-1; n++){
			if(array[n] % 2 == 0){
				if(previous != 1){
					System.out.print(previous + ", ");
				}
				previous =  array[n];
			}
		}
		System.out.println(previous + ".");
	}
	
	// Split the list into four lists of numbers divisible by 2, 3, 5 and all other prime numbers respectively.
	static void Split(int[] array){
		ArrayList<Integer> prime2 = new ArrayList<Integer>();
		ArrayList<Integer> prime3 = new ArrayList<Integer>();
		ArrayList<Integer> prime5 = new ArrayList<Integer>();
		ArrayList<Integer> rest = new ArrayList<Integer>();
		for(int number: array){
			if(number % 2 != 0 && number % 3 != 0 && number % 5 != 0){
				rest.add(number);
			}else{
				if(number % 2 == 0){
					prime2.add(number);
				}
				if(number % 3 == 0){
					prime3.add(number);
				}
				if(number % 5 == 0){
					prime5.add(number);
				}
			}
		}
		System.out.println("Divisible by 2: " + prime2);
		System.out.println("Divisible by 3: " + prime3);
		System.out.println("Divisible by 5: " + prime5);
		System.out.println("Not divisible by 2, 3 or 5: " + rest);
	}
	
	// Sort the array in ascending order using the Bubble Sort algorithm.
	static void Sort(int[] array){
		boolean swapped = true;
		int temp;
		while(swapped){
			swapped = false;
			for(int n = 1; n < array.length; n++){
				if(array[n-1] > array[n]){
					temp = array[n];
					array[n] = array[n-1];
					array[n-1] = temp;
					swapped = true;
				}
			}
		}
		System.out.println("Sorted in ascending order: " + Arrays.toString(array));
	}
	
}