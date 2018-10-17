package comp1110.ass2;

import java.io.*;
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
    static StartPieces pi;


    /*
     * the start placements contain 1)1-5 pegs;
     * 2)pegs plus 1 piece
     */
    public static Set<String> pegs() {
        Set<String> pegset1 = new HashSet<>();
        Set<String> pegset = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 4; k++) {
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

    public static String validpieces(){
        String p = pi.twopieces();
        while (obj.getSolutions(p).length == 0){
            p = pi.twopieces();
        }

        return p;
    }

    /*
     * level 1: 6 pieces
     */

    public static void main(String[] args) {
        String p = validpieces();
            System.out.println(p + " " + obj.isPlacementStringValid(p));
//            String[] solutions = obj.getSolutions(p);
//            for (String pi : solutions){
//                System.out.println(pi);
//            }
//            System.out.println();

//        String[] str = obj.getSolutions("c3A7d7A3");
//        for (String p : str){
//            System.out.println(str.length);
//        }
//        String placement = "c1A3d2A6";
//        String[] pl = obj.getSolutions(placement);
//        for (String pi : pl) {
//            System.out.println(pi);
//        }
    }

    }

