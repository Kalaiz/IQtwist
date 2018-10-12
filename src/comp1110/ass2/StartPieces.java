package comp1110.ass2;

import java.util.HashSet;
import java.util.Set;

public class StartPieces {
    static TwistGame obj;

    public static Set<String> pieces( int number) {
        Set<String> pieceset = new HashSet<>();
        Set<String> pieceset1 = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 8; l++) {
                        String piece = "";
                        piece += (char) (97 + i);
                        piece += (char) (49 + j);
                        piece += (char) (65 + k);
                        piece += (char) (48 + l);
                        if (obj.isPlacementStringValid(piece)) {
                            pieceset.add(piece);
                        }
                    }
                }
            }
        }

        for (String p : pieceset) {
            pieceset1.add(p);
            if (pieceset1.size() > number) {
                break;
            }
        }

        return pieceset1;
    }
}
