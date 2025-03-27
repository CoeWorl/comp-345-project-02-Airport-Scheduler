package layout;

import java.util.UUID;

public class Gate extends POI{
    private boolean isEntranceToTerminal;

    public Gate(String name, int terminal, boolean isEntranceToTerminal){
        String str_uuid = UUID.randomUUID().toString();
        UUID uuid = UUID.fromString('a' + str_uuid.substring(1));
        super(name, terminal, uuid);
        this.isEntranceToTerminal = isEntranceToTerminal;
    }

    public Gate(String name, int terminal) {
        super(name, terminal);
    }
}
