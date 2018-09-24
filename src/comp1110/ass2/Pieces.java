package comp1110.ass2;

public class Pieces {
     /*Pieces are represented as a multidimensional array in which 1 represents
    the occupied places .
     */


    public final char piece_name;
    public String[][] actual_piece;
    PieceColours color;
    String[][] aactual_piece={{"or","r","or"},{"x","x","r"}};
    String[][] bactual_piece={{"r","r","x"},{"x","or","r"}};
    String[][] cactual_piece={{"b","ob","b","b"}};
    String[][] dactual_piece={{"b","b","b"},{"x","ob","ob"}};
    String[][] eactual_piece={{"g","og"},{"x","og"}};
    String[][] factual_piece={{"g","g","og"},{"x","og","x"}};
    String[][] gactual_piece={{"oy","x","x"},{"oy","y","y"},{"x","oy","y"}};
    String[][] hactual_piece={{"oy","y","y"}};
    String[][] iactual_piece={{"pr"}};
    String[][] jactual_piece={{"pb"}};
    String[][] kactual_piece={{"pg"}};
    String[][] lactual_piece={{"py"}};


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


    /**
     *Applies color to the given Piece object
     *@param  piece_name
     *
     */
        void applycolor(char piece_name){
            int seqnum=(int)piece_name;
            if(seqnum>104){
                seqnum-=96; }
            else{
                seqnum-=104; }
        if(seqnum<3){
            this.color=color.RED; }
        else if(seqnum<5){
            this.color=color.BLUE; }
        else if(seqnum <7){
            this.color=color.GREEN; }
        else{
            this.color=color.YELLOW;
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
