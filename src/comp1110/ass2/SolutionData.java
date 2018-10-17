package comp1110.ass2;

import java.util.*;

public class SolutionData extends TwistGame{
    // FIXME Task 11: Generate interesting starting placements
    /*
     * 3 difficulty levels:
     * 1: Place 6 or 7 pegs /4  pieces on board which may lead to a single solution, because pegs limited the color of pieces and may have fixed solutions
     * 2: Place 5 or 6 pegs /3 pieces on board which may have more possibilities but lead to a single solution
     * 3: Place 4 or 5 pegs /2 pieces  on board which have the most possibilities but lead to the single answer
     * */
    /*In reference to Difficulty level choose a certain state from SolutionData */

    private static List<Character> availPiece=new ArrayList<>();
    TwistGame game= new TwistGame();//For access to Task 9
    /*Using linked list as it has no size limit
    the string will be the encoding for two random pieces*/
    private static List<String> storage=new ArrayList<>(25);
    /*String[0]=startingBoardPlacement String[1]=Solution */
    private static HashMap<Integer,String[]> difficultyStorage=new HashMap<>();
    private static int[]pegDetails={1,2,2,2};
    private static int[][] difficultyLevelDetails={{7,4},{6,3},{5,2}};



    /**Gets a solution and places pegs where ever possible and returns a board State string filled with pegs*
     * @param solution - Complete solution string from task 9 codes
     */
    private static String pegAdder(String solution){
      boardcreator(solution,'a');
      String[][] board = gobj.getaboard();
      for(int row=0;row<board.length;row++){
          for(int col =0;col<board[0].length;col++){
                 if(board[row][col].startsWith("o")){
                     int pgno=0;
                    switch(board[row][col]){
                        case "or":
                          break;
                        case "ob":
                           pgno=1;
                            break;
                        case "og":
                           pgno=2;
                            break;
                        case "oy":
                            pgno=3;
                    }
                     if(pegDetails[pgno]>0){
                         solution+=((char)(105+pgno))+Integer.toString((col+1))+((char)(row+65))+"0";
                         --pegDetails[pgno];
                     }}
          }
      }
        return solution;
    }


    private void updatePieceDetails(){

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
     * 0-25 -- Level 1
     * 25-50 -- Level 2
     * 50-75 -- Level 3
     * Each number is the key for difficultyStorage and it will have a starting board string  as a value.
     */
    private static void difficultyStorageConverter(){
        Random rnd=new Random();
        for(int i=0;i<storage.size();i++){
            String completeSolution=pegAdder(storage.get(i));
            for(int dlo=0;dlo<difficultyLevelDetails.length;dlo++){
               for(int dloi=0;dloi<difficultyLevelDetails[0].length;dloi++){

               }
            }
        }



    }



    public static void main(String[] args) {
        Pieces.initialisehms();
        //System.out.println(pegAdder("a7A7b3B1c1A0d5A3e1C2f1B0g6B7h4D0")); ;
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
