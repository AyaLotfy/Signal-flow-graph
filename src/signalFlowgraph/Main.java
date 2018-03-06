package signalFlowgraph;

public class Main {
	public static void main(String[] args) {
		Graph<Integer> graph = new Graph<>(true);

//		graph.addEdge(1, 2, 1);
//		graph.addEdge(2, 3, 5);
//		graph.addEdge(2, 7, 10);
//		graph.addEdge(3, 4, 10);
//		graph.addEdge(5, 2, -1);
//		graph.addEdge(4, 5, 2);
//		graph.addEdge(7, 5, 2);
//		graph.addEdge(5, 6, 1);
//		graph.addEdge(7, 7, -1);
		
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

		SEG seg = new SEG<>();
		double result = seg.input(graph, 1, 6);
		System.out.println(result);
	}
}
