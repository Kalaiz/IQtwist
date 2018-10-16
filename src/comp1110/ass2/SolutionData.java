package comp1110.ass2;

import java.util.*;

public class SolutionData {
    private static List<Character> availPiece=new ArrayList<>();
    TwistGame game= new TwistGame();//For access to Task 9
    /*Using linked list as it has no size limit
    the string will be the encoding for two random pieces*/
    private static HashMap<String, LinkedList<String>> storage=new HashMap<>();
    private static HashMap<Integer,String> difficultyStorage=new HashMap<>();


    /**Gets a solution and places pegs where ever possible and makes all the other possible solutions*
     * @param solution - Complete solution string from task 9 codes
     */
    private static String pegAdder(String solution){
        Random rndp=new Random();
        return null;
    }

    /*Creates a board String such that it consist of 2 pieces
     * Produces the key for the Hashmap - storage*/
    private static String pieceCreator(){
        return null;
    }

    /** Adds the solution in accordance to the respective key of HashMap - storage
     *  If there is no solution , do not create the key(twoPieceBoard).
     * @param twoPieceBoard - The board state which has two random pieces.
     * */
    private static void solutionAdder(String twoPieceBoard){}


    /**Converts the available data into respective difficulty levels
     * Available Series:
     * 0-200 -- Level 1
     * 200-400 -- Level 2
     * 400-600 -- Level 3
     * Each number is the key for difficultyStorage and it will have a starting board string  as a value.
     */
    private static void difficultyStorageConvertor(){}



    public static void main(String[] args) {
        /*Where to store these data ?
        Options are :
        1)write it in a java class(not manually but using write operations)
        2)write it to a text file similar to the way above
        3)Dynamically:
             set a predefined number of twoPieceBoard  (considering the fact that it should run only once upon the start of game)
             - convert the available data in accordance to difficulty level
             - having a loading screen in the game  - meanwhile the solutions are stored .
             - Access accordingly.
         */

    }




}
