package comp1110.ass2;

import java.util.Arrays;
import static comp1110.ass2.Pieces.hm;

public class GameBoard {
    private String[][] checkingBoard = new String[10][14];// Inclusive of the main board(4x8)
    private String[][] actualBoard = new String[4][8]; //The actual board

    /**
     * Adds a piece to the respective board and updates it.
     *
     * @param piece
     * @param bt    represents board-type(either actual or checking)
     */
    void pieceTobeAdded(String piece, String bt) {
        char pname = piece.charAt(0);
        int orientation_no = Character.getNumericValue(piece.charAt(3));
        int col = Character.getNumericValue(piece.charAt(1)) - 1;
        int row = piece.charAt(2) - 65;
        int hashmapkeyvalue=(pname>104)?pname-104+63:(pname-97)*8+orientation_no;//(pname - 97)*8 to get the corresponding piece base number
        if (bt == "a") {
            this.actualBoard = placer(actualBoard,hm.get(hashmapkeyvalue) , row, col, 0);
        } else {
            this.checkingBoard = placer(checkingBoard,hm.get(hashmapkeyvalue)  , row, col, 3);
        }

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
     * Obtains the checkingboard
     *
     * @return Multidimensional array representing the checkingBoard
     */
    String[][] getcboard() {
        return checkingBoard;
    }


    /**
     * Resets the respective Board(s)
     *
     * @param s which could represent
     *          "a"-actual board
     *          "c"-checking board
     *          "ac"-both the boards
     */
    void resetBoardvalues(String s) { //TODO-Try to implement Map function using streams
        int row = 10, col = 14;
        if (s .equals("ac") ) {
            resetBoardvalues("a");
            resetBoardvalues("c");
        } else if (s.equals("a")) {
            row = 4;
            col = 8;
        }
        for (int trow = 0; trow < row; trow++) {
            for (int tcol = 0; tcol < col; tcol++) {
                if (!s.equals("a")) {
                    this.checkingBoard[trow][tcol] = "x";
                } else {
                    this.actualBoard[trow][tcol] = "x";
                }
            }
        }


    }

    /**
     * Places the piece on the board multidimensional array
     * It will return the non modified board if the (placing array)
     * piecearr is larger than the board or if the input values
     * aren't valid.
     *
     * @param board   Non-modified board
     * @param piecearr Multidimensional array of the piece
     * @param row2     the row on which the top-most piece resides
     * @param col2     the column in which the left-most piece resides
     * @param modifier value added for the sake of differentiating actual and checking board  (Default should be 0)
     * @return (Updated) board
     */
    public static String[][] placer(String[][] board, String[][] piecearr, int row2, int col2, int modifier) {
        if(row2<0 || col2<0){
            return board;
        }
        int prow = piecearr.length;
        int pcol = piecearr[0].length;
        int endr = prow + row2;
        int endc = pcol + col2;
        String[][] oboard= board;
        try { for (int cr = 0; row2 < endr; row2++, cr++) {
            int col2_temp = col2;
            for (int cc = 0; col2_temp < endc; col2_temp++, cc++) {
                if (oboard[row2 + modifier][col2_temp + modifier] == "x") {
                    oboard[row2 + modifier][col2_temp + modifier] = piecearr[cr][cc];
                } else if (piecearr[cr][cc] == "x") {// if the piece part is empty dont update the output board
                } else {
                    //adding 3 so to add the first segment of the piece to the inner board(mandatory)
                    oboard[row2 + modifier][col2_temp + modifier] = piecearr[cr][cc] + oboard[row2 + modifier][col2_temp + modifier];
                }
            } } }
        //if piecearr size is more than than board or the board has null value(i.e Values of the board not declared)
        catch(ArrayIndexOutOfBoundsException|NullPointerException e){
            return board;
        }
        return oboard;
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
