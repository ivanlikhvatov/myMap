package myMap;

import java.util.*;

public class MyHashMap<K,V> implements MyMap<K,V> {

    private MyHashMap.Node<K, V>[] table;
    private int size;

    MyHashMap.Node<K, V> newNode(int hash, K key, V value, MyHashMap.Node<K, V> next) {
        return new MyHashMap.Node(hash, key, value, next);
    }

    public int size() {
        return this.size;
    }

    public V put(K key, V value) {
        return this.putVal(hash(key), key, value);
    }

    public V putVal(int hash, K key, V value){
        MyHashMap.Node[] tab;
        int n;

        if ((tab = this.table) == null || (n = tab.length) == 0) {
            n = (tab = this.resize()).length;
        }

        Object p;
        int i;
        if ((p = tab[i = n - 1 & hash]) == null) {
            tab[i] = this.newNode(hash, key, value, (MyHashMap.Node)null);
            size++;
        }else{
            Object pKey;
            Object newNode;
            if (((MyHashMap.Node)p).hash == hash && ((pKey = ((MyHashMap.Node)p).key) == key || key != null && key.equals(pKey))){
                newNode = p;
            } else {

                while (true){
                    if ((newNode = ((MyHashMap.Node)p).next) == null) {
                        ((MyHashMap.Node)p).next = this.newNode(hash, key, value, (MyHashMap.Node)null);
                        size++;
                        break;
                    }

                    if (((MyHashMap.Node)newNode).hash == hash && ((pKey = ((MyHashMap.Node)newNode).key) == key || key != null && key.equals(pKey))) {
                        break;
                    }

                    p = newNode;
                }
            }

            if (newNode != null) {
                V oldValue = (V) ((Node)newNode).value; //TODO
                return oldValue;
            }

        }
        return null;
    }

    public V get(Object key) {
        MyHashMap.Node e;
        return (e = this.getNode(hash(key), key)) == null ? null : (V)e.value;
    }

    final MyHashMap.Node<K, V> getNode(int hash, Object key) {
        MyHashMap.Node[] tab;
        MyHashMap.Node first;
        int n;
        if ((tab = this.table) != null && (n = tab.length) > 0 && (first = tab[n - 1 & hash]) != null) {
            Object k;
            if (first.hash == hash && ((k = first.key) == key || key != null && key.equals(k))) {
                return first;
            }

            MyHashMap.Node e;
            if ((e = first.next) != null) {
                do {
                    if (e.hash == hash && ((k = e.key) == key || key != null && key.equals(k))) {
                        return e;
                    }
                } while((e = e.next) != null);
            }
        }

        return null;
    }

    public V remove(Object key) {
        MyHashMap.Node e;
        return (e = this.removeNode(hash(key), key)) == null ? null : (V) e.value;
    }

    final MyHashMap.Node<K, V> removeNode(int hash, Object key) {
        MyHashMap.Node[] tab;
        MyHashMap.Node p;
        MyHashMap.Node previousNode = null;
        int n;
        int index;

        if ((tab = this.table) != null && (n = tab.length) > 0 && (p = tab[index = n - 1 & hash]) != null){
            if (p.next == null){
                this.table[index] = null;
                size--;
                return (MyHashMap.Node)p;
            } else {
                while (true){
                    if (p.hash == hash && (p.key == key || (p.key != null && p.key.equals(key)))){
                        if (previousNode == null){
                            this.table[index] = (MyHashMap.Node)p.next;
                        } else {
                            previousNode.next = p.next;
                        }
                        size--;
                        return (MyHashMap.Node)p;
                    }

                    if (p.next == null){
                        break;
                    }

                    previousNode = p;
                    p = p.next;
                }
            }
        }

        return null;
    }

    static final int hash(Object key) {
        return key == null ? 0 : (key.hashCode());
    }

    final MyHashMap.Node<K,V>[] resize(){
        MyHashMap.Node<K, V>[] oldTab = this.table;

        int oldCap = oldTab == null ? 0 : oldTab.length;
        int newCap;

        if (oldCap > 0){
            if (oldCap >= 1073741824){
                return oldTab;
            } else {
                newCap = oldCap + 5;
            }
        } else {
            newCap = 16;
        }

        MyHashMap.Node<K, V>[] newTab = new MyHashMap.Node[newCap];
        this.table = newTab;

        return newTab;
    }

    static class Node<K, V> implements MyMap.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        MyHashMap.Node<K, V> next;

        Node(int hash, K key, V value, MyHashMap.Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey() {
            return this.key;
        }

        public final V getValue() {
            return this.value;
        }

        public final String toString() {
            return this.key + "=" + this.value;
        }

        public final int hashCode() {
            return Objects.hashCode(this.key) ^ Objects.hashCode(this.value);
        }

        public final V setValue(V newValue) {
            V oldValue = this.value;
            this.value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            } else {
                if (o instanceof Map.Entry) {
                    MyMap.Entry<?, ?> e = (MyMap.Entry)o;
                    if (Objects.equals(this.key, e.getKey()) && Objects.equals(this.value, e.getValue())) {
                        return true;
                    }
                }

                return false;
            }
        }
    }
}
