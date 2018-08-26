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


    public String returner(String input,int n ){//n =0 for .png alphabet ;n=1 for rotation
        String s="";
        for(int i=0;i<input.length();i++){
            if((i-n)%4==0){
                s+=input.charAt(i);
            }}
        return s;

    }
    public int[] rotator_translate(String input3,int e){//return respective rotation ,translation value and  flipping approval.
        int[] v1v2=new int[3];
        int rotation=0;
        int translation=0;
        if(Character.getNumericValue(input3.charAt(e))<4){
                rotation=90*Character.getNumericValue(input3.charAt(e));
                translation=0;//TODO : Get translation value


        }
        else {
            v1v2[2]=-1;//For Flip Horizontal set the scaleX variable of the ImageView to -1.
        }return  v1v2;
    }

    public int[] position(String input2){//positioning method (in accordance to grid add)
        return null;//TODO : Get grid values
    }
    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement  A valid placement string
     */
    void makePlacement(String placement) {

        // FIXME Task 4: implement the simple placement viewer

        GridPane grid = new GridPane();
        for(int i=0;i<8;i++){
            ColumnConstraints col = new ColumnConstraints(VIEWER_WIDTH/8);
            grid.getColumnConstraints().add(col);
        }

        for(int i=0;i<4;i++){
            RowConstraints row = new RowConstraints((VIEWER_HEIGHT-50)/4);
            grid.getRowConstraints().add(row);
        }


        String rotations=returner(placement,1);
        String images=returner(placement,0); //for making multiple imageview objects
        List <ImageView> imageviews=new ArrayList();
       for(int i=0;i<images.length();i++){
           imageviews.add(new ImageView());
           (imageviews.get(i)).setImage(new Image(Viewer.class.getResource(URI_BASE+ images.charAt(i)+".png").toString()));
       }




       for(int i=0;i<imageviews.size();i++){
          int[] t= rotator_translate(rotations,i);
           imageviews.get(i).setRotate(t[0]);
           imageviews.get(i).setTranslateX(t[1]);//to adjust the offset
           if(t[2]==-1){
               imageviews.get(i).setScaleX(-1);//For Flip Horizontal set the scaleX variable of the ImageView to -1.
           }



       }




 /*
        ImageView imageView = new ImageView();
        Image image = new Image(Viewer.class.getResource(URI_BASE+"a.png").toString());
        imageView.setFitWidth(279);
        imageView.setFitHeight(190);
        imageView.setImage(image);
        imageView.setRotate(90);

        ImageView imageView1 = new ImageView();
        Image image1 = new Image(Viewer.class.getResource(URI_BASE+"c.png").toString());
        imageView1.setFitWidth(372);
        imageView1.setFitHeight(80);
        imageView1.setImage(image1);

        ImageView imageView2 = new ImageView();//whenever setRotate is being used,it causes some misalignment.
        Image image2 = new Image(Viewer.class.getResource(URI_BASE+"f.png").toString());
        imageView2.setFitWidth(279);
        imageView2.setFitHeight(190);
        imageView2.setImage(image2);
        imageView2.setRotate(270);
        imageView2.setTranslateX(-45);//to adjust the offset


        grid.add(imageView,1,2,2,3);
        grid.add(imageView1,2,0,4,1);
        grid.add(imageView2,5,1,2,3);
        grid.setGridLinesVisible(true);
        */
        controls.getChildren().addAll(grid);
    }


    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
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
