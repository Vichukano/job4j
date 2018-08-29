package ru.job4j.collections.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс представляет реализацию структуры данных HashMap.
 *
 * @param <K> ключ.
 * @param <V> значение.
 */
public class SimpleHashMap<K, V> implements Iterable<V> {
    private Entry<K, V>[] table;
    private int modCount;

    /**
     * Конструктор. При инициализации экземпляра класса создается массив типа Object размером 16.
     */
    public SimpleHashMap() {
        this.table = new Entry[16];
    }

    /**
     * Метод кставки значений в контейнер. Вставка производится за фиксированное время O(1).
     * Вычисляется хэш ключа.
     * Вычисляется индекс, куда будет помезщен объект Entry.
     * Если индекс больше массива, его размер увеличивается.
     * Если ячейка массива пуста, в нее помещяется объект Entry, хранящий ключ и значение.
     *
     * @param key   ключ.
     * @param value значение
     * @return true, если элемент добавлен, иначе false.
     */
    public boolean insert(K key, V value) {
        boolean result = false;
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        while (index >= table.length) {
            enlargeSize();
        }
        if (table[index] == null) {
            table[index] = new Entry<>(key, value);
            result = true;
            modCount++;
        }
        return result;
    }

    /**
     * Метод возвращает значение, соответсвующее передаваемому ключу. Работает за фиксированное время O(1).
     * Высчитывается хэш ключа.
     * Высчитывается индекс ячейки массива.
     * Если ячейка не пустая, то возвращается значение из объекта Entry в этой ячейке.
     *
     * @param key ключ.
     * @return значение соответствуюшее передаваемому ключу.
     * @throws NoSuchElementException если такого ключа нет в таблице.
     */
    public V get(K key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        if (table[index] != null) {
            Entry<K, V> e = table[index];
            V value = e.value;
            return value;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Метод возвращает ключ, соответствующий заданному значению. Работает за линейное время O(n).
     * Реализован проход по элементам массива и пропуск пустых ячеек.
     * Если ячейка не пустая, то сравнивается значение value объекта Entry, хранящегося в ней, с передаваемы значением.
     * В случае совпадения значений, цикл прерывается и возвращается ключ, соответствующий передаваемому значению.
     *
     * @param value значение.
     * @return key ключ, соотвествующий передаваемому значению.
     * @throws NoSuchElementException если передаваемого значения нет в контейнере.
     */
    public K getByKey(V value) {
        K key = null;
        for (int i = 0; i < table.length; i++) {
            while (i < table.length && table[i] == null) {
                i++;
            }
            if (i < table.length) {
                Entry<K, V> e = table[i];
                if (e.value.equals(value)) {
                    key = e.key;
                    break;
                }
            } else {
                throw new NoSuchElementException();
            }
        }
        return key;
    }

    /**
     * Метод удаляет объект из контейнера, хранящий передаваемый ключ. Работает за фиксированное время O(1).
     * Вычисляется хэш ключа.
     * Расчитывается ячейка массива, где хранится объект с передаваемым ключом.
     *
     * @param key ключ удаляемого объекта.
     * @return true, если объект удален. False, если объекта с таким ключом нет в контейнере.
     */
    public boolean delete(K key) {
        boolean result = false;
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        if (table[index] != null) {
            table[index] = null;
            result = true;
            modCount++;
        }
        return result;
    }

    /**
     * Метод вычисляет хэш ключа по аналогии с HashMap.
     *
     * @param key ключ.
     * @return хэш ключа.
     */
    private int hash(K key) {
        int keyHashCode = key.hashCode();
        keyHashCode ^= (keyHashCode >>> 20) ^ (keyHashCode >>> 12);
        return keyHashCode ^ (keyHashCode >>> 7) ^ (keyHashCode >>> 4);
    }

    /**
     * Метод расчитывает ячейку массива, куда будет помещен объект Entry.
     *
     * @param hash      хэш ключа.
     * @param tableSize размер массива, где хранятся Entry.
     * @return int индекс ячейка массива table.
     */
    private int indexFor(int hash, int tableSize) {
        return hash % tableSize;
    }

    /**
     * Метод увеличивает размер массива table в 2 раза.
     */
    private void enlargeSize() {
        int newLength = table.length * 2;
        Entry<K, V>[] newContainer = new Entry[newLength];
        System.arraycopy(table, 0, newContainer, 0, table.length);
        table = newContainer;
        transfer(table);
    }

    /**
     * Метод перераспределяет объекты Entry по ячейкам массива новой длинны.
     *
     * @param table массив новой длинны.
     */
    private void transfer(Entry<K, V>[] table) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                Entry<K, V> e = table[i];
                int hash = hash(e.key);
                int index = indexFor(hash, table.length);
                table[index] = e;
                table[i] = null;
            }
        }
    }

    /**
     * Реализация итератора.
     *
     * @return класс - итератор.
     */
    @Override
    public Iterator<V> iterator() {
        return new SimpleHashMapIterator();
    }

    /**
     * Класс - итератор для значений.
     *
     * @param <V> value значение.
     */
    private class SimpleHashMapIterator<V> implements Iterator<V> {
        private int cursor;
        private int expectedModCount;

        public SimpleHashMapIterator() {
            this.expectedModCount = modCount;
        }

        /**
         * Метод проверяет есть ли значения в массиве.
         * В цикде while в условии сначала проверяется cursor < table.length, иначе будет выход за пределы массива
         * и бросит ArrayIndexOutOfBoundsException.
         *
         * @return true если есть элементы, иначе false.
         */
        @Override
        public boolean hasNext() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (this.cursor < table.length) {
                while (cursor < table.length && table[cursor] == null) {
                    cursor++;
                }
            }
            return cursor < table.length;
        }

        @Override
        public V next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                Entry<K, V> e = (Entry<K, V>) table[cursor++];
                return e.value;
            }
        }
    }

    /**
     * Класс-хранилище. Экземпляры класса содержат ключи и значения. Хранится в массиве table.
     *
     * @param <K> ключ.
     * @param <V> значение.
     */
    private static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
