package patterns.chainofresponsibility;

/**
 * One link in the approval chain.
 *
 * <p>
 * An approver either handles a {@link PurchaseRequest} itself (if it's within
 * its authority) or passes it along to the next approver. The requester never
 * knows — or cares — who ends up approving.
 *
 * <p>
 * Storage of the link is done for you ({@link #linkTo} / {@link #next}). Your
 * job is the routing in {@link #handle} and the concrete approvers' authority.
 */
public abstract class Approver {

    private Approver next;

    /**
     * Links {@code next} after this approver and returns it, so chains read
     * fluently:
     *
     * <pre>{@code
     * TeamLead lead = new TeamLead();
     * lead.linkTo(new Manager()).linkTo(new Director()).linkTo(new CFO());
     * // lead is the head of the chain
     * }</pre>
     */
    public Approver linkTo(Approver next) {
        this.next = next;
        return next;
    }

    /** The next approver in the chain, or {@code null} at the end. */
    protected Approver next() {
        return next;
    }

    /** Whether this approver is authorised to approve the request itself. */
    protected abstract boolean canApprove(PurchaseRequest request);

    /** Human-readable job title, used in the {@link Approval} result. */
    protected abstract String title();

    /**
     * Routes the request through the chain:
     * <ol>
     * <li>if this approver {@link #canApprove}, it approves;</li>
     * <li>otherwise the request goes to the {@link #next} approver;</li>
     * <li>if there is no next approver, the request is denied.</li>
     * </ol>
     */
    public Approval handle(PurchaseRequest request) {
        // - approve -> return Approval.by(title())
        // - delegate -> next().handle(request)
        // - dead end -> return Approval.denied()
        if (this.canApprove(request)) {
            return Approval.by(this.title());
        } else if (this.next() != null) {
            return this.next().handle(request);
        }
        return Approval.denied();
    }
}
