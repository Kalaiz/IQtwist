package comp1110.ass2;

public class GameBoard {
    public String[][] checkingBoard = new String[10][14];// Inclusive of the main board(4x8)
    public String[][] actualBoard = new String[4][8]; //The actual board

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
        Pieces p = new Pieces(pname);
        if (orientation_no > 3) {
            orientation_no -= 4;
            if (pname == 'c' || pname == 'h') {//flipping these pieces will not cause any problem for on_board function
            } else {
                p.changeactualplace(flipper(p.getactual_piece()));//flipping the actual piece obj
            }
        }
        while (orientation_no != 0) {
            p.changeactualplace(rotator(p.getactual_piece()));
            orientation_no--;
        }
        if (bt == "a") {
            this.actualBoard = placer(actualBoard, p.getactual_piece(), row, col, 0);
        } else {
            this.checkingBoard = placer(checkingBoard, p.getactual_piece(), row, col, 3);
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
        int row = 10, col = 12;
        if (s == "ac") {
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
     *
     * @param board3   Non-modified board
     * @param piecearr Multidimensional array of the piece
     * @param row2     the row on which the top-most piece resides
     * @param col2     the column in which the left-most piece resides
     * @param modifier value added for the sake of is_onboard (Default should be 0)
     * @return Updated board
     */
    public static String[][] placer(String[][] board3, String[][] piecearr, int row2, int col2, int modifier) {//places the array into the board array
        int row = piecearr.length;
        int col = piecearr[0].length;
        int endr = row + row2;
        int endc = col + col2;
        for (int cr = 0; row2 < endr; row2++, cr++) {
            int col2_temp = col2;
            for (int cc = 0; col2_temp < endc; col2_temp++, cc++) {
                if (board3[row2 + modifier][col2_temp + modifier] == "x"){
                    board3[row2 + modifier][col2_temp + modifier] =piecearr[cr][cc];
                }
                else {
                    //adding 3 so to add the first segment of the piece to the inner board(mandatory)
                    board3[row2 + modifier][col2_temp + modifier] = piecearr[cr][cc] + board3[row2 + modifier][col2_temp + modifier];
                }}
        }
        return board3;
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