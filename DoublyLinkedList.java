import java.util.NoSuchElementException;
public class DoublyLinkedList<T> {
	private Node header;
	private Node trailer;
	private int size;

	public DoublyLinkedList() {
		this.header = new Node(null, null, null);
		this.trailer =(new Node(null, null, header));
		this.header.next = getTrailer();
		this.size = 0;
	}

	public int size() {
		return this.size;
	}
	
	public void addFirst(T value) {
		addBetween(value, header, header.next);

	}
	public int search(T value) {
		Node ref = header;
		int index = -1;
		int returnvalue = -1;
		while(ref.next!=null) {
			ref = ref.next;
			index++;
			if(ref.value == value) {
				return index;
			}
		}
		return returnvalue;
	}
	
	public void addLast(T value) {
		addBetween(value, getTrailer().prev, getTrailer());
	}

	private void addBetween(T value, Node first, Node second) {

		if (first ==null || second ==null) {
			throw new IllegalArgumentException("DLL: null values passed to addBetween");
		}
		if(first.next != second || second.prev!= first) {
			throw new IllegalArgumentException("DLL: addBetween: cannot place new node unless passed nodes are adjacent");
		}

		Node newNode = new Node(value, second, first);
		first.next = newNode;
		second.prev = newNode;
		size++;
	}
	public T removeBetween(Node node1, Node node2) {
	
		if (node1 ==  null || node2 == null)
		{
			throw new IllegalArgumentException("Must have valid parameters");
		}
	
		if (header.next ==  getTrailer())
		{
			throw new NoSuchElementException("Cannot delete from an empty list");
		}

		if(node1.next.next != node2)
		{
			throw new IllegalArgumentException("The nodes must have a single node betwen them");
		}
		T valueToReturn = null;
		valueToReturn = node1.next.value;
		node1.next = node2;
		node2.prev = node1;
		return valueToReturn;
	}
	public T removeFirst() {
		return removeBetween(this.header,this.header.next.next);	
	}

	public T removeLast() {
		return removeBetween(this.trailer.prev.prev,this.trailer);
	}

	@Override
	public String toString() {
		String complete = "";
		Node temp = this.header;
		while(temp.next.next!= null) {
			temp = temp.next;
			complete = complete + temp.value + " ";
		}
		return complete;
	}

	public Node getTrailer() {
		return this.trailer;
	}

	public Node getHeader() {
		return this.header;
	}
	//inner class node
	public class Node{
		private T value;
		private Node next;
		private Node prev;

		public Node(T value) {
			this.value = value;
			this.next = null;
			this.prev = null;
		}

		public Node(T value, Node next, Node prev ) {
			this.prev = prev;
			this.value = value;
			this.next = next;
		}
	}
}
