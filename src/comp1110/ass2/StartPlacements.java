package comp1110.ass2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;


/* implements task9 public String[] getSolutions(String placement)
 * while the start plecement has exactly one solution
 * getSolutions.length = 1
 * the more constrained the player is, the fewer options they have, the solution is simpler
 */
public class StartPlacements {
    static TwistGame obj;
    static final int side = 4;
    static final String BASE = "assets/";

    public static Set<String> pieces(){
        Set<String> piecelist = new HashSet<>();
        for (int i = 0; i < 8; i ++){
            for (int j = 0; j < 8; j ++){
                for (int k = 0; k < 4; k ++){
                    for (int l = 0; l < 8; l ++){
                        char[] piece = new char[side];
                        piece[0] = (char)(97 + i);
                        piece[1] = (char)(49 + j);
                        piece[2] = (char)(65 + k);
                        piece[3] = (char)(48 + l);
                        String pi = new String(piece);
                        if(obj.isPlacementStringValid(pi)){
                        piecelist.add(pi);}
                    }
                }
            }
        }

        return piecelist;
    }

    /*
     * the start placements contain 1)1-5 pegs;
     * 2)pegs plus 1 piece
     */
    public static Set<String> pegs(){
        Set<String> peglist = new HashSet<>();
        for (int i = 0; i < 4; i ++){
            for (int j = 0; j < 8; j ++){
                for (int k = 0; k < 4; k ++){
                        char[] peg = new char[side];
                        peg[0] = (char)(105 + i);
                        peg[1] = (char)(49 + j);
                        peg[2] = (char)(65 + k);
                        peg[3] = (char)(48);
                        String pi = new String(peg);
                        peglist.add(pi);}
            }
        }
        return peglist;
    }

    /* choose a difficult level
     * each level contains a set of String(start placement)
     * level 3 : one piece
     */
    public static Set<String> level3() {
        Set<String> finalpegs = new HashSet<>();
        Set<String> fpiecee = pieces();

        //1 piece
        for (String p1 : fpiecee) {
            //String[] start = obj.getSolutions(p1);
            if (obj.isPlacementStringValid(p1) ) {
                finalpegs.add(p1);
            }
        }

        return finalpegs;
    }

    /*
     * level 2: two pieces
     */
    public static Set<String> level2() {
        Set<String> finalpegs = new HashSet<>();
        Set<String> fpiecee = pieces();
        Set<String> level3 = level3();

        //2 piece
        for (String p1 : fpiecee) {
            for (String p2 : level3) {
                String[] start = obj.getSolutions(p1 + p2);
                if (obj.isPlacementStringValid(p1 + p2) && start.length == 1) {
                    finalpegs.add(p1 + p2);
                }
            }
        }

        return finalpegs;
    }

    //public static void main(String[] args) {
//        Set<String> piecelist = pieces();
//        Set<String> peglist = pegs();
//
//        System.out.println(piecelist.size());
//        System.out.println(peglist.size());

       // Set<String> start = level2();
       // System.out.println(start.size());
//        for(String piece : start){
//            System.out.println(piece);
//        }
//        if(piecelist.contains("a7A7")){
//            System.out.println("true");
//        } else {
//            System.out.println("false");
//        }
    //}

        public static void main(String[] args) throws Exception{
            File filename=new File("assets/1.txt");
            if (!filename.exists()) {
                filename.createNewFile();
            }

            Set<String> level3 = level3();
            BufferedWriter bw=new BufferedWriter(new FileWriter(filename));
            for(String pi : level3){
                bw.write(pi);
                bw.newLine();
            }
            bw.close();

    }

}
