package comp1110.ass2.gui;
import com.sun.prism.paint.Color;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Game extends Application{
    private static final int VIEWER_WIDTH = 1280;
    private static final int VIEWER_HEIGHT = 640;
    private static final String URI_BASE = "assets/";
    private final Group root = new Group();
    private final Group controls = new Group();
    static double tempx;//for positioning
    static double tempy;//for positioning

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Game");


        List<ImageView> imgObjs=new ArrayList();// list of images
        //For loop to iterate through everypiece
        for(int i=0;i<12;i++){//12 pieces
            imgObjs.add(new ImageView());
            ImageView ivo=imgObjs.get(i);
            ivo.setImage((new Image(Viewer.class.getResource(URI_BASE+ ((char) (i +97)) +".png").toString())));
           double width = ivo.getImage().getWidth()/2;//scaling reduces size
           double height= ivo.getImage().getHeight()/2;//scaling reduces size
          boxcreator b = new boxcreator(((char)(i+97)),height,width);
            ivo.setScaleY(0.5);
            ivo.setScaleX(0.5);
            if(i==4){
                tempx=340;
                tempy=0; }
            else if(i==7){
                tempx+=300;
                tempy=230;
            }
            else if(i>7){
                tempy=230;
                tempx+=50;
            }
           tempy+=b.measurement;;
            ivo.setX(tempx);
            ivo.setY(tempy);
            Glow g = new Glow();
            g.setLevel(0.9);
            Glow g2 = new Glow();
            g.setLevel(0);
            ivo.setOnMouseEntered(e-> {
         ivo.setEffect(g2);});
            ivo.setOnMouseExited(e-> {ivo.setEffect(g);});
            ivo.setOnScroll(e-> {
                b.rotate();
                ivo.setRotate(b.rotate);});

        root.getChildren().add(ivo);

        }

        GridPane grid = new GridPane(); //Creating Grid
        for(int i=0;i<8;i++){
            ColumnConstraints col = new ColumnConstraints(48);
            grid.getColumnConstraints().add(col);
        }
        for(int i=0;i<4;i++){
            RowConstraints row = new RowConstraints(48);
            grid.getRowConstraints().add(row);
        }
        grid.setGridLinesVisible(true);
        grid.setLayoutX(700);
        grid.setLayoutY(10);

        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(grid);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    class boxcreator{//containers which will hold the pieces
        // each piece will have it's container
        double measurement;
        int rotate;

        boxcreator(char ptype,double height,double width){
                measurement=(ptype=='a'||ptype=='e'||(int)ptype>104)? 0 :(height > width) ? height : width; }

        void rotate(){
            if(rotate>360){
                rotate=90;
            }
            else{
                rotate+=90;
            }
        }
        int getrotate(){
     return rotate;
        }
    }
    //DRAFT CODES
     /*ImageView img = new ImageView();
        img.setImage((new Image(Game.class.getResource(URI_BASE+ "a.png").toString())));
        img.setScaleX(0.5);
        img.setScaleY(0.5);
        ImageView img2 = new ImageView();
        img2.setImage((new Image(Game.class.getResource(URI_BASE+ "g.png").toString())));
        img2.setScaleX(0.5);
        img2.setScaleY(0.5);
        img2.setX(0);
        img2.setY(90);
        Glow g = new Glow();
        g.setLevel(0.9);
        Glow g2 =new Glow();
        g2.setLevel(0);
        int t=0;

     img.setOnMouseEntered(e-> {
     img.setEffect(g);});
     img.setOnMouseExited(e-> {img.setEffect(g2);});
        img.setOnMouseClicked(e -> {
            img.setRotate(90);
        });*/
/*
    public class InGameBoard extends ImageView{//creates an board

    }

    public class PieceBoard extends ImageView{//creates a box with all pieces



    }*/
}
