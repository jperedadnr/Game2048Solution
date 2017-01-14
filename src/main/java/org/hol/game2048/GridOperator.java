package org.hol.game2048;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * JAVAONE 2014 - Create the Game 2048 with Java 8 and JavaFX [HOL3244]
 *
 * @authors bruno.borges@oracle.com @brunoborges & pereda@eii.uva.es @jperedadnr
 */
public
class GridOperator {

    private static final List< Integer > TRAVERSAL_X = IntStream.range(0, 4).boxed().collect(Collectors.toList());
    private static final List< Integer > TRAVERSAL_Y = IntStream.range(0, 4).boxed().collect(Collectors.toList());

    /**
     * @param direction
     */
    public static
    void sortGrid( final Direction direction ) {
        // TO-DO: Step 26. Sort TraversalX, TRAVERSAL_Y, so for Right or Down directions
        // they are taken in reverse order
        if ( Game2048.STEP >= 26 ) {
            TRAVERSAL_X.sort(( direction == Direction.RIGHT ) ? Collections.reverseOrder() : Integer::compareTo);
            TRAVERSAL_Y.sort(( direction == Direction.DOWN ) ? Collections.reverseOrder() : Integer::compareTo);
        }
    }

    /**
     * @param func
     *
     * @return
     */
    public static
    int traverseGrid( final IntBinaryOperator func ) {
        final AtomicInteger at = new AtomicInteger();
        // TO-DO: Step 24. Traverse grid, applyinf the functional to every cell, returning the
        // accumulated result
        if ( Game2048.STEP >= 24 ) {
            TRAVERSAL_X.forEach(x -> TRAVERSAL_Y.forEach(y -> at.addAndGet(func.applyAsInt(x, y))));
        }
        return at.get();
    }
}
