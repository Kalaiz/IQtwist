package comp1110.ass2.gui;


import comp1110.ass2.TwistGame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.xml.ws.Holder;
import java.util.Iterator;

public class NewBoardTrial extends Application {
    private static String gameState = "";
    private static final int DISPLAY_WIDTH = 1280;
    private static final int DISPLAY_HEIGHT = 649;
    private static final String URI_BASE = "assets/";

    /*Game object*/
    TwistGame game=new TwistGame();

    /*NODE Groups*/
    private Group root = new Group();
    private Group board = new Group();
    private Group pieces = new Group();
    private Group controls = new Group();


    /* Grid  */
    static GridPane grid = new GridPane();

    /* Default (home) x & y coordinates*/
    static final double[] hxy= {100,50,100,200,100,400,740,380,340
                                 ,50,340,200,340,350,540,380};

    /* the difficulty slider */
    private final Slider difficulty = new Slider();

    /*Glow objects*/
    private Glow g1=new Glow(0.5);
    private Glow g2=new Glow();

    /*Sets up the board*/
    private void makeBoard() {
        board.getChildren().clear();
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
        board.getChildren().add(grid);
        //board.toBack();      //places the node it at the back
    }




    /* Inner class for pieces*/
    class piece extends ImageView {
        int pieceType;//The actual ascii number.
        double defaultX;//Default x coordinate position on start of the game
        double defaultY;//Default y coordinate position on start of the game
        ImageView holder;

        piece(char pieceType) {
            holder= new ImageView();//Since grid needs a node
            Image img = (new Image(Viewer.class.getResource(URI_BASE + pieceType + ".png").toString()));
            holder.setImage(img);
            root.getChildren().add(holder);
            double height= img.getHeight()*0.5;//Resizing the image
            double width= img.getWidth()*0.5;
            holder.setFitHeight(height);
            holder.setFitWidth(width);
            this.pieceType=pieceType;// according to ascii encoding
            defaultX=hxy[2*(pieceType-97)];
            defaultY=hxy[(2*(pieceType-97))+1];
            holder.setX(defaultX);
            holder.setY(defaultY);

        }



    }
    class eventPiece extends piece{
        String pieceInfo;
        int gridRow;//placement on the grid
        int gridCol;
        boolean flip;
        int rotate;

        void reset(){
            holder.setX(defaultX);
            holder.setY(defaultY);
            holder.setRotate(0);
            holder.setScaleX(1);
        }


        private void decodePieces(){
            int col = gridCol + 1;//As the string for the column (piece-encoding) starts from 1 .
            int orientation=(flip)?(int)(holder.getRotate()/90)-4:(int)(holder.getRotate()/90);
                    // Character of the piece + Column:number + Row:Alpha + orientation:number
            pieceInfo = Character.toString((char)pieceType)+ col + ((char) (gridRow + 65)) + orientation;
        }





    eventPiece(char piece){
            super(piece);
            flip=false;//Declaring
            holder.setOnScroll(scroll->{//Rotation on scroll
                if(!grid.getChildren().contains(holder)){//If grid does not contain the piece .
                    rotate+=90;
                    rotate=(rotate>=360)?0:rotate;
                    holder.setRotate(rotate);
                }
            });

            holder.setOnMouseEntered(geffect->{ //Glow effect
                holder.setEffect(g1);
            });

            holder.setOnMouseExited(ageffect->{//Anti glow effect
                holder.setEffect(g2);
            });

            holder.setOnMouseClicked(click->{//Flip on right click
                if(click.getButton()==MouseButton.SECONDARY&&!grid.getChildren().contains(holder) ){
                    if(flip){holder.setScaleX(1); flip=false;}else{holder.setScaleX(-1); flip=true;}
                }


            });

            holder.setOnMouseDragged(drag->{
                double cornerX=drag.getSceneX();
                double cornerY=drag.getSceneY();
                //Divide by 2 so to pull it by the center & using getScene for relative positioning
                holder.setY(cornerY-holder.getFitHeight()/2);
                holder.setX(cornerX-holder.getFitWidth()/2);
                //Image remains non rotated according to Javafx(Grid).
                double imgCornerx=((rotate/90)%2==0)?holder.getX():holder.getX()+holder.getFitWidth()/2.5;
                double imgCornery=((rotate/90)%2==0)?holder.getY():cornerY-holder.getFitWidth()/2.25;
                System.out.println("Height of image "+ holder.getFitHeight() +" width of image " + holder.getFitWidth());
                System.out.println("X is " +(drag.getSceneX()) +" Y is  " + (drag.getSceneY())+" ");//TESTING
                System.out.println("getX is : " + holder.getX()+" getY is: " +holder.getY());
                System.out.println("imgCornerx: " + imgCornerx+"  imgcY:  " +imgCornery);
                holder.setOnMouseReleased(released->{
                    //not exactly the coordinates of the grid as to allow flexibility for the user
                    if(imgCornerx<680||imgCornerx>1080||imgCornery>200||imgCornery<0){
                        holder.setX(defaultX);
                        holder.setY(defaultY);
                    }
                    else{ setOnGrid(imgCornerx,imgCornery); }// Image's top left corner.
                });

            });


        }


        void setOnGrid(double positionalX,double positionalY){// Image's top left corner.
            // 50 - width of each grid
            // 700 & 10 - the starting co-ordinates  of the grid
            System.out.println("X: " + positionalX+"  Y: " + positionalY);
            gridCol=(int)(positionalX-698)/50;
            gridRow=(int)(positionalY-8)/50;
            int rowspan=(int)holder.getImage().getHeight()/100;
            int colspan=(int)holder.getImage().getWidth()/100;
            int rs=((rotate/90)%2==0)?rowspan:colspan;//Switching the row and col span upon rotation
            int cs= ((rotate/90)%2==0)?colspan:rowspan;
            int translateX=((rotate/90)%2==0)?0:-(int)(holder.getFitWidth()-holder.getFitHeight())/2;//(-(int)(holder.getFitWidth()/2.65))
            System.out.println("Translation value  " + translateX);
            holder.setTranslateX(translateX);//offset created upon setting the image  on the grid
            decodePieces();//Converting available data into piece encoding
            gameState+=pieceInfo;
            System.out.println(gameState);
            if(game.isPlacementStringValid(gameState)){
            grid.add(holder,gridCol,gridRow,cs,rs);}
            else{
                holder.setX(defaultX);
                holder.setY(defaultY);
                //updating the gameState string if the position is not valid.
                gameState=gameState.substring(0,gameState.length()-4);
            }
            System.out.println(rotate);
            System.out.println("GridCol: " + gridCol + " GridRow: " +gridRow + " RowSpan: " + rs + " ColSpan: "+cs);

        }

        private void decodePieces(){

            int col = gridCol + 1;
            int num = 0;

            if (flip){
                num = (int) getRotate() + 4;
            } else{ num = (int) getRotate();}

            String decPie = (char)pieceType + "" + col + ((char) (gridRow + 65)) + num;
            System.out.println(decPie);

            pieceInfo=decPie;
        }

    }

    /*Creates all required pieces -- for the start of the game*/
    private void createPieces() {
        pieces.getChildren().clear();
        for (char ch = 'a'; ch <= 'h'; ch++) {//for only pieces not for pegs as pegs can't be placed by players
            pieces.getChildren().add(new eventPiece(ch));
        }
    }


    /**
     * Start a new game & clear the previous board
     */
    private void newGame() {
        createPieces();
        makeBoard();
    }


    private void resetgame(){
        board.getChildren().clear();
        Iterator griditer=grid.getChildren().iterator();
        grid = new GridPane();
       /* while(griditer.hasNext()){
          eventPiece p  = (eventPiece) griditer.next();
         root.getChildren().add(new eventPiece((char)p.pieceType));
       }*/


        for (Node n : pieces.getChildren()) {
            ((eventPiece) n).reset();
           // grid.getChildren().remove(((eventPiece) n).holder);
        }
        makeBoard();



    }

    /**
     * Create the controls that allow the game to be restarted and the difficulty
     * level set.
     */
    private void makeControls() {
        Button bt = new Button("Restart");
        bt.setLayoutX(DISPLAY_WIDTH / 4 + 30);
        bt.setLayoutY(DISPLAY_HEIGHT - 45);
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                resetgame();
            }
        });
        controls.getChildren().add(bt);
        difficulty.setMin(1);
        difficulty.setMax(4);
        difficulty.setValue(0);
        difficulty.setShowTickLabels(true);
        difficulty.setShowTickMarks(true);
        difficulty.setMajorTickUnit(1);
        difficulty.setMinorTickCount(1);
        difficulty.setSnapToTicks(true);
        difficulty.setLayoutX(DISPLAY_WIDTH / 4 - 140);
        difficulty.setLayoutY(DISPLAY_HEIGHT - 40);
        controls.getChildren().add(difficulty);
        final Label difficultyCaption = new Label("Difficulty:");
        difficultyCaption.setTextFill(Color.GREY);
        difficultyCaption.setLayoutX(DISPLAY_WIDTH / 4 - 210);
        difficultyCaption.setLayoutY(DISPLAY_HEIGHT - 40);
        controls.getChildren().add(difficultyCaption);

        root.getChildren().add(controls);
    }

    /*Start of JavaFX operations */
    @Override
    public void start (Stage primaryStage) throws Exception {
        primaryStage.setTitle("IQ-twist");//sets the title name on the bar
        Scene scene = new Scene(root, DISPLAY_WIDTH, DISPLAY_HEIGHT);
        newGame();
        root.getChildren().add(board);
        root.getChildren().add(pieces);
        makeControls();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
