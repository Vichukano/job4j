package patterns.decorator;

public class TeamLeadJavaDeveloper extends DeveloperDecorator {

    public TeamLeadJavaDeveloper(Developer developer) {
        super(developer);
    }

    public String doSeriousJob() {
        return " Be serious";
    }

    @Override
    public String work() {
        return super.work() + doSeriousJob();
    }
}
