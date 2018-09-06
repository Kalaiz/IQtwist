package comp1110.ass2;

public class Pieces {
     /*Pieces are represented as a multidimensional array in which 1 represents
    the occupied places .
     */


    public final char piece_name;
    public int[][] actual_piece;
    PieceColours color;
    int[][] aactual_piece={{1,1,1},{0,0,1}};
    int[][] bactual_piece={{1,1,0},{0,1,1}};
    int[][] cactual_piece={{1,1,1,1}};
    int[][] dactual_piece={{1,1,1},{0,1,1}};
    int[][] eactual_piece={{1,1},{0,1}};
    int[][] factual_piece={{1,1,1},{0,1,0}};
    int[][] gactual_piece={{1,0,0},{1,1,1},{0,1,0}};
    int[][] hactual_piece={{1,1,1}};
    int[][] ijklactual_piece={{1}};


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
        else if(piece_name=='i'||piece_name=='j'||piece_name=='k'||piece_name=='l'){this.actual_piece=ijklactual_piece;}
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
    int[][] getactual_piece() {
        return actual_piece;
    }



    /**
     *Changes the current placement array with a updated one
     *@param  changingplace
     *@return  A multidimensional array of the respective piece.
     */
    void changeactualplace(int[][] changingplace){
        this.actual_piece=changingplace;

    }


}
