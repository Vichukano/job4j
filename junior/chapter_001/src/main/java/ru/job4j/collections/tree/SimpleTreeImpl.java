package ru.job4j.collections.tree;

import java.util.*;

/**
 * Класс реализует хранилище - дерево.
 * Может хранить только уникальные элементы.
 * Реализует интерфейс SimpleTree.
 * Реализован итератор.
 *
 * @param <E> тип передаваемого в контейнер объекта.
 */
public class SimpleTreeImpl<E extends Comparable<E>> implements SimpleTree<E> {
    private Node<E> root;

    /**
     * Конструктор принимает значение для корневого узла.
     *
     * @param value значениеб хранимое в корневом узле.
     */
    public SimpleTreeImpl(E value) {
        root = new Node<>(value);
    }

    /**
     * Метод добавляет значение в контейнер - дерево.
     * Создается узел - родитель посредством вызова метода findBy.
     * Если созданный узел - родитель не null, в список дочерних узлов добавляется новый Node с передаваемым значением.
     *
     * @param parent значение в узле - родителе.
     * @param child  значение, добавляемого в дерево узла.
     * @return true если элемент добавленб, иначе false.
     */
    @Override
    public boolean add(E parent, E child) {
        boolean result = false;
        Optional<Node<E>> parentNode = findBy(parent);
        if (parentNode.isPresent()) {
            if (parentNode.get().add(new Node<>(child))) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Метод ищет узел - родитель с переданным значением.
     * Реализуется алгоритм поиска в ширину.
     *
     * @param value - значение в узле - родителе.
     * @return true если узел - родитель найдейн, иначе false.
     */
    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue((E) value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    /**
     * Реализация итератора.
     *
     * @return класс - итератор контейнера.
     */
    @Override
    public Iterator<E> iterator() {
        return new SimpleTreeIterator<E>(root);
    }

    /**
     * Вложенный класс - итератор.
     * Все узлы дерева добавляются в очередь.
     * Итерация идет по очереди.
     *
     * @param <E>
     */
    private class SimpleTreeIterator<E extends Comparable<E>> implements Iterator<E> {
        private Queue<Node<E>> queue = new LinkedList<>();
        private Iterator it;

        /**
         * Конструктор клсса - итератор.
         * При инициализации класса все узлы дерева добавляеются в очередь queue
         * и инициализируется итератор очереди.
         *
         * @param root
         */
        public SimpleTreeIterator(Node<E> root) {
            fill(root);
            it = queue.iterator();
        }

        /**
         * Метод помещает все узлы дерева в хранилище - очередь.
         * Создается новая очередь data. В него и в queue добавляется переданный в метод Node.
         * В цикле, пока в очереди data есть объекты, создается новый узел element, равный первому удаленному
         * узлу в очереди data.
         * Через цикл for в очередь data и queue добавляются дочерние узлы.
         * После этого создается новый Node, являющийся дочерним к переданному в методе.
         *
         * @param n узел дерева.
         */
        private void fill(Node<E> n) {
            Queue<Node<E>> data = new LinkedList<>();
            queue.offer(n);
            data.offer(n);
            while (!data.isEmpty()) {
                Node<E> element = data.poll();
                for (Node<E> child : element.leaves()) {
                    data.offer(child);
                    queue.offer(child);
                }
            }
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public E next() {
            return ((Node<E>) it.next()).getValue();
        }
    }
}
