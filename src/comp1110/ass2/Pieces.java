package comp1110.ass2;

public class Pieces {


    public final char piece_name;
    public int[][] actual_piece;

    Pieces(char piece_name){
        this.piece_name=piece_name;
        if(piece_name=='a'){
            int[][] actual_piece={{1,1,1},{0,0,1}}; }
        else if(piece_name=='b'){int[][] actual_piece={{1,1,0},{0,1,1}};}
        else if(piece_name=='c'){ int[][] actual_piece={{1,1,1,1}}; }
        else if(piece_name=='d'){int[][] actual_piece={{1,1,1},{0,1,1}};}
        else if(piece_name=='e'){int[][] actual_piece={{1,1},{0,1}};}
        else if(piece_name=='f'){int[][] actual_piece={{1,1,1},{0,1,0}};}
        else if(piece_name=='g'){int[][] actual_piece={{1,0,0},{1,1,1},{0,1,0}};}
        else if(piece_name=='h'){int[][] actual_piece={{1,1,1}};}
        else if(piece_name=='i'||piece_name=='j'||piece_name=='k'||piece_name=='l'){int[][] actual_piece={{1}};}
        this.actual_piece=actual_piece;}


    int[][] getactual_piece() {
        return actual_piece;
    }

    void changeactualplace(int[][] changingplace){
        this.actual_piece=changingplace;

    }


   /* Pieces(char piece_name,int row,int col,int[][] actualpieces){
        this.piece_name=piece_name;
        this.row=row;
        this.col=col;
    }*/

}
