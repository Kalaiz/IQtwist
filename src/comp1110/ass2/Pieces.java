package comp1110.ass2;
import java.util.HashMap;

import static comp1110.ass2.PieceData.*;
public class Pieces {
     /*Pieces are represented as a multidimensional array in which 1 represents
    the occupied places .
     */
    public final char piece_name;
    public String[][] actual_piece;
   /*Numbers to denote the key of hashmap are unique
    *a0-1,a1-2.....goes on as such
    * Why not use String as keys?
    * https://stackoverflow.com/questions/1516549/bad-idea-to-use-string-key-in-hashmap
    *Because an integer literal prefixed with 0 is treated as octal, and '8' and '9' aren't valid octal digits.
    */

   static HashMap<Integer,String[][]> hm=new HashMap<>();




    Pieces(char piece_name){
        this.piece_name=piece_name;
        if(piece_name=='a'){
            this.actual_piece=aactual_piece; }
        else if(piece_name=='b'){this.actual_piece=bactual_piece; }
        else if(piece_name=='c'){ this.actual_piece=cactual_piece; }
        else if(piece_name=='d'){this.actual_piece=dactual_piece; }
        else if(piece_name=='e'){this.actual_piece=eactual_piece; }
        else if(piece_name=='f'){this.actual_piece=factual_piece; }
        else if(piece_name=='g'){this.actual_piece=gactual_piece; }
        else if(piece_name=='h'){this.actual_piece=hactual_piece; }
        else if(piece_name=='i'){this.actual_piece=iactual_piece;}
        else if(piece_name=='j'){this.actual_piece=jactual_piece;}
        else if(piece_name=='k'){this.actual_piece=kactual_piece;}
        else if(piece_name=='l'){this.actual_piece=lactual_piece;}

        }


        void initialisehms(){
        for(int i=0,t=0;i<64;i+=8){// t be the hashmap index
            String piece[][]=new Pieces((char)((int)(i/8)+97)).getactual_piece();
            for(int m=0;m<8;m++){
               piece=GameBoard.rotator(piece);
            }
            //hm.put(i,GameBoard.rotator(new Pieces((char))))

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
