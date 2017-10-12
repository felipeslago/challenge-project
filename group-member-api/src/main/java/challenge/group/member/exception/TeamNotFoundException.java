package challenge.group.member.exception;

/**
 * A custom exception for treating not found teams.
 */
public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(String message) {
        super(message);
    }
}
