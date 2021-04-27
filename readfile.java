import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class readfile {

	private static final double INFINITY = Integer.MAX_VALUE / 3;
	static String fileName = "";
	static int nodes = 0;
	static int edges = 0;
	static double cost = 0;
	static ArrayList<String> stopID = new ArrayList<String>();
	static ArrayList<String> transferID = new ArrayList<String>();
	ArrayList<String> graphString = null;
	double[][] graph = null;
	
	public void createEdge()
	{
		for (int i = 0; i < transferID.size(); i++)
		{
			if (transferID.get(i) == transferID.get(i+1))
			{
				//edge.create(); between stopID(i) and StopID(i+1)
			}
		}
	}
	public void createNode()
	{
		
	}


public int timeRequiredforCompetition() {

		double[][] distance = new double[nodes][nodes];

		for (int i = 0; i < nodes; i++) {
			for (int j = 0; j < nodes; j++) {
				distance[i][j] = INFINITY;
			}
		}

		graph = createGraph();
		double maximumDist = dijkstra();
		return (int) (Math.ceil( (maximumDist * 1000)));
	}

	private double[][] createGraph() {
		double[][] graph = new double[nodes][nodes];

		for (int i = 0; i < nodes; i++) {
			for (int j = 0; j < nodes; j++) {
				graph[i][j] = INFINITY;
			}
		}
		for (int i = 0; i < nodes; i++) {
			graph[i][i] = 0;
		}

		for (int i = 0; i < graphString.size(); i++) {
			Scanner lineReader = new Scanner(this.graphString.get(i));
			int nodeID = lineReader.nextInt();
			int connectingNode = lineReader.nextInt();

			cost = getCost();
			graph[nodeID][connectingNode] = cost;
			lineReader.close();
		}

		return graph;
	}
	

	private double getCost() {
		
		return 0;
	}

	private double dijkstra() {
		double currentMaxDist = 0;
		for (int i = 0; i < nodes; i++) {
			int queueSize = 1;
			double[] dist = new double[nodes];
			boolean[] marked = new boolean[nodes];
			boolean[] reached = new boolean[nodes];

			for (int j = 0; j < nodes; j++) {
				dist[j] = INFINITY;
				marked[j] = false;
				reached[j] = false;
			}

			dist[i] = 0;
			reached[i] = true;

			while (queueSize > 0) {
				int indexOfShortest = getShortestPath(dist, marked);
				for (int j = 0; j < nodes; j++) {
					if (((graph[indexOfShortest][j] + dist[indexOfShortest]) < dist[j]) && (!marked[j])) {
						dist[j] = (graph[indexOfShortest][j] + dist[indexOfShortest]);
						queueSize++;
						reached[j] = true;
					}
				}

				marked[indexOfShortest] = true;
				queueSize--;
			}
		}
		return currentMaxDist;
	}
	private int getShortestPath(double[] dist, boolean[] marked) {
		int shortest = 0;
		for (int i = 1; i < dist.length; i++)
			if ((dist[i] < dist[shortest] && !marked[i]) || marked[shortest]) {
				shortest = i;
			}
		return shortest;
	}
	private static void parseFile() throws IOException
	{
		String file = "stop_times.txt"; 
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		try {
			String headerLine = br.readLine();
			while((line=br.readLine())!=null){
				
				String[] columns = line.split(",");
				stopID.add(columns[3]);
				transferID.add(columns[0]);
			}

		} catch (Exception e) {
			br.close();
		}
	}

	public static void main(String[] args) throws IOException
	{
		//System.out.println("Welcome to our Algorithms And Data Structures Project. Please input a bus stop to search or enter two bus stops to find the shortest path, or type exit to quit the program");
		
		
		parseFile();
		


	}
}
