package it.unicam.cs.bdslab.sernalign.models;

import it.unicam.cs.bdslab.sernalign.antlr.RNASecondaryStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditableStructuralSequence extends StructuralSequence {

    private Context head;
    private Context tail;

    private ArrayList<Node> nodes;
    private ArrayList<BondInfo> bounds;
    public EditableStructuralSequence(RNASecondaryStructure secondaryStructure) {
        super(secondaryStructure);
        build();
    }

    public EditableStructuralSequence(int[] structuralSequence) {
        super(structuralSequence);
        build();
    }

    /**
     * Add a context to the sequence
     * @param i the index in the structural sequence where to add the context,
     *          it will add at i and the previous context will be at i+1
     * @param context the position where to insert the context
     */
    public void addContextAt(int i, int context) {
        // if i is 0 it will be 1
        if(i == 0){
            AddBoundInfoAt(0, new BondInfo(0, 1, 1));
        }
        int ctx = 0;
        List<Integer> indexes = new ArrayList<>(i);
        for (int x = 0; x < i; x++) {
            BondInfo bondInfo = this.bounds.get(x);
            indexes.add(bondInfo.getI());
            indexes.add(bondInfo.getJ());
        }
        indexes.sort(Integer::compareTo);
        int where = indexes.get(context - 1) - 1;
        AddBoundInfoAt(where, new BondInfo(where, 0, context));

        super.structuralSequence = buildSequence(
                this.bounds.stream()
                        .map(x -> new WeakBond(x.getI(), x.getJ()))
                        .toList()
                );
    }

    /**
     * Remove a context from the sequence
     * @param i the index in the structural sequence where to remove the context
     */
    public void removeContextAt(int i) {
        this.RemoveBoundInfoAt(i);
        super.structuralSequence = buildSequence(
                this.bounds.stream()
                        .map(x -> new WeakBond(x.getI(), x.getJ()))
                        .toList()
        );
    }

    /**
     * Change the context at the index i
     * @param i the index where to change the context
     * @param context the new context
     */
    public void changeContextAt(int i, int context) {
        BondInfo bondInfo = this.bounds.get(i);
        this.changeBoundInfoAt(i, bondInfo);
        super.structuralSequence = buildSequence(
                this.bounds.stream()
                        .map(x -> new WeakBond(x.getI(), x.getJ()))
                        .toList()
        );
    }

    private void build() {
        List<WeakBond> bounds = this.getSecondaryStructure().getBonds();
        Collections.sort(bounds);
        int max = bounds.getLast().getRight();
        int[] s = this.getStructuralSequence();
        this.bounds = new ArrayList<>(s.length);
        this.nodes = new ArrayList<>(s.length*2);
        List<Integer> indexes = new ArrayList<>(s.length*2);
        // in this way i avoid to iterate over all the array of max size
        Node[] array = new Node[max];
        for (int i = 0; i < s.length; i++) {
            indexes.add(bounds.get(i).getLeft());
            indexes.add(bounds.get(i).getRight());
            this.bounds.add(
                    new BondInfo(
                            bounds.get(i).getLeft()
                            , bounds.get(i).getRight()
                            , s[i]
                    )
            );
            array[bounds.get(i).getLeft()] = new Node(this.bounds.get(i));
            array[bounds.get(i).getRight()] = new Node(this.bounds.get(i));
        }
        indexes.sort(Integer::compareTo);
        //in this way i avoid to iterate over all the array of max size
        for (int i : indexes) {
            BondInfo bondInfo = array[i].bondInfo;
            if (bondInfo != null) {
                if (!bondInfo.ichanged){
                    bondInfo.setI(i);
                    this.nodes.add(array[i]);
                } else {
                    bondInfo.setJ(i);
                    this.nodes.add(array[i]);
                }
            }
        }


    }

    /**
     * Add a context to the sequence
     * @param i the index where to add the context
     * @param bondInfo note that the bound info must have the i and context set
     */
    private void AddBoundInfoAt(int i, BondInfo bondInfo) {
        BondInfo beforeAt = this.bounds.get(i);
        boolean modifiedWithoutConflict = nodes.get(beforeAt.getJ() + 1) == null
                                        && nodes.get(bondInfo.getI()) == null;
        if (modifiedWithoutConflict) {
            BondInfo add = new BondInfo(bondInfo.getI(), beforeAt.getJ()+1, bondInfo.getContext());
            this.nodes.get(add.getI()).setBoundInfo(add);
            this.nodes.get(add.getJ()).setBoundInfo(add);
            this.bounds.add(i, add);
        }else {
            //I have to force the add but i also have to change all the bound info that are in conflict
            BondInfo add = new BondInfo(bondInfo.getI(), beforeAt.getJ()+1, bondInfo.getContext());
            this.bounds.add(i, add);
            this.nodes.add(add.getI(), new Node(add));
            this.nodes.add(add.getI(), new Node());
            this.nodes.set(add.getJ(), new Node(add));
            FixSequence(add);
        }
    }

    private void RemoveBoundInfoAt(int i) {
        BondInfo toRemove = this.bounds.get(i);
        this.bounds.remove(i);
        this.nodes.set(toRemove.getI(), null);
        this.nodes.set(toRemove.getJ(), null);
    }

    private void changeBoundInfoAt(int i, BondInfo bondInfo){
        BondInfo old = this.bounds.get(i);
        boolean noConflict = nodes.get(bondInfo.getI()) == null
                && nodes.get(bondInfo.getJ()) == null;
        if(noConflict) {
            ChangeAt(i, bondInfo, old);
        }else {
            //I have to force the change but I also have to change all the bound info that are in conflict
            ChangeAt(i, bondInfo, old);
            FixSequence(bondInfo);
        }
    }

    private void FixSequence(BondInfo bondInfo) {
        for (int j = bondInfo.getI()+1; j < nodes.size(); j++) {
            // i have the node that contains the bound info and different to add
            if(this.nodes.get(j) != null && j == bondInfo.getJ()) {
                BondInfo toChange = this.nodes.get(j).bondInfo;
                int newI = toChange.getI(), newJ = toChange.getJ();
                if (toChange.getI() == j) {
                    newI += 2;
                }else if (toChange.getJ() == j) {
                    newJ += 2;
                }
                BondInfo newBound = new BondInfo(newI, newJ, toChange.getContext());
                this.nodes.get(j).setBoundInfo(newBound);
            }
        }
    }

    private void ChangeAt(int i, BondInfo bondInfo, BondInfo old) {
        this.bounds.set(i, bondInfo);
        this.nodes.set(old.getI(), null);
        this.nodes.set(old.getJ(), null);
        this.nodes.set(bondInfo.getI(), new Node(bondInfo));
        this.nodes.set(bondInfo.getJ(), new Node(bondInfo));
    }


    private class Node {
        public BondInfo bondInfo;
        public Node(BondInfo bondInfo) {
            this.bondInfo = bondInfo;
        }
        public Node(){
            this.bondInfo = null;
        }
        public void setBoundInfo(BondInfo bondInfo) {
            this.bondInfo = bondInfo;
        }

    }
}
