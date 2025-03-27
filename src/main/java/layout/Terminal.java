package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Terminal {
    private final String name;
    private final UUID uuid;
    private final List<Gate> entrances;

    public Terminal(String name, Gate entrance) {
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.entrances = new LinkedList<>();
        this.entrances.add(entrance);
    }

    @JsonCreator
    public Terminal(@JsonProperty("name") String name, @JsonProperty("uuid") UUID uuid,
                    @JsonProperty("entrances") List<Gate> entrances) {
        this.name = name;
        this.uuid = uuid;
        this.entrances = entrances;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }
}
