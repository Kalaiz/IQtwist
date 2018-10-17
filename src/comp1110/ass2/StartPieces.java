package comp1110.ass2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class StartPieces {
    static TwistGame obj;

    public static String pieces() {
        Random rand = new Random();
        int i = rand.nextInt(8);
        int j = rand.nextInt(8);
        int k = rand.nextInt(4);
        int l = rand.nextInt(8);
        String piece = "";
        piece += (char) (97 + i);
        piece += (char) (49 + j);
        piece += (char) (65 + k);
        piece += (char) (48 + l);

        return piece;
    }

    public static String twopieces(){
        String p1 = pieces();
        String p2 = pieces();

        while (!(obj.isPlacementStringWellFormed(p1 + p2) && obj.isPlacementStringValid(p1 + p2 ))){
            p1 = pieces();
            p2 = pieces();
        }

        return p1 + p2;
    }

    }
