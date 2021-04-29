import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class EdgeWeighted implements Comparable<EdgeWeighted>
{
	NodeWeighted dest;
	NodeWeighted src;
	double weight;

	EdgeWeighted(NodeWeighted s, NodeWeighted d, double w) 
	{
		src = s;
		weight = w;
		dest = d;
	}
	public String toString()
	{
		return String.format("(%s -> %s, %f)", src.name, dest.name, weight);
	}

	public int compareTo(EdgeWeighted otherEdge)
	{
		if (this.weight > otherEdge.weight)
		{
			return 1;
		}
		else return -1;
	}
	public static class NodeWeighted 
	{
		String name;
		int n;
		private boolean isVisited;
		LinkedList<EdgeWeighted> edgeList;

		NodeWeighted(int n, String name) 
		{
			this.n = n;
			this.name = name;
			isVisited = false;
			edgeList = new LinkedList<>();
		}

		boolean isVisited() 
		{
			return isVisited;
		}

		void visit()
		{
			isVisited = true;
		}

		void unvisit()
		{
			isVisited = false;
		}
	}
	public static class GraphWeighted 
	{
		private Set<NodeWeighted> nodeSet;
		private boolean isDirected;

		GraphWeighted(boolean directed)
		{
			this.isDirected = directed;
			nodeSet = new HashSet<>();
		}
		// called for any node that has no edges
		public void addNode(NodeWeighted... n) 
		{
			nodeSet.addAll(Arrays.asList(n));
		}

		public void addEdge(NodeWeighted src, NodeWeighted dest, double weight) 
		{

			nodeSet.add(src);
			nodeSet.add(dest);

			// addEdgeHelper to make sure there are no duplicate edges
			addEdgeHelper(src, dest, weight);

			if (!isDirected && src != dest)
			{     
				addEdgeHelper(dest, src, weight);
			}
		}

		private void addEdgeHelper(NodeWeighted j, NodeWeighted k, double weight)
		{
			// Go through all the edges and see whether that edge has already been added
			for (EdgeWeighted edge : j.edgeList) 
			{
				if (edge.src == j && edge.dest == k) 
				{
					// Update the value 
					edge.weight = weight;
					return;
				}
			}
			// If it hasn't been added already add the edge
			j.edgeList.add(new EdgeWeighted(j, k, weight));
		}
		public void printEdges() {
			for (NodeWeighted node : nodeSet) {
				LinkedList<EdgeWeighted> edges = node.edgeList;

				if (edges.isEmpty()) {
					System.out.println("Node " + node.name + " has no edges.");
					continue;
				}
				System.out.print("Node " + node.name + " has edges to: ");

				for (EdgeWeighted edge : edges) {
					System.out.print(edge.dest.name + "(" + edge.weight + ") ");
				}
				System.out.println();
			}

		}
	

	public void DijkstraShortestPath(NodeWeighted start, NodeWeighted end) 
	{

		HashMap<NodeWeighted, NodeWeighted> changedAt = new HashMap<>();
		changedAt.put(start, null);

		// Keep track of the shortest path so far for every node
		HashMap<NodeWeighted, Double> shortestPathMap = new HashMap<>();

		// Setting each node shortest path weight to positive infinity at start and starting node shortest path weight to 0
		for (NodeWeighted node : nodeSet) 
		{
			if (node == start)
				shortestPathMap.put(start, 0.0);
			else shortestPathMap.put(node, Double.POSITIVE_INFINITY);
		}
		// go through all nodes possible from starting node
		for (EdgeWeighted edge : start.edgeList) {
			shortestPathMap.put(edge.dest, edge.weight);
			changedAt.put(edge.dest, start);
		}
		start.visit();	
		while (true) {
			NodeWeighted currentNode = closestReachableUnvisited(shortestPathMap);
			
			if (currentNode == null)
			{
				System.out.println("There isn't a path between " + start.name + " and " + end.name);
				return;
			}

			
			if (currentNode == end)
			{
				System.out.println("The path with the lowest cost from Stop "
						+ start.name + " to Stop " + end.name + " is:");

				NodeWeighted child = end;
				String path = end.name;
				while (true) {
					NodeWeighted parent = changedAt.get(child);
					if (parent == null)
					{
						break;
					}
					path = parent.name + " " + path;
					child = parent;
				}
				System.out.println(path);
				System.out.println("The path costs: " + shortestPathMap.get(end));
				return;
			}
			currentNode.visit();

			for (EdgeWeighted edge : currentNode.edgeList)
			{
				if (edge.dest.isVisited())
					continue;

				if (shortestPathMap.get(currentNode)
						+ edge.weight
						< shortestPathMap.get(edge.dest))
				{
					shortestPathMap.put(edge.dest,
							shortestPathMap.get(currentNode) + edge.weight);
					changedAt.put(edge.dest, currentNode);
				}
			}
		}
	}

	private NodeWeighted closestReachableUnvisited(HashMap<NodeWeighted, Double> shrstPMap) 
	{

		double shrstDist = Double.POSITIVE_INFINITY;
		NodeWeighted closeReachableNode = null;
		for (NodeWeighted node : nodeSet) 
		{
			if (node.isVisited())
				continue;

			double currDist = shrstPMap.get(node);
			if (currDist == Double.POSITIVE_INFINITY)
				continue;

			if (currDist < shrstDist) 
			{
				shrstDist = currDist;
				closeReachableNode = node;
			}
		}
		return closeReachableNode;
	}
}

public static class Graph 
{
	static String fileName = "";
	static int nodes = 0;
	static int edges = 0;
	static double cost = 0;
	static ArrayList<String> stopID = new ArrayList<String>();
	static ArrayList<String> stopID1 = new ArrayList<String>();
	static ArrayList<String> tripID = new ArrayList<String>();
	static ArrayList<Integer> StopIDint = new ArrayList<>();
	static ArrayList<String> tripData = new ArrayList<>();
	static ArrayList<String> arrivalTime = new ArrayList<>();
	static ArrayList<String> arrayOfTrips = new ArrayList<>();
	static ArrayList<String> transferData = new ArrayList<>();
	static ArrayList<String> minTransferTime = new ArrayList<>();
	static ArrayList<Integer> minTransferTimeint = new ArrayList<>();
	static ArrayList<String> transferType = new ArrayList<>();
	static ArrayList<String> fromStop = new ArrayList<>();
	static ArrayList<String> toStop = new ArrayList<>();
	static ArrayList<Double> costList = new ArrayList<>();

	public static void parseFile() throws IOException
	{
		//read through file to make ArrayList of stops
		String file = "stops.txt"; 
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		try {
			@SuppressWarnings("unused")
			String headerLine = br.readLine();
			while((line=br.readLine())!=null)
			{
				String[] columns = line.split(",");
				stopID.add(columns[0]);
			}

		} catch (Exception e) 
		{
			br.close();
		}
		{
			//read through file to make ArrayList of tripData
			String file2 = "stop_times.txt"; 
			@SuppressWarnings("resource")
			BufferedReader br2 = new BufferedReader(new FileReader(file2));
			String line2;
			try {
				@SuppressWarnings("unused")
				String headerLine = br2.readLine();
				while( (line2 = br2.readLine()) != null)
				{
					tripData.add(line2);
				}

			} catch (Exception e)
{
				br.close();

			}
			//convert stopID to int array

			String[] g = stopID.toArray(new String[0]);
			for (int i = 0; i < stopID.size(); i++) 
			{
				int j = Integer.parseInt(g[i]);
				StopIDint.add(j);
			}

			//read through file to make ArrayList of tripID stopID and arrival times
			file = "stop_times.txt"; 
			BufferedReader br1 = new BufferedReader(new FileReader(file));
			String line1;
			try {
				@SuppressWarnings("unused")
				String headerLine = br1.readLine();
				while((line1=br1.readLine())!=null)
				{
					String[] columns = line1.split(",");
					tripID.add(columns[0]);
					stopID1.add(columns[3]);
					arrivalTime.add(columns[2]);
				}

			} catch (Exception e) {
				br1.close();
			}
		}

	}
	//parses transfers.txt and handles cost calculation
	public static void readTransfers() throws IOException
	{
		String file = "transfers.txt"; 
		BufferedReader br3 = new BufferedReader(new FileReader(file));
		String line3;
		try {
			@SuppressWarnings("unused")
			String headerLine = br3.readLine();
			while( (line3 = br3.readLine()) != null)
			{	String[] columns = line3.split(",");
			fromStop.add(columns[0]);
			toStop.add(columns[1]);
			transferType.add(columns[2]);
			transferData.add(line3);
			}

		} catch (Exception e)
		{
			br3.close();
		}

		for (int i = 0; i < transferData.size(); i++) 
		{
			String str = transferData.get(i);
			String[] columns = str.split(",", -1); 
			minTransferTime.add(columns[3]);
		}
		System.out.println(minTransferTime.get(1));
		for (int i = 0; i < transferData.size(); i++) 
		{
			if (transferType.get(i).equals("2"))
			{
				double temp =	Integer.parseInt( minTransferTime.get(i));
				costList.add(temp/100);

			} else costList.add((double)0);

		}


	}
	// print time for Q3
	public static void printTime(String s0) throws IOException
	{
		parseFile();	
		readTransfers();
		if (arrivalTime.contains(s0))
		{
			System.out.println("\n here are all of the trips which have the inputted arrival time:");
			System.out.println("\nTripID: Arrival Time: DepartureTime: StopID: stop_sequence: stop_headsign: pickup_type: drop_off_type: shape_dist_traveled: ");
			for (int i = 0; i < arrivalTime.size(); i++)
			{

				String s1 = arrivalTime.get(i);
				if (s1.equals(s0)) {
					arrayOfTrips.add((tripData.get(i)));
					System.out.println(tripData.get(i));
				}

			} 
		}else System.out.println("error the input is in the incorrect format or there are no trips at the inputted time");
	}
	//set up for hash map array list graph and creates nodes and edges
	public static void setup(String busStop1, String busStop2) throws IOException {

		GraphWeighted graphWeighted = new GraphWeighted(true);
		parseFile();	
		readTransfers();
		if (stopID.contains(busStop1) && stopID.contains(busStop2))
		{
			List<NodeWeighted> nodeList = new ArrayList<>(stopID.size());
			HashMap<String, Integer> hashMap = new HashMap<>();
			for(int i=0; i<stopID.size(); i++) {
				NodeWeighted nodeName = new NodeWeighted(i,stopID.get(i));
				hashMap.put(stopID.get(i),i);
				nodeList.add(nodeName);
			}	
			for (int i = 1; i < stopID1.size(); i++) {
				if (tripID.get(i).equals(tripID.get(i-1))){
					graphWeighted.addEdge(nodeList.get(hashMap.get(stopID1.get(i-1))),
							nodeList.get(hashMap.get(stopID1.get(i))), 1);
				}
			}
			for (int i = 0; i < transferData.size(); i++) {
				graphWeighted.addEdge(nodeList.get(hashMap.get(fromStop.get(i))),
						nodeList.get(hashMap.get(toStop.get(i))), costList.get(i));	
			}
			graphWeighted.DijkstraShortestPath(nodeList.get(hashMap.get(busStop1)),nodeList.get(hashMap.get(busStop2)));
		}
		else {
			System.out.println("error bus stop does not exist");
		}
	}
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		boolean isValid = true;
		System.out.println("Welcome to our Algorithms and Data Structures Project.");
		do {

			System.out.println("\n please input \"1\" for dijkstra algorithm or \"2\" for search by time or enther Quit at any time");
			String checker = input.next();
			if (checker.equalsIgnoreCase("Quit"))
			{
				System.out.println("program terminated");
				isValid = false;
			}
			if (checker.equals("1")) {
				System.out.println("\nPlease input two bus stops to find shortest path.");
				String busStop1 = input.next();
				if (busStop1.equalsIgnoreCase("Quit"))
				{
					System.out.println("program terminated");
					isValid = false;
				}
				String busStop2 = input.next();
				if (busStop2.equalsIgnoreCase("Quit"))
				{
					System.out.println("program terminated");
					isValid = false;
				}
				System.out.println("One Moment Please.");
				setup(busStop1,busStop2);
			}
			else if (checker.equals("2")){
				System.out.println("\n please input a time to search for in the format hh:mm:ss");
				String tmp = input.next();
				if (tmp.equalsIgnoreCase("Quit"))
				{
					System.out.println("program terminated");
					isValid = false;
				}
				String time = " " + tmp;
				printTime(time);
			}
			else {
				System.out.println("error incorrect input");
			}
		}
		while(isValid);
	}
}

}