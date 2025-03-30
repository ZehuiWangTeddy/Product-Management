package com.nhlstendent.productmanagement.model;

public class MyHashMap<K, V> {
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private Node<K, V>[] buckets;
    private int size;

    public MyHashMap() {
        buckets = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public void put(K key, V value) {
        int index = hash(key);
        Node<K, V> current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        Node<K, V> current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null; // Key not found
    }

    public void remove(K key) {
        int index = hash(key);
        Node<K, V> current = buckets[index];
        Node<K, V> prev = null;
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

