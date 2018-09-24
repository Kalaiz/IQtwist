package comp1110.ass2;

import comp1110.ass2.gui.Viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class provides the text interface for the Twist Game
 * <p>
 * The game is based directly on Smart Games' IQ-Twist game
 * (http://www.smartgames.eu/en/smartgames/iq-twist)
 */
public class TwistGame {
  public static int[][] test = new int[4][8];
  static GameBoard gobj = new GameBoard();

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
   */


  //  Task 2: determine whether a piece or peg placement is well-formed
  public static boolean isPlacementWellFormed(String piecePlacement){
    char[]data ={'a','l','1','8','A','D','0','7'};
    /*For the fourth character;In situation where given input represents a peg,
    then the 4th character must be 0. It also ensures the input has a length of 4.*/
    if((piecePlacement.charAt(0)>='i'&& piecePlacement.charAt(3)!='0')|| piecePlacement.length()!=4){
      return false;}
    for(int i=0;i<4;i++){
      //Characters compared in accordance to ascii encoding values
      if( piecePlacement.charAt(i)>=data[i*2]&& piecePlacement.charAt(i)<=data[i*2+1]){
      }
      else{return false;}
    }
    return true; }

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
   */
  public static boolean isPlacementStringWellFormed(String placement) {
    // FIXME Task 3: determine whether a placement is well-formed
    String[] items = new String[placement.length()/4];                      //Using a loop to store all four-character placements into a String[]
    int countRed = 0;
    int countGre = 0;
    int countBlu = 0;
    int countYel = 0;

    if(placement.length()%4!=0 || placement==null || placement==""){        //Check if the length of the placement String is valid and the content of the string

      return false;

    }else {

      for(int i = 0; i < placement.length()/4 ; i++){

        items[i] = placement.substring(4*i,4*i+4);

        if(isPlacementWellFormed(items[i])!=true)

          return false;

      }

      if(placement.length()<=32 && placement.length()>4){

        for(int j=0;j<placement.length()/4-1;j++){

          if(items[j].charAt(0) >= items[j+1].charAt(0)){

            return false;

          }

        }

      }else if(placement.length()>=32) {
        for (int j = 1; j < 8; j++) {                                          //Second judge if the pieces occur in the correct alphabetical order or have duplicated

          if (items[j - 1].charAt(0) >= items[j].charAt(0)) {
            return false;
          }

        }
      }


      for(int x = 0; x < placement.length(); x++){                         //Last judge if pegs are outnumbered

        if (placement.charAt(x)=='i')
          countRed++;
        else if (placement.charAt(x)=='j')
          countBlu++;
        else if (placement.charAt(x)=='k')
          countGre++;
        else if (placement.charAt(x)=='l')
          countYel++;

      }

      if(countRed > 1 || countBlu > 2 || countGre > 2 || countYel > 2) {

        return false;

      }

    }

    return true;

  }


  /**
   *Checks if any pieces is in the outerboard
   *
   *          o o o o o o o o o o o o o o
   *          o o o o o o o o o o o o o o
   *          o o o o o o o o o o o o o o
   *          o o o I I I I I I I I o o o
   *          o o o I I I I I I I I o o o
   *          o o o I I I I I I I I o o o
   *          o o o I I I I I I I I o o o
   *          o o o o o o o o o o o o o o
   *          o o o o o o o o o o o o o o
   *          o o o o o o o o o o o o o o
   *
   *     'o' fields represent the outer board
   *     meanwhile 'I' fields represent the inner board
   *
   *
   *@param board2  Modified board
   *@return  True if there is a piece  on the outerboard
   */
  public static boolean checkboard(String [][] board2){//check if  pieces are in the outer board
    int row=board2.length;
    int col=board2[0].length;
    for(int cr=0;cr<row;cr++){
      for(int cc=0;cc<col;cc++){
        if((cr<3||cr>6)&&board2[cr][cc]!="x"){
          return true;
        }
        else if((cc<3||cc>10)&& board2[cr][cc]!="x"){
          return true;
        }
      }
    }
    return false;
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
      if(gobj.getaboard()[row][col].length()>=2){//gobj.getaboard()[row][col].length()>=2
        System.out.print("#");
      }
      else {
        System.out.print(gobj.getaboard()[row][col]);
      }} } }

  public static String[][] displayOverlapBoard(String[][] displayerboard){
    String[][] actualboard = new String[4][8];
    for (int row = 0; row < 4; row++) {// initialize the board
      for (int col = 0; col < 8; col++) {
        if(gobj.getaboard()[row][col].length()>=2){//gobj.getaboard()[row][col].length()>=2
          actualboard[row][col] = "#";
        }
        else {
         actualboard[row][col] = gobj.getaboard()[row][col];
        }
      }
    }
        return actualboard;
  }

  public static boolean checkBoard2(){
    for (int row = 3; row < 7; row++) {// initialize the board
      for (int col = 3; col < 12; col++) {
        if(gobj.getcboard()[row][col].length() == 4 ){
          if (gobj.getcboard()[row][col].charAt(1) != gobj.getcboard()[row][col].charAt(3)){
            return false;
          } else if(gobj.getcboard()[row][col].charAt(0) == gobj.getcboard()[row][col].charAt(2)){
            return false;
          }
        }else if(gobj.getcboard()[row][col].length() == 3){
          return false;
        }
        else if((gobj.getcboard()[row][col].length() == 2) ){
          if((gobj.getcboard()[row][col].charAt(0) != 'p') && (gobj.getcboard()[row][col].charAt(0) != 'o')){
            return false;
          }
        } else if(gobj.getcboard()[row][col].length() > 4){
          return false;
        }
      }
    }
    return true;
  }

  public static void displayOverlapBoard1(String[][] actualboard) {
    for (int row = 0; row < 4; row++) {// initialize the board
      for (int col = 0; col < 8; col++) {
        System.out.print(actualboard[row][col]);
      }
      System.out.print("\n");
    }
  }


  public static boolean check2(String placement){
    if(!is_onboard(placement))
      {return false;}
      else if(!checkBoard2()){
        return false;
      }
      return true;
    }

  public static void main(String[] args) {

    boolean check = check2("a7A7j8A0");

    System.out.println(check);

    //displayBoard(boardcreator("a7A7c1A3d2A6e2C3f3C4g4A7h6D0i6A0j2B0j1C0k3C0l4B0l5C0",'a'));
  }



  /**
   *Modifies respective board based on the placement string
   *
   *@param placement details of the pieces
   *@param bt describes the board type
   *                actualboard (4x8 board)
   *                checkingboard (10x12 board specifically for is_onboard and isvalidPlacement )
   *@local temp used as the initial value for is_onboard or else will get updated as per requirement and return itself
   *@return Updated board
   */
  public static String[][] boardcreator(String placement,char bt ) {
    String[][] temp = {{"z"}};//For on_board
    gobj.resetBoardvalues(Character.toString(bt));//resets the respective board
    for (int i = 0; i < placement.length() / 4; i++) {
      String ch=placement.substring(4*i,4*i+4);
      if(bt=='a'){
         gobj.pieceTobeAdded(ch,"a"); }
      else{
      gobj.pieceTobeAdded(ch,"c");
      if(checkboard(gobj.getcboard())){
        return temp;
      }}
    }
    temp = (bt =='a') ? gobj.getaboard() : gobj.getcboard();
    return temp;}




  /**
   *Checks if any pieces is on the main board
   *
   *@param placement A well formed piece placement String
   *@return  True if all pieces are on the main board
   */

  public static boolean is_onboard(String placement) {
    if (boardcreator(placement, 'c')[0][0] == "x") {
      return false;
    }
    return true;
  }
  /**
   * Determine whether a placement string is valid.  To be valid, the placement
   * string must be well-formed and each piece placement must be a valid placement
   * according to the rules of the game.
   * - pieces must be entirely on the board
   * - pieces must not overlap each other
   * - pieces may only overlap pegs when the a) peg is of the same color and b) the
   *   point of overlap in the piece is a hole.
   *
   * @param placement A placement sequence string
   * @return True if the placement sequence is valid
   */
  public static boolean isPlacementStringValid(String placement) {
    /*piece : a-h, 1-8, A-D, 0-3, 4-7;
     *peg: i, j, k, l; 0
     *divide the placement string into pieces and pegs
     *store the information into a 2d matrix ch
     */


    if(!is_onboard(placement))
    {return false;}
    else if(!checkBoard2()){
      return false;
    }

    //boolean bh2 = true, bh3 = true,  bh;

    //bh = bh2 && bh3;
    // FIXME Task 5: determine whether a placement string is valid
    return true;
  }

  /**
   * Given a string describing a placement of pieces and pegs, return a set
   * of all possible next viable piece placements.   To be viable, a piece
   * placement must be a valid placement of a single piece.  The piece must
   * not have already been placed (ie not already in the placement string),
   * and its placement must be valid.   If there are no valid piece placements
   * for the given placement string, return null.
   *
   * When symmetric placements of the same piece are viable, only the placement
   * with the lowest rotation should be included in the set.
   *
   * @param placement A valid placement string (comprised of peg and piece placements)
   * @return An set of viable piece placements, or null if there are none.
   */
  public static Set<String> getViablePiecePlacements(String placement) {
    // FIXME Task 6: determine the set of valid next piece placements
    Viewer v = new Viewer();
    String placed_pieces = v.returner(placement,0);
    String unplaced_pieces = "";
    boardcreator(placement,'a');//Creates* an  actualboard
    for (int i = 'a' ; i <= 'l' ; i++){
      if(placed_pieces.indexOf(String.valueOf((char)i))==-1)
        unplaced_pieces = unplaced_pieces + String.valueOf((char)i);
    }

    List<int[]> emptyGrid = getEmptyGrid(placement);
    System.out.println(emptyGrid);

    return null;
  }

  /*
   * Gives the indices of the empty grids
   *
   * @param placement details of the piece location
   * @return list of required indices
   *
   */
  public static List<int[]> getEmptyGrid(String placement) {
    List<int[]> emptyGrid = new ArrayList<>();
    for (int x = 0; x < 4; x++){
      for(int y = 0; y < 8; y++){
        if (gobj.getaboard()[x][y]=="x"){
          int[] gridIndex = new int[2];
          gridIndex[0] = x;
          gridIndex[1] = y;
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
  public static String[] getSolutions(String placement) {//Use task 6 code here

    // FIXME Task 9: determine all solutions to the game, given a particular starting placement
    return null;
  }
}


/*DRAFT CODES
  TASK2
  public static boolean isPlacementWellFormed2(String piecePlacement) {//initial  code
    boolean  contain=(piecePlacement.charAt(0)>'a'&& piecePlacement.charAt(0)<'l');//when char is compared it is converted to ascii encoding numbers
    boolean contain2=(piecePlacement.charAt(1)<'1'&& piecePlacement.charAt(1)<'8');
    boolean contain3=(piecePlacement.charAt(2)<'A'&& piecePlacement.charAt(2)>'D');
    boolean contain4= (piecePlacement.charAt(3)<='0'&& piecePlacement.charAt(3)>'8');// could be a peg
    contain=contain && contain2&&contain3&& contain4;
    return contain;
  }*/
