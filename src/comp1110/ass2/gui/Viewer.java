package comp1110.ass2.gui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
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
    TextField textField;

    /*Returns a String of  .png alphabets or rotation value
    for .png alphabet input should be  n =0 ; for rotation n=1*/
    public String returner(String input,int n ){
        String s="";
        for(int i=0;i<input.length();i++){
            if((i-n)%4==0){
                s+=input.charAt(i);
            }}
        return s;
    }

    /*return respective rotation ,translation value and
     flipping approval for the respective eth image_objs */
    public int[] rotator_translate(String rotations2,int e){
        int[] v1v2=new int[3];
        int rotation=0;
        int translation=0;
        if(Character.getNumericValue(rotations2.charAt(e))<4){
            rotation=90*Character.getNumericValue(rotations2.charAt(e));
            translation=0;//TODO : Get translation value
        }
        else {
            v1v2[2]=-1;//For Flip Horizontal set the scaleX variable of the ImageView to -1.
        }return  v1v2;
    }

    /* returns an array of int values(irow and icol only )
    for grid.add (for only one image_objs)*/
    public int[] orientation_shw(char row,char col){
        int[] iric=new int[2];
        iric[0]=Character.getNumericValue(col);
        iric[1]=(int) row-65;//using ascii encoding
        return iric;//TODO : Get grid values
    }

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement  A valid placement string
     */
    void makePlacement(String placement) { // FIXME Task 4: implement the simple placement viewer
        GridPane grid = new GridPane();//TODO :make this grid displayed without clicking on refresh
        for(int i=0;i<8;i++){
            ColumnConstraints col = new ColumnConstraints(VIEWER_WIDTH/8);
            grid.getColumnConstraints().add(col);
        }

        for(int i=0;i<4;i++){
            RowConstraints row = new RowConstraints((VIEWER_HEIGHT-50)/4);
            grid.getRowConstraints().add(row);
        }

        grid.setGridLinesVisible(true);
        String row_val=returner(placement,2);
        String col_value=returner(placement,3);
        String rotations=returner(placement,1);
        String images=returner(placement,0); //for making multiple imageview objects
        List <ImageView> image_objs=new ArrayList();
        for(int i=0;i<images.length();i++){
            image_objs.add(new ImageView());
            (image_objs.get(i)).setImage(new Image(Viewer.class.getResource(URI_BASE+ images.charAt(i)+".png").toString()));
        }

        for(int i=0;i<image_objs.size();i++){//Configuring all image_obj
            int[] t= rotator_translate(rotations,i);
            image_objs.get(i).setRotate(t[0]);
            image_objs.get(i).setTranslateX(t[1]);//to adjust the offset
            if(t[2]==-1){
                image_objs.get(i).setScaleX(-1);//For Flip Horizontal set the scaleX variable of the ImageView to -1.
            }
        }

        for(int i=0;i<image_objs.size();i++){
            int rowspan=(int)((image_objs.get(i).getImage())).getHeight()/100;
            //System.out.println(rowspan);
            int colspan=(int)((image_objs.get(i).getImage()).getWidth())/100;
            int[] gridvalues=orientation_shw(row_val.charAt(i),col_value.charAt(i));
            //System.out.println(gridvalues[0]+""+gridvalues[1]+""+colspan+""+rowspan);
            if(Character.getNumericValue(rotations.charAt(i))%2==0)//Since rotations changes the row span and colspan
            { grid.add(image_objs.get(i),gridvalues[0],gridvalues[1],colspan,rowspan);}
            else{grid.add(image_objs.get(i),gridvalues[0],gridvalues[1],rowspan,colspan);}
        }

/*     //TESTING
       ImageView imageView = new ImageView();
       Image image = new Image(Viewer.class.getResource(URI_BASE+"a.png").toString());
       imageView.setFitWidth(image.getWidth()*0.9);
       imageView.setFitHeight(image.getHeight()*0.9);
       imageView.setImage(image);

       ImageView imageView1 = new ImageView();
       Image image1 = new Image(Viewer.class.getResource(URI_BASE+"c.png").toString());
       imageView1.setFitWidth(372);
       imageView1.setFitHeight(80);
       imageView1.setImage(image1);

       ImageView imageView2 = new ImageView();//whenever setRotate is being used,it causes some misalignment.
       Image image2 = new Image(Viewer.class.getResource(URI_BASE+"e.png").toString());
       imageView2.setImage(image2);
       imageView2.setScaleX(-1);
       imageView2.setRotate(90);
      //imageView2.setTranslateX(-45);//to adjust the offset

      TRANSLATION DATA
      when rotate is 90 or 270 -Translation is needed ;does not matter whether flipped or not.
      360 and 180 does not require translation.
      e piece does not need any changes.rotation 180 ,

       grid.add(imageView,1,2,2,3);
       grid.add(imageView1,2,0,4,1);
       grid.add(imageView2,5,1,2,2);*/


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