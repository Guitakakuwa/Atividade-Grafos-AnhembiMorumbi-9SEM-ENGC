import java.io.BufferedReader;
import java.io.File; // Import the File class
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Enum.DirectedGraphState;
import utils.Utils;

public class Driver {

	public static ArrayList<Edge<String>> connected;
	public static Graph<String> graph;
	public static boolean isGraphDirected;
	public static int numberOfVertex;

	public static void main(String[] args) throws IOException {
		Driver.startGraphCreation();
		
		System.out.println(Driver.dijkstraShortestPath(Driver.graph, "A"));
	}

	public static void startGraphCreation() throws IOException {

//		File graphFile = new File("NonWeightGraph.txt");
//		File graphFile = new File("NonWeightGraphWithError.txt");
		File graphFile = new File("WeightedGraph.txt");
//		File graphFile = new File("WeightedGraphWithError.txt");
		
		@SuppressWarnings("resource")
		BufferedReader bufferReaderObject = new BufferedReader(new FileReader(graphFile));

		String textLineContent;
		int lineCounter = 1;
		while ((textLineContent = bufferReaderObject.readLine()) != null) {
			System.out.println("Line: " + lineCounter);
			switch (lineCounter) {
			case 1: {
				// CHECK IF GRAPH IS DIRECTED
				Driver.isGraphDirected = textLineContent.equals(DirectedGraphState.NON_DIRECTED.toString()) ? false : true;
				Driver.askIfGraphIsDirected(Driver.isGraphDirected);
				
				break;
			}
			
			case 2: {
				// GET NUMBER OF VERTEX
				Driver.getNumberOfVertex(textLineContent);
				System.out.println(textLineContent);
				System.out.println(" ");

				break;
			}
			
			default:
				if (lineCounter <= + 2 + Driver.numberOfVertex) {
					// VERTEX CREATION
					System.out.println(textLineContent);
					System.out.println(" ");
					connected.add(new Edge<String>(textLineContent, 0));
					
					break;
				} else {
					if (textLineContent.length() == 3) {
						//NON WEIGHT ARC
						createArc(textLineContent);
						System.out.println(textLineContent);
						System.out.println(" ");
						break;
					} else if (textLineContent.length() >= 5) {
						// WEIGHT ARC
						createArcWithWeight(textLineContent);
						System.out.println(textLineContent);
						System.out.println(" ");
						break;
					} else {
						System.out.println("Erro de sintaxe ao criar grafo com conteudo do arquivo");
						System.out.println("Erro de conteudo: "+ textLineContent);
					}
				}
			}
			lineCounter++;
		}
		System.out.println(" ");
		System.out.println(graph.toString());
	}

	public static void createArcWithWeight(String arcCreationStringText) {
		String regexExpression = "([A-z]),([A-z]),([0-9])+";

		if (arcCreationStringText.matches(regexExpression)) {
			String firstVertex = String.valueOf(arcCreationStringText.charAt(0));
			String secondVertex = String.valueOf(arcCreationStringText.charAt(2));
			
			int weightOfVertex = Integer.parseInt(arcCreationStringText.substring(4));
			graph.addArc(firstVertex, secondVertex, weightOfVertex);
		}else{
			System.out.println("Erro de sintaxe ao criar grafo com conteudo do arquivo");
			System.out.println("Erro de conteudo: "+ arcCreationStringText);
		}
	}

	public static void createArc(String arcCreationStringText) {

		String regexExpression = "([A-z]),([A-z])+";

		if (arcCreationStringText.matches(regexExpression)) {
			String firstVertex = String.valueOf(arcCreationStringText.charAt(0));
			String secondVertex = String.valueOf(arcCreationStringText.charAt(2));
			graph.addArc(firstVertex, secondVertex, 0);
		}else{
			System.out.println("Erro de sintaxe ao criar grafo com conteudo do arquivo");
			System.out.println("Erro de conteudo: "+ arcCreationStringText);
		}
	}

	public static void getNumberOfVertex(String numberOfVertexInString) {

		if (!Utils.isValidStringNumber(numberOfVertexInString)) {
			System.out.println("Número invalido, tente novamente.");
		} else {
			Driver.numberOfVertex = Integer.parseInt(numberOfVertexInString);
		}
	}

	private static void askIfGraphIsDirected(boolean graphIsDirected) {

		Driver.isGraphDirected = graphIsDirected;

		String selectedGraphConfiguration = graphIsDirected ? "Grafo Direcionado" : "Grafo Não Direcionado";
		System.out.println("Configuração Atual de Grafo:");
		System.out.println("- " + selectedGraphConfiguration);
		System.out.println(" ");

		Driver.graph = new Graph<String>(graphIsDirected);
		Driver.connected = new ArrayList<Edge<String>>();

	}
	
	
	public static <Vertex> HashMap<Vertex, Double> dijkstraShortestPath(Graph<Vertex> graph,
			Vertex source) {
		HashMap<Vertex, Double> distances = new HashMap<Vertex, Double>();
		ArrayList<Vertex> queue = new ArrayList<Vertex>();
		ArrayList<Vertex> visited = new ArrayList<Vertex>();
		queue.add(0, source);
		distances.put(source, 0.0);
		while (!queue.isEmpty()) {

			Vertex currentVertex = queue.remove(queue.size() - 1);

			if (distances.get(currentVertex) == null) {
				distances.put(currentVertex, Double.POSITIVE_INFINITY);
			}
			
			for (Vertex adjacentVertex : graph.getAdjacentVertices(currentVertex)) {
				if (distances.get(adjacentVertex) == null) {
					distances.put(adjacentVertex, Double.POSITIVE_INFINITY);
				}


				if (distances.get(adjacentVertex) > graph
						.getDistanceBetween(currentVertex, adjacentVertex)
						+ distances.get(currentVertex)) {

					distances.put(adjacentVertex,graph.getDistanceBetween(currentVertex,adjacentVertex)+ distances.get(currentVertex));
				}
				

				if (!visited.contains(adjacentVertex)
						&& !queue.contains(adjacentVertex)) {
					queue.add(0, adjacentVertex);
				}
			}
			visited.add(currentVertex);

		}

		for (Vertex vertex : graph.getVertexList()) {
			if (!distances.containsKey(vertex)) {
				distances.put(vertex, Double.POSITIVE_INFINITY);
			}
		}

		return distances;
	}
}
