package com.nhlstendent.productmanagement.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class MyArrayList<T> implements Iterable<T> {
    private Object[] elements;
    private int size;

    public MyArrayList() {
        this.elements = new Object[10];
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    public void set(int index, T element) {
        checkIndex(index);
        elements[index] = element;
    }

    public void add(T element) {
        if (size == elements.length) {
            resize();
        }
        elements[size++] = element;
    }

    public int size() {
        return size;
    }

    private void resize() {
        int newCapacity = elements.length * 2;
        Object[] newElements = new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    public boolean remove(T item) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(item)) { // Check if the item matches
                // Shift elements to the left to fill the gap
                for (int j = i; j < size - 1; j++) {
                    elements[j] = elements[j + 1];
                }
                elements[--size] = null; // Avoid memory leak by setting last element to null
                return true; // Item was removed
            }
        }
        return false; // Item not found
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) elements[index++]; // ✅ 需要强制转换 (T)
            }
        };
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    public Stream<T> stream() {
        return Stream.of((T[]) elements).limit(size);
    }

    public void sort() {
        Arrays.sort((T[]) elements, 0, size);
    }

    public MyArrayList<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("Invalid range.");
        }

        MyArrayList<T> subList = new MyArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add((T) elements[i]);
        }

        return subList;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }
}
