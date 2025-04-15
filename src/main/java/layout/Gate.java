package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Gate extends POI{
    private final boolean isEntranceToTerminal;

    public Gate(String name, int terminal, boolean isEntranceToTerminal) {
        super(name, terminal, UUID.fromString('a' + UUID.randomUUID().toString().substring(1)));
        this.isEntranceToTerminal = isEntranceToTerminal;
    }

    @JsonCreator
    public Gate(@JsonProperty("name") String name,
                @JsonProperty("terminal") int terminal,
                @JsonProperty("isEntranceToTerminal") boolean isEntranceToTerminal,
                @JsonProperty("uuid") UUID uuid) {
        super(name, terminal, uuid);
        this.isEntranceToTerminal = isEntranceToTerminal;
    }

    public boolean isEntranceToTerminal() {
        return isEntranceToTerminal;
    }
}
