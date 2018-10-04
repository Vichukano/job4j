package tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс - модель заявки.
 */
public class Item {
    private String id;
    private String name;
    private String desc;
    private long created;
    private List<String> comments = new ArrayList<>();

    public Item(String name, String desc, long created) {
        this.name = name;
        this.desc = desc;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }

    @Override
    public String toString() {
        return "Id='" + id + '\''
                + ", Name='" + name + '\''
                + ", Description='" + desc + '\'';
    }
}
