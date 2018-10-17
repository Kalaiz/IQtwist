package comp1110.ass2;

import java.util.*;

public class SolutionData extends TwistGame{


    private static List<Character> availPiece=new ArrayList<>();
    private static  List<String> storage=new ArrayList<>(25);
    /*String[0]=startingBoardPlacement; String[1]=Solution */
    private static HashMap<Integer,String[]> difficultyStorage=new HashMap<>();
    private static String[] solutions = {"a6B0b6C0c5A2d1B5e4A5f4C2g2B5h1A2",
                                 "a1A6b1B1c2D0d3B6e7A3f7B1g5B7h4A0",
                                 "a7A7b3B1c1A0d5A3e1C2f1B0g6B7h4D0",
                                 "a1C6b6A4c2D0d7B1e1A3f2A2g4B2h4A0"};
    private static int[] pegDetails={1,2,2,2};
                                              /*pegnos,piecenos,pegnos,piecenos...*/
    private static int[] difficultyLevelDetails={6,4,5,3,4,2};



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

        Random r = new Random();
        String existpiece = "";     //The piece which we initially choose from the choosen string
        String unaddpiece = "";
        List<String> choPie = new ArrayList<>();
        List<String> choFro = new ArrayList<>();    //The pieces from which we may choose the next piece
        int ranSol = r.nextInt(4);
        int ranPie = r.nextInt(8);

        String str = solutions[ranSol];
        choFro = getFormalPieces(str);
        existpiece = choFro.get(ranPie);
        unaddpiece = choFro.get(ranPie);
        choPie.add(existpiece);
        choPie = addPiece(choPie,unaddpiece,choFro);

        StringBuilder sb = new StringBuilder();
        for (Object c : choPie){
            sb.append(c);
        }
        //System.out.println(sb.toString());

        return sb.toString();
    }

    private static List<String> addPiece(List<String> choPie, String unaddPiece, List<String> choFro){

        Random r = new Random();
        int ranPie = r.nextInt(8);

        if (choPie.get(0) != unaddPiece) {
            choPie.add(unaddPiece);
        } else {
            unaddPiece = choFro.get(ranPie);
            addPiece(choPie,unaddPiece,choFro);
        }

        return choPie;
    }

    /** Adds the solution in accordance to the respective key of ArrayList - storage
     *
     * @param twoPieceBoard - The board state which has two random pieces.
     * */
    private static void solutionAdder(String twoPieceBoard){
        String[] str = getSolutions(twoPieceBoard);
        for (int i = 0; i < str.length; i++){
            if(!(storage.size()>25)){
            storage.add(str[i]);}
            else{
                break;
            }
        }
    }



    // FIXME Task 11: Generate interesting starting placements
    /*
     * 3 difficulty levels:
     * 1: Place 6 or 7 pegs on board which may lead to a single solution, because pegs limited the color of pieces and may have fixed solutions
     * 2: Place 5 or pegs on board which may have more possibilities but lead to a single solution
     * 3: Place 3 or 、4 pegs on board which have the most possibilities but lead to the single answer
     * */
    /*In reference to Difficulty level choose a certain state from SolutionData */
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
