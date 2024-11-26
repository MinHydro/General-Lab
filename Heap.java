/**
 * Heap.java
 * @author Minh Long Hang
 * CIS 22C, Lab 18
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Heap<T> {
    private int heapSize;
    private ArrayList<T> heap;
    private Comparator<T> cmp;

    /**
     * Constructor for the Heap class.
     * Sets heapSize to data size, stores parameters, inserts null at heap
     * element 0, and calls buildHeap().
     * @param data an unordered ArrayList, where element 0 is not used.
     * @param comparator that determines organization of heap
     * based on priority.
     */
    public Heap(ArrayList<T> data, Comparator<T> cmp) {
        this.heap = new ArrayList<T>();
        this.heap.add(null); // Add null at index 0
        this.heap.addAll(data);
        this.heapSize = data.size();
        this.cmp = cmp;
        buildHeap();
    }

    /**
     * Converts an ArrayList into a valid max heap. Called by constructor.
     * Calls helper method heapify.
     */
    public void buildHeap() {
        for(int i = heapSize/2; i >= 1; i--){
            heapify(i);
        }
    }

    /**
     * Helper method to buildHeap, remove, and sort.
     * Bubbles an element down to its proper location within the heap.
     * @param index an index in the heap
     */
    private void heapify(int index) {
        int largest = index;
        int left = getLeft(index);
        int right = getRight(index);

        if (left <= heapSize && cmp.compare(heap.get(left), heap.get(largest)) > 0) {
            largest = left;
        }
        if (right <= heapSize && cmp.compare(heap.get(right), heap.get(largest)) > 0) {
            largest = right;
        }

        if (largest != index) {
            T temp = heap.get(index);
            heap.set(index, heap.get(largest));
            heap.set(largest, temp);
            heapify(largest);
        }
    }

    /**
     * Inserts the given data into heap.
     * Calls helper method heapIncreaseKey.
     * @param key the data to insert
     */
    public void insert(T key) {
        heapSize++;
        heap.add(null); // placeholder
        heapIncreaseKey(heapSize, key);
    }

    /**
     * Helper method for insert.
     * Bubbles an element up to its proper location
     * @param index the current index of the key
     * @param key the data
     */
    private void heapIncreaseKey(int index, T key) {
        heap.set(index, key);
        while (index > 1 && cmp.compare(heap.get(getParent(index)), heap.get(index)) < 0) {
            T temp = heap.get(index);
            heap.set(index, heap.get(getParent(index)));
            heap.set(getParent(index), temp);
            index = getParent(index);
        }
    }

    /**
     * Removes the element at the specified index.
     * Calls helper method heapify
     * @param index the index of the element to remove
     */
    public void remove(int index) {
        if (index <= 0 || index > heapSize) {
            throw new IndexOutOfBoundsException();
        }
        
        // Replace the element to be removed with the last element
        heap.set(index, heap.get(heapSize));
        
        // Reduce heap size
        heapSize--;
        
        // Remove the last element (which is now a duplicate)
        heap.remove(heapSize + 1);
        
        // Maintain heap property only if heap is not empty and index is valid
        if (heapSize > 0 && index <= heapSize) {
            heapify(index);
        }
    }

    /**
     * Returns the heap size (current number of elements)
     * @return the size of the heap
     */
    public int getHeapSize() {
        return heapSize;
    }

    /**
     * Returns the location (index) of the
     * left child of the element stored at index.
     * @param index the current index
     * @return the index of the left child.
     * @precondition 0 < index <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public int getLeft(int index) throws IndexOutOfBoundsException {
        if (index <= 0 || index > heapSize) {
            throw new IndexOutOfBoundsException();
        }
        return 2 * index;
    }

    /**
     * Returns the location (index) of the right child of the element
     * stored at index.
     * @param index the current index
     * @return the index of the right child
     * @precondition 0 < i <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public int getRight(int index) throws IndexOutOfBoundsException {
        if (index <= 0 || index > heapSize) {
            throw new IndexOutOfBoundsException();
        }
        return 2 * index + 1;
    }

    /**
     * Returns the location (index) of the
     * parent of the element stored at index.
     * @param index the current index
     * @return the index of the parent
     * @precondition 1 < i <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public int getParent(int index) throws IndexOutOfBoundsException {
        if (index <= 1 || index > heapSize) {
            throw new IndexOutOfBoundsException();
        }
        return index / 2;
    }

    /**
     * Returns the maximum element (highest priority)
     * @return the max value
     */
    public T getMax() {
        if (heapSize < 1) {
            throw new NoSuchElementException();
        }
        return heap.get(1);
    }

    /**
     * Returns the element at a specific index.
     * @param index an index in the heap.
     * @return the data at the index.
     * @precondition 0 < i <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public T getElement(int index) throws IndexOutOfBoundsException {
        if (index <= 0 || index > heapSize) {
            throw new IndexOutOfBoundsException();
        }
        return heap.get(index);
    }

    /**
     * Creates a String of all elements in the heap, separated by ", ".
     * @return a String of all elements in the heap, separated by ", ".
     */
    @Override
    public String toString() {
        if (heapSize == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= heapSize; i++) {
            sb.append(heap.get(i));
            if (i < heapSize) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    /**
    * Uses the heap sort algorithm to sort the heap into ascending order.
    * Calls helper method heapify.
    * @return an ArrayList of sorted elements
    * @postcondition heap remains a valid heap
    */
    public ArrayList<T> sort() {
        ArrayList<T> sorted = new ArrayList<>();
        int originalSize = heapSize;

        ArrayList<T> tempHeap = new ArrayList<>(heap);
        for (int i = heapSize; i > 0; i--){
            T temp = heap.get(1);
            heap.set(1, heap.get(i));
            heap.set(i, temp);

            heapSize--;
            if(heapSize > 0){
                heapify(1);
            }
            sorted.add(0, temp);
        }
        heap = tempHeap;
        heapSize = originalSize;
        return sorted;
    }
}