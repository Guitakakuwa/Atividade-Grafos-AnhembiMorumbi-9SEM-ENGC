import java.util.ArrayList;
import java.util.HashMap;

public class Graph<V> {

	private HashMap<V, ArrayList<Edge<V>>> adjacencyList;

	private ArrayList<V> vertexList;

	private boolean directed;

	public Graph(boolean isDirected) {
		directed = isDirected;
		adjacencyList = new HashMap<V, ArrayList<Edge<V>>>();
		vertexList = new ArrayList<V>();
	}

	public void add(V vertex, ArrayList<Edge<V>> connectedVertices) {
		adjacencyList.put(vertex, connectedVertices);
		vertexList.add(vertex);
			for (Edge<V> vertexConnectedToAddedVertex : connectedVertices) {
				ArrayList<Edge<V>> correspondingConnectedList = adjacencyList
						.get(vertexConnectedToAddedVertex.getVertex());
				if (correspondingConnectedList == null) {
					adjacencyList.put(vertexConnectedToAddedVertex.getVertex(),
							new ArrayList<Edge<V>>());
					vertexList.add(vertexConnectedToAddedVertex.getVertex());
					correspondingConnectedList = adjacencyList
							.get(vertexConnectedToAddedVertex.getVertex());
				}
				
				if (!directed) {
					int weight = vertexConnectedToAddedVertex.getWeight();
					correspondingConnectedList.add(new Edge<V>(vertex, weight));
				}
			}
		
	}

	public boolean addArc(V source, V end, int weight) {
		if (!directed) {
			return false;
		}

		if (!adjacencyList.containsKey(source)) {
			ArrayList<Edge<V>> tempList = new ArrayList<Edge<V>>();
			tempList.add(new Edge<V>(end, weight));
			add(source, tempList);
			return true;
		}
		
		if (!adjacencyList.containsKey(end)) {
			ArrayList<Edge<V>> tempList = new ArrayList<Edge<V>>();
			add(end, tempList);
		}
		

		adjacencyList.get(source).add(new Edge<V>(end, weight));
		return true;
	}

	public boolean addEdge(V vertexOne, V vertexTwo, int weight) {
		if (directed) {
			return false;
		}
		
		if (!adjacencyList.containsKey(vertexOne)) {
			ArrayList<Edge<V>> tempList = new ArrayList<Edge<V>>();
			tempList.add(new Edge<V>(vertexTwo, weight));
			add(vertexOne, tempList);
			return true;
		}

		if (!adjacencyList.containsKey(vertexTwo)) {
			ArrayList<Edge<V>> tempList = new ArrayList<Edge<V>>();
			tempList.add(new Edge<V>(vertexOne, weight));
			add(vertexTwo, tempList);
			return true;
		}

		adjacencyList.get(vertexOne).add(new Edge<V>(vertexTwo, weight));
		adjacencyList.get(vertexTwo).add(new Edge<V>(vertexOne, weight));
		return true;
	}

	public ArrayList<V> getAdjacentVertices(V vertex){
		ArrayList<V> returnList = new ArrayList<V>();
		for (Edge<V> edge : adjacencyList.get(vertex)) {
			returnList.add(edge.getVertex());
		}
		return returnList;
	}
	
	public double getDistanceBetween(V source, V end){
		 for (Edge<V> edge : adjacencyList.get(source)) {
			if (edge.getVertex() == end){
				return edge.getWeight();
			}
		}
		return Double.POSITIVE_INFINITY;
	}
	
	public ArrayList<V> getVertexList() {
		return vertexList;
	}
	
	public String toString() {
		String s = "";
		for (V vertex : vertexList) {
			s += vertex.toString();
			s += " : ";
			s += adjacencyList.get(vertex);
			s += "\n";
		}
		return s;
	}
}
