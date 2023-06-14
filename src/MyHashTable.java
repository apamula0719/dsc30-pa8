/*
 * Name: TODO
 * PID: TODO
 */

import java.util.ArrayList;
import java.util.Arrays;

/**
 * HashTable class
 * 
 * @author Aneesh Pamula
 * @since 5/29/2023
 */
public class MyHashTable implements HashTable {

    /* the bridge for lazy deletion */
    private static final String BRIDGE = new String("[BRIDGE]".toCharArray());

    /* instance variables */
    private int size; // number of elements stored
    private String[] table; // data table
    private int rehashes;
    ArrayList<Double> loadFactors;
    ArrayList<Integer> collisions;
    public MyHashTable() {
        this(20);
    }

    public MyHashTable(int capacity) {
        if(capacity < 5)
            throw new IllegalArgumentException();
        size = 0;
        table = new String[capacity];
        rehashes = 0;
        loadFactors = new ArrayList<>();
        collisions = new ArrayList<>();
        collisions.add(0);
    }

    @Override
    public boolean insert(String value) {
        if(value == null)
            throw new NullPointerException();
        int i = 0;
        int toInsert = -1;
        if(!this.lookup(value)){
            if(((double) this.size()) / this.capacity() > 0.7){//When load factor > 0.7
                loadFactors.add(((double) size()) / capacity());
                rehash();
                rehashes++;
                collisions.add(0);
            }
        }
        while(true){
            int pos = (hashString(value) + i) % this.capacity();//h(k, i)
            if(table[pos] == null){
                if(toInsert < 0)//If there isn't a previous place to insert
                    toInsert = pos;
                break;//Index to insert in has been decided
            }
            if(table[pos] == BRIDGE){
                if(toInsert < 0)
                    toInsert = pos;//Set the index to insert to this position,
                // continue looking to see if element exists
            }
            if(table[pos].equals(value))
                return false;//Element exists
            if(i >= this.capacity())//Gone through all elements, exit
                break;
            collisions.set(rehashes, collisions.get(rehashes) + 1);// update
            // number of collisions and continue probing
            i++;
        }
        table[toInsert] = value;
        size++;
        return true;
    }

    @Override
    public boolean delete(String value) {
        if(value == null)
            throw new NullPointerException();
        int i = 0;
        while(true){
            int pos = (hashString(value) + i) % this.capacity();//h(k, i)
            if(table[pos] == null){
                return false;
            }
            if(table[pos].equals(value)){
                table[pos] = BRIDGE;
                size--;
                return true;
            }
            i++;
        }
    }

    @Override
    public boolean lookup(String value) {
        if(value == null)
            throw new NullPointerException();
        int i = 0;//Iterating variable
        while(true){
            int pos = (hashString(value) + i) % this.capacity();//h(k, i)
            if(table[pos] == null || i > this.capacity())
                return false;
            if(table[pos].equals(value))
                return true;
            i++;
        }
    }

    public String[] returnAll() {
        String[] toReturn = new String[size];
        int addCounter = 0;
        for(String s : table){
            if(s != null && s != BRIDGE) {
                toReturn[addCounter] = s;
                addCounter++;
            }
        }
        return toReturn;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return table.length;
    }

    public String getStatsLog() {
        String output = "";
        for(int i = 0; i < rehashes; i++){
            output += "Before rehash # " + (i+1) + ": load factor " + String.format("%.2f", loadFactors.get(i)) + ", "
                    + collisions.get(i) + " collision(s).\n";
        }
        return output;
    }

    private void rehash() {
        String[] oldTable = this.returnAll();
        table = new String[capacity()*2];
        size = 0;
        for(String s : oldTable){
            insert(s);
        }
    }

    private int hashString(String value) {
        int hashValue = 0;
        for(int i = 0; i < value.length(); i++){
            int leftShifted = hashValue<<5;
            int rightShifted = hashValue>>>27;
            hashValue = Math.abs((leftShifted | rightShifted) ^ value.charAt(i));
        }
        return hashValue % this.capacity();
    }

    /**
     * Returns the string representation of the hash table.
     * This method internally uses the string representation of the table array.
     * DO NOT MODIFY. You can use it to test your code.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return Arrays.toString(table);
    }
}