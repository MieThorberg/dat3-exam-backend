package errorhandling;

import javax.ws.rs.WebApplicationException;

public class EntityNotFoundException extends WebApplicationException {

    public EntityNotFoundException() {
        super("The entity was not found", 404);
    }

    public EntityNotFoundException(String msg) {
        super(msg, 404);
    }
}
