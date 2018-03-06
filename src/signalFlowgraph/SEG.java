package signalFlowgraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SEG<T> implements ISFG {
	Graph<Integer> graph = new Graph<>(true);

	List<List<Vertex<Integer>>> allCycles = new ArrayList<>();
	List<List<Vertex<Integer>>> allPaths = new ArrayList<>();
	ArrayList<String> resultArray = new ArrayList<>();
	double deltaIArray[] = new double[allPaths.size()];
	ArrayList<String> nonTouchingLoops = new ArrayList<>();
	double mIArray[] ;
	double loopsGain[] ;

	public double solve(List<List<Vertex<Integer>>> allCycles, List<List<Vertex<Integer>>> allPaths) {
		double result = 0;
		// Map<String, List<List<List<Vertex<Integer>>>>> allCombination = new
		// HashMap<>();
		ArrayList<String> allCombination = new ArrayList<>();
		for (int i = 0; i < loopsGain.length; i++) {
			loopsGain[i] = getMI(allCycles.get(i));
			System.out.println(loopsGain[i]);
		}
		Object[] elem = new Object[allCycles.size()];
		for (int i = 0; i < elem.length; i++) {
			elem[i] = i;

		}
		for (int i = 1; i < elem.length + 1; i++) {
			combination(elem, i);

		}
		allCombination = this.resultArray;
		// allCombination = combinationCycles(allCycles.size());
		for (int i = 0; i < allPaths.size(); i++) {
			double mi = getMI(allPaths.get(i));
			double deltIVar = getDeltaI(allPaths.get(i), allCombination);
			// result += getMI(allPaths.get(i)) * getDeltaI(allPaths.get(i),
			mIArray[i] = mi;
			// allCombination);
			deltaIArray[i] = deltIVar;
			result += mi * deltIVar;

		}
		double delta = getDelta(allCombination);
		// System.out.println(delta);
		result = result / delta;
		return result;
	}

	@Override
	public double input(Graph graph, long start, long end) {
		this.graph = graph;
		Cycles c = new Cycles();
		List<List<Vertex<Integer>>> allCycles = c.simpleCyles(graph);
		Paths p = new Paths();
		LinkedList visited = new LinkedList();
		visited.add(start);

		p.depthFirst(graph, visited, start, end);
		this.allCycles = allCycles;
		this.allPaths = p.allPaths;
		deltaIArray = new double[allPaths.size()];
		 mIArray = new double[allPaths.size()];
		 loopsGain = new double[allCycles.size()];
		return solve(allCycles, p.allPaths);

	}

	double getDelta(ArrayList<String> allCombination) {

		int delta = 1;
		int sign = -1;
		int len = 0;

		double allCyclesWight = 1;
		String nonTouchingLoops = "(1)";
		int loop = 0;

		while (loop < allCombination.size()) {
			String[] spliter = allCombination.get(loop).split(" ");
			if (spliter.length == 1) {
				List<Vertex<Integer>> list1 = allCycles.get(Integer.parseInt(spliter[0]));
				double oneCycleWieght = 1;

				oneCycleWieght = getMI(allCycles.get(Integer.parseInt(spliter[0])));
				allCyclesWight += Math.pow(-1, spliter.length) * oneCycleWieght;

				nonTouchingLoops += "-" + listToString(list1);

			} else {
				int[][] allComTwo = allTwo(spliter.length);
				boolean bool = false;
				for (int j = 0; j < allComTwo.length; j++) {

					int first = allComTwo[j][0];
					int second = allComTwo[j][1];
					boolean nonTouched = nonTouchingLoops(allCycles.get(Integer.parseInt(spliter[(first)])),
							allCycles.get(Integer.parseInt(spliter[(second)])));
					if (!nonTouched) {
						bool = true;

						break;
					}

				}

				if (!bool) {

					int oneCycleWieght = 1;
					if (Math.pow(-1, spliter.length) == -1) {
						nonTouchingLoops += "-";
					} else {
						nonTouchingLoops += "+";
					}
					for (int i = 0; i < spliter.length; i++) {
						List<Vertex<Integer>> list1 = allCycles.get(Integer.parseInt(spliter[i]));
						oneCycleWieght *= getMI(allCycles.get(Integer.parseInt(spliter[i])));

						nonTouchingLoops += listToString(list1);

					}

					allCyclesWight += Math.pow(-1, spliter.length) * oneCycleWieght;

				}

			}

			loop++;

		}
		this.nonTouchingLoops.add(nonTouchingLoops);
		return allCyclesWight;

	}

	double getDeltaI(List<Vertex<Integer>> path, ArrayList<String> allCombination) {

		int delta = 1;
		int sign = -1;
		int len = 0;

		double allCyclesWight = 1;

		int loop = 0;
		String nonTouchingLoops = "(1)";

		while (loop < allCombination.size()) {
			String[] spliter = allCombination.get(loop).split(" ");
			if (spliter.length == 1) {
				List<Vertex<Integer>> list1 = allCycles.get(Integer.parseInt(spliter[0]));
				if (nonTouchingLoops(list1, path)) {
					double oneCycleWieght = 1;

					oneCycleWieght = getMI(allCycles.get(Integer.parseInt(spliter[0])));

					nonTouchingLoops += "-" + listToString(list1);
					allCyclesWight += Math.pow(-1, spliter.length) * oneCycleWieght;
				}

			} else {
				int[][] allComTwo = allTwo(spliter.length);
				boolean bool = false;
				for (int j = 0; j < allComTwo.length; j++) {

					int first = allComTwo[j][0];
					int second = allComTwo[j][1];
					boolean nonTouched = nonTouchingLoops(allCycles.get(Integer.parseInt(spliter[(first)])),
							allCycles.get(Integer.parseInt(spliter[(second)])));
					if (!nonTouched) {
						bool = true;

						break;
					}

				}
				boolean boolPath = false;
				for (int i = 0; i < spliter.length; i++) {
					List<Vertex<Integer>> list1 = allCycles.get(Integer.parseInt(spliter[i]));

					boolean nonTouched = nonTouchingLoops(list1, path);
					if (!nonTouched) {
						boolPath = true;
						break;
					}

				}

				if (!bool && !boolPath) {

					int oneCycleWieght = 1;
					if (Math.pow(-1, spliter.length) == -1) {
						nonTouchingLoops += "-";
					} else {
						nonTouchingLoops += "+";
					}
					for (int i = 0; i < spliter.length; i++) {
						List<Vertex<Integer>> list1 = allCycles.get(Integer.parseInt(spliter[i]));
						nonTouchingLoops += listToString(list1);

						oneCycleWieght *= getMI(allCycles.get(Integer.parseInt(spliter[i])));

					}
					allCyclesWight += Math.pow(-1, spliter.length) * oneCycleWieght;

				}

			}

			loop++;

		}

		this.nonTouchingLoops.add(nonTouchingLoops);

		return allCyclesWight;
	}

	double getMI(List<Vertex<Integer>> path) {
		double m = 1;
		double weight = 1;
		for (int i = 1; i < path.size(); i++) {
			long id1 = path.get(i - 1).getId();
			long id2 = path.get(i).getId();
			weight = graph.getVertex(id1).getEdgeByV2(graph.getVertex(id2));
			m *= weight;

		}

		return m;

	}

	ArrayList combinationCycles(int maxLenght) {

		ArrayList<String> result = new ArrayList<>();
		for (int i = 0; i < maxLenght; i++) {
			String s = "";
			for (int j = i; j < maxLenght; j++) {
				s += j + " ";
				result.add(s);
			}
		}
		return result;

	}

	int[][] allTwo(int length) {
		List<Integer> arr = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			arr.add(i);
		}
		int[][] all = new int[arr.size() * (arr.size() - 1) / 2][2];
		int k = 0;
		for (int i = 0; i < arr.size(); i++) {
			for (int j = i + 1; j < arr.size(); j++) {
				all[k][0] = arr.get(i);

				all[k][1] = arr.get(j);
				k++;

			}

		}
		return all;
	}

	String listToString(List<Vertex<Integer>> list) {
		String s = "";
		for (int i = 0; i < list.size(); i++) {
			s += "y" + list.get(i).getId() + " ";
		}
		return s;
	}

	public void combination(Object[] elements, int K) {

		int N = elements.length;

		if (K > N) {
			System.out.println("Invalid input, K > N");
			return;
		}

		c(N, K);

		int combination[] = new int[K];

		int r = 0;
		int index = 0;

		while (r >= 0) {

			if (index <= (N + (r - K))) {
				combination[r] = index;

				if (r == K - 1) {

					String output = "";
					for (int z = 0; z < combination.length; z++) {
						output += elements[combination[z]] + " ";
					}
					resultArray.add(output);

					index++;
				} else {
					index = combination[r] + 1;
					r++;
				}
			} else {
				r--;
				if (r > 0)
					index = combination[r] + 1;
				else
					index = combination[0] + 1;
			}
		}
	}

	public int c(int n, int r) {
		int nf = fact(n);
		int rf = fact(r);
		int nrf = fact(n - r);
		int npr = nf / nrf;
		int ncr = npr / rf;

		return ncr;
	}

	public int fact(int n) {
		if (n == 0)
			return 1;
		else
			return n * fact(n - 1);
	}

	void combination(String a[]) {

		Map<String, List<List<String>>> map;
		map = new HashMap<>();

		for (int i = 0; i < a.length; i++) {
			String s = "";
			for (int j = i; j < a.length; j++) {
				s += a[j] + " ";

				List<String> tempList = new ArrayList<String>(Arrays.asList(s.split(" ")));
				String len = tempList.size() + "";
				List<List<String>> mapList = new ArrayList<>();

				mapList = map.get(len);
				if (mapList == null) {
					mapList = new ArrayList<>();
					mapList.add(tempList);

				} else {
					mapList.add(tempList);

				}
				map.put(len, mapList);

			}
		}

		for (int i = 1; i < a.length + 1; i++) {
			List<List<String>> mapList = null;

			mapList = map.get(i + "");

			for (int j = 0; j < mapList.size(); j++) {

				List<String> tempList = mapList.get(j);
				for (int k = 0; k < tempList.size(); k++) {
					System.out.print(tempList.get(k) + " ");
				}
				System.out.println();

			}

		}
	}

	boolean nonTouchingLoops(List<Vertex<Integer>> list1, List<Vertex<Integer>> list2) {
		String[] list1Array = new String[list1.size()];
		for (int i = 0; i < list1Array.length; i++) {
			list1Array[i] = list1.get(i).getId() + "";
		}
		String[] list2Array = new String[list2.size()];
		for (int i = 0; i < list2Array.length; i++) {
			list2Array[i] = list2.get(i).getId() + "";
		}
		for (int i = 0; i < list1Array.length; i++) {
			for (int j = 0; j < list2Array.length; j++) {
				if (list1Array[i].equals(list2Array[j])) {
					return false;
				}
			}
		}

		return true;
	}

	public static void main(String[] args) {
		Graph<Integer> graph = new Graph<>(true);

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
		for (int i = 0; i < seg.deltaIArray.length; i++) {
			//System.out.println(seg.deltaIArray[i]);
		}
		for (int i = 0; i < seg.nonTouchingLoops.size(); i++) {
			//System.out.println(seg.nonTouchingLoops.get(i));
		}
	}

}
