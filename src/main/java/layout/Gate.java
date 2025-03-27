package layout;

public class Gate extends POI{
    private boolean isEntranceToTerminal;

    public Gate(String name, int terminal, boolean isEntranceToTerminal){
        super(name, terminal);
        this.isEntranceToTerminal = isEntranceToTerminal;
    }
}
