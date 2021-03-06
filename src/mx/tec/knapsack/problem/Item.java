package mx.tec.knapsack.problem;

/**
 * Provides the methods to create and use items for the knapsack problem.
 *
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @author Emmanuel Byrd (a01166339@itesm.mx)
 * @version 2.1
 */
public class Item {

    private final int id, weight;
    private final double profit;
    private double score;

    /**
     * Creates a new instance of <code>Item</code>.
     *
     * @param id The identifier of this item.
     * @param profit The profit of this item.
     * @param weight The weight of this item.
     */
    public Item(int id, double profit, int weight) {
        this.id = id;
        this.profit = profit;
        this.weight = weight;
        this.score = 0;
    }

    /**
     * Returns the identifier of this item.
     *
     * @return The identifier of this item.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the profit of this item.
     *
     * @return The profits of this item.
     */
    public double getProfit() {
        return profit;
    }

    /**
     * Returns the weight of this item.
     *
     * @return The weight of this item.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Returns the profit per weight unit of this item.
     *
     * @return The profit per weight unit of this item.
     */
    public double getProfitPerWeightUnit() {
        return profit / weight;
    }
    
    /**
     * Return the score of the item
     * 
     * @return the score of the item.
     */
    public double getScore() {
        return score;
    }
    
    /**
     * Changes the score of the item
     * 
     * @param new_score the new score of the item
     */
    public void setScore(double new_score) {
        score = new_score;
    }

    /**
     * Returns the string representation of this item.
     *
     * @return The string representation of this item.
     */
    public String toString() {
        StringBuilder string;
        string = new StringBuilder();
        string.append("(").append(id).append(", ").append(weight).append(", ").append(profit).append(")");
        return string.toString();
    }

}
