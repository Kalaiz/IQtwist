package comp1110.ass2;

import org.junit.Test;

import static org.junit.Assert.*;

public class RotatorTest {


    public void test(Pieces p, String[][] expected){
        boolean res = true;
        String[][] result = GameBoard.rotator(p.getactual_piece());
        for (int i = 0; i < result.length; i++){
            for (int j = 0; j < result[i].length; j ++){
                if (result[i][j]!=expected[i][j])
                    res = false;
            }
        }

        assertTrue("Got wrong result "+result+" but expect: "+expected,res);
    }

    @Test
    public void testa(){
        String[][] expected = {{"x","or"},{"x","r"},{"r","or"}};
        test(new Pieces('a'), expected);
    }

    @Test
    public void testc(){
        String[][] expected = {{"b"},{"ob"},{"b"},{"b"}};
        test(new Pieces('c'),expected);
    }

    @Test
    public void testg(){
        String[][] expected = {{"x","oy","oy"},{"oy","y","x"},{"y","y","x"}};
        test(new Pieces('g'),expected);
    }

}