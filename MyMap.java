package myMap;

public interface MyMap<K,V>{
    int size();

    V put (K key, V value);

    V get(K key);

    V remove(K object);

    interface Entry<K,V>{
        K getKey();

        V getValue();

        V setValue(V var1);

        boolean equals(Object var1);

        int hashCode();
    }
}
