package layout;

public class Gate extends POI{
    private boolean isEntranceToTerminal;

    public Gate(String name, boolean isEntranceToTerminal){
        super(name);
        this.isEntranceToTerminal = isEntranceToTerminal;
    }
}
