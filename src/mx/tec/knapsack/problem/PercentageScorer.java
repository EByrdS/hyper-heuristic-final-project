/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.knapsack.problem;

import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Emmanuel Byrd (a01166339@itesm.mx)
 * @version 1.0
 */
public class PercentageScorer extends AbstractScorer {
    private final double bot_size;
    private final double bot_score;
    private final double top_size;
    private final double top_score;
    private final double default_score;
    private final Comparator<Item> comparator;
    
    public PercentageScorer (double bot_size, double bot_score,
            double top_size, double top_score, double default_score,
            Comparator<Item> item_comparator) {
        this.bot_size = bot_size;
        this.bot_score = bot_score;
        this.top_size = top_size;
        this.top_score = top_score;
        this.default_score = default_score;
        this.comparator = item_comparator;
    }
    
    @Override
    public void SetScores(List<Item> items) {
        items.sort(this.comparator);
        int length = items.size();
        int bot_limit = (int)(length * bot_size); // Truncate decimals.
        int top_limit = length - (int)(length * top_size);
        for (int i = 1; i <= length; i++) {
            double score = default_score;
            Item item = items.get(i-1);
            if (i <= bot_limit) {
                score = bot_score;
            } else if (i > top_limit) {
                score = top_score;
            }
            item.setScore(score);
        }
    }
}
