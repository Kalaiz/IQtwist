package comp1110.ass2;
import java.util.Arrays;
import java.util.HashMap;

import static comp1110.ass2.PieceData.*;
public class Pieces {
     /*Pieces are represented as a multidimensional array in which 1 represents
    the occupied places .
     */
    public final char piece_name;
    public String[][] actual_piece;
    static HashMap<Integer,String[][]> hm=new HashMap<>();
   /*Numbers to denote the key of hashmap are unique
    *a0-1,a1-2.....goes on as such
    * Why not use String as keys?
    * https://stackoverflow.com/questions/1516549/bad-idea-to-use-string-key-in-hashmap
    * Source used on how to use streams to copy elements -https://www.baeldung.com/java-array-copy
    * https://jaxenter.com/java-performance-tutorial-how-fast-are-the-java-8-streams-118830.html
    */


    Pieces(char piece_name){
        int i = piece_name-97;//gets the 'i'th element of the 3D array
        //parallel streams are usually faster than sequencial ones**
        //Will be testing if it is really faster than for loop or not later
        actual_piece= Arrays.stream(all_pieces[i]).parallel().toArray(String[][]::new);
        this.piece_name=piece_name;
    }


        static void initialisehms(){//Hash Map with all pieces with orientations  except for pegs
            String piece[][];
        for(int innerval=-1,t=0;t<64;t++){// -1 for initialisation;t be the hashmap index
            innerval=(innerval==7)?0:++innerval;
           piece=new Pieces((char)((t/8)+97)).getactual_piece();
           int orientation_no=innerval;
       for(int m=0;m<orientation_no;++m){
                if(orientation_no>3){
                      orientation_no-=4;
                    piece=GameBoard.flipper(piece);
                    if(orientation_no==0){break;}//if orientation number is 0 no further rotation is required
                      m=-1;
                continue;}
           piece= GameBoard.rotator(piece);
            }
            hm.put(t,piece);
        }
        }

    /**
     * @return  A multidimensional array of the respective piece.
     */
    String[][] getactual_piece() {
        return actual_piece;
    }


    /**
     *Changes the current placement array with a updated one
     *@param  changingplace
     *@return  A multidimensional array of the respective piece.
     */
    void changeactualplace(String[][] changingplace){
        this.actual_piece=changingplace;
    }


}
