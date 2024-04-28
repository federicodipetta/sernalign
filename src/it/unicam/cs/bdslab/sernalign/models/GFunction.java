package it.unicam.cs.bdslab.sernalign.models;
/*
    * This class represents the G function of Gotoh algorithm.
 */
public class GFunction {

    int alpha;
    int beta;

    public GFunction(int alpha, int beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    public int G(int k){
        return alpha + beta * k;
    }
}
