package comp1110.ass2;

import comp1110.ass2.gui.Viewer;

import java.util.*;

/**
 * This class provides the text interface for the Twist Game
 * <p>
 * The game is based directly on Smart Games' IQ-Twist game
 * (http://www.smartgames.eu/en/smartgames/iq-twist)
 */
public class TwistGame {
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
   * Authorship:Kalai
   */

  public static boolean isPlacementWellFormed(String piecePlacement){
    char[]data ={'a','l','1','8','A','D','0','7'};
    /*For the fourth character;In situation where given input represents a peg,
    then the 4th character must be 0. It also ensures the input has a length of 4.*/
    if((piecePlacement.charAt(0)>='i'&& piecePlacement.charAt(3)!='0')|| piecePlacement.length()!=4){
      return false;}
    for(int i=0;i<4;i++){
      //Characters compared in accordance to ascii encoding values
      if( !(piecePlacement.charAt(i)>=data[i*2]&& piecePlacement.charAt(i)<=data[i*2+1])){
        return false;}
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
   * Authorship:Kalai
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
/*
  public static void main(String[] args) {
    Pieces.initialisehms();
    String[][]board =  boardcreator("a1A0",'a');
    Arrays.stream(board).forEach(ch->Arrays.stream(ch).forEach(ich->ich="x") );
    Arrays.stream(board[0]).forEach((ich->ich="x") );
    displayBoard(board);
  }*/

  public static boolean checkBoard2(){//checks for colourpeg and overlap
    for (int row = 3; row < 7; row++) {
      for (int col = 3; col < 11; col++) {
        String ccs=gobj.getcboard()[row][col];//ccs-Current checking piece string
        if(ccs.length() == 4 ){
          if (ccs.charAt(1) != ccs.charAt(3)){//pror (or)  orpr
            return false;
          } else if(ccs.charAt(0) == ccs.charAt(2)){// ogog case
            return false;
          }
        }else if(ccs.length() == 3){
          return false;
        }
        else if((ccs.length() == 2) ){
          //shouldnt start with any character other than o or p
          if(!((ccs.charAt(0) == 'p') || (ccs.charAt(0) == 'o'))){
            return false;
          }
        } else if(ccs.length() > 4){
          return false;
        }
      }
    }
    return true;
  }

  /**
   *Modifies respective board based on the placement string
   *and checks whether place is valid or not concurrently
   *
   *@param placement details of the pieces
   *@param bt describes the board type
   *                actualboard (4x8 board)
   *                checkingboard (10x12 board specifically for is_onboard and isvalidPlacement )
   *@local temp used as the initial value for is_onboard or else will get updated as per requirement and return itself
   *@return Updated board
   * Authorship:Kalai
   */
  public static String[][] boardcreator(String placement,char bt ) {
    String[][] temp = {{"z"}};//The return array if placement is not valid
    gobj.resetBoardvalues(Character.toString(bt));//resets the respective board
    for (int i = 0; i < placement.length() / 4; i++) {
      String ch=placement.substring(4*i,4*i+4);
      if(bt=='a'){
        gobj.pieceTobeAdded(ch,"a"); }
      else{
        gobj.pieceTobeAdded(ch,"c");
        if(checkboard(gobj.getcboard())|| !checkBoard2()){
          return temp;
        }}
    }
    temp = (bt =='a') ? gobj.getaboard() : gobj.getcboard();
    return temp;}

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
   * Authorship: Yuqing Zhang & Kalai
   */
  public static boolean isPlacementStringValid(String placement) {
    Pieces.initialisehms();
    if (boardcreator(placement, 'c')[0][0] == "z") {
      return false;
    }
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
    Set<String> viablePiece = new HashSet();
    Pieces.initialisehms();
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

    return viablePiece;
  }

  /*
   * Gives the indices of the empty grids
   *
   * @param placement details of the piece location
   * @return list of required indices
   *
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



