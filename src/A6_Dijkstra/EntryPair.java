package A6_Dijkstra;

public class EntryPair implements EntryPair_Interface {
	  public String value;
	  public int priority;

	  public EntryPair(String aValue, int aPriority) {
	    value = aValue;
	    priority = aPriority;
	  }
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return priority;
	}
	
}
