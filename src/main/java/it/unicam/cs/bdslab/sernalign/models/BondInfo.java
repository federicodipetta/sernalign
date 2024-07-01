package it.unicam.cs.bdslab.sernalign.models;

public class BondInfo {
    private int i;
    private int j;
    private int context;
    public boolean ichanged;
    public boolean jchanged;

    public BondInfo(int i, int j, int context) {
        this.i = i;
        this.j = j;
        this.context = context;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getContext() {
        return context;
    }

    public void setI(int i) {
        this.ichanged = true;
        this.i = i;
    }

    public void setJ(int j) {
        this.jchanged = true;
        this.j = j;
    }


}
