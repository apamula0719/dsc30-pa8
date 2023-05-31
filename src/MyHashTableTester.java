import static org.junit.jupiter.api.Assertions.*;

class MyHashTableTester {

    MyHashTable hash;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        hash = new MyHashTable(5);
    }

    @org.junit.jupiter.api.Test
    void insert() {
        hash.insert("abc");
        assertEquals("[null, null, abc, null, null]", hash.toString());
        hash.insert("def");
        hash.insert("dsc");
        assertEquals("[def, null, abc, dsc, null]", hash.toString());
        hash.insert("ghi");
        hash.insert("jkl");
        hash.insert("mno");
        assertEquals("[def, ghi, mno, null, jkl, null, null, abc, dsc, null]", hash.toString());
        //Exceptions
        assertThrows(NullPointerException.class, () -> {
            hash.insert(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            hash = new MyHashTable(3);
        });
    }

    @org.junit.jupiter.api.Test
    void delete() {
        hash.insert("abc");
        hash.delete("abc");
        assertEquals(hash.size(), 0);
        assertEquals("[null, null, [BRIDGE], null, null]", hash.toString());
        hash.insert("abc");
        hash.insert("def");
        hash.insert("dsc");
        assertFalse(hash.delete("ghi"));
        assertTrue(hash.delete("def"));
        assertEquals("[[BRIDGE], null, abc, dsc, null]", hash.toString());
        hash.insert("ghi");
        hash.insert("jkl");
        hash.insert("mno");
        hash.delete("jkl");
        assertEquals("[mno, ghi, null, null, null, null, null, abc, dsc, [BRIDGE]]", hash.toString());
        assertThrows(NullPointerException.class, () -> {
            hash.delete(null);
        });
    }

    @org.junit.jupiter.api.Test
    void lookup() {
        hash.insert("abc");
        hash.insert("def");
        hash.insert("ghi");
        assertTrue(hash.lookup("def"));
        assertFalse(hash.lookup("dsc"));
        hash.delete("abc");
        assertFalse(hash.lookup("abc"));
        assertThrows(NullPointerException.class, () -> {
            hash.lookup(null);
        });
    }

    @org.junit.jupiter.api.Test
    void returnAll() {
        //No elements
        String[] hashReturn = hash.returnAll();
        String output = "";
        for(String s : hashReturn)
            output += s + " ";
        assertEquals(output, "");

        //Some elements
        hash.insert("abc");
        hash.insert("def");
        hash.insert("dsc");
        hashReturn = hash.returnAll();
        output = "";
        for(String s : hashReturn)
            output += s + " ";
        assertEquals(output, "def abc dsc ");

        //Rehashed table
        hash.insert("ghi");
        hash.insert("jkl");
        hash.insert("mno");
        hashReturn = hash.returnAll();
        output = "";
        for(String s : hashReturn)
            output += s + " ";
        assertEquals(output, "def ghi mno jkl abc dsc ");
    }

    @org.junit.jupiter.api.Test
    void size() {
        assertEquals(0, hash.size());
        hash.insert("abc");
        assertEquals(hash.size(), 1);
        hash.insert("def");
        hash.insert("ghi");
        hash.delete("def");
        assertEquals(hash.size(), 2);
    }

    @org.junit.jupiter.api.Test
    void capacity() {
        assertEquals(hash.capacity(), 5);
        for(String s : new String[]{"A", "B", "C", "D", "E", "F"})
            hash.insert(s);
        assertEquals(hash.capacity(), 10);
        hash = new MyHashTable();
        assertEquals(hash.capacity(), 20);
    }

    @org.junit.jupiter.api.Test
    void getStatsLog() {
        for(String s : new String[]{"A", "B", "C", "D", "U", "F"})
            hash.insert(s);
        assertEquals("Before rehash # 1: load factor 0.80, 4 collision(s).\n", hash.getStatsLog());
        for(String s : new String[]{"G", "H", "I", "J", "K", "V"})
            hash.insert(s);
        assertEquals("Before rehash # 1: load factor 0.80, 4 collision(s).\n" +
                "Before rehash # 2: load factor 0.80, 4 collision(s).\n", hash.getStatsLog());

        hash = new MyHashTable(7);
        for(String s : new String[]{"Z", "Y", "X", "W", "V", "U"})
            hash.insert(s);
        assertEquals("Before rehash # 1: load factor 0.71, 0 collision(s).\n", hash.getStatsLog());
        for(String s : new String[]{"L", "K", "J", "I", "H", "G"})
            hash.insert(s);
        assertEquals("Before rehash # 1: load factor 0.71, 0 collision(s).\n" +
                "Before rehash # 2: load factor 0.71, 25 collision(s).\n", hash.getStatsLog());
    }
}