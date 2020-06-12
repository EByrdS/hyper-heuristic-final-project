/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.knapsack.problem;

/**
 *
 * @author Emmanuel Byrd (a01166339@itesm.mx)
 * @version 1.0
 */
public class ImportanceModifier {
    private final String type;
    private final double score_per_ratio;
    
    public ImportanceModifier(double min_score, double max_score, String field) {
        type = field;
        score_per_ratio = max_score - min_score;
    }
    
    public double Multiplier(double weight_avg, int capacity) {
        double x  = weight_avg / capacity;
        return x * score_per_ratio;
    }
}
