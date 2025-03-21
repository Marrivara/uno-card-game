package src.BusinessLayer.DataTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A Bag data structure to hold Card objects with random access functionality.
 * Provides methods for adding, removing, and randomly retrieving cards.
 */
public class Bag<T> {
    private List<T> elements;
    private Random random;

    /**
     * Constructs an empty Bag.
     */
    public Bag() {
        elements = new ArrayList<>();
        random = new Random();
    }

    /**
     * Constructs a Bag with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the bag
     */
    public Bag(int initialCapacity) {
        elements = new ArrayList<>(initialCapacity);
        random = new Random();
    }

    /**
     * Adds an element to the bag.
     *
     * @param element the element to add
     */
    public void add(T element) {
        elements.add(element);
    }

    /**
     * Adds all elements from the given collection to this bag.
     *
     * @param collection the collection of elements to add
     */
    public void addAll(List<T> collection) {
        elements.addAll(collection);
    }

    /**
     * Removes an element from the bag.
     *
     * @param element the element to remove
     * @return true if the element was removed, false otherwise
     */
    public boolean remove(T element) {
        return elements.remove(element);
    }

    /**
     * Removes and returns a random element from the bag.
     *
     * @return a random element from the bag, or null if the bag is empty
     */
    public T getRandomElement() {
        if (isEmpty()) {
            return null;
        }

        int randomIndex = random.nextInt(elements.size());
        return elements.remove(randomIndex);
    }

    /**
     * Retrieves but does not remove a random element from the bag.
     *
     * @return a random element from the bag, or null if the bag is empty
     */
    public T peekRandomElement() {
        if (isEmpty()) {
            return null;
        }

        int randomIndex = random.nextInt(elements.size());
        return elements.get(randomIndex);
    }

    /**
     * Gets the element at the specified index.
     *
     * @param index the index of the element to get
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T get(int index) {
        return elements.get(index);
    }

    /**
     * Removes and returns the element at the top of the bag (last element added).
     *
     * @return the element at the top of the bag, or null if the bag is empty
     */
    public T getTop() {
        if (isEmpty()) {
            return null;
        }
        return elements.removeLast();
    }

    /**
     * Peeks at the element at the top of the bag without removing it.
     *
     * @return the element at the top of the bag, or null if the bag is empty
     */
    public T peekTop() {
        if (isEmpty()) {
            return null;
        }
        return elements.getLast();
    }

    /**
     * Checks if the bag is empty.
     *
     * @return true if the bag is empty, false otherwise
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Returns the number of elements in the bag.
     *
     * @return the number of elements in the bag
     */
    public int size() {
        return elements.size();
    }

    /**
     * Shuffles the elements in the bag.
     */
    public void shuffle() {
        Collections.shuffle(elements, random);
    }

    /**
     * Clears all elements from the bag.
     */
    public void clear() {
        elements.clear();
    }

    /**
     * Transfers all elements from this bag to another bag.
     * This bag will be empty after the transfer.
     *
     * @param otherBag the bag to transfer elements to
     */
    public void transferAllTo(Bag<T> otherBag) {
        otherBag.addAll(elements);
        this.clear();
    }

    /**
     * Returns a string representation of the bag.
     *
     * @return a string representation of the bag
     */
    @Override
    public String toString() {
        return elements.toString();
    }
}