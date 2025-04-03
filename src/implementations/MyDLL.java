package implementations;

import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * Implementation of a doubly linked list that implements the ListADT interface.
 *
 * @param <E> The type of elements stored in this list
 */
public class MyDLL<E> implements ListADT<E> {
    private MyDLLNode<E> head;
    private MyDLLNode<E> tail;
    private int size;

    /**
     * Constructs an empty doubly linked list.
     */
    public MyDLL() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }
        
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);

        if (index == 0) {
            // Add to front
            newNode.setNext(head);
            if (head != null) {
                head.setPrevious(newNode);
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
        } else if (index == size) {
            // Add to end
            newNode.setPrevious(tail);
            if (tail != null) {
                tail.setNext(newNode);
            }
            tail = newNode;
            if (head == null) {
                head = newNode;
            }
        } else {
            // Add in middle
            MyDLLNode<E> current = getNode(index);
            MyDLLNode<E> prev = current.getPrevious();

            newNode.setNext(current);
            newNode.setPrevious(prev);
            prev.setNext(newNode);
            current.setPrevious(newNode);
        }

        size++;
        return true;
    }

    @Override
    public boolean add(E toAdd) throws NullPointerException {
        return add(size, toAdd);
    }

    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null list");
        }

        Iterator<? extends E> iterator = toAdd.iterator();
        while (iterator.hasNext()) {
            add(iterator.next());
        }

        return true;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        return getNode(index).getElement();
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        MyDLLNode<E> toRemove = getNode(index);
        E element = toRemove.getElement();

        if (size == 1) {
            // Only one element in list
            head = null;
            tail = null;
        } else if (index == 0) {
            // Remove first element
            head = head.getNext();
            head.setPrevious(null);
        } else if (index == size - 1) {
            // Remove last element
            tail = tail.getPrevious();
            tail.setNext(null);
        } else {
            // Remove middle element
            MyDLLNode<E> prev = toRemove.getPrevious();
            MyDLLNode<E> next = toRemove.getNext();
            prev.setNext(next);
            next.setPrevious(prev);
        }

        size--;
        return element;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Cannot remove null element");
        }

        MyDLLNode<E> current = head;
        int index = 0;
        while (current != null) {
            if (toRemove.equals(current.getElement())) {
                return remove(index);
            }
            current = current.getNext();
            index++;
        }

        return null;
    }

    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) {
            throw new NullPointerException("Cannot set null element");
        }

        MyDLLNode<E> node = getNode(index);
        E oldElement = node.getElement();
        node.setElement(toChange);
        return oldElement;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null element");
        }

        MyDLLNode<E> current = head;
        while (current != null) {
            if (toFind.equals(current.getElement())) {
                return true;
            }
            current = current.getNext();
        }

        return false;
    }

    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException("Target array cannot be null");
        }

        if (toHold.length < size) {
            toHold = (E[]) java.lang.reflect.Array.newInstance(
                toHold.getClass().getComponentType(), size);
        }

        MyDLLNode<E> current = head;
        for (int i = 0; i < size; i++) {
            toHold[i] = current.getElement();
            current = current.getNext();
        }

        if (toHold.length > size) {
            toHold[size] = null;
        }

        return toHold;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        MyDLLNode<E> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.getElement();
            current = current.getNext();
        }
        return array;
    }

    @Override
    public Iterator<E> iterator() {
        return new DLLIterator();
    }

    /**
     * Helper method to get the node at a specific index.
     *
     * @param index The index of the node to retrieve
     * @return The node at the specified index
     * @throws IndexOutOfBoundsException If the index is out of range
     */
    private MyDLLNode<E> getNode(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        MyDLLNode<E> current;
        if (index < size / 2) {
            // Search from head
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            // Search from tail
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrevious();
            }
        }

        return current;
    }

    /**
     * Iterator implementation for the doubly linked list.
     */
    private class DLLIterator implements Iterator<E> {
        private MyDLLNode<E> current;
        private int currentIndex;

        public DLLIterator() {
            current = head;
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the list");
            }

            E element = current.getElement();
            current = current.getNext();
            currentIndex++;
            return element;
        }
    }
}