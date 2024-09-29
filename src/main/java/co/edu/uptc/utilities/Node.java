package co.edu.uptc.utilities;

public class Node<T> {
    protected T data;
    protected Node<T> prev;
    protected Node<T> next;

    public Node(T data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}
