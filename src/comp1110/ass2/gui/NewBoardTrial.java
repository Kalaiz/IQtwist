package comp1110.ass2.gui;


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

import java.util.Iterator;

public class NewBoardTrial extends Application {
    private static String gameState = "";
    private static final int DISPLAY_WIDTH = 1280;
    private static final int DISPLAY_HEIGHT = 649;
    private static final String URI_BASE = "assets/";

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
        int pieceType;
        double defaultX;//Default x coordinate position on start of the game
        double defaultY;//Default y coordinate position on start of the game
        ImageView holder;

        piece(char pieceType) {
            holder= new ImageView();//Since grid needs a node
            Image img = (new Image(Viewer.class.getResource(URI_BASE + pieceType + ".png").toString()));
            holder.setImage(img);
            root.getChildren().add(holder);
            double height= img.getHeight()*0.5;
            double width= img.getWidth()*0.5;
            holder.setFitHeight(height);
            holder.setFitWidth(width);
            this.pieceType=pieceType-97;// according to ascii encoding
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


        eventPiece(char piece){
            super(piece);
            flip=false;//Declaring

            holder.setOnScroll(scroll->{//Rotation on scroll
                if(!grid.getChildren().contains(holder)){//If grid does not contain the piece .
                    holder.setRotate(rotate);
                    rotate =(rotate>=360)?0:rotate+90;
                }
            });

            holder.setOnMouseEntered(geffect->{ //Glow effect
                holder.setEffect(g1);
            });

            holder.setOnMouseExited(ageffect->{//Anti glow effect
                holder.setEffect(g2);
            });

            holder.setOnMouseClicked(click->{//Flip  on right click
                if(click.getButton()==MouseButton.SECONDARY&&!grid.getChildren().contains(holder) ){
                    if(flip){holder.setScaleX(1); flip=false;}else{holder.setScaleX(-1); flip=true;}
                }


            });

            holder.setOnMouseDragged(drag->{
                //Divide by 2 so to pull it by the center & using getScene for relative positioning
                double centerX=drag.getSceneX()-holder.getFitWidth()/2;
                double centerY=drag.getSceneY()-holder.getFitHeight()/2;
                holder.setY(centerY);
                holder.setX(centerX);
                //Actual cursor position - image's top left corner.
                double ScreenPositionX = drag.getScreenX();
                double ScreenPositionY = drag.getScreenY();


                // System.out.println("X is " +(drag.getScreenX()) +" Y is  " + (drag.getScreenY())+" ");//TESTING
                holder.setOnMouseReleased(released->{
                    if(ScreenPositionX<700||ScreenPositionX>1100||ScreenPositionY>210||ScreenPositionY<10){
                        holder.setX(defaultX);
                        holder.setY(defaultY);
                    }
                    else{ setOnGrid(ScreenPositionX,ScreenPositionY);


                    }

                });

            });


        }


        void setOnGrid(double positionalX,double positionalY){
            // 50- width of each grid
            // 700 & 10 - the starting co-ordinates  of the grid
            gridCol=(int)((positionalX-700)/50)-1;
            gridRow=(int)((positionalY-10)/50)-1;
            int rs= (int)holder.getImage().getHeight()/100;
            int cs= (int)holder.getImage().getWidth()/100;
            grid.add(holder,gridCol,gridRow,cs,rs); //System.out.println("GridCol: " + gridCol + " GridRow:" +gridRow + " RowSpan : " + rs + "ColSpan: "+cs);


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
