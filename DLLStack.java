import java.util.LinkedList;

public class DLLStack<T> implements Stack<T> {
	LinkedList<T> stack;

	public DLLStack() {
		stack = new LinkedList<T>();
	}

	@Override
	public int size() {
		return stack.size();
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public void push(T v) {
		stack.addFirst(v);
	}

	@Override
	public T pop() {
		return stack.removeFirst();
	}

	@Override
	public T top() {
		return stack.get(0);
	}

}