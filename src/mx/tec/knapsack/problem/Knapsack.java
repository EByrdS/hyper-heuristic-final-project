package mx.tec.knapsack.problem;

import java.util.LinkedList;
import java.util.List;

/**
 * Provides the methods to create and use knapsacks for the knapsack problem.
 * <p>
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 2.0
 */
public class Knapsack {

    private int capacity;
    private double profit;
    private final List<Item> items;

    /**
     * Creates a new instance of <code>Knapsack</code>.
     *
     * @param capacity The capacity of this knapsack.
     */
    public Knapsack(int capacity) {
        this.capacity = capacity;
        profit = 0;
        items = new LinkedList();
    }

    /**
     * Returns the current capacity of this knapsack.
     *
     * @return The current capacity of this knapsack.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the current profit of this knapsack.
     *
     * @return The current profit of this knapsack.
     */
    public double getProfit() {
        return profit;
    }

    /**
     * Revises if the item provided can be packed in this knapsack.
     *
     * @param item The item to be packed.
     * @return <code>true</code> if the item can be packed in this knapsack, <code>false</code> otherwise.
     */
    public boolean canPack(Item item) {
        return item.getWeight() <= getCapacity();
    }

    /**
     * Packs an item into this knapsack.
     *
     * @param item The item to pack.
     * @return <code>true</code> if the item was successfully packed, <code>false</code> otherwise.
     */
    public boolean pack(Item item) {
        if (item.getWeight() <= capacity) {
            items.add(item);
            capacity -= item.getWeight();
            profit += item.getProfit();
            return true;
        }
        return false;
    }

    /**
     * Returns the items in this knapsack.
     *
     * @return The items in this knapsack.
     */
    public final Item[] getItems() {
        int i;
        Item[] tmp;
        tmp = new Item[items.size()];
        i = 0;
        for (Item item : items) {
            tmp[i++] = item;
        }
        return tmp;
    }

    /**
     * Returns the string representation of this knapsack.
     *
     * @return The string representation of this knapsack.
     */
    public String toString() {
        StringBuilder string;
        string = new StringBuilder();
        for (Item item : items) {
            string.append(item.toString()).append(" ");
        }
        return string.toString().trim();
    }

}
