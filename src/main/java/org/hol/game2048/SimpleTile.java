/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hol.game2048;

import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointValue;

/**
 * @author franc
 */
public
class SimpleTile
        implements SamplePointValue {

    private final int value;

    /**
     *
     */
    public
    SimpleTile() {
        this(0);
    }

    /**
     * @param num
     */
    public
    SimpleTile( final int num ) {
        super();
        value = num;
    }

    @Override
    public
    boolean equals( final Object obj ) {
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
        return value == other.value;
    }

    /**
     * @return the value
     */
    public
    int getValue() {
        return value;
    }

    @Override
    public
    int hashCode() {
        int hash = 5;
        hash = ( 97 * hash ) + value;
        return hash;
    }

    boolean isEmpty() {
        return value == 0;
    }
}
