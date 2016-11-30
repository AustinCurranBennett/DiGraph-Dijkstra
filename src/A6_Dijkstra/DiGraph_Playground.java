package A6_Dijkstra;

public class DiGraph_Playground {
	public static void main (String[] args) {
		
		
		
		dijkstraTest();
	}
	
	
	public static void dijkstraTest(){
    	DiGraph d = new DiGraph();
    	long startTime = System.currentTimeMillis();
    	
    	d.addNode(1, "A");
    	d.addNode(2, "B");
    	d.addNode(3, "C");
    	d.addNode(4, "D");
    	d.addNode(5, "E");
    	d.addNode(6,"F");
    	d.addNode(7, "G");
    	d.addNode(8, "H");
    	d.addNode(9,"I");
    	d.addNode(10, "J");
    	d.addEdge(1, "A", "B", 1, null);
    	d.addEdge(2, "A", "C", 4, null);
    	d.addEdge(3, "A", "I", 15, null);
    	d.addEdge(4, "B", "C", 2, null);
    	d.addEdge(5, "B", "E", 6, null);
    	d.addEdge(6, "B", "D", 2, null);
    	d.addEdge(7, "C", "D", 1, null);
    	d.addEdge(8, "C", "F", 5, null);
    	d.addEdge(9, "C", "E", 1, null);
    	d.addEdge(10, "D", "G", 1, null);
    	d.addEdge(11, "D", "F", 3, null);
    	d.addEdge(12, "E", "G", 3, null);
    	d.addEdge(13, "F", "H", 4, null);
    	d.addEdge(14, "G", "H", 1, null);

    	//Tests build time
    	long endTime = System.currentTimeMillis();
    	long totalTime = endTime - startTime;
    	System.out.println("numEdges: "+d.numEdges());
	    System.out.println("numNodes: "+d.numNodes());
	    System.out.println("Build took: "+ totalTime + " ms or " + (totalTime/1000) + " seconds.");
	    System.out.println();
	    //Test dijkstra's time
	    startTime = System.currentTimeMillis();
	    ShortestPathInfo finish[] = new ShortestPathInfo[(int) d.numNodes()];
	    finish = d.shortestPath("A");
	    endTime = System.currentTimeMillis();
    	totalTime = endTime - startTime;
    	System.out.println("Algorithm took: "+ totalTime + " ms or " + (totalTime/1000) + " seconds.");
    	for(int i = 0; i < finish.length; i++){
    		System.out.println(finish[i].getDest() + " : " + finish[i].getTotalWeight());
    	}
    }
}
