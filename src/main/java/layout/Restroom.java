package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Restroom extends POI{

    public Restroom(int terminal) {
        super("Restroom", terminal, UUID.fromString('c' + UUID.randomUUID().toString().substring(1)));
    }

    @JsonCreator
    public Restroom(@JsonProperty("terminal") int terminal,
                  @JsonProperty("uuid") UUID uuid) {
        super("Restroom", terminal, uuid);
    }
}
