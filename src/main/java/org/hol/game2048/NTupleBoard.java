/*
 * Copyright (C) 2016  Lucia Bressan <lucyluz333@gmial.com>,
 *                     Franco Pellegrini <francogpellegrini@gmail.com>,
 *                     Renzo Bianchini <renzobianchini85@gmail.com
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.hol.game2048;

import ar.edu.unrc.coeus.tdlearning.interfaces.IState;
import ar.edu.unrc.coeus.tdlearning.interfaces.IStateNTuple;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.lang.System.arraycopy;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini pellegrini
 */
public
class NTupleBoard
        implements IStateNTuple {

    /**
     *
     */
    public static final int             TILE_NUMBER        = 4 << 2;
    private             List< Integer > availableSpaceList = null;
    private boolean         canMove;
    private boolean         iWin;
    private boolean         isFull;
    private boolean needToAddTile = false;
    private int             partialScore;
    private SimpleTile[]    tiles;

    /**
     * @param tiles
     */
    public
    NTupleBoard( final SimpleTile[] tiles ) {
        super();
        iWin = false;
        canMove = true;
        this.tiles = tiles;
        partialScore = 0;
        isFull = false;
    }

    void addPartialScore( final int value ) {
        partialScore += value;
    }

    private
    List< Integer > calculateAvailableSpace() {
        final List< Integer > list       = new ArrayList<>(16);
        final int             tileLength = tiles.length;
        for ( int i = 0; i < tileLength; i++ ) {
            if ( tiles[i].isEmpty() ) {
                list.add(i);
            }
        }
        return list;
    }

    private
    boolean calculateCanMove() {
        if ( !isFull ) {
            return true;
        }
        for ( int x = 0; x < 4; x++ ) {
            for ( int y = 0; y < 4; y++ ) {
                final SimpleTile t = tileAt(x, y);
                if ( ( ( x < 3 ) && ( t.getValue() == tileAt(x + 1, y).getValue() ) ) ||
                     ( ( y < 3 ) && ( t.getValue() == tileAt(x, y + 1).getValue() ) ) ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param obj
     *
     * @return
     */
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
        final NTupleBoard other = (NTupleBoard) obj;
        return ( partialScore == other.partialScore ) && ( iWin == other.iWin ) && ( canMove == other.canMove ) && ( isFull == other.isFull ) &&
               ( needToAddTile == other.needToAddTile ) && Arrays.deepEquals(tiles, other.tiles) &&
               Objects.equals(availableSpaceList, other.availableSpaceList);
    }

    @Override
    public
    IState getCopy() {
        final NTupleBoard copy = new NTupleBoard(new SimpleTile[TILE_NUMBER]);
        arraycopy(tiles, 0, copy.tiles, 0, TILE_NUMBER);
        copy.iWin = iWin;
        copy.canMove = canMove;
        copy.isFull = isFull;
        copy.availableSpaceList = new ArrayList<>(TILE_NUMBER);
        availableSpaceList.forEach(( space ) -> copy.availableSpaceList.add(space));
        copy.needToAddTile = needToAddTile;
        copy.partialScore = partialScore;
        return copy;
    }

    @Override
    public
    SamplePointValue[] getNTuple( final int nTupleIndex ) {
        switch ( nTupleIndex ) {
            // verticales
            case 0:
                return new SamplePointValue[] { tileAt(0, 0), tileAt(0, 1), tileAt(0, 2), tileAt(0, 3) };
            case 1:
                return new SamplePointValue[] { tileAt(1, 0), tileAt(1, 1), tileAt(1, 2), tileAt(1, 3) };
            case 2:
                return new SamplePointValue[] { tileAt(2, 0), tileAt(2, 1), tileAt(2, 2), tileAt(2, 3) };
            case 3:
                return new SamplePointValue[] { tileAt(3, 0), tileAt(3, 1), tileAt(3, 2), tileAt(3, 3) };
            // horizontales
            case 4:
                return new SamplePointValue[] { tileAt(0, 0), tileAt(1, 0), tileAt(2, 0), tileAt(3, 0) };
            case 5:
                return new SamplePointValue[] { tileAt(0, 1), tileAt(1, 1), tileAt(2, 1), tileAt(3, 1) };
            case 6:
                return new SamplePointValue[] { tileAt(0, 2), tileAt(1, 2), tileAt(2, 2), tileAt(3, 2) };
            case 7:
                return new SamplePointValue[] { tileAt(0, 3), tileAt(1, 3), tileAt(2, 3), tileAt(3, 3) };
            // cuadrados
            // primera fila de rectángulos
            case 8:
                return new SamplePointValue[] { tileAt(0, 0), tileAt(0, 1), tileAt(1, 1), tileAt(1, 0) };
            case 9:
                return new SamplePointValue[] { tileAt(1, 0), tileAt(1, 1), tileAt(2, 1), tileAt(2, 0) };
            case 10:
                return new SamplePointValue[] { tileAt(2, 0), tileAt(2, 1), tileAt(3, 1), tileAt(3, 0) };
            //segunda fila de rectángulos
            case 11:
                return new SamplePointValue[] { tileAt(0, 1), tileAt(0, 2), tileAt(1, 2), tileAt(1, 1) };
            case 12:
                return new SamplePointValue[] { tileAt(1, 1), tileAt(1, 2), tileAt(2, 2), tileAt(2, 1) };
            case 13:
                return new SamplePointValue[] { tileAt(2, 1), tileAt(2, 2), tileAt(3, 2), tileAt(3, 1) };
            //tercera fila de rectángulos
            case 14:
                return new SamplePointValue[] { tileAt(0, 2), tileAt(0, 3), tileAt(1, 3), tileAt(1, 2) };
            case 15:
                return new SamplePointValue[] { tileAt(1, 2), tileAt(1, 3), tileAt(2, 3), tileAt(2, 2) };
            case 16:
                return new SamplePointValue[] { tileAt(2, 2), tileAt(2, 3), tileAt(3, 3), tileAt(3, 2) };

            default:
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    /**
     * @return the partialScore
     */
    public
    int getPartialScore() {
        return partialScore;
    }

    /**
     * @param partialScore the partialScore to set
     */
    public
    void setPartialScore( final int partialScore ) {
        this.partialScore = partialScore;
    }

    @Override
    public
    double getStateReward( final int outputNeuron ) {
        return partialScore;
    }

    /**
     * @return the tiles
     */
    public
    SimpleTile[] getTiles() {
        return tiles;
    }

    /**
     * @param tiles the tiles to set
     */
    public
    void setTiles( final SimpleTile[] tiles ) {
        this.tiles = tiles;
    }

    /**
     * @return
     */
    @Override
    public
    int hashCode() {
        int hash = 3;
        hash = ( 29 * hash ) + Arrays.deepHashCode(tiles);
        hash = ( 29 * hash ) + partialScore;
        hash = ( 29 * hash ) + ( iWin ? 1 : 0 );
        hash = ( 29 * hash ) + ( canMove ? 1 : 0 );
        hash = ( 29 * hash ) + ( isFull ? 1 : 0 );
        hash = ( 29 * hash ) + ( needToAddTile ? 1 : 0 );
        hash = ( 29 * hash ) + Objects.hashCode(availableSpaceList);
        return hash;
    }

    /**
     * @return the canMove
     */
    public
    boolean isCanMove() {
        return canMove;
    }

    /**
     * @param canMove the canMove to set
     */
    public
    void setCanMove( final boolean canMove ) {
        this.canMove = canMove;
    }

    /**
     * @param gameBoard para comparar
     *                  <p>
     *
     * @return true si los 2 tableros son iguales topológicamente
     */
    public
    boolean isEqual( final NTupleBoard gameBoard ) {
        final int tileLength = tiles.length;
        for ( int i = 0; i < tileLength; i++ ) {
            if ( !tiles[i].equals(gameBoard.tiles[i]) ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public
    boolean isTerminalState() {
        return iWin || !canMove;
    }

    /**
     * @return the iWin
     */
    public
    boolean isiWin() {
        return iWin;
    }

    /**
     * @param iWin the iWin to set
     */
    public
    void setiWin( final boolean iWin ) {
        this.iWin = iWin;
    }

    /**
     * @param needToAddTile the needToAddTile to set
     */
    public
    void setNeedToAddTile( final boolean needToAddTile ) {
        this.needToAddTile = needToAddTile;
    }

    /**
     * Establece que este tablero gano el juego
     */
    public
    void setToWin() {
        iWin = true;
    }

    /**
     * @param x
     * @param y
     *
     * @return
     */
    public
    SimpleTile tileAt(
            final int x,
            final int y
    ) {
        return tiles[x + ( y << 2 )];
    }

    /**
     * actualizamos la traducción del tablero como entrada del perceptron, encriptado y normalizado. También se
     * actualiza el calculo de si este es un tablero final o no.
     */
    public
    void updateInternalState() {
        availableSpaceList = calculateAvailableSpace();
        isFull = availableSpaceList.isEmpty();
        canMove = calculateCanMove();
    }

}
