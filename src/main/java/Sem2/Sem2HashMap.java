package Sem2;

import java.util.ArrayList;
import java.util.List;

/**
 The purpose of Sem2HashMap is to...

 @author kasper
 */
public class Sem2HashMap {

    private KeyValuePair[] internal;
    private int size;

    public Sem2HashMap() {
        internal = new KeyValuePair[ 3 ];
        size = 0;
    }

    public Address get( String key ) {
        int index = findIndex( key );
        if ( internal[ index ] == null ) {
            return null;
        } else {
            return internal[ index ].value;
        }
    }

    private int findIndex( String key ) {
        int index = Math.abs( key.hashCode() ) % internal.length;
        while ( internal[ index ] != null
                && !internal[ index ].key.equals( key ) ) {
            index = ( index + 1 ) % internal.length;
        }
        return index;
    }

    public void put( String key, Address value ) {
        int index = findIndex( key );
        if ( internal[ index ] == null ) {
            internal[ index ] = new KeyValuePair( key, value );
            size ++;
            if (size*4 > internal.length*3) {
                extend();
            }
        } else {
            internal[ index ].value = value;
        }
    }

    public boolean containsKey( String key ) {
        int index = findIndex(key);
        return internal[ index ] != null;
    }
    
    public void remove(String key){
        int index = findIndex(key);
        if (internal[index] == null) return;
        internal[index] = null; // remove the key
        size --;
        index = (index + 1) % internal.length;
        ArrayList<KeyValuePair> mayMove = new ArrayList();
        while (internal[index] != null){
            mayMove.add( internal[index]);
            internal[index] = null;
            size--;
            index = (index + 1) % internal.length;
        }
        for (KeyValuePair kp : mayMove)
            put(kp.key, kp.value);
        if (size*4 < internal.length){
            reduceSize();
        }
    }

    public int size() {
        return size;
    }

    public List<Address> values() {
        ArrayList<Address> all = new ArrayList();
        for ( int i = 0; i < internal.length; i++ ) {
            if ( internal[ i ] != null ) {
                all.add( internal[ i ].value );
            }
        }
        return all;
    }

    private void extend() {
        KeyValuePair[] existing = internal;
        size = 0;
        internal = new KeyValuePair[ existing.length*2];
        for (KeyValuePair kp : existing){
            if ( kp != null)
                put(kp.key, kp.value);
        }
    }

    private void reduceSize() {
        KeyValuePair[] existing = internal;
        size = 0;
        internal = new KeyValuePair[ existing.length/2];
        for (KeyValuePair kp : existing){
            if ( kp != null)
                put(kp.key, kp.value);
        }
    }
}
