import java.util.LinkedList;


public class DLLQueue<T>implements Queue<T> {
	LinkedList<T> queue;
	
	public DLLQueue() {
		queue = new LinkedList<T>();
	}
	@Override
	public int size() {
		return queue.size();
	
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public void enqueue(T v) {
		queue.addLast(v);
		
	}

	@Override
	public T dequeue() {
		return queue.removeFirst();
	}

	@Override
	public T first() {
		return queue.getFirst();
	}


}
