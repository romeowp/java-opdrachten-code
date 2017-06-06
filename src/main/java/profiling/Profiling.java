package profiling;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Profiling {
	
	static List<Profile> profiles = new ArrayList<Profile>();
	static Scanner in = new Scanner(System.in);
	
    public static void main(final String[] args) throws IOException {
    	List<String> lines = Files.readAllLines(Paths.get("src\\main\\resources\\profiling-data.txt"));
    	for(String line : lines){
    		String[] mainSplit = line.split(", ", 2);
    		String[] nameSplit = mainSplit[0].split("  ");
    		String[] bookSplit = mainSplit[1].split(", ");
    		profiles.add(new Profile(nameSplit[0] + " " + nameSplit[1], bookSplit));	
    	}
    	System.out.println( "What would you like to do?\n" + 
    						"1: Find out which books have been read by a customer.\n" +
    						"2: Find out which customers have read a book.\n" +
    						"3: Receive a book recommendation for a customer.\n" +
    						"4: Quit.");
    	int option = in.nextInt();
    	while(option < 1 || option > 4){
			System.out.print("Please select an option between between 1 and 4 and press enter. ");
			option = in.nextInt();
		}
    	in.nextLine();
    	switch(option){
    		case(1) : 	System.out.print("\nPlease enter a (partial) name: ");
    					nameSearch(in.nextLine());
    					break;
    		case(2) : 	System.out.print("\nPlease enter the (exact) name of a book: ");
						bookSearch(in.nextLine());
						break;
    		case(3) : 	System.out.print("\nPlease enter the (exact) name of a customer: ");
						recommendBook(in.nextLine());
						break;
    		case(4) : 	System.out.println("\nGoodbye!");
						break;
    	}
    	in.close();
    }
    
    static void nameSearch(String nameQuery){
    	boolean noneFound = true;
    	for(Profile profile : profiles){
    		if(profile.name.toLowerCase().contains(nameQuery.toLowerCase())){
    			System.out.println("\nBooks read by " + profile.name + ":");
    			for(String bookTitle : profile.bookTitles){
    				System.out.println(bookTitle);
    			}
    			noneFound = false;
    		}
    	}
    	if(noneFound){
    		System.out.println("\nNo names matching \"" + nameQuery + "\" were found.");
    	}
    }
    
    static void bookSearch(String bookQuery){
    	boolean noneFound = true;
    	System.out.println("\n" + bookQuery + " has been read by the following customers:");
    	for(Profile profile : profiles){
    		for(String bookTitle : profile.bookTitles){
    			if(bookTitle.equals(bookQuery)){
    				System.out.println(profile.name);
    				noneFound = false;
    			}
    		}
    	}
    	if(noneFound){
    		System.out.println("No names matching \"" + bookQuery + "\" were found.");
    	}
    }
    
    static void recommendBook(String nameQuery){
    	String[] booksRead = null;
    	for(Profile profile : profiles){
    		if(profile.name.equals(nameQuery)){
    			booksRead = profile.bookTitles;
    			break;
    		}
    	}
    	if(booksRead == null){
    		throw new Error("Customer not found.");
    	}
    	for(Profile profile : profiles){
    		if(!profile.name.equals(nameQuery)){
    			ArrayList<String> inCommon = new ArrayList<String>(Arrays.asList(booksRead));
    			ArrayList<String> readByOther = new ArrayList<String>(Arrays.asList(profile.bookTitles));
    			inCommon.retainAll(readByOther);
    			if(inCommon.size() > 2 && inCommon.size() < readByOther.size()){
    				readByOther.removeAll(inCommon);	
    				System.out.println(nameQuery + ", we recommend \"" + readByOther.get(0) + "\" to you.");
    				return;
    			}
    		}
    	}
    	System.out.println("There are no recommendations at this time.");
    }
}
