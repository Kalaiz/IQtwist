package comp1110.ass2;

import comp1110.ass2.gui.Viewer;
import java.util.*;
import java.util.stream.IntStream;
import static comp1110.ass2.Pieces.hm;


/**
 * This class provides the text interface for the Twist Game
 * <p>
 * The game is based directly on Smart Games' IQ-Twist game
 * (http://www.smartgames.eu/en/smartgames/iq-twist)
 */
public class TwistGame {
    public static GameBoard gobj = new GameBoard();
    private static final  int[] containerSpecs={3,2,2,3,3,3,4,1,1,4};//rcrcrc.. where r represents row and c represents column
    static int[][] ppContainer=new int[5][];//Jagged array for task 6
    private static final int[] bWeakSymmetricpair={0,2,1,3,4,6,5,7};
    private static final int[] eWeakSymmetricpair={0,7,1,4,2,5,3,6};
    private static final int[] fWeakSymmetricpair={0,6,1,7,2,4,3,5};
    //private static int testval=0;
    //private static  int totalmethodcalls=0;
    static boolean  initialisedhm=false;
    static boolean initilisedCs=false;
    static Set<String> viablePiece2 = new HashSet<String>();
    //static String[][] tempboard=new String[4][8];




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
        //  Task 3: determine whether a placement is well-formed
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



    /*
     *check if there is overlap or badpegs on the checkingboard
     * according to the length of the String element
     * and each char value of the String element
     * Authorship: Yuqing Zhang
     */
    private static boolean checkBoard2() {//checks for colourpeg and overlap
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 8; col++) {
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
     *
     */
    public static String[][] boardcreator(String placement, char bt) {
 /*if (gobj.getCBoardName()!=null){
     if(placement.equals(gobj.getCBoardName().substring(0,placement.length()-4))&&bt=='c'){
         gobj.removepiece(gobj.getCBoardName().substring(gobj.getcboard().length-4),'c');
         placement=placement.substring(placement.length()-4);//so to not do so many operations
        }
     else {
         gobj.resetBoardvalues(Character.toString(bt));//resets the respective board ( test reasons)
     }}
        else {*/
 /*if(tempboard!=null){
     gobj.updateBoard(tempboard);
     placement=placement.substring(placement.length()-4);
 }
 else {*/
     gobj.resetBoardvalues(Character.toString(bt));//resets the respective board ( test reasons)
/* }*/
         /* }*/
        //ToDo : make a static board fr task 6 operations so that there wont be any need to reset the board
        //if static board(temp) is same as placement string-4 or same as placment  dont reset and update placement to just the piece and
        // change checking board to temp--better than calling :Placer,piecetobeAdded,access to hashmap,and reseting the board
        for (int i = 0; i < placement.length() / 4; i++) {
            String ch = placement.substring(4 * i, 4 * i + 4);
            if (bt == 'a') {
                gobj.pieceTobeAdded(ch, "a");
            } else {
                gobj.pieceTobeAdded(ch, "c");
                if(gobj.getcboard()==null||!checkBoard2()){
                    return null; }
            }
        }
        if (bt=='c'){ gobj.updateCBoardName(placement);}else{gobj.updateABoardName(placement);}
        return (bt == 'a') ? gobj.getaboard() : gobj.getcboard();
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
        if(!initialisedhm) {//for task test purposes
          Pieces.initialisehms(); // USE ONLY  FOR TESTS
            initialisedhm=true;
        }
        return (!(boardcreator(placement, 'c')==null));

    }

    public static void main(String[] args) {
        //getViablePiecePlacements("c1A3d2A6");

        String placement="a7A7c1A0";
        int ctr=0;
        String[] s = getSolutions(placement);
        if(s.length==0){
            System.out.println("No Solutions found");
        }
        for(String c : s){

            System.out.println(ctr++ +" "+ c);
        }
        /*Set<String> p = getViablePiecePlacements(placement);
                Iterator<String> a = p.iterator();
        while (a.hasNext()) {
            ctr++;
            String h = a.next();
            System.out.println(ctr + "" + h);
        }*/

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
    public static Set<String> ligetViablePiecePlacements(String placement) //Take note that this does not check if board is valid or not
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



       // totalmethodcalls+=methodcallctr;

        if (viablePiece.isEmpty()){
            return null;}

        //  System.out.println("Test" + (++testval) +" calls Task 5  " + Integer.toString(methodcallctr)+ " Times. Total Average is : " +( totalmethodcalls/testval) );
        return viablePiece;
    }


    public static Set<String> getViablePiecePlacements(String placement) {//Take note that this does not check if board is valid or not
        viablePiece2.clear();
     if (!initialisedhm&&!initilisedCs) {
    //gobj.resetBoardvalues("ac");
    Pieces.initialisehms();//initialise hashmap just for the sake of task tests
    initialiseContainersSpecs();
    initialisedhm=initilisedCs=true;
}
        boardcreator(placement,'c');//creates a board in accordance to the placement string
        //tempboard=gobj.getcboard();
        int[][] ppContainer2=ppContainer.clone();
        Viewer access=new Viewer();
        String unplaced ="";
        String nonAvailcharpieces=access.returner(placement,0);
        //will give an int array of numbers which represent ascii encodings
        int[] output=IntStream.rangeClosed(97, 104).filter(i-> !nonAvailcharpieces.contains((char)i+"")).parallel().toArray();
        for(int i:output){ unplaced+=(Character.toString((char)i)); }//converting to String- not necessary
        List<Integer> containerscanbeused=listofcontainersused(unplaced);
        ppContainer2=updateppcandnew(nonAvailcharpieces,ppContainer2);
        // ctr+=2;
        //update ppcontainer value in accordance to unplaced
        //find range of the containers in accordance to the emptyindices
        int[] emptygridval=getEmptyGrid2('x');
        int[] emptygridvaly=getEmptyGrid2('y');
        int minx= emptygridval[0];
        int maxx= emptygridval[emptygridval.length-1];
        int miny= emptygridvaly[0];
        int maxy= emptygridvaly[emptygridvaly.length-1];
        for(int cno:containerscanbeused) {
            for (int mx=minx; mx < maxx + 1; mx++) {
                for (int my=miny; my < maxy + 1; my++) {
                    if(mx+containerSpecs[2*cno]-1<maxx+1 || my+containerSpecs[(2*cno)+1]-1<maxy+1){//if the row are same? minx = maxx hence no &&
                        char row=(char)(65+mx);
                        String col=Integer.toString(my+1);//string col starts from 1
                        {
                            for (int pno : ppContainer2[cno]) {
                                if((pno>31&&pno<40)||(pno>55&&pno<60)){
                                    //within the container change the  piece e and h placements
                                    for(int ix=mx;ix<mx+containerSpecs[2*cno];ix++){//removed -1 from bound
                                        for(int iy=my;iy<my+containerSpecs[2*cno+1];iy++){
                                            String piece=(char) ((pno/8)+97)+col+row+Integer.toString(pno-((pno/8)*8));
                                            if(isPlacementStringValid(placement+piece)&&!viablePiece2.contains(piece)){
                                                // ctr++;
                                                insertinfakeset(piece);
                                            }
                                        }
                                    }
                                }

                                else {
                                    String piece = (char) ((pno / 8) + 97)  + col + row + Integer.toString(pno - ((pno / 8) * 8));
                                    if (isPlacementStringValid(placement + piece)) {
                                        insertinfakeset(piece);
                                        // ctr++;
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

        if(viablePiece2.size()==0)
        { return null;}
        //totalmethodcalls+=ctr;
        // System.out.println("Test" + (++testval) +" calls helper functions (inclusive of task5) " + " Times. Total Average is : " +( totalmethodcalls/testval) );
        return viablePiece2;
    }


    private static void insertinfakeset(String piece) {
        if (viablePiece2.size() == 0) {//if set has nothing
            viablePiece2.add(piece);
        } else {
            try{ int check = 0;
                Iterator<String> iter = viablePiece2.iterator();
                insertloop:
                while (iter.hasNext()) {
                    String x = iter.next();
                    if (piece.substring(0, 2).equals(x.substring(0, 2)) && !(piece.startsWith("a") || piece.startsWith("d") || piece.startsWith("g"))) {
                        int piecer = Integer.parseInt(piece.substring(3));//rotation value of the input piece
                        int alrsetpr = Integer.parseInt(x.substring(3));//rotation value of already in set (piece)
                        int[] temparr = new int[8];
                        //if there is a Weakly symmetric
                        switch (piece.charAt(0)) {
                            case 'c':
                            case 'h':
                                if (alrsetpr - piecer == 2) {
                                    viablePiece2.remove(x);
                                    viablePiece2.add(piece);
                                    break;
                                }
                            case 'b':
                                temparr = bWeakSymmetricpair;
                                break;

                            case 'f':
                                temparr = fWeakSymmetricpair;
                                break;

                            case 'e':
                                temparr = eWeakSymmetricpair;
                                break;
                        }
                        for (int t = 0; t < 4; t++) {
                            if (temparr[t * 2] == piecer && temparr[(2 * t) + 1] == alrsetpr) {//
                                viablePiece2.remove(x);
                                viablePiece2.add(piece);
                                check++;
                                break insertloop;//once updated the other values in the set must be checked too
                            } else if (temparr[t * 2] == alrsetpr && temparr[(2 * t) + 1] == piecer) {
                                check++;//if vice versa then dont add
                                break insertloop;
                            } else if (t == 3) {
                                continue insertloop;
                            }
                        }
                        viablePiece2.add(piece);//if pieces are not weakly symmetric
                        break;

                    }
                }
                if (check != 1) {
                    viablePiece2.add(piece);//if pieces are not weakly symmetric
                }


            } catch(java.util.ConcurrentModificationException e){}  }

    }





    private static int[][] updateppcandnew(String shouldnotbeonrefdata,int[][] ppContainer3){
        for(int c=0;c<shouldnotbeonrefdata.length();c++){
            if(shouldnotbeonrefdata.charAt(c)>='i'){continue;}
            int startnum=(shouldnotbeonrefdata.charAt(c)-97) * 8;
            int endnum=startnum+7;
            for(int i=0;i<5;i++){
                ppContainer3[i]=Arrays.stream(ppContainer3[i]).parallel().filter(no->!(no>=startnum &&no<=endnum)).toArray();
            }
        }
        return ppContainer3;
    }


    private  static  List<Integer> listofcontainersused(String availpieces){
        List<Integer> ap = new ArrayList<Integer>();
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
     *
     *
     *   Containers
     *
     *   0)         1)          2)         3)       4)
     *    X X         X X X       X X X       X       X X X X
     *    X X         X X X       X X X       X
     *    X X                     X X X       X
     *                                        X
     * */

    private  static void  initialiseContainersSpecs(){
        for(int i=0;i<5;i++){
            List<Integer> cs= new ArrayList<>();
            for(int t=0;t<60;t++){
                if(t>19&&t<24){//neglecting strong symmetric pieces which are redundant
                    // do nothing
                }
                else if( hm.get(t).length<=containerSpecs[i*2] && hm.get(t)[0].length<=containerSpecs[i*2+1]){
                    if((i==3||i==4)&&(t>55)){//skipping redundants
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
    }



    /** Gives the indices of the empty grids
     *
     * @return list of required indices
     *  author: Lingyu Xia & Kalai
     *
     */
    private static int[] getEmptyGrid2(char coordinate) {
        List<Integer> emptyGridcoord= new ArrayList<>();
        for (int x = 0; x < 4; x++){
            for(int y = 0; y < 8; y++){
                /*    gobj.getcboard()[x][y].contains("p")&&gobj.getcboard()[x][y].length()==2    */
                if (gobj.getcboard()[x][y].equals("x")|| gobj.getcboard()[x][y].equals("pr") || gobj.getcboard()[x][y].equals("pb")//this runs faster
                        || gobj.getcboard()[x][y].equals("pg") || gobj.getcboard()[x][y].equals("py")){
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

    // FIXME : IMPORTANT :
    // 1)Whenever there is just one piece left to solve getSolution return null --
    //     example : "b6A7c1A3d2A6e2C3f3C4g4A7h6D0i6B0j2B0j1C0k3C0l4B0" should return a7A7 added to it

    public static String[] getSolutions(String placement) {//Use task 6 code here

        // FIXME Task 9: determine all solutions to the game, given a particular starting placement

        String[] solutions = reorderPlacementStrings(placement);

        return solutions;
    }

    /*
     *  This method uses task 6 and construct a 32-character placement string
     *  Reorder the string into a valid placement string
     * */
    public static String[] reorderPlacementStrings(String placement){

        Set<String> nextPieces = getViablePiecePlacements(placement);
        List<String> solutions = new ArrayList<>();
        int index = 0;
        String[] formalPlacement = new String[2];
        Iterator<String> it = nextPieces.iterator();
        List<String> itStr = new ArrayList<>();

        Viewer v = new Viewer();
        String placed_pieces = v.returner(placement,0);
        String unplaced_pieces = "";
        boardcreator(placement,'a');//Creates* an actualboard

        /*Find the missing pieces*/
        for (int i = 'a' ; i <= 'h' ; i++){

            if(placed_pieces.indexOf(String.valueOf((char)i))==-1)

                unplaced_pieces = unplaced_pieces + String.valueOf((char)i);

        }


        while (it.hasNext()){
            itStr.add(it.next());
        }

        //maintain the placement string that only conatins the pieces
        if (placement.contains("i")){
            formalPlacement = placement.split("i",2);
        }else if (placement.contains("j")){
            formalPlacement = placement.split("j",2);
        }else if (placement.contains("k")){
            formalPlacement = placement.split("k",2);
        }else formalPlacement = placement.split("l",2);

        //sort the viable pieces so that it can be assigned to a String[][] precisely
        Collections.sort(itStr);


        /*
        Transfer the list of pieces got from task 6 into String[][]
        the same piece with different positions are placed in one dimension
        First calculate the length of each dimension
        e.g. aaabbcc is a String[][] with String[0] = new String[3]; String[1] = new String[2]; String[2] = new String[2]
        */
        String[][] difPie = new String [unplaced_pieces.length()][];  //The length of unplaced_pieces defines the rows of String[][]
        char c = itStr.get(0).charAt(0);
        int row = 0;
        int column = 0;


        /*
        Initializing the multi-dimensional string. Accroding to the number of missing pieces defines the rows of String[][],
        and the count of each missing pieces defines the length of each row.
        */
        for (int i = 0; i < itStr.size(); i++) {

            if (itStr.get(i).charAt(0)==c){

                column++;

                if (i == itStr.size()-1){

                    difPie[row] = new String[column];

                }

            }else {

                difPie[row++] = new String[column];
                c = itStr.get(i).charAt(0);
                column = 1;

                if (i == itStr.size()-1){

                    difPie[row] = new String[column];

                }
            }
        }


        /*
         * assign the piece value to the multi-dimensional string
         * */
        for (int i = 0; i < difPie.length; i++) {

            if (difPie[i]!=null) {

                for (int j = 0; j < difPie[i].length; j++) {

                    difPie[i][j] = itStr.get(index++);

                }

            }

        }

        /*
         *    Give the multi-dimentional string to combination() to find all the possible combinations
         * */
        for (int i = 0; i < row; i++){

            if (!solutions.isEmpty()){

                String[] temp = new String[solutions.size()];

                for (int j = 0; j < solutions.size(); j++){

                    temp[j] = solutions.get(j);

                }

                solutions.clear();
                solutions = combination(solutions, temp, difPie[i + 1]);
                temp = null;

            }else solutions = combination(solutions,difPie[i],difPie[i+1]);

        }

        //sort the list in right order of the first letter of piece string
        Collections.sort(solutions);

        List<String> finalSols = new ArrayList<>();
        List<List> unorderedPlacement = new ArrayList<>();

        //add the all the possible combination of viable pieces to the initial placement string
        for (int i = 0; i < solutions.size(); i++){
            unorderedPlacement.add(getFormalPieces(solutions.get(i) + formalPlacement[0]));

            //sort the list in right order and append it into a formal string
            Collections.sort(unorderedPlacement.get(i));
            StringBuilder des = new StringBuilder();
            for (Object o : unorderedPlacement.get(i)){
                des.append(o);
            }

            if (des.length()==32) {
                finalSols.add(des.toString());
                des = null;
            }

        }

        //transfer the list into a string and return.
        String[] retVal = new String[finalSols.size()];
        for (int i = 0; i < finalSols.size(); i++){
            retVal[i] = finalSols.get(i);
        }

        return retVal;
    }


    //Combine all the different pieces, give all the possible combinations.  e.g. a1,a2,a3,b1,b2,c1 --> a1,b1,c1; a1,b2,c1; a2, b1, c1; a2, b2, c1; ....
    public static List<String> combination(List<String> result, String[] madePieces, String[] missingPieces){

        for (int j = 0; j < madePieces.length; j++){

            for (int i = 0; i < missingPieces.length; i++){

                //check if the new formed string is valid by using task 5 first, if it is valid, add it to the result list, else do nothing.
                //Otherwise it may cause OutOfmemory Exception(heap problems)
                if (isPlacementStringValid(madePieces[j] + missingPieces[i]))

                    result.add(madePieces[j] + missingPieces[i]);

            }

        }

        return result;
    }

    //Divid the string into single pieces so that it can be sorted.
    public static List<String> getFormalPieces(String formalPiece){

        List<String> fp = new ArrayList<>();
        for (int i = 0; i < formalPiece.length() / 4; i++) {
            fp.add(formalPiece.substring(i * 4, i * 4 + 4));
        }

        return fp;
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

}
