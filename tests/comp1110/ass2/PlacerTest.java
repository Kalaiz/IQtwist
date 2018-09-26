package comp1110.ass2;

import org.junit.Test;
import java.util.Arrays;
import static junit.framework.TestCase.assertTrue;



public class PlacerTest  {

    GameBoard tb = new GameBoard();
    String[][]testpiece=(new Pieces('a')).getactual_piece();

    private void test(boolean cond,String expected,String input){
        tb.resetBoardvalues("ac");//initialises both the  boards
        assertTrue("Expected : "+expected+" for inputs : "+ input, cond); }

   private String[][] filler(String[][] input){
        for(int i=0;i<input.length;i++){
            for(int m=0;m<input[0].length;m++){
                input[i][m]="x"; } }return input; }

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

    private boolean placesitsum(char type){
        int modifer=(type=='a')?0:3;
        for(int i=0;i<9;i++){
            Pieces p= (new Pieces((char) (i+97)));
            String[][]testboard= GameBoard.placer(tb.getaboard(),(new Pieces((char) (i+97))).getactual_piece(),modifer,modifer,modifer);
            //Arrays.stream(testboard).filter(ch->ch=="x")
            int eoccupancy=sumnotx(testboard);
            int aoccupancy=sumnotx(p.getactual_piece());
            if((eoccupancy!=aoccupancy)){return false;}
        }
        return true;

    }

    public int sumnotx(String[][] input){
        int acc=0;
        for(int i=0;i<input.length;i++){
            for(int m=0;m<input[0].length;m++){
                if(input[i][m]!="x"){
                    acc+=1;
                }
            }
        }
    return acc;}



    //if inputpiecearray  is too big ,then return the non-modified board.
  @Test
    public void  inputPieceTooBig(){
      test((iptb('a')&&iptb('c')),"The default board","valid board,row,col and invalid inputpiecearr");
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
      test((wpv('a')&&wpv('c')),"The default board","valid board,inputpiecearr and an invalid positional row and col");
        }


     @Test
    public void correctlyPlaces(){
        boolean cond1=!Arrays.deepEquals(GameBoard.placer(tb.getaboard(),testpiece,3,3,3),DeliverableTestUtility.expectedaaboards);
         test(cond1 && !Arrays.deepEquals(GameBoard.placer(tb.getcboard(),testpiece,3,3,3),DeliverableTestUtility.expectedacboards),"exp","inp");
    }
    @Test
    public void basicPlacesIt(){
        tb.resetBoardvalues("ac");
test(!(placesitsum('a')&&placesitsum('c')),"The Input pieces to be placed within the board","which are valid ");
    }//calculate the number of characters other than "x" if placed ;ensure whether it is correct

    @Test
    public void EmptyPiecedataDoesntOverlap(){

    }



}

