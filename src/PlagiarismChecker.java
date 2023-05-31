/*
 * NAME: TODO
 * PID: TODO
 */

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * PlagiarismChecker implementation.
 * 
 * @author Aneesh Pamula
 * @since 5/30/2023
 */
public class PlagiarismChecker {

    private static final int MIN_INIT_CAPACITY = 5;

    /**
     * Method to print the filename to console
     */
    public static void printFileName(String filename) {
        System.out.println("\n" + filename + ":");
    }

    /**
     * Method to print the statistics to console
     */

    public static void printStatistics(String compareFileName, int percentage) {
        System.out.println(percentage + "% of lines are also in " + compareFileName);
    }

    public static void main(String[] args) throws FileNotFoundException {

        if (args.length < 2) {
            System.err.println("Invalid number of arguments passed");
            return;
        }

        int numArgs = args.length;

        // Create a hash table for every file
        MyHashTable[] tableList = new MyHashTable[numArgs];

        // Preprocessing: Read every file and create a HashTable
        

            for (int i = 0; i < numArgs; i++) {
                File file = new File(args[i]);
                Scanner sc = new Scanner(file);
                tableList[i] = new MyHashTable();
                while(sc.hasNext())
                    tableList[i].insert(sc.nextLine());
            }
            // Find similarities across files

            for (int i = 0; i < numArgs; i++) {
                File file = new File(args[i]);
                Scanner sc = new Scanner(file);
                Integer[] overlappedLines = new Integer[numArgs];//Number of overlapped lines for
                // each file (same file is null)
                double totalLines = 0;
                while(sc.hasNext()){
                    String lineToCheck = sc.nextLine();
                    for(int j = 0; j < numArgs; j++){
                        if(j==i)
                            continue;
                        if(overlappedLines[j] == null)//This way, the only null will be where j = i
                            overlappedLines[j] = 0;
                        if(tableList[j].lookup(lineToCheck))
                            overlappedLines[j]++;
                    }
                    totalLines++;
                }
                printFileName(trim(args[i]));//print the results
                for(int k = 0; k < overlappedLines.length; k++)
                    if(overlappedLines[k] != null)
                        printStatistics(trim(args[k]), (int) (overlappedLines[k]*100/totalLines));
            }

                
    }
    /**
    Trims the given string to just show "filename.txt"

     @param str the string to trim
     */
    private static String trim(String str){
        int leftIndex = 0;
        for(int i = str.length()-2; i >= 0 ; i--){
            if(str.charAt(i) == '/'){
                leftIndex = i;
                break;
            }
        }
        return str.substring(leftIndex+1);
    }

}