package signalFlowgraph;

import java.util.List;

public interface ISFG<T> {
	
	double input(Graph<T> graph,long start,long end);
	//boolean solve(	List<List<Vertex<Integer>>> allCycles,List<List<Vertex<Integer>>> allPaths);
}
