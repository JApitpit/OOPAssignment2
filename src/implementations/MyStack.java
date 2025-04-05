package implementations;

import exceptions.EmptyStackException;
import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.StackADT;

public class MyStack<E> implements StackADT<E> {
    private static final long serialVersionUID = 1L;
    private MyArrayList<E> stack;

    public MyStack() {
        stack = new MyArrayList<>();
    }

    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot push null element to stack");
        }
        stack.add(toAdd);
    }

    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("Cannot pop from empty stack");
        }
        return stack.remove(stack.size() - 1);
    }

    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("Cannot peek empty stack");
        }
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
        Object[] array = new Object[size()];
        for (int i = 0; i < size(); i++) {
            array[i] = stack.get(size() - 1 - i);
        }
        return array;
    }

    @SuppressWarnings("unchecked")
	@Override
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null) {
            throw new NullPointerException("Holder array cannot be null");
        }
        
        // Create a new array if the holder is too small
        if (holder.length < size()) {
            holder = (E[]) java.lang.reflect.Array.newInstance(
                holder.getClass().getComponentType(), size());
        }
        
        for (int i = 0; i < size(); i++) {
            holder[i] = stack.get(size() - 1 - i);
        }
        
        if (holder.length > size()) {
            holder[size()] = null;
        }
        
        return holder;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        return stack.contains(toFind);
    }

    @Override
    public int search(E toFind) {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null element");
        }
        
        int position = 1;
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            if (toFind.equals(iterator.next())) {
                return position;
            }
            position++;
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<E> {
        private int currentIndex = size() - 1;

        @Override
        public boolean hasNext() {
            return currentIndex >= 0;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new exceptions.NoSuchElementException();
            }
            return stack.get(currentIndex--);
        }
    }

    @Override
    public boolean equals(StackADT<E> that) {
        if (that == null || this.size() != that.size()) {
            return false;
        }

        Iterator<E> thisIterator = this.iterator();
        Iterator<E> thatIterator = that.iterator();

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
}