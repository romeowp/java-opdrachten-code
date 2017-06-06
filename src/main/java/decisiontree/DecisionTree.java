package decisiontree;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DecisionTree {

	static List<Node> nodes = new ArrayList<Node>();
	static List<Edge> edges = new ArrayList<Edge>();
	static List<String> leafNodes;
	static Scanner in = new Scanner(System.in);
	
	public static void main(final String[] args) throws IOException {
    	List<String> lines = Files.readAllLines(Paths.get("src\\main\\resources\\decision-tree-data.txt"));
    	ArrayList<String> origins = new ArrayList<String>();
		ArrayList<String> destinations = new ArrayList<String>();
    	for(String line : lines){
    		String[] parsed = line.split(", ");
    		if(parsed.length == 2){
    			nodes.add(new Node(parsed[0], parsed[1]));
    		}else if(parsed.length == 3){
    			edges.add(new Edge(parsed[0], parsed[1], parsed[2].toLowerCase()));
    			origins.add(parsed[0]);
    			if(!destinations.contains(parsed[1])){
    				destinations.add(parsed[1]);
    			}
    		}
    	}
    	leafNodes = new ArrayList<String>(destinations);
    	leafNodes.removeAll(origins);
    	origins.removeAll(destinations);
    	String nextNode = origins.get(0);
    	while(nextNode != null){
    		nextNode = decision(nextNode);
    	}
    	in.close();
	}
	
	static String decision(String nextNode){
		for(Node node : nodes){
			if(node.name.equals(nextNode)){
				System.out.println(node.question);
				if(leafNodes.contains(nextNode)){
					return null;
				}
				String answer = in.nextLine().toLowerCase();
				for(Edge edge : edges){
					if(edge.origin.equals(nextNode) && edge.answer.equals(answer)){
						return edge.destination;
					}
				}
				System.out.println("Uw antwoord is ongeldig.");
				return null;
			}
		}
		throw new Error("Nonexistent destination node.");
	}
}
