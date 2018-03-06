package signalFlowgraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Paths {

	// private static final long START = 2;
	// private static final long END = 5;
	static List<List<Vertex<Integer>>> allPaths=new ArrayList<>();
	Graph<Integer> graph = new Graph<>(true);

	public static void main(String[] args) {
		// this graph is directional
		Graph<Integer> graph = new Graph<>(true);
		// a1 b2 c3 d4 e5 f6

		// graph.addEdge("A", "B");
		// graph.addEdge("A", "C");
		// graph.addEdge("B", "A");
		// graph.addEdge("B", "D");
		// graph.addEdge("B", "E"); // this is the only one-way connection
		// graph.addEdge("B", "F");
		// graph.addEdge("C", "A");
		// graph.addEdge("C", "E");
		// graph.addEdge("C", "F");
		// graph.addEdge("D", "B");
		// graph.addEdge("E", "C");
		// graph.addEdge("E", "F");
		// graph.addEdge("F", "B");
		// graph.addEdge("F", "C");
		// graph.addEdge("F", "E");

//		graph.addEdge(1, 2);
//		graph.addEdge(1, 3);
//		graph.addEdge(2, 1);
//		graph.addEdge(2, 4);
//		graph.addEdge(2, 5); // this is the only one-way connection
//		graph.addEdge(2, 6);
//		graph.addEdge(3, 1);
//		graph.addEdge(3, 5);
//		graph.addEdge(3, 6);
//		graph.addEdge(4, 2);
//		graph.addEdge(5, 3);
//		graph.addEdge(5, 6);
//		graph.addEdge(6, 2);
//		graph.addEdge(6, 3);
//		graph.addEdge(6, 5);
		
		
		
		graph.addEdge(1, 2, 1);
		graph.addEdge(2, 3, 5);
		graph.addEdge(2, 7, 10);
		graph.addEdge(3, 4, 10);
		graph.addEdge(5, 2, -1);
		graph.addEdge(4, 5, 2);
		
		
		///
		graph.addEdge(5, 4, -2);
		graph.addEdge(4, 3, -1);
		////
		
		
		graph.addEdge(7, 5, 2);
		graph.addEdge(5, 6, 1);
		graph.addEdge(7, 7, -1);
		LinkedList visited = new LinkedList();
		Long START = (long) 1;
		Long END = (long) 6;
		visited.add(START);
		new Paths().depthFirst(graph, visited, START, END);
		int m = allPaths.size();
		for (int i = 0; i <allPaths.size() ; i++) {
			System.out.println(allPaths.get(i));
		}
	}

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	void depthFirst(Graph graph, LinkedList visited, long START, long END) {
		this.graph = graph;
		// LinkedList<String> nodes = graph.adjacentNodes(visited.getLast());
		// long id = Integer.parseInt(visited.getLast());
		Vertex<Integer> v = graph.getVertex((long) visited.getLast());
		List<Vertex<Integer>> nodes = v.getAdjacentVertexes();
		// examine adjacent nodes
		for (Vertex<Integer> node : nodes) {
			long i = node.getId();
			boolean b = visited.contains(i);
			if (b) {
				continue;
			}
			if (node.getId() == (END)) {
				visited.add((long) node.getId());
				printPath(visited);
				visited.removeLast();
				break;
			}
		}
		for (Vertex<Integer> node : nodes) {

			long i = node.getId();

			if (visited.contains(i) || i == (END)) {
				continue;
			}
			visited.addLast((long) node.getId());
			depthFirst(graph, visited, START, END);
			visited.removeLast();
			// return;
		}
	}
	
	private void printPath(LinkedList visited) {
		List<Vertex<Integer>> path = new ArrayList<>();

		for (int i = 0; i < visited.size(); i++) {
			path.add(graph.getVertex((long) visited.get(i)));
			// System.out.print(visited.get(i));
			// System.out.print(" ");
		}
		allPaths.add(path);
		
	//	System.out.println();
	}

}