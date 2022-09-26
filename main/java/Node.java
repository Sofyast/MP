import java.util.concurrent.atomic.AtomicMarkableReference;

public class Node<T extends Comparable<T>> {
    T item;
    AtomicMarkableReference<Node<T>> next;


    public Node(T item, Node<T> next) {
        this.item = item;
        this.next = new AtomicMarkableReference<>(next, false);
    }
}