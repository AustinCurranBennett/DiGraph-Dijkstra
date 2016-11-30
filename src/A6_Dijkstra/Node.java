package A6_Dijkstra;

public class Node {
	String label;
	long id;
	long inDegree;
	int topNum;
	public Node(long inId, String inName){
		label = inName;
		id = inId;
		inDegree = 0;
		topNum = 0;
	}
	
	public void setLabel(String input){
		label = input;
	}
	public String getLabel(){
		return label;
	}
	public void setId(long input){
		id = input;
	}
	public long getId(){
		return id;
	}
	public long getInDegree(){
		return inDegree;
	}
}
