package comp1110.ass2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static comp1110.ass2.TwistGame.boardcreator;
import static org.junit.Assert.*;

public class GetEmptyGridTest {

    public void getEmptyGrid(String placementString,List<int[]> expected) {
        boolean valid = true;
        boardcreator(placementString);
        List<int[]> emptyGrid = TwistGame.getEmptyGrid();
        for (int i = 0; i < emptyGrid.size(); i++){
            for (int j = 0; j < 2; j++){
                if(emptyGrid.get(i)[j] != expected.get(i)[j]){
                    valid = false;
                }
            }
        }
        assertTrue("Wrong answer: "+emptyGrid+" but expected: "+expected,valid);
    }

    @Test
    public void test1(){
        String placement = "a7A7c1A3d2A6e2C3f3C2g4A7h6D0i6B0j2B0j1C0k3C0l4B0l5C0";
        List<int[]> expected = new ArrayList<>();
        int[] a = {0,5};
        int[] b = {1,6};
        int[] c = {2,6};
        expected.add(a);
        expected.add(b);
        expected.add(c);
        getEmptyGrid(placement,expected);
    }

    @Test
    public void test2(){
        String placement = "a7A7b6A7c1A3e2C3f3C2g4A7h6D0i6B0j2B0j1C0k3C0l4B0l5C0";
        List<int[]> expected = new ArrayList<>();
        int[] a = {0,1};
        int[] b = {0,2};
        int[] c = {0,3};
        int[] d = {1,2};
        expected.add(a);
        expected.add(b);
        expected.add(c);
        expected.add(d);
        getEmptyGrid(placement,expected);
    }
}