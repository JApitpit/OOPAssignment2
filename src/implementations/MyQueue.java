package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

public class MyQueue<E> implements QueueADT<E> {
    private static final long serialVersionUID = 1L;
    private MyDLL<E> queue;

    public MyQueue() {
        queue = new MyDLL<>();
    }

    @Override
    public void enqueue(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot enqueue null element");
        }
        queue.add(toAdd);
    }

    @Override
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty");
        }
        return queue.remove(0);
    }

    @Override
    public E peek() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty");
        }
        return queue.get(0);
    }

    @Override
    public void dequeueAll() {
        queue.clear();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        return queue.contains(toFind);
    }

    @Override
    public int search(E toFind) {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null element");
        }

        int position = 1;
        Iterator<E> iterator = queue.iterator();
        while (iterator.hasNext()) {
            E element = iterator.next();
            if (element.equals(toFind)) {
                return position;
            }
            position++;
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }

    @Override
    public boolean equals(QueueADT<E> that) {
        if (that == null || this.size() != that.size()) {
            return false;
        }

        Iterator<E> thisIterator = this.iterator();
        Iterator<E> thatIterator = that.iterator();

        while (thisIterator.hasNext()) {
            E thisElement = thisIterator.next();
            E thatElement = thatIterator.next();
            if (!thisElement.equals(thatElement)) {
                return false;
            }
        }
        return true;
    }

    public Object[] toArray() {
        return queue.toArray();
    }

    public E[] toArray(E[] holder) throws NullPointerException {
        return queue.toArray(holder);
    }

    public boolean isFull() {
        return false;
    }

    public int size() {
        return queue.size();
    }
}