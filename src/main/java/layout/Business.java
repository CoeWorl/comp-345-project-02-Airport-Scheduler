package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Business extends POI{

    public Business(String name, int terminal){
        String str_uuid = UUID.randomUUID().toString();
        UUID uuid = UUID.fromString('b' + str_uuid.substring(1));
        super(name, terminal, uuid);
    }

    @JsonCreator
    public Business(@JsonProperty("name") String name,
                    @JsonProperty("terminal") int terminal,
                    @JsonProperty("uuid") UUID uuid){
        super(name, terminal, uuid);
    }
}
