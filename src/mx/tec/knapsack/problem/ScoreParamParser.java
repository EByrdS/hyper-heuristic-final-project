/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.knapsack.problem;

// import java.util.Comparator; // Needed to the PercentageScorer
import java.util.HashMap;

/**
 *
 * @author Emmanuel Byrd (a01166339@itesm.mx)
 * @version 1.0
 */
public class ScoreParamParser {
    //private final PercentageScorer profit_scorer, weight_scorer;
    private final AbstractScorer profit_scorer, weight_scorer, density_scorer;
    private final ImportanceModifier profit_score_modifier, 
                weight_score_modifier, density_score_modifier;
    
    public ScoreParamParser(HashMap<String, Double> config) {
        weight_scorer = new LinearRegScorer(
                config.get("bot_wg_score"), config.get("top_wg_score"),
                Item::getWeight
        );
        profit_scorer = new LinearRegScorer(
                config.get("bot_pf_score"), config.get("top_pf_score"),
                Item::getProfit
        );
        density_scorer = new LinearRegScorer(
                config.get("dens_min"), config.get("dens_max"),
                Item::getProfitPerWeightUnit
        );
        profit_score_modifier = new ImportanceModifier(
                config.get("pf_mod_min"), config.get("pf_mod_max"),
                "profit"
        );
        weight_score_modifier = new ImportanceModifier(
                config.get("wg_mod_min"), config.get("wg_mod_max"),
                "weight"
        );
        density_score_modifier = new ImportanceModifier(
                config.get("dens_mod_min"), config.get("dens_mod_max"),
                "density"
        );
    }
    
    public ScoreParamParser() {
        HashMap<String, Double> config = DefaultConfig();
        weight_scorer = new LinearRegScorer(
                config.get("bot_wg_score"), config.get("top_wg_score"),
                Item::getWeight
        );
        profit_scorer = new LinearRegScorer(
                config.get("bot_pf_score"), config.get("top_pf_score"),
                Item::getProfit
        );
        density_scorer = new LinearRegScorer(
                config.get("dens_min"),
                config.get("dens_max"),
                Item::getProfitPerWeightUnit
        );
        profit_score_modifier = new ImportanceModifier(
                config.get("pf_mod_min"),
                config.get("pf_mod_max"),
                "profit"
        );
        weight_score_modifier = new ImportanceModifier(
                config.get("wg_mod_min"),
                config.get("wg_mod_max"),
                "weight"
        );
        density_score_modifier = new ImportanceModifier(
                config.get("dens_mod_min"),
                config.get("dens_mod_max"),
                "density"
        );
    }
    
    private HashMap DefaultConfig() {
        HashMap<String, Double> config = new HashMap<>();
        /* Weight */
        // config.put("wg_default", 1.0);
        // config.put("bot_wg_size", 0.10);
        config.put("bot_wg_score", 2.0);
        // config.put("top_wg_size", 0.10);
        config.put("top_wg_score", 0.0);
        
        /* Profit */
        // config.put("pf_default", 1.0);
        // config.put("bot_pf_size", 0.10);
        config.put("bot_pf_score", 0.0);
        // config.put("top_pf_size", 0.10);
        config.put("top_pf_score", 2.0);
        
        /* Density */
        config.put("dens_min", 0.0);
        config.put("dens_max", 2.0);
        
        /**
         * The x value of the modifiers is in range (0,1),
         * the y values for those limits are the following...
         */
        
        /* Weights Modifier */
        config.put("wg_mod_min", 0.0);
        config.put("wg_mod_max", 1.1);
        
        /* Profits Modifier */
        config.put("pf_mod_min", 0.0);
        config.put("pf_mod_max", 0.6);
        
        /* Density Modifier */
        config.put("dens_mod_min", 1.7);
        config.put("dens_mod_max", 0.0);
        
        return config;
    }
    
    public AbstractScorer GetProfitScorer() {
        return this.profit_scorer;
    }
    
    public AbstractScorer GetWeightScorer() {
        return this.weight_scorer;
    }
    
    public AbstractScorer GetDensityScorer() {
        return this.density_scorer;
    }
    
    public ImportanceModifier GetProfitModifier() {
        return this.profit_score_modifier;
    }
    
    public ImportanceModifier GetWeightModifier() {
        return this.weight_score_modifier;
    }
    
    public ImportanceModifier GetDensityModifier() {
        return this.density_score_modifier;
    }
}
