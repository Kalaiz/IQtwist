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
  public static int[][] test =new int[4][8];
  static GameBoard gobj=new GameBoard();

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
    for(int i=0,z=0;i<8;i+=2,z++){
      //Characters compared in accordance to ascii encoding values
      if( piecePlacement.charAt(z)>=data[i]&& piecePlacement.charAt(z)<=data[i+1]){
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
   *     meanwhile 'i' fields represent the inner board
   *
   *
   *@param board2  Modified board
   *@return  True if there is a piece  on the outerboard
   */
  public static boolean checkboard(int [][] board2){//check if  pieces are in the outer board
    int row=board2.length;
    int col=board2[0].length;
    for(int cr=0;cr<row;cr++){
      for(int cc=0;cc<col;cc++){
        if((cr<3||cr>6)&&board2[cr][cc]==1){
          return true;
        }
        else if((cc<3||cc>10)&& board2[cr][cc]==1){
          return true;
        }
      }
    }
    return false;
  }


  //TESTING PROGRAM: Displays the entire board (must be 4x8)
  public static void displayBoard(int[][] displayerboard){
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
        System.out.print(gobj.getaboard()[row][col]);
      } } }



  public static void main(String[] args) {
    displayBoard(boardcreator("a1A0",'a'));
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
  public static int[][] boardcreator(String placement,char bt ) {
    int[][] temp = {{9}};//For on_board
    gobj.resetBoardvalues("ac");//resets both the Boards
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
    if (boardcreator(placement, 'c')[0][0] == 9) {
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
    boolean bh1 = true, bh2 = true, bh3 = true,  bh;
    int rnumber = placement.length()/4;
    int cnumber = 4;
    char [][] ch = new char [rnumber][cnumber];//12 objects
    char[] placechar = placement.toCharArray();
    int s = 0;
    int i = 0, j = 0;

    while (i < rnumber){
      ch [i][0] = placechar[s];
      ch [i][1] = placechar[s+1];
      ch [i][2] = placechar[s+2];
      ch [i][3] = placechar[s+3];
      s += 4;
      i += 1;
    }

    /** pieces must be entirely on the board
     * left and top, for pegs
     * 1 < ch[i][1] < 8, A < ch[i][2] < D
     */


    /**right and bottom:
     * just 8 pieces
     */
    for (i = 0; i < rnumber; i++){
      //piece a, b, d, f are 2*3 grids
      if ((ch[i][0]=='a')||(ch[i][0]=='b')||(ch[i][0]=='d')||(ch[i][0]=='f')){
        //right and bottom of the pieces
        if ((ch[i][3] == '0') || (ch[i][3] == '2') || (ch[i][3] == '4') || (ch[i][3] == '6')){
          if (((ch[i][1]) > '6')||((ch[i][2]) > 'C')){
            bh1 = false;
          }
        } else {
          if (((ch[i][1]) > '7')||((ch[i][2]) > 'B')){
            bh1 = false;
          }
        }
      } else if(ch[i][0]=='c'){
        //piece c
        if ((ch[i][3] == '0') || (ch[i][3] == '2') || (ch[i][3] == '4') || (ch[i][3] == '6')){
          if ((ch[i][1]) > '5'){
            bh1 = false;
          }
        } else {
          if((ch[i][2]) > 'A'){
            bh1 = false;
          }
        }
      } else if (ch[i][0]=='e'){
        //piece e
        if (((ch[i][1]) > '7')||(ch[i][2] > 'C')){
          bh1 = false;
        }
      } else if (ch[i][0]=='g'){
        //piece g
        if (((ch[i][1]) > '6')||((ch[i][2]) > 'B')){
          bh1 = false;
        }
      } else if (ch[i][0]=='h'){
        //piece h
        if ((ch[i][3]== '0') || (ch[i][3] == '2') || (ch[i][3] == '4') || (ch[i][3] == '6')){
          if ((ch[i][1]) > '6'){
            bh1 = false;
          }
        } else {
          if (ch[i][2] > 'B'){
            bh1 = false;
          }
        }
      }
    }

    /**
     * build a 4*8 matrix
     * initialized as board[4][8] = {0}
     * at the end
     * if board[i][j] > 1
     * bh is false
     */
    int[][] board = new int[4][8];
    char[][] pegboard = new char[4][8];
    int red = 'i';
    int blue = 'j';
    int green = 'k';
    int yellow = 'l';
    for(i = 0; i < 4; i++){
      for(j = 0; j < 8; j++){
        board[i][j] = 0;
        pegboard[i][j] = ' ';
      }
    }

    /**
     * left of the piece
     * board[row][(int) ch[i][1]]
     * top of the piece
     * board[ch[i][2]-'A'][column]
     */
    int rch;//top row of the piece
    int cch;//left column of the piece
    for(i = 0; i < rnumber; i++){
      rch = ch[i][2] - 'A';
      cch = ch[i][1] - '1';
      if (ch[i][0] == 'a'){
        if (ch[i][3] == '0'){
          board[rch][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch][cch+2] += 1;
          board[rch+1][cch+2] += 1;
        }
        if (ch[i][3] == '1'){
          board[rch][cch+1] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+2][cch+1] += 1;
          board[rch+2][cch] += 1;
        }
        if (ch[i][3] == '2'){
          board[rch][cch] += 1;
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+1][cch+2] += 1;
        }
        if (ch[i][3] == '3'){
          board[rch][cch] += 1;
          board[rch+1][cch] += 1;
          board[rch+2][cch] += 1;
          board[rch][cch+1] += 1;
        }
        if (ch[i][3] == '4'){
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+1][cch+2] += 1;
          board[rch][cch+2] += 1;
        }
        if (ch[i][3] == '5'){
          board[rch][cch] += 1;
          board[rch+1][cch] += 1;
          board[rch+2][cch] += 1;
          board[rch+2][cch+1] += 1;
        }
        if (ch[i][3] == '6'){
          board[rch][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch][cch+2] += 1;
          board[rch+1][cch] += 1;
        }
        if (ch[i][3] == '7'){
          board[rch][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+2][cch+1] += 1;
        }

        //badpegs
        if ((ch[i][3] == '0') || (ch[i][3] == '6')) {
          pegboard[rch][cch] = 'i';
          pegboard[rch][cch + 2] = 'i';
        }
        if ((ch[i][3] == '1') || (ch[i][3] == '7')) {
          pegboard[rch][cch+1] = 'i';
          pegboard[rch+2][cch + 1] = 'i';
        }
        if ((ch[i][3] == '2') || (ch[i][3] == '4')) {
          pegboard[rch+1][cch] = 'i';
          pegboard[rch+1][cch + 2] = 'i';
        }
        if ((ch[i][3] == '3') || (ch[i][3] == '5')) {
          pegboard[rch][cch] = 'i';
          pegboard[rch+2][cch] = 'i';
        }
      }

      if (ch[i][0] == 'b'){
        if ((ch[i][3] == '0') || (ch[i][3] == '2')){
          board[rch][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+1][cch+2] += 1;
        }
        if ((ch[i][3] == '1') ||(ch[i][3] == '3')){
          board[rch][cch+1] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+1][cch] += 1;
          board[rch+2][cch] += 1;
        }
        if ((ch[i][3] == '4') || (ch[i][3] == '6')){
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
          board[rch][cch+1] += 1;
          board[rch][cch+2] += 1;
        }
        if ((ch[i][3] == '5') || (ch[i][3] == '7')){
          board[rch][cch] += 1;
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+2][cch+1] += 1;
        }

        //badpegs
        if ((ch[i][3] == '0') || (ch[i][3] == '3') || (ch[i][3] == '5') || (ch[i][3] == '6')) {
          pegboard[rch+1][cch+1] = 'i';
        }
        if ((ch[i][3] == '1') || (ch[i][3] == '7')) {
          pegboard[rch+1][cch] = 'i';
        }
        if ((ch[i][3] == '2') || (ch[i][3] == '4')) {
          pegboard[rch][cch+1] = 'i';
        }
      }

      if (ch[i][0] == 'c'){
        if ((ch[i][3] == '0') || (ch[i][3] == '2') ||(ch[i][3] == '4') ||(ch[i][3] == '6')){
          board[rch][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch][cch+2] += 1;
          board[rch][cch+3] += 1;
        }
        if ((ch[i][3] == '1') || (ch[i][3] == '3') || (ch[i][3] == '5') || (ch[i][3] == '7')){
          board[rch][cch] += 1;
          board[rch+1][cch] += 1;
          board[rch+2][cch] += 1;
          board[rch+3][cch] += 1;
        }

        //badpegs
        if ((ch[i][3] == '0') || (ch[i][3] == '4')) {
          pegboard[rch][cch+1] = 'j';
        }
        if ((ch[i][3] == '1') || (ch[i][3] == '5')) {
          pegboard[rch+1][cch] = 'j';
        }
        if ((ch[i][3] == '2') || (ch[i][3] == '6')) {
          pegboard[rch][cch+2] = 'j';
        }
        if ((ch[i][3] == '3') || (ch[i][3] == '7')) {
          pegboard[rch+2][cch] = 'j';
        }
      }

      if (ch[i][0] == 'd'){
        if (ch[i][3] == '0'){
          board[rch][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch][cch+2] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+1][cch+2] += 1;
        }
        if (ch[i][3] == '1'){
          board[rch][cch+1] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+2][cch+1] += 1;
          board[rch+2][cch] += 1;
          board[rch+1][cch] += 1;
        }
        if (ch[i][3] == '2'){
          board[rch][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+1][cch+2] += 1;
        }
        if (ch[i][3] == '3'){
          board[rch][cch] += 1;
          board[rch+1][cch] += 1;
          board[rch+2][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch+1][cch+1] += 1;
        }
        if (ch[i][3] == '4'){
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+1][cch+2] += 1;
          board[rch][cch+2] += 1;
          board[rch][cch+1] += 1;
        }
        if (ch[i][3] == '5'){
          board[rch][cch] += 1;
          board[rch+1][cch] += 1;
          board[rch+2][cch] += 1;
          board[rch+2][cch+1] += 1;
          board[rch+1][cch+1] += 1;
        }
        if (ch[i][3] == '6'){
          board[rch][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch][cch+2] += 1;
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
        }
        if (ch[i][3] == '7'){
          board[rch][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+2][cch+1] += 1;
          board[rch+1][cch] += 1;
        }

        //badpegs
        if (ch[i][3] == '0') {
          pegboard[rch+1][cch+1] = 'j';
          pegboard[rch+1][cch+2] = 'j';
        }
        if (ch[i][3] == '1') {
          pegboard[rch+1][cch] = 'j';
          pegboard[rch+2][cch] = 'j';
        }
        if (ch[i][3] == '2') {
          pegboard[rch][cch] = 'j';
          pegboard[rch][cch+1] = 'j';
        }
        if (ch[i][3] == '3') {
          pegboard[rch][cch+1] = 'j';
          pegboard[rch+1][cch+1] = 'j';
        }
        if (ch[i][3] == '4') {
          pegboard[rch][cch+1] = 'j';
          pegboard[rch][cch+2] = 'j';
        }
        if (ch[i][3] == '5') {
          pegboard[rch+1][cch+1] = 'j';
          pegboard[rch+2][cch+1] = 'j';
        }
        if (ch[i][3] == '6') {
          pegboard[rch+1][cch] = 'j';
          pegboard[rch+1][cch+1] = 'j';
        }
        if (ch[i][3] == '7') {
          pegboard[rch][cch] = 'j';
          pegboard[rch+1][cch] = 'j';
        }
      }

      if (ch[i][0] == 'e'){
        if ((ch[i][3] == '0') || (ch[i][3] == '7')){
          board[rch][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch+1][cch+1] += 1;
        }
        if ((ch[i][3] == '1') || (ch[i][3] == '4')){
          board[rch][cch+1] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+1][cch] += 1;
        }
        if ((ch[i][3] == '2') || (ch[i][3] == '5')){
          board[rch][cch] += 1;
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
        }
        if ((ch[i][3] == '3') || (ch[i][3] == '6')){
          board[rch][cch] += 1;
          board[rch+1][cch] += 1;
          board[rch][cch+1] += 1;
        }

        //badpegs
        if ((ch[i][3] == '0') || (ch[i][3] == '4')) {
          pegboard[rch][cch+1] = 'k';
          pegboard[rch+1][cch+1] = 'k';
        }
        if ((ch[i][3] == '1') || (ch[i][3] == '5')) {
          pegboard[rch+1][cch] = 'k';
          pegboard[rch+1][cch+1] = 'k';
        }
        if ((ch[i][3] == '2') || (ch[i][3] == '6')) {
          pegboard[rch][cch] = 'k';
          pegboard[rch+1][cch] = 'k';
        }
        if ((ch[i][3] == '3') || (ch[i][3] == '7')) {
          pegboard[rch][cch] = 'k';
          pegboard[rch][cch+1] = 'k';
        }
      }

      if (ch[i][0] == 'f'){
        if ((ch[i][3] == '0') || (ch[i][3] == '6')){
          board[rch][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch][cch+2] += 1;
          board[rch+1][cch+1] += 1;
        }
        if ((ch[i][3] == '1') || (ch[i][3] == '7')){
          board[rch][cch+1] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+2][cch+1] += 1;
          board[rch+1][cch] += 1;
        }
        if ((ch[i][3] == '2') || (ch[i][3] == '4')){
          board[rch][cch+1] += 1;
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+1][cch+2] += 1;
        }
        if ((ch[i][3] == '3') || (ch[i][3] == '5')){
          board[rch][cch] += 1;
          board[rch+1][cch] += 1;
          board[rch+2][cch] += 1;
          board[rch+1][cch+1] += 1;
        }

        //badpegs
        if (ch[i][3] == '0') {
          pegboard[rch][cch+2] = 'k';
          pegboard[rch+1][cch+1] = 'k';
        }
        if (ch[i][3] == '1') {
          pegboard[rch+1][cch] = 'k';
          pegboard[rch+2][cch+1] = 'k';
        }
        if (ch[i][3] == '2') {
          pegboard[rch][cch+1] = 'k';
          pegboard[rch+1][cch] = 'k';
        }
        if (ch[i][3] == '3') {
          pegboard[rch][cch] = 'k';
          pegboard[rch+1][cch+1] = 'k';
        }
        if (ch[i][3] == '4') {
          pegboard[rch][cch+1] = 'k';
          pegboard[rch+1][cch+2] = 'k';
        }
        if (ch[i][3] == '5') {
          pegboard[rch+1][cch+1] = 'k';
          pegboard[rch+2][cch] = 'k';
        }
        if (ch[i][3] == '6') {
          pegboard[rch][cch] = 'k';
          pegboard[rch+1][cch+1] = 'k';
        }
        if (ch[i][3] == '7') {
          pegboard[rch][cch+1] = 'k';
          pegboard[rch+1][cch] = 'k';
        }
      }

      if (ch[i][0] == 'g'){
        if (ch[i][3] == '0'){
          board[rch][cch] += 1;
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+1][cch+2] += 1;
          board[rch+2][cch+1] += 1;
        }
        if (ch[i][3] == '1'){
          board[rch][cch+1] += 1;
          board[rch][cch+2] += 1;
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+2][cch+1] += 1;
        }
        if (ch[i][3] == '2'){
          board[rch][cch+1] += 1;
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+1][cch+2] += 1;
          board[rch+2][cch+2] += 1;
        }
        if (ch[i][3] == '3'){
          board[rch+2][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+2][cch+1] += 1;
          board[rch+1][cch+2] += 1;
        }
        if (ch[i][3] == '4'){
          board[rch][cch+1] += 1;
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+1][cch+2] += 1;
          board[rch+2][cch] += 1;
        }
        if (ch[i][3] == '5'){
          board[rch][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+1][cch+2] += 1;
          board[rch+2][cch+1] += 1;
        }
        if (ch[i][3] == '6'){
          board[rch][cch+2] += 1;
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+1][cch+2] += 1;
          board[rch+2][cch+1] += 1;
        }
        if (ch[i][3] == '7'){
          board[rch][cch+1] += 1;
          board[rch+1][cch] += 1;
          board[rch+1][cch+1] += 1;
          board[rch+2][cch+1] += 1;
          board[rch+2][cch+2] += 1;
        }

        //badpegs
        if (ch[i][3] == '0') {
          pegboard[rch][cch] = 'l';
          pegboard[rch+1][cch] = 'l';
          pegboard[rch+2][cch+1] = 'l';
        }
        if (ch[i][3] == '1') {
          pegboard[rch][cch+1] = 'l';
          pegboard[rch][cch+2] = 'l';
          pegboard[rch+1][cch] = 'l';
        }
        if (ch[i][3] == '2') {
          pegboard[rch][cch+1] = 'l';
          pegboard[rch+1][cch+2] = 'l';
          pegboard[rch+2][cch+2] = 'l';
        }
        if (ch[i][3] == '3') {
          pegboard[rch+1][cch+2] = 'l';
          pegboard[rch+2][cch] = 'l';
          pegboard[rch+2][cch+1] = 'l';
        }
        if (ch[i][3] == '4') {
          pegboard[rch][cch+1] = 'l';
          pegboard[rch+1][cch] = 'l';
          pegboard[rch+2][cch] = 'l';
        }
        if (ch[i][3] == '5') {
          pegboard[rch][cch] = 'l';
          pegboard[rch][cch+1] = 'l';
          pegboard[rch+1][cch+2] = 'l';
        }
        if (ch[i][3] == '6') {
          pegboard[rch][cch+2] = 'l';
          pegboard[rch+1][cch+2] = 'l';
          pegboard[rch+2][cch+1] = 'l';
        }
        if (ch[i][3] == '7') {
          pegboard[rch+1][cch] = 'l';
          pegboard[rch+2][cch+1] = 'l';
          pegboard[rch+2][cch+2] = 'l';
        }
      }

      if (ch[i][0] == 'h'){
        if ((ch[i][3] == '0') || (ch[i][3] == '2') ||(ch[i][3] == '4') ||(ch[i][3] == '6')){
          board[rch][cch] += 1;
          board[rch][cch+1] += 1;
          board[rch][cch+2] += 1;
        }
        if ((ch[i][3] == '1') || (ch[i][3] == '3') || (ch[i][3] == '5') || (ch[i][3] == '7')){
          board[rch][cch] += 1;
          board[rch+1][cch] += 1;
          board[rch+2][cch] += 1;
        }

        //badpegs
        if ((ch[i][3] == '0') || (ch[i][3] == '1') || (ch[i][3] == '4') || (ch[i][3] == '5')) {
          pegboard[rch][cch] = 'l';
        }
        if ((ch[i][3] == '2') || (ch[i][3] == '6')) {
          pegboard[rch][cch+2] = 'l';
        }
        if ((ch[i][3] == '3') || (ch[i][3] == '7')) {
          pegboard[rch+2][cch] = 'l';
        }
      }
    }

    for(i = 0; i < 4; i++){
      for(j = 0; j < 8; j++){
        if(board[i][j] > 1){
          bh2 = false;
        }
      }
    }

    for (i = 0; i < rnumber; i++){
      if(ch[i][0]=='i'){
        if(pegboard[ch[i][2]-'A'][ch[i][1]-'1'] != 'i'){
          bh3 = false;
        }
      }
      if(ch[i][0]=='j'){
        if(pegboard[ch[i][2]-'A'][ch[i][1]-'1'] != 'j'){
          bh3 = false;
        }
      }
      if(ch[i][0]=='k'){
        if(pegboard[ch[i][2]-'A'][ch[i][1]-'1'] != 'k'){
          bh3 = false;
        }
      }
      if(ch[i][0]=='l'){
        if(pegboard[ch[i][2]-'A'][ch[i][1]-'1'] != 'l'){
          bh3 = false;
        }
      }
    }

    bh = bh1 && bh2 && bh3;
    // FIXME Task 5: determine whether a placement string is valid
    return bh;
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

  /**
   * Gives the indices of the empty grids
   *
   * @param placement details of the piece location
   * @return list of required indices
   * */
  public static List<int[]> getEmptyGrid(String placement) {
    List<int[]> emptyGrid = new ArrayList<>();
    for (int x = 0; x < 4; x++){
      for(int y = 0; y < 8; y++){
        if (gobj.getaboard()[x][y]==0){
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
