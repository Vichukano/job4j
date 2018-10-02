package tracker;

import java.util.List;

/**
 * Метод для теста пользовательского вода в консоль.
 */
public class StubInput implements Input {
    private final String[] value;
    private int position;

    public StubInput (final String[] value) {
        this.value = value;
    }


    @Override
    public String ask(String question) {
        return this.value[this.position++];
    }

    @Override
    public int ask(String question, List<Integer> range) {
        int key = Integer.valueOf(this.ask(question));
        for (int value : range) {
            if (value == key) {
                break;
            }
        }
        return key;
    }
}
