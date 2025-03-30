package com.nhlstendent.productmanagement.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyHashSet<T> implements Iterable<T> {
    private static class Node<T> {
        T key;
        Node<T> next;

        Node(T key) {
            this.key = key;
            this.next = null;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private Node<T>[] buckets;
    private int size;

    public MyHashSet() {
        buckets = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    private int hash(T key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public boolean add(T key) {
        int index = hash(key);
        Node<T> current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return false; // Key already exists, do nothing and return false
            }
            current = current.next;
        }
        Node<T> newNode = new Node<>(key);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
        return true; // Element was added successfully
    }


    public boolean contains(T key) {
        int index = hash(key);
        Node<T> current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void remove(T key) {
        int index = hash(key);
        Node<T> current = buckets[index];
        Node<T> prev = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    public boolean addAll(MyHashSet<T> collection) {
        boolean isChanged = false;
        for (T element : collection) {
            if (add(element)) {
                isChanged = true;
            }
        }
        return isChanged;
    }

    // Iterable interface implementation
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int bucketIndex = 0;
            private Node<T> currentNode = null;

            @Override
            public boolean hasNext() {
                if (currentNode != null && currentNode.next != null) {
                    return true;
                }
                while (bucketIndex < buckets.length) {
                    if (buckets[bucketIndex] != null) {
                        return true;
                    }
                    bucketIndex++;
                }
                return false;
            }

            @Override
            public T next() {
                if (currentNode == null || currentNode.next == null) {
                    while (bucketIndex < buckets.length && buckets[bucketIndex] == null) {
                        bucketIndex++;
                    }
                    if (bucketIndex >= buckets.length) {
                        throw new NoSuchElementException();
                    }
                    currentNode = buckets[bucketIndex];
                }
                T result = currentNode.key;
                currentNode = currentNode.next;
                return result;
            }
        };
    }

    // Get the size of the set
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        buckets = new Node[DEFAULT_CAPACITY];
        size = 0;
    }
}
