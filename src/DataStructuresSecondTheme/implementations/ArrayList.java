package DataStructuresSecondTheme.implementations;

import DataStructuresSecondTheme.interfaces.List;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayList<E> implements List<E> {

    private Object[] elements;
    private static final int INITIAL_SIZE = 4;
    private int size = 0;
    private int capacity = 4;


    public ArrayList() {
        this.elements = new Object[INITIAL_SIZE];
        this.size = 0;
    }

    @Override
    public boolean add(E element) {
        if (this.size == capacity) {
            grow();
        }
        this.elements[this.size++] = element;
        return true;
    }

    private void grow() {
        this.capacity *= 2;
        Object[] tmp = new Object[this.capacity];
        for (int i = 0; i < this.elements.length; i++) {
            tmp[i] = this.elements[i];
        }
        this.elements = tmp;
    }

    @Override
    public boolean add(int index, E element) {
        if (!validIndex(index)) {
            return false;
        }
        shiftRight(index);
        this.elements[index] = element;
        this.size++;

        return true;
    }

    private boolean validIndex(int index) {
        return index >= 0 && index < this.size;
    }

    private void shiftRight(int index) {
        for (int i = this.size - 1; i >= index; i--) {
            this.elements[i + 1] = elements[i];
        }
    }

    @Override
    public E get(int index) {
        ensureIndex(index);
        return (E) this.elements[index];
    }

    private void ensureIndex(int index) {
        if (!validIndex(index)) {
            throw new IndexOutOfBoundsException("Cannot get this index!");
        }
    }

    @Override
    public E set(int index, E element) {
        ensureIndex(index);

        Object exist = this.elements[index];
        this.elements[index] = element;

        return (E) exist;
    }

    @Override
    public E remove(int index) {
        ensureIndex(index);

        Object exist = this.elements[index];
        shiftLeft(index);
        this.size--;

        shrinkIfNeeded();
        return (E) exist;
    }

    private void shrinkIfNeeded() {
        if (this.size > this.capacity / 3) {
            return;
        }
        this.capacity /= 2;
        this.elements = Arrays.copyOf(this.elements, this.capacity);
    }

    private void shiftLeft(int index) {
        for (int i = index; i < this.size - 1; i++) {
            elements[i] = elements[i + 1];
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(E element) {
        return this.indexOf(element) != -1;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                return get(index++);
            }
        };
    }
}
