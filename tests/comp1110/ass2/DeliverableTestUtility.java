package comp1110.ass2;


import java.util.ArrayList;
import java.util.List;

public class DeliverableTestUtility {
    static TwistGame g;
    static final String[][] expectedaaboards = {{"or", "r", "or", "x", "x", "x", "x", "x", "x"}, {"x", "x", "r", "x", "x", "x", "x", "x", "x"}, {"x", "x", "x", "x", "x", "x", "x", "x", "x"}, {"x", "x", "x", "x", "x", "x", "x", "x", "x"}};
    static final String[][] expectedacboards = {{"x", "x", "x", "x", "x", "x", "x", "x", "x,", "x", "x", "x", "x"},
            {"x", "x", "x", "x", "x", "x", "x", "x", "x,", "x", "x", "x", "x"},
            {"x", "x", "x", "x", "x", "x", "x", "x", "x,", "x", "x", "x", "x"},
            {"x", "x", "x", "or", "r", "or", "x", "x", "x", "x", "x", "x", "x"},
            {"x", "x", "x", "x", "x", "r", "x", "x", "x,", "x", "x", "x", "x"},
            {"x", "x", "x", "x", "x", "x", "x", "x", "x,", "x", "x", "x", "x"},
            {"x", "x", "x", "x", "x", "x", "x", "x", "x,", "x", "x", "x", "x"},
            {"x", "x", "x", "x", "x", "x", "x", "x", "x,", "x", "x", "x", "x"},
            {"x", "x", "x", "x", "x", "x", "x", "x", "x,", "x", "x", "x", "x"},
            {"x", "x", "x", "x", "x", "x", "x", "x", "x,", "x", "x", "x", "x"}};
    static  final String[] sampleboarddata = {"g1A0", "b2A1", "c1A1", "e1A0c3A1", "k1A0",};

    static List<String[][]> multiboards(char type) {
        List<String[][]> mboards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mboards.add(g.boardcreator(sampleboarddata[i], type));
        }
        return mboards;
    }
}
