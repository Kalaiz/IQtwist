package comp1110.ass2;

import org.junit.Test;
import java.util.Arrays;
import static junit.framework.TestCase.assertTrue;


public class PlacerTest  {

    private GameBoard tb = new GameBoard();
    private String[][]testpiece=(new Pieces('a')).getactual_piece();

    private void test(boolean cond,String expected,String input){
        tb.resetBoardvalues("ac");//initialises both the  boards
        assertTrue("Expected : "+expected+" for inputs : "+ input, cond); }


    /*Checks for the return of the non - modified board upon
    *an input which contains inputpiecearray  which  is too big.
    */
    @Test
    public void  inputPieceTooBig(){
      test((iptb('a')&&iptb('c')),"The default board","valid board,row,col and invalid inputpiecearr");
    }


    /*Checks for the return of the non - modified board upon blow mentioned  conditions are not met.
    1)row & col must be positive
    2)row & col must a valid indices on the board
    3)once placed piece must not go out of board
    */
    @Test
    public void  wrongPositionValues(){
        tb.resetBoardvalues("ac");
      test((wpv('a')),"The default board","valid board,inputpiecearr and an invalid positional row and col");
        }


    //Checks for the return of an correctly-updated board based upon an empty board.
     @Test
    public void exactlyPlaced(){
         test( !Arrays.deepEquals(GameBoard.placer(tb.getcboard(),testpiece,3,3),DeliverableTestUtility.expectedacboards),"exp","inp");
    }


    //Checks for the return of an correctly-updated board based upon an empty board(Mathematical logic).
    @Test
    public void basicPlacesIt(){
        tb.resetBoardvalues("ac");
test(!(placesitsum('a')),"The Input pieces to be placed within the board","which are valid ");
    }//calculate the number of characters other than "x" if placed ;ensure whether it is correct



    /*Ensures that the empty spots of the inputpiecearr does not overlap the current
     *board piece
     *
     */
    @Test
    public void emptyPieceDataDoesntOverlapBoard(){
    boolean acc =true;
    char type='a';

     loop:for(int m =0;m<DeliverableTestUtility.multiboards(type).size()+1;m++) {
       for(int i=0;i<10;i++){
            if(m==5&& type!='c'){
               type='c';
                m=0;
             continue loop; }
        if(m==5){test (acc,"An updated board","which includes a Board which contain(s) piece(s) and other required valid inputs");
        break loop;}
        Pieces p= (new Pieces((char) (i+97)));
        acc=acc&&((GameBoard.placer(DeliverableTestUtility.multiboards(type).get(m),p.getactual_piece(),0,0))==null);
            }
        }
    }


    private int sumnotx(String[][] input){
        int acc=0;
        for(int i=0;i<input.length;i++){
            for(int m=0;m<input[0].length;m++){
                if(!input[i][m].equals("x")){
                    acc+=1;
                }
            }
        }
        return acc;}


    private boolean checkoverlaps(String[][] board){//for previous version of placer
        for(int i=0;i<board.length;i++){
            for(int m=0;m<board[0].length;m++){
                if(board[i][m].contains("x")&&board[i][m].length()>1){
                    return true;
                }
            }
        }
        return false;
    }

    private String[][] filler(String[][] input){
        for(int i=0;i<input.length;i++){
            for(int m=0;m<input[0].length;m++){
                input[i][m]="x"; } }return input; }

    private boolean iptb(char type){
        String[][] board=(type=='a')?tb.getaboard():tb.getcboard();
        int row= board.length;
        int col=board[0].length;
        String[][] bigPiece= filler(new String[row][col]);//to avoid nullpointerexception
        return Arrays.deepEquals(GameBoard.placer(board,bigPiece,0,0),null);
    }
    private boolean wpv(char type){
        String[][] board=(type=='a')?tb.getaboard():tb.getcboard();
        int prow = board.length;
        int pcol = board[0].length;
        //for negative positioning input
        boolean cond1 = Arrays.deepEquals(GameBoard.placer(board,testpiece,-prow,-pcol),null);
        //cond2 is for out of board
        return cond1 && Arrays.deepEquals(GameBoard.placer(board,testpiece,prow,pcol),null);
    }

    private boolean placesitsum(char type){

        for(int i=0;i<9;i++){
            Pieces p= (new Pieces((char) (i+97)));
            String[][]testboard= GameBoard.placer(tb.getaboard(),(new Pieces((char) (i+97))).getactual_piece(),0,0);
            //Arrays.stream(testboard).filter(ch->ch=="x")
            int eoccupancy=sumnotx(testboard);
            int aoccupancy=sumnotx(p.getactual_piece());
            if((eoccupancy!=aoccupancy)){return false;}
        }
        return true;

    }

}

