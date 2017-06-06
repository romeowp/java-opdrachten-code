package itemfusion;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemFusion {

	static Map<String, Item> items = new HashMap<String, Item>();
	static List<Fusion> fusions = new ArrayList<Fusion>();
	static List<String> stores = new ArrayList<String>();
	
	
	public static void main(final String[] args) throws IOException {
    	readFusionsFile(Paths.get("src\\main\\resources\\item-fusion-data.txt"));
    	System.out.println("Welcome to the item price calculator for Stella Deus: The Gate of Eternity!");
    	System.out.println("Which item would you like to purchase?"); // Bijvoorbeeld Glide on Breeze
    	Scanner in = new Scanner(System.in);
    	String itemName = in.nextLine();
    	System.out.println("Would you like to see the cheapest way or the fastest way to obtain this item?");
    	System.out.println("Press 1 for cheapest or press 2 for fastest.");
    	int choice = in.nextInt();
    	in.close();
    	if(choice == 1){
    		updateItemCosts();
    	}else if(choice == 2){
    		earliest(itemName);
    	}
    	printResults(itemName);
	}
	
	
	static void readFusionsFile(Path path) throws IOException {
    	List<String> lines = Files.readAllLines(path);
    	Pattern storePattern = Pattern.compile("(.+Store) Items.*");
    	Pattern costPattern = Pattern.compile("\\**(\\w.+\\w)\\s*~\\s*(\\d+)");
    	Pattern fusionPattern = Pattern.compile("(\\w.+\\w)\\s*\\+\\s*(\\w.+\\w)\\s*=\\s*([\\w\\s]+\\w).*");
    	for(String line : lines){
    		Matcher fusionMatcher = fusionPattern.matcher(line);
    		if(fusionMatcher.matches()){
    			if(items.get(fusionMatcher.group(1)) == null)	items.put(fusionMatcher.group(1), new Item());
    			if(items.get(fusionMatcher.group(2)) == null)	items.put(fusionMatcher.group(2), new Item());
    			if(items.get(fusionMatcher.group(3)) == null)	items.put(fusionMatcher.group(3), new Item());
    			fusions.add(new Fusion(fusionMatcher.group(1), fusionMatcher.group(2), fusionMatcher.group(3)));
    			continue;
    		} 
    		Matcher costMatcher = costPattern.matcher(line);
    		if(costMatcher.matches() && items.get(costMatcher.group(1)) == null){
    			items.put(costMatcher.group(1), new Item(Integer.parseInt(costMatcher.group(2)), stores.get(stores.size()-1)));
    			continue;
    		}
    		Matcher storeMatcher = storePattern.matcher(line);
    		if(storeMatcher.matches()){
    			stores.add(storeMatcher.group(1));
    			continue;
    		} 
    	}
	}

	
	static void updateItemCosts(){
		boolean updated = true;
		while(updated){
			updated = false;
			for(Fusion fusion : fusions){
				int[] prices = {items.get(fusion.ingredient1).price, items.get(fusion.ingredient2).price, items.get(fusion.product).price};
				if(prices[0]*prices[1] != 0 && (prices[2] == 0 || prices[0]+prices[1] < prices[2])){			
					items.put(fusion.product, new Item(prices[0]+prices[1], "Fusion#" + fusion.ingredient1 + '#' + fusion.ingredient2));
					updated = true;
				}
			}
		}
	}
	
	
	static void earliest(String itemName){
		for(int storeNumber = 1; storeNumber <= stores.size(); storeNumber++){
			Map<String, Item> tempItems = new HashMap<>(items);
			for (Map.Entry<String, Item> entry : items.entrySet()){
			    if(stores.subList(0, storeNumber).contains(entry.getValue().store)){
			    	items.put(entry.getKey(), new Item(entry.getValue().price, entry.getValue().store));
			    }else{
			    	items.put(entry.getKey(), new Item());
			    }
			}
			updateItemCosts();
			if(items.get(itemName).price != 0){
				return;
			}
			items = new HashMap<>(tempItems);
		}
	}
	
	
	static void printResults(String itemName){
		System.out.println("Which Item: " + itemName + "\n");
		List<String> branchItems = new ArrayList<String>();
		branchItems.add(itemName);
		List<Boolean> branchDone= new ArrayList<Boolean>();
		branchDone.add(false);
		List<String[]> baseIngredients = new ArrayList<String[]>();
		printTree(branchItems, branchDone, baseIngredients);
		System.out.println();
    	int maxLength = 0;
    	for(String key : items.keySet()){
    		if(key.length() > maxLength){
    			maxLength = key.length();
    		}
    	}
    	int total = 0;
		for(String store : stores){
			boolean storePrinted = false;
			for(int ii = 0; ii < baseIngredients.size(); ii++){
				if(baseIngredients.get(ii)[0].equals(store)){
					if(!storePrinted){
						System.out.println(store + ':');
						storePrinted = true;
					}
					int count = 1;
					for(int jj = ii+1; jj < baseIngredients.size(); jj++){
						if(baseIngredients.get(jj)[1].equals(baseIngredients.get(ii)[1])){
							count++;
							baseIngredients.remove(jj);
							jj--;
						}
					}
					System.out.print(count + "x " + baseIngredients.get(ii)[1]);
					printChars(1+ maxLength - baseIngredients.get(ii)[1].length(), ' ');
					System.out.print(count + "x ");
					printChars(4 - String.valueOf(items.get(baseIngredients.get(ii)[1]).price).length(), ' ');
					System.out.print(items.get(baseIngredients.get(ii)[1]).price + " = ");
					printChars(4 - String.valueOf(count*items.get(baseIngredients.get(ii)[1]).price).length(), ' ');
					System.out.println(count*items.get(baseIngredients.get(ii)[1]).price);
					total += count*items.get(baseIngredients.get(ii)[1]).price;
				}
			}
		}
		printChars(maxLength+18, '-');
		System.out.print("\nTotal");
		printChars(maxLength+9, ' ');
		System.out.print(total);
	}	
	
	
	static void printTree(List<String> branchItems, List<Boolean> branchDone, List<String[]> baseIngredients){
		if(items.get(branchItems.get(branchItems.size()-1)).store.matches("Fusion.+")){
			System.out.print(branchItems.get(branchItems.size()-1) + " <-+-- ");
			List<String> newBranchItems = new ArrayList<>(branchItems);
			newBranchItems.add(items.get(newBranchItems.get(newBranchItems.size()-1)).store.split("#")[1]);
			List<Boolean> newBranchDone = new ArrayList<>(branchDone);
			newBranchDone.add(false);
			printTree(newBranchItems, newBranchDone, baseIngredients);
			for(int depth = 0; depth < branchItems.size(); depth++){
				printChars(branchItems.get(depth).length()+3, ' ');
				System.out.print((branchDone.get(depth) ? ' ' : '|') + "   ");
			}
			System.out.println();
			for(int depth = 0; depth < branchItems.size()-1; depth++){
				printChars(branchItems.get(depth).length()+3, ' ');
				System.out.print((branchDone.get(depth) ? ' ' : '|') + "   ");
			}
			printChars(branchItems.get(branchItems.size()-1).length()+3, ' ');
			System.out.print("+-- ");
			newBranchItems = new ArrayList<>(branchItems);
			newBranchItems.add(items.get(newBranchItems.get(newBranchItems.size()-1)).store.split("#")[2]);
			newBranchDone = new ArrayList<>(branchDone);
			newBranchDone.set(newBranchDone.size()-1, true);
			newBranchDone.add(false);
			printTree(newBranchItems, newBranchDone, baseIngredients);
			
		}else{
			String itemName = branchItems.get(branchItems.size()-1);
			baseIngredients.add(new String[] {items.get(itemName).store, itemName});
			System.out.println(branchItems.get(branchItems.size()-1));
		}
	}
	
	
	static void printChars(int quantity, char character){
		for(int spaces = 0; spaces < quantity; spaces++){
			System.out.print(character);
		}
	}
	
}