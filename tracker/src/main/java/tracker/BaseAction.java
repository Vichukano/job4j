package tracker;


/**
 * Абстрактный класс, содержащий реализацию общих методов для классво,
 * реализующих интерфейс UserAction.
 */
public abstract class BaseAction implements UserAction {
    private final int key;
    private final String name;

    public BaseAction(final int key, final String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public int key() {
        return this.key;
    }

    @Override
    public String info() {
        return String.format("%s : %s", this.key, this.name);
    }
}
