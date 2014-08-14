package org.hol.game2048;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *  JAVAONE 2014 - Create the Game 2048 with Java 8 and JavaFX [HOL3244]
 *  @authors bruno.borges@oracle.com @brunoborges & pereda@eii.uva.es @jperedadnr
 */
public class Game2048 extends Application {
    
    public static final int STEP = 45;

    private GameManager gameManager;
    
    @Override
    public void init() {
        // TO-DO: Step 10. Load font when css is enabled
         if(STEP>=10){
             Font.loadFont(Game2048.class.getResource("ClearSans-Bold.ttf").toExternalForm(), 10.0);
         }
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        StackPane root=new StackPane();
        // TO-DO: Step 1. Add gameManager to root
        if(STEP>=1){
            gameManager=new GameManager();
            root.getChildren().add(gameManager);
        }
        Scene scene = new Scene(root, 600, 700);
        // TO-DO: Step 10. Load css 
        if(STEP>=10){
            scene.getStylesheets().add(Game2048.class.getResource("game.css").toExternalForm());
            root.getStyleClass().addAll("game-root");
        }
        // TO-DO: Step 14. enable arrow keys to move the tiles
        if(Game2048.STEP>=14){
            scene.setOnKeyPressed(ke -> {
                KeyCode keyCode = ke.getCode();
                if(keyCode.isArrowKey()){
                    Direction dir = Direction.valueFor(keyCode);
                    gameManager.move(dir);
                }
            });
        }
        
        primaryStage.setTitle("2048FX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
