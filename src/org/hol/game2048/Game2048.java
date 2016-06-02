package org.hol.game2048;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IActor;
import ar.edu.unrc.tdlearning.perceptron.learning.ActionPrediction;
import ar.edu.unrc.tdlearning.perceptron.learning.FunctionUtils;
import ar.edu.unrc.tdlearning.perceptron.learning.MaximalListConsumer;
import static ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning.randomBetween;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import ar.edu.unrc.tdlearning.perceptron.ntuple.SamplePointState;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

/**
 * JAVAONE 2014 - Create the Game 2048 with Java 8 and JavaFX [HOL3244]
 *
 * @authors bruno.borges@oracle.com @brunoborges & pereda@eii.uva.es @jperedadnr
 */
public class Game2048 extends Application {

    /**
     *
     */
    public static final int STEP = 45;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *
     */
    public NormalizedField normOutput;
    private boolean computeParallelBestPossibleAction;

    private GameManager gameManager;
    private NTupleSystem nTupleSystem;

    /**
     *
     * @param turnInitialState
     * @param allPossibleActionsFromTurnInitialState
     * @param player
     *
     * @return
     */
    public IAction computeBestPossibleAction(NTupleBoard turnInitialState, List<IAction> allPossibleActionsFromTurnInitialState, IActor player) {
        Stream<IAction> stream;
        if ( computeParallelBestPossibleAction ) {
            stream = allPossibleActionsFromTurnInitialState.parallelStream();
        } else {
            stream = allPossibleActionsFromTurnInitialState.stream();
        }
        List<ActionPrediction> bestActiones
                = stream
                .map(possibleAction -> evaluate(turnInitialState, possibleAction, player))
                .collect(MaximalListConsumer::new, MaximalListConsumer::accept, MaximalListConsumer::combine)
                .getList();
        IAction bestAction = bestActiones.get(randomBetween(0, bestActiones.size() - 1)).getAction();
        return bestAction;
    }

    @Override
    public void init() {
        // TO-DO: Step 10. Load font when css is enabled
        if ( STEP >= 10 ) {
            Font.loadFont(Game2048.class.getResource("ClearSans-Bold.ttf").toExternalForm(), 10.0);
            InputStream perceptronFile = this.getClass().getResourceAsStream("/org/hol/game2048/trainedntuplas/Experiment_TanH_trained.ser");
            Function<Double, Double> activationFunction = FunctionUtils.tanh;
            Function<Double, Double> derivatedActivationFunction = FunctionUtils.derivatedTanh;
            boolean concurrency = false;
            int maxTile = 15;
            double activationFunctionMax = 1;
            double activationFunctionMin = -1;
            int maxReward = 500_000;
            int minReward = -500_000;
            int[] nTuplesLenght = new int[17];
            for ( int i = 0; i < 17; i++ ) {
                nTuplesLenght[i] = 4;
            }

            ArrayList<SamplePointState> allSamplePointStates = new ArrayList<>();
            allSamplePointStates.add(new SimpleTile());
            for ( int i = 1; i <= maxTile; i++ ) {
                allSamplePointStates.add(new SimpleTile((int) Math.pow(2, i)));
            }
            nTupleSystem = new NTupleSystem(allSamplePointStates, nTuplesLenght, activationFunction, derivatedActivationFunction, concurrency);
            try {
                nTupleSystem.load(perceptronFile);
            } catch ( IOException | ClassNotFoundException ex ) {
                Logger.getLogger(Game2048.class.getName()).log(Level.SEVERE, null, ex);
            }

            normOutput = new NormalizedField(NormalizationAction.Normalize,
                    null, maxReward, minReward, activationFunctionMax, activationFunctionMin);
            computeParallelBestPossibleAction = false;
        }
    }

    @Override
    public void start(Stage primaryStage) {

        StackPane root = new StackPane();
        // TO-DO: Step 1. Add gameManager to root
        if ( STEP >= 1 ) {
            gameManager = new GameManager();
            root.getChildren().add(gameManager);
        }
        Scene scene = new Scene(root, 600, 700);
        // TO-DO: Step 10. Load css
        if ( STEP >= 10 ) {
            scene.getStylesheets().add(Game2048.class.getResource("game.css").toExternalForm());
            root.getStyleClass().addAll("game-root");
        }
        // TO-DO: Step 14. enable arrow keys to move the tiles
        if ( Game2048.STEP >= 14 ) {
            scene.setOnKeyPressed(ke -> {
                KeyCode keyCode = ke.getCode();
                if ( keyCode.isArrowKey() ) {
                    Direction dir = Direction.valueFor(keyCode);
                    gameManager.move(dir);
                } else if ( keyCode == KeyCode.SPACE ) {
                    List<IAction> possibleActions = gameManager.listAllPossibleActions(gameManager.getNTupleBoard());
                    Direction bestAction = (Direction) computeBestPossibleAction(gameManager.getNTupleBoard(), possibleActions, null);
                    gameManager.move(bestAction);
                }
            });
        }

        primaryStage.setTitle("2048FX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     *
     * @param turnInitialState
     * @param action
     * @param player
     *
     * @return
     */
    protected ActionPrediction evaluate(NTupleBoard turnInitialState, IAction action, IActor player) {
        NTupleBoard afterstate = gameManager.computeAfterState(turnInitialState, action);
        Double output = normOutput.deNormalize(nTupleSystem.getComputation(afterstate)) + afterstate.getPartialScore();
        return new ActionPrediction(action, output);
    }

}
