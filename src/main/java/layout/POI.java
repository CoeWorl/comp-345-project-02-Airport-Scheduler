package layout;

import java.util.UUID;

public abstract class POI {
    private String name;
    private Terminal terminal;
    private UUID uuid;

    public POI(String name, Terminal terminal) {
        this.name = name;
        this.terminal = terminal;
        this.uuid = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public UUID getUuid() {
        return uuid;
    }
}
