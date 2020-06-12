/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.knapsack.problem;

import java.util.List;

/**
 *
 * @author Emmanuel Byrd (a01166339@itesm.mx)
 * @version 1.0
 */
public abstract class AbstractScorer {
    
    public abstract void SetScores(List<Item> items);
}
