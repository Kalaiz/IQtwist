package comp1110.ass2;

import java.util.Set;

/**
 * This class provides the text interface for the Twist Game
 * <p>
 * The game is based directly on Smart Games' IQ-Twist game
 * (http://www.smartgames.eu/en/smartgames/iq-twist)
 */
public class TwistGame {



  /**
   * Determine whether a piece or peg placement is well-formed according to the following:
   * - it consists of exactly four characters
   * - the first character is in the range a .. l (pieces and pegs)
   * - the second character is in the range 1 .. 8 (columns)
   * - the third character is in the range A .. D (rows)
   * - the fourth character is in the range 0 .. 8 (if a piece) or is 0 (if a peg)
   *
   * @param piecePlacement A string describing a single piece or peg placement
   * @return True if the placement is well-formed
   */


  public static boolean isPlacementWellFormed(String piecePlacement){//chars are compared in accordance to ascii encoding values
    char[]data ={'a','l','1','8','A','D','0','8'};
    for(int i=0;i<3;i++){
      if(i==2&&piecePlacement.charAt(i)>=data[i]&& piecePlacement.charAt(i)<data[i+1]){//for the fourth character case where the peg might be 0
        return true;//no need of continue here as it is going to be the last iteration
      }
      else if( i!=2 && piecePlacement.charAt(i)>data[i]&& piecePlacement.charAt(i)<data[i+1]){
       continue;
     }
     else{return false;}//no need break cause of return statement
    }
    return true;
  }
  // FIXME Task 2: determine whether a piece or peg placement is well-formed


  public static boolean isPlacementWellFormed2(String piecePlacement) {//initial  code
    boolean  contain=(piecePlacement.charAt(0)>'a'&& piecePlacement.charAt(0)<'l');//when char is compared it is converted to ascii encoding numbers
    boolean contain2=(piecePlacement.charAt(1)<'1'&& piecePlacement.charAt(1)<'8');
    boolean contain3=(piecePlacement.charAt(2)<'A'&& piecePlacement.charAt(2)>'D');
    boolean contain4= (piecePlacement.charAt(3)<='0'&& piecePlacement.charAt(3)>'8');// could be a peg
    contain=contain && contain2&&contain3&& contain4;
    return contain;
  }
  /**
   * Determine whether a placement string is well-formed:
   * - it consists of exactly N four-character piece placements (where N = 1 .. 15);
   * - each piece or peg placement is well-formed
   * - each piece or peg placement occurs in the correct alphabetical order (duplicate pegs can be in either order)
   * - no piece or red peg appears more than once in the placement
   * - no green, blue or yellow peg appears more than twice in the placement
   *
   * @param placement A string describing a placement of one or more pieces and pegs
   * @return True if the placement is well-formed
   */
  public static boolean isPlacementStringWellFormed(String placement) {
    // FIXME Task 3: determine whether a placement is well-formed

    if(placement.length()%4!=0){                                            //Check if the length of the placement String is valid

      return false;

    }else {


      String[] items = new String[placement.length()/4];                   //Using a loop to store all four-character placements into a String[]

      loop: for(int i = 0; i < placement.length()/4 ; i++){

        items[i] = placement.substring(4*i,4*i+3);



        if(isPlacementStringWellFormed(items[i])==true)                    //First judge if each placement is well-formed

          continue loop;

        else

          return false;

      }



      for(int j = 1; j < 8; j++){                                          //Second judge if the 8 pieces occur in the correct alphabetical order or have duplicated

        if( items[j-1].charAt(0) >= items[j].charAt(0) )

          return false;

      }



      for(int x = 0; x < placement.length(); x++){                         //Last judge if pegs are outnumbered

        int countRed = 0;
        int countGre = 0;
        int countBlu = 0;
        int countYel = 0;

        if (placement.charAt(x)=='i')
          countRed++;
        else if (placement.charAt(x)=='j')
          countBlu++;
        else if (placement.charAt(x)=='k')
          countGre++;
        else if (placement.charAt(x)=='l'){
          countYel++;

        if(countRed > 1 || countBlu > 2 || countGre > 2 || countYel > 2)

          return false;

        }

      }

    }

    return true;

  }
  /**
   * Determine whether a placement string is valid.  To be valid, the placement
   * string must be well-formed and each piece placement must be a valid placement
   * according to the rules of the game.
   * - pieces must be entirely on the board
   * - pieces must not overlap each other
   * - pieces may only overlap pegs when the a) peg is of the same color and b) the
   *   point of overlap in the piece is a hole.
   *
   * @param placement A placement sequence string
   * @return True if the placement sequence is valid
   */
  public static boolean isPlacementStringValid(String placement) {
    // FIXME Task 5: determine whether a placement string is valid
    return false;
  }

  /**
   * Given a string describing a placement of pieces and pegs, return a set
   * of all possible next viable piece placements.   To be viable, a piece
   * placement must be a valid placement of a single piece.  The piece must
   * not have already been placed (ie not already in the placement string),
   * and its placement must be valid.   If there are no valid piece placements
   * for the given placement string, return null.
   *
   * @param placement A valid placement string (comprised of peg and piece placements)
   * @return An set of viable piece placements, or null if there are none.
   */
  public static Set<String> getViablePiecePlacements(String placement) {
    // FIXME Task 6: determine the set of valid next piece placements
    return null;
  }

  /**
   * Return an array of all unique solutions to the game, given a starting placement.
   * A given unique solution may have more than one than
   * one placement sequence, however, only a single (unordered) solution should
   * be returned for each such case.
   *
   * @param placement A valid piece placement string.
   * @return An array of strings, each describing a unique unordered solution to
   * the game given the starting point provided by placement.
   */
  public static String[] getSolutions(String placement) {//Use task 6 code here
    // FIXME Task 9: determine all solutions to the game, given a particular starting placement
    return null;
  }
}
