package comp1110.ass2;

import java.util.*;

public class SolutionData extends TwistGame{

    private static List<String> sol = new ArrayList<>();
    private static List<Character> availPiece=new ArrayList<>();
    private static  HashMap<String, String> storage=new HashMap<>();
    /*String[0]=startingBoardPlacement; String[1]=Solution */
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


    private void updatePieceDetails(){

    }

    /*Creates a board String such that it consist of 2 pieces
     * Produces the key for the Hashmap - storage*/
    private static String pieceCreator(){

        Random r = new Random();
        /*String existpiece = "";     //The piece which we initially choose from the choosen string
        String unaddpiece = "";*/
        List<String> choPie = new ArrayList<>();
        List<String> choFro = new ArrayList<>();    //The pieces from which we may choose the next piece
        int ranSol = r.nextInt(4);
        //int ranPie = r.nextInt(8);

        String str = initiator[ranSol];
        choFro = getFormalPieces(str);
        /*existpiece = choFro.get(ranPie);
        unaddpiece = choFro.get(ranPie);
        choPie.add(existpiece);*/
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

   /* private static List<String> addPiece(List<String> choPie, String unaddPiece, List<String> choFro){

        Random r = new Random();
        int ranPie = r.nextInt(8);

        if (choPie.get(0) != unaddPiece) {
            choPie.add(unaddPiece);
        } else {
            unaddPiece = choFro.get(ranPie);
            addPiece(choPie,unaddPiece,choFro);
        }

        return choPie;
    }*/


   /* private static void solutionAdder(String twoPieceBoard){
        String[] str = getSolutions(twoPieceBoard);
        for (int i = 0; i < str.length; i++){
            if(!(storage.size()>25)){
                storage.put(str[i],pegAdder(str[i]));
            }
            else{
                break;
            }
        }

    }*/

   /*gives the difficulty level and a random solution string, find the values of pegs in hashMap*/
    private static String difficultyLevel(int level, String[] solution){

        Random r = new Random();
        String startPlacement = "";
        int pieceNum = 0;
        int pegNum = 0;
        int solNum = r.nextInt(sol.size());
        String pegs = pegAdder(solution[solNum]);

        //get the pieces & pegs number by difficulty level
        pegNum = difficultyLevelDetails[2 * level];
        pieceNum = difficultyLevelDetails[2*level + 1];

        List<String> pie = getFormalPieces(solution[solNum]);
        List<String> peg = getFormalPieces(pegs);

        Collections.shuffle(pie);
        Collections.shuffle(peg);

        for (int i = 0; i < pieceNum; i++) {
            startPlacement += pie.get(i);
        }

        for (int j = 0; j < pegNum; j++) {
            startPlacement += peg.get(j);
        }

        return startPlacement;

    }


    // FIXME Task 11: Generate interesting starting placements
    /*
     * 3 difficulty levels:
     * 1: Place 6 or 7 pegs on board which may lead to a single solution, because pegs limited the color of pieces and may have fixed solutions
     * 2: Place 5 or pegs on board which may have more possibilities but lead to a single solution
     * 3: Place 3 or 4 pegs on board which have the most possibilities but lead to the single answer
     * pegnos,piecenos,pegnos,piecenos...
     * {6,4,5,3,4,2};
    /*In reference to Difficulty level choose a certain state from SolutionData */
    /**Converts the available data into respective difficulty levels
     * Available Series:
     * 0-25 -- Level 1
     * 25-50 -- Level 2
     * 50-75 -- Level 3
     * Each number is the key for difficultyStorage and it will have a starting board string  as a value.
     */
/*    private static void difficultyStorageConverter(){
        Random rnd=new Random();
        int difficultyRnd;
        int keynumbase=0;
        String startingplacement="";
        for(int i=0;i<storage.size();i++){
            String solution=(storage.get(i));
            List<String> listOfPieces = getFormalPieces(solution);
            List<String> listOfPegs = getFormalPieces(pegAdder(solution));
            for(int dlo=0;dlo<3;dlo++){
                List<String> listOfPiecesCopy=listOfPieces;
                List<String> listOfPegsCopy =listOfPegs;

                difficultyRnd=rnd.nextInt(1);
                int numofpieces=difficultyLevelDetails[(dlo*2)+1];
                int numofpegs=difficultyLevelDetails[dlo*2];
                int rndPiece;
                int rndPegs;
                for(int pieceCollater=0;pieceCollater<numofpieces+difficultyRnd+1;pieceCollater++){
                    if(listOfPiecesCopy.size()==0){break;}
                    rndPiece=rnd.nextInt(listOfPiecesCopy.size());
                    startingplacement+=listOfPiecesCopy.get(rndPiece);
                    listOfPiecesCopy.remove(rndPiece);
                }
                for(int pegCollator =0; pegCollator<numofpegs+difficultyRnd+1;pegCollator++){
                    if(listOfPegsCopy.size()==0){break;}
                    rndPegs= rnd.nextInt(listOfPegsCopy.size());
                    startingplacement+=listOfPegsCopy.get(rndPegs);
                    listOfPegsCopy.remove(rndPegs);
                }
                String[] solstart={solution,startingplacement};
                difficultyStorage.put((25*difficultyRnd)+(keynumbase),solstart);
                if(dlo==(difficultyLevelDetails.length/2 )-1){
                    keynumbase++;
                    startingplacement="";
                }

                listOfPegsCopy = null;
                listOfPegs = null;
            }
        }
    }*/



    public static void main(String[] args) {

        System.out.println(pieceCreator());;
        String[] s = new String[sol.size()];
        //pegAdder();
        for (int i = 0; i < s.length; i++) {
            s[i] = sol.get(i);
        }
        String p = difficultyLevel(2,s);
        System.out.println(p);
  /*      Pieces.initialisehms();


       *//* while(storage.size()!=25){
            solutionAdder(pieceCreator());
        }*//*
        int c=0;

        //difficultyStorageConverter();
        String piece=pieceCreator();
        System.out.println(piece);
        solutionAdder(piece);
        difficultyStorageConverter();
        for(int i=0;i<difficultyStorage.size();i++){
            System.out.println(c++ +  " " + difficultyStorage.get(i)[1]);
        }*/

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
