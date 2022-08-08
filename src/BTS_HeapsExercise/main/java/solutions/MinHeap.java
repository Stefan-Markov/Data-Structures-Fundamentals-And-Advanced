package BTS_HeapsExercise.main.java.solutions;

import interfaces.Decrease;
import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinHeap<E extends Comparable<E> & Decrease<E>> implements Heap<E> {

    private List<E> data;

    public MinHeap() {
        this.data = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public void add(E element) {
        this.data.add(element);
        this.heapifyUp(this.data.size() - 1);
    }

    private void heapifyUp(int index) {

        int parentIndex = getParentIndexFor(index);

        while (index > 0 && isLess(index, parentIndex)) {
            Collections.swap(this.data, index, parentIndex);
            index = parentIndex;
            parentIndex = this.getParentIndexFor(index);
        }
    }

    private boolean isLess(int index, int parentIndex) {
        return this.data.get(index).compareTo(data.get(parentIndex)) < 0;
    }

    private int getParentIndexFor(int index) {
        return (index - 1) / 2;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.data.get(0);
    }

    private void ensureNonEmpty() {
        if (this.data.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        Collections.swap(this.data, 0, this.data.size() - 1);
        E toReturn = this.data.remove(this.data.size() - 1);
        this.heapifyDown();

        return toReturn;
    }

    private void heapifyDown() {
        int index = 0;
        int swapIndex = getLeftChildIndex(index);

        while (swapIndex < this.data.size()) {
            int rightChildIndex = this.getRightChildIndex(index);
            if (rightChildIndex < this.data.size()) {
                swapIndex = this.data.get(swapIndex).compareTo(this.data.get(rightChildIndex)) < 0 ? swapIndex : rightChildIndex;
            }
            if (isLess(index, swapIndex)) {
                break;
            }


            Collections.swap(this.data, index, swapIndex);
            index = swapIndex;
            swapIndex = getLeftChildIndex(index);
        }
    }

    private int getLeftChildIndex(int index) {
        return (index * 2) + 1;
    }

    private int getRightChildIndex(int index) {
        return (index * 2) + 2;
    }

    @Override
    public void decrease(E element) {
        int elementIndex = this.data.indexOf(element);
        E heapElement = this.data.get(elementIndex);
        heapElement.decrease();

        this.heapifyUp(elementIndex);
    }
}
