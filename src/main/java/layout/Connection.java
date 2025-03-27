package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Connection {
    private final int weight;
    private final String type;
    private final UUID uuid;

    @JsonCreator
    public Connection(@JsonProperty("weight") int weight,
                      @JsonProperty("type") String type,
                      @JsonProperty("uuid") UUID uuid) {
        this.weight = weight;
        this.type = type;
        this.uuid = uuid;
    }

    public int getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

    public UUID getUuid() {
        return uuid;
    }
}