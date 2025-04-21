package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public abstract class POI {
    final String name;
    final UUID uuid;
    final int terminal;

    public POI(String name, int terminal, UUID id) {
        this.name = name;
        this.uuid = id;
        this.terminal = terminal;
        terminal.addPOI(this);
    }

    @JsonCreator
    public POI(@JsonProperty("name") String name, @JsonProperty("uuid") UUID uuid,
               @JsonProperty("terminal") int terminal) {
        this.name = name;
        this.uuid = uuid;
        this.terminal = terminal;
    }

    @Override
    public String toString() {
        return String.format("POI: %s{name='%s', UUID=%s, terminal=%d}", this.getClass(), name, uuid, terminal);
    }
    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getTerminal() {
        return terminal;
    }
}
