import java.util.regex.Pattern;

public class Sudoku {
	static boolean solved = false;
	
	public static void main(String[] args){		//000820090500000000308040007100000040006402503000090010093004000004035200000700900
		int[] sudoku = Pattern.compile("").splitAsStream(args[0]).mapToInt(Integer::parseInt).toArray();
		System.out.println("Initial State:");
		printSudoku(sudoku);
		System.out.println("\nSolved:");
		long startTime = System.nanoTime();
		solveSudoku(sudoku);
		System.out.println("Solved in " + (System.nanoTime()-startTime)/1000000000.0 + " seconds.");
	}
	
	static void solveSudoku(int[] sudoku){
		int remaining = 81;
		boolean solvable = true;
		while(solvable && !solved){
			remaining = 81;
			for(int ii = 0; ii < 81; ii++){
				if(sudoku[ii] == 0){
					int number = 1;
					while(number < 10 && !solved){
						boolean validNumber = true;
						for(int jj = 0; jj < 81; jj++){
							if(sudoku[jj]==number && (((ii/9)/3 == (jj/9)/3 && (ii%9)/3 == (jj%9)/3) || ii/9 == jj/9 || ii%9 == jj%9)){
								validNumber = false;
								break;
							}
						}
						if(validNumber){
							int[] newSudoku = sudoku.clone();
							newSudoku[ii] = number;
							solveSudoku(newSudoku);
						}
						number++;
					}
					solvable = false;
					break;
				}else{
					remaining--;
				}
			}
			if(remaining == 0){
				solved = true;
				printSudoku(sudoku);
			}
		}
	}
	
	static void printSudoku(int[] sudoku){
		for(int index = 0; index < 81; index++){
			if(index%9 == 0){
				System.out.print("\n-------------------------------------\n| ");
			}
			System.out.print((sudoku[index]==0 ? " " : sudoku[index]) + " | ");
		}
		System.out.println("\n");
	}	

}