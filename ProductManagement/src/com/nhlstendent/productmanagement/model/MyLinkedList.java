package com.nhlstendent.productmanagement.model;

public class MyLinkedList<T>
{
    private Node<T> head;
    private int size;
    public MyLinkedList()
    {
        this.head = null;
        this.size = 0;
    }

    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public void add(T element)
    {
        if (head == null)
        {
            head = new Node<>(element);
        }
        else
        {
            Node<T> current = head;
            while (current.next != null)
            {
                current = current.next;
            }
            current.next = new Node<>(element);
        }
        size++;
    }

    public T get(int index)
    {
        checkIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++)
        {
            current = current.next;
        }
        return current.data;
    }

    public void set(int index, T element)
    {
        checkIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++)
        {
            current = current.next;
        }
        current.data = element;
    }

    // Remove by index (existing method)
    public void remove(int index)
    {
        checkIndex(index);
        if (index == 0)
        {
            head = head.next;
        }
        else
        {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++)
            {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }

    // New method: Remove by value (item)
    public boolean remove(T item)
    {
        if (head == null)
        {
            return false; // List is empty, nothing to remove
        }

        // Special case: item is at the head
        if (head.data.equals(item))
        {
            head = head.next;  // Remove the head
            size--;
            return true;
        }

        // Traverse the list to find the item
        Node<T> current = head;
        while (current.next != null)
        {
            if (current.next.data.equals(item))
            {
                current.next = current.next.next;  // Bypass the node to remove it
                size--;
                return true;
            }
            current = current.next;
        }
        return false;  // Item not found
    }

    private void checkIndex(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    public void clear()
    {
        head = null;
        size = 0;
    }

    private static class Node<T>
    {
        T data;
        Node<T> next;

        Node(T data)
        {
            this.data = data;
            this.next = null;
        }
    }
}
