package implementations;

/**
 * Represents a node in a doubly linked list.
 *
 * @param <E> The type of element stored in the node
 */
public class MyDLLNode<E> {
    private E element;
    private MyDLLNode<E> next;
    private MyDLLNode<E> previous;

    /**
     * Constructs a node with the given element.
     *
     * @param element The element to store in this node
     */
    public MyDLLNode(E element) {
        this.element = element;
        this.next = null;
        this.previous = null;
    }

    /**
     * Gets the element stored in this node.
     *
     * @return The element stored in this node
     */
    public E getElement() {
        return element;
    }

    /**
     * Sets the element stored in this node.
     *
     * @param element The new element to store
     */
    public void setElement(E element) {
        this.element = element;
    }

    /**
     * Gets the next node in the list.
     *
     * @return The next node
     */
    public MyDLLNode<E> getNext() {
        return next;
    }

    /**
     * Sets the next node in the list.
     *
     * @param next The new next node
     */
    public void setNext(MyDLLNode<E> next) {
        this.next = next;
    }

    /**
     * Gets the previous node in the list.
     *
     * @return The previous node
     */
    public MyDLLNode<E> getPrevious() {
        return previous;
    }

    /**
     * Sets the previous node in the list.
     *
     * @param previous The new previous node
     */
    public void setPrevious(MyDLLNode<E> previous) {
        this.previous = previous;
    }
}