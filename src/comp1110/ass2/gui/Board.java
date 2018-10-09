package comp1110.ass2.gui;

import comp1110.ass2.GameBoard;
import comp1110.ass2.TwistGame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board extends Application {
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    static GameBoard g = new GameBoard();
    private static TwistGame t = new TwistGame();
    private static final int VIEWER_WIDTH = 1280;
    private static final int VIEWER_HEIGHT = 649;
    private static final String URI_BASE = "assets/";
    private final Group root = new Group();
    private final Group controls = new Group();
    private static double tempx;//for positioning
    private static double tempy;//for positioning
    Glow g2 = new Glow();
    Glow g1 = new Glow();
    static String boardStr="";

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Game");
        GridPane grid = new GridPane(); //Creating Grid
        for (int i = 0; i < 8; i++) {
            ColumnConstraints col = new ColumnConstraints(50);
            grid.getColumnConstraints().add(col);
        }
        for (int i = 0; i < 4; i++) {
            RowConstraints row = new RowConstraints(50);
            grid.getRowConstraints().add(row);
        }
        grid.setGridLinesVisible(true);
        grid.setLayoutX(700);
        grid.setLayoutY(10);
        tempy = 50;
        tempx = 100;
        TwistGame game =new TwistGame();
        GameBoard board= new GameBoard();

        List<ImageView> imgObjs = new ArrayList();// list of images
        List<boxcreator> boxes = new ArrayList();
        //For loop to iterate through everypiece
        for (int i = 0; i < 12; i++) {//12 pieces
            imgObjs.add(new ImageView());
            ImageView ivo = imgObjs.get(i);
            Image img = (new Image(Viewer.class.getResource(URI_BASE + ((char) (i + 97)) + ".png").toString()));
            ivo.setImage((new Image(Viewer.class.getResource(URI_BASE + ((char) (i + 97)) + ".png").toString(), img.getWidth() * 0.5, img.getHeight() * 0.5, false, false)));
            double width = ivo.getImage().getWidth(); // divide by 2 so to have cursor in middle of it when dragging
            double height = ivo.getImage().getHeight();
            boxes.add(new boxcreator(((char) (i + 97)), height, width));
            boxcreator b = boxes.get(i);
            if (i == 4) {
                tempx = 340;
                tempy = 50;
            } else if (i == 7) {
                tempx += 300;
                tempy = 230;
            } else if (i > 7) {
                tempy = 230;
                tempx += 50;
            }
            tempy += b.measurement;

            ivo.setX(tempx);
            ivo.setY(tempy);
            b.defaultxy(tempx, tempy);
            g1.setLevel(0);
            ivo.setOnMouseEntered(e -> {
                ivo.setEffect(g2);
            });
            b.updateflip(1);
            ivo.setOnMouseClicked(t -> {
                if (t.getButton() == MouseButton.SECONDARY) {
                    int sy = (ivo.getScaleY() == -1) ? 1 : -1;
                    b.updateflip(sy);
                    ivo.setScaleY(sy);
                }
            });
            ivo.setOnMouseExited(e -> {
                ivo.setEffect(g1);
            });
            ivo.setOnScroll(e -> {
                b.rotate();
                ivo.setRotate(b.rotate);
            });
            ivo.setOnMouseDragged(m -> {

                ivo.setY(m.getSceneY() - height / 2);//for centering piece upon drag
                ivo.setEffect(g2);
                ivo.setX(m.getSceneX() - width / 2);
                ivo.setOnMouseReleased(t -> {
                    if (ivo.getX() < 650 || ivo.getY() > 200 || ivo.getX() > 1100) {//If out of board image goes back to the default place
                        ivo.setX(b.x);
                        ivo.setY(b.y);
                    } else if (m.getSceneX() > 700 && m.getSceneY() < 1100 && m.getSceneY() < 230 && m.getSceneY() > 10) {//if it is within the board
                        double y = m.getSceneY() - height / 2;//for moving piece with cursor being centerd
                        double x = m.getSceneX() - width / 2;
                        double rotval = b.rotate;

                        int[] xyval = getrowcol(x, y, rotval, b.getchar());
                        System.out.println("col index : "+ xyval[0]+ " row index :  "+ xyval[1] + "  Game board : "+ boardStr);
                        int[] csrs = {(int) height / 50, (int) width / 50};
                        if ((b.rotate / 90) % 2 != 0) {//Switch cs and rs .
                            csrs[0] = (int) width / 50;
                            csrs[1] = (int) height / 50;
                        }

                        int[] gridVal={ xyval[0], xyval[1], csrs[1], csrs[0]};
                        b.setOrientation();
                        b.updategridval(gridVal);
                        b.updatepieceinfo();
                        String piece=b.getPieceinfo();
                        updateboard(piece);

                        try {
                            System.out.println(piece);
                            if(game.isPlacementStringValid(boardStr)){
                                if (b.rotate / 90 % 2 != 0 && !(b.getchar() == 'g' || b.getchar() == 'e')) {
                                    int translatex = (b.getchar() == 'h') ? -50 : (b.rotate == 90) ? -75 : -25;
                                    ivo.setTranslateX(translatex);
                                }
                                grid.add(ivo, xyval[0], xyval[1], csrs[1], csrs[0]);
                            }
                            else{
                                boardStr=boardStr.substring(0,boardStr.length()-4);
                                ivo.setX(b.x);//make the piece go back to default place
                                ivo.setY(b.y);
                            }


                        }//column index, rowindex, colspan, rowspan
                        catch (IllegalArgumentException e) {
                            ivo.setX(b.x);//make the piece go back to default place
                            ivo.setY(b.y);
                        }
                    }
                });
            });


            root.getChildren().add(ivo);

        }

        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        scene.setOnKeyPressed(c->{if(!c.getCharacter().equals("r")){
            String remove=  boardStr.substring(boardStr.length()-4);
            char ptype=remove.charAt(0);
            imgObjs.get(ptype-97).setX(boxes.get(ptype-97).x);
            imgObjs.get(ptype-97).setY(boxes.get(ptype-97).y);
        }
        });
        root.getChildren().add(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void updateboard(String piece){
        boardStr+=(piece);
    }

    private int[] getrowcol(double x, double y, double rotate, char ptype) {//returns respective grid row values
        int[] xyvals = new int[2];
        if (rotate == 0 || rotate == 180 || rotate == 360) {
            x = (x - 675 < 10) ? x + 10 : Math.ceil(x);
            xyvals[0] = (int) Math.round((x - 700) / 50);//ci
            xyvals[1] = (int) Math.round(((Math.ceil(y) - 10)) / 50);//ri
            return xyvals;
        } else {
            if (ptype == 'c') {
                x = Math.ceil(x) + 55;
                y = Math.ceil(y) - 56;
            } else if (ptype == 'h') {
                x = Math.ceil(x) + 55;
                y = Math.ceil(y) - 50;
            } else {
                x = (rotate == 90.0) ? Math.ceil(x) + 55 : Math.ceil(x) + 25;
                y = (rotate == 90.0) ? Math.ceil(y) - 5 : Math.ceil(y) - 25;
            }
            xyvals[0] = (int) Math.round(((x - 700)) / 50);//ci
            xyvals[1] = (int) Math.round(((y - 10)) / 50);//ri
            return xyvals;
        }
    }


    class boxcreator {//containers which will hold the pieces
        // each piece will have it's container
        char ptype;
        double measurement;//the offset calculated for the piece placesment (default)
        int rotate;
        double x, y;// default x position and y position
        double height, width;
        int gridVal[];//column index, rowindex, colspan, rowspan
        String pieceinfo;
        String orientation;
        int flip;//-1 means it is flipped


        void updateflip(int flip){
            this.flip=flip;
        }
        void setOrientation(){
            int rno=(rotate/90==4)?0:rotate/90;
            int orientationno=(flip==1)?rno:rno+4;
            orientation=Integer.toString(orientationno);
            //orientation=()
        }

        boxcreator(char ptype, double height, double width) {
            this.ptype = ptype;
            this.width = width;
            this.height = height;
            measurement = (ptype == 'a' || ptype == 'e' || (int) ptype > 104) ? 0 : (height > width) ? height : width;
        }

        void rotate() {
            if (rotate > 360) {
                rotate = 90;
            } else {
                rotate += 90;
            }
        }


        String getPieceinfo(){
            return pieceinfo;
        }


        void defaultxy(double x, double y) {
            this.x = x;
            this.y = y;
        }

        char getchar() {
            return ptype;
        }

        void updategridval(int[] gridVal){
            int r = Integer.parseInt(orientation);
            this.gridVal=gridVal;
            if(r==1||r==5){
                switch(ptype){
                    case 'c': case 'h':
                        break;
                    default:
                        this.gridVal[0]=gridVal[0]-1;
                        break;
                }

            }
            if(r==3&& ptype=='e'){

            }


        }

        void updatepieceinfo(){
            pieceinfo=ptype+Integer.toString(gridVal[0]+1)+((char)(gridVal[1]+65))+orientation;
        }


    }

    class PieceStats {
        //Will inherit from boxcontainer
        //holds rotate,default x coordinate and y coordinate
        //if is on board or not
        //if on board it  will contain the piece placement(the piece string form )
        //update piece if needed
    }

    class GameStatus {
        //contains the game data
        //
    }

   /* public static void main(String[] args) {
        for(int m=0;m<10;m++){
            System.out.println(piece());
            System.out.println(t.isPlacementStringValid(piece()));  ;
    }
    }*/

    // FIXME Task 7: Implement a basic playable Twist Game in JavaFX that only allows pieces to be placed in valid places
    //uses task 8(creates the base for the game) and 5 (check pieces can be used or not).6 should be used here

    // FIXME Task 8: Implement starting placements


    private String start_play() throws IOException {
        File filename = new File("assets/1.txt");
        FileReader read = new FileReader(filename);
        LineNumberReader reader = new LineNumberReader(read);

        Random rand = new Random();
        int line = rand.nextInt(100);
        String txt = "";
        int i = 0;
        while (txt != null) {
            i ++;
            txt = reader.readLine();
            if (i == line) {
                //System.out.println("Line" + line + ": " + reader.readLine());
                System.exit(0);
            }
        }
        reader.close();;
        read.close();

        return txt;
    }


    private void makeBoard() {
        Random rn = new Random();
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


    /*set opacity of selected pieces to a certain percentage  or
    use Blur effect for that certain piece (using setEffect) Use task 9 code for the solutions.*/
    // FIXME Task 10: Implement hints
    public static void hint() {

    }

    // FIXME Task 11: Generate interesting starting placements
    /*In reference to Difficulty level choose a certain state from Difficulty_level */

}
