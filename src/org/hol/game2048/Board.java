package org.hol.game2048;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * JAVAONE 2014 - Create the Game 2048 with Java 8 and JavaFX [HOL3244]
 *
 * @authors bruno.borges@oracle.com @brunoborges & pereda@eii.uva.es @jperedadnr
 */
public class Board extends Group {

    /**
     *
     */
    public static final int CELL_SIZE = 128;
    private static final int BORDER_WIDTH = (14 + 2) / 2;

    /**
     *
     */
    public static final int GRID_WIDTH = 4 * CELL_SIZE + 2 * BORDER_WIDTH;
    private static final int GAP_HEIGHT = 50;
    private static final int TOP_HEIGHT = 92;
    private final Timeline animateAddedPoints = new Timeline();
    private final Button bContinue = new Button("Keep going");
    private final Button bTry = new Button("Try again");
    private final HBox buttonsOverlay = new HBox();
    private final IntegerProperty gameMovePoints = new SimpleIntegerProperty(0);
    private final BooleanProperty gameOverProperty = new SimpleBooleanProperty(false);
    private final IntegerProperty gameScoreProperty = new SimpleIntegerProperty(0);
    private final BooleanProperty gameWonProperty = new SimpleBooleanProperty(false);
    private final Group gridGroup = new Group();
    private final HBox hBottom = new HBox();
    private final HBox hMid = new HBox();
    private final HBox hTop = new HBox(0);
    private final Label lOvrText = new Label();
    private final Label lblBest = new Label("0");
    private final Label lblPoints = new Label();
    private final Label lblScore = new Label("0");
    private final HBox overlay = new HBox();
    private final BooleanProperty resetGame = new SimpleBooleanProperty(false);
    private final VBox vGame = new VBox(0);
    private final VBox vScore = new VBox(0);

    /**
     *
     */
    public Board() {
        // TO-DO: Step 3. Call create score
        if ( Game2048.STEP >= 3 ) {
            createScore();
        }
        // TO-DO: Step 5. Call create grid
        if ( Game2048.STEP >= 5 ) {
            createGrid();
        }
        // TO-DO: Step 41. Call initGameProperties
        if ( Game2048.STEP >= 41 ) {
            initGameProperties();
        }
    }

    /**
     *
     * @param points
     */
    public void addPoints(int points) {
        // TO-DO: Step 33. add points to score, bind lblScore to gameScore
        if ( Game2048.STEP >= 33 ) {
            gameMovePoints.set(gameMovePoints.get() + points);
            gameScoreProperty.set(gameScoreProperty.get() + points);
        }
    }

    /**
     *
     * @param tile
     */
    public void addTile(Tile tile) {
        // TO-DO: Step 8. Specify tile's location before adding it to the gridGroup
        if ( Game2048.STEP >= 8 ) {
            moveTile(tile, tile.getLocation());
        }
        gridGroup.getChildren().add(tile);
    }

    /**
     *
     */
    public void animateScore() {
        // TO-DO: Step 35. Start animating points added.
        if ( Game2048.STEP >= 35 ) {
            animateAddedPoints.playFromStart();
        }
    }

    /**
     *
     * @param gameOver
     */
    public void setGameOver(boolean gameOver) {
        gameOverProperty.set(gameOver);
    }

    /**
     *
     * @param won
     */
    public void setGameWin(boolean won) {
        gameWonProperty.set(won);
    }

    /**
     *
     * @return
     */
    public Group getGridGroup() {
        return gridGroup;
    }

    /**
     *
     * @return
     */
    public int getPoints() {
        return gameMovePoints.get();
    }

    /**
     *
     * @param points
     */
    public void setPoints(int points) {
        gameMovePoints.set(points);
    }

    /**
     *
     * @param tile
     * @param location
     */
    public void moveTile(Tile tile,
            Location location) {
        // TO-DO: Step 8. Translate the tile to the selected location
        if ( Game2048.STEP >= 8 ) {
            double layoutX = location.getLayoutX(CELL_SIZE) - (tile.getMinWidth() / 2);
            double layoutY = location.getLayoutY(CELL_SIZE) - (tile.getMinHeight() / 2);

            tile.setLayoutX(layoutX);
            tile.setLayoutY(layoutY);
        }
    }

    /**
     *
     * @return
     */
    public BooleanProperty resetGameProperty() {
        return resetGame;
    }

    private Rectangle createCell(int i,
            int j) {
        Rectangle cell = null;
        // TO-DO: Step 4. Create a squared rectangle, located over each coordinate (x,y), size CELL_SIZE
        if ( Game2048.STEP >= 4 ) {
            cell = new Rectangle(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            cell.setFill(Color.WHITE);
            cell.setStroke(Color.GREY);
        }
        // TO-DO: Step 10. Load css
        if ( Game2048.STEP >= 10 && cell != null ) {
            cell.setArcHeight(CELL_SIZE / 6d);
            cell.setArcWidth(CELL_SIZE / 6d);
            cell.getStyleClass().add("game-grid-cell");
        }
        return cell;
    }

    private void createGrid() {
        // TO-DO: Step 5. Add 4x4 cells to the gridGroup
        if ( Game2048.STEP >= 5 && Game2048.STEP < 25 ) {
            for ( int i = 0; i < 4; i++ ) {
                for ( int j = 0; j < 4; j++ ) {
                    gridGroup.getChildren().add(createCell(i, j));
                }
            }
        } // TO-DO: Step 25. Use traverseGrid
        else if ( Game2048.STEP >= 25 ) {
            GridOperator.traverseGrid((i, j) ->
                    {
                        gridGroup.getChildren().add(createCell(i, j));
                        return 0;
                    });
        }

        // TO-DO: Step 10. Load css
        if ( Game2048.STEP >= 10 ) {
            gridGroup.getStyleClass().add("game-grid");
            hBottom.getStyleClass().add("game-backGrid");
        }

        gridGroup.setManaged(false);
        gridGroup.setLayoutX(BORDER_WIDTH);
        gridGroup.setLayoutY(BORDER_WIDTH);

        hBottom.setMinSize(GRID_WIDTH, GRID_WIDTH);
        hBottom.setPrefSize(GRID_WIDTH, GRID_WIDTH);
        hBottom.setMaxSize(GRID_WIDTH, GRID_WIDTH);
        // TO-DO: Step 31. Clip the grid to avoid dropshadow effect moves it
        if ( Game2048.STEP >= 31 ) {
            Rectangle rect = new Rectangle(GRID_WIDTH, GRID_WIDTH);
            hBottom.setClip(rect);
        }
        hBottom.getChildren().add(gridGroup);

        vGame.getChildren().add(hBottom);
        getChildren().add(0, vGame);
    }

    private void createScore() {
        // TO-DO: Step 3. Add the nodes to the top HBox
        if ( Game2048.STEP >= 3 ) {
            Label lblTitle = new Label("2048");
            Label lblSubtitle = new Label("FX");

            HBox hFill = new HBox();
            HBox.setHgrow(hFill, Priority.ALWAYS);

            VBox vScores = new VBox();
            HBox hScores = new HBox(5);

            Label lblTit = new Label("SCORE");
            vScore.getChildren().addAll(lblTit, lblScore);

            VBox vRecord = new VBox(0);
            Label lblTitBest = new Label("BEST");
            vRecord.getChildren().addAll(lblTitBest, lblBest);

            hScores.getChildren().addAll(vScore, vRecord);
            VBox vFill = new VBox();
            VBox.setVgrow(vFill, Priority.ALWAYS);
            vScores.getChildren().addAll(hScores, vFill);

            hTop.getChildren().addAll(lblTitle, lblSubtitle, hFill, vScores);

            // TO-DO: Step 10. Load css
            if ( Game2048.STEP >= 10 ) {
                lblTitle.getStyleClass().addAll("game-label", "game-title");
                lblSubtitle.getStyleClass().addAll("game-label", "game-subtitle");
                vScore.getStyleClass().add("game-vbox");
                lblTit.getStyleClass().addAll("game-label", "game-titScore");
                lblScore.getStyleClass().addAll("game-label", "game-score");
                vRecord.getStyleClass().add("game-vbox");
                lblTitBest.getStyleClass().addAll("game-label", "game-titScore");
                lblBest.getStyleClass().addAll("game-label", "game-score");
            }
        }

        hTop.setMinSize(GRID_WIDTH, TOP_HEIGHT);
        hTop.setPrefSize(GRID_WIDTH, TOP_HEIGHT);
        hTop.setMaxSize(GRID_WIDTH, TOP_HEIGHT);

        vGame.getChildren().add(hTop);

        hMid.setMinSize(GRID_WIDTH, GAP_HEIGHT);

        vGame.getChildren().add(hMid);

        // TO-DO: Step 32. Animate points added to score
        if ( Game2048.STEP >= 32 ) {
            lblPoints.getStyleClass().addAll("game-label", "game-points");
            lblPoints.setAlignment(Pos.CENTER);
            lblPoints.setMinWidth(100);
            getChildren().add(lblPoints);
        }

        // TO-DO: Step 33. bind lblPoints  to gameMovePoints with a “+” prefix, if points>0,
        // and bind the lblScore text property with the gameScore property
        if ( Game2048.STEP >= 33 ) {
            lblPoints.textProperty().bind(Bindings.createStringBinding(() ->
                    (gameMovePoints.get() > 0) ? "+".concat(Integer.toString(gameMovePoints.get())) : "",
                    gameMovePoints.asObject()));
            lblScore.textProperty().bind(gameScoreProperty.asString());
        }

        // TO-DO: Step 34. Center points under vScore
        if ( Game2048.STEP >= 34 ) {
            lblScore.textProperty().addListener((ov, s, s1) ->
                    {
                        lblPoints.setLayoutX(0);
                        double midScoreX = vScore.localToScene(vScore.getWidth() / 2d, 0).getX();
                        lblPoints.setLayoutX(
                                lblPoints.sceneToLocal(midScoreX, 0).getX() - lblPoints.getWidth() / 2d);
                    });
        }
        // TO-DO: Step 35. Add a timeline to translate the lblPoints in Y from 20 to 100
        // and reduce its opacity from 1 to 0 in 600 ms.
        if ( Game2048.STEP >= 35 ) {
            final KeyValue kvO0 = new KeyValue(lblPoints.opacityProperty(), 1);
            final KeyValue kvY0 = new KeyValue(lblPoints.layoutYProperty(), 20);
            final KeyValue kvO1 = new KeyValue(lblPoints.opacityProperty(), 0);
            final KeyValue kvY1 = new KeyValue(lblPoints.layoutYProperty(), 100);
            final KeyFrame kfO0 = new KeyFrame(Duration.ZERO, kvO0);
            final KeyFrame kfY0 = new KeyFrame(Duration.ZERO, kvY0);

            Duration animationDuration = Duration.millis(600);
            final KeyFrame kfO1 = new KeyFrame(animationDuration, kvO1);
            final KeyFrame kfY1 = new KeyFrame(animationDuration, kvY1);

            animateAddedPoints.getKeyFrames().addAll(kfO0, kfY0, kfO1, kfY1);
        }
    }

    private void initGameProperties() {
        overlay.setMinSize(GRID_WIDTH, GRID_WIDTH);
        overlay.setAlignment(Pos.CENTER);
        overlay.setTranslateY(TOP_HEIGHT + GAP_HEIGHT);

        overlay.getChildren().setAll(lOvrText);

        buttonsOverlay.setAlignment(Pos.CENTER);
        buttonsOverlay.setTranslateY(TOP_HEIGHT + GAP_HEIGHT + GRID_WIDTH / 2);
        buttonsOverlay.setMinSize(GRID_WIDTH, GRID_WIDTH / 2);
        buttonsOverlay.setSpacing(10);

        // TO-DO: Step 39. style buttons, set listeners to click. In both, remove overlay.
        // In bTry also remove tiles and reset all game properties
        if ( Game2048.STEP >= 39 ) {
            bTry.getStyleClass().add("game-button");
            bTry.setOnAction(e ->
                    {
                        getChildren().removeAll(overlay, buttonsOverlay);
                        gridGroup.getChildren().removeIf(c ->
                                c instanceof Tile);
                        resetGame.set(false);
                        gameScoreProperty.set(0);
                        gameWonProperty.set(false);
                        gameOverProperty.set(false);
                        resetGame.set(true);
                    });
            bContinue.getStyleClass().add("game-button");
            bContinue.setOnAction(e ->
                    getChildren().removeAll(overlay, buttonsOverlay));
        }
        // TO-DO: Step 40. Add listeners to game over, won properties. Set style to
        // overlay, set text and its style, add buttons, and add overlay to board
        if ( Game2048.STEP >= 40 ) {
            gameOverProperty.addListener((observable, oldValue, newValue) ->
                    {
                        if ( newValue ) {
                            overlay.getStyleClass().setAll("game-overlay", "game-overlay-over");
                            lOvrText.setText("Game over!");
                            lOvrText.getStyleClass().setAll("game-label", "game-lblOver");
                            buttonsOverlay.getChildren().setAll(bTry);
                            this.getChildren().addAll(overlay, buttonsOverlay);
                        }
                    });

            gameWonProperty.addListener((observable, oldValue, newValue) ->
                    {
                        if ( newValue ) {
                            overlay.getStyleClass().setAll("game-overlay", "game-overlay-won");
                            lOvrText.setText("You win!");
                            lOvrText.getStyleClass().setAll("game-label", "game-lblWon");
                            buttonsOverlay.getChildren().setAll(bContinue, bTry);
                            this.getChildren().addAll(overlay, buttonsOverlay);
                        }
                    });
        }
    }
}
