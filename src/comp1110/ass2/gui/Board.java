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

    public static String start_Placements(){
        int numofpiece ; //number of the pieces
        int numofr;
        int numofb;
        int numofg;
        int numofy ; //random number of different pegs
//
// int startlength = 4 * (numofpiece + numofr + numofb + numofg + numofy);

        do {
            Random rand1 = new Random();
            numofpiece = rand1.nextInt(2); //number of the pieces
            numofr = rand1.nextInt(2);
            numofb = rand1.nextInt(3);
            numofg = rand1.nextInt(3);
            numofy = rand1.nextInt(3); //random number of different pegs
        }while((numofr + numofb + numofg + numofy) < 1);

        int startlength = 4 * (numofpiece + numofr + numofb + numofg + numofy);

        Random rand = new Random();

        //the piece of the output String
        char[] piece = new char[4];
        piece[0] = (char) (rand.nextInt(8) + 97);
        piece[1] = (char) (rand.nextInt(8) + 49);
        piece[2] = (char) (rand.nextInt(4) + 65);
        piece[3] = (char) (rand.nextInt(8) + 48);
        String strp = new String(piece);

        //the pegs of the output String
        char[] rpeg = new char[4];
        rpeg[0] = 'i';
        rpeg[1] = (char) (rand.nextInt(8) + 49);
        rpeg[2] = (char) (rand.nextInt(4) + 65);
        rpeg[3] = '0';
        String strr = new String(rpeg);

        char[] bpeg1 = new char[4];
        bpeg1[0] = 'j';
        bpeg1[1] = (char) (rand.nextInt(8) + 49);
        bpeg1[2] = (char) (rand.nextInt(4) + 65);
        bpeg1[3] = '0';
        char[] bpeg2 = new char[4];
        bpeg2[0] = 'j';
        bpeg2[1] = (char) (rand.nextInt(8) + 49);
        bpeg2[2] = (char) (rand.nextInt(4) + 65);
        bpeg2[3] = '0';
        String strb1 = new String(bpeg1);
        String strb2 = new String(bpeg2);

        char[] gpeg1 = new char[4];
        gpeg1[0] = 'k';
        gpeg1[1] = (char) (rand.nextInt(8) + 49);
        gpeg1[2] = (char) (rand.nextInt(4) + 65);
        gpeg1[3] = '0';
        char[] gpeg2 = new char[4];
        gpeg2[0] = 'k';
        gpeg2[1] = (char) (rand.nextInt(8) + 49);
        gpeg2[2] = (char) (rand.nextInt(4) + 65);
        gpeg2[3] = '0';
        String strg1 = new String(gpeg1);
        String strg2 = new String(gpeg2);

        char[] ypeg1 = new char[4];
        ypeg1[0] = 'l';
        ypeg1[1] = (char) (rand.nextInt(8) + 49);
        ypeg1[2] = (char) (rand.nextInt(4) + 65);
        ypeg1[3] = '0';
        char[] ypeg2 = new char[4];
        ypeg2[0] = 'l';
        ypeg2[1] = (char) (rand.nextInt(8) + 49);
        ypeg2[2] = (char) (rand.nextInt(4) + 65);
        ypeg2[3] = '0';
        String stry1 = new String(ypeg1);
        String stry2 = new String(ypeg2);

        String str = "                                            ";
        StringBuilder str1 = new StringBuilder(str);

        if(numofpiece == 1){
            str1.insert(0, strp);
        }

        if(numofr == 1){
            str1.insert(4, strr);
        }

        if(numofb == 1){
            str1.insert(8, strb1);
        } else if(numofb == 2){
            str1.insert(8, strb1);
            str1.insert(12,strb2);
        }

        if(numofg == 1){
            str1.insert(8, strg1);
        } else if(numofg == 2){
            str1.insert(8, strg1);
            str1.insert(12,strg2);
        }

        if(numofy == 1){
            str1.insert(8, stry1);
        } else if(numofy == 2){
            str1.insert(8, stry1);
            str1.insert(12,stry2);
        }

        str = new String(str1);

        char[] charpla = str.toCharArray();
        char[] charnew = new char[startlength];

        int j = 0;
        for(int i = 0; i < charpla.length; i++){
            if(charpla[i] != ' '){
                charnew[j] = charpla[i];
                j ++;
            }
        }

        str = new String(charnew);

        return str;
    }

    public static String validStartPlacement(){
        String str = start_Placements();
        if (!t.isPlacementStringValid(str)) {
            str = start_Placements();
        }

        return str;
    }


    //private void rotate(){}//rotate pieces

    //Snap the pieces to nearest position (if it is over the respective position.)
    //private void snap_to_nearest_grid(){}

    // FIXME Task 8: Implement starting placements

    private void makeBoard(){
        Random rn =new Random();
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
    }

    public static void main(String[] args) {
       /* Board b = new Board();
        b.makeBoard();*/
        String str = validStartPlacement();
        System.out.println(t.isPlacementStringValid(str));
        System.out.println(str);
    }

    /*set opacity of selected pieces to a certain percentage  or
    use Blur effect for that certain piece (using setEffect) Use task 9 code for the solutions.*/
    // FIXME Task 10: Implement hints
    public static void hint(){
    }

    // FIXME Task 11: Generate interesting starting placements
    /*In reference to Difficulty level choose a certain state from Difficulty_level */
    @Override
    public void start(Stage primaryStage) throws Exception {

    }
 /* Task 8 Draft codes
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

    }*/





}



