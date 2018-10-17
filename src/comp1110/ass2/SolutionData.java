package comp1110.ass2;

import java.util.*;

public class SolutionData extends TwistGame{
    // FIXME Task 11: Generate interesting starting placements
    /*
     * 3 difficulty levels:
     * 1: Place 6 、7 pegs on board which may lead to a single solution, because pegs limited the color of pieces and may have fixed solutions
     * 2: Place 5 pegs on board which may have more possibilities but lead to a single solution
     * 3: Place 3 、4 pegs on borad which have the most possibilities but lead to the single answer
     * */
    /*In reference to Difficulty level choose a certain state from SolutionData */

    private static List<Character> availPiece=new ArrayList<>();
    TwistGame game= new TwistGame();//For access to Task 9
    /*Using linked list as it has no size limit
    the string will be the encoding for two random pieces*/
    private static  List<String> storage=new ArrayList<>(25);
    public static HashMap<Integer,String[]> difficultyStorage=new HashMap<>();
    static String[] solutions = {"ba6B06C0c5A2d1B5e4A5f4C2g2B5h1A2",
                                 "a1A6b1B1c2D0d3B6e7A3f7B1g5B7h4A0",
                                 "a7A7b3B1c1A0d5A3e1C2f1B0g6B7h4D0",
                                 "a1C6b6A4c2D0d7B1e1A3f2A2g4B2h4A0"};


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
            storage.add(str[i]);
        }
    }


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
