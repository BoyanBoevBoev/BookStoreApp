package org.bosch.intern.core;

import org.bosch.intern.util.Command;
import org.bosch.intern.util.Entity;

public class ClientRequest {
    private Command method;
    private Entity entityParam;
    private String options;

    public ClientRequest(String method, String entityParam, String options) {
        this(method,entityParam);
        this.options = options;
    }

    public ClientRequest(String method, String entityParam) {
        this.method = Command.valueOf(method);
        this.entityParam = Entity.valueOf(entityParam);
    }

    public Command getMethod() {
        return method;
    }

    public Entity getEntityParam() {
        return entityParam;
    }

    public String getOptions() {
        return options;
    }
}
