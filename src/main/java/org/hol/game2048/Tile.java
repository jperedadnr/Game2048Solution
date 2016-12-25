package org.hol.game2048;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * JAVAONE 2014 - Create the Game 2048 with Java 8 and JavaFX [HOL3244]
 *
 * @authors bruno.borges@oracle.com @brunoborges & pereda@eii.uva.es @jperedadnr
 */
public
class Tile
        extends Label {

    private Location location;
    private boolean  merged;
    private int      value;

    private
    Tile( final int value ) {
        // TO-DO. Step 6. Create tile
        if ( Game2048.STEP >= 6 ) {
            final int squareSize = Board.CELL_SIZE - 13;
            setMinSize(squareSize, squareSize);
            setMaxSize(squareSize, squareSize);
            setPrefSize(squareSize, squareSize);
            // TO-DO: Step 10. Load css and remove style
            if ( Game2048.STEP < 10 ) {
                setStyle("-fx-background-color: #c9c9c9;");
            } else {
                getStyleClass().addAll("game-label", "game-tile-" + value);
            }
            setAlignment(Pos.CENTER);

            this.value = value;
            merged = false;
            setText(Integer.toString(value));
        }
    }

    /**
     * @return
     */
    public static
    Tile newRandomTile() {
        // TO-DO. Step 7. Create random value, 90% chance 2, 10% 4
        if ( Game2048.STEP >= 7 ) {
            return newTile(( Math.random() < 0.9 ) ? 2 : 4);
        }
        return newTile(2);
    }

    /**
     * @param value
     *
     * @return
     */
    public static
    Tile newTile( final int value ) {
        return new Tile(value);
    }

    /**
     * @return
     */
    public
    Location getLocation() {
        return location;
    }

    /**
     * @param location
     */
    public
    void setLocation( final Location location ) {
        this.location = location;
    }

    /**
     * @return
     */
    public
    int getValue() {
        return value;
    }

    /**
     * @param value
     */
    public
    void setValue( final int value ) {
        this.value = value;
    }

    /**
     * @param anotherTile
     *
     * @return
     */
    public
    boolean isMergeable( final Tile anotherTile ) {
        // TO-DO: Step 27. Check it this.tile can be merged with anotherTile
        return ( Game2048.STEP >= 27 ) && ( anotherTile != null ) && ( value == anotherTile.value );
    }

    /**
     * @return
     */
    public
    boolean isMerged() {
        return merged;
    }

    /**
     * @param merged
     */
    public
    void setMerged( final boolean merged ) {
        this.merged = merged;
    }

    /**
     * @param another
     */
    public
    void merge( final Tile another ) {
        // TO-DO: Step 27. Add to tile's value the value of the tile to be merged to,
        // set the text with the new value and replace the old style ‘game-title-“-value with the new one
        if ( Game2048.STEP >= 27 ) {
            getStyleClass().remove("game-tile-" + value);
            value += another.value;
            setText(Integer.toString(value));
            merged = true;
            getStyleClass().add("game-tile-" + value);
        }
    }

    @Override
    public
    String toString() {
        return "Tile{" + "value=" + value + ", location=" + location + ", merged=" + merged + '}';
    }

}
