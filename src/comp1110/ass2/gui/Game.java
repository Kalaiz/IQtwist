package comp1110.ass2.gui;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
    Glow g2 = new Glow();
    Glow g = new Glow();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Game");
        GridPane grid = new GridPane(); //Creating Grid
        for(int i=0;i<8;i++){
            ColumnConstraints col = new ColumnConstraints(50);
            grid.getColumnConstraints().add(col); }
        for(int i=0;i<4;i++){
            RowConstraints row = new RowConstraints(50);
            grid.getRowConstraints().add(row); }
        grid.setGridLinesVisible(true);
        grid.setLayoutX(700);
        grid.setLayoutY(10);
        tempy=50;
        tempx=100;
        List<ImageView> imgObjs=new ArrayList();// list of images
        List<boxcreator> boxes = new ArrayList();
        //For loop to iterate through everypiece
        for(int i=0;i<12;i++){//12 pieces
            imgObjs.add(new ImageView());
            ImageView ivo=imgObjs.get(i);
            Image img= (new Image(Viewer.class.getResource(URI_BASE+ ((char) (i +97)) +".png").toString()));
            ivo.setImage((new Image(Viewer.class.getResource(URI_BASE+ ((char) (i +97)) +".png").toString(),img.getWidth()*0.5,img.getHeight()*0.5,false,false)));
            double width = ivo.getImage().getWidth(); // divide by 2 so to have cursor in middle of it when dragging
            double height= ivo.getImage().getHeight();
            boxes.add(new boxcreator(((char)(i+97)),height,width));
            boxcreator b = boxes.get(i);
            if(i==4){
                tempx=340;
                tempy=50; }
            else if(i==7){
                tempx+=300;
                tempy=230; }
            else if(i>7){
                tempy=230;
                tempx+=50; }
            tempy+=b.measurement;
            ivo.setX(tempx);
            ivo.setY(tempy);
            b.defaultxy(tempx,tempy);
            g.setLevel(0);
               ivo.setOnMouseEntered(e-> {
               ivo.setEffect(g2);});
               ivo.setOnMouseExited(e-> {ivo.setEffect(g);});
               ivo.setOnScroll(e-> {b.rotate();ivo.setRotate(b.rotate);});
               ivo.setOnMouseDragged(m->{
                     ivo.setY(m.getSceneY()-height/2);//for centering piece upon drag
                     ivo.setEffect(g2);
                     ivo.setX(m.getSceneX()-width/2);
               ivo.setOnMouseReleased(t->{
                    if(m.getSceneX()>700&&m.getSceneY()<1100&&m.getSceneY()<230&&m.getSceneY()>10){//if it is within the board
                        double y=m.getSceneY()-height/2;
                        double x= m.getSceneX()-width/2;
                        double rotval=b.rotate;
                        if(b.rotate/90%2!=0&&!(b.getchar()=='g'||b.getchar()=='e')){
                                 int translatex = (b.getchar()=='h')?-50:(b.rotate==90)?-75:-25;
                                 ivo.setTranslateX(translatex); }
                        int[] xyval=getrowcol(x,y,rotval,b.getchar());
                        int[] csrs={(int)height/50,(int)width/50};
                        if((b.rotate/90)%2!=0){//Switch cs and rs .
                            csrs[0]=(int)width/50;
                            csrs[1]=(int)height/50; }
                        try{grid.add(ivo,xyval[0],xyval[1],csrs[1],csrs[0]);}//column index, rowindex, colspan, rowspan
                        catch(IllegalArgumentException e){
                            //make the piece go back to default place
                        } } });
            });
            root.getChildren().add(ivo);
        }
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public int[] getrowcol(double x,double y,double rotate,char ptype) {//returns respective grid row values
        int[] xyvals = new int[2];
        if (rotate == 0||rotate==180||rotate==360) {
            x = (x - 675 < 10) ? x + 10 : Math.ceil(x);
            y = Math.ceil(y);
            xyvals[0] = (int) Math.round((x - 700) / 50);//ci
            xyvals[1] = (int) Math.round(((y - 10)) / 50);//ri
            return xyvals; }
        else {
            if( ptype=='c'){
            x=Math.ceil(x)+55;
            y=Math.ceil(y)-56; }
            else if(ptype=='h'){
             x = Math.ceil(x)+55;
             y = Math.ceil(y)-50; }
            else {
            x = (rotate==90.0)?Math.ceil(x)+55:Math.ceil(x)+25;
            y = (rotate==90.0)?Math.ceil(y)-5:Math.ceil(y)-25;}
            xyvals[0] = (int) Math.round(((x - 700)) / 50);//ci
            xyvals[1] = (int) Math.round(((y - 10)) / 50);//ri
            return xyvals; }

    }


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
                rotate += 90; }
            }
             void defaultxy(double x,double y){
             this.x=x;
             this.y=y; }

        char getchar(){
            return ptype; }


    }
    class PieceStats{//Will inherit from boxcontainer
        //holds rotate,default x coordinat and y coordinate
        //if is on board or not
        //if on board it  will contain the piece placement(the piece string form )
        //update piece if needed
    }
    class GameStatus  {
       //contains the game data
        //
    }
}
