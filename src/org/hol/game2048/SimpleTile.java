/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hol.game2048;

import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointState;

/**
 *
 * @author franc
 */
public class SimpleTile implements SamplePointState {

    private int value;

    /**
     *
     */
    public SimpleTile() {
        this(0);
    }

    /**
     *
     * @param num
     */
    public SimpleTile(int num) {
        value = num;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final SimpleTile other = (SimpleTile) obj;
        return this.value == other.value;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.value;
        return hash;
    }

    boolean isEmpty() {
        return value == 0;
    }
}
