package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Stairs extends POI{

    public Stairs(int terminal) {
        super("Stairs", terminal, UUID.fromString('e' + UUID.randomUUID().toString().substring(1)));
    }

    @JsonCreator
    public Stairs(@JsonProperty("terminal") int terminal,
                @JsonProperty("uuid") UUID uuid) {
        super("Stairs", terminal, uuid);
    }
}
