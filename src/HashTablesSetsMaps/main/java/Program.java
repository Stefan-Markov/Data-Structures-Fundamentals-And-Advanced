package HashTablesSetsMaps.main.java;

import java.util.Iterator;
import java.util.LinkedList;

public class Program {
    static <K extends Comparable<K>> boolean isLessThan(K a, K b) {
        return a.compareTo(b) < 0;
    }

    static class HashTable<K, V> {

        public static final int INITIAL_CAPACITY = 16;
        public static final double MAX_LOAD_FACTOR = 0.7;

        static class KeyValue<K, V> {
            K key;
            V value;

            public KeyValue(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        LinkedList<KeyValue<K, V>>[] table;
        int size;

        public HashTable() {
            table = new LinkedList[INITIAL_CAPACITY];
            size = 0;
        }

        public void put(K key, V value) {
            int index = getHashTableIndex(key);

            if (table[index] == null) {
                LinkedList<KeyValue<K, V>> list = new LinkedList<KeyValue<K, V>>();
                list.add(new KeyValue<>(key, value));
                table[index] = list;
            } else {
                LinkedList<KeyValue<K, V>> list = table[index];

                Iterator<KeyValue<K, V>> iterator = list.iterator();
                boolean foundValue = false;
                for (KeyValue<K, V> keyValue : list) {
                    if (keyValue.key.equals(key)) {
                        keyValue.value = value;
                        foundValue = true;
                        break;
                    }
                }

                if (!foundValue) {
                    list.add(new KeyValue<>(key, value));
                }
            }

            size++;

            reziseIfNecessary();
        }

        public V get(K key) {
            LinkedList<KeyValue<K, V>> list = table[getHashTableIndex(key)];
            if (list == null) {
                return null;
            }

            for (KeyValue<K, V> keyValue : list) {
                if (keyValue.key.equals(key)) {
                    return keyValue.value;
                }
            }

            return null;
        }

        private void reziseIfNecessary() {
        }

        private int getHashTableIndex(K key) {
            int hashCode = key.hashCode();

            return Math.abs(hashCode) % table.length;
        }
    }

    public static void main(String[] args) {
        HashTable<String, Integer> numbersByNameHashTable = new HashTable<>();

        numbersByNameHashTable.put("zero", -1);
        numbersByNameHashTable.put("zero", 0);
        numbersByNameHashTable.put("one", 1);
        numbersByNameHashTable.put("two", 2);
        numbersByNameHashTable.put("three", 3);
        numbersByNameHashTable.put("four", 4);
        numbersByNameHashTable.put("five", 5);
        numbersByNameHashTable.put("six", 6);
        numbersByNameHashTable.put("seven", 7);
        numbersByNameHashTable.put("eight", 8);
        numbersByNameHashTable.put("nine", 9);
        numbersByNameHashTable.put("enin", -42);

        System.out.println(numbersByNameHashTable.get("zero"));
        System.out.println(numbersByNameHashTable.get("six"));
        System.out.println(numbersByNameHashTable.get("eight"));
        System.out.println(numbersByNameHashTable.get("enin"));
    }

}
