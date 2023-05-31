/**
 * PA8 Part 1 worksheet (style not required)
 */
public class CollisionHandling {

    /**
     * method that stores the result of Q1 in PA8 worksheet
     * 4 insertions and then all of them
     * @return hashtable representation after insertions
     */
    public static int[][] linearProbingResult(){
        /*
        TODO: create the values for the hashtable representations
         */
        int[][] output;
        output = new int[2][8];
        output[0] = new int[]{0, 56, 0, 42, 0, 28, 0, 14};
        output[1] = new int[]{70, 56, 112, 42, 98, 28, 84, 14};
        return output;
    }

    /**
     * method that stores the result of Q2 in PA8 worksheet
     * 4 insertions and then all of them
     * @return hashtable representation after insertions
     */
    public static int[][] quadraticProbingResult(){
        /*
        TODO: create the values for the hashtable representations
         */
        int[][] output;
        output = new int[2][8];
        output[0] = new int[]{0, 16, 1, 0, 11, 0, 0, 6};
        output[1] = new int[]{27, 16, 1, 9, 11, 3, 21, 6};
        return output;
    }

    /**
     * method that stores the result of Q3 in PA8 worksheet
     * 4 insertions and then all of them
     * @return hashtable representation after insertions
     */
    public static int[][] doubleHashingResult(){
        /*
        TODO: create the values for the hashtable representations
         */
        int[][] output = new int[2][8];
        output[0] = new int[]{10, 0, 0, 17, 0, 23, 2, 0};
        output[1] = new int[]{10, 31, 38, 17, 44, 23, 2, 51};
        return output;
    }

}