package implementations;

import exceptions.EmptyStackException;
import utilities.StackADT;

public class MyStack<E> implements StackADT<E> {
    private static final long serialVersionUID = 1L;
    private MyArrayList<E> stack;

    public MyStack() {
        stack = new MyArrayList<>();
    }

    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("Cannot push null");
        stack.add(toAdd);
    }

    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();
        return stack.remove(stack.size() - 1);
    }

    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();
        return stack.get(stack.size() - 1);
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Object[] toArray() {
        return stack.toArray();
    }

    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        return stack.toArray(holder);
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        return stack.contains(toFind);
    }

    @Override
    public int search(E toFind) {
        // Manual iteration (no for-each loop)
        utilities.Iterator<E> iterator = stack.iterator();
        int distance = stack.size();
        
        while (iterator.hasNext()) {
            E element = iterator.next();
            if (element.equals(toFind)) {
                return distance;
            }
            distance--;
        }
        return -1;
    }

    @Override
    public utilities.Iterator<E> iterator() {
        return stack.iterator();
    }

    @Override
    public boolean equals(StackADT<E> that) {
        if (that == null || this.size() != that.size()) return false;
        
        utilities.Iterator<E> thisIterator = this.iterator();
        utilities.Iterator<E> thatIterator = that.iterator();
        
        while (thisIterator.hasNext()) {
            if (!thisIterator.next().equals(thatIterator.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean stackOverflow() {
        return false;
    }

    @Override
    public E pop1() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();
        return stack.remove(stack.size() - 1);
    }

    @Override
    public E peek1() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();
        return stack.get(stack.size() - 1);
    }

}