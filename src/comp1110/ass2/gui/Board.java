package comp1110.ass2.gui;


import comp1110.ass2.Pieces;
import comp1110.ass2.StartingBoard;
import comp1110.ass2.TwistGame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static comp1110.ass2.StartingBoard.solNum;
import static comp1110.ass2.TwistGame.getFormalPieces;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.setOut;

public class Board extends Application {
    private static String specificSol = "";
    private static String gameState = "";//The game String
    private static String startingBoard = "";//The starting board string
    private static final int DISPLAY_WIDTH = 1280;
    private static final int DISPLAY_HEIGHT = 649;
    private static final String URI_BASE = "assets/";
    private final Text completionText = new Text("Well done!");

    /*Game object*/
    TwistGame game = new TwistGame();

    /*NODE groups*/
    private Group root = new Group();
    private Group boardgrid = new Group();
    private Group pieces = new Group();
    private Group controls = new Group();
    private Group board=new Group();
    private Group outsides = new Group();


    /* Grid */
    private static GridPane grid = new GridPane();

    /* Default (home) x & y coordinates*/
    private static final double[] hxy = {100, 50, 100, 200, 100, 400, 740, 380, 340
                                          , 50, 340, 200, 340, 350, 540, 380};

    /* the difficulty slider */
    private final Slider difficulty = new Slider();

    /*Glow objects*/
    private Glow g1 = new Glow(0.5);
    private Glow g2 = new Glow();

    /*States whether game has started or not */
    private static boolean gamerunning;

    /* For access to StartingBoard */
    private static StartingBoard sb = new StartingBoard();

    /*Contains the updated Hints */
    private static List<String>hintList=new ArrayList<>();


    /*Sets up the board*/
    private void createBoardGrid() {
        boardgrid.getChildren().clear();
        for (int i = 0; i < 8; i++) {
            ColumnConstraints col = new ColumnConstraints(50);
            grid.getColumnConstraints().add(col);
        }
        for (int i = 0; i < 4; i++) {
            RowConstraints row = new RowConstraints(50);
            grid.getRowConstraints().add(row);
        }
        grid.setGridLinesVisible(false);//hiding the grid
        grid.setLayoutX(700);
        grid.setLayoutY(10);
        boardgrid.getChildren().add(grid);
    }


    /**
     * Create a dialogue when the click the "help" button
     * show the instructions for the game
     *
     * Authorship: Yuqing Zhang
     */
    private void instructions(){
        Stage dialogue = new Stage();
        dialogue.setTitle("Instructions");
        dialogue.initModality(Modality.APPLICATION_MODAL);
        dialogue.setMinWidth(500);
        dialogue.setMaxHeight(500);
        dialogue.getIcons().add((new Image((Viewer.class.getResource(URI_BASE + "e.png").toString()))));
        Button button = new Button("Close the widow");
        button.setOnAction(e -> dialogue.close());
        Text t1 = new Text("Right click for flip.");
        Text t2 = new Text("Scroll over the piece to rotate it.");
        Text t3 = new Text("To reset a piece,hover over it and press the middle button.");
        Text t4= new Text ("For hints, press ' / '");
        VBox layout = new VBox(10);
        layout.getChildren().addAll(t1, t2, t3,t4, button);
        layout.setAlignment(Pos.CENTER);;
        Scene scene = new Scene(layout);
        scene.setFill(Color.WHITE);
        dialogue.setScene(scene);
        dialogue.showAndWait();
    }


    /**
     * Creates a board using different shapes
     *
     * Authorship: Yuqing Zhang
     */
    private void createBoard(){
        Rectangle rect1 = new Rectangle(690, 2.5, 420, 215);
        Rectangle rect2 = new Rectangle(692.5, 5, 415, 210);
        rect2.setFill(Color.WHITE);
        rect1.setArcHeight(30);
        rect1.setArcWidth(30);
        rect2.setArcHeight(30);
        rect2.setArcWidth(30);
        rect1.toBack();
        rect2.toBack();
        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j < 4; j ++) {
                Circle circle1 = new Circle(725 + 50 * i, 35 + 50 * j, 27);
                Circle circle2 = new Circle(725 + 50 * i, 35 + 50 * j, 25);
                Circle innercircle =new Circle(725 + 50 * i, 35 + 50 * j, 15);
                Circle innercircle2 =new Circle(725 + 50 * i, 35 + 50 * j, 13);
                circle1.setFill(Color.DARKGREY);
                circle2.setFill(Color.WHITE);
                innercircle.setFill(Color.LIGHTGREY);
                innercircle2.setFill(Color.WHITE);
                circle1.toBack();
                circle2.toBack();
                board.getChildren().add(circle1);
                board.getChildren().add(circle2);
                board.getChildren().add(innercircle);
                board.getChildren().add(innercircle2);
            }
        }
        outsides.getChildren().add(rect1);
        outsides.getChildren().add(rect2);
    }


    /**
     * Display a "Well Done!" message
     *
     * Authorship: Yuqing Zhang
     */
    private void makeCompletion(){
        completionText.setFill(Color.BLACK);
        completionText.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 80));
        completionText.setLayoutX(DISPLAY_WIDTH/3);
        completionText.setLayoutY(DISPLAY_HEIGHT/2);
        completionText.setTextAlignment(TextAlignment.CENTER);
        completionText.toFront();
        completionText.setOpacity(1);
        root.getChildren().add(completionText);
    }


    /* Inner class for pieces
    * Author:Kalai & Lingyi Xia*/
    class piece extends ImageView {
        int pieceType;//The actual ascii number.
        double defaultX;//Default x coordinate position on start of the game
        double defaultY;//Default y coordinate position on start of the game
        ImageView holder;

        piece(char pieceType) {
            holder = new ImageView();//Since grid needs a node
            Image img = (new Image(Viewer.class.getResource(URI_BASE + pieceType + ".png").toString()));
            holder.setImage(img);
            root.getChildren().add(holder);
            double height = img.getHeight() * 0.5;//Resizing the image
            double width = img.getWidth() * 0.5;
            holder.setFitHeight(height);
            holder.setFitWidth(width);
            this.pieceType = pieceType;// according to ascii encoding
            if (!(pieceType > 104)) {//pieces
                defaultX = hxy[2 * (pieceType - 97)];
                defaultY = hxy[(2 * (pieceType - 97)) + 1];
                holder.setX(defaultX);
                holder.setY(defaultY);
            }
        }

    }


/* Class which handle Events upon User interaction
*  Author:Kalai */
    class eventPiece extends piece {
        String pieceInfo;
        int gridRow;//placement on the grid
        int gridCol;
        boolean flip;
        int rotate;

        void reset() {
            holder.setX(defaultX);
            holder.setY(defaultY);
            holder.setRotate(0);
            holder.setScaleY(1);
            holder.setTranslateX(0);
        }

        void delete() {
            holder.setImage(null);
        }

        eventPiece(String pieceInfo) {
            super(pieceInfo.charAt(0));
            gridCol = Integer.parseInt(pieceInfo.charAt(1) + "") - 1;
            gridRow = pieceInfo.charAt(2) - 65;
            int orientation = Integer.parseInt(pieceInfo.charAt(3) + "");
            flip = (orientation > 3);
            if (flip) {
                holder.setScaleY(-1);
            } else {
                holder.setScaleY(1);
            }
            rotate = (orientation > 4) ? 90 * (orientation - 4) : 90 * orientation;
            holder.setRotate(rotate);
            int[] csrs = imgModifier();
            grid.add(holder, gridCol, gridRow, csrs[0], csrs[1]);
        }

        private void decodePieces() {
            //As the string for the column (piece-encoding) starts from 1
            int col = gridCol + 1;
            int orientation = (flip) ? (int) ((holder.getRotate() / 90) + 4) : (int) (holder.getRotate() / 90);
            // Character of the piece + Column:number + Row:Alpha + orientation:number
            pieceInfo = Character.toString((char) pieceType) + col + ((char) (gridRow + 65)) + Integer.toString(orientation);

        }


        eventPiece(char piece) {
            super(piece);
            flip = false;//Declaring
            holder.setOnScroll(scroll -> {//Rotation on scroll
                if (!grid.getChildren().contains(holder)){//If grid does not contain the piece .
                    rotate += 90;
                    rotate = (rotate >= 360) ? 0 : rotate;
                    holder.setRotate(rotate);
                }
            });

            holder.setOnMouseEntered(geffect -> { //Glow effect
                holder.setEffect(g1);

            });

            holder.setOnMouseExited(ageffect -> {//Anti glow effect
                holder.setEffect(g2);
            });

            holder.setOnMouseClicked(click -> {//Flip on right click
                if (click.getButton() == MouseButton.SECONDARY && !grid.getChildren().contains(holder)) {
                    if (flip) {
                        holder.setScaleY(1);
                        flip = false;
                    } else {
                        holder.setScaleY(-1);
                        flip = true;
                    }
                }
                else if(click.getButton()==MouseButton.MIDDLE&&grid.getChildren().contains(holder)){
                  resetPiecestr(pieceInfo);

                }
            });

            holder.setOnMouseDragged(drag -> {
                if (!grid.getChildren().contains(holder)) {
                    holder.toFront();
                    double cornerX = drag.getSceneX();
                    double cornerY = drag.getSceneY();
                    //Divide by 2 so to pull it by the center & using getScene for relative positioning
                    holder.setY(cornerY - holder.getFitHeight() / 2);
                    holder.setX(cornerX - holder.getFitWidth() / 2);
                    //Image remains non rotated according to Javafx(Grid).
                    //Piece G is an exception as it does not cause any offset error
                    double imgCornerx = ((rotate / 90) % 2 == 0 || pieceType == 103) ? holder.getX() : holder.getX() + holder.getFitWidth() / 2.5;
                    double imgCornery = ((rotate / 90) % 2 == 0 || pieceType == 103) ? holder.getY() : cornerY - holder.getFitWidth() / 2.25;
                    holder.setOnMouseReleased(released -> {
                        //not exactly the coordinates of the grid as to allow flexibility for the user
                        if (imgCornerx < 680 || imgCornerx > 1080 || imgCornery > 200 || imgCornery < 0) {
                            holder.setX(defaultX);
                            holder.setY(defaultY);
                        } else {// Image's top left corner.
                            setOnGrid(imgCornerx, imgCornery);
                        }
                    });
                }
            });
        }


        int[] imgModifier() {
            int rowspan = (int) holder.getImage().getHeight() / 100;
            int colspan = (int) holder.getImage().getWidth() / 100;
            //Switching the row and col span upon rotation
            int rs = ((rotate / 90) % 2 == 0) ? rowspan : colspan;
            int cs = ((rotate / 90) % 2 == 0) ? colspan : rowspan;
            int translateX = ((rotate / 90) % 2 == 0) ? 0 : -(int) (holder.getFitWidth() - holder.getFitHeight()) / 2;
            //to balance the offset created upon setting the image  on the grid
            holder.setTranslateX(translateX);
            int[] csrs = {cs, rs};
            return csrs;
        }


    /**
     * Places the piece(image) accordingly upon dragging
     * @param positionalX- the x coordinate of the image
     * @param positionalY- the y coordinate of the image
     * Author:Kalai
     */
    void setOnGrid(double positionalX, double positionalY) {// Image's top left corner.
            // 50 - width/height of each grid
            // 695 & 5 - the approximate starting co-ordinates  of the grid
            gridCol = (int) (positionalX - 695) / 50;
            gridRow = (int) (positionalY - 5) / 50;
            int[] csrs = imgModifier();
            decodePieces();//Converting available data into piece encoding
                gameState += pieceInfo;//Concatenating the piece encoding into the game String
            if (game.isPlacementStringValid(gameState)) {
                grid.add(holder, gridCol, gridRow, csrs[0], csrs[1]);
            } else {
                holder.setX(defaultX);
                holder.setY(defaultY);
                //updating the gameState string if the position is not valid.
                gameState = gameState.substring(0, gameState.length() - 4);
            }

            showCompletion();
        }
    }


    /**
     * Reset the pieces (when user clicks newGame)
     * Author:Kalai
     */
    private void forceReset() {//Used for new game
        grid = new GridPane();
        Iterator im = pieces.getChildren().iterator();
        while (im.hasNext()) {
            eventPiece g = (eventPiece) im.next();
            g.delete();//Deleting the images formed by eventpieces
        }
    }



    /**
     * Start a new game & clear the previous board and pieces
     * Authors:All of Us.
     */
    private void newGame() {
        Viewer access = new Viewer();
        TwistGame t = new TwistGame();
        if (gamerunning) {
            forceReset();//clear the board and pieces using a separate function
        }
        gamerunning = true;
        specificSol = diffLevel(difficulty.getValue());
        hintList=getFormalPieces(specificSol);
        gameState = startingBoard;
        String placed = access.returner(startingBoard, 0);
        String unplaced = "";
        int[] output = IntStream.rangeClosed(97, 104).filter(i -> !placed.contains((char) i + "")).parallel().toArray();
        for (int i : output) {
            unplaced += (Character.toString((char) i));
        }//converting to String- not necessary
        for (int m = 0; m < unplaced.length(); m++) {
            pieces.getChildren().add(new eventPiece(unplaced.charAt(m)));
        };
        List<String> listOfPieces = t.getFormalPieces(startingBoard);
        for (int i = 0; i < listOfPieces.size(); i++) {
            pieces.getChildren().add(new eventPiece(listOfPieces.get(i)));
        }
        createBoardGrid();



    }


    /**
     * Resets an individual event piece back to the default positions
     * @param p- eventpiece object
     * Author:Kalai
     */
    private void  resetPiece(eventPiece p){
        grid.getChildren().remove(p.holder);
            pieces.getChildren().remove(p);
            pieces.getChildren().add(new eventPiece(((p).pieceInfo).charAt(0)));

    }
/**
 * reset the particular piece
 * @param pieceInfo- The piece encoding
 * Author:Kalai
 */
    private void  resetPiecestr(String pieceInfo){
        try {
        for(Node n:pieces.getChildren()){
            if (((((eventPiece) n).pieceInfo)) != null) {
            if(((eventPiece) n).pieceInfo.equals(pieceInfo)){
                resetPiece(((eventPiece) n));}
                gameState=gameState.replace(pieceInfo,"");
        }}

    }
    catch(ConcurrentModificationException e){}}


    /**
     * Reset the game
     * Author:Kalai
     */
    private void resetgame() {
        try {
            gameState = startingBoard;
            Object[] arr = pieces.getChildren().toArray();//cause of concurrentModificationerror
            for (Object obj : arr) {
                if (((((eventPiece) obj).pieceInfo)) != null) {
                    if (!startingBoard.contains(((eventPiece) obj).pieceInfo)) {
                    resetPiece((eventPiece )obj);
                    }
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Error");
        }
    }




    /**
     * Create the controls that allow the game to be restarted and the difficulty
     * level set.
     * New Game - will create a new game board according to sliding level value
     * author: Lingyu Xia
     */
    private void makeControls() {
        Button newGame = new Button("New Game");
        Button reset = new Button("Reset");
        Button random = new Button("Random");
        Button help = new Button("Help");
        reset.setLayoutX(DISPLAY_WIDTH / 4 + 30);
        reset.setLayoutY(DISPLAY_HEIGHT - 45);
        newGame.setLayoutX(DISPLAY_WIDTH / 4 + 100);
        newGame.setLayoutY(DISPLAY_HEIGHT - 45);
        random.setLayoutX(DISPLAY_WIDTH/4 + 200);
        random.setLayoutY(DISPLAY_HEIGHT - 45);
        help.setLayoutX(DISPLAY_WIDTH / 4 + 285);
        help.setLayoutY(DISPLAY_HEIGHT - 45);
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                resetgame();
            }
        });
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                hideCompletion();
                newGame();

            }
        });
        random.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startingBoard = RandomStart();
                hideCompletion();
                newGame();
            }
        });
        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                instructions();
            }
        });

        controls.getChildren().add(newGame);
        controls.getChildren().add(reset);
        controls.getChildren().add(random);
        controls.getChildren().add(help);
        difficulty.setMin(0);
        difficulty.setMax(2);
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





    /**
     * Hide the "Well Done!" message when player click "new game" button
     *
     * Authorship: Yuqing Zhang
     */
    private void hideCompletion(){
        root.getChildren().remove(completionText);
    }

    /**
     * Show a message when the player add all pieces to the board
     *
     * Authorship: Yuqing Zhang
     */
    private void showCompletion(){
        int length = gameState.length();
        int numofpiece = 0;
        int i = 0;

        while (i < length){
            if (gameState.substring(i).charAt(0) < (char)105){
                numofpiece ++;
            }
            i += 4;
        }
        if (numofpiece >= 8){
            makeCompletion();

        }
    }

    /*Start of JavaFX operations
    * Author(s):All of us*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("IQ-twist");//sets the title name on the bar
        primaryStage.getIcons().add(new Image((Viewer.class.getResource(URI_BASE + "e.png").toString())));
        Scene scene = new Scene(root, DISPLAY_WIDTH, DISPLAY_HEIGHT);
        if(!gamerunning){
            sb.pegAdder(sb.pieceCreator());
          }
        createBoard();
        root.getChildren().add(outsides);
        root.getChildren().add(board);
        newGame();
        root.getChildren().add(boardgrid);
        root.getChildren().add(pieces);
        makeControls();

        scene.setOnKeyPressed(mk->{
            if(mk.getCode()== KeyCode.SLASH){
                String hintpiece=hint();
                hintList.remove(hintpiece);
                if(hintpiece!=null){
                    eventPiece p = new eventPiece(hintpiece);
                    p.holder.setOpacity(0.4);
                    root.getChildren().add(p);
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String diffLevel(double level){
        startingBoard = sb.difficultyLevel(level);
        return sb.sol.get(solNum);
    }



    /*
     * select a random valid start placement from the dictionary
     * implement class StatingBoard
     *
     * Authorship: Yuqing Zhang
     */
    public static String RandomStart() {
        Random rand = new Random();
        int level = rand.nextInt(3);
        StartingBoard start = new StartingBoard();
        String startpoints = start.difficultyLevel(level);
        return startpoints;
    }



    /*Produces Hints based on the current gameState String
    author: Kalai*/

    public static String  hint() {
       Random r = new Random();
       List<String>usedPieces= getFormalPieces(gameState);

       for(String up:usedPieces){
           Predicate<String> piecePredicate = piece-> piece.equals(up);
           hintList.removeIf(piecePredicate);
       }
       if(hintList.size()!=0){
       int rnd=r.nextInt(hintList.size());
       return hintList.get(rnd);}
       else{return null;}
      }
  ;  }






