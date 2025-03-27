package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Connection {
    private final int weight;
    private final UUID uuid;

    @JsonCreator
    public Connection(@JsonProperty("weight") int weight,
                      @JsonProperty("uuid") UUID uuid) {
        this.weight = weight;
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return STR."Connection{weight=\{weight}, destUUID=\{uuid}}";
    }

    public int getWeight() {
        return weight;
    }

    public UUID getUuid() {
        return uuid;
    }
}