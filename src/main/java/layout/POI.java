package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public abstract class POI {
    static HashMap<UUID,POI> POI_MAP = new HashMap<>();
    final String name;
    final UUID uuid;
    final int terminal;

    public POI(String name, int terminal, UUID id) {
        this.name = name;
        this.uuid = id;
        this.terminal = terminal;
    }

    @JsonCreator
    public POI(@JsonProperty("name") String name, @JsonProperty("uuid") UUID uuid,
               @JsonProperty("terminal") int terminal) {
        if(!POI_MAP.containsKey(uuid)) {
            POI_MAP.put(uuid, this);
            this.name = name;
            this.uuid = uuid;
            this.terminal = terminal;
        }
        else {
            POI poi = POI_MAP.get(uuid);
            this.name = poi.getName();
            this.uuid = poi.getUuid();
            this.terminal = poi.getTerminal();
        }
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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof POI poi)) return false;
        return Objects.equals(uuid, poi.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
