package implementations;

import java.io.Serializable;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

public class MyArrayList<E> implements ListADT<E>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_CAPACITY = 10;
    private E[] data;
    private int size;

    public MyArrayList() {
        data = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            E[] newData = (E[]) new Object[data.length * 2];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) throw new NullPointerException("Element cannot be null");
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index out of bounds");
        ensureCapacity();
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = toAdd;
        size++;
        return true;
    }

    @Override
    public boolean add(E toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("Element cannot be null");
        ensureCapacity();
        data[size++] = toAdd;
        return true;
    }

    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("List cannot be null");
        for (Iterator<? extends E> it = toAdd.iterator(); it.hasNext();) {
            add(it.next());
        }
        return true;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds");
        return data[index];
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds");
        E removed = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null;
        return removed;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) throw new NullPointerException("Element cannot be null");
        for (int i = 0; i < size; i++) {
            if (toRemove.equals(data[i])) {
                return remove(i);
            }
        }
        return null;
    }

    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) throw new NullPointerException("Element cannot be null");
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds");
        E old = data[index];
        data[index] = toChange;
        return old;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) throw new NullPointerException("Element cannot be null");
        for (int i = 0; i < size; i++) {
            if (toFind.equals(data[i])) return true;
        }
        return false;
    }

    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) throw new NullPointerException("Array cannot be null");
        if (toHold.length < size) {
            return (E[]) java.util.Arrays.copyOf(data, size, toHold.getClass());
        }
        System.arraycopy(data, 0, toHold, 0, size);
        if (toHold.length > size) toHold[size] = null;
        return toHold;
    }

    @Override
    public Object[] toArray() {
        return java.util.Arrays.copyOf(data, size);
    }

    @Override
    public Iterator<E> iterator() {
        return new MyArrayListIterator();
    }

    private class MyArrayListIterator implements Iterator<E> {
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException("No more elements");
            return data[cursor++];
        }
    }
}
