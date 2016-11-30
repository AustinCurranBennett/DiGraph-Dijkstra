package A6_Dijkstra;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class DiGraph implements DiGraph_Interface {
	Map<String, Node> nodeHash = new HashMap <String,Node>();
	Map<Long,Node> idHash = new HashMap <Long,Node>();
	Map<String,LinkedList<Edge>> vertices = new HashMap<String,LinkedList<Edge>>();
	Map<Long,Edge>edges = new HashMap<Long,Edge>();
	int numEdges, numNodes;
	@Override
	public boolean addNode(long idNum, String label) {
		if(nodeHash.containsKey(label) || idNum < 0 || idHash.containsKey(idNum) || label == null){
			return false;
		}else{
			Node newNode = new Node(idNum, label);
			nodeHash.put(label, newNode);
			idHash.put(idNum, newNode);
			vertices.put(label, new LinkedList<Edge>());
			numNodes++;
			return true;

		}
	}

	@Override
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		if(idNum < 0 || !nodeHash.containsKey(sLabel) || !nodeHash.containsKey(dLabel) || edges.containsKey(idNum)){
			return false;
		}else if(vertices.containsKey(sLabel)){
			for(int i=0; i < vertices.get(sLabel).size(); i++){
				if(vertices.get(sLabel).get(i).getDLabel().equals(dLabel) || vertices.get(sLabel).get(i).getId() == idNum){
					return false;
				}
			}
			Edge e = new Edge(idNum, sLabel, dLabel,weight,eLabel);
			vertices.get(sLabel).add(e);
			nodeHash.get(dLabel).inDegree++;
			edges.put(idNum, e);
			numEdges++;
			return true;
		}else{
			Edge e = new Edge(idNum, sLabel, dLabel,weight,eLabel);
			vertices.get(sLabel).add(e);
			nodeHash.get(dLabel).inDegree++;
			edges.put(idNum, e);
			numEdges++;
			return true;
		}
	}

	@Override
	public boolean delNode(String label) {
		if(!nodeHash.containsKey(label)){
			return false;
		}
		int len = vertices.get(label).size();
		for(int i=0; i < len; i++){
			String temp = vertices.get(label).get(i).getDLabel();
			if(nodeHash.containsKey(temp)){
				nodeHash.get(temp).inDegree--;
			}
		}

		if(nodeHash.get(label).inDegree != 0){
			findInEdge(label);
		}
		long temp = nodeHash.get(label).getId();
		numEdges = numEdges - len;
		vertices.get(label).clear();
		vertices.remove(label);
		nodeHash.remove(label);
		idHash.remove(temp);
		numNodes--;
		return true;
	}
	public boolean findInEdge(String label){
		for(Entry<String, LinkedList<Edge>> s: vertices.entrySet()){
			for(int i=0; i < vertices.get(s.getKey()).size();i++){
				if(vertices.get(s.getKey()).get(i).getDLabel().equals(label)){
					delEdge(s.getKey(),label);
				}
			}
		}

		return true;
	}

	@Override
	public boolean delEdge(String sLabel, String dLabel) {
		boolean tOrF = false;
		if(!vertices.containsKey(sLabel) || !vertices.containsKey(dLabel)){
			return false;
		}

		for(int i=0; i < vertices.get(sLabel).size(); i++){
			if(vertices.get(sLabel).get(i).getDLabel().equals(dLabel)){
				vertices.get(sLabel).remove(i);
				tOrF = true;
			}
		}
		if(tOrF == false){
			return false;
		}
		for(Map.Entry<Long, Edge> edge : edges.entrySet()){
			if(edge.getValue().getSLabel().equals(sLabel) && edge.getValue().getDLabel().equals(dLabel)){
				edges.remove(edge.getKey());
				break;
			}
		}
		nodeHash.get(dLabel).inDegree--;
		numEdges--;
		return true;
	}

	@Override
	public long numNodes() {
		// TODO Auto-generated method stub
		return numNodes;
	}

	@Override
	public long numEdges() {
		// TODO Auto-generated method stub
		return numEdges;
	}

	@Override
	public String[] topoSort() {
		LinkedList<Node> q = new LinkedList<Node>();
		int count = 0;
		String[] topoS = new String[numNodes];
		DiGraph g = new DiGraph();
		Node s;
		for(Map.Entry<String, Node> entry : nodeHash.entrySet()){
			g.nodeHash.put(entry.getKey(), entry.getValue());
		}
		for(Map.Entry<String, Node> entry : g.nodeHash.entrySet()){
			if(entry.getValue().getInDegree() == 0){
				q.add(entry.getValue());
			}
		}
		while(q.size() > 0){
			s = q.remove();
			String temp = s.label;
			for(int i=0; i < vertices.get(temp).size(); i++){
				g.nodeHash.get(vertices.get(temp).get(i).getDLabel()).inDegree--;
				if(g.nodeHash.get(vertices.get(temp).get(i).getDLabel()).getInDegree() == 0){
					q.add(g.nodeHash.get(vertices.get(temp).get(i).getDLabel()));
				}
			}
			topoS[count] = s.getLabel();
			count++;
		}

		if(topoS[topoS.length-1] == null){
			return null;
		}
		return topoS;
	}

	@Override
	public ShortestPathInfo[] shortestPath(String label) {
		Map<String,Integer> distance = new HashMap<String,Integer>();
		MinBinHeap pq = new MinBinHeap();
		Map<String,Integer> known = new HashMap<String,Integer>();
		EntryPair n;
		int d,count=0;
		for(Map.Entry<String, Node> node: nodeHash.entrySet()){
			distance.put(node.getValue().getLabel(), (int)Double.POSITIVE_INFINITY);  //set all distances to infinity because all nodes are unknown at this point
		}
		distance.put(label,0);  //give the start node a distance of zero
		pq.insert(new EntryPair(label,0));  //add it to the binary heap
		
		while(pq.size() > 0){
			n = pq.getMin();
			d = pq.getMin().getPriority();
			pq.delMin();
			if(!known.containsKey(n.getValue())){  //if the node is not known add it to the list of known nodes
				known.put(n.getValue(), n.getPriority());
				for(int i=0; i < vertices.get(n.getValue()).size(); i++){
					if(distance.get(vertices.get(n.getValue()).get(i).getDLabel()) > (d + vertices.get(n.getValue()).get(i).getWeight())){  //if the distance from the known node to its adjacent edge is less than the distance to that node already in the distance hashmap, replace it
						distance.put(vertices.get(n.getValue()).get(i).getDLabel(),(int) (d + vertices.get(n.getValue()).get(i).getWeight()));
						pq.insert(new EntryPair(vertices.get(n.getValue()).get(i).getDLabel(),(int) (d + vertices.get(n.getValue()).get(i).getWeight()))); //add the new node to the binary heap
					}
				}
			}
		}
		ShortestPathInfo[] shortest = new ShortestPathInfo[numNodes];
		for(Map.Entry<String, Node> node : nodeHash.entrySet()){
			if(distance.containsKey(node.getValue().getLabel())){
				ShortestPathInfo temp = new ShortestPathInfo(node.getValue().getLabel(),distance.get(node.getValue().getLabel()));
				if(temp.getTotalWeight() > 1000000){
					ShortestPathInfo temp2 = new ShortestPathInfo(node.getValue().getLabel(),-1);
					shortest[count] = temp2;
				}else{
					shortest[count] = temp;
				}
				count++;
			}
		}
		
		return shortest;
	}

}
