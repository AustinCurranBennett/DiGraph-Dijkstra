package A6_Dijkstra;

public class MinBinHeap implements Heap_Interface {
	private EntryPair[] array; //load this array
	  private int size;
	  private static final int arraySize = 10000; //Everything in the array will initially 
	                                              //be null. This is ok! Just build out 
	                                              //from array[1]

	  public MinBinHeap() {
	    this.array = new EntryPair[arraySize];
	    array[0] = new EntryPair(null, -100000);
	    size=0;
	   //0th will be unused for simplicity 
	//of child/parent computations...
	  }                                  //the book/animation page both do 
	@Override
	public void insert(EntryPair entry) {
		array[++size] = entry;
		int current = size;
		while(array[current].getPriority() < array[parent(current)].getPriority()){
			swap(current,parent(current));
			current = parent(current);
		}
	}

	private int parent(int nodeIndex) {
		return (nodeIndex) / 2;
	}
	@Override
	public void delMin() {
		if(size == 0){
			return;
		}
		array[1] = array[size--];
		percolateDown(1);
		
	}

	@Override
	public EntryPair getMin() {
		if (size == 0){
			return null;
		}
		
		return array[1];
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public void build(EntryPair[] entries) {
		// TODO Auto-generated method stub
		size = entries.length;
		int i = 1;
		
		for(EntryPair entry : entries){
			this.array[i++] = entry;
		
		}
		for(int j=size/2; j >0; j--){
			percolateDown(j);
		}
	
		
	}
	
	public void percolateDown(int h){
		int child;
		EntryPair tmp = array[h];
		for(; h * 2 <= size; h = child){
			child = h * 2 ;
			if(child != size && this.array[child+1].priority < this.array[child].priority){
				child++;
			}if(tmp.priority > this.array[child].priority){
				array[h] = this.array[child];
			}else break;
			
		}
		array[h] = tmp;
	}
	public void swap(int i, int j){
		EntryPair tmp;
		tmp = this.array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
	@Override
	public EntryPair[] getHeap() {
		// TODO Auto-generated method stub
		
		return this.array;
	}

}
