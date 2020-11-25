package myMap;

import java.util.*;

public class MyHashMap<K,V> implements MyMap<K,V>{
    private int size;
    private Node<K,V>[] table;

    public int size() {
        return size;
    }

    public V put(K key, V value) {
        return putVal(key, value, hash(key));
    }

    final V putVal(K key, V value, int hash){
        Node<K,V>[] tab;
        int n;

        if ((tab = this.table) == null || (n = tab.length) == 0){
            n = (tab = resize()).length;
        }

        int i;
        Node<K,V> node;
        V oldValue = null;

        if (tab[i = n - 1 & hash] == null){
            node = newNode(hash, key, value);
            tab[i] = node;
        } else {
            node = tab[i];

            while(true){
                if (node.hash == hash && Objects.equals(key, node.key)){
                    oldValue = node.value;
                    node.value = value;
                    break;
                }

                if (node.next == null){
                    node.next = newNode(hash, key, value);
                    break;
                }

                node = node.next;
            }
        }

        if (++this.size > table.length){
            resize();
        }

        return oldValue;
    }

    public V get(K key) {
        Node<K,V> e;
        return (e = this.getNode(hash(key), key)) == null ? null : e.value;
    }

    final Node<K,V> getNode(int hash, K key){
        Node<K,V>[] tab = this.table;
        Node<K,V> node;
        int n;

        if (tab != null && (n = tab.length) != 0 && (node = tab[n - 1 & hash]) != null){

            while (true){
                if (node.hash == hash && (Objects.equals(node.key, key))){
                    return node;
                }

                if (node.next == null){
                    break;
                }

                node = node.next;
            }
        }

        return null;
    }

    public V remove(K key) {
        Node<K,V> e;
        return (e = this.removeNode(hash(key), key)) == null ? null : e.value;
    }

    final Node<K, V> removeNode(int hash, Object key) {
        Node<K,V>[] tab;
        Node<K,V> node;
        Node<K,V> previousNode = null;
        int n;
        int index;

        if ((tab = this.table) != null && (n = tab.length) > 0 && (node = tab[index = n - 1 & hash]) != null){
            while (true){
                if (node.hash == hash && (Objects.equals(node.key, key))){
                    if (previousNode == null){
                        this.table[index] = node.next;
                    } else {
                        previousNode.next = node.next;
                    }
                    size--;
                    return node;
                }
                if (node.next == null){
                    break;
                }
                previousNode = node;
                node = node.next;
            }
        }

        return null;
    }

    static int hash(Object key){
        return key == null ? 0 : (key.hashCode());
    }

    final Node<K,V>[] resize(){
        Node<K, V>[] oldTab = this.table;
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

        Node<K, V>[] newTab = new Node[newCap];
        Node<K,V> oldNode;
        Node<K,V> newNode;
        this.table = newTab;

        if (oldTab != null){
            for (int i = 0; i < oldCap; i++){
                if ((oldNode = oldTab[i]) != null) {
                    oldTab[i] = null;
                    List<Node<K,V>> nextNodesInOldNode = new ArrayList<>();

                    while (true){
                        nextNodesInOldNode.add(oldNode);

                        if (oldNode.next == null){
                            break;
                        }

                        oldNode = oldNode.next;
                    }

                    for (Node<K,V> node : nextNodesInOldNode) {
                        if ((newNode = newTab[node.hash & newCap - 1]) == null){
                            node.next = null;
                            newTab[node.hash & newCap - 1] = node;
                        }else {
                            while (true){
                                if (newNode.next == null){
                                    node.next = null;
                                    newNode.next = node;
                                    break;
                                }

                                newNode = newNode.next;
                            }
                        }
                    }
                }
            }
        }
        return newTab;
    }

    final Node<K, V> newNode(int hash, K key, V value) {
        return new Node<>(hash, key, value, null);
    }

    static class Node<K,V> implements Entry<K, V> {
        final K key;
        final int hash;
         V value;
         Node<K,V> next;

        public Node(int hash, K key, V value, Node<K, V> next) {
            this.key = key;
            this.hash = hash;
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
                    return Objects.equals(this.key, e.getKey()) && Objects.equals(this.value, e.getValue());
                }

                return false;
            }
        }
    }
}