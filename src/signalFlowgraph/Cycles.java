package signalFlowgraph;

import java.util.*;

public class Cycles {
	Set<Vertex<Integer>> blockedSet;
	Map<Vertex<Integer>, Set<Vertex<Integer>>> blockedMap;
	Deque<Vertex<Integer>> stack;
	List<List<Vertex<Integer>>> allCycles;

	public List<List<Vertex<Integer>>> simpleCyles(Graph<Integer> graph) {

		blockedSet = new HashSet<>();
		blockedMap = new HashMap<>();
		stack = new LinkedList<>();
		allCycles = new ArrayList<>();
		long startIndex = 1;
		SCC tarjan = new SCC();
		while (startIndex <= graph.getAllVertex().size()) {
			// Graph <Integer> subGraph = createSubGraph(startIndex, graph);
			Graph<Integer> subGraph = createSubGraph(1, graph);

			List<Set<Vertex<Integer>>> sccs = tarjan.scc(subGraph);

			Vertex<Integer> leastVertex = subGraph.getVertex(startIndex);

			blockedSet.clear();
			blockedMap.clear();
			findCyclesInSCG(leastVertex, leastVertex);
			startIndex = leastVertex.getId() + 1;
			// } else {
			// break;
			// }
		}
		return allCycles;
	}

	private Optional<Vertex<Integer>> leastIndexSCC(List<Set<Vertex<Integer>>> sccs, Graph<Integer> subGraph) {
		long min = Integer.MAX_VALUE;
		Vertex<Integer> minVertex = null;
		Set<Vertex<Integer>> minScc = null;
		for (Set<Vertex<Integer>> scc : sccs) {
			if (scc.size() == 1) {
				continue;
			}
			for (Vertex<Integer> vertex : scc) {
				if (vertex.getId() < min) {
					min = vertex.getId();
					minVertex = vertex;
					minScc = scc;
				}
			}
		}

		if (minVertex == null) {
			return Optional.empty();
		}
		Graph<Integer> graphScc = new Graph<>(true);
		for (Edge<Integer> edge : subGraph.getAllEdges()) {
			if (minScc.contains(edge.getVertex1()) && minScc.contains(edge.getVertex2())) {
				graphScc.addEdge(edge.getVertex1().getId(), edge.getVertex2().getId());
			}
		}
		return Optional.of(graphScc.getVertex(minVertex.getId()));
	}

	private void unblock(Vertex<Integer> u) {
		blockedSet.remove(u);
		if (blockedMap.get(u) != null) {
			blockedMap.get(u).forEach(v -> {
				if (blockedSet.contains(v)) {
					unblock(v);
				}
			});
			blockedMap.remove(u);
		}
	}

	private boolean findCyclesInSCG(Vertex<Integer> startVertex, Vertex<Integer> currentVertex) {
		boolean foundCycle = false;
		stack.push(currentVertex);
		blockedSet.add(currentVertex);

		for (Edge<Integer> e : currentVertex.getEdges()) {
			Vertex<Integer> neighbor = e.getVertex2();

			if (neighbor == startVertex) {
				List<Vertex<Integer>> cycle = new ArrayList<>();
				stack.push(startVertex);
				List<Vertex<Integer>> tempListSolveDup = new ArrayList<>();
				tempListSolveDup.addAll(stack);
				tempListSolveDup.remove(tempListSolveDup.size() - 1);

				cycle.addAll(stack);

				Collections.reverse(cycle);
				stack.pop();
				boolean[] visited = new boolean[allCycles.size()];
				boolean marked = false;
				if (allCycles.size() == 0) {
					allCycles.add(cycle);
					foundCycle = true;
					marked = true;
				} else {

					boolean notFound = true;
					for (int i = 0; i < allCycles.size(); i++) {
						int index = -1;
						List<Vertex<Integer>> cycleOfI = new ArrayList<>();
						cycleOfI = allCycles.get(i);

						int ids[] = new int[cycleOfI.size()];
						int temp[] = new int[tempListSolveDup.size()];
						for (int j = 0; j < cycleOfI.size(); j++) {
							ids[j] = (int) cycleOfI.get(j).getId();

						}
						for (int j = 0; j < temp.length; j++) {
							temp[j] = (int) tempListSolveDup.get(j).getId();
						}

						if (tempListSolveDup.size() == cycleOfI.size() - 1) {
							for (int j = 0; j < cycleOfI.size() - 1; j++) {

								int cycI = ids[j];

								int[] mmm = new int[temp.length];
								for (int k = 0; k < temp.length; k++) {
									index = -1;

									if (cycI != temp[k]) {

										mmm[k] = 1;

									}
								}
								boolean vvv = false;
								for (int k = 0; k < mmm.length; k++) {
									if (mmm[k] == 0) {
										vvv = true;

										break;
									}
								}
								if (!vvv) {
									visited[i] = true;
									index = i;
									foundCycle = true;
								}
								if (index != -1) {
									break;
								}

							}

						} else {
							visited[i] = true;

						}

					}
				}
				if (!marked) {
					boolean check = false;
					for (int i = 0; i < visited.length; i++) {
						if (visited[i] == false) {
							check = true;
							break;
						}
					}
					if (!check) {
						allCycles.add(cycle);
						foundCycle = true;
					}
				}

			} 
			
//			
//			else if (stack.contains(neighbor)) {
//
//				List<Vertex<Integer>> cycle = new ArrayList<>();
//				stack.push(neighbor);
//				List<Vertex<Integer>> tempListSolveDup = new ArrayList<>();
//				tempListSolveDup.addAll(stack);
//				tempListSolveDup.remove(tempListSolveDup.size() - 1);
//
//				cycle.addAll(stack);
//
//				Collections.reverse(cycle);
//				int del = 0;
//				for (int i = 0; i < cycle.size(); i++) {
//					if (cycle.get(i-del) == neighbor) {
//						break;
//					} else {
//						cycle.remove(i - del);
//						del++;
//					}
//				}
//				stack.pop();
//				boolean[] visited = new boolean[allCycles.size()];
//				boolean marked = false;
//				if (allCycles.size() == 0) {
//					allCycles.add(cycle);
//					foundCycle = true;
//					marked = true;
//				} else {
//
//					boolean notFound = true;
//					for (int i = 0; i < allCycles.size(); i++) {
//						int index = -1;
//						List<Vertex<Integer>> cycleOfI = new ArrayList<>();
//						cycleOfI = allCycles.get(i);
//
//						int ids[] = new int[cycleOfI.size()];
//						int temp[] = new int[tempListSolveDup.size()];
//						for (int j = 0; j < cycleOfI.size(); j++) {
//							ids[j] = (int) cycleOfI.get(j).getId();
//
//						}
//						for (int j = 0; j < temp.length; j++) {
//							temp[j] = (int) tempListSolveDup.get(j).getId();
//						}
//
//						if (tempListSolveDup.size() == cycleOfI.size() - 1) {
//							for (int j = 0; j < cycleOfI.size() - 1; j++) {
//
//								int cycI = ids[j];
//
//								int[] mmm = new int[temp.length];
//								for (int k = 0; k < temp.length; k++) {
//									index = -1;
//
//									if (cycI != temp[k]) {
//
//										mmm[k] = 1;
//
//									}
//								}
//								boolean vvv = false;
//								for (int k = 0; k < mmm.length; k++) {
//									if (mmm[k] == 0) {
//										vvv = true;
//
//										break;
//									}
//								}
//								if (!vvv) {
//									visited[i] = true;
//									index = i;
//									foundCycle = true;
//								}
//								if (index != -1) {
//									break;
//								}
//
//							}
//
//						} 
//			else {
//							visited[i] = true;
//
//						}
//
//					}
//				}
//				if (!marked) {
//					boolean check = false;
//					for (int i = 0; i < visited.length; i++) {
//						if (visited[i] == false) {
//							check = true;
//							break;
//						}
//					}
//					if (!check) {
//						allCycles.add(cycle);
//						foundCycle = true;
//					}
//				}
//
//			} 
			else if (!blockedSet.contains(neighbor)) {
				boolean gotCycle = findCyclesInSCG(startVertex, neighbor);
				foundCycle = foundCycle || gotCycle;
			}
		}

		if (foundCycle) {

			unblock(currentVertex);
		} else {

			for (Edge<Integer> e : currentVertex.getEdges()) {
				Vertex<Integer> w = e.getVertex2();
				// Set<Vertex<Integer>> bSet = getBSet(w);

				Set<Vertex<Integer>> parent = new HashSet<>();
				parent.add(currentVertex);
				Set<Vertex<Integer>> bSet = getBSet(w, parent);

				bSet.add(currentVertex);

				int x = 9999;

			}
		}
		stack.pop();
		return foundCycle;
	}

	private Set<Vertex<Integer>> getBSet(Vertex<Integer> v, Set<Vertex<Integer>> parent) {

		// Set<Vertex<Integer>> b = blockedMap.computeIfAbsent(v, (key) -> new
		// HashSet<>());
		blockedMap.put(v, parent);

		return parent;
	}

	private Graph createSubGraph(long startVertex, Graph<Integer> graph) {
		Graph<Integer> subGraph = new Graph<>(true);
		for (Edge<Integer> edge : graph.getAllEdges()) {
			if (edge.getVertex1().getId() >= startVertex && edge.getVertex2().getId() >= startVertex) {
				subGraph.addEdge(edge.getVertex1().getId(), edge.getVertex2().getId());
			}
		}
		return subGraph;
	}

	public static void main(String args[]) {
		Cycles c = new Cycles();
		Graph<Integer> graph = new Graph<>(true);
		// graph.addEdge(1, 2);
		// graph.addEdge(1, 8);
		// graph.addEdge(1, 5);
		// graph.addEdge(2, 9);
		// graph.addEdge(2, 7);
		// graph.addEdge(2, 3);
		// graph.addEdge(3, 1);
		// graph.addEdge(3, 2);
		// graph.addEdge(3, 6);
		// graph.addEdge(3, 4);
		// graph.addEdge(6, 4);
		// graph.addEdge(4, 5);
		// graph.addEdge(5, 2);
		// graph.addEdge(8, 9);
		// graph.addEdge(9, 8);

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

		List<List<Vertex<Integer>>> allCycles = c.simpleCyles(graph);
		allCycles.forEach(cycle -> {
			StringJoiner joiner = new StringJoiner("->");
			cycle.forEach(vertex -> joiner.add(String.valueOf(vertex.getId())));
			System.out.println(joiner);
		});
	}

}