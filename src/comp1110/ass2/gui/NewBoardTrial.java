package comp1110.ass2.gui;

import comp1110.ass2.GameBoard;
import comp1110.ass2.Pieces;
import comp1110.ass2.StartPieces;
import comp1110.ass2.TwistGame;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewBoardTrial extends Application {
    private static final int DISPLAY_WIDTH = 1280;
    private static final int DISPLAY_HEIGHT = 649;
    private static final String URI_BASE = "assets/";
    
    /*NODE Groups*/
    private Group root = new Group();
    private Group board = new Group();
    private Group pieces = new Group();

    /* Default (home) x & y coordinates*/
    static final double[] hxy= {100,50,100,200,100,400,740,380,340
                                ,50,340,200,340,350,640,380};


    /* the difficulty slider */
    private final Slider difficulty = new Slider();

    /*Sets up the board*/
    private void makeBoard() {
        board.getChildren().clear();
        //Creating Grid
        GridPane grid = new GridPane();
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
        double defaultY;//Default y  coordinate position on start of the game


        piece(char pieceType) {
            Image img = (new Image(Viewer.class.getResource(URI_BASE + pieceType + ".png").toString()));
            setImage(img);
            double height= img.getHeight()*0.5;
            double width= img.getWidth()*0.5;
            setFitHeight(height);
            setFitWidth(width);
            setXY(img);
            this.pieceType = pieceType-97;//ascii encoding
        }

        void setXY(Image img ){
            for (int x =0;x<pieceType;x++){//in reference to home x and y
                setLayoutX(hxy[2*x]);
                setLayoutY(hxy[2*x+1]);
            }

        }
    }

    /*Creates all required pieces -- for the start of the game*/
    private void createPieces() {
        pieces.getChildren().clear();
        for (char ch = 'a'; ch <= 'h'; ch++) {//for only pieces not for pegs as pegs can't be placed by players
            pieces.getChildren().add(new piece(ch));
        }
    }


    /*Start of JavaFX operations */
    @Override
    public void start (Stage primaryStage) throws Exception {
        primaryStage.setTitle("IQ-twist");//sets the title name on the bar
        Scene scene = new Scene(root, DISPLAY_WIDTH, DISPLAY_HEIGHT);
        createPieces();
        makeBoard();
        root.getChildren().add(board);
        root.getChildren().add(pieces);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



}
