package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import java.util.Random;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlacerTest  {
    @Rule
    public Timeout globalTimeout = Timeout.millis(20000);
    GameBoard tb = new GameBoard();
    static Random rn  = new Random();
    static String[][]testpiece=(new Pieces('a')).getactual_piece();



    private void test(boolean cond){
        tb.resetBoardvalues("ac");//initialises both the  boards
        assertFalse("", cond);


   }


   public boolean checkarray(String[][] array1, String[][] array2){
       if(array1.length==array2.length && array1[0].length==array2[0].length){return false;}//if the sizes of the array are same
           else{for(int row =0;row<array1.length;row++){
               for(int col=0;col<array1[0].length;col++){
                   if(array1[row][col]!=array2[row][col]){
                      return false;
                   } } } }
   return true;}


/*    @Test
    public void outOfBoundforCB() {
       int irow = tb.getcboard().length+rn.nextInt(20);
       int icol = tb.getcboard()[0].length + rn.nextInt(20);
       String[][] inputpa = new String[irow][icol];
       test(checkarray(GameBoard.placer(tb.getcboard(),inputpa,irow,icol,3),tb.getcboard()));
   }

   @Test
    public void outOfBoundforAB(){
       int irow = tb.getaboard().length+rn.nextInt(20);
       int icol = tb.getaboard()[0].length + rn.nextInt(20);
       String[][] inputpa = new String[irow][icol];
       test(checkarray(GameBoard.placer(tb.getaboard(),inputpa,irow,icol,0),tb.getaboard()));
   }*/
  @Test
    public void  inputPieceTooBigAB(){
      int irow=5;
      int icol=5;
      test(checkarray(GameBoard.placer(tb.getaboard(),testpiece,irow,icol,0),tb.getaboard()));



    }
    @Test
    public void  inputPieceTooBigCB(){
        int irow=5;
        int icol=5;
        test(checkarray(GameBoard.placer(tb.getaboard(),testpiece,irow,icol,0),tb.getaboard()));
    }


    @Test
    public void  WrongPositionValuesAB(){
        tb.resetBoardvalues("ac");
        int icrow = tb.getcboard().length+rn.nextInt(20);
        int iccol = tb.getcboard()[0].length + rn.nextInt(20);
        int iarow= tb.getaboard().length +rn.nextInt(20);
        int iacol= tb.getaboard()[0].length+rn.nextInt(20);


            //boolean[i]=GameBoard.placer(tb.getcboard(),testpiece,irow,icol,0));



        }



    @Test
    public void  WrongPositionValuesCB(){
        tb.resetBoardvalues("ac");
        int icrow = tb.getcboard().length+rn.nextInt(20);
        int iccol = tb.getcboard()[0].length + rn.nextInt(20);
        int iarow= tb.getaboard().length +rn.nextInt(20);
        int iacol= tb.getaboard()[0].length+rn.nextInt(20);


        //boolean[i]=GameBoard.placer(tb.getcboard(),testpiece,irow,icol,0));



    }





    @Test
    public void OffBoardAB(){
        tb.resetBoardvalues("ac");


    }
    @Test
    public void OffBoardCB(){
        tb.resetBoardvalues("ac");


    }

}

