package layout;

import java.util.UUID;

public abstract class POI {
    private String name;
    private UUID uuid;

    public POI(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }
}
