package myMap;

public interface MyMap<K, V> {
    int size();

    V put(K key, V value);

    V get(Object o);

    V remove(Object key);

    interface Entry<K, V> {
        K getKey();

        V getValue();

        V setValue(V var1);

        boolean equals(Object var1);

        int hashCode();
    }
}
