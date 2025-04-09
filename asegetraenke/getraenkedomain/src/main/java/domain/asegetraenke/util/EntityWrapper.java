package domain.asegetraenke.util;

import java.util.UUID;

public abstract class EntityWrapper<T extends EntityWrapper<T>> {
    protected final UUID id;
    protected EntityWrapper(){
        id = UUID.randomUUID();
    }
    public UUID getId(){
        return id;
    }
}
