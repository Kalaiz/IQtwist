package comp1110.ass2;

import java.util.*;

public class StartingBoard extends TwistGame{

    private static List<String> sol = new ArrayList<>();
    private static  HashMap<String, String> storage=new HashMap<>();
    public static HashMap<Integer,String[]> difficultyStorage=new HashMap<>();
    private static String[] initiator = {"a6B0b6C0c5A2d1B5e4A5f4C2g2B5h1A2",
                                         "a1A6b1B1c2D0d3B6e7A3f7B1g5B7h4A0",
                                         "a7A7b3B1c1A0d5A3e1C2f1B0g6B7h4D0",
                                         "a1C6b6A4c2D0d7B1e1A3f2A2g4B2h4A0"};
    private static final int[] pegDetails={1, 2, 2, 2};
    /*pgnos,piecenos,pgnos,piecenos...*/
    private static final int[] difficultyLevelDetails={6,4,5,3,4,2};


    /**Gets all pegs where ever possible and returns a board State string filled with pegs*
     * @param solution - Complete solution string from task 9 codes
     */
    private static String pegAdder(String solution){
        boardcreator(solution,'a');
        String[][] board = gobj.getaboard();
        String pegs="";
        int[]pegD =pegDetails.clone();
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
                    if(pegD[pgno]>0){
                        pegs+=((char)(105+pgno))+Integer.toString((col+1))+((char)(row+65))+"0";
                        --pegD[pgno];
                    }}
            }
        }
        storage.put(solution,pegs);
        return pegs;
    }
    /* TO initialise the storage at the start of the game */
    private void initialiseStorage(){
        pieceCreator();

    }



    /*Creates a board String such that it consist of 2 pieces
     * Produces the key for the Hashmap - storage*/
    private static String pieceCreator(){

        Random r = new Random();
        List<String> choPie = new ArrayList<>();
        List<String> choFro = new ArrayList<>();    //The pieces from which we may choose the next piece
        int ranSol = r.nextInt(4);

        String str = initiator[ranSol];
        choFro = getFormalPieces(str);
        Collections.shuffle(choFro);
        choPie.add(choFro.get(0));
        choPie.add(choFro.get(1));

        StringBuilder sb = new StringBuilder();
        for (Object c : choPie){
            sb.append(c);
        }
        System.out.println(sb.toString());
        String[] s = getSolutions(sb.toString());
        for (int i = 0; i < s.length; i++) {
            sol.add(s[i]);
        }

        return sb.toString();
    }


   /*gives the difficulty level and a random solution string, find the values of pegs in hashMap*/
   // FIXME Task 11: The output must be sorted so the get 1 solution from getSolutions
    private static String difficultyLevel(int level, String[] solution){

        Random r = new Random();
        int ndv=r.nextInt(2);//ndv-non-deterministic value
        String startPlacement = "";
        int pieceNum ;
        int pegNum ;
        int solNum = r.nextInt(sol.size());
        String pegs = pegAdder(solution[solNum]);

        //get the pieces & pegs number by difficulty level
        pegNum = difficultyLevelDetails[2 * level];
        pieceNum = difficultyLevelDetails[2 * level + 1];

        List<String> pie = getFormalPieces(solution[solNum]);
        List<String> peg = getFormalPieces(pegs);

        Collections.shuffle(pie);
        Collections.shuffle(peg);

        for (int i = 0; i < pieceNum+ndv; i++) {
            startPlacement += pie.get(i);
        }

        for (int j = 0; j < pegNum+ndv; j++) {
            startPlacement += peg.get(j);
        }

        return startPlacement;

    }



    /*
     * 3 difficulty levels:
     * 1: Place 6 or 7 pegs on board which may lead to a single solution, because pegs limited the color of pieces and may have fixed solutions
     * 2: Place 5 or pegs on board which may have more possibilities but lead to a single solution
     * 3: Place 3 or 4 pegs on board which have the most possibilities but lead to the single answer
     * pegnos,piecenos,pegnos,piecenos...
     * {6,4,5,3,4,2};
    /*In reference to Difficulty level choose a certain state from StartingBoard */

    public static void main(String[] args) {
  /*      Pieces.initialisehms();
        System.out.println(getSolutions("b6C0c5A0h1A0i6B0j4C0j6A0l1A0l4A0").length);*/
        for (int m = 0; m < 10; m++) {
            System.out.println(pieceCreator());
            ;
            String[] s = new String[sol.size()];
            //pegAdder();
            for (int i = 0; i < s.length; i++) {
                s[i] = sol.get(i);
            }
            String p = difficultyLevel(0, s);
            System.out.println(p);

        }
    }

}
