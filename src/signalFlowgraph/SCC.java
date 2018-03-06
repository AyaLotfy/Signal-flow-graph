package signalFlowgraph;


import java.util.*;


public class SCC {

	private Map<Vertex<Integer>, Integer> visitedTime;
	private Map<Vertex<Integer>, Integer> lowTime;
	private Set<Vertex<Integer>> onStack;
	private Deque<Vertex<Integer>> stack;
	private Set<Vertex<Integer>> visited;
	private List<Set<Vertex<Integer>>> result;
	private int time;

	public List<Set<Vertex<Integer>>> scc(Graph<Integer> graph) {

		time = 0;
		visitedTime = new HashMap<>();

		lowTime = new HashMap<>();

		onStack = new HashSet<>();

		stack = new LinkedList<>();

		visited = new HashSet<>();

		result = new ArrayList<>();

		for (Vertex<Integer> vertex : graph.getAllVertex()) {
			if (visited.contains(vertex)) {
				continue;
			}
			sccUtil(vertex);
		}

		return result;
	}

	private void sccUtil(Vertex<Integer> vertex) {

		visited.add(vertex);
		visitedTime.put(vertex, time);
		lowTime.put(vertex, time);
		time++;
		stack.addFirst(vertex);
		onStack.add(vertex);

		for (Vertex child : vertex.getAdjacentVertexes()) {
			
			if (!visited.contains(child)) {
				sccUtil(child);
				lowTime.compute(vertex, (v, low) -> Math.min(low, lowTime.get(child)));
			} 
			else if (onStack.contains(child)) {
				
				lowTime.compute(vertex, (v, low) -> Math.min(low, visitedTime.get(child)));
			}
		}

		if (visitedTime.get(vertex) == lowTime.get(vertex)) {
			Set<Vertex<Integer>> stronglyConnectedComponenet = new HashSet<>();
			Vertex v;
			do {
				v = stack.pollFirst();
				onStack.remove(v);
				stronglyConnectedComponenet.add(v);
			} while (!vertex.equals(v));
			result.add(stronglyConnectedComponenet);
		}
	}

	public static void main(String args[]) {
		Graph<Integer> graph = new Graph<>(true);
		graph.addEdge(1, 2);
		graph.addEdge(2, 3);
		graph.addEdge(3, 1);
		graph.addEdge(3, 4);
		graph.addEdge(4, 5);
		graph.addEdge(5, 6);
		graph.addEdge(6, 4);
		graph.addEdge(7, 6);
		graph.addEdge(7, 8);
		graph.addEdge(8, 7);

		

		SCC SCC = new SCC();
		List<Set<Vertex<Integer>>> result = SCC.scc(graph);

		result.forEach(scc -> {
			scc.forEach(vertex -> System.out.print(vertex + " "));
			System.out.println();
		});

	}

	
}