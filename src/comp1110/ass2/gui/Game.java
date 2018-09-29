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

public class Game extends Application{
    private static final int VIEWER_WIDTH = 1280;
    private static final int VIEWER_HEIGHT = 640;
    private static final String URI_BASE = "assets/";
    private final Group root = new Group();
    private final Group controls = new Group();
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Game");
        ImageView img = new ImageView();
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
     img.setOnMouseEntered(e-> {
     img.setEffect(g);});
     img.setOnMouseExited(e-> {img.setEffect(g2);});
        img.setOnMouseClicked(e -> img.setRotate(90));
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
        grid.setLayoutY(100);


        //Rectangle r = new Rectangle(500,300,400 ,200);


        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(img);
        root.getChildren().add(img2);
        //root.getChildren().add(r);
        root.getChildren().add(grid);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    public class InGameBoard extends ImageView{//creates an board

    }

    public class PieceBoard extends ImageView{//creates a box with all pieces



    }
}
