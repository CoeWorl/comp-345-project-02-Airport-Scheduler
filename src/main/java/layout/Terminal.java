package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Terminal {
    private final String name;
    private final UUID uuid;
    public Terminal(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    @JsonCreator
    public Terminal(@JsonProperty("name") String name, @JsonProperty("uuid") UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }
}
