package comp1110.ass2;

import comp1110.ass2.gui.Viewer;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static comp1110.ass2.Pieces.hm;
import static comp1110.ass2.Pieces.initialisehms;

/**
 * This class provides the text interface for the Twist Game
 * <p>
 * The game is based directly on Smart Games' IQ-Twist game
 * (http://www.smartgames.eu/en/smartgames/iq-twist)
 */
public class TwistGame {
    static GameBoard gobj = new GameBoard();
    static List<String> fakeset=new ArrayList<>(10000000);
    static final  int[] containerSpecs={3,2,2,3,3,3,4,1,1,4};//rcrcrc.. where r represents row and c represents column
    static int[][] ppContainer=new int[5][];//Jagged array for task 6
   static int[][] ppContainer2 = new int[5][];
   static final int[] bWeakSymmetricpair={0,2,1,3,4,6,5,7};
   static final int[] eWeakSymmetricpair={0,7,1,4,2,5,3,6};
   static final int[] fWeakSymmetricpair={0,6,1,7,2,4,3,5};
   static int testval=0;
   static  int totalmethodcalls=0;




    /**
     * Determine whether a piece or peg placement is well-formed according to the following:
     * - it consists of exactly four characters
     * - the first character is in the range a .. l (pieces and pegs)
     * - the second character is in the range 1 .. 8 (columns)
     * - the third character is in the range A .. D (rows)
     * - the fourth character is in the range 0 .. 7 (if a piece) or is 0 (if a peg)
     *
     * @param piecePlacement A string describing a single piece or peg placement
     * @return True if the placement is well-formed
     * Authorship:Kalai
     */

    public static boolean isPlacementWellFormed(String piecePlacement) {
        char[] data = {'a', 'l', '1', '8', 'A', 'D', '0', '7'};
    /*For the fourth character;In situation where given input represents a peg,
    then the 4th character must be 0. It also ensures the input has a length of 4.*/
        if ((piecePlacement.charAt(0) >= 'i' && piecePlacement.charAt(3) != '0') || piecePlacement.length() != 4) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            //Characters compared in accordance to ascii encoding values
            if (!(piecePlacement.charAt(i) >= data[i * 2] && piecePlacement.charAt(i) <= data[i * 2 + 1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N four-character piece placements (where N = 1 .. 15);
     * - each piece or peg placement is well-formed
     * - each piece or peg placement occurs in the correct alphabetical order (duplicate pegs can be in either order)
     * - no piece or red peg appears more than once in the placement
     * - no green, blue or yellow peg appears more than twice in the placement
     *
     * @param placement A string describing a placement of one or more pieces and pegs
     * @return True if the placement is well-formed
     * author: Lingyu Xia
     */
    public static boolean isPlacementStringWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement is well-formed
        String[] items = new String[placement.length() / 4];                      //Using a loop to store all four-character placements into a String[]
        int countRed = 0;
        int countGre = 0;
        int countBlu = 0;
        int countYel = 0;

        if (placement.length() % 4 != 0 || placement == null || placement == "") {        //Check if the length of the placement String is valid and the content of the string

            return false;

        } else {

            for (int i = 0; i < placement.length() / 4; i++) {

                items[i] = placement.substring(4 * i, 4 * i + 4);

                if (isPlacementWellFormed(items[i]) != true)

                    return false;

            }

            if (placement.length() <= 32 && placement.length() > 4) {

                for (int j = 0; j < placement.length() / 4 - 1; j++) {

                    if (items[j].charAt(0) >= items[j + 1].charAt(0)) {

                        return false;

                    }

                }

            } else if (placement.length() >= 32) {
                for (int j = 1; j < 8; j++) {                                          //Second judge if the pieces occur in the correct alphabetical order or have duplicated

                    if (items[j - 1].charAt(0) >= items[j].charAt(0)) {
                        return false;
                    }

                }
            }


            for (int x = 0; x < placement.length(); x++) {                         //Last judge if pegs are outnumbered

                if (placement.charAt(x) == 'i')
                    countRed++;
                else if (placement.charAt(x) == 'j')
                    countBlu++;
                else if (placement.charAt(x) == 'k')
                    countGre++;
                else if (placement.charAt(x) == 'l')
                    countYel++;

            }

            if (countRed > 1 || countBlu > 2 || countGre > 2 || countYel > 2) {

                return false;

            }

        }

        return true;

    }


    /**
     * Checks if any pieces is in the outerboard
     *
     * o o o o o o o o o o o o o o
     * o o o o o o o o o o o o o o
     * o o o o o o o o o o o o o o
     * o o o I I I I I I I I o o o
     * o o o I I I I I I I I o o o
     * o o o I I I I I I I I o o o
     * o o o I I I I I I I I o o o
     * o o o o o o o o o o o o o o
     * o o o o o o o o o o o o o o
     * o o o o o o o o o o o o o o
     *
     * 'o' fields represent the outer board
     * meanwhile 'I' fields represent the inner board
     *
     * @param board2 Modified board
     * @return True if there is a piece  on the outerboard
     * Authorship:Kalai
     */
    private static boolean checkboard(String[][] board2) {//check if  pieces are in the outer board
        int row = board2.length;
        int col = board2[0].length;
        for (int cr = 0; cr < row; cr++) {
            for (int cc = 0; cc < col; cc++) {
                if ((cr < 3 || cr > 6) && !board2[cr][cc].equals("x") ) {
                    return true;
                } else if ((cc < 3 || cc > 10) && !board2[cr][cc].equals("x")) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     *check if there is overlap or badpegs on the checkingboard
     * according to the length of the String element
     * and each char value of the String element
     * Authorship: Yuqing Zhang
     */
    private static boolean checkBoard2() {//checks for colourpeg and overlap
        for (int row = 3; row < 7; row++) {
            for (int col = 3; col < 11; col++) {
                String ccs = gobj.getcboard()[row][col];//ccs-Current checking piece string
                if (ccs.length() == 4) {
                    if (ccs.charAt(1) != ccs.charAt(3)) {//pror (or)  orpr
                        return false;
                    } else if (ccs.charAt(0) == ccs.charAt(2)) {// ogog case
                        return false;
                    }
                } else if (ccs.length() == 3) {
                    return false;
                } else if ((ccs.length() == 2)) {
                    //shouldnt start with any character other than o or p
                    if (!((ccs.charAt(0) == 'p') || (ccs.charAt(0) == 'o'))) {
                        return false;
                    }
                } else if (ccs.length() > 4) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Modifies respective board based on the placement string
     * and checks whether place is valid or not concurrently
     *
     * @param placement details of the pieces
     * @param bt        describes the board type
     *                  actualboard (4x8 board)
     *                  checkingboard (10x12 board specifically for is_onboard and isvalidPlacement )
     * @return Updated board
     * Authorship:Kalai
     * local:temp used as the initial value for is_onboard or else will get updated as per requirement and return itself
     */
    public static String[][] boardcreator(String placement, char bt) {
        String[][] temp = {{"z"}};//For task5
        gobj.resetBoardvalues(Character.toString(bt));//resets the respective board
        for (int i = 0; i < placement.length() / 4; i++) {
            String ch = placement.substring(4 * i, 4 * i + 4);
            if (bt == 'a') {
                gobj.pieceTobeAdded(ch, "a");
            } else {
                gobj.pieceTobeAdded(ch, "c");
                if (checkboard(gobj.getcboard()) || !checkBoard2()) {
                    return temp;
                }
            }
        }
        temp = (bt == 'a') ? gobj.getaboard() : gobj.getcboard();
        return temp;
    }

    /**
     * Determine whether a placement string is valid.  To be valid, the placement
     * string must be well-formed and each piece placement must be a valid placement
     * according to the rules of the game.
     * - pieces must be entirely on the board
     * - pieces must not overlap each other
     * - pieces may only overlap pegs when the a) peg is of the same color and b) the
     * point of overlap in the piece is a hole.
     *
     * @param placement A placement sequence string
     * @return True if the placement sequence is valid
     * Authorship: Yuqing Zhang & Kalai
     */
    public static boolean isPlacementStringValid(String placement) {
        Pieces.initialisehms();
        if (boardcreator(placement, 'c')[0][0].equals("z")) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {

    }
    /**
     * Given a string describing a placement of pieces and pegs, return a set
     * of all possible next viable piece placements.   To be viable, a piece
     * placement must be a valid placement of a single piece.  The piece must
     * not have already been placed (ie not already in the placement string),
     * and its placement must be valid.   If there are no valid piece placements
     * for the given placement string, return null.
     * <p>
     * When symmetric placements of the same piece are viable, only the placement
     * with the lowest rotation should be included in the set.
     *
     * @param placement A valid placement string (comprised of peg and piece placements)
     * @return An set of viable piece placements, or null if there are none.
     * author: Lingyu Xia
     */
    public static Set<String> lgetViablePiecePlacements(String placement) //Take note that this does not check if board is valid or not
    {
        // FIXME Task 6: determine the set of valid next piece placements
        Set<String> viablePiece = new HashSet();
        Pieces.initialisehms();
        int methodcallctr=0;
        Viewer v = new Viewer();
        String placed_pieces = v.returner(placement,0);
        String unplaced_pieces = "";
        boardcreator(placement,'a');//Creates* an  actualboard
        for (int i = 'a' ; i <= 'h' ; i++){
            if(placed_pieces.indexOf(String.valueOf((char)i))==-1)
                unplaced_pieces = unplaced_pieces + String.valueOf((char)i);
        }
        //System.out.print(unplaced_pieces);
        //System.out.println();
        List<int[]> emptyGrid = getEmptyGrid();
        String newPiece = "";
        String newPlacement = "";

        loop: for (int i = 0; i < unplaced_pieces.length(); i++){

            for (int j = 0; j < emptyGrid.size(); j++){

                for (int l = 'A'; l <= 'D'; l++) {

                    for (int k = 0; k < 8; k++) {

                        if (unplaced_pieces.charAt(i) >= 'i') {
                            break;
                        } else {
                            newPiece = String.valueOf(unplaced_pieces.charAt(i)) + emptyGrid.get(j)[1] + String.valueOf((char)l) + k;
                            newPlacement = placement + newPiece;
                        }

                        //System.out.println(placement + newPiece);
                        methodcallctr++;
                        if (isPlacementStringValid(newPlacement)) {
                            //System.out.println(newPlacement);
                            viablePiece.add(newPiece);
                            //if (unplaced_pieces.charAt(i) == 'c' || unplaced_pieces.charAt(i) == 'h' || unplaced_pieces.charAt(i) == 'f')
                            if (unplaced_pieces.charAt(i) != 'a')
                                continue loop;
                        }
                    }
                }
            }
        }


        if (viablePiece.isEmpty())
            return null;
        totalmethodcalls+=methodcallctr;

        System.out.println("Test" + (++testval) +" calls Task 5  " + Integer.toString(methodcallctr)+ " Times. Total Average is : " +( totalmethodcalls/testval) );
        return viablePiece;
    }


    public static Set<String> getViablePiecePlacements(String placement) {//Take note that this does not check if board is valid or not
        Set<String> viablePiece = new HashSet();
        fakeset.clear();
        Pieces.initialisehms();//initialise hashmap just for the sake of task tests
        boardcreator(placement,'a');//creates a board in accordance to the placement string
        int[][] ppContainer =  new int[5][];
        int ctr=0;
        Viewer access=new Viewer();
        String unplaced ="";
        String nonAvailcharpieces=access.returner(placement,0);
        //will give an int array of numbers which represent ascii encodings
        int[] output=IntStream.rangeClosed(97, 104).filter(i-> !nonAvailcharpieces.contains((char)i+"")).parallel().toArray();
        for(int i:output){ unplaced+=(Character.toString((char)i)); }//converting to String- not necessary
        List<Integer> containerscanbeused=listofcontainersused(unplaced);
        ppContainer=updateppcandnew(nonAvailcharpieces,initialiseContainersSpecs(ppContainer));
        ctr+=2;


        //update ppcontainer value in accordance to unplaced
        //find range of the containers in accordance to the emptyindices
        int minx= getEmptyGrid2('x')[0];
        int maxx= getEmptyGrid2('x')[getEmptyGrid2('x').length-1];
        int miny= getEmptyGrid2('y')[0];
        int maxy= getEmptyGrid2('y')[getEmptyGrid2('y').length-1];
        for(int cno:containerscanbeused) {
            for (int mx=minx; mx < maxx + 1; mx++) {
                for (int my=miny; my < maxy + 1; my++) {
                    if(mx+containerSpecs[2*cno]-1<maxx+1 || my+containerSpecs[(2*cno)+1]-1<maxy+1){//if the row are same? minx = maxx hence no &&
                        char row=(char)(65+mx);
                        String col=Integer.toString(my+1);//string col starts from 1
                        {
                            for (int pno : ppContainer[cno]) {
                                if((pno>31&&pno<40)||(pno>55&&pno<60)){
                                    for(int ix=mx;ix<mx+containerSpecs[2*cno];ix++){//removed -1 from bound
                                        for(int iy=my;iy<my+containerSpecs[2*cno+1];iy++){
                                            String piece=(char) ((pno/8)+97)+""+col+row+Integer.toString(pno-((pno/8)*8));
                                            if(isPlacementStringValid(placement+piece) && isPlacementWellFormed(piece)){
                                                ctr++;
                                              insertinfakeset(piece);
                                            }
                                        }
                                    }//within the container change the  piece e and h placements
                                }

                                else {
                                    String piece = (char) ((pno / 8) + 97) + "" + col + row + Integer.toString(pno - ((pno / 8) * 8));

                                    if (isPlacementStringValid(placement + piece)) {
                                       insertinfakeset(piece);
                                       ctr++;
                                    }
                                    //generate piece data  accordingly to check with isValidPlacement  and then check
                                    //if valid add to viablePiece set
                                }
                            }
                        }
                    }
                }
            }
        }

        if(fakeset.size()==0)
        { return null;}

            viablePiece.addAll(fakeset);
        totalmethodcalls+=ctr;

        System.out.println("Test" + (++testval) +" calls helper functions (inclusive of task5) " + Integer.toString(ctr)+ " Times. Total Average is : " +( totalmethodcalls/testval) );
        return viablePiece;
    }


    static void insertinfakeset(String piece) {
        if (fakeset.size() == 0) {
            fakeset.add(piece);
        } else {
         insertloop:   for (int i = 0; i < fakeset.size(); i++) {
                if (piece.substring(0, 2).equals(fakeset.get(i).substring(0, 2)) && !(piece.startsWith("a") || piece.startsWith("d") || piece.startsWith("g"))) {
                   int piecer=Integer.parseInt(piece.substring(3));
                   int alrsetpr=Integer.parseInt(fakeset.get(i).substring(3));
                    if (piece.equals(fakeset.get(i))) {

                    }

                    else  {
                        int[] temparr=new int[8];
                        switch(piece.charAt(0)){
                            case 'c': case'h':
                                if (alrsetpr-piecer==2){
                                    fakeset.set(i, piece);
                                    continue insertloop;
                                }
                            case 'b':
                                temparr= bWeakSymmetricpair;
                                break;

                            case 'f':
                               temparr=fWeakSymmetricpair;
                               break;

                            case 'e':
                                temparr=eWeakSymmetricpair;
                                break;
                        }
                        for(int t=0;t<4;t++){
                            if(temparr[t*2]==piecer&&temparr[(2*t)+1]==alrsetpr){
                                fakeset.set(i, piece);
                                continue insertloop;
                            }
                            else if(temparr[t*2]==alrsetpr&&temparr[(2*t)+1]==piecer) {
                                if(fakeset.contains(piece.substring(0,3)+Integer.toString(piecer))){
                                    fakeset.remove(piece.substring(0,3)+Integer.toString(piecer));
                                    continue insertloop;
                                }
                                else if (i == fakeset.size() - 1) {
                                    break insertloop;
                                } else {
                                    continue insertloop;
                                }
                            }
                        }
                        fakeset.add(piece);
                      if(i+1==fakeset.size()){
                          break ;
                      }
                    }
                }
                else if (i == fakeset.size() - 1) {//if no other piece in the fakeset is similar
                    fakeset.add(piece);
                    break;
                }
            }
        }
    }



    static int[][] updateppcandnew(String shouldnotbeonrefdata,int[][] ppContainer){
        for(int c=0;c<shouldnotbeonrefdata.length();c++){
            if(shouldnotbeonrefdata.charAt(c)>='i'){continue;}

            int startnum=(shouldnotbeonrefdata.charAt(c)-97) * 8;
            int endnum=startnum+7;
            for(int i=0;i<5;i++){
                ppContainer[i]=Arrays.stream(ppContainer[i]).filter(no->!(no>=startnum &&no<=endnum)).toArray();
            }
        }
        return ppContainer;
    }


    static  List<Integer> listofcontainersused(String availpieces){
        List<Integer> ap = new ArrayList();
        if(availpieces.contains("g")){
            ap.add(2);
        }
        if(availpieces.contains("c")){
            ap.add(3);
            ap.add(4);
        }
        {
            ap.add(0);
            ap.add(1);
        }
        return ap;}


    /**Initialise the possible piece container values  which will be used in task6
     * for example
     *  for this container
     *          X X
     *          X X
     *          X X
     *
     *    ppContainer[0][]=[0,2,4,6,8,10,12,14,24,26,28,30,(32 to 39 inclusive),40,42,44,46,56,58]
     *    ppContainer[1][]=[1,3,5,7,9,11,13,15,25,27,29,31,(32 to 39 inclusive),41,43,45,47,57,59]
     *    ppContainer[2][]=[48 to 55]
     *    ppContainer[3][]=[17,19]
     *    ppContainer[4][]=[16,18]
     *
     *    Related to existing hashmap: add 10 to the key value to obtain such numbers
     *
     *   Containers
     *
     *   0)         1)          2)         3)       4)
     *    X X         X X X       X X X       X       X X X X
     *    X X         X X X       X X X       X
     *    X X                     X X X       X
     *                                        X
     * */

    static int[][] initialiseContainersSpecs(int[][] ppContainer){

        for(int i=0;i<5;i++){
            List<Integer> cs= new ArrayList<>();
            for(int t=0;t<60;t++){
                if(t>19&&t<24){//neglecting strong symmetric pieces which are redundant
                    // do nothing
                }
                else if( hm.get(t).length<=containerSpecs[i*2] && hm.get(t)[0].length<=containerSpecs[i*2+1]){
                    if((i==3||i==4)&&(t>55&&t<60)){
                        continue;
                    }
                    else if(i==2&&!(t>47&&t<56)){
                        continue;
                    }
                    cs.add(t); } }
            int oarr[]= cs.stream().mapToInt(no->no).toArray();
            //ppContainer[i] =new int[oarr.length];   //not necessary
            ppContainer[i]=oarr;
        }
        return ppContainer;
    }



    /** Gives the indices of the empty grids
     *
     * @return list of required indices
     *  author: Kalai
     */
    public static int[] getEmptyGrid2(char coordinate) {
        List<Integer> emptyGridcoord= new ArrayList<>();
        int[][] egxy= new int[2][];
        for (int x = 0; x < 4; x++){
            for(int y = 0; y < 8; y++){
                if (gobj.getaboard()[x][y].equals("x")|| gobj.getaboard()[x][y].contains("p")&&gobj.getaboard()[x][y].length()==2){
                    if(coordinate=='x'){emptyGridcoord.add(x);}
                    else{emptyGridcoord.add(y);} } } }
        int[] sortcoord=emptyGridcoord.stream().mapToInt(x->x).toArray();
        Arrays.sort(sortcoord);
        return sortcoord;
    }


    /** Gives the indices of the empty grids
     *
     * @return list of required indices
     *  author: Lingyu Xia
     */
    public static List<int[]> getEmptyGrid() {
        List<int[]> emptyGrid = new ArrayList<>();
        for (int x = 0; x < 4; x++){
            for(int y = 0; y < 8; y++){
                if (gobj.getaboard()[x][y]=="x" || gobj.getaboard()[x][y]=="pr" || gobj.getaboard()[x][y]=="pb"
                        || gobj.getaboard()[x][y]=="pg" || gobj.getaboard()[x][y]=="py"){

                    int[] gridIndex = new int[2];
                    gridIndex[0] = x;
                    gridIndex[1] = y + 1;
                    emptyGrid.add(gridIndex);
                    //System.out.print(x);
                    //System.out.println(y);
                }
            }
        }

        return emptyGrid;
    }
    /**
     * Return an array of all unique solutions for a given starting placement.
     *
     * Each solution should be a 32-character string giving the placement sequence
     * of all eight pieces, given the starting placement.
     *
     * The set of solutions should not include any symmetric piece placements.
     *
     * In the IQ-Twist game, valid challenges can have only one solution, but
     * other starting placements that are not valid challenges may have more
     * than one solution.  The most obvious example is the unconstrained board,
     * which has very many solutions.
     *
     * @param placement A valid piece placement string.
     * @return An array of strings, each 32-characters long, describing a unique
     * unordered solution to the game given the starting point provided by placement.
     */
    public static String[] getSolutions(String placement) {//Use task 6 code here

        // FIXME Task 9: determine all solutions to the game, given a particular starting placement
        return null;
    }



    //TESTING PROGRAM: Displays the entire board (must be 4x8)
    public static void displayBoard(String[][] displayerboard){
        int ctr =1;
        System.out.print("  ");
        while(ctr!=9){
            if(ctr==8){
                System.out.println(ctr); }
            else {
                System.out.print(ctr); }
            ctr++; }
        for (int row = 0; row < 4; row++) {// initialize the board
            if(row>0){
                System.out.printf("%n"+Character.toString((char) (65 + row))+" "); }
            else {
                System.out.print(Character.toString((char) (65 + row))+" "); }
            for (int col = 0; col < 8; col++) {
                if(displayerboard[row][col].length()>=2){//gobj.getaboard()[row][col].length()>=2
                    System.out.print("#");
                }
                else {
                    System.out.print(displayerboard[row][col]);
                }} } }

    //TESTING PROGRAM: Displays the checkingboard (must be 10x14)
    public static void displayCheckingBoard(String[][] board){
        for (int row = 0; row < 10; row++) {// initialize the board
            for (int col = 0; col < 14; col++) {
                if(board[row][col].length()>=2&& col==13){//gobj.getaboard()[row][col].length()>=2
                    System.out.println("#");
                }
                else if(board[row][col].length()>=2) {
                    System.out.print(board[row][col]);
                }
                else if(col==13){
                    System.out.println(board[row][col]);
                }
                else{
                    System.out.print(board[row][col]);
                }
            }
        }

    }
}


/*if (cno == 0 && minx + 1 < maxx + 1 && miny + 2 < maxy + 1) {//if container does not go pass the board
            //do something to link to for loop

          } else if (cno == 1 && minx + 2 < maxx + 1 && miny + 1 < maxy + 1) {//if container does not go pass the board

          } else if (cno == 2 && minx + 2 < maxx + 1 && miny + 2 < maxy + 1) {//if container does not go pass the board

          } else if (cno == 3 &&   miny + 3 < maxy + 1) {//if container does not go pass the board

          } else if (cno==4 && minx +3 <maxy+1) {//for last container
          }*/

/*   if(viablePiece.isEmpty())
    {return null;}*/
//convert list to set

    /*Notice that if we ignore the holes , aside from a, d and g,
    all pieces exhibit symmetry.  We describe these as 'weakly
    symmetric', and thus take up exactly the same space on the
    board.  We ignore the redundant rotations with higher numberings (e.g.
    if a solution could be made with eithere0 or e7 then we ignore the
    solution with e7 because it is weakly symmetric and has a higher
    rotation number).  Other examples include b0 & b2, c0 & c2,
            f0 & f6, and h0 & h2, each of which are identical pairs if we
    ignore the holes.*/


//filter set accordingly


/*
 *   -> choose the containers you want from ppContainers by 1)checking the size of the empty spaces
 *
 *                                                          2)check in accordance with unplaced for example:
 *                                                            if piece c - b b ob b  is not in unplaced
 *                                                            there is no necessity to use container 3 and 4
 *
 *   -> Create and Use the indexContainer required to produce the data structure for container exact position
 *      (basically a list of indices):I think indexContainer must be static.
 *      indexContainer can be created based on the chosen container and the current status of the board.
 *   -> check if pieces can fit using ppContainer and task-5  in relation to unplaced and indexContainer
 *   -> If yes then based on the information form the piece and insert it into the output set
 *   **Take note of Strictly symmetric pieces c and h and in Weakly symmetric places it should return the lowest orientation number.
 */
//System.out.println("End result is " );
