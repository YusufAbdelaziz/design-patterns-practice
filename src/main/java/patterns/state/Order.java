package patterns.state;

/**
 * The context: an e-commerce order that moves through a lifecycle.
 *
 * <p>It holds the current {@link State} and delegates every event to it, so the
 * order's behavior changes as its state changes — without a single
 * {@code switch}/{@code if} over a status field.
 *
 * <p>This class is done for you. Your job is the five concrete states.
 */
public class Order {

    // The order owns one instance of each state and keeps a cursor to the current one.
    private final State pending = new PendingState(this);
    private final State paid = new PaidState(this);
    private final State shipped = new ShippedState(this);
    private final State delivered = new DeliveredState(this);
    private final State cancelled = new CancelledState(this);

    private State current;

    public Order() {
        this.current = pending;
    }

    // ---- Events the client fires (delegated to the current state) ----------

    public String pay() {
        return current.pay();
    }

    public String ship() {
        return current.ship();
    }

    public String deliver() {
        return current.deliver();
    }

    public String cancel() {
        return current.cancel();
    }

    // ---- Introspection for clients and tests -------------------------------

    /** Simple name of the current state class, e.g. {@code "PendingState"}. */
    public String stateName() {
        return current.getClass().getSimpleName();
    }

    // ---- Machinery the states use to transition (package-private) ----------

    void setState(State next) {
        this.current = next;
    }

    State pendingState() {
        return pending;
    }

    State paidState() {
        return paid;
    }

    State shippedState() {
        return shipped;
    }

    State deliveredState() {
        return delivered;
    }

    State cancelledState() {
        return cancelled;
    }
}
