package model;

import java.util.ArrayList;

public class LogicAnd implements LogicComponent {

    private String title;
    private ArrayList<LogicComponent> inputs;

    public LogicAnd(String title, ArrayList<LogicComponent> inputs) {
        this.title = title;
        this.inputs = inputs;
    }

    public ArrayList<LogicComponent> getInputs() {
        return inputs;
    }

    public String title() {
        return title;
    }
}
