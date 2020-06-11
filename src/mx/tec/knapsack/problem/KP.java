package mx.tec.knapsack.problem;

import mx.tec.hermes.exceptions.NoSuchFeatureException;
import mx.tec.hermes.exceptions.NoSuchHeuristicException;
import mx.tec.hermes.HyperHeuristic;
import mx.tec.hermes.problems.Problem;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import mx.tec.hermes.utils.Files;
import mx.tec.hermes.utils.Statistical;

/**
 * Provides the methods to create and solve knapsack problems.
 *
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 2.0
 */
public class KP extends Problem {

    private final int capacity;
    private final Knapsack knapsack;
    private final List<Item> items;

    /**
     * Creates an empty instance of <code>KP</code>.
     */
    public KP() {
        capacity = 0;
        items = new ArrayList(0);
        fileName = "Not available";
        knapsack = new Knapsack(capacity);
    }

    /**
     * Creates a new instance of <code>KP</code>.
     *
     * @param fileName The name of the file to initialize this problem.
     */
    public KP(String fileName) {
        int weight, i;
        double profit;
        String string;
        StringTokenizer fileTokenizer, lineTokenizer;
        string = Files.load(fileName);
        fileTokenizer = new StringTokenizer(string, "\n");
        lineTokenizer = new StringTokenizer(fileTokenizer.nextToken().trim(), ", \t");
        items = new ArrayList(Integer.parseInt(lineTokenizer.nextToken()));
        capacity = Integer.parseInt(lineTokenizer.nextToken());
        /*
         * Reads the information about the items.
         */
        i = 0;
        while (fileTokenizer.hasMoreTokens()) {
            lineTokenizer = new StringTokenizer(fileTokenizer.nextToken().trim(), ", \t");
            weight = Integer.parseInt(lineTokenizer.nextToken().trim());
            profit = Double.parseDouble(lineTokenizer.nextToken().trim());
            items.add(new Item(i, profit, weight));
            i++;
        }
        this.fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        knapsack = new Knapsack(capacity);
    }

    /**
     * Creates a new instance of <code>KP</code>.
     *
     * @param items The items in this problem.
     * @param capacity The capacity of the knapsack in this problem.
     */
    public KP(List<Item> items, int capacity) {
        this.capacity = capacity;
        this.items = new ArrayList(items.size());
        for (Item item : items) {
            this.items.add(item);
        }
        fileName = "Not available";
        knapsack = new Knapsack(capacity);
    }

    /**
     * Returns the items packed in the knapsack.
     *
     * @return The items packed in the knapsack.
     */
    public final Item[] getPackedItems() {
        return knapsack.getItems();
    }

    /**
     * Returns the items left to pack.
     *
     * @return The items left to pack.
     */
    public final Item[] getUnpackedItems() {
        int i;
        Item[] tmp;
        tmp = new Item[items.size()];
        i = 0;
        for (Item item : items) {
            tmp[i++] = item;
        }
        return tmp;
    }

    @Override
    public void solve(String heuristic) {
        Item item;
        try {
            item = nextItem(heuristic);
            while (item != null) {
                knapsack.pack(item);
                items.remove(item);
                item = nextItem(heuristic);
            }
        } catch (NoSuchHeuristicException exception) {
            System.err.println(exception);
            System.err.println("The system will halt.");
            System.exit(1);
        }
    }

    @Override
    public void solve(HyperHeuristic hyperHeuristic) {
        Item item;
        String heuristic;
        try {
            hyperHeuristic.reset();
            heuristic = hyperHeuristic.getHeuristic(this);
            item = nextItem(heuristic);
            while (item != null) {
                knapsack.pack(item);
                items.remove(item);
                heuristic = hyperHeuristic.getHeuristic(this);
                item = nextItem(heuristic);
            }
        } catch (NoSuchHeuristicException exception) {
            System.err.println(exception);
            System.err.println("The system will halt.");
            System.exit(1);
        }
    }

    @Override
    public double getObjValue() {
        return -knapsack.getProfit();
    }

    @Override
    public double getFeature(String feature) throws NoSuchFeatureException {
        int i;
        double[] x, y;
        switch (feature) {
            case "NORM_MEAN_WEIGHT":
                i = 0;
                x = new double[items.size()];
                for (Item item : items) {
                    x[i++] = item.getWeight();
                }
                return Statistical.mean(x) / Statistical.max(x);
            case "NORM_MEDIAN_WEIGHT":
                i = 0;
                x = new double[items.size()];
                for (Item item : items) {
                    x[i++] = item.getWeight();
                }
                return Statistical.median(x) / Statistical.max(x);
            case "NORM_STD_WEIGHT":
                i = 0;
                x = new double[items.size()];
                for (Item item : items) {
                    x[i++] = item.getWeight();
                }
                return Statistical.stdev(x) / Statistical.max(x);
            case "NORM_MEAN_PROFIT":
                i = 0;
                x = new double[items.size()];
                for (Item item : items) {
                    x[i++] = item.getProfit();
                }
                return Statistical.mean(x) / Statistical.max(x);
            case "NORM_MEDIAN_PROFIT":
                i = 0;
                x = new double[items.size()];
                for (Item item : items) {
                    x[i++] = item.getProfit();
                }
                return Statistical.median(x) / Statistical.max(x);
            case "NORM_STD_PROFIT":
                i = 0;
                x = new double[items.size()];
                for (Item item : items) {
                    x[i++] = item.getProfit();
                }
                return Statistical.stdev(x) / Statistical.max(x);
            case "NORM_MEAN_PROFIT_WEIGHT":
                i = 0;
                x = new double[items.size()];
                for (Item item : items) {
                    x[i++] = item.getProfitPerWeightUnit();
                }
                return Statistical.mean(x) / Statistical.max(x);
            case "NORM_MEDIAN_PROFIT_WEIGHT":
                i = 0;
                x = new double[items.size()];
                for (Item item : items) {
                    x[i++] = item.getProfitPerWeightUnit();
                }
                return Statistical.median(x) / Statistical.max(x);
            case "NORM_STD_PROFIT_WEIGHT":
                i = 0;
                x = new double[items.size()];
                for (Item item : items) {
                    x[i++] = item.getProfitPerWeightUnit();
                }
                return Statistical.stdev(x) / Statistical.max(x);
            case "NORM_CORRELATION":
                i = 0;
                x = new double[items.size()];
                y = new double[items.size()];
                for (Item item : items) {
                    x[i] = item.getWeight();
                    y[i++] = item.getProfit();
                }
                return Statistical.correlation(x, y) / 2 + 0.5;
            case "FEASIBILLITY":
                i = 0;
                for (Item item : items) {
                    if (item.getWeight() <= knapsack.getCapacity()) {
                        // Not ready! Check!
                    }
                }
                return 0;
            default:
                throw new NoSuchFeatureException("Feature \'" + feature + "\' is not recognized by the system.");
        }
    }

    /**
     * Solves a given instance by using dynamic programming.
     */
    public void solve() {
        int row;
        double tmpProfit;
        double[][] table;
        Item item;
        table = new double[knapsack.getCapacity() + 1][items.size()];
        for (int i = 0; i < table[0].length; i++) {
            item = items.get(i);
            for (int rowCapacity = 0; rowCapacity < table.length; rowCapacity++) {
                if (item.getWeight() <= rowCapacity) {
                    tmpProfit = item.getProfit();
                    if (i == 0) {
                        table[rowCapacity][i] = tmpProfit;
                    } else {
                        table[rowCapacity][i] = (int) Math.max(table[rowCapacity][i - 1], tmpProfit + table[rowCapacity - item.getWeight()][i - 1]);
                    }
                } else {
                    if (i > 0) {
                        table[rowCapacity][i] = table[rowCapacity][i - 1];
                    }
                }
            }
        }
        row = knapsack.getCapacity();
        for (int i = items.size() - 1; i > 0; i--) {
            if (table[row][i] == table[row][i - 1]) {
                // Do nothing.
            } else {
                item = items.remove(i);
                knapsack.pack(item);
                row = row - item.getWeight();
            }
        }
        if (table[row][0] != 0) {
            item = items.remove(0);
            knapsack.pack(item);
        }
    }

    /**
     * Saves this problem into a text file (in its current state).
     *
     * @param fileName The name of the file where the problem is to be saved.
     */
    public void save(String fileName) {
        StringBuilder string;
        DecimalFormat format;
        string = new StringBuilder();
        string.append(items.size()).append(", ").append(capacity).append("\r\n");
        format = new DecimalFormat("0.000");
        for (Item item : items) {
            string.append(item.getWeight()).append(", ").append(format.format(item.getProfit())).append("\r\n");
        }
        Files.save(string.toString().trim(), fileName);
    }

    @Override
    public String toString() {
        StringBuilder string;
        string = new StringBuilder();
        string.append(items.size()).append(", ").append(capacity).append("\n");
        for (Item item : items) {
            string.append(item.toString()).append("\n");
        }
        string.append(knapsack.toString());
        return string.toString().trim();
    }

    /**
     * Returns a suitable item to place in the knapsack.
     *
     * @return The next item to pack.
     * @throws mx.tec.hermes.exceptions.NoSuchHeuristicException
     */
    private Item nextItem(String heuristic) throws NoSuchHeuristicException {
        double value;
        Item selected;
        selected = null;
        switch (heuristic) {
            case "DEFAULT":
                for (Item item : items) {
                    if (knapsack.canPack(item)) {
                        selected = item;
                        break;
                    }
                }
                return selected;
            case "MAX_PROFIT":
                value = -Double.MAX_VALUE;
                for (Item item : items) {
                    if (knapsack.canPack(item) && item.getProfit() > value) {
                        selected = item;
                        value = selected.getProfit();
                    }
                }
                return selected;
            case "MAX_PROFIT/WEIGHT":
                value = -Double.MAX_VALUE;
                for (Item item : items) {
                    if (knapsack.canPack(item) && item.getProfitPerWeightUnit() > value) {
                        selected = item;
                        value = selected.getProfitPerWeightUnit();
                    }
                }
                return selected;
            case "MIN_WEIGHT":
                value = Double.MAX_VALUE;
                for (Item item : items) {
                    if (knapsack.canPack(item) && item.getWeight() < value) {
                        selected = item;
                        value = selected.getWeight();
                    }
                }
                return selected;
            case "MARKOVITZ":
                value = -Double.MAX_VALUE;
                for (Item item : items) {
                    if (knapsack.canPack(item) && item.getProfit() * item.getWeight() > value) {
                        selected = item;
                        value = item.getProfit() * item.getWeight();
                    }
                }
                return selected;
        }
        throw new NoSuchHeuristicException("Heuristic \'" + heuristic + "\' is not recognized by the system.");
    }

}
