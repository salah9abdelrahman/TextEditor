package textgen;

import java.util.AbstractList;

/**
 * A class that implements a doubly linked list
 *
 * @param <E> The type of the elements stored in the list
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MyLinkedList<E> extends AbstractList<E> {
    LLNode<E> head;
    LLNode<E> tail;
    int size;

    public MyLinkedList() {
        head = new LLNode<>(null);
        tail = new LLNode<>(null);
    }

    /**
     * Get the element at position index
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E get(int index) {

        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException();
        }

        LLNode<E> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.data;

    }
    /**
     * Create a new empty LinkedList
     */

    /**
     * Appends an element to the end of the list
     *
     * @param element The element to add
     */
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException("can't insert null elements");
        }
        LLNode<E> newNode = new LLNode<>(element);
        if (size == 0) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
        return true;
    }

    private void addAtFront(E element) {
        LLNode<E> newNode = new LLNode<>(element);
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
        size++;
    }

    /**
     * Add an element to the list at the specified index
     *
     * @param index   where the element should be added
     * @param element The element to add
     */
    public void add(int index, E element) {
        if (element == null) {
            throw new NullPointerException();
        }
        if ((index < 0) || (index > size)) {
            throw new IndexOutOfBoundsException();
        }
        LLNode<E> newNode = new LLNode<>(element);

        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else if (index == size) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            LLNode<E> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    /**
     * Return the size of the list
     */
    public int size() {
        return this.size;
    }

    /**
     * Remove a node at the specified index and return its data element.
     *
     * @param index The index of the element to remove
     * @return The data element removed
     * @throws IndexOutOfBoundsException If index is outside the bounds of the list
     */
    public E remove(int index) {
        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException();
        }
        LLNode<E> node = head;

        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        E returnedValue = node.data;

        if (index == 0) {
            head = head.next;
        } else if (index == size - 1) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        if(size == 0){
            head = null;
            tail = null;
        }
        return returnedValue;
    }


    /**
     * Set an index position in the list to a new element
     *
     * @param index    The index of the element to change
     * @param newValue The new element
     * @return The element that was replaced
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E set(int index, E newValue) {
        if (newValue == null) {
            throw new NullPointerException();
        }
        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException();
        }
        LLNode<E> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        E oldValue = node.data;
        node.data = newValue;
        return oldValue;
    }
}

class LLNode<E> {
    LLNode<E> prev;
    LLNode<E> next;
    E data;


    public LLNode(E e) {
        this.data = e;
        this.prev = null;
        this.next = null;
    }

}
