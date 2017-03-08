package org.hol.game2048;

import ar.edu.unrc.coeus.tdlearning.interfaces.IAction;
import javafx.scene.input.KeyCode;

/**
 * JAVAONE 2014 - Create the Game 2048 with Java 8 and JavaFX [HOL3244]
 *
 * @authors bruno.borges@oracle.com @brunoborges & pereda@eii.uva.es @jperedadnr
 */
public
enum Direction
        implements IAction {

    /**
     *
     */
    UP(0, -1),
    /**
     *
     */
    RIGHT(1, 0),
    /**
     *
     */
    DOWN(0, 1),
    /**
     *
     */
    LEFT(-1, 0);

    private final int x;
    private final int y;

    Direction(
            final int x,
            final int y
    ) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param keyCode
     *
     * @return
     */
    public static
    Direction valueFor( final KeyCode keyCode ) {
        // TO-DO: Step 11. return the direction of the arrow
        if ( Game2048.STEP >= 11 ) {
            return valueOf(keyCode.name());
        }
        return RIGHT;
    }

    /**
     * @return
     */
    public
    int getX() {
        return x;
    }

    /**
     * @return
     */
    public
    int getY() {
        return y;
    }

    /**
     * @return
     */
    @Override
    public
    String toString() {
        return "Direction{" + "y=" + y + ", x=" + x + "} " + name();
    }

}
