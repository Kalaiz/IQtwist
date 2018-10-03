package comp1110.ass2.gui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;


/**
 * A very simple viewer for piece placements in the twist game.
 *
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {
    /* board layout */
    private static final int SQUARE_SIZE = 60;
    private static final int VIEWER_WIDTH = 750;
    private static final int VIEWER_HEIGHT = 500;
    private static final String URI_BASE = "assets/";
    private final Group root = new Group();
    private final Group controls = new Group();
    private static String iimages=""; //String of pieces in accordance to the input placement String .
    TextField textField;


    /**
     * Returns a String of  .png alphabets or rotation value or
     * column value or row value input should be
     * n =0 ; for column n=1; for row n=2;for orientation n=3
     * @param input is the piece encoding string
     * @param n; 0 - for .png alphabet
     *           1 - for column
     *           2 - for row
     *           3 - for orientation
     * @return Respective "n" string
     * Authorship:Kalai
     */
    public String returner(String input,int n ){
        String s="";
        for(int i=0;i<input.length();i++){
            if((i-n)%4==0){
                s+=input.charAt(i);
            }}
        return s;
    }


    /**
     * Gets the  respective rotation ,translation  and
     * flipping value for the respective 'e'th image_objs.
     * Only rotates  if the rotation value  is lesser than 4,If it
     * is greater than 3 it will flip (in accordance to line y) and
     * then rotates for example in a0A4. We flip the a piece , if
     * it is a7, we still flip the a0 and rotate it.
     * @param rotations2 is the String which consist of the entire rotation values of the input placement
     * @param e - index
     * @return Respective Rotation ,Translation & flipping  value .
     * Authorship:Kalai
     */
    private int[] rotator_translate(String rotations2,int e){
        int[] rts=new int[3];
        int r=Character.getNumericValue(rotations2.charAt(e));
        if(r>=4){
            r-=4;
            rts[2]=-1;//For Flip Horizontal set the scaleY variable of the ImageView to -1.
        }
        rts[0]=90*r;
        if(rts[0]==90||rts[0]==270){
            switch ((Viewer.iimages).charAt(e)){
                case 'a': case 'b':
                case 'd':case 'f':
                    rts[1]=-45;
                    break;
                case 'c':
                    rts[1]=-140;
                    break;
            }
        }
        return  rts;
    }


    /**
     * Returns an array of int values(irow and icol only )
     * for grid.add (for only one image_objs)
     * @param col column
     * @param  row row
     * @return array of  the respective row and column for the single image_objs
     * Authorship:Kalai
     */
    private int[] orientation_shw(char col,char row){
        int[] iric=new int[2];
        iric[0]=Character.getNumericValue(col)-1;
        iric[1]=(int) row-65;//using ascii encoding
        return iric;
    }

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement  A valid placement string
     * Authorship:LingYu Xia & Kalai
     * Idea: LingYu Xia - Usage of gridpane
     *       Kalai - Forming a grid interface and putting pieces over them .
     */
    //  Task 4: implement the simple placement viewer
    private void makePlacement(String placement) {
        //StackPane stack = new StackPane();
        GridPane grid = new GridPane(); //Creating Grid
        for(int i=0;i<8;i++){
            ColumnConstraints col = new ColumnConstraints(92);
            grid.getColumnConstraints().add(col);
        }

        for(int i=0;i<4;i++){
            RowConstraints row = new RowConstraints(92);
            grid.getRowConstraints().add(row);
        }
        grid.setLayoutX(8);//adjusting the grid

        grid.setGridLinesVisible(true);
        String row_val=returner(placement,2);
        String col_value=returner(placement,1);
        String rotations=returner(placement,3);
        Viewer.iimages=returner(placement,0);

        List <ImageView> image_objs=new ArrayList();
        for(int i=0;i<Viewer.iimages.length();i++){
            image_objs.add(new ImageView());
            (image_objs.get(i)).setImage(new Image(Viewer.class.getResource(URI_BASE+ Viewer.iimages.charAt(i)+".png").toString()));
            (image_objs.get(i)).setFitWidth((image_objs.get(i)).getImage().getWidth()*92/100);
            (image_objs.get(i)).setFitHeight((image_objs.get(i)).getImage().getHeight()*92/100);
        }

        for(int i=0;i<image_objs.size();i++){//Configuring all image_obj
            int[] t= rotator_translate(rotations,i);//Gets the  respective rotation ,translation  and flipping value
            if(t[2]==-1){
                image_objs.get(i).setScaleY(t[2]);//For Flip Horizontal set the scaleY variable of the ImageView to -1.
            }
            image_objs.get(i).setRotate(t[0]);
            image_objs.get(i).setTranslateX(t[1]);//to adjust the offset caused by rotation
            int rowspan=(int)((image_objs.get(i).getImage())).getHeight()/100;
            int colspan=(int)((image_objs.get(i).getImage()).getWidth())/100;
            int[] gridvalues=orientation_shw(col_value.charAt(i),row_val.charAt(i));
            if(Character.getNumericValue(rotations.charAt(i))%2==0)//Since rotations changes the row span and colspan
            { grid.add(image_objs.get(i),gridvalues[0],gridvalues[1],colspan,rowspan);}
            else{
                grid.add(image_objs.get(i),gridvalues[0],gridvalues[1],rowspan,colspan);}

        }
     /*TRANSLATION DATA
        R90    R180    R270  RS
      a -45     0      -45   N
      b -45     0      -45   N
      c -140    0     -140   N
      d -45     0      -45   N
      e  0      0       0    N
      f -45     0      -45   N
      g 0       0        0   N
      h 0       0        0   N
        */

        controls.getChildren().addAll(grid);
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls(){
        Label label1 = new Label("Placement:");
        textField = new TextField ();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());//attention
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TwistGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(controls);
        makeControls();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
