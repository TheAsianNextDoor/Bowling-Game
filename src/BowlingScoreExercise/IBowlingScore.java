/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BowlingScoreExercise;

/**
 *
 * @author aaron
 */
public interface IBowlingScore {

    public void recordFrame(int[] pinsKnockedDown);

    public int displayScore();
    
}
