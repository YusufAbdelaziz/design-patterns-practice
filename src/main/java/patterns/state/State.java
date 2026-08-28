package patterns.state;

/**
 * A stage in an {@link Order}'s lifecycle.
 *
 * <p>Each concrete state answers the four order events its own way, and is
 * responsible for transitioning the order to the next state when the event is
 * legal. The order reference (and how a state reaches its siblings) is provided
 * here; the per-state behavior is the exercise.
 */
public abstract class State {

    /** The order this state belongs to — use it to transition via {@code order.setState(...)}. */
    protected final Order order;

    protected State(Order order) {
        this.order = order;
    }

    /** The customer pays for the order. */
    public abstract String pay();

    /** The warehouse ships the order. */
    public abstract String ship();

    /** The courier marks the order delivered. */
    public abstract String deliver();

    /** The customer (or system) cancels the order. */
    public abstract String cancel();
}
