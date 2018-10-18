package comp1110.ass2;
import java.util.Arrays;
import java.util.HashMap;
import static comp1110.ass2.PieceData.*;

/**
 *Class which creates pieces for the game.
 *Pieces are represented as a multidimensional array in which alphabets represents
 *the occupied places and the colour.
 *Authorship:Kalai
 */

public class Pieces {
    private char piece_name;
    private String[][] actual_piece;
    /**
     *hm:Hashmap which holds all types of pieces.
     *Numbers are used to denote the key of hashmap.
     *Values are the multidimensional piece array
     *=======Details of the key numbers=======
     * a0=1,a1=2..... where the character is the piece type
     * and the integer is the orientation number.
     */
    static HashMap<Integer,String[][]> pp=new HashMap<>();


    /**
     *Creates a Piece based on PieceData class
     *and piece_name.
     * @param piece_name : Respective piece character
     */
    Pieces(char piece_name){
        int i = piece_name-97;
        actual_piece= Arrays.stream(all_pieces[i]).parallel().toArray(String[][]::new);
        this.piece_name=piece_name; }

    /**
     *Initialises the Hashmap with all the required values(pieces)
     * t is the hashmap key
     */
    static void initialisehms(){
        String piece[][];
         for(int innerval=-1,t=0;t<68;t++){// Main hashmap loop :-1 for initialisation;
            innerval=(innerval==7)?0:++innerval;
            if(t>63){//need to change
                pp.put(t,(new Pieces((char)((t-64)+105))).getactual_piece());
                continue;
            }
            else if(t>19&&t<24||t>59&&t<63){//neglecting strong symmetric pieces which are redundant
                continue;
            }
            piece=new Pieces((char)((t/8)+97)).getactual_piece();
            int orientation_no=innerval;
            for(int m=0;m<orientation_no;++m){//rotation loop
                if(orientation_no>3){
                    orientation_no-=4;
                    piece=GameBoard.flipper(piece);
                    if(orientation_no==0){break;}//if orientation number is 0 no further rotation is required
                    m=-1;
                    continue;}
                piece= GameBoard.rotator(piece);
            }

            pp.put(t,piece);
        }

    }

    /**
     * @return  A multidimensional array of the respective piece.
     */
    String[][] getactual_piece() {
        return actual_piece;
    }




}
