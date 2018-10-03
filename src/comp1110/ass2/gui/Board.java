package comp1110.ass2.gui;

import comp1110.ass2.GameBoard;
import comp1110.ass2.TwistGame;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board extends Application {
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    static GameBoard g = new GameBoard();
    static TwistGame t = new TwistGame();

    // FIXME Task 7: Implement a basic playable Twist Game in JavaFX that only allows pieces to be placed in valid places
    //uses task 8(creates the base for the game) and 5 (check pieces can be used or not).6 should be used here
    //private void start_play(){ }
/*
task 8 return valid_Placement()
 */
    public static String piece() {
        char[] piece = new char[4];
        Random rand = new Random();
        piece[0] = (char) (rand.nextInt(8) + 97);
        piece[1] = (char) (rand.nextInt(8) + 49);
        piece[2] = (char) (rand.nextInt(4) + 65);
        piece[3] = (char) (rand.nextInt(8) + 48);
        String finalpiece = new String(piece);

        return finalpiece;
    }

    public static String peg() {
        char[] peg = new char[4];
        Random rand = new Random();
        peg[0] = (char) (rand.nextInt(4) + 105);
        peg[1] = (char) (rand.nextInt(8) + 49);
        peg[2] = (char) (rand.nextInt(4) + 65);
        peg[3] = (char) (rand.nextInt(8) + 48);
        String finalpeg = new String(peg);

        return finalpeg;
    }

    public static String start_Placements() {
        Random rand = new Random();
        String str = "";
        int numofpiece = rand.nextInt(2);
        int numofpeg = rand.nextInt(5) + 1;
        if (numofpiece == 1) {
            str = str + piece();
        }
        for (int i = 0; i < numofpeg; i++) {
            str = str + peg();
        }

        return str;
    }

    public static String valid_Placement() {
        String str = start_Placements();
        while(!t.isPlacementStringWellFormed(str)){
            str = start_Placements();
        }
        return str;
    }

    // FIXME Task 8: Implement starting placements

    // private void makeBoard(){
    // Random rn =new Random();
    /*
     *1)create an (linked)hashset of string ,such that each string is a piece placement data.
     *  use hashsets to prevent duplicates
     *  This hashset contains the data about those value which needs pieces to be created.
     *  if insertion order is crucial use linked type hashset
     *2)create a list of piece inclusive of pegs - alphas
     *  represent pegs as with literal and 0 added behind
     *  example: a0
     *  Create a rng value specifically for the no of pieces to be placed
     *  Create a string for the output
     *3)run for loop (cond index should be greater than 1 and must us rng with a bound of x - 4 )
     *   every iteration should check if such string exist
     *   in the list and whether it is valid or not.
     *  (if it exist then remove or else repeat for loop - counters updated)
     *  then remove it from the list .After that  add it to the output string
     *
     *4)create a case such that Starting must at least contain a single peg
     *  alter 4th rng to 0 just fr this case
     *
     *5) use rng to generate a random value
     *      for 1st char- use list.size as reference to get the bound value
     *                    then apply it to the list of char
     *               2nd-  standard bound value of 8
     *               3rd-  standard bound value of 3
     *               4th-  standard bound value of 7
     */

//    public static void main(String[] args) {
//       /* Board b = new Board();
//        b.makeBoard();*/
//        String str = valid_Placement();
//        int i = 0;
//        while ((t.isPlacementStringValid(str)) && i < 20){
//            System.out.println(true);
//            i ++;
//        }
//    }

    /*set opacity of selected pieces to a certain percentage  or
    use Blur effect for that certain piece (using setEffect) Use task 9 code for the solutions.*/
    // FIXME Task 10: Implement hints
    public static void hint() {
    }

    // FIXME Task 11: Generate interesting starting placements
    /*In reference to Difficulty level choose a certain state from Difficulty_level */
    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
