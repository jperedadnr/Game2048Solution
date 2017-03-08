package org.hol.game2048;

/**
 * JAVAONE 2014 - Create the Game 2048 with Java 8 and JavaFX [HOL3244]
 *
 * @authors bruno.borges@oracle.com @brunoborges & pereda@eii.uva.es @jperedadnr
 */
public
class Location {

    private int x;
    private int y;

    /**
     * @param x
     * @param y
     */
    public
    Location(
            final int x,
            final int y
    ) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * @param obj
     *
     * @return
     */
    @Override
    public
    boolean equals( final Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Location other = (Location) obj;
        return ( x == other.x ) && ( y == other.y );
    }

    /**
     * @param CELL_SIZE
     *
     * @return
     */
    @SuppressWarnings( "IntegerDivisionInFloatingPointContext" )
    public
    double getLayoutX( final int CELL_SIZE ) {
        return ( x * CELL_SIZE ) + ( CELL_SIZE / 2 );
    }

    /**
     * @param CELL_SIZE
     *
     * @return
     */
    public
    double getLayoutY( final int CELL_SIZE ) {
        //noinspection IntegerDivisionInFloatingPointContext
        return ( y * CELL_SIZE ) + ( CELL_SIZE / 2 );
    }

    /**
     * @return
     */
    public
    int getX() {
        return x;
    }

    /**
     * @param x
     */
    public
    void setX( final int x ) {
        this.x = x;
    }

    /**
     * @return
     */
    public
    int getY() {
        return y;
    }

    /**
     * @param y
     */
    public
    void setY( final int y ) {
        this.y = y;
    }

    /**
     * @return
     */
    @Override
    public
    int hashCode() {
        int hash = 7;
        hash = ( 61 * hash ) + x;
        hash = ( 61 * hash ) + y;
        return hash;
    }

    /**
     * @return
     */
    public
    boolean isValidFor() {
        return ( x >= 0 ) && ( x < 4 ) && ( y >= 0 ) && ( y < 4 );
    }

    /**
     * @param direction
     *
     * @return
     */
    public
    Location offset( final Direction direction ) {
        // TO-DO: Step 12. Return the location of the tile in the selected direction
        if ( Game2048.STEP >= 11 ) {
            return new Location(x + direction.getX(), y + direction.getY());
        }
        return new Location(x, y);
    }

    /**
     * @return
     */
    @Override
    public
    String toString() {
        return "Location{" + "x=" + x + ", y=" + y + '}';
    }

}
