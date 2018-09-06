package comp1110.ass2.gui;

import comp1110.ass2.GameBoard;
import comp1110.ass2.TwistGame;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Random;

public class Board extends Application {
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    static GameBoard g = new GameBoard();
    static TwistGame t=new TwistGame();

    // FIXME Task 7: Implement a basic playable Twist Game in JavaFX that only allows pieces to be placed in valid places
    //uses task 8(creates the base for the game) and 5 (check pieces can be used or not).6 should be used here
    private void start_play(){}

    private void rotate(){}//rotate pieces

    //Snap the pieces to nearest position (if it is over the respective position.)
    private void snap_to_nearest_grid(){}

    // FIXME Task 8: Implement starting placements

    private void makeBoard(){//Draft copy
        Random rn =new Random();
        String rngame="";
        int[] arr ={12,8,4,7};
        int in =rn.nextInt(6);
        int in2 =rn.nextInt(3);//for the first alpha
        for(int m =0;m<2+in;m++) {
            if (m == 0) { //Starting must at least contain a single peg
                rngame += (char) (105 + in2);
                rngame += (Integer.toString(rn.nextInt(12)));
                rngame += (char) (65 + in2);
                rngame += (Integer.toString(rn.nextInt(7)));
            } else {

                for (int i = 0; i < 4; i+=4) {
                    rngame += (char) (97 + rn.nextInt(arr[i]));
                    rngame += (Integer.toString(rn.nextInt(arr[i+1])));
                    rngame += (char) (65 + rn.nextInt(arr[i+2]));
                    rngame += (Integer.toString(rn.nextInt(arr[i+3])));
                    System.out.println(rngame);
                    if (!t.isPlacementStringWellFormed(rngame)&&!t.isPlacementStringValid(rngame)) {
                        rngame = rngame.substring(0, rngame.length() - 4);
                        System.out.println(i);
                        i = 0;

                    }
                }
            }

        }


        if (!t.isPlacementStringValid(rngame)) {
            System.out.println("Screw this");

        } else {

            t.displayBoard(t.boardcreator(rngame, 'a'));

        }




    }

    public static void main(String[] args) {
        Board b = new Board();
        b.makeBoard();
    }
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
