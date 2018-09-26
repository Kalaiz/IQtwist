package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import org.junit.rules.Timeout;
import static org.junit.Assert.assertFalse;


public class PlacerTest  {
    @Rule
    public Timeout globalTimeout = Timeout.millis(20000);
    GameBoard tb = new GameBoard();
    static Random rn  = new Random();
    static String[][]testpiece=(new Pieces('a')).getactual_piece();
    String[][] output = {{"x","x","x","x",}};
    String[][] output2 = {{}};


    private void test(boolean cond,String expected,String input){
        tb.resetBoardvalues("ac");//initialises both the  boards
        assertFalse("Expected : "+expected+" for inputs : "+ input, cond); }



   private String[][] filler(String[][] input){
        for(int i=0;i<input.length;i++){
            for(int m=0;m<input[0].length;m++){
                input[i][m]="x"; } }return input;
 }

   private boolean iptb(char type){
     String[][] board=(type=='a')?tb.getaboard():tb.getcboard();
     int modifer=(type=='a')?0:3;
     int row= board.length;
     int col=board[0].length;
     String[][] bigPiece= filler(new String[row][col]);//to avoid nullpointerexception
     return Arrays.deepEquals(GameBoard.placer(board,bigPiece,modifer,modifer,modifer),board);
 }
    private boolean wpv(char type){
        String[][] board=(type=='a')?tb.getaboard():tb.getcboard();
        int modifer=(type=='a')?0:3;
        int prow = board.length;
        int pcol = board[0].length;
        //for negative positioning input
        boolean cond1 = Arrays.deepEquals(GameBoard.placer(board,testpiece,-prow,-pcol,modifer),board);
        //cond2 is for out of board
        return cond1 && Arrays.deepEquals(GameBoard.placer(board,testpiece,prow,pcol,modifer),board);
    }
    //If inputpiecearray  is too big ,then return the non-modified board.
  @Test
    public void  inputPieceTooBig(){
      test(!(iptb('a')&&iptb('c')),"The default board","valid board,row,col and invalid inputpiecearr");
    }


    /*includes wrong value for positioning row and col
    1)row &col must be positive
    2)row & col must a valid indices on the board
    3)once placed piece must not go out of board
    if any ot those conditions are not met , the non-modified board is returned.
    */
    @Test
    public void  wrongPositionValues(){
        tb.resetBoardvalues("ac");
      test(!(wpv('a')&&wpv('c')),"The default board","valid board,inputpiecearr and an invalid positional row and col");
        }

     @Test
    public void correctlyplaces(){
         //String[][] otpt=(type=='a')?output:output2;

     }


/*
   private boolean checkarray(String[][] array1, String[][] array2){
       if(array1.length==array2.length && array1[0].length==array2[0].length){return false;}//if the sizes of the array are same
           else{for(int row =0;row<array1.length;row++){
               for(int col=0;col<array1[0].length;col++){
                   if(array1[row][col]!=array2[row][col]){
                      return false;
                   } } } }
   return true;}*/


}

