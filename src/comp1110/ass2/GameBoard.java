package comp1110.ass2;
import static comp1110.ass2.Pieces.pp;

/*
 *Class which creates a board and does operation over them whenever needed.
 *
 *Authorship:LingYu Xia (rotator)
 *           Kalai (Everything else)
 */
public class GameBoard {
    private String[][] actualBoard = new String[4][8];
    public static boolean offBoardOrOverlap;


    /**
     * Adds a piece to the respective board and updates it.
     *
     * @param piece piece String
     *
     */
    void pieceTobeAdded(String piece) {
        int hashMapKey= getHashmapkey(piece);
        int col = Character.getNumericValue(piece.charAt(1)) - 1;
        int row = piece.charAt(2) - 65;
            this.actualBoard = placer(actualBoard, pp.get(hashMapKey), row, col );

    }


    private int getHashmapkey(String piece){
        char pName = piece.charAt(0);
        int orientationNo = Character.getNumericValue(piece.charAt(3));
        int hashMapKey = (pName > 104) ? pName - 104 + 63 : (pName - 97) * 8 + orientationNo;//(pname - 97)*8 to get the corresponding piece base number
        //Strong symmetric pieces dont have redudndant piece orientations
        if((hashMapKey>19&& hashMapKey<24 )||(hashMapKey>59 && hashMapKey<64))
        {hashMapKey-=4;}

return hashMapKey;
    }


    /**
     * Obtains the actual board
     *
     * @return Multidimensional array representing the actualBoard
     */
    String[][] getaboard() {
        return actualBoard;
    }




    /**
     * Resets the respective Board(s)
     */
    void resetBoardvalues() {
        String[][] board = new String[4][8];
        for (int trow = 0; trow < 4; trow++) {
            for (int tcol = 0; tcol < 8; tcol++) {
                    board[trow][tcol] = "x";
                }
            }
                this.actualBoard=board;

        }




    /**
     * Places the piece on the board multidimensional array
     * It will return the non modified board if the (placing array)
     * piecearr is larger than the board or if the input values
     * aren't valid.
     *
     * @param board    Non-modified board
     * @param piecearr Multidimensional array of the piece
     * @param row2     the row on which the top-most piece resides
     * @param col2     the column in which the left-most piece resides
     * @return (Updated) board or null if there is a piece in an  invalid position
     */
    public static String[][] placer(String[][] board, String[][] piecearr, int row2, int col2) {
        if (row2 < 0 || col2 < 0||row2>=board.length||col2>=board[0].length) {
            offBoardOrOverlap=true;
            return null;
        }
        try {
        int prow = piecearr.length;
        int pcol = piecearr[0].length;
        int endr = prow + row2;
        int endc = pcol + col2;
         for (int cr = 0; row2 < endr; row2++, cr++) {
                int col2_temp = col2;
                for (int cc = 0; col2_temp < endc; col2_temp++, cc++) {
                    if (board[row2 ][col2_temp ] .equals("x")) {
                        board[row2 ][col2_temp ] = piecearr[cr][cc];
                    } else if (!piecearr[cr][cc].equals("x")) {// if the piece part is empty don't update the output board
                        board[row2 ][col2_temp ] = piecearr[cr][cc] + board[row2 ][col2_temp];
                    }
                }
            }
        }
        //if piecearr size is more than than board or the board has null value(i.e Values of the board not declared)
        catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            offBoardOrOverlap=true;
            return null;
        }
        return board;
    }

    /**
     * flip the array stuffs (not considering the holes in
     * pieces for now) and hence cannot take in c or h pieces.
     *
     * @param actualpiece multidimensional array (Respective piece)
     * @return a Flipped array(in respective to X-axis)
     */
    public static String[][] flipper(String[][] actualpiece) {
        int row = actualpiece.length;
        int col = actualpiece[0].length;
        String result[][] = new String[row][col];
        for (int i = 0; i <= row / 2; i++) {
            for (int j = 0; j < col; j++) {
                result[i][j] = actualpiece[row - (i + 1)][j];
                result[row - (i + 1)][j] = actualpiece[i][j];
            }
        }
        return result;
    }


    /**
     * rotates the array clockwise(90 degree)
     *
     * @param actualpiece2 multidimensional array (Respective piece)
     * @return a Rotated array
     *  author: Lingyu Xia
     */
    public static String[][] rotator(String[][] actualpiece2) {
        String[][] rotated = new String[actualpiece2[0].length][actualpiece2.length];
        for (int i = 0; i < actualpiece2[0].length; ++i) {
            for (int j = 0; j < actualpiece2.length; ++j) {
                rotated[i][j] = actualpiece2[actualpiece2.length - j - 1][i];
            }
        }
        return rotated;
    }


}
/*
    void pieceTobeAdded(String piece, String bt) {
        int hashMapKey= getHashmapkey(piece);
        int col = Character.getNumericValue(piece.charAt(1)) - 1;
        int row = piece.charAt(2) - 65;
        if (bt.equals("a")) {
            if((placer(actualBoard, hm.get(hashMapKey), row, col )!=null)){
                actualBoard = placer(actualBoard, hm.get(hashMapKey), row, col );
            }
            else{//actual board hasnt been implemented yet as it does not require remove piece for now
            }
        } else {
            if((placer(checkingBoard, hm.get(hashMapKey), row, col )!=null)){
                checkingBoard = placer(checkingBoard, hm.get(hashMapKey), row, col );
            }
            else{
                removepiece(piece,'c');
            }
        }

    }*/
/*
public static String[][] boardcreator(String placement, char bt) {
    if (gobj.getCBoardName()!=null){
        if(placement.equals(gobj.getCBoardName().substring(0,placement.length()-4))&&bt=='c'){
            gobj.removepiece(gobj.getCBoardName().substring(gobj.getcboard().length-4),'c');
            placement=placement.substring(placement.length()-4);//so to not do so many operations
        }
        else {
            gobj.resetBoardvalues(Character.toString(bt));//resets the respective board ( test reasons)
        }}
    else {
        gobj.resetBoardvalues(Character.toString(bt));//resets the respective board ( test reasons)
    }
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
*/
