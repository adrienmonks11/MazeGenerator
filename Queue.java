
public interface Queue<T> {

	public int size();

	public boolean isEmpty();

	public void enqueue(T v);

	public T dequeue();

	public T first();
	
	public String toString();




}
