package model;

public class LogicNot implements LogicComponent {
    private LogicComponent input;

    public LogicNot(LogicComponent input) {
        this.input = input;
    }
}
