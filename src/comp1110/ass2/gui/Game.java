package comp1110.ass2.gui;
import com.sun.prism.paint.Color;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
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
//USE BUFFERED IMAGES
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
        GridPane grid = new GridPane(); //Creating Grid
        for(int i=0;i<8;i++){
            ColumnConstraints col = new ColumnConstraints(50);
            grid.getColumnConstraints().add(col);

        }
        for(int i=0;i<4;i++){
            RowConstraints row = new RowConstraints(50);
            grid.getRowConstraints().add(row);
        }
        grid.setGridLinesVisible(true);
        grid.setLayoutX(700);
        grid.setLayoutY(10);
  /*      ImageView iv=new ImageView();
        iv.setImage((new Image(Viewer.class.getResource(URI_BASE+ "h" +".png").toString())));
        iv.setFitWidth(iv.getImage().getWidth()*0.5);
        iv.setFitHeight(iv.getImage().getHeight()*0.5);*/
        /*iv.setScaleY(0.5);//causing problems
        iv.setScaleX(0.5);*/
        /*iv.setTranslateX(-50);
        iv.setTranslateY(-25);*/

        //grid.add(iv,7,2,1,3);
tempy=50;
tempx=100;
        List<ImageView> imgObjs=new ArrayList();// list of images
        List<boxcreator> boxes = new ArrayList();
        //For loop to iterate through everypiece
        for(int i=0;i<12;i++){//12 pieces
            imgObjs.add(new ImageView());
            ImageView ivo=imgObjs.get(i);
            //need to make it syntatically sugar
            double imageWidth = (new Image(Viewer.class.getResource(URI_BASE+ ((char) (i +97)) +".png").toString())).getWidth();
            double imageHeight=(new Image(Viewer.class.getResource(URI_BASE+ ((char) (i +97)) +".png").toString()).getHeight());
            ivo.setImage((new Image(Viewer.class.getResource(URI_BASE+ ((char) (i +97)) +".png").toString(),imageWidth*0.5,imageHeight*0.5,false,false)));
            double width = ivo.getImage().getWidth()/2; // divide by 2 so to have cursor in middle of it when dragging
            double height= ivo.getImage().getHeight()/2;
           boxes.add(new boxcreator(((char)(i+97)),height*2,width*2));
           boxcreator b = boxes.get(i);

           //ivo.setTranslateX(b.getTranslation());
           // System.out.println(ivo.getImage().getWidth());
            /*ivo.setScaleX(0.5);
            ivo.setScaleY(0.5);*///CAUSING PROBLEMS DO NOT USE !!!!!
            //ivo.resize(iv.getImage().getWidth()*0.25,iv.getImage().getHeight()*0.25);
            //ivo.setFitHeight(iv.getImage().getHeight()*50/100);
            if(i==4){
                tempx=340;
                tempy=50; }
            else if(i==7){
                tempx+=300;
                tempy=230;
            }
            else if(i>7){
                tempy=230;
                tempx+=50;
            }
            System.out.println(b.measurement);
           tempy+=b.measurement;
            ivo.setX(tempx);
            ivo.setY(tempy);
            b.defaultxy(tempx,tempy);
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
            ivo.setOnMouseDragged(m->{ivo.setY(m.getSceneY()-height);//for centering piece upon drag
               ivo.setEffect(g2);
           /*    System.out.println("Screen x"+m.getScreenX());
                System.out.println(m.getScreenY());
                System.out.println(m.getSceneX());*/
            ivo.setX(m.getSceneX()-width);


          ivo.setOnMouseReleased(t->{
              if(m.getSceneX()>700&&m.getSceneY()<1100&&m.getSceneY()<230&&m.getSceneY()>10){//if it is within the board
                  System.out.println((m.getSceneX()-width) +" "+(m.getSceneY()-height));
                  //Add condition here stating that if rotation has occured you shd change th g
                  double y=m.getSceneY()-height;
                  double x= m.getSceneX()-width;
    /*if(b.rotate==90){
        x+=25;
        //if(getrowcol(x,y)[])
        ivo.setTranslateY(-49);
        ivo.setTranslateX(-24);
    }*/
              int[] xyval=getrowcol(x,y);
              //b.updateGridVal(xyval[1],xyval[0],);
                  System.out.println("width is "+width);
                  System.out.println("height is "+height);
                  int[] csrs={(int)height/25,(int)width/25};
                  if((b.rotate/90)%2!=0){

                      csrs[0]=(int)width/25;
                      csrs[1]=(int)height/25;
                  }

                  System.out.println(xyval[0]+  " " +xyval[1]+" "+ csrs[1]+" " + csrs[0]);
                grid.add(ivo,xyval[0],xyval[1],csrs[1],csrs[0]);//use an outer function ,for loop might be causing troubles
                //column index, rowindex, colspan, rowspan
              }});

            });

        root.getChildren().add(ivo);

        }



        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(grid);
        primaryStage.setScene(scene);
        primaryStage.show();



    }

    public int[] getrowcol(double x,double y){//returns respective grid row values
          int[] xyvals= new int[2];

          x=(x-675<10)?x+10:Math.ceil(x);
          y=Math.ceil(y);
          xyvals[0]=(int) Math.round((x-700)/50);
          xyvals[1]=(int)Math.round(((y-10))/50);

    return xyvals;}


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
                rotate += 90;
            }
        }

        double getx(){
            return x;
        }
        double gety(){
            return y;
        }
        int getrotate(){
     return rotate;
        }

        void defaultxy(double x,double y){
            this.x=x;
            this.y=y;
        }

    /*    int[]csrs(){
            int[] csrs={(int)height/100,(int)width/100};
            if((rotate/90)%2!=0){
                csrs[0]=(int)width/100;
                csrs[1]=(int)height/100;
            }
           return csrs;
        }*/
        void updateGridVal(int[]vals){


        }
        int getTranslation() {
            if (rotate / 90 % 2 == 0) {
                return translation = 0;
            } else {
                switch (ptype) {
                    case 'a': case 'b':
                     case 'd': case 'f':
                        return translation = -45;
                    case 'c':
                       return  translation = -140;
                    default:
                         return  translation = 0;
                }
            }
        }


    }
    class DragPieces  {
        int pX, pY;
        double x, y; // the position in the window where the mask should be when not on the board
        public void attachtoGrid(){

        }


    }
}
