/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.knapsack.problem;

import java.util.List;
import java.util.HashMap;

/**
 *
 * @author emman
 */
public class ScoreHeuristic {
    private final PercentageScorer profit_scorer, weight_scorer;
    private final LinearRegScorer density_scorer;
    private final ImportanceModifier profit_score_modifier, 
                weight_score_modifier, density_score_modifier;
    
    public ScoreHeuristic(
            PercentageScorer profit_scorer, 
            PercentageScorer weight_scorer,  
            LinearRegScorer density_scorer, 
            ImportanceModifier profit_score_modifier, 
            ImportanceModifier weight_score_modifier, 
            ImportanceModifier density_score_modifier) {
        this.profit_scorer = profit_scorer;
        this.weight_scorer = weight_scorer;
        this.density_scorer = density_scorer;
        this.profit_score_modifier = profit_score_modifier;
        this.weight_score_modifier = weight_score_modifier;
        this.density_score_modifier = density_score_modifier;
    }
    
    public void ScoreItems(List<Item> items, Knapsack sack, double weight_avg) {
        int capacity = sack.getCapacity();
        
        double w_mult = this.weight_score_modifier.Multiplier(weight_avg, capacity);
        double p_mult = this.profit_score_modifier.Multiplier(weight_avg, capacity);
        double d_mult = this.density_score_modifier.Multiplier(weight_avg, capacity);
        
        HashMap<Integer, Double> items_score_tracker = new HashMap<>();
        double heuristic_score, modified_score, prev_score, new_score;
        
        
        this.weight_scorer.SetScores(items);
        for (Item item : items) {
            heuristic_score = item.getScore();
            new_score = heuristic_score * w_mult;
            items_score_tracker.put(item.getId(), new_score);
        }
        
        this.profit_scorer.SetScores(items);
        for (Item item : items) {
            heuristic_score = item.getScore();
            modified_score = heuristic_score * p_mult;
            
            prev_score = items_score_tracker.get(item.getId());
            new_score = prev_score + modified_score;
            items_score_tracker.put(item.getId(), new_score);
        }
        
        this.density_scorer.SetScores(items);
        for (Item item : items) {
            heuristic_score = item.getScore();
            modified_score = heuristic_score * d_mult;
            
            prev_score = items_score_tracker.get(item.getId());
            new_score = prev_score + modified_score;
            
            item.setScore(new_score); // Set the final score for each item
        }
    }
}
