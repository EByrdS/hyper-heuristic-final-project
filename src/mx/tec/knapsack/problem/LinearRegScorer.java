/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.knapsack.problem;

import java.util.List;
import java.util.function.Function;

/**
 *
 * @author emmanuel byrd
 */
public class LinearRegScorer {
    private final double min_score;
    private final double max_score;
    private final Function<Item, Double> item_prop_getter;
    
    public LinearRegScorer (
            double min_score, double max_score,
            Function<Item, Double> item_prop_getter){
        this.min_score = min_score;
        this.max_score = max_score;
        this.item_prop_getter = item_prop_getter;
    }
    
    public void SetScores(List<Item> items) {
        double min_val = Double.MAX_VALUE;
        double max_val = Double.MIN_VALUE;
        
        for (Item item : items) {
            double density = this.item_prop_getter.apply(item);
            /** 
             * min and max ate checked independently to prevent bugs
             * when there is only one item
            **/
            if (density < min_val) {
                min_val = density;
            }
            if (density > max_val) {
                max_val = density;
            }
        }
        
        double score_inc_per_val = (max_score - min_score) / 
                (max_val - min_val);
        
        for (Item item : items) {
            double x = this.item_prop_getter.apply(item) - min_val;
            item.setScore(score_inc_per_val * x);
        }
    }
}
