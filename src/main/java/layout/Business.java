package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Business extends POI{

    public Business(String name, int terminal){
        super(name, terminal, UUID.fromString('b' + UUID.randomUUID().toString().substring(1)));
    }

    @JsonCreator
    public Business(@JsonProperty("name") String name,
                    @JsonProperty("terminal") int terminal,
                    @JsonProperty("uuid") UUID uuid){
        super(name, terminal, uuid);
    }
}
