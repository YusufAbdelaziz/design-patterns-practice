package patterns.chainofresponsibility;

/**
 * The outcome of routing a {@link PurchaseRequest} through the chain.
 *
 * @param approved whether someone in the chain approved the request
 * @param approver the title of whoever approved it, or {@code null} if denied
 */
public record Approval(boolean approved, String approver) {

    /** An approval granted by the approver with the given {@code title}. */
    public static Approval by(String title) {
        return new Approval(true, title);
    }

    /** A denial — nobody in the chain was authorised to approve. */
    public static Approval denied() {
        return new Approval(false, null);
    }
}
