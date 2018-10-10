/*
package comp1110.ass2;

import java.io.*;
import java.util.*;


*/
/* implements task9 public String[] getSolutions(String placement)
 * while the start plecement has exactly one solution
 * getSolutions.length = 1
 * the more constrained the player is, the fewer options they have, the solution is simpler
 *//*

public class StartPlacements {
    static TwistGame obj;
    static final int side = 4;
    static final String BASE = "assets/";
    static StartPieces pi;


    */
/*
     * the start placements contain 1)1-5 pegs;
     * 2)pegs plus 1 piece
     *//*

    public static Set<String> pegs() {
        Set<String> pegset1 = new HashSet<>();
        Set<String> pegset = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 2; k++) {
                    String peg = "";
                    peg += (char) (105 + i);
                    peg += (char) (49 + j);
                    peg += (char) (65 + k);
                    peg += (char) (48);
                    pegset1.add(peg);
                }
            }
        }

        for (String p : pegset1) {
            pegset.add(p);
            if (pegset.size() > 10) {
                break;
            }
        }

        return pegset;
    }

    */
/* choose a difficult level
     * each level contains a set of String(start placement)
     * level 3 : 4 pieces + 2 pegs
     *//*

    public static Set<String> level3() {
        Set<String> finalstart = new HashSet<>();
        Set<String> finalstart1 = new HashSet<>();
        //Set<String> fpieces = pieces1();

        //6 piece
        for (String p1 : pi.pieces(0)) {
            for (String p2 : pi.pieces(1))
                for (String p3 : pi.pieces(2)) {
                    for (String p4 : pi.pieces(3)) {
                        for (String p5 : pegs()) {
                            for (String p6 : pegs()) {
                                String p = p1 + p2 + p3 + p4 + p5 +p6;
                                if (obj.isPlacementStringWellFormed(p) && obj.isPlacementStringValid(p)) {
                                    finalstart.add(p);
                                }
                            }
                        }
                    }
                }
        }

        for (String p1 : finalstart){
            if (obj.getSolutions(p1).length == 1){
                finalstart1.add(p1);
            }
        }


        return finalstart1;
    }

    */
/*
     * level 1: 6 pieces
     *//*


//    public static void main(String[] args) {
//        String placement = "c3A0d5B0e3C3h5D2j1B0l2B0";
//        String[] pl = obj.getSolutions(placement);
//        for (String pi : pl) {
//            System.out.println(pi);
//        }
//    }
//        System.out.println(pl.length);
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
//    }

    public static void main (String[]args) throws Exception {
        File filename = new File("assets/level3.txt");
        if (filename.exists()) {
            filename.delete();
            filename.createNewFile();
        }

        Set<String> level3 = level3();
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        for (String pi : level3) {
            bw.write(pi);
            bw.newLine();
        }
        bw.close();

    }

}
*/
