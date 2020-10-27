import java.util.ArrayList;
import java.util.Scanner;

import utils.Utils;

public class Driver {
	
	public static ArrayList<Edge<String>> connected;
	public static Graph<String> graph;
	public static boolean isGraphDirected;
	public static Scanner scannerObjectReader = new Scanner(System.in);
	
	public static void main(String[] args) {
		Driver.startGraphCreation();
	}
	
	
	public static void startGraphCreation() {
		askIfGraphIsDirected();
		askForVertexNumberAndLetter();
		askForVertex();
		
		System.out.println("Grafo Construido:");
		Driver.graph.toString();
	}


	public static void askForVertex() {
		System.out.println("As arestas terão peso?");
		System.out.println("Responda com sim ou não.");
		System.out.println("\n");
		String doesArcHaveWeight = scannerObjectReader.nextLine();
		
		if(doesArcHaveWeight.equals("não") || doesArcHaveWeight.equals("nao") || doesArcHaveWeight.equals("n")) {
			createArc();
		}else{
			createArcWithWeight();
		}
		
	}


	public static void createArcWithWeight() {
		System.out.println("Indique os grafos com criação da seguinte forma:");
		System.out.println("Vertice , Vertice, Peso");
		System.out.println("Ex: A,B,0");
		System.out.println("\n");
		
		String arcCrationStringText = scannerObjectReader.nextLine();
		String regexExpression = "([A-z]),([A-z]),([0-9])+";
		
		if(arcCrationStringText.matches(regexExpression)) {
			String firstVertex = String.valueOf(arcCrationStringText.charAt(0));
			String secondVertex = String.valueOf(arcCrationStringText.charAt(2));
			int weightOfVertex =  arcCrationStringText.charAt(4);
			graph.addArc(firstVertex, secondVertex,weightOfVertex);
		}
	}


	public static void createArc() {
		System.out.println("Indique os grafos com criação da seguinte forma:");
		System.out.println("Vertice , Vertice");
		System.out.println("Ex: A,B");
		System.out.println("\n");
		
		String arcCrationStringText = scannerObjectReader.nextLine();
		String regexExpression = "([A-z]),([A-z])+";
		
		if(arcCrationStringText.matches(regexExpression)) {
			String firstVertex = String.valueOf(arcCrationStringText.charAt(0));
			String secondVertex = String.valueOf(arcCrationStringText.charAt(2));
			graph.addArc(firstVertex, secondVertex,0);
		}
	}


	public static void askForVertexNumberAndLetter() {
		System.out.println("Informe a quantidade de vertices à serem formados. \n");
		String numberOfVertexInString = scannerObjectReader.nextLine();

		if(Utils.isValidStringNumber(numberOfVertexInString)) {
			createVertex(numberOfVertexInString);
		}else {
			System.out.println("Número invalido, tente novamente.");
			askForVertexNumberAndLetter();
		}
	}


	public static void createVertex(String numberOfVertexInString) {
		
		int numberOfVertex = Integer.parseInt(numberOfVertexInString);
		
		for (int i = 0; i < numberOfVertex; i++) {
			System.out.println("Indique a letra para representar o vértice.");
			String letterOfVertex = scannerObjectReader.nextLine();
			connected.add(new Edge<String>(letterOfVertex,0));
		}
	}


	private static void askIfGraphIsDirected() {
		System.out.println("Digite 0 para grafo não direcionado e 1 para grafo direcionado\n");
		Driver.isGraphDirected = scannerObjectReader.nextLine().equals(Utils.DirectedGraphState.NON_DIRECTED.toString()) ? false : true;

	    
	    String selectedGraphConfiguration = isGraphDirected ? "Grafo Direcionado" : "Grafo Não Direcionado";
    	System.out.println("Configuração Atual de Grafo:");
    	System.out.println("- " + selectedGraphConfiguration);

    	
    	Driver.graph = new Graph<String>(isGraphDirected);
    	Driver.connected = new ArrayList<Edge<String>>();

	}
}

	