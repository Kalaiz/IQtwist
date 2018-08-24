package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Board extends Application {
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    // FIXME Task 7: Implement a basic playable Twist Game in JavaFX that only allows pieces to be placed in valid places
    //uses task 8(creates the base for the game) and 5 (check pieces can be used or not).
    private void start_play(){}

    private void rotate(){}//rotate pieces

    //Snap the pieces to nearest position (if it is over the respective position.)
    private void snap_to_nearest_grid(){}

    // FIXME Task 8: Implement starting placements
    /*This will create an  initial board state (according to difficulty level).
     Use task 3 to check game state is correct.Uses assets (pictures) */
    private void makeBoard(){ }
    /*set opacity of selected pieces to a certain percentage  or
    use Blur effect for that certain piece (using setEffect) Use task 9 code for the solutions.*/
    // FIXME Task 10: Implement hints
    public static void hint(){}

    // FIXME Task 11: Generate interesting starting placements
/*In reference to Difficulty level choose a certain state from Difficulty_level */
    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
