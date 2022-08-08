package DataStructuresSecondTheme.implementations;

import DataStructuresSecondTheme.interfaces.AbstractStack;

import java.util.Iterator;

public class Stack<E> implements AbstractStack<E> {

    private Node<E> top;
    private int size;

    private static class Node<E> {
        private E value;
        private Node<E> next;

        Node(E element) {
            this.value = element;
        }
    }

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    @Override
    public void push(E element) {
        Node<E> insert = new Node<>(element);
        insert.next = this.top;
        this.top = insert;
        this.size++;

    }

    @Override
    public E pop() {
        ensureNotEmpty();

        Node<E> tmp = this.top;
        this.top = tmp.next;
        this.size--;

        return tmp.value;
    }

    @Override
    public E peek() {
        ensureNotEmpty();
        return this.top.value;

    }

    private void ensureNotEmpty() {
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = top;

            @Override
            public boolean hasNext() {
                return this.current.next != null;
            }

            @Override
            public E next() {
                E value = this.current.value;
                this.current = this.current.next;
                return value;
            }
        };
    }
}
