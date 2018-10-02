package comp1110.ass2.gui;

import comp1110.ass2.GameBoard;
import comp1110.ass2.TwistGame;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board extends Application {
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    static GameBoard g = new GameBoard();
    static TwistGame t = new TwistGame();
    private static final int VIEWER_WIDTH = 1280;
    private static final int VIEWER_HEIGHT = 640;
    private static final String URI_BASE = "assets/";
    private final Group root = new Group();
    private final Group controls = new Group();
    static double tempx;//for positioning
    static double tempy;//for positioning
    Glow g2 = new Glow();
    Glow g1 = new Glow();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Game");
        GridPane grid = new GridPane(); //Creating Grid
        for(int i=0;i<8;i++){
            ColumnConstraints col = new ColumnConstraints(50);
            grid.getColumnConstraints().add(col); }
        for(int i=0;i<4;i++){
            RowConstraints row = new RowConstraints(50);
            grid.getRowConstraints().add(row); }
        grid.setGridLinesVisible(true);
        grid.setLayoutX(700);
        grid.setLayoutY(10);
        tempy=50;
        tempx=100;
        List<ImageView> imgObjs=new ArrayList();// list of images
        List<boxcreator> boxes = new ArrayList();
        //For loop to iterate through everypiece
        for(int i=0;i<12;i++){//12 pieces
            imgObjs.add(new ImageView());
            ImageView ivo=imgObjs.get(i);
            Image img= (new Image(Viewer.class.getResource(URI_BASE+ ((char) (i +97)) +".png").toString()));
            ivo.setImage((new Image(Viewer.class.getResource(URI_BASE+ ((char) (i +97)) +".png").toString(),img.getWidth()*0.5,img.getHeight()*0.5,false,false)));
            double width = ivo.getImage().getWidth(); // divide by 2 so to have cursor in middle of it when dragging
            double height= ivo.getImage().getHeight();
            boxes.add(new boxcreator(((char)(i+97)),height,width));
            boxcreator b = boxes.get(i);
            if(i==4){
                tempx=340;
                tempy=50; }
            else if(i==7){
                tempx+=300;
                tempy=230; }
            else if(i>7){
                tempy=230;
                tempx+=50; }
            tempy+=b.measurement;
            ivo.setX(tempx);
            ivo.setY(tempy);
            b.defaultxy(tempx,tempy);
            g1.setLevel(0);
            ivo.setOnMouseEntered(e-> {
                ivo.setEffect(g2);});
            ivo.setOnMouseExited(e-> {ivo.setEffect(g1);});
            ivo.setOnScroll(e-> {b.rotate();ivo.setRotate(b.rotate);});
            ivo.setOnMouseDragged(m->{
                ivo.setY(m.getSceneY()-height/2);//for centering piece upon drag
                ivo.setEffect(g2);
                ivo.setX(m.getSceneX()-width/2);
                ivo.setOnMouseReleased(t->{
                    if(m.getSceneX()>700&&m.getSceneY()<1100&&m.getSceneY()<230&&m.getSceneY()>10){//if it is within the board
                        double y=m.getSceneY()-height/2;
                        double x= m.getSceneX()-width/2;
                        double rotval=b.rotate;
                        if(b.rotate/90%2!=0&&!(b.getchar()=='g'||b.getchar()=='e')){
                            int translatex = (b.getchar()=='h')?-50:(b.rotate==90)?-75:-25;
                            ivo.setTranslateX(translatex); }
                        int[] xyval=getrowcol(x,y,rotval,b.getchar());
                        int[] csrs={(int)height/50,(int)width/50};
                        if((b.rotate/90)%2!=0){//Switch cs and rs .
                            csrs[0]=(int)width/50;
                            csrs[1]=(int)height/50; }
                        try{grid.add(ivo,xyval[0],xyval[1],csrs[1],csrs[0]);}//column index, rowindex, colspan, rowspan
                        catch(IllegalArgumentException e){
                            //make the piece go back to default place
                        } } });
            });
            root.getChildren().add(ivo);
        }
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public int[] getrowcol(double x,double y,double rotate,char ptype) {//returns respective grid row values
        int[] xyvals = new int[2];
        if (rotate == 0||rotate==180||rotate==360) {
            x = (x - 675 < 10) ? x + 10 : Math.ceil(x);
            y = Math.ceil(y);
            xyvals[0] = (int) Math.round((x - 700) / 50);//ci
            xyvals[1] = (int) Math.round(((y - 10)) / 50);//ri
            return xyvals; }
        else {
            if( ptype=='c'){
                x=Math.ceil(x)+55;
                y=Math.ceil(y)-56; }
            else if(ptype=='h'){
                x = Math.ceil(x)+55;
                y = Math.ceil(y)-50; }
            else {
                x = (rotate==90.0)?Math.ceil(x)+55:Math.ceil(x)+25;
                y = (rotate==90.0)?Math.ceil(y)-5:Math.ceil(y)-25;}
            xyvals[0] = (int) Math.round(((x - 700)) / 50);//ci
            xyvals[1] = (int) Math.round(((y - 10)) / 50);//ri
            return xyvals; }

    }


    class boxcreator{//containers which will hold the pieces
        // each piece will have it's container
        char ptype;
        double measurement;//the offset calculated for the piece placesment (default)
        int rotate;
        double x ,y;// default x position and y position
        double height,width;
        int gridVal[];
        int translation;

        boxcreator(char ptype,double height,double width){
            this.ptype=ptype;
            this.width=width;
            this.height=height;
            measurement=(ptype=='a'||ptype=='e'||(int)ptype>104)? 0 :(height > width) ? height : width; }

        void rotate() {
            if (rotate > 360) {
                rotate = 90;
            } else {
                rotate += 90; }
        }
        void defaultxy(double x,double y){
            this.x=x;
            this.y=y; }

        char getchar(){
            return ptype; }


    }
    class PieceStats{//Will inherit from boxcontainer
        //holds rotate,default x coordinate and y coordinate
        //if is on board or not
        //if on board it  will contain the piece placement(the piece string form )
        //update piece if needed
    }
    class GameStatus  {
        //contains the game data
        //
    }


    public static String start_Placements(){
        int numofpiece ; //number of the pieces
        int numofr;
        int numofb;
        int numofg;
        int numofy ; //random number of different pegs
//
// int startlength = 4 * (numofpiece + numofr + numofb + numofg + numofy);

        do {
            Random rand1 = new Random();
            numofpiece = rand1.nextInt(2); //number of the pieces
            numofr = rand1.nextInt(2);
            numofb = rand1.nextInt(3);
            numofg = rand1.nextInt(3);
            numofy = rand1.nextInt(3); //random number of different pegs
        }while((numofr + numofb + numofg + numofy) < 1);

        int startlength = 4 * (numofpiece + numofr + numofb + numofg + numofy);

        Random rand = new Random();

        //the piece of the output String
        char[] piece = new char[4];
        piece[0] = (char) (rand.nextInt(8) + 97);
        piece[1] = (char) (rand.nextInt(8) + 49);
        piece[2] = (char) (rand.nextInt(4) + 65);
        piece[3] = (char) (rand.nextInt(8) + 48);
        String strp = new String(piece);

        //the pegs of the output String
        char[] rpeg = new char[4];
        rpeg[0] = 'i';
        rpeg[1] = (char) (rand.nextInt(8) + 49);
        rpeg[2] = (char) (rand.nextInt(4) + 65);
        rpeg[3] = '0';
        String strr = new String(rpeg);

        char[] bpeg1 = new char[4];
        bpeg1[0] = 'j';
        bpeg1[1] = (char) (rand.nextInt(8) + 49);
        bpeg1[2] = (char) (rand.nextInt(4) + 65);
        bpeg1[3] = '0';
        char[] bpeg2 = new char[4];
        bpeg2[0] = 'j';
        bpeg2[1] = (char) (rand.nextInt(8) + 49);
        bpeg2[2] = (char) (rand.nextInt(4) + 65);
        bpeg2[3] = '0';
        String strb1 = new String(bpeg1);
        String strb2 = new String(bpeg2);

        char[] gpeg1 = new char[4];
        gpeg1[0] = 'k';
        gpeg1[1] = (char) (rand.nextInt(8) + 49);
        gpeg1[2] = (char) (rand.nextInt(4) + 65);
        gpeg1[3] = '0';
        char[] gpeg2 = new char[4];
        gpeg2[0] = 'k';
        gpeg2[1] = (char) (rand.nextInt(8) + 49);
        gpeg2[2] = (char) (rand.nextInt(4) + 65);
        gpeg2[3] = '0';
        String strg1 = new String(gpeg1);
        String strg2 = new String(gpeg2);

        char[] ypeg1 = new char[4];
        ypeg1[0] = 'l';
        ypeg1[1] = (char) (rand.nextInt(8) + 49);
        ypeg1[2] = (char) (rand.nextInt(4) + 65);
        ypeg1[3] = '0';
        char[] ypeg2 = new char[4];
        ypeg2[0] = 'l';
        ypeg2[1] = (char) (rand.nextInt(8) + 49);
        ypeg2[2] = (char) (rand.nextInt(4) + 65);
        ypeg2[3] = '0';
        String stry1 = new String(ypeg1);
        String stry2 = new String(ypeg2);

        String str = "                                            ";
        StringBuilder str1 = new StringBuilder(str);

        if(numofpiece == 1){
            str1.insert(0, strp);
        }

        if(numofr == 1){
            str1.insert(4, strr);
        }

        if(numofb == 1){
            str1.insert(8, strb1);
        } else if(numofb == 2){
            str1.insert(8, strb1);
            str1.insert(12,strb2);
        }

        if(numofg == 1){
            str1.insert(8, strg1);
        } else if(numofg == 2){
            str1.insert(8, strg1);
            str1.insert(12,strg2);
        }

        if(numofy == 1){
            str1.insert(8, stry1);
        } else if(numofy == 2){
            str1.insert(8, stry1);
            str1.insert(12,stry2);
        }

        str = new String(str1);

        char[] charpla = str.toCharArray();
        char[] charnew = new char[startlength];

        int j = 0;
        for(int i = 0; i < charpla.length; i++){
            if(charpla[i] != ' '){
                charnew[j] = charpla[i];
                j ++;
            }
        }

        str = new String(charnew);

        return str;
    }

    public static String validStartPlacement(){
        String str = start_Placements();
        if (!t.isPlacementStringValid(str)) {
            str = start_Placements();
        }

        return str;
    }


    //private void rotate(){}//rotate pieces

    //Snap the pieces to nearest position (if it is over the respective position.)
    //private void snap_to_nearest_grid(){}

    // FIXME Task 8: Implement starting placements

    private void makeBoard(){
        Random rn =new Random();
        /*
         *1)create an (linked)hashset of string ,such that each string is a piece placement data.
         *  use hashsets to prevent duplicates
         *  This hashset contains the data about those value which needs pieces to be created.
         *  if insertion order is crucial use linked type hashset
         *2)create a list of piece inclusive of pegs - alphas
         *  represent pegs as with literal and 0 added behind
         *  example: a0
         *  Create a rng value specifically for the no of pieces to be placed
         *  Create a string for the output
         *3)run for loop (cond index should be greater than 1 and must us rng with a bound of x - 4 )
         *   every iteration should check if such string exist
         *   in the list and whether it is valid or not.
         *  (if it exist then remove or else repeat for loop - counters updated)
         *  then remove it from the list .After that  add it to the output string
         *
         *4)create a case such that Starting must at least contain a single peg
         *  alter 4th rng to 0 just fr this case
         *
         *5) use rng to generate a random value
         *      for 1st char- use list.size as reference to get the bound value
         *                    then apply it to the list of char
         *               2nd-  standard bound value of 8
         *               3rd-  standard bound value of 3
         *               4th-  standard bound value of 7
         */
    }

   /* public static void main(String[] args) {
       *//* Board b = new Board();
        b.makeBoard();*//*
        String str = validStartPlacement();
        System.out.println(t.isPlacementStringValid(str));
        System.out.println(str);
    }*/

    /*set opacity of selected pieces to a certain percentage  or
    use Blur effect for that certain piece (using setEffect) Use task 9 code for the solutions.*/
    // FIXME Task 10: Implement hints
    public static void hint(){
    }

    // FIXME Task 11: Generate interesting starting placements
    /*In reference to Difficulty level choose a certain state from Difficulty_level */


}



